package com.sysw.suite.core.domain.business.module;



import com.sysw.suite.core.pagination.Pagination;

import java.util.Optional;

public interface ModuleGateway {

    Module create(Module aCategory);

    void deleteById(ModuleID anId);

    Optional<Module> findById(ModuleID anId);

    Module update(Module aCategory);

    Pagination<Module> findAll(SearchQuery aQuery);

}
