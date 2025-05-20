package ProjetoCrossJoin.Models;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Proposal {

    private UUID proposalId;
    private Lead lead;
    private List<Product> products;
    private double productionCost;
    private int monthlyProducedProducts;
    private double expectedMonthlyProfit;
    private Status status;

    public Proposal(Lead lead) {
        if (lead == null){
            throw new IllegalArgumentException("Lead cannot be null");
        }
        this.proposalId = UUID.randomUUID();
        this.lead = lead;
        this.products = new ArrayList<>();
        this.status = Status.DRAFT;

    }

    public void addProduct(Product product) {
        if (product == null) {
            return;
        }
        Product dependency = product.getDependentProduct();
        if (dependency != null && !products.contains(dependency)) {
            throw new IllegalArgumentException("Dependent product has not been added before.");
        }

        if (dependency != null && !dependency.getProductType().equals(product.getProductType())) {
            throw new IllegalArgumentException("Dependent products must have the same type.");
        }

        products.add(product);
    }

    public void finalizeProposal() {
        this.status = Status.ACTIVE;
        lead.setStatus(Status.ACTIVE);
        lead.getCompany().setStatus(Status.ACTIVE);
    }

    public UUID getProposalId() {
        return proposalId;
    }

    public Lead getLead() {
        return lead;
    }

    public double getProductionCost() {
        return productionCost;
    }

    public void setProductionCost(double productionCost) {
        this.productionCost = productionCost;
    }

    public int getMonthlyProducedProducts() {
        return monthlyProducedProducts;
    }

    public void setMonthlyProducedProducts(int monthlyProducedProducts) {
        this.monthlyProducedProducts = monthlyProducedProducts;
    }

    public double getExpectedMonthlyProfit() {
        return expectedMonthlyProfit;
    }

    public void setExpectedMonthlyProfit(double expectedMonthlyProfit) {
        this.expectedMonthlyProfit = expectedMonthlyProfit;
    }

    public Status getStatus() {
        return status;
    }

    public List<Product> getProducts() {
        return products;
    }

    @Override
    public String toString() {
        return "Proposal{" +
                "proposalId=" + proposalId +
                ", lead=" + lead +
                ", products=" + products +
                ", productionCost=" + productionCost +
                ", monthlyProducedProducts=" + monthlyProducedProducts +
                ", expectedMonthlyProfit=" + expectedMonthlyProfit +
                ", status=" + status +
                '}';
    }
}
