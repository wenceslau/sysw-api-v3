package com.sysw.suite.core.infrastructure.module;

import com.sysw.suite.core.domain.module.Module;
import com.sysw.suite.core.domain.module.ModuleGateway;
import com.sysw.suite.core.domain.module.ModuleID;
import com.sysw.suite.core.domain.module.ModuleSearchQuery;
import com.sysw.suite.core.domain.pagination.Pagination;
import com.sysw.suite.core.infrastructure.module.persistence.ModuleJpaEntity;
import com.sysw.suite.core.infrastructure.module.persistence.ModuleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ModuleMySQLGateway implements ModuleGateway {

    private final ModuleRepository repository;


    public ModuleMySQLGateway(ModuleRepository repository) {
        this.repository = repository;
    }

    @Override
    public Module create(Module aCategory) {
        return repository.save(ModuleJpaEntity.from(aCategory))
                .toAggregate();
    }

    @Override
    public void deleteById(ModuleID anId) {

    }

    @Override
    public Optional<Module> findById(ModuleID anId) {
        return Optional.empty();
    }

    @Override
    public Module update(Module aCategory) {
        return null;
    }

    @Override
    public Pagination<Module> findAll(ModuleSearchQuery aQuery) {
        return null;
    }
}





