package com.sysw.suite.core.infrastructure.configuration.usecases;

import com.sysw.suite.core.application.module.create.CreateModuleUseCase;
import com.sysw.suite.core.application.module.retrieve.get.GetModuleByIDUseCase;
import com.sysw.suite.core.application.module.retrieve.list.ListModuleUseCase;
import com.sysw.suite.core.application.module.update.UpdateModuleUseCase;
import com.sysw.suite.core.domain.business.module.ModuleGateway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModuleUseCaseConfig {

    private final ModuleGateway gateway;

    public ModuleUseCaseConfig(ModuleGateway gateway) {
        this.gateway = gateway;
    }

    @Bean
    public CreateModuleUseCase createModuleUseCase() {
        return new CreateModuleUseCase(gateway);
    }

    @Bean
    public UpdateModuleUseCase updateModuleUseCase() {
        return new UpdateModuleUseCase(gateway);
    }

    @Bean
    public GetModuleByIDUseCase getModuleByIdUseCase() {
        return new GetModuleByIDUseCase(gateway);
    }

    @Bean
    public ListModuleUseCase listCategoriesUseCase() {
        return new ListModuleUseCase(gateway);
    }

}
