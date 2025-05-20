package ProjetoCrossJoin.Models;

import java.util.UUID;

public class Product {

    public enum ProductType {
        ELECTRONICS,
        FURNITURE,
        OTHER,
    }

    private UUID productId;
    private ProductType productType;
    private Product dependentProduct;

    public Product(ProductType productType) {
        this(productType, null);
    }

    public Product(ProductType productType, Product dependentProduct) {
        this.productId = UUID.randomUUID();
        this.productType = productType;
        this.dependentProduct = dependentProduct;
    }

    public UUID getProductId() {
        return productId;
    }

    public ProductType getProductType() {
        return productType;
    }

    public Product getDependentProduct() {
        return dependentProduct;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public void setDependentProduct(Product dependentProduct) {
        this.dependentProduct = dependentProduct;
    }



    @Override
    public String toString() {
        return "Product{" +
                "productId=" + productId +
                ", productType=" + productType +
                ", dependentProduct=" + dependentProduct +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;
        return getProductId().equals(product.getProductId());
    }

    @Override
    public int hashCode() {
        return getProductId().hashCode();
    }
}
