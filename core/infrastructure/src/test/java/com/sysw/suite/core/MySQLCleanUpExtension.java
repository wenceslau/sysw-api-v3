package com.sysw.suite.core;

import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.springframework.data.repository.CrudRepository;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collection;

public class MySQLCleanUpExtension implements BeforeEachCallback {

    /**
     * This class is used to clean up the database before each test.
     * The interface BeforeEachCallback is used to execute the method beforeEach before each test.
     * When the method beforeEach is executed, it will get all the repositories and delete all the data.
     * I can use this class because I am using the annotation @DataJpaTest in my test class.
     */
    @Override
    public void beforeEach(ExtensionContext context) throws Exception {
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