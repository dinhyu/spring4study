package pers.spring4.day03.n2_aop.bean;

import org.springframework.stereotype.Component;

import pers.spring4.day03.n2_aop.inter.Calculator;
@Component
public class CalculatorImpl implements Calculator{
    @Override
    public Double getSum(Double a, Double b) {
        System.out.println("CalculatorImpl getSum");
        // TODO Auto-generated method stub
        return a + b;
    }
}
