package com.light.owl.util;

import com.light.owl.exceptions.BlankCharSequenceException;
import com.light.owl.exceptions.EmptyCharSequenceException;
import com.light.owl.exceptions.NotStringException;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

public final class OptionalCharSequence {

  private static final OptionalCharSequence EMPTY = new OptionalCharSequence(null);

  private final CharSequence value;

  private final boolean empty;

  private final boolean blank;

  private final boolean string;

  public static OptionalCharSequence empty() {
    return EMPTY;
  }

  private OptionalCharSequence(CharSequence value) {
    this.value = value;
    this.empty = value == null || value.length() == 0;
    this.blank = empty || isBlank(value);
    string = value instanceof String;
  }

  /**
   * Checks if a CharSequence is empty (""), null or whitespace only.
   *
   * <p>Whitespace is defined by {@link Character#isWhitespace(char)}.
   *
   * <pre>
   * isBlank(null)      = true
   * isBlank("")        = true
   * isBlank(" ")       = true
   * isBlank("bob")     = false
   * isBlank("  bob  ") = false
   * </pre>
   */
  private boolean isBlank(final CharSequence charSequence) {
    final int length = charSequence.length();
    for (int i = 0; i < length; i++) {
      if (!Character.isWhitespace(charSequence.charAt(i))) {
        return false;
      }
    }
    return true;
  }

  /** 不可为null的容器 */
  public static OptionalCharSequence of(CharSequence value) {
    return new OptionalCharSequence(Objects.requireNonNull(value));
  }

  /** 可为null 的容器 */
  public static OptionalCharSequence ofNullable(CharSequence value) {
    return value == null ? EMPTY : new OptionalCharSequence(value);
  }

  /** 是否存在，即不为null */
  public boolean isPresent() {
    return value != null;
  }

  /** 如果不为空 do action */
  public void ifPresent(Consumer<? super CharSequence> action) {
    if (value != null) {
      action.accept(value);
    }
  }

  /** 如果不为空 do action */
  public void ifPresentString(Consumer<? super String> action) {
    if (value != null && string) {
      action.accept((String) value);
    }
  }

  /** 如果不为空 do action，else do emptyAction */
  public void ifPresentOrElse(Consumer<? super CharSequence> action, Runnable emptyAction) {
    if (value != null) {
      action.accept(value);
    } else {
      emptyAction.run();
    }
  }

  /** 如果不为空 do action，else do emptyAction */
  public void ifPresentStringOrElse(Consumer<? super String> action, Runnable emptyAction) {
    if (string && value != null) {
      action.accept((String) value);
      return;
    }
    emptyAction.run();
  }

  /** 筛选器，基于非null */
  public OptionalCharSequence filter(Predicate<? super CharSequence> predicate) {
    Objects.requireNonNull(predicate);
    if (!isPresent()) {
      return this;
    } else {
      return predicate.test(value) ? this : empty();
    }
  }

  /** 筛选器，基于非null */
  public OptionalCharSequence stringFilter(Predicate<? super String> predicate) {
    Objects.requireNonNull(predicate);
    if (!isPresent() || !string) {
      return this;
    } else {
      return predicate.test((String) value) ? this : empty();
    }
  }

  public OptionalCharSequence notEmptyFilter(Predicate<? super CharSequence> predicate) {
    Objects.requireNonNull(predicate);
    if (empty) {
      return this;
    } else {
      return predicate.test(value) ? this : empty();
    }
  }

  public OptionalCharSequence notEmptyStringFilter(Predicate<? super String> predicate) {
    Objects.requireNonNull(predicate);
    if (empty || !string) {
      return this;
    } else {
      return predicate.test((String) value) ? this : empty();
    }
  }

  public OptionalCharSequence notBlankFilter(Predicate<? super CharSequence> predicate) {
    Objects.requireNonNull(predicate);
    if (blank) {
      return this;
    } else {
      return predicate.test(value) ? this : empty();
    }
  }

  public OptionalCharSequence notBlankStringFilter(Predicate<? super String> predicate) {
    Objects.requireNonNull(predicate);
    if (blank || !string) {
      return this;
    } else {
      return predicate.test((String) value) ? this : empty();
    }
  }

  /** 映射器，基于非null，映射到Optional */
  public <U> Optional<U> map(Function<? super CharSequence, ? extends U> mapper) {
    Objects.requireNonNull(mapper);
    if (isPresent()) {
      return Optional.empty();
    } else {
      return Optional.ofNullable(mapper.apply(value));
    }
  }

  /** flat 映射器，基于非null，映射到Optional */
  public <U> Optional<U> flatMap(
      Function<? super CharSequence, ? extends Optional<? extends U>> mapper) {
    Objects.requireNonNull(mapper);
    if (!isPresent()) {
      return Optional.empty();
    } else {
      @SuppressWarnings("unchecked")
      Optional<U> r = (Optional<U>) mapper.apply(value);
      return Objects.requireNonNull(r);
    }
  }

  /** 转stream流 */
  public Stream<CharSequence> stream() {
    if (!isPresent()) {
      return Stream.empty();
    } else {
      return Stream.of(value);
    }
  }

