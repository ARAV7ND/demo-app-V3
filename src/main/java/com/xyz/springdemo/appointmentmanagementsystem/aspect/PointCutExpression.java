package com.xyz.springdemo.appointmentmanagementsystem.aspect;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect
public class PointCutExpression {

    @Pointcut("execution(* com.xyz.springdemo.appointmentmanagementsystem.service.*.find*(..))")
    public void find(){
        //no implementation
    }
    @Pointcut("execution(* com.xyz.springdemo.appointmentmanagementsystem.service.*.save*(..))")
    public void save(){
        //no implementation
    }
    @Pointcut("execution(* com.xyz.springdemo.appointmentmanagementsystem.service.*.delete*(..))")
    public void delete(){
        //no implementation
    }
    @Pointcut("execution(* com.xyz.springdemo.appointmentmanagementsystem.service.*.update*(..))")
    public void update(){
        //no implementation
    }
}
