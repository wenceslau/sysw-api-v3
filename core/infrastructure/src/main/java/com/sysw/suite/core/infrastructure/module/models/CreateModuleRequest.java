package com.sysw.suite.core.infrastructure.module.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CreateModuleRequest(
        @JsonProperty("name") String name,
        @JsonProperty("display_name") String displayName,
        @JsonProperty("license") String license,
        @JsonProperty("active") Boolean active
) {
}
