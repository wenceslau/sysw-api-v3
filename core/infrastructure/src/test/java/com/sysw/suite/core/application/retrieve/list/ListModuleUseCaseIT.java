package com.sysw.suite.core.application.retrieve.list;

import com.sysw.suite.core.IntegrationTest;
import com.sysw.suite.core.application.module.retrieve.list.ListModuleUseCase;
import com.sysw.suite.core.domain.enums.Direction;
import com.sysw.suite.core.domain.enums.Operator;
import com.sysw.suite.core.domain.module.Module;
import com.sysw.suite.core.domain.module.ModuleSearchQuery;
import com.sysw.suite.core.infrastructure.module.persistence.ModuleJpaEntity;
import com.sysw.suite.core.infrastructure.module.persistence.ModuleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Arrays;
import java.util.stream.Stream;

import static com.sysw.suite.core.domain.enums.Direction.ASC;
import static com.sysw.suite.core.domain.enums.Operator.*;

@IntegrationTest
public class ListModuleUseCaseIT {

    @Autowired
    private ListModuleUseCase useCase;

    @Autowired
    private ModuleRepository moduleRepository;

    @BeforeEach
    void mockUp() {
        var categoryStream = Stream.of(
                        Module.newModule("RH", "Module RH", "xx", true),
                        Module.newModule("DRE", "Module DRE", "tt", true),
                        Module.newModule("Core", "Module Core", "WW", true),
                        Module.newModule("JOB", "Module Job", "AA", true),
                        Module.newModule("JOB2", "Module Job", "AA", true),
                        Module.newModule("JOB3", "Module Job", "AA1", true),
                        Module.newModule("JOB4", "Module Job", "AA4", true),
                        Module.newModule("JOB100", "Module Job 100", null, true),
                        Module.newModule("JOB101", "Module Job 101", null, false)

                )
                .map(ModuleJpaEntity::from)
                .toList();

        moduleRepository.saveAll(categoryStream);

    }

    @Test
    public void givenAValidTerm_whenTermDoesntMatchsPrePersisted_shouldReturnEmptyPage() {
        final var expectedPage = 0;
        final var expectedPerPage = 10;
        final var expectedTerms = "ji1j3i 1j3i1oj";
        final var expectedSort = "name";
        final var expectedDirection = ASC;
        final var expectedItemsCount = 0;
        final var expectedTotal = 0;

        final var aQuery = ModuleSearchQuery.with(expectedPage,
                expectedPerPage, expectedSort, expectedDirection, "name", LIKE, expectedTerms);

        final var actualResult = useCase.execute(aQuery);

        Assertions.assertEquals(expectedItemsCount, actualResult.items().size());
        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(expectedTotal, actualResult.total());
    }

    @ParameterizedTest
    @CsvSource({
            "Module DRE,0,10,1,1,DRE",
            "Module RH,0,10,1,1,RH",
            "Module Core,0,10,1,1,Core",
            "Module Job,0,10,4,4,JOB",
    })
    void givenAValidTerm_whenTermMatchsPrePersisted_shouldReturnPageWithOneItem(
            String expectedTerms,
            int expectedPage,
            int expectedPerPage,
            int expectedItemsCount,
            long expectedTotal,
            String expectedName
    ) {

        final var aQuery = ModuleSearchQuery.with(expectedPage,
                expectedPerPage, "name", ASC, "displayName", EQUAL, expectedTerms);

        final var actualResult = useCase.execute(aQuery);

        Assertions.assertEquals(expectedItemsCount, actualResult.items().size());
        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(expectedTotal, actualResult.total());
        Assertions.assertEquals(expectedName, actualResult.items().get(0).name());
    }

