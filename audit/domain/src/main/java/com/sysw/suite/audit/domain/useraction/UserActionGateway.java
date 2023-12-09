package com.sysw.suite.audit.domain.useraction;

import com.sysw.suite.audit.domain.pagination.Pagination;

import java.util.Optional;

public interface UserActionGateway {

    UserAction create(UserAction anUserAction);

    void deleteById(UserActionID anId);

    Optional<UserAction> findById(UserActionID anId);

    UserAction update(UserAction anUserAction);

    Pagination<UserAction> findAll(UserActionSearchQuery aQuery);

}
