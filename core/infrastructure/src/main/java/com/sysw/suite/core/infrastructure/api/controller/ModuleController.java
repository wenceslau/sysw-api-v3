package com.sysw.suite.core.infrastructure.api.controller;

import com.sysw.suite.core.application.module.create.CreateModuleInput;
import com.sysw.suite.core.application.module.create.CreateModuleOutput;
import com.sysw.suite.core.application.module.create.CreateModuleUseCase;
import com.sysw.suite.core.domain.pagination.Pagination;
import com.sysw.suite.core.infrastructure.api.ModuleAPI;
import com.sysw.suite.core.infrastructure.module.models.CreateModuleRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RestController
public class ModuleController implements ModuleAPI {

    private final CreateModuleUseCase createModuleUseCase;

    public ModuleController(CreateModuleUseCase createModuleUseCase) {
        this.createModuleUseCase = createModuleUseCase;
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
}
