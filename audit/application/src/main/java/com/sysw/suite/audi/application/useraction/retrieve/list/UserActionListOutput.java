package com.sysw.suite.audi.application.useraction.retrieve.list;

import com.sysw.suite.audit.domain.useraction.Action;
import com.sysw.suite.audit.domain.useraction.UserAction;
import com.sysw.suite.audit.domain.useraction.UserActionID;

import java.time.Instant;

public record UserActionListOutput(UserActionID id,
                                   Action action,
                                   String service,
                                   String objectId,
                                   String objectName,
                                   String objectValue,
                                   String username,
                                   Instant updateAt,
                                   Instant createdAt
                                   ) {

    public static UserActionListOutput from(UserAction anUserAction) {
        return new UserActionListOutput(anUserAction.getId(),
                anUserAction.getAction(),
                anUserAction.getService(),
                anUserAction.getObjectId(),
                anUserAction.getObjectName(),
                anUserAction.getObjectValue(),
                anUserAction.getUsername(),
                anUserAction.getActionAt(),
                anUserAction.getCreatedAt());
    }

}