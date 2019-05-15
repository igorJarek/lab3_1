package test.builder;

import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.ClientData;
import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.Id;
import pl.com.bottega.ecommerce.sales.domain.reservation.Reservation;
import pl.com.bottega.ecommerce.sales.domain.reservation.Reservation.ReservationStatus;

import java.util.Date;
import java.util.List;

public class ReservationBuilder {
    private Id id = Id.generate();
    private ReservationStatus status = ReservationStatus.OPENED;
    private Id clientDataID = Id.generate();
    private String clientName = "Kowalski";
    private int orderYear = 2000;
    private int orderMonth = 1;
    private int orderDay = 1;

    public ReservationBuilder() {}

    public ReservationBuilder setID(Id id) {
        this.id = id;
        return this;
    }

    public ReservationBuilder setStatus(ReservationStatus status) {
        this.status = status;
        return this;
    }

    public ReservationBuilder setClientDataID(Id clientDataID) {
        this.clientDataID = clientDataID;
        return this;
    }

    public ReservationBuilder setClientName(String clientName) {
        this.clientName = clientName;
        return this;
    }

    public ReservationBuilder setOrderYear(int orderYear) {
        this.orderYear = orderYear;
        return this;
    }

    public ReservationBuilder setOrderMonth(int orderMonth) {
        this.orderMonth = orderMonth;
        return this;
    }

    public ReservationBuilder setOrderDay(int orderDay) {
        this.orderDay = orderDay;
        return this;
    }

    public Reservation build() {
        return new Reservation(id, status, new ClientData(clientDataID, clientName), new Date(orderYear, orderMonth, orderDay));
    }
}
