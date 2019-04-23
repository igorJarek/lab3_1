package pl.com.bottega.ecommerce.sales.domain.invoicing;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.ClientData;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.Id;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductData;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductType;
import pl.com.bottega.ecommerce.sharedkernel.Money;

import static org.mockito.Mockito.*;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class BookKeeperTest {

    @Rule
    public MockitoRule rule = MockitoJUnit.rule();

    private BookKeeper bookKeeper;
    private ClientData clientData;

    @Before
    public void setUp() {
        bookKeeper = new BookKeeper(new InvoiceFactory());
        clientData = new ClientData(Id.generate(), "Kowalski");
    }

    @Test
    public void invoiceRequestWIthOneItemMustReturnInvoiceWithOneItem() {
        InvoiceRequest invoiceRequest = new InvoiceRequest(clientData);
        ProductData productData = mock(ProductData.class);
        RequestItem requestItem = new RequestItem(productData, 1, Money.ZERO);

        when(productData.getType()).thenReturn(ProductType.STANDARD);

        TaxPolicy taxPolicy = mock(TaxPolicy.class);
        when(taxPolicy.calculateTax(any(ProductType.class), any(Money.class)))
               .thenReturn(new Tax(Money.ZERO, null));

        invoiceRequest.add(requestItem);
        Invoice invoice = bookKeeper.issuance(invoiceRequest, taxPolicy);

        assertThat(invoice.getItems().size(), is(1));
    }
}
