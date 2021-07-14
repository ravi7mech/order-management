package com.apptium.order;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {
        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("com.apptium.order");

        noClasses()
            .that()
            .resideInAnyPackage("com.apptium.order.service..")
            .or()
            .resideInAnyPackage("com.apptium.order.repository..")
            .should()
            .dependOnClassesThat()
            .resideInAnyPackage("..com.apptium.order.web..")
            .because("Services and repositories should not depend on web layer")
            .check(importedClasses);
    }
}
