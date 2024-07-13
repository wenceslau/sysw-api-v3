package com.sysw.suite.core.infrastructure.api.controller;

import com.sysw.suite.core.application.module.create.CreateModuleInput;
import com.sysw.suite.core.application.module.create.CreateModuleOutput;
import com.sysw.suite.core.application.module.create.CreateModuleUseCase;
import com.sysw.suite.core.application.module.retrieve.get.GetModuleByIDUseCase;
import com.sysw.suite.core.application.module.retrieve.ModuleOutput;
import com.sysw.suite.core.application.module.retrieve.list.ListModuleUseCase;
import com.sysw.suite.core.application.module.update.UpdateModuleInput;
import com.sysw.suite.core.application.module.update.UpdateModuleOutput;
import com.sysw.suite.core.application.module.update.UpdateModuleUseCase;
import com.sysw.suite.core.pagination.Direction;
import com.sysw.suite.core.domain.business.module.SearchQuery;
import com.sysw.suite.core.pagination.Pagination;
import com.sysw.suite.core.infrastructure.api.ModuleAPI;
import com.sysw.suite.core.infrastructure.module.models.CreateModuleRequest;
import com.sysw.suite.core.infrastructure.module.models.ModuleResponse;
import com.sysw.suite.core.infrastructure.module.models.UpdateModuleRequest;
import com.sysw.suite.core.infrastructure.module.presenters.ModuleApiPresenters;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class ModuleController implements ModuleAPI {

    private final CreateModuleUseCase createModuleUseCase;

    private final GetModuleByIDUseCase getModuleByIDUseCase;

    private final UpdateModuleUseCase updateModuleUseCase;

    private final ListModuleUseCase listModuleUseCase;

    public ModuleController(CreateModuleUseCase createModuleUseCase, GetModuleByIDUseCase getModuleByIDUseCase, UpdateModuleUseCase updateModuleUseCase, ListModuleUseCase listModuleUseCase) {
        this.createModuleUseCase = createModuleUseCase;
        this.getModuleByIDUseCase = getModuleByIDUseCase;
        this.updateModuleUseCase = updateModuleUseCase;
        this.listModuleUseCase = listModuleUseCase;
    }

    @Override
    public ResponseEntity<?> createModule(CreateModuleRequest request) {

        System.out.println("Name: "+request);

        CreateModuleInput command = new CreateModuleInput(
                request.name(),
                request.displayName(),
                request.license(),
                request.active() != null && request.active()
        );

        CreateModuleOutput output = createModuleUseCase.execute(command);

        return ResponseEntity
                .created(URI.create("/modules/" + output.id()))
                .body(output);
    }

    @Override
    public Pagination<ModuleResponse> listCategories(String search, Integer page, Integer perPage,
                                                     String sort, String direction) {

        SearchQuery query = SearchQuery.with(page, perPage, sort,Direction.from(direction));

        return listModuleUseCase.execute(query)
                .map(ModuleApiPresenters::present);

    }


    @Override
    public ModuleResponse getModule(String id) {
        ModuleOutput execute = getModuleByIDUseCase.execute(id);
        return ModuleApiPresenters.present(execute);
    }

    @Override
    public ResponseEntity<?> updateModule(String id, UpdateModuleRequest request) {

        UpdateModuleInput input = new UpdateModuleInput(
                id,
                request.name(),
                request.displayName(),
                request.license(),
                request.active() != null && request.active()
        );

        UpdateModuleOutput output = updateModuleUseCase.execute(input);

        return ResponseEntity
                .noContent()
                .location(URI.create("/modules/" + output.id()))
                .build();

    }
}
