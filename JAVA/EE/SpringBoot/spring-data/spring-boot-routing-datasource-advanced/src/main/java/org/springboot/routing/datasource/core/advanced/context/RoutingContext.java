package org.springboot.routing.datasource.core.advanced.context;

public class RoutingContext {
    private static final ThreadLocal<String> DATASOURCES = new ThreadLocal<String>();

    public static void setDatasource(String dataSourceName) {
        DATASOURCES.set(dataSourceName);
    }

    public static String getCurrentDataSource(){
        return DATASOURCES.get();
    }

    public static void clear() {
        DATASOURCES.remove();
    }
}
