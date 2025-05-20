package ProjetoCrossJoin;

import ProjetoCrossJoin.Models.Product;

import java.util.List;
import java.util.Scanner;

import static ProjetoCrossJoin.Main.ruleManager;

public class ProductManagement {
    private final List<Product> products;

    public ProductManagement(List<Product> products) {
        this.products = products;
    }

    public void addProduct(Scanner scanner, List<Product> allProducts) {
        try {
            System.out.print("Product Type (ELECTRONICS/FURNITURE/OTHER): ");
            Product.ProductType type = Product.ProductType.valueOf(scanner.nextLine().toUpperCase());

            if (allProducts.isEmpty()) {
                System.out.println("No existing products.");
            } else {
                System.out.println("Existing products:");
                for (int i = 0; i < allProducts.size(); i++) {
                    System.out.println(i + ": " + allProducts.get(i));
                }
            }

            System.out.print("Dependent product index (or -1 if none): ");
            int depIndex = Integer.parseInt(scanner.nextLine());

            Product p;
            if (depIndex >= 0 && depIndex < allProducts.size()) {
                Product dependency = allProducts.get(depIndex);
                if (!dependency.getProductType().equals(type)) {
                    throw new IllegalArgumentException("Dependent product must have the same ProductType.");
                }
                p = new Product(type, dependency);
            } else {
                p = new Product(type);
            }

            if (!ruleManager.validate(p)) {
                System.out.println("Validation failed. Product not added.");
                return;
            }

            products.add(p);
            System.out.println("Product added.");
        } catch (Exception e) {
            System.out.println("Error adding product: " + e.getMessage());
        }
    }

    public void editProduct(Scanner scanner) {
        for (int i = 0; i < products.size(); i++) {
            System.out.println(i + ": " + products.get(i));
        }
        System.out.print("Choose product index to edit: ");
        int index = Integer.parseInt(scanner.nextLine());
        if (index < 0 || index >= products.size()) return;

        Product product = products.get(index);
        boolean editing = true;

        while (editing) {
            try {
                System.out.println("\nEditing Product: " + product);
                System.out.println("1. Change Product Type (current: " + product.getProductType() + ")");
                System.out.println("2. Change Dependent Product (current: " + (product.getDependentProduct() != null ? product.getDependentProduct() : "None") + ")");
                System.out.println("0. Save and exit");
                System.out.print("Choose option: ");

                String choice = scanner.nextLine();
                switch (choice) {
                    case "1" -> {
                        System.out.print("New Product Type (ELECTRONICS/FURNITURE/OTHER): ");
                        product.setProductType(Product.ProductType.valueOf(scanner.nextLine().toUpperCase()));
                    }
                    case "2" -> {
                        for (int i = 0; i < products.size(); i++) {
                            System.out.println(i + ": " + products.get(i));
                        }
                        System.out.print("New dependent product index (-1 for none): ");
                        int depIndex = Integer.parseInt(scanner.nextLine());
                        if (depIndex >= 0 && depIndex < products.size()) {
                            product.setDependentProduct(products.get(depIndex));
                        } else {
                            product.setDependentProduct(null);
                        }
                    }
                    case "0" -> editing = false;
                    default -> System.out.println("Invalid option.");
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a number.");
            }  catch (Exception e) {
                System.out.println("Unexpected error: " + e.getMessage());
            }
        }
        System.out.println("Product updated.");
    }
    public void viewProducts() {
        if (products.isEmpty()) {
            System.out.println("No products available.");
        } else {
            System.out.println("Product list:");
            for (int i = 0; i < products.size(); i++) {
                System.out.println(i + ": " + products.get(i));
            }
        }
    }
}
