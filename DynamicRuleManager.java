package ProjetoCrossJoin;

import ProjetoCrossJoin.Models.Company;
import ProjetoCrossJoin.Models.Lead;
import ProjetoCrossJoin.Models.Product;
import ProjetoCrossJoin.Models.Proposal;

import java.lang.reflect.Field;
import java.util.*;
import java.util.function.Predicate;

public class DynamicRuleManager {

    private static class Rule {
        Predicate<Object> condition;
        String fieldName;
        boolean isRequired;
        String conditionText;

        public Rule(String fieldName, Predicate<Object> condition, boolean isRequired, String conditionText) {
            this.fieldName = fieldName;
            this.condition = condition;
            this.isRequired = isRequired;
            this.conditionText = conditionText;
        }

        @Override
        public String toString() {
            return String.format("Field '%s' is required when %s.", fieldName, conditionText);
        }
    }

    private final Map<String, List<Rule>> rules = new HashMap<>();

    public void setRequired(String className, String fieldName, Predicate<Object> condition, boolean isRequired, String conditionText) {
        rules.putIfAbsent(className, new ArrayList<>());
        rules.get(className).add(new Rule(fieldName, condition, isRequired, conditionText));
    }

    public boolean validate(Object obj) {
        String className = obj.getClass().getSimpleName();
        List<Rule> classRules = rules.getOrDefault(className, Collections.emptyList());

        for (Rule rule : classRules) {
            if (rule.condition.test(obj)) {
                Object fieldValue = getFieldValue(obj, rule.fieldName);
                if (rule.isRequired && (fieldValue == null || fieldValue.toString().isBlank())) {
                    System.out.println("Missing required field: " + rule.fieldName);
                    return false;
                }
            }
        }
        return true;
    }

    private Object getFieldValue(Object obj, String fieldName) {
        try {
            Field field = obj.getClass().getDeclaredField(fieldName);
            field.setAccessible(true);
            return field.get(obj);
        } catch (Exception e) {
            System.out.println("Error accessing field: " + fieldName);
            return null;
        }
    }

    private static final Map<String, List<String>> classFields = Map.of(
            "Company", List.of("nif", "country", "address", "stakeholder", "contact"),
            "Lead", List.of("company", "country", "businessType", "status"),
            "Proposal", List.of("lead", "productionCost", "monthlyProducedProducts", "expectedMonthlyProfit", "status"),
            "Product", List.of("productType", "dependentProduct")
    );


    public void addRuleInteractively(Scanner scanner) {
        try {
            System.out.println("Enter class name (Company, Lead, Proposal, Product): ");
            String className = scanner.nextLine();

            List<String> fields = classFields.get(className);
            if (fields == null) {
                System.out.println("Invalid class name.");
                return;
            }

            System.out.println("Available fields: " + String.join(", ", fields));
            System.out.print("Enter field name to validate: ");
            String fieldName = scanner.nextLine();

            System.out.println("Enter condition (ex: country == Portugal): ");
            String condition = scanner.nextLine();

            String[] parts = condition.split("==");
            if (parts.length != 2) {
                System.out.println("Invalid condition format. Use: field == value");
                return;
            }

            String conditionField = parts[0].trim();
            String expectedValue = parts[1].trim();

            Predicate<Object> conditionPredicate = obj -> {
                try {
                    switch (className) {
                        case "Company" -> {
                            if (obj instanceof Company c) {
                                return conditionField.equals("country") &&
                                        c.getCountry().equalsIgnoreCase(expectedValue);
                            }
                        }
                        case "Lead" -> {
                            if (obj instanceof Lead l) {
                                return conditionField.equals("status") &&
                                        l.getStatus().toString().equalsIgnoreCase(expectedValue);
                            }
                        }
                        case "Proposal" -> {
                            if (obj instanceof Proposal p) {
                                return conditionField.equals("status") &&
                                        p.getStatus().toString().equalsIgnoreCase(expectedValue);
                            }
                        }
                        case "Product" -> {
                            if (obj instanceof Product pr) {
                                return conditionField.equals("productType") &&
                                        pr.getProductType().toString().equalsIgnoreCase(expectedValue);
                            }
                        }
                        default -> {
                            System.out.println("Unsupported class: " + className);
                            return false;
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Error while evaluating condition: " + e.getMessage());
                }
                return false;
            };

            setRequired(className, fieldName, conditionPredicate, true, condition);
            System.out.println("Rule added: " + fieldName + " is required when " + condition);

        } catch (Exception e) {
            System.out.println("Unexpected error while adding rule: " + e.getMessage());
        }
    }


    public void viewRules() {
        for (String className : rules.keySet()) {
            System.out.println("Rules for class: " + className);
            for (Rule rule : rules.get(className)) {
                System.out.println(" - " + rule);
            }
        }
    }
}

