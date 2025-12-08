package cbo.core.adrs.utils;

import cbo.core.adrs.models.ApplicationCategory;
import cbo.core.adrs.repositories.ApplicationCategoryRepo;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class DataSeeder implements CommandLineRunner {

    private final ApplicationCategoryRepo categoryRepository;

    public DataSeeder(ApplicationCategoryRepo categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        seedApplicationCategories();
    }

    private void seedApplicationCategories() {
        List<String> categories = Arrays.asList(
                "Core Banking System",
                "Internet Banking",
                "Mobile Banking",
                "Payment Gateway",
                "Card Management System",
                "ATM Monitoring",
                "Loan Management System",
                "Trade Finance",
                "Treasury System",
                "AML / Compliance",
                "KYC / Identity Verification",
                "HR Management System",
                "ERP System",
                "Report Management",
                "Document Management System",
                "CRM System",
                "Reporting & BI",
                "API Gateway / ESB",
                "CI/CD Tools",
                "Version Control System",
                "Internal Development",
                "Financial System",
                "Open Source Software",
                "Licensed"
        );

        for (String title : categories) {
            if (!categoryRepository.existsByTitle(title)) {
                categoryRepository.save(new ApplicationCategory(null, title));
            }
        }
        System.out.println("✅ Application categories seeded!");
    }
}
