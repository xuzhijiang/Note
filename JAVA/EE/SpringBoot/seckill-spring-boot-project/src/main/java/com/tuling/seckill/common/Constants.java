package com.tuling.seckill.common;

public class Constants {
    public static final String REDIS_PRODUCT_PREFIX = "product_";

    public static final String REDIS_PRODUCT_STOCK_PREFIX = "product_stock_";

    public static final String ZK_PRODUCT_SOLD_OUT_FLAG = "/product_sold_out_flag";

    public static String getZKSoldOutProductPath(Long productId) {
        return ZK_PRODUCT_SOLD_OUT_FLAG + "/" + productId;
    }

}
