package com.sysw.suite.core.infrastructure;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;
import java.util.Collection;

@Target(ElementType.TYPE) //
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME) //
@Inherited // This annotation is used to inherit the annotation from the parent class.
@ActiveProfiles("test") //
@ComponentScan(includeFilters = {
        @ComponentScan.Filter(type = FilterType.REGEX, pattern = ".*[MySQLGateway]") // The pattern is used to scan the package where the JPA repositories are located.
}) // This annotation is used to scan the package where the JPA repositories are located. Also, it is used to exclude the other repositories.
@DataJpaTest // This annotation is used to test JPA repositories, it will load only the JPA configuration.
@ExtendWith(MySQLGatewayTest.CleanUpExtension.class) // This annotation is used to extend the test class with the CleanUpExtension class.
public @interface MySQLGatewayTest {


    /**
     * This class is used to clean up the database before each test.
     * The interface BeforeEachCallback is used to execute the method beforeEach before each test.
     * When the method beforeEach is executed, it will get all the repositories and delete all the data.
     * I can use this class because I am using the annotation @DataJpaTest in my test class.
     */
    class CleanUpExtension implements BeforeEachCallback {

        @Override
        public void beforeEach(ExtensionContext context)  {
            final var repositories = SpringExtension
                    .getApplicationContext(context)
                    .getBeansOfType(CrudRepository.class)
                    .values();

            cleanUp(repositories);
        }

        private void cleanUp(final Collection<CrudRepository> repositories) {
            repositories.forEach(CrudRepository::deleteAll);
        }
    }
}
