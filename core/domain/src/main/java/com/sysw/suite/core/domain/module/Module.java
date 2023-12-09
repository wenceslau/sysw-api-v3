package com.sysw.suite.core.domain.module;

import com.sysw.suite.core.domain.AggregateRoot;
import com.sysw.suite.core.domain.validation.ValidationHandler;

import java.time.Instant;

public class Module extends AggregateRoot<ModuleID> implements Cloneable {

    private String name;
    private String displayName;
    private String license;
    private boolean active;
    private Instant createdAt;
    private Instant updatedAt;

    private Module(ModuleID anId,
                   String aName,
                   String aDisplayName,
                   String aLicense,
                   boolean isActive,
                   Instant aCreatedAt,
                   Instant aUpdatedAt) {
        super(anId);
        this.name = aName;
        this.displayName = aDisplayName;
        this.license = aLicense;
        this.active = isActive;
        this.createdAt = aCreatedAt;
        this.updatedAt = aUpdatedAt;
    }

    public static Module create(String aName,
                                String aDisplayName,
                                String aLicense,
                                boolean isActive) {
        final var id = ModuleID.unique();
        return create(id, aName, aDisplayName, aLicense, isActive);
    }

    public static Module create(ModuleID anId,
                                String aName,
                                String aDisplayName,
                                String aLicense,
                                boolean isActive) {
        final var now = Instant.now();
        return new Module(anId, aName, aDisplayName, aLicense, isActive, now, now);
    }


    @Override
    public void validate(ValidationHandler validationHandler) {
        new ModuleValidator(validationHandler, this).validate();
    }

    public String getName() {
        return name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getLicense() {
        return license;
    }

    public boolean isActive() {
        return active;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }


    public Module inactive() {
        active = false;
        updatedAt = Instant.now();
        return this;
    }

    public Module active() {
        active = true;
        updatedAt = Instant.now();
        return this;
    }

    public Module update(String name, String displayName, String license, boolean active) {
        this.name = name;
        this.displayName = displayName;
        this.license = license;
        if (active){
            active();
        }else{
            inactive();
        }
        return this;
    }

    @Override
    public Module clone() {
        try {
            Module clone = (Module) super.clone();
            // TODO: copy mutable state here, so the clone can't change the internals of the original
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }


}
