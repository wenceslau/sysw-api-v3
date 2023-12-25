package com.sysw.suite.core.infrastructure.configuration.json;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.afterburner.AfterburnerModule;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

import java.util.concurrent.Callable;


public enum Json {
    INSTANCE;

    public static ObjectMapper mapper() {
        return INSTANCE.mapper.copy();
    }

    public static String writeValueAsString(Object value) {
        return invoke(() -> mapper().writeValueAsString(value));
    }

    public static <T> T readValue(String json, Class<T> clazz) {
        return invoke(() -> mapper().readValue(json, clazz));
    }

    private final ObjectMapper mapper = new Jackson2ObjectMapperBuilder()
            .dateFormat(new StdDateFormat()) //format ISO 8601 "yyyy-MM-dd'T'HH:mm:ss.SSSX"
            .featuresToDisable(
                    DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,
                    DeserializationFeature.FAIL_ON_NULL_FOR_PRIMITIVES,
                    DeserializationFeature.FAIL_ON_NULL_CREATOR_PROPERTIES,
                    SerializationFeature.WRITE_DATES_AS_TIMESTAMPS
            )
            .modules(
                    new JavaTimeModule(),
                    new Jdk8Module(),
                    afterburnerModule()
            )
            .propertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE)
            .build();

    // Make afterburner generate bytecode only for public getters/setters and fields
    // without this, Java 9+ complains about illegal reflective access
    private AfterburnerModule afterburnerModule() {
        var module = new AfterburnerModule();
        module.setUseValueClassLoader(false);
        return module;
    }

    private static <T> T invoke(Callable<T> callable) {
        try {
            return callable.call();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
