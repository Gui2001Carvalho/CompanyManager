package ProjetoCrossJoin;

import ProjetoCrossJoin.Models.BusinessType;
import ProjetoCrossJoin.Models.Company;
import ProjetoCrossJoin.Models.Lead;

import java.util.List;
import java.util.Scanner;

import static ProjetoCrossJoin.Main.ruleManager;

public class LeadManagement {

    private final List<Lead> leads;

    public LeadManagement(List<Lead> leads) {
        this.leads = leads;
    }

    public void addLead(Scanner scanner, List<Company> companies) {
        for (int i = 0; i < companies.size(); i++) {
            System.out.println(i + ": " + companies.get(i));
        }
        System.out.print("Select company index: ");
        int index = Integer.parseInt(scanner.nextLine());
        System.out.print("Business Type (INDUSTRY/RETAIL/OTHER): ");
        BusinessType type = BusinessType.valueOf(scanner.nextLine().toUpperCase());

        Lead l = new Lead(companies.get(index), type);

        if (!ruleManager.validate(l)) {
            System.out.println("Validation failed. Lead not added.");
            return;
        }

        leads.add(l);
        System.out.println("Lead added.");
    }

    public void editLead(Scanner scanner, List<Company> companies) {
        viewLeads();
        System.out.print("Choose index to edit: ");
        int index = Integer.parseInt(scanner.nextLine());
        if (index < 0 || index >= leads.size()) return;
        Lead lead = leads.get(index);

        boolean editing = true;
        while (editing) {
            try {
                System.out.println("\nEditing Lead: " + lead);
                System.out.println("1. Edit Business Type (current: " + lead.getBusinessType() + ")");
                System.out.println("2. Edit Company (current: " + lead.getCompany() + ")");
                System.out.println("3. Edit Country (current: " + lead.getCountry() + ")");
                System.out.println("0. Save and exit");
                System.out.print("Choose field to edit: ");
                String choice = scanner.nextLine();

                switch (choice) {
                    case "1" -> {
                        System.out.print("New Business Type (INDUSTRY/RETAIL/OTHER): ");
                        lead.setBusinessType(BusinessType.valueOf(scanner.nextLine().toUpperCase()));
                    }
                    case "2" -> {
                        for (int i = 0; i < companies.size(); i++) {
                            System.out.println(i + ": " + companies.get(i));
                        }
                        System.out.print("Select new company index: ");
                        int companyIndex = Integer.parseInt(scanner.nextLine());
                        lead.setCompany(companies.get(companyIndex));
                        lead.setCountry(companies.get(companyIndex).getCountry());
                    }
                    case "3" -> {
                        System.out.print("New Country: ");
                        lead.setCountry(scanner.nextLine());
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
        System.out.println("Lead updated.");
    }

    public void viewLeads() {
        for (int i = 0; i < leads.size(); i++) {
            System.out.println(i + ": " + leads.get(i));
        }
    }
}
