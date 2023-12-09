package com.sysw.suite.audi.application.useraction.create;

import com.sysw.suite.audit.domain.useraction.Action;

import javax.swing.*;
import java.time.Instant;

public record UserActionInput(String service,
                              Action action,
                              String objectId,
                              String objectName,
                              String objectValue,
                              String Username,
                              Instant actionAt) {

    public static UserActionInput with( String service,
                                        Action action,
                                        String objectId,
                                        String objectName,
                                        String objectValue,
                                        String Username,
                                        Instant actionAt) {
        return new UserActionInput(service, action, objectId, objectName, objectValue, Username, actionAt);
    }
}