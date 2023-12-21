package com.sysw.suite.core.infrastructure.module.persistence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ModuleRepository extends JpaRepository<ModuleJpaEntity, String> {

    Page<ModuleJpaEntity> findAll(Specification<ModuleJpaEntity> whereClause, Pageable page);
}
