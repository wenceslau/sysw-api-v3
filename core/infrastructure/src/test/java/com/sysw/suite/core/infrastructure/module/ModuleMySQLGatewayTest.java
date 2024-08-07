package com.sysw.suite.core.infrastructure.module;

import com.sysw.suite.core.pagination.Operator;
import com.sysw.suite.core.domain.business.module.Module;
import com.sysw.suite.core.domain.business.module.ModuleID;
import com.sysw.suite.core.domain.business.module.SearchQuery;
import com.sysw.suite.core.MySQLGatewayTest;
import com.sysw.suite.core.infrastructure.module.persistence.ModuleJPA;
import com.sysw.suite.core.infrastructure.module.persistence.ModuleRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static com.sysw.suite.core.pagination.Direction.ASC;
import static com.sysw.suite.core.pagination.Operator.LIKE;
import static org.junit.jupiter.api.Assertions.*;

@MySQLGatewayTest
class ModuleMySQLGatewayTest {

    @Autowired
    private ModuleMySQLGateway moduleMySQLGateway;

    @Autowired
    private ModuleRepository moduleRepository;

    @Test
    public void testInjectionDependencies() {
        assertNotNull(moduleMySQLGateway);
        assertNotNull(moduleRepository);
    }

    @Test
    public void givenAValidModule_whenCalledCreate_shouldReturnANewModule() {
        final var module = Module.newModule("Name 1", "Display Name 1", "License 1",true);

        Assertions.assertEquals(0, moduleRepository.count());

        final var result = moduleMySQLGateway.create(module);

        Assertions.assertEquals(1, moduleRepository.count());

        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(module.getName(), result.getName());
        assertEquals(module.getDisplayName(), result.getDisplayName());
        assertEquals(module.getLicense(), result.getLicense());
        assertEquals(module.isActive(), result.isActive());
        assertNotNull(result.getCreatedAt());
        assertNotNull(result.getUpdatedAt());

        var createdModule = moduleRepository.findById(result.getId().getValue()).orElseThrow();

        assertNotNull(createdModule);
        assertNotNull(createdModule.getId());
        assertEquals(module.getName(), createdModule.getName());
        assertEquals(module.getDisplayName(), createdModule.getDisplayName());
        assertEquals(module.getLicense(), createdModule.getLicense());
        assertEquals(module.isActive(), createdModule.isActive());
        assertEquals(module.getCreatedAt(), createdModule.getCreatedAt());
        assertEquals(module.getUpdatedAt(), createdModule.getUpdatedAt());
    }

    @Test
    void givenAValidModule_whenCalledUpdate_shouldReturnModuleUpdated(){
        //Given
        var module = Module.newModule("Name 1", "Display Name 1", "License 1",true);

        //When
        Assertions.assertEquals(0, moduleRepository.count());
        var createdModuleEntity = moduleRepository.save(ModuleJPA.from(module));
        Assertions.assertEquals(1, moduleRepository.count());

        //Then
        assertNotNull(createdModuleEntity);
        assertNotNull(createdModuleEntity.getId());
        assertEquals(module.getName(), createdModuleEntity.getName());
        assertEquals(module.getDisplayName(), createdModuleEntity.getDisplayName());
        assertEquals(module.getLicense(), createdModuleEntity.getLicense());
        assertEquals(module.isActive(), createdModuleEntity.isActive());
        assertNotNull(createdModuleEntity.getCreatedAt());
        assertNotNull(createdModuleEntity.getUpdatedAt());

        //When
        var createdModuleEntityFound = moduleRepository.findById(createdModuleEntity.getId()).orElseThrow();

        //Then
        assertEquals(module.getId().getValue(), createdModuleEntityFound.getId());
        assertEquals(module.getName(), createdModuleEntityFound.getName());
        assertEquals(module.getDisplayName(), createdModuleEntityFound.getDisplayName());
        assertEquals(module.getLicense(), createdModuleEntityFound.getLicense());
        assertEquals(module.isActive(), createdModuleEntityFound.isActive());
        assertEquals(module.getCreatedAt(), createdModuleEntityFound.getCreatedAt());
        assertEquals(module.getUpdatedAt(), createdModuleEntityFound.getUpdatedAt());

        //given
        var nameUpdated = "Name 2";
        var displayNameUpdated = "Display Name 2";
        var licenseUpdated = "License 2";
        var activeUpdated = false;

        //When
        var moduleUpdated = module.clone().update(nameUpdated, displayNameUpdated, licenseUpdated, activeUpdated);
        var updatedModuleEntity = moduleMySQLGateway.update(moduleUpdated);

        //Then
        assertNotNull(updatedModuleEntity);
        assertNotNull(updatedModuleEntity.getId());
        assertEquals(moduleUpdated.getName(), updatedModuleEntity.getName());
        assertEquals(moduleUpdated.getDisplayName(), updatedModuleEntity.getDisplayName());
        assertEquals(moduleUpdated.getLicense(), updatedModuleEntity.getLicense());
        assertEquals(moduleUpdated.isActive(), updatedModuleEntity.isActive());
        assertNotNull(updatedModuleEntity.getCreatedAt());
        assertNotNull(updatedModuleEntity.getUpdatedAt());

        //When
        var updatedModuleEntityFound = moduleRepository.findById(updatedModuleEntity.getId().getValue()).orElseThrow();

        //Then
        assertEquals(moduleUpdated.getId().getValue(), updatedModuleEntityFound.getId());
        assertEquals(moduleUpdated.getName(), updatedModuleEntityFound.getName());
        assertEquals(moduleUpdated.getDisplayName(), updatedModuleEntityFound.getDisplayName());
        assertEquals(moduleUpdated.getLicense(), updatedModuleEntityFound.getLicense());
        assertEquals(moduleUpdated.isActive(), updatedModuleEntityFound.isActive());
        assertEquals(moduleUpdated.getCreatedAt(), updatedModuleEntityFound.getCreatedAt());
        assertEquals(moduleUpdated.getUpdatedAt(), updatedModuleEntityFound.getUpdatedAt());


    }

