package com.sysw.suite.core.domain.business.businessunit;

import com.sysw.suite.core.domain.business.module.SearchQuery;
import com.sysw.suite.core.pagination.Pagination;

import java.util.Optional;

public interface BusinessUnitGateway {

    BusinessUnit create(BusinessUnit aBusinessUnit);

    Optional<BusinessUnit> findById(BusinessUnitID anId);

    BusinessUnit update(BusinessUnit aBusinessUnit);

    Pagination<BusinessUnit> findAll(SearchQuery aQuery);

}
