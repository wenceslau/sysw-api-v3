package com.sysw.suite.core;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.test.context.ActiveProfiles;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

/**
 * This class is used to test the JPA repositories.
 * The interface BeforeEachCallback is used to execute the method beforeEach before each test.
 * When the method beforeEach is executed, it will get all the repositories and delete all the data.
 * I can use this class because I am using the annotation @DataJpaTest in my test class.
 */
@ComponentScan(includeFilters = {
        @ComponentScan.Filter(type = FilterType.REGEX, pattern = ".[MySQLGateway]")    // The pattern is used to scan the package where the JPA repositories are located.
})                                                                                      // This annotation is used to scan the package where the JPA repositories are located. Also, it is used to exclude the other repositories.
@Target(ElementType.TYPE)
@Retention(java.lang.annotation.RetentionPolicy.RUNTIME)
@Inherited
@ActiveProfiles("test")
@DataJpaTest                                                                            // This annotation is used to test JPA repositories, it will load only the JPA configuration.
@ExtendWith(CleanUpExtension.class)                                                     // This annotation is used to execute the method beforeEach before each test.
public @interface MySQLGatewayTest {
}
