package org.java.core.DesignPatterns.chain;

public class PutRequestHandler implements RequestHandler{
    @Override
    public void doHandle(Request request) {
        if ("PUT".equals(request.getMethod())) {
            System.out.println("PUT: " + request.getData());
        }
    }
}
