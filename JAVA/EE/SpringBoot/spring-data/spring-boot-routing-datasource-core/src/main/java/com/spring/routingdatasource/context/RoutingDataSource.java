package com.spring.routingdatasource.context;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

/**
 * Routing data source by key of data source context.
 */
public class RoutingDataSource extends AbstractRoutingDataSource {
	@Override
	protected Object determineCurrentLookupKey() {
		return RoutingDataSourceContext.getDataSourceRoutingKey();
	}
}
