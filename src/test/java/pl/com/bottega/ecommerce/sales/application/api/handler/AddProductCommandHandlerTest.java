package pl.com.bottega.ecommerce.sales.application.api.handler;

import org.junit.Before;
import org.junit.Test;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.*;
import pl.com.bottega.ecommerce.sales.application.api.command.AddProductCommand;
import pl.com.bottega.ecommerce.sales.domain.client.*;
import pl.com.bottega.ecommerce.sales.domain.equivalent.SuggestionService;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.*;
import pl.com.bottega.ecommerce.sales.domain.reservation.Reservation.ReservationStatus;
import pl.com.bottega.ecommerce.sales.domain.reservation.*;
import pl.com.bottega.ecommerce.sharedkernel.Money;
import pl.com.bottega.ecommerce.system.application.SystemContext;

import java.util.Date;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

public class AddProductCommandHandlerTest {

    private AddProductCommand addProductCommand;
    private ReservationRepository reservationRepository;
    private ProductRepository productRepository;
    private SuggestionService suggestionService;
    private ClientRepository clientRepository;
    private SystemContext systemContext;
    private AddProductCommandHandler productCommandHandler;
    private Reservation reservation;
    private Product removedProduct;
    private Product availableProduct;
    private Client client;

    @Before
    public void setUp() {
        addProductCommand = new AddProductCommand(Id.generate(), Id.generate(), 1);

        reservationRepository = mock(ReservationRepository.class);
        productRepository = mock(ProductRepository.class);
        suggestionService = mock(SuggestionService.class);
        clientRepository = mock(ClientRepository.class);
        systemContext = new SystemContext();

        productCommandHandler = new AddProductCommandHandler(reservationRepository, productRepository, suggestionService, clientRepository, systemContext);
        reservation = new Reservation(Id.generate(), ReservationStatus.OPENED, new ClientData(Id.generate(), "Kowalski"), new Date(2000, 1, 1));
        removedProduct = new Product(Id.generate(), new Money(10), "Papier toaletowy", ProductType.STANDARD);
        removedProduct.markAsRemoved();
        availableProduct = new Product(Id.generate(), new Money(20), "Papier ścierny", ProductType.STANDARD);
        client = new Client();
    }

    @Test
    public void addOneProductShouldReturnReservationWithOneProduct() {
        when(reservationRepository.load(addProductCommand.getOrderId())).thenReturn(reservation);
        when(productRepository.load(addProductCommand.getProductId())).thenReturn(availableProduct);

        doNothing().when(reservationRepository).save(reservation);

        productCommandHandler.handle(addProductCommand);

        assertThat(reservation.getReservedProducts().size(), is(1));
        verifyZeroInteractions(suggestionService);
        verifyZeroInteractions(clientRepository);
    }

    @Test
    public void addOneRemovedProductShouldAddSuggestedProduct() {
        when(reservationRepository.load(addProductCommand.getOrderId())).thenReturn(reservation);
        when(productRepository.load(addProductCommand.getProductId())).thenReturn(removedProduct);

        when(clientRepository.load(systemContext.getSystemUser().getClientId())).thenReturn(client);
        when(suggestionService.suggestEquivalent(removedProduct, client)).thenReturn(availableProduct);
        doNothing().when(reservationRepository).save(reservation);

        productCommandHandler.handle(addProductCommand);

        verify(reservationRepository, times(1)).load(addProductCommand.getOrderId());
        verify(productRepository, times(1)).load(addProductCommand.getProductId());
        verify(clientRepository, times(1)).load(systemContext.getSystemUser().getClientId());
        verify(suggestionService, times(1)).suggestEquivalent(removedProduct, client);
        verify(reservationRepository, times(1)).save(reservation);
    }
}
