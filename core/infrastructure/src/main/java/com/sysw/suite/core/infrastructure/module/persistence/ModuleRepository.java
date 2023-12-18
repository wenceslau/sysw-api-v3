package com.sysw.suite.core.infrastructure.module.persistence;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ModuleRepository extends JpaRepository<ModuleJpaEntity, String> {

}
