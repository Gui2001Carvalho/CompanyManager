package ProjetoCrossJoin;

import ProjetoCrossJoin.Models.Company;

import java.util.List;
import java.util.Scanner;

import static ProjetoCrossJoin.Main.ruleManager;

public class CompanyManagement {
    private final List<Company> companies;

    public CompanyManagement(List<Company> companies) {
        this.companies = companies;
    }

    public void addCompany(Scanner scanner) {
        System.out.print("NIF: ");
        String nif = scanner.nextLine();
        System.out.print("Address: ");
        String address = scanner.nextLine();
        System.out.print("Country: ");
        String country = scanner.nextLine();
        System.out.print("Stakeholder: ");
        String stakeholder = scanner.nextLine();
        System.out.print("Contact: ");
        String contact = scanner.nextLine();

        Company c = new Company(nif, address, country, stakeholder, contact);
        if (!ruleManager.validate(c)) {
            System.out.println("Validation failed. Company not added.");
            return;
        }

        companies.add(c);
        System.out.println("Company added.");
    }

    public void editCompany(Scanner scanner) {
        viewCompanies();
        System.out.print("Choose index to edit: ");
        int index = Integer.parseInt(scanner.nextLine());
        if (index < 0 || index >= companies.size()) return;
        Company c = companies.get(index);

        boolean editing = true;
        while (editing) {
            try {
                System.out.println("\nEditing Company: " + c);
                System.out.println("1. Edit NIF (current: " + c.getNif() + ")");
                System.out.println("2. Edit Country (current: " + c.getCountry() + ")");
                System.out.println("3. Edit Address (current: " + c.getAddress() + ")");
                System.out.println("4. Edit Stakeholder (current: " + c.getStakeholder() + ")");
                System.out.println("5. Edit Contact (current: " + c.getContact() + ")");
                System.out.println("0. Save and exit");
                System.out.print("Choose field to edit: ");
                String choice = scanner.nextLine();

                switch (choice) {
                    case "1" -> {
                        System.out.print("New NIF: ");
                        c.setNif(scanner.nextLine());
                    }
                    case "2" -> {
                        System.out.print("New Country: ");
                        c.setCountry(scanner.nextLine());
                    }
                    case "3" -> {
                        System.out.print("New Address: ");
                        c.setAddress(scanner.nextLine());
                    }
                    case "4" -> {
                        System.out.print("New Stakeholder: ");
                        c.setStakeholder(scanner.nextLine());
                    }
                    case "5" -> {
                        System.out.print("New Contact: ");
                        c.setContact(scanner.nextLine());
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
        System.out.println("Company updated.");
    }

    public void viewCompanies() {
        for (int i = 0; i < companies.size(); i++) {
            System.out.println(i + ": " + companies.get(i));
        }
    }
}
