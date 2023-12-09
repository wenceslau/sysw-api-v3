package com.sysw.suite.audi.application.useraction.create;

import com.sysw.suite.audit.domain.useraction.Action;
import com.sysw.suite.audit.domain.useraction.UserAction;
import com.sysw.suite.audit.domain.useraction.UserActionGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.Objects;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateUserActionUseCaseTest {

    @Mock
    private UserActionGateway userActionGateway;

    @InjectMocks
    private CreateUserActionUseCase useCase;

    @BeforeEach
    void cleanUp(){
        Mockito.reset(userActionGateway);
    }
    @Test
    public void execute_CreatesUserAction_WhenValidRequestProvided() {
        // Given
        String service = "testService";
        Action action = Action.INSERT;
        String objectId = "testObjectId";
        String objectName = "testObjectName";
        String objectValue = "testObjectValue";
        String Username = "testUsername";
        Instant actionAt = Instant.now();

        UserActionInput input = UserActionInput.with(service, action, objectId, objectName, objectValue,
                Username, actionAt);

        UserAction expectedUserAction = UserAction.create(input.service(), input.action(), input.objectId(),
                input.objectName(), input.objectValue(), input.Username(), input.actionAt());

        //When
        when(userActionGateway.create(any(UserAction.class))).thenReturn(expectedUserAction);
        useCase.execute(input);

        //Then
        verify(userActionGateway, times(1)).create(any(UserAction.class));
        verify(userActionGateway, times(1)).create(argThat(userAction ->
                Objects.equals(expectedUserAction.getService(), userAction.getService())
                        && Objects.equals(expectedUserAction.getAction(), userAction.getAction())
                        && Objects.equals(expectedUserAction.getObjectId(), userAction.getObjectId())
                        && Objects.equals(expectedUserAction.getObjectName(), userAction.getObjectName())
                        && Objects.equals(expectedUserAction.getObjectValue(), userAction.getObjectValue())
                        && Objects.equals(expectedUserAction.getUsername(), userAction.getUsername())
                        && Objects.equals(expectedUserAction.getActionAt(), userAction.getActionAt())
                        && Objects.nonNull(userAction.getId())
                        && Objects.nonNull(userAction.getCreatedAt())
        ));

    }
}