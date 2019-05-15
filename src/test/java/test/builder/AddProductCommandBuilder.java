package test.builder;

import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.Id;
import pl.com.bottega.ecommerce.sales.application.api.command.AddProductCommand;

public class AddProductCommandBuilder {

    private Id orderId = Id.generate();
    private Id productId = Id.generate();
    private int quantity = 1;

    public AddProductCommandBuilder() {}

    public AddProductCommandBuilder setOrderId(Id orderId) {
        this.orderId = orderId;
        return this;
    }

    public AddProductCommandBuilder setProductId(Id productId) {
        this.productId = productId;
        return this;
    }

    public AddProductCommandBuilder setQuantity(int quantity) {
        this.quantity = quantity;
        return this;
    }

    public AddProductCommand build() {
        return new AddProductCommand(orderId, productId, quantity);
    }
}
