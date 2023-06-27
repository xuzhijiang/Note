package org.java.core.DesignPatterns.chain;

public class DeleteRequestHandler implements RequestHandler{
    @Override
    public void doHandle(Request request) {
        if ("DELETE".equals(request.getMethod())) {
            System.out.println("DELETE: " + request.getData());
        }
    }
}
