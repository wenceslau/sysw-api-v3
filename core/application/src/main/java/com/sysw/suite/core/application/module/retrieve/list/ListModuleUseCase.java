package com.sysw.suite.core.application.module.retrieve.list;

import com.sysw.suite.core.application.UseCase;
import com.sysw.suite.core.application.module.retrieve.get.ModuleOutput;
import com.sysw.suite.core.domain.exception.DomainException;
import com.sysw.suite.core.domain.module.Module;
import com.sysw.suite.core.domain.module.ModuleGateway;
import com.sysw.suite.core.domain.module.ModuleID;
import com.sysw.suite.core.domain.module.ModuleSearchQuery;
import com.sysw.suite.core.domain.pagination.Pagination;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public class ListModuleUseCase extends UseCase<ModuleSearchQuery, Pagination<ModuleListOutput>> {

    private final ModuleGateway moduleGateway;

    public ListModuleUseCase(final ModuleGateway moduleGateway) {
        this.moduleGateway = Objects.requireNonNull(moduleGateway);
    }

    @Override
    public Pagination<ModuleListOutput> execute(ModuleSearchQuery searchQuery) {
        Pagination<Module> all = moduleGateway.findAll(searchQuery);
        return all.map(module -> ModuleListOutput.from(module));
    }

    private Supplier<? extends RuntimeException> notFound(ModuleID aModuleID) {
        return () -> DomainException.with(new Error("Module with ID %s not found".formatted(aModuleID.getValue())));
    }


}
