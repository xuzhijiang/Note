package org.java.core.DesignPatterns.chain;

public class PostRequestHandler implements RequestHandler{
    @Override
    public void doHandle(Request request) {
        if ("POST".equals(request.getMethod())) {
            System.out.println("POST: " + request.getData());
        }
    }
}