    @ParameterizedTest
    @CsvSource({
            "name,asc,0,10,9,9,Core",
            "name,desc,0,10,9,9,RH",
            "displayName,asc,0,10,9,9,Core",
            "displayName,desc,0,10,9,9,RH",
            "license,asc,0,10,9,9,JOB100",
            "license,desc,0,10,9,9,RH",
    })
    public void givenAValidSortAndDirection_whenCallsListCategories_thenShouldReturnCategoriesOrdered(
            final String expectedSort,
            final String expectedDirection,
            final int expectedPage,
            final int expectedPerPage,
            final int expectedItemsCount,
            final long expectedTotal,
            final String expectedCategoryName
    ) {
        final var expectedTerms = "";

        final var aQuery = ModuleSearchQuery.with(expectedPage, expectedPerPage, expectedSort,
                Direction.valueOf(expectedDirection.toUpperCase()), "name", LIKE, expectedTerms);

        final var actualResult = useCase.execute(aQuery);

        Assertions.assertEquals(expectedItemsCount, actualResult.items().size());
        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(expectedTotal, actualResult.total());
        Assertions.assertEquals(expectedCategoryName, actualResult.items().get(0).name());
    }

    @ParameterizedTest
    @CsvSource({
            "0,2,2,9,Core;DRE",
            "1,2,2,9,JOB;JOB100",

    })
    public void givenAValidPage_whenCalledListCategories_shouldReturnCategoriesPaginated(
            final int expectedPage,
            final int expectedPerPage,
            final int expectedItemsCount,
            final long expectedTotal,
            final String expectedName
    ) {
        final var expectedSort = "name";
        final var expectedDirection = ASC;
        final var expectedTerms = "";

        final var aQuery = ModuleSearchQuery.with(expectedPage, expectedPerPage, expectedSort,
                expectedDirection);

        final var actualResult = useCase.execute(aQuery);

        Assertions.assertEquals(expectedItemsCount, actualResult.items().size());
        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(expectedTotal, actualResult.total());

        int index = 0;
        for (final String name : expectedName.split(";")) {
            final String actualName = actualResult.items().get(index).name();
            Assertions.assertEquals(name, actualName);
            index++;
        }
    }

//    Module.newModule("RH", "Module RH", "xx", true),
//    Module.newModule("DRE", "Module DRE", "tt", true),
//    Module.newModule("Core", "Module Core", "WW", true),
//    Module.newModule("JOB", "Module Job", "AA", true)
//    Module.newModule("JOB2", "Module Job", "AA", true)
//    Module.newModule("JOB3", "Module Job", "AA1", true)
//    Module.newModule("JOB4", "Module Job", "AA4", true)

    @ParameterizedTest
    @CsvSource({
            "0,3,3,3, displayName;license , LIKE;IN      , JOB;AA:AA1         , JOB;JOB2;JOB3",
            "0,1,1,1, name;license        , EQUAL;IN     , JOB;AA             , JOB",
            "0,2,2,2, displayName;license , LIKE;IN      , JOB;AA             , JOB;JOB2",
            "0,3,3,3, displayName;license , LIKE;IN      , JOB;AA:AA1:XX      , JOB;JOB2;JOB3",
            "0,1,1,1, name;license        , EQUAL;IN     , RH;xx              , RH",
            "0,1,1,1, name;displayName    , EQUAL;IN     , Core;Module Core   , Core",
    })
    public void givenAValidTermsMatchUsingLikeOrEqualsAndIn_whenCallsListModule_shouldReturnModulesPaginated(
            final int expectedPage,
            final int expectedPerPage,
            final int expectedItemsCount,
            final long expectedTotal,
            final String expectedFields,
            final String expectedOperator,
            final String expectedTerms,
            final String expectedName
    ) {
        final var expectedSort = "name";

        String[] fields = expectedFields.trim().split(";");
        Operator[] operators = Arrays.stream(expectedOperator.trim().split(";")).map(Operator::valueOf).toArray(Operator[]::new);
        String expectedTerms1 = expectedTerms.trim().split(";")[0];
        String[] expectedTerms2 = expectedTerms.trim().split(";")[1].trim().split(":");
        Object[] terms = {expectedTerms1, Arrays.stream(expectedTerms2).toList()};

        var aQuery = ModuleSearchQuery.with(expectedPage, expectedPerPage, expectedSort, ASC, fields, operators, terms);

        final var actualResult = useCase.execute(aQuery);

        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(expectedItemsCount, actualResult.items().size());
        Assertions.assertEquals(expectedTotal, actualResult.total());

        int index = 0;
        for (final String name : expectedName.split(";")) {
            final String actualName = actualResult.items().get(index).name();
            Assertions.assertEquals(name, actualName);
            index++;
        }
    }

