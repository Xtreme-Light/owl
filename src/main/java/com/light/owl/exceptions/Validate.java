package com.light.owl.exceptions;

public interface Validate<T> {

  boolean verify(T t);

  T get();

  RuntimeException exception();
}
