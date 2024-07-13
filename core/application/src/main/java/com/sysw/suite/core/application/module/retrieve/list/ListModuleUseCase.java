package com.sysw.suite.core.application.module.retrieve.list;

import com.sysw.suite.core.application.UseCase;
import com.sysw.suite.core.application.module.retrieve.ModuleOutput;
import com.sysw.suite.core.domain.exception.DomainException;
import com.sysw.suite.core.domain.business.module.Module;
import com.sysw.suite.core.domain.business.module.ModuleGateway;
import com.sysw.suite.core.domain.business.module.ModuleID;
import com.sysw.suite.core.domain.business.module.SearchQuery;
import com.sysw.suite.core.domain.pagination.Pagination;

import java.util.Objects;
import java.util.function.Supplier;

public class ListModuleUseCase extends UseCase<SearchQuery, Pagination<ModuleOutput>> {

    private final ModuleGateway moduleGateway;

    public ListModuleUseCase(final ModuleGateway moduleGateway) {
        this.moduleGateway = Objects.requireNonNull(moduleGateway);
    }

    @Override
    public Pagination<ModuleOutput> execute(SearchQuery searchQuery) {
        Pagination<Module> all = moduleGateway.findAll(searchQuery);
        return all.map(module -> ModuleOutput.from(module));
    }

    private Supplier<? extends RuntimeException> notFound(ModuleID aModuleID) {
        return () -> DomainException.with(new Error("Module with ID %s not found".formatted(aModuleID.getValue())));
    }


}
