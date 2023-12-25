package com.sysw.suite.core.infrastructure.module.models;

import com.sysw.suite.core.JacksonTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.json.JacksonTester;

import static org.assertj.core.api.Assertions.assertThat;

@JacksonTest
class CreateModuleRequestTest {

    @Autowired
    private JacksonTester<CreateModuleRequest> json;

    @Test
    public void testMarshal() throws Exception {
        final var expectedName = "name";
        final var expectedDisplayName = "description";
        final var expectedLicense = "license";
        final var expectedStatus = false;

        final var categoryGetResponse = new CreateModuleRequest(
                expectedName,
                expectedDisplayName,
                expectedLicense,
                expectedStatus
        );

        final var actualJson = json.write(categoryGetResponse);

        System.out.println(actualJson);

        assertThat(actualJson)
                .hasJsonPathValue("$.name", expectedName)
                .hasJsonPathValue("$.display_name", expectedDisplayName)
                .hasJsonPathValue("$.license", expectedLicense)
                .hasJsonPathValue("$.active", expectedStatus);
    }

    @Test
    public void testUnmarshal() throws Exception {
        final var expectedName = "name";
        final var expectedDisplayName = "description";
        final var expectedLicense = "license";
        final var expectedStatus = false;

        final var json = """
                {
                    "name": "%s",
                    "display_name": "%s",
                    "license": "%s",
                    "active": %s
                }
                """.formatted(
                expectedName,
                expectedDisplayName,
                expectedLicense,
                expectedStatus
        );

        final var object = this.json.parse(json);

        assertThat(object)
                .hasFieldOrPropertyWithValue("name", expectedName)
                .hasFieldOrPropertyWithValue("displayName", expectedDisplayName)
                .hasFieldOrPropertyWithValue("license", expectedLicense)
                .hasFieldOrPropertyWithValue("active", expectedStatus);
    }

}