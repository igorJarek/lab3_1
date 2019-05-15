package test.builder;

import pl.com.bottega.ecommerce.canonicalmodel.publishedlanguage.Id;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.Product;
import pl.com.bottega.ecommerce.sales.domain.productscatalog.ProductType;
import pl.com.bottega.ecommerce.sharedkernel.Money;

public class ProductBuilder {

    private Id id = Id.generate();
    private double price = 10.0;
    private String currency = "USD";
    private String name = "Produkt";
    private ProductType productType = ProductType.STANDARD;

    public ProductBuilder() {}

    public ProductBuilder setId(Id id) {
        this.id = id;
        return this;
    }

    public ProductBuilder setPrice(double price) {
        this.price = price;
        return this;
    }

    public ProductBuilder setCurrency(String currency) {
        this.currency = currency;
        return this;
    }

    public ProductBuilder setName(String name) {
        this.name = name;
        return this;
    }

    public ProductBuilder setProductType(ProductType productType) {
        this.productType = productType;
        return this;
    }

    public Product build() {
        return new Product(id, new Money(price, currency), name, productType);
    }

}
