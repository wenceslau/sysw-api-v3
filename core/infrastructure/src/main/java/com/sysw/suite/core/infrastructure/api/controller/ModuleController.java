package com.sysw.suite.core.infrastructure.api.controller;

import com.sysw.suite.core.application.module.create.CreateModuleInput;
import com.sysw.suite.core.application.module.create.CreateModuleOutput;
import com.sysw.suite.core.application.module.create.CreateModuleUseCase;
import com.sysw.suite.core.application.module.retrieve.get.GetModuleUseCase;
import com.sysw.suite.core.application.module.retrieve.get.ModuleOutput;
import com.sysw.suite.core.application.module.update.UpdateModuleInput;
import com.sysw.suite.core.application.module.update.UpdateModuleOutput;
import com.sysw.suite.core.application.module.update.UpdateModuleUseCase;
import com.sysw.suite.core.domain.pagination.Pagination;
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

    private final GetModuleUseCase getModuleUseCase;

    private final UpdateModuleUseCase updateModuleUseCase;

    public ModuleController(CreateModuleUseCase createModuleUseCase, GetModuleUseCase getModuleUseCase, UpdateModuleUseCase updateModuleUseCase) {
        this.createModuleUseCase = createModuleUseCase;
        this.getModuleUseCase = getModuleUseCase;
        this.updateModuleUseCase = updateModuleUseCase;
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
    public Pagination<?> listCategories(String search, Integer page, Integer perPage, String sort, String direction) {
        return null;
    }


    @Override
    public ModuleResponse getModule(String id) {
        ModuleOutput execute = getModuleUseCase.execute(id);
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
