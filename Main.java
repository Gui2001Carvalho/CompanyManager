package ProjetoCrossJoin;

import ProjetoCrossJoin.Models.Company;
import ProjetoCrossJoin.Models.Lead;
import ProjetoCrossJoin.Models.Product;
import ProjetoCrossJoin.Models.Proposal;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    static Scanner scanner = new Scanner(System.in);
    static List<Company> companies = new ArrayList<>();
    static List<Lead> leads = new ArrayList<>();
    static List<Proposal> proposals = new ArrayList<>();
    static List<Product> products = new ArrayList<>();

    static CompanyManagement Companymanagement = new CompanyManagement(companies);
    static LeadManagement leadManagement = new LeadManagement(leads);
    static ProposalManagement proposalManagement = new ProposalManagement(proposals);
    static ProductManagement productManagement = new ProductManagement(products);
    static DynamicRuleManager ruleManager = new DynamicRuleManager();

    public static void main(String[] args) {
        while (true) {
            try {
                System.out.println("\n--- MENU ---\n");
                System.out.println("1. Add Company");
                System.out.println("2. Edit Company");
                System.out.println("3. View Companies");
                System.out.println("4. Add Lead");
                System.out.println("5. Edit Lead");
                System.out.println("6. View Leads");
                System.out.println("7. Convert Lead to Proposal");
                System.out.println("8. Edit Proposal");
                System.out.println("9. Add Product");
                System.out.println("10. iew Product");
                System.out.println("11. Edit Product");
                System.out.println("12. Add Product to Proposal");
                System.out.println("13. Finalize Proposal");
                System.out.println("14. View Proposals");
                System.out.println("15. Add Validation Rule");
                System.out.println("16. View Rules");
                System.out.println("0. Exit");
                System.out.print("Choose an option: ");

                int option = Integer.parseInt(scanner.nextLine());
                switch (option) {
                    case 1 -> Companymanagement.addCompany(scanner);
                    case 2 -> Companymanagement.editCompany(scanner);
                    case 3 -> Companymanagement.viewCompanies();
                    case 4 -> leadManagement.addLead(scanner, companies);
                    case 5 -> leadManagement.editLead(scanner, companies);
                    case 6 -> leadManagement.viewLeads();
                    case 7 -> proposalManagement.convertLead(scanner, leads);
                    case 8 -> proposalManagement.editProposal(scanner, products);
                    case 9 -> productManagement.addProduct(scanner, products);
                    case 10 -> productManagement.viewProducts();
                    case 11 -> productManagement.editProduct(scanner);
                    case 12 -> proposalManagement.addProductToProposal(scanner, proposals, products);
                    case 13 -> proposalManagement.finalizeProposal(scanner);
                    case 14 -> proposalManagement.viewProposals();
                    case 15 -> ruleManager.addRuleInteractively(scanner);
                    case 16 -> ruleManager.viewRules();
                    case 0 -> {
                        System.out.println("Exiting...");
                        return;
                    }
                    default -> System.out.println("Invalid option.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }  catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
            }
        }
    }
}