    @Test
    void givenAValidModule_whenCallsDeleteById_shouldReturnModuleDeleted(){
        //Given
        var module = Module.newModule("Name 1", "Display Name 1", "License 1",true);

        //When/then
        Assertions.assertEquals(0, moduleRepository.count());
        var createdModuleEntity = moduleRepository.save(ModuleJPA.from(module));
        Assertions.assertEquals(1, moduleRepository.count());
        moduleMySQLGateway.deleteById(ModuleID.from(createdModuleEntity.getId()));
        Assertions.assertEquals(0, moduleRepository.count());

    }

    @Test
    void givenAInvalidModule_whenCallsDeleteById_shouldReturnModuleDeleted(){
        //Given
        var module = Module.newModule("Name 1", "Display Name 1", "License 1",true);

        //When/then
        Assertions.assertEquals(0, moduleRepository.count());
        moduleRepository.save(ModuleJPA.from(module));
        Assertions.assertEquals(1, moduleRepository.count());
        moduleMySQLGateway.deleteById(ModuleID.from("123"));
        Assertions.assertEquals(1, moduleRepository.count());

    }

    @Test
    void givenAValidModule_whenCallsFindById_shouldReturnModule(){
       //Given
        var module = Module.newModule("Name 1", "Display Name 1", "License 1",true);

        //When
        Assertions.assertEquals(0, moduleRepository.count());
        var createdModuleEntity = moduleRepository.save(ModuleJPA.from(module));
        Assertions.assertEquals(1, moduleRepository.count());
        var moduleFound = moduleMySQLGateway.findById(ModuleID.from(createdModuleEntity.getId())).orElseThrow();
        Assertions.assertEquals(1, moduleRepository.count());

        //Then
        assertNotNull(moduleFound);
        assertNotNull(moduleFound.getId());
        assertEquals(module.getName(), moduleFound.getName());
        assertEquals(module.getDisplayName(), moduleFound.getDisplayName());
        assertEquals(module.getLicense(), moduleFound.getLicense());
        assertEquals(module.isActive(), moduleFound.isActive());
        assertNotNull(moduleFound.getCreatedAt());
        assertNotNull(moduleFound.getUpdatedAt());
    }

    @Test
    void givenAInvalidModule_whenCallsFindById_shouldReturnEmpty(){
        //Given
        var module = Module.newModule("Name 1", "Display Name 1", "License 1",true);

        //When/then
        Assertions.assertEquals(0, moduleRepository.count());
        moduleRepository.save(ModuleJPA.from(module));
        Assertions.assertEquals(1, moduleRepository.count());
        var moduleFound = moduleMySQLGateway.findById(ModuleID.from("123"));
        Assertions.assertEquals(1, moduleRepository.count());
        Assertions.assertTrue(moduleFound.isEmpty());

    }