    @ParameterizedTest
    @CsvSource({
            "0,2,2,2, license , IS_NULL , NULL , JOB100;JOB101"
    })
    public void givenAValidTermsLicenseNull_whenCallsListModule_shouldReturnModulesPaginated(
            final int expectedPage,
            final int expectedPerPage,
            final int expectedItemsCount,
            final long expectedTotal,
            final String expectedFields,
            final String expectedOperator,
            final String expectedTerms,
            final String expectedName
    ) {
        final var expectedSort = "name";

        String fields = expectedFields.trim();
        Operator operators = Operator.valueOf(expectedOperator.trim());
        Object terms = expectedTerms.trim();

        var aQuery = ModuleSearchQuery.with(expectedPage, expectedPerPage, expectedSort, ASC, fields, operators, terms);

        final var actualResult = useCase.execute(aQuery);

        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(expectedItemsCount, actualResult.items().size());
        Assertions.assertEquals(expectedTotal, actualResult.total());

        int index = 0;
        for (final String name : expectedName.split(";")) {
            final String actualName = actualResult.items().get(index).name();
            Assertions.assertEquals(name, actualName);
            index++;
        }
    }

    @ParameterizedTest
    @CsvSource({
            "0,1,1,1, active , EQUAL , false , JOB101"
    })
    public void givenAValidTermsActiveIsFalse_whenCallsListModule_shouldReturnModulesPaginated(
            final int expectedPage,
            final int expectedPerPage,
            final int expectedItemsCount,
            final long expectedTotal,
            final String expectedFields,
            final String expectedOperator,
            final boolean expectedTerms,
            final String expectedName
    ) {
        final var expectedSort = "name";

        String fields = expectedFields.trim();
        Operator operators = Operator.valueOf(expectedOperator.trim());
        Object terms = expectedTerms;

        var aQuery = ModuleSearchQuery.with(expectedPage, expectedPerPage, expectedSort, ASC, fields, operators, terms);

        final var actualResult = useCase.execute(aQuery);

        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(expectedItemsCount, actualResult.items().size());
        Assertions.assertEquals(expectedTotal, actualResult.total());

        int index = 0;
        for (final String name : expectedName.split(";")) {
            final String actualName = actualResult.items().get(index).name();
            Assertions.assertEquals(name, actualName);
            index++;
        }
    }

    @ParameterizedTest
    @CsvSource({
            "0,10,8,8, active , EQUAL , true"
    })
    public void givenAValidTermsActiveIsTrue_whenCallsListModule_shouldReturnModulesPaginated(
            final int expectedPage,
            final int expectedPerPage,
            final int expectedItemsCount,
            final long expectedTotal,
            final String expectedFields,
            final String expectedOperator,
            final boolean expectedTerms
    ) {
        final var expectedSort = "name";

        String fields = expectedFields.trim();
        Operator operators = Operator.valueOf(expectedOperator.trim());
        Object terms = expectedTerms;

        var aQuery = ModuleSearchQuery.with(expectedPage, expectedPerPage, expectedSort, ASC, fields, operators, terms);

        final var actualResult = useCase.execute(aQuery);

        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(expectedItemsCount, actualResult.items().size());
        Assertions.assertEquals(expectedTotal, actualResult.total());

    }


}