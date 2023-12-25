package com.sysw.suite.core.infrastructure.module.models;

import com.sysw.suite.core.JacksonTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;


@JacksonTest
class ModuleGetResponseTest {

    @Autowired
    private JacksonTester<ModuleGetResponse> json;

    @Test
    public void testMarshal() throws Exception {
        final var expectedId = "123";
        final var expectedName = "name";
        final var expectedDisplayName = "description";
        final var expectedLicense = "license";
        final var expectedStatus = false;
        final var expectedCreatedAt = Instant.now();
        final var expectedUpdatedAt = Instant.now();

        final var categoryGetResponse = new ModuleGetResponse(
                expectedId,
                expectedName,
                expectedDisplayName,
                expectedLicense,
                expectedStatus,
                expectedCreatedAt,
                expectedUpdatedAt
        );

        final var actualJson = json.write(categoryGetResponse);

        System.out.println(actualJson);

        assertThat(actualJson).hasJsonPathValue("$.id", expectedId)
                .hasJsonPathValue("$.name", expectedName)
                .hasJsonPathValue("$.display_name", expectedDisplayName)
                .hasJsonPathValue("$.license", expectedLicense)
                .hasJsonPathValue("$.active", expectedStatus)
                .hasJsonPathValue("$.created_at", expectedCreatedAt.toString())
                .hasJsonPathValue("$.updated_at", expectedUpdatedAt.toString());
    }

    @Test
    public void testUnmarshal() throws Exception {
        final var expectedId = "123";
        final var expectedName = "name";
        final var expectedDisplayName = "description";
        final var expectedLicense = "license";
        final var expectedStatus = false;
        final var expectedCreatedAt = Instant.now();
        final var expectedUpdatedAt = Instant.now();

        final var json = """
                {
                    "id": "%s",
                    "name": "%s",
                    "display_name": "%s",
                    "license": "%s",
                    "active": %s,
                    "created_at": "%s",
                    "updated_at": "%s"
                }
                """.formatted(
                expectedId,
                expectedName,
                expectedDisplayName,
                expectedLicense,
                expectedStatus,
                expectedCreatedAt.toString(),
                expectedUpdatedAt.toString()
        );

        final var object = this.json.parse(json);

        assertThat(object).hasFieldOrPropertyWithValue("id", expectedId)
                .hasFieldOrPropertyWithValue("name", expectedName)
                .hasFieldOrPropertyWithValue("display_name", expectedDisplayName)
                .hasFieldOrPropertyWithValue("license", expectedLicense)
                .hasFieldOrPropertyWithValue("active", expectedStatus)
                .hasFieldOrPropertyWithValue("createdAt", expectedCreatedAt)
                .hasFieldOrPropertyWithValue("updatedAt", expectedUpdatedAt);
    }


}