    @Test
    void givenPrePersistedModules_whenCallsFindAll_shouldReturnPaginated(){
        //Given
        final var expectedPage = 0;
        final var expectedPerPage = 1;
        final var expectedTotal = 3;

        var module1 = Module.newModule("Name 1", "Display Name 1", "License 1",true);
        var module2 = Module.newModule("Name 2", "Display Name 2", "License 2",true);
        var module3 = Module.newModule("Name 3", "Display Name 3", "License 3",true);

        //When
        Assertions.assertEquals(0, moduleRepository.count());
        var createdModuleEntity1 = moduleRepository.save(ModuleJPA.from(module1));
        moduleRepository.save(ModuleJPA.from(module2));
        moduleRepository.save(ModuleJPA.from(module3));
        Assertions.assertEquals(3, moduleRepository.count());

        var query = SearchQuery.with(0, 1, "name", ASC);
        var modulesFound = moduleMySQLGateway.findAll(query);

        assertEquals(expectedPage, modulesFound.currentPage());
        assertEquals(expectedPerPage, modulesFound.perPage());
        assertEquals(expectedTotal, modulesFound.total());
        assertEquals(1, modulesFound.items().size());
        assertEquals(createdModuleEntity1.getId(), modulesFound.items().get(0).getId().getValue());


    }

    @Test
    void givenEmptyModulesTable_whenCallsFindAll_shouldReturnEmptyPage(){
        //Given
        final var expectedPage = 0;
        final var expectedPerPage = 1;
        final var expectedTotal = 0;

        assertEquals(0, moduleRepository.count());

        var query = SearchQuery.with(0, 1, "name", ASC);
        var modulesFound = moduleMySQLGateway.findAll(query);

        assertEquals(expectedPage, modulesFound.currentPage());
        assertEquals(expectedPerPage, modulesFound.perPage());
        assertEquals(expectedTotal, modulesFound.total());
        assertEquals(0, modulesFound.items().size());
    }

    @Test
    void givenFollowPagination_whenCallsFindAllWhitPage1_shouldReturnPaginated(){
        //Given
        final var expectedPerPage = 1;
        final var expectedTotal = 3;

        var module1 = Module.newModule("Name 1", "Display Name 1", "License 1",true);
        var module2 = Module.newModule("Name 2", "Display Name 2", "License 2",true);
        var module3 = Module.newModule("Name 3", "Display Name 3", "License 3",true);

        //When Page 1
        Assertions.assertEquals(0, moduleRepository.count());
        moduleRepository.save(ModuleJPA.from(module1));
        var createdModuleEntity2 = moduleRepository.save(ModuleJPA.from(module2));
        var createdModuleEntity3 = moduleRepository.save(ModuleJPA.from(module3));
        Assertions.assertEquals(3, moduleRepository.count());
        var query = SearchQuery.with(1, 1,"name", ASC);
        var modulesFound = moduleMySQLGateway.findAll(query);

        //Then
        assertEquals(1, modulesFound.currentPage());
        assertEquals(expectedPerPage, modulesFound.perPage());
        assertEquals(expectedTotal, modulesFound.total());
        assertEquals(1, modulesFound.items().size());
        assertEquals(createdModuleEntity2.getId(), modulesFound.items().get(0).getId().getValue());

        //When Page 2
        query = SearchQuery.with(2, 1, "name", ASC);
        modulesFound = moduleMySQLGateway.findAll(query);

        //Then
        assertEquals(2, modulesFound.currentPage());
        assertEquals(expectedPerPage, modulesFound.perPage());
        assertEquals(expectedTotal, modulesFound.total());
        assertEquals(1, modulesFound.items().size());
        assertEquals(createdModuleEntity3.getId(), modulesFound.items().get(0).getId().getValue());


    }

