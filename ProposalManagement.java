package ProjetoCrossJoin;

import ProjetoCrossJoin.Models.Lead;
import ProjetoCrossJoin.Models.Product;
import ProjetoCrossJoin.Models.Proposal;

import java.util.List;
import java.util.Scanner;

import static ProjetoCrossJoin.Main.ruleManager;

public class ProposalManagement {

    private final List<Proposal> proposals;

    public ProposalManagement(List<Proposal> proposals) {
        this.proposals = proposals;
    }

    public void convertLead(Scanner scanner, List<Lead> leads) {
        for (int i = 0; i < leads.size(); i++) {
            System.out.println(i + ": " + leads.get(i));
        }
        System.out.print("Choose lead index: ");
        int index = Integer.parseInt(scanner.nextLine());

        Proposal p = new Proposal(leads.get(index));

        if (!ruleManager.validate(p)) {
            System.out.println("Validation failed. Proposal not added.");
            return;
        }

        proposals.add(p);
        System.out.println("Proposal created.");
    }

    public void addProductToProposal(Scanner scanner, List<Proposal> proposals, List<Product> products) {
        for (int i = 0; i < proposals.size(); i++) {
            System.out.println(i + ": " + proposals.get(i));
        }
        System.out.print("Choose proposal index: ");
        int propIndex = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < products.size(); i++) {
            System.out.println(i + ": " + products.get(i));
        }
        System.out.print("Choose product index: ");
        int prodIndex = Integer.parseInt(scanner.nextLine());

        proposals.get(propIndex).addProduct(products.get(prodIndex));
        System.out.println("Product added.");
    }

    public void finalizeProposal(Scanner scanner) {
        viewProposals();
        System.out.print("Choose index to finalize: ");
        int index = Integer.parseInt(scanner.nextLine());
        proposals.get(index).finalizeProposal();
        System.out.println("Proposal finalized.");
    }

    public void viewProposals() {
        for (int i = 0; i < proposals.size(); i++) {
            System.out.println(i + ": " + proposals.get(i));
        }
    }

    public void editProposal(Scanner scanner, List<Product> products) {
        viewProposals();
        System.out.print("Choose index to edit: ");
        int index = Integer.parseInt(scanner.nextLine());
        if (index < 0 || index >= proposals.size()) return;
        Proposal proposal = proposals.get(index);

        boolean editing = true;
        while (editing) {
            try {
                System.out.println("\nEditing Proposal: " + proposal);
                System.out.println("1. Set Production Cost (current: " + proposal.getProductionCost() + ")");
                System.out.println("2. Set Monthly Produced Products (current: " + proposal.getMonthlyProducedProducts() + ")");
                System.out.println("3. Set Expected Monthly Profit (current: " + proposal.getExpectedMonthlyProfit() + ")");
                System.out.println("4. Add Product to Proposal");
                System.out.println("0. Save and exit");
                System.out.print("Choose field to edit: ");
                String choice = scanner.nextLine();

                switch (choice) {
                    case "1" -> {
                        System.out.print("New Production Cost: ");
                        proposal.setProductionCost(Double.parseDouble(scanner.nextLine()));
                    }
                    case "2" -> {
                        System.out.print("New Monthly Produced Products: ");
                        proposal.setMonthlyProducedProducts(Integer.parseInt(scanner.nextLine()));
                    }
                    case "3" -> {
                        System.out.print("New Expected Monthly Profit: ");
                        proposal.setExpectedMonthlyProfit(Double.parseDouble(scanner.nextLine()));
                    }
                    case "4" -> {
                        for (int i = 0; i < products.size(); i++) {
                            System.out.println(i + ": " + products.get(i));
                        }
                        System.out.print("Choose product index to add: ");
                        int productIndex = Integer.parseInt(scanner.nextLine());
                        proposal.addProduct(products.get(productIndex));
                        System.out.println("Product added.");
                    }
                    case "0" -> editing = false;
                    default -> System.out.println("Invalid option.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            } catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
            }
        }
        System.out.println("Proposal updated.");
    }
}
