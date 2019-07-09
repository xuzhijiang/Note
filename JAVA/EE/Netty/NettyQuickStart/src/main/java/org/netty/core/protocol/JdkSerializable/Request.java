package org.netty.core.protocol.JdkSerializable;

import java.io.Serializable;
import java.util.Date;

public class Request implements Serializable {

    private String request;

    @Override
    public String toString() {
        return "Request{" +
                "request='" + request + '\'' +
                ", requestTime=" + requestTime +
                '}';
    }

    private Date requestTime;

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public Date getRequestTime() {
        return requestTime;
    }

    public void setRequestTime(Date requestTime) {
        this.requestTime = requestTime;
    }
}