  /** 如果为null 提供其他的值 */
  public CharSequence orElse(CharSequence other) {
    return value != null ? value : other;
  }

  /** 如果为null 提供其他的值 */
  public String orElseString(String other) {
    return value != null ? string ? (String) value : other : other;
  }

  /** 获取容器内容 */
  public CharSequence get() {
    if (value == null) {
      throw new NoSuchElementException("No value present");
    }
    return value;
  }

  /** 获取容器内容 */
  public String getString() {
    if (value == null) {
      throw new NoSuchElementException("No value present");
    }
    if (!string) {
      throw new NotStringException();
    }
    return (String) value;
  }

  /** 是否为空字符 */
  public boolean isEmpty() {
    return empty;
  }

  /** 如果不为空字符 do action */
  public void ifNotEmpty(Consumer<? super CharSequence> action) {
    if (!empty) {
      action.accept(value);
    }
  }

  public void ifNotEmptyString(Consumer<? super String> action) {
    if (!empty) {
      action.accept((String) value);
    }
  }

  /** 如果为空字符 提供其他的值 */
  public CharSequence notEmptyOrElse(CharSequence other) {
    return empty ? other : value;
  }

  /** 如果为空字符 提供其他的值 */
  public String notEmptyStringOrElse(String other) {
    return string ? (String) notEmptyOrElse(other) : other;
  }

  /** 如果不为空 do action，else do emptyAction */
  public void ifNotEmptyOrElse(Consumer<? super CharSequence> action, Runnable emptyAction) {
    if (!empty) {
      action.accept(value);
    } else {
      emptyAction.run();
    }
  }

  /** 如果不为空 do action，else do emptyAction */
  public void ifNotEmptyStringOrElse(Consumer<? super String> action, Runnable emptyAction) {
    if (string) {
      if (!empty) {
        action.accept((String) value);
        return;
      }
    }
    emptyAction.run();
  }

  /** 是否为空白字符 */
  public boolean isBlank() {
    return blank;
  }

  public boolean isString() {
    return string;
  }

  /** 如果不为空字符 do action */
  public void ifNotBlank(Consumer<? super CharSequence> action) {
    if (!blank) {
      action.accept(value);
    }
  }

  /** 如果不为空字符 do action */
  public void ifNotBlankString(Consumer<? super String> action) {
    if (!blank && string) {
      action.accept((String) value);
    }
  }

  /** 如果不为空白字符串 do action，else do emptyAction */
  public void ifNotBlankOrElse(Consumer<? super CharSequence> action, Runnable emptyAction) {
    if (!blank) {
      action.accept(value);
    } else {
      emptyAction.run();
    }
  }

  /** 如果不为空白字符串 do action，else do emptyAction */
  public void ifNotBlankStringOrElse(Consumer<? super String> action, Runnable emptyAction) {
    if (string) {
      if (!blank) {
        action.accept((String) value);
        return;
      }
    }
    emptyAction.run();
  }

  /** 如果为空白字符 提供其他的值 */
  public CharSequence notBlankOrElse(CharSequence other) {
    return blank ? other : value;
  }

  /** 如果为空白字符 或者不是字符串类型 提供其他的值 */
  public String notBlankStringOrElse(String other) {
    return blank || !string ? other : (String) value;
  }

  public CharSequence orElseThrow() {
    if (value == null) {
      throw new NoSuchElementException("No value present");
    }
    return value;
  }

  public CharSequence stringOrElseThrow() {
    if (value == null || !string) {
      throw new NoSuchElementException("No value present");
    }
    return value;
  }

  public CharSequence notEmptyOrElseThrow() {
    if (empty) {
      throw new EmptyCharSequenceException("空字符串");
    }
    return value;
  }

  public String notEmptyStringOrElseThrow() {
    if (empty) {
      throw new EmptyCharSequenceException("空字符串");
    }
    if (!string) {
      throw new NotStringException();
    }
    return (String) value;
  }

  public <X extends Throwable> CharSequence notEmptyOrElseThrow(
      Supplier<? extends X> exceptionSupplier) throws X {
    if (!empty) {
      return value;
    } else {
      throw exceptionSupplier.get();
    }
  }

  public <X extends Throwable> String notEmptyStringOrElseThrow(
      Supplier<? extends X> exceptionSupplier) throws X {
    if (!empty && string) {
      return (String) value;
    } else {
      throw exceptionSupplier.get();
    }
  }

  public CharSequence notBlankOrElseThrow() {
    if (blank) {
      throw new BlankCharSequenceException("空白字符串");
    }
    return value;
  }

  public String notBlankStringOrElseThrow() {
    if (blank) {
      throw new BlankCharSequenceException("空白字符串");
    }
    if (!string) {
      throw new NotStringException();
    }
    return (String) value;
  }

  public <X extends Throwable> CharSequence notBlankOrElseThrow(
      Supplier<? extends X> exceptionSupplier) throws X {
    if (!blank) {
      return value;
    } else {
      throw exceptionSupplier.get();
    }
  }

  public <X extends Throwable> String notBlankStringOrElseThrow(
      Supplier<? extends X> exceptionSupplier) throws X {
    if (!blank && string) {
      return (String) value;
    } else {
      throw exceptionSupplier.get();
    }
  }
}
