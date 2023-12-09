package com.sysw.suite.audit.domain.useraction;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

class UserActionTest {


    @Test
    void givenValidValues_whenCallCreateUserAction_thenShouldReturnUserAction(){
        //Given
        final var expectedService = "Application A";
        final var expectedAction = Action.INSERT;
        final var expectedObjectId = "123";
        final var expectedObjectName = "Module";
        final var expectedObjectValue= "{id: '123', name: 'Module DRE'}";
        final var expectedUsername = "NETOWE";
        final var expectedActionAt = Instant.now();

        //When
        var expectedUserAction = UserAction.create(expectedService, expectedAction, expectedObjectId, expectedObjectName,
                expectedObjectValue, expectedUsername, expectedActionAt);

        //Then
        String actualService = expectedUserAction.getService();
        Action actualAction = expectedUserAction.getAction();
        String actualObjectId = expectedUserAction.getObjectId();
        String actualObjectName = expectedUserAction.getObjectName();
        String actualObjectValue = expectedUserAction.getObjectValue();
        String actualUsername = expectedUserAction.getUsername();
        Instant actualActionAt = expectedUserAction.getActionAt();

        assertEquals(expectedService, actualService);
        assertEquals(expectedAction, actualAction);
        assertEquals(expectedObjectId, actualObjectId);
        assertEquals(expectedObjectName, actualObjectName);
        assertEquals(expectedObjectValue, actualObjectValue);
        assertEquals(expectedUsername, actualUsername);
        assertEquals(expectedActionAt, actualActionAt);
        assertNotNull(expectedUserAction.getCreatedAt());

    }

}