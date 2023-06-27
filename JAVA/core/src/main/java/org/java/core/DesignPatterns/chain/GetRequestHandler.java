package org.java.core.DesignPatterns.chain;

public class GetRequestHandler implements RequestHandler{
    @Override
    public void doHandle(Request request) {
        if ("GET".equals(request.getMethod())) {
            System.out.println("GET: " + request.getData());
        }
    }
}
