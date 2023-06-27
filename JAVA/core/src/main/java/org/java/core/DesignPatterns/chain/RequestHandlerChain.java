package org.java.core.DesignPatterns.chain;

import java.util.ArrayList;
import java.util.List;

public class RequestHandlerChain implements RequestHandler{
    List<RequestHandler> list;

    public RequestHandlerChain() {
        list = new ArrayList<>();
    }

    public void addRequestHandler(RequestHandler requestHandler) {
        list.add(requestHandler);
    }

    @Override
    public void doHandle(Request request) {
        for (RequestHandler requestHandler : list) {
            requestHandler.doHandle(request);
        }
    }
}
