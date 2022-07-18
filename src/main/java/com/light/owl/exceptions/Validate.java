package com.light.owl.exceptions;

/** 校验规则接口 */
public interface Validate {


  boolean verify();

  RuntimeException runtimeException();
}