    @Test
    void givenPrePersistedModulesAndDocAsTerms_whenCallsFindAllAndTermsMatchModuleName_shouldReturnPaginated(){
        //Given
        final var expectedPage = 0;
        final var expectedPerPage = 1;
        final var expectedTotal = 1;

        var module1 = Module.newModule("Name 1", "Display Name 1", "License 1",true);
        var module2 = Module.newModule("Name 2", "Display Name 2", "License 2",true);
        var module3 = Module.newModule("Name 3", "Display Name 3", "License 3",true);

        //When
        Assertions.assertEquals(0, moduleRepository.count());
        var createdModuleEntity1 = moduleRepository.save(ModuleJPA.from(module1));
        moduleRepository.save(ModuleJPA.from(module2));
        moduleRepository.save(ModuleJPA.from(module3));
        Assertions.assertEquals(3, moduleRepository.count());

        var query = SearchQuery.with(0, 1, "name", ASC, "name", LIKE, "Name 1");
        var modulesFound = moduleMySQLGateway.findAll(query);

        //Then
        assertEquals(expectedPage, modulesFound.currentPage());
        assertEquals(expectedPerPage, modulesFound.perPage());
        assertEquals(expectedTotal, modulesFound.total());
        assertEquals(1, modulesFound.items().size());
        assertEquals(createdModuleEntity1.getId(), modulesFound.items().get(0).getId().getValue());


    }

    @Test
    void givenPrePersistedModuleAndDisplayName2AsTerms_whenCallsFindAllAndTermsMatchModuleDisplayName_shouldReturnPaginated(){
        //Given
        final var expectedPage = 0;
        final var expectedPerPage = 1;
        final var expectedTotal = 1;

        var module1 = Module.newModule("Name 1", "Display Name 1", "License 1",true);
        var module2 = Module.newModule("Name 2", "Display Name 2", "License 2",true);
        var module3 = Module.newModule("Name 3", "Display Name 3", "License 3",true);

        //When
        Assertions.assertEquals(0, moduleRepository.count());
        var createdModuleEntity1 = moduleRepository.save(ModuleJPA.from(module1));
        var createdModuleEntity2 = moduleRepository.save(ModuleJPA.from(module2));
        var createdModuleEntity3 = moduleRepository.save(ModuleJPA.from(module3));
        Assertions.assertEquals(3, moduleRepository.count());

        var query = SearchQuery.with(0, 1, "name" , ASC,"displayName" ,LIKE, "DISPLAY NAME 2");
        var modulesFound = moduleMySQLGateway.findAll(query);

        //Then
        assertEquals(expectedPage, modulesFound.currentPage());
        assertEquals(expectedPerPage, modulesFound.perPage());
        assertEquals(expectedTotal, modulesFound.total());
        assertEquals(1, modulesFound.items().size());
        assertEquals(createdModuleEntity2.getId(), modulesFound.items().get(0).getId().getValue());

    }

    @Test
    void givenPrePersistedModuleAndDisplayName2AndName1AsTerms_whenCallsFindAllAndTermsMatchModuleDisplayNameAndName_shouldReturnPaginated(){
        //Given
        final var expectedPage = 0;
        final var expectedPerPage = 1;
        final var expectedTotal = 1;

        var module1 = Module.newModule("Name 1", "Display Name 1", "License 1",true);
        var module2 = Module.newModule("Name 2", "Display Name 3", "License 2",true);
        var module3 = Module.newModule("Name 3", "Display Name 3", "License 3",true);

        //When
        Assertions.assertEquals(0, moduleRepository.count());
        var createdModuleEntity1 = moduleRepository.save(ModuleJPA.from(module1));
        var createdModuleEntity2 = moduleRepository.save(ModuleJPA.from(module2));
        var createdModuleEntity3 = moduleRepository.save(ModuleJPA.from(module3));
        Assertions.assertEquals(3, moduleRepository.count());

        String[] fields = {"displayName", "name"};
        Operator[] operators = {Operator.LIKE, Operator.LIKE};
        String[] terms = {"DISPLAY NAME 1", "Name 1"};
        var query = SearchQuery.with(0, 1, "name", ASC, fields, operators, terms);
        var modulesFound = moduleMySQLGateway.findAll(query);

        //Then
        assertEquals(expectedPage, modulesFound.currentPage());
        assertEquals(expectedPerPage, modulesFound.perPage());
        assertEquals(expectedTotal, modulesFound.total());
        assertEquals(expectedTotal, modulesFound.items().size());
        assertEquals(createdModuleEntity1.getId(), modulesFound.items().get(0).getId().getValue());

    }

