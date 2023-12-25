package com.sysw.suite.core.infrastructure.module.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public record ModuleGetResponse(
        @JsonProperty("id") String id,
        @JsonProperty("name") String name,
        @JsonProperty("display_name") String displayName,
        @JsonProperty("license") String license,
        @JsonProperty("active") Boolean active,
        @JsonProperty("created_at") Instant createdAt,
        @JsonProperty("updated_at") Instant updatedAt
        ) {
}
