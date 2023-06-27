package org.java.core.DesignPatterns.chain;

// 通过责任链模式 解决if else滥用的问题
public class ChainMainClass {

    public static void main(String[] args) {
        Request request = new Request("PUT", "data");
        handleRequest(request);
    }

    private static void handleRequest(Request request) {
        // 重构前
        beforeRefactor(request);

        // 重构后
        afterRefactor(request);
    }

    private static void beforeRefactor(Request request) {
        if ("GET".equals(request.getMethod())) {
            System.out.println("GET: " + request.getData());
        } else if ("POST".equals(request.getMethod())) {
            System.out.println("POST: " + request.getData());
        } else if ("PUT".equals(request.getMethod())) {
            System.out.println("PUT: " + request.getData());
        } else if ("DELETE".equals(request.getMethod())) {
            System.out.println("DELETE: " + request.getData());
        }
    }

    private static void afterRefactor(Request request) {
        RequestHandlerChain chain = new RequestHandlerChain();
        chain.addRequestHandler(new GetRequestHandler());
        chain.addRequestHandler(new PostRequestHandler());
        chain.addRequestHandler(new PutRequestHandler());
        chain.addRequestHandler(new DeleteRequestHandler());

        chain.doHandle(request);
    }
}