    @Test
    void givenPrePersistedModuleListInAsTerms_whenCallsFindAllAndTermsMatchModuleListIn_shouldReturnPaginated(){
        //Given
        final var expectedPage = 0;
        final var expectedPerPage = 2;
        final var expectedTotal = 2;

        var module1 = Module.newModule("Name 1", "Display Name 1", "License 1",true);
        var module2 = Module.newModule("Name 2", "Display Name 3", "License 2",true);
        var module3 = Module.newModule("Name 3", "Display Name 3", "License 3",true);
        var module4 = Module.newModule("Name 4", "Display Name 4", "License 4",true);

        //When
        Assertions.assertEquals(0, moduleRepository.count());
        var createdModuleEntity1 = moduleRepository.save(ModuleJPA.from(module1));
        var createdModuleEntity2 = moduleRepository.save(ModuleJPA.from(module2));
        var createdModuleEntity3 = moduleRepository.save(ModuleJPA.from(module3));
        var createdModuleEntity4 = moduleRepository.save(ModuleJPA.from(module4));
        Assertions.assertEquals(4, moduleRepository.count());

        String[] fields = {"name"};
        Operator[] operators = {Operator.IN};
        Object[] terms = {List.of("Name 1", "Name 2")};
        var query = SearchQuery.with(0, 2, "name", ASC, fields, operators, terms);
        var modulesFound = moduleMySQLGateway.findAll(query);

        //Then
        assertEquals(expectedPage, modulesFound.currentPage());
        assertEquals(expectedPerPage, modulesFound.perPage());
        assertEquals(expectedTotal, modulesFound.total());
        assertEquals(expectedTotal, modulesFound.items().size());
        assertEquals(createdModuleEntity1.getId(), modulesFound.items().get(0).getId().getValue());

    }

    @Test
    void givenPrePersistedModuleCreatedAtGreaterThanAsTerms_whenCallsFindAll_shouldReturnPaginated(){
        //Given
        final var expectedPage = 0;
        final var expectedPerPage = 3;
        final var expectedTotal = 3;

        var module1 = Module.newModule("Name 1", "Display Name 1", "License 1",true);
        var module2 = Module.newModule("Name 2", "Display Name 3", "License 2",true);
        var module3 = Module.newModule("Name 3", "Display Name 3", "License 3",true);

        //When
        Assertions.assertEquals(0, moduleRepository.count());
        var createdModuleEntity1 = moduleRepository.save(ModuleJPA.from(module1));
        var createdModuleEntity2 = moduleRepository.save(ModuleJPA.from(module2));
        var createdModuleEntity3 = moduleRepository.save(ModuleJPA.from(module3));
        Assertions.assertEquals(3, moduleRepository.count());

        String[] fields = {"createdAt"};
        Operator[] operators = {Operator.GREATER_THAN};
        Object[] terms = {LocalDateTime.now().plusMinutes(-1)};
        var query = SearchQuery.with(0, 3, "name", ASC, fields, operators, terms);
        var modulesFound = moduleMySQLGateway.findAll(query);

        //Then
        assertEquals(expectedPage, modulesFound.currentPage());
        assertEquals(expectedPerPage, modulesFound.perPage());
        assertEquals(expectedTotal, modulesFound.total());
        assertEquals(expectedTotal, modulesFound.items().size());
        assertEquals(createdModuleEntity1.getId(), modulesFound.items().get(0).getId().getValue());

    }

    @Test
    void givenPrePersistedModuleCreatedAtLessThanAsTerms_whenCallsFindAll_shouldReturnEmpty(){
        //Given
        final var expectedPage = 0;
        final var expectedPerPage = 3;
        final var expectedTotal = 0;

        var module1 = Module.newModule("Name 1", "Display Name 1", "License 1",true);
        var module2 = Module.newModule("Name 2", "Display Name 3", "License 2",true);
        var module3 = Module.newModule("Name 3", "Display Name 3", "License 3",true);

        //When
        Assertions.assertEquals(0, moduleRepository.count());
        var createdModuleEntity1 = moduleRepository.save(ModuleJPA.from(module1));
        var createdModuleEntity2 = moduleRepository.save(ModuleJPA.from(module2));
        var createdModuleEntity3 = moduleRepository.save(ModuleJPA.from(module3));
        Assertions.assertEquals(3, moduleRepository.count());

        String[] fields = {"createdAt"};
        Operator[] operators = {Operator.LESS_THAN};
        Object[] terms = {LocalDateTime.now().plusMinutes(-1)};
        var query = SearchQuery.with(0, 3, "name", ASC, fields, operators, terms);
        var modulesFound = moduleMySQLGateway.findAll(query);

        //Then
        assertEquals(expectedPage, modulesFound.currentPage());
        assertEquals(expectedPerPage, modulesFound.perPage());
        assertEquals(expectedTotal, modulesFound.total());
        assertEquals(expectedTotal, modulesFound.items().size());

    }


}