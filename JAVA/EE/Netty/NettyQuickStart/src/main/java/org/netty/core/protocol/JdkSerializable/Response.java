package org.netty.core.protocol.JdkSerializable;

import java.io.Serializable;
import java.util.Date;

public class Response implements Serializable {

    private String response;

    @Override
    public String toString() {
        return "Response{" +
                "response='" + response + '\'' +
                ", responseTime=" + responseTime +
                '}';
    }

    private Date responseTime;

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public Date getResponseTime() {
        return responseTime;
    }

    public void setResponseTime(Date responseTime) {
        this.responseTime = responseTime;
    }
}
