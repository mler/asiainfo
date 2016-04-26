package com.bdx.rainbow.base.security;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

@Aspect 
public class SystemArchitecture { 
  /** 
   * A Join Point is defined in the action layer where the method needs 
   * a permission check. 
   */ 
   @Pointcut("@annotation(com.bdx.rainbow.base.annotation.Security)") 
   public void userAccess() {} 
   
//
//   @Pointcut("execution(*  com.bdx.rainbow.controller..*.*(..))")
//    public void test() {}
   
}
