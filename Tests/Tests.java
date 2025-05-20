package ProjetoCrossJoin.Tests;

import ProjetoCrossJoin.*;
import ProjetoCrossJoin.Models.*;
import org.junit.Test;

import static org.junit.Assert.*;

public class Tests {

    //Company
    @Test
    public void companyShouldBeDraftWhenCreated() {
        Company c = new Company("123456789", "Rua X", "Portugal", "Ana", "ana@email.com");
        assertEquals(Status.DRAFT, c.getStatus());
    }

    @Test
    public void companyCanBeActivated() {
        Company c = new Company("123456789", "Rua X", "Portugal", "Ana", "ana@email.com");
        c.setStatus(Status.ACTIVE);
        assertEquals(Status.ACTIVE, c.getStatus());
    }

    //LeadTests

    @Test
    public void leadShouldInheritCountryFromCompany() {
        Company c = new Company("999999999", "Av. Central", "Brazil", "Carlos", "carlos@email.com");
        Lead lead = new Lead(c, BusinessType.INDUSTRY);

        assertEquals("Brazil", lead.getCountry());
        assertEquals(c, lead.getCompany());
    }

    @Test
    public void testLeadStatusDefaultDraft() {
        Company company = new Company("123", "Avenida", "Portugal", "Pedro", "pedro@email.com");
        Lead lead = new Lead(company, BusinessType.OTHER);
        assertEquals(Status.DRAFT, lead.getStatus());
    }

    //ProductTests

    @Test
    public void testProductWithoutDependencyIsValid() {
        Product p = new Product(Product.ProductType.ELECTRONICS);
        assertNull(p.getDependentProduct());
        assertEquals(Product.ProductType.ELECTRONICS, p.getProductType());
    }

    @Test
    public void testProductWithDependencySameType() {
        Product base = new Product(Product.ProductType.FURNITURE);
        Product dependent = new Product(Product.ProductType.FURNITURE, base);

        assertEquals(base, dependent.getDependentProduct());
        assertEquals(Product.ProductType.FURNITURE, dependent.getProductType());
    }

    @Test
    public void testProductWithNullDependency() {
        Product p = new Product(Product.ProductType.OTHER);
        assertNull(p.getDependentProduct());
    }

    //ProposalTests

    @Test
    public void testProposalInheritsLeadFields() {
        Company company = new Company("123456789", "Rua A", "Portugal", "João", "joao@email.com");
        Lead lead = new Lead(company, BusinessType.INDUSTRY);
        Proposal proposal = new Proposal(lead);

        assertEquals(lead, proposal.getLead());
        assertEquals(Status.DRAFT, proposal.getStatus());
    }

    @Test
    public void testAddValidProductWithoutDependency() {
        Company company = new Company("123", "Rua X", "Portugal", "Ana", "ana@email.com");
        Lead lead = new Lead(company, BusinessType.RETAIL);
        Proposal proposal = new Proposal(lead);
        Product product = new Product(Product.ProductType.ELECTRONICS);

        proposal.addProduct(product);

        assertEquals(1, proposal.getProducts().size());
        assertTrue(proposal.getProducts().contains(product));
    }

    @Test
    public void testAddProductWithMissingDependencyThrowsException() {
        Company company = new Company("123", "Rua X", "Portugal", "Ana", "ana@email.com");
        Lead lead = new Lead(company, BusinessType.RETAIL);
        Proposal proposal = new Proposal(lead);
        Product base = new Product(Product.ProductType.ELECTRONICS);
        Product dependent = new Product(Product.ProductType.ELECTRONICS, base);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            proposal.addProduct(dependent);
        });

        assertTrue(exception.getMessage().contains("Dependent product has not been added"));
    }

    @Test
    public void testAddProductWithDifferentTypeDependencyThrowsException() {
        Company company = new Company("123", "Rua X", "Portugal", "Ana", "ana@email.com");
        Lead lead = new Lead(company, BusinessType.RETAIL);
        Proposal proposal = new Proposal(lead);

        Product base = new Product(Product.ProductType.FURNITURE);
        Product dependent = new Product(Product.ProductType.ELECTRONICS, base);

        proposal.addProduct(base);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            proposal.addProduct(dependent);
        });

        assertTrue(exception.getMessage().contains("Dependent products must have the same type"));
    }

    @Test
    public void testFinalizeProposalActivatesEntities() {
        Company company = new Company("123", "Rua X", "Portugal", "Ana", "ana@email.com");
        Lead lead = new Lead(company, BusinessType.RETAIL);
        Proposal proposal = new Proposal(lead);

        proposal.finalizeProposal();

        assertEquals(Status.ACTIVE, proposal.getStatus());
        assertEquals(Status.ACTIVE, lead.getStatus());
        assertEquals(Status.ACTIVE, company.getStatus());
    }

    //DynamicRuleTest
    @Test
    public void testCompanyValidationWithRequiredNifPortugal() {
        DynamicRuleManager ruleManager = new DynamicRuleManager();
        ruleManager.setRequired("Company", "nif",
                c -> ((Company) c).getCountry().equalsIgnoreCase("Portugal"),
                true, "country == Portugal");

        Company invalid = new Company("", "Rua X", "Portugal", "Ana", "ana@email.com");
        Company valid = new Company("123456789", "Rua X", "Portugal", "Ana", "ana@email.com");

        assertFalse(ruleManager.validate(invalid));
        assertTrue(ruleManager.validate(valid));
    }

    @Test
    public void testCompanyValidationWithDifferentCountry() {
        DynamicRuleManager ruleManager = new DynamicRuleManager();
        ruleManager.setRequired("Company", "nif",
                c -> ((Company) c).getCountry().equalsIgnoreCase("Portugal"),
                true, "country == Portugal");

        Company foreign = new Company("", "Rua Y", "Spain", "Luis", "luis@email.com");
        assertTrue(ruleManager.validate(foreign));
    }
}
