package com.sysw.suite.audi.application.useraction.create;

import com.sysw.suite.audi.application.VoidUseCase;
import com.sysw.suite.audit.domain.useraction.UserAction;
import com.sysw.suite.audit.domain.useraction.UserActionGateway;

public class CreateUserActionUseCase extends VoidUseCase<UserActionInput> {

    private final UserActionGateway userActionGateway;

    public CreateUserActionUseCase(UserActionGateway userActionGateway) {
        this.userActionGateway = userActionGateway;
    }

    @Override
    public void execute(UserActionInput anIn) {

        UserAction userAction = UserAction.create(anIn.service(), anIn.action(), anIn.objectId(), anIn.objectName(),
                anIn.objectValue(), anIn.Username(), anIn.actionAt());

        userActionGateway.create(userAction);
    }
}
