package com.sysw.suite.audi.application.useraction.retrieve.list;

import com.sysw.suite.audi.application.UseCase;
import com.sysw.suite.audit.domain.pagination.Pagination;
import com.sysw.suite.audit.domain.useraction.UserAction;
import com.sysw.suite.audit.domain.useraction.UserActionGateway;
import com.sysw.suite.audit.domain.useraction.UserActionSearchQuery;

import java.util.Objects;

public class ListUserActionUseCase extends UseCase<UserActionSearchQuery, Pagination<UserActionListOutput>> {

    private final UserActionGateway userActionGateway;

    public ListUserActionUseCase(final UserActionGateway userActionGateway) {
        this.userActionGateway = Objects.requireNonNull(userActionGateway);
    }

    @Override
    public Pagination<UserActionListOutput> execute(UserActionSearchQuery searchQuery) {
        Pagination<UserAction> all = userActionGateway.findAll(searchQuery);
        return all.map(userAction -> UserActionListOutput.from(userAction));
    }

}
