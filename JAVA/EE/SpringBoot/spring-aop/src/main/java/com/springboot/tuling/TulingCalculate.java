package com.springboot.tuling;

import org.springframework.aop.framework.AopContext;

public class TulingCalculate implements Calculate{
    @Override
    public int add(int numA, int numB) {
        return numA + numB;
    }

    @Override
    public int reduce(int numA, int numB) {
        return numA - numB;
    }

    @Override
    public int div(int numA, int numB) {
        return numA / numB;
    }

    @Override
    public int multi(int numA, int numB) {
        return numA * numB;
    }

    @Override
    public int mod(int numA, int numB) {
        int retVal = ((Calculate)AopContext.currentProxy()).add(numA, numB);
        return retVal % numA;
    }
}
