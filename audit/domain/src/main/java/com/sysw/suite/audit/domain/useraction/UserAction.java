package com.sysw.suite.audit.domain.useraction;

import com.sysw.suite.audit.domain.AggregateRoot;
import com.sysw.suite.audit.domain.validation.ValidationHandler;

import java.time.Instant;

public class UserAction extends AggregateRoot<UserActionID> implements Cloneable {

    private String service;
    private Action action;
    private String objectId;
    private String objectName;
    private String objectValue;
    private String username;
    private Instant actionAt;
    private Instant createdAt;

    private UserAction(UserActionID id, String service, Action action, String objectId,String objectName, String objectValue,
                       String username, Instant actionAt, Instant createdAt) {
        super(id);
        this.service = service;
        this.action = action;
        this.objectId = objectId;
        this.objectName = objectName;
        this.objectValue = objectValue;
        this.username = username;
        this.actionAt = actionAt;
        this.createdAt = createdAt;
    }

    public static UserAction create(UserActionID id, String service, Action action, String objectId, String objectName,
                                    String objectValue, String username, Instant actionAt) {
        Instant now = Instant.now();
        return new UserAction(id, service, action, objectId, objectName, objectValue, username, actionAt, now);
    }

    public static UserAction create(String service, Action action, String objectId,String objectName, String objectValue,
                                    String username, Instant actionAt) {
        Instant now = Instant.now();
        UserActionID id = UserActionID.unique();
        return new UserAction(id, service, action, objectId, objectName, objectValue, username, actionAt, now);
    }


    public String getService() {
        return service;
    }

    public Action getAction() {
        return action;
    }

    public String getObjectId() {
        return objectId;
    }

    public String getObjectName() {
        return objectName;
    }

    public String getObjectValue() {
        return objectValue;
    }

    public String getUsername() {
        return username;
    }

    public Instant getActionAt() {
        return actionAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    @Override
    public void validate(ValidationHandler handler) {

    }
}
