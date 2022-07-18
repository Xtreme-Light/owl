package com.light.owl.exceptions;

import com.light.owl.util.OptionalCollection;
import java.util.Collection;
import java.util.function.Supplier;

/**
 * 对于单个属性的断言，如果是对整个结构类的校验，请使用spring 的validator
 *
 * @link <a
 *     href="https://docs.spring.io/spring-framework/docs/current/reference/html/core.html#validation">validation</a>
 */
public class Assert {
  private Assert() {}

  public static void validate(Validate validate) {
    if (validate.verify()) {
      return;
    }
    throw validate.runtimeException();
  }

  public static <X extends RuntimeException> void validate(
      Validate validate, Supplier<? extends X> exceptionSupplier) {
    if (validate.verify()) {
      return;
    }
    throw exceptionSupplier.get();
  }

  public static <C extends Collection<T>, T> void notEmpty(C collection) {
    if (collection == null || collection.isEmpty()) {
      return;
    }
    throw new EmptyCollectionException();
  }

  public static <C extends CharSequence> void notEmpty(C collection) {
    if (collection == null || collection.length() == 0) {
      return;
    }
    throw new EmptyCharSequenceException();
  }

  public static <C extends CharSequence> void notBlank(C collection) {

  }
}
