package com.sysw.suite.core.domain.business.businessunit;

import com.sysw.suite.core.AggregateRoot;
import com.sysw.suite.core.domain.business.module.ModuleID;
import com.sysw.suite.core.domain.business.exception.DomainException;
import com.sysw.suite.core.domain.business.validation.ValidationHandler;
import com.sysw.suite.core.domain.business.validation.handler.NotificationValidationHandler;
import com.sysw.suite.core.domain.business.validation.handler.ThrowsValidationHandler;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BusinessUnit extends AggregateRoot<BusinessUnitID> {

    private String name;
    private String tradeName;
    private InternalCode internalCode;
    private List<ModuleID> modules;
    private boolean active;
    private Instant createdAt;
    private Instant updatedAt;

    private BusinessUnit(BusinessUnitID businessUnitID, String name, String tradeName, InternalCode internalCode,
                         List<ModuleID> modules, boolean active, Instant createdAt, Instant updatedAt) {
        super(businessUnitID);
        this.name = name;
        this.tradeName = tradeName;
        this.internalCode = internalCode;
        this.modules = modules;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        selfValidate();
    }

    public static BusinessUnit newBusinessUnit(String name, String tradeName, InternalCode internalCode, boolean active) {
        var id = BusinessUnitID.unique();
        var createdAt = Instant.now();
        var updatedAt = createdAt;
        return new BusinessUnit(id, name, tradeName, internalCode, new ArrayList<>(), active, createdAt, updatedAt);
    }

    public static BusinessUnit whit(BusinessUnitID id, String name, String tradeName, InternalCode internalCode,
                                    List<ModuleID> modules, boolean active, Instant createdAt, Instant updatedAt) {
        return new BusinessUnit(id, name, tradeName, internalCode, modules, active, createdAt, updatedAt);
    }

    public static BusinessUnit from(BusinessUnit businessUnit){
        return new BusinessUnit(businessUnit.getId(), businessUnit.name, businessUnit.tradeName, businessUnit.internalCode,
                new ArrayList<>(businessUnit.modules), businessUnit.active, businessUnit.createdAt, businessUnit.updatedAt);
    }

    public BusinessUnit update(String name, String tradeName, InternalCode internalCode) {
        this.name = name;
        this.tradeName = tradeName;
        this.internalCode = internalCode;
        updatedAt = Instant.now();
        selfValidate();
        return this;
    }

    public BusinessUnit deactivate() {
        active = false;
        updatedAt = Instant.now();
        return this;
    }

    public BusinessUnit activate() {
        active = true;
        updatedAt = Instant.now();
        return this;
    }

    public String name() {
        return name;
    }

    public String tradeName() {
        return tradeName;
    }

    public InternalCode internalCode() {
        return internalCode;
    }

    public List<ModuleID> modules() {
        return Collections.unmodifiableList(modules);
    }

    public boolean active() {
        return active;
    }

    public Instant createdAt() {
        return createdAt;
    }

    public Instant updatedAt() {
        return updatedAt;
    }

    public BusinessUnit addCategory(final ModuleID aModuleID) {
        if (aModuleID == null) {
            return this;
        }
        this.modules.add(aModuleID);
        this.updatedAt = Instant.now();
        return this;
    }

    public BusinessUnit removeCategory(final ModuleID aModuleID) {
        if (aModuleID == null) {
            return this;
        }
        this.modules.remove(aModuleID);
        this.updatedAt = Instant.now();
        return this;
    }

    public BusinessUnit addCategories(final List<ModuleID> modules) {
        if (modules == null || modules.isEmpty()) {
            return this;
        }
        this.modules.addAll(modules);
        this.updatedAt = Instant.now();
        return this;
    }

    private void selfValidate() {
        final var notification = NotificationValidationHandler.create();
        validate(notification);

        if (notification.hasError()) {
            throw DomainException.with(notification.getErrors());
        }
    }

    @Override
    public void validate(ValidationHandler validationHandler) {
        new BusinessUnitValidator(validationHandler, this).validate();
    }

}
