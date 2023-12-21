package com.sysw.suite.core.infrastructure.module;

import com.sysw.suite.core.domain.module.Module;
import com.sysw.suite.core.domain.module.ModuleGateway;
import com.sysw.suite.core.domain.module.ModuleID;
import com.sysw.suite.core.domain.module.ModuleSearchQuery;
import com.sysw.suite.core.domain.pagination.Pagination;
import com.sysw.suite.core.infrastructure.module.persistence.ModuleJpaEntity;
import com.sysw.suite.core.infrastructure.module.persistence.ModuleRepository;
import com.sysw.suite.core.infrastructure.utils.SpecificationUtils;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
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
        return save(aCategory);
    }

    @Override
    public void deleteById(ModuleID anId) {
        String anIdValue = anId.getValue();
        if (repository.existsById(anIdValue)) {
            repository.deleteById(anIdValue);
        }
    }

    @Override
    public Optional<Module> findById(ModuleID anId) {
        // If not exist, the map is not executed, and the Optional is empty
        return repository.findById(anId.getValue())
                .map(ModuleJpaEntity::toAggregate);
    }

    @Override
    public Module update(Module aModule) {
        return save(aModule);
    }

    @Override
    public Pagination<Module> findAll(ModuleSearchQuery aQuery) {

        //Pagination
        PageRequest pageRequest = PageRequest.of(
                aQuery.page(),
                aQuery.perPage(),
                Sort.by(Sort.Direction.fromString(aQuery.direction()), aQuery.sort())
        );

        //Dynamic query by terms name and displayName
        var specification = Optional.ofNullable(aQuery.terms())
                .filter(terms -> !terms.isBlank())
                .map(terms -> {
                            Specification<ModuleJpaEntity> nameLike = SpecificationUtils.like("name", terms);
                            Specification<ModuleJpaEntity> displayNameLike = SpecificationUtils.like("displayName", terms);
                            return nameLike.or(displayNameLike);
                        }
                )
                .orElse(null);

        //Execute the query
        var pageResult = this.repository.findAll(Specification.where(specification), pageRequest);

        return new Pagination<>(
                pageResult.getNumber(),
                pageResult.getSize(),
                pageResult.getTotalElements(),
                pageResult.map(ModuleJpaEntity::toAggregate)
                        .toList()
        );
    }

    private Module save(Module aModule) {
        return repository.save(ModuleJpaEntity.from(aModule))
                .toAggregate();
    }

}





