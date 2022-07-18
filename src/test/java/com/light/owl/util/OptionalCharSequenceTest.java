package com.light.owl.util;

import com.light.owl.exceptions.BlankCharSequenceException;
import com.light.owl.exceptions.EmptyCharSequenceException;
import com.light.owl.exceptions.NotStringException;
import org.junit.Test;

public class OptionalCharSequenceTest {

  @Test
  public void test() {
    final OptionalCharSequence of = OptionalCharSequence.of("");
    assert of.isPresent();
    assert of.isBlank();
    assert of.isEmpty();
    assert of.isString();
  }

  @Test
  public void test2() {
    final OptionalCharSequence of = OptionalCharSequence.ofNullable(null);
    assert !of.isPresent();
    assert of.isBlank();
    assert of.isEmpty();
  }

  @Test
  public void test3() {
    final OptionalCharSequence of = OptionalCharSequence.ofNullable(" ");
    assert of.isPresent();
    assert !of.isEmpty();
    assert of.isBlank();
  }

  @Test
  public void test4() {
    final OptionalCharSequence of = OptionalCharSequence.ofNullable("");
    final CharSequence charSequence = of.notEmptyOrElse("123");
    assert charSequence.equals("123");
  }

  @Test
  public void test5() {
    final OptionalCharSequence of = OptionalCharSequence.ofNullable("   ");
    final CharSequence charSequence = of.notBlankOrElse("123");
    assert charSequence.equals("123");
  }

  @Test
  public void test6() {
    final OptionalCharSequence of = OptionalCharSequence.ofNullable("123");
    final OptionalCharSequence filter = of.filter(v -> v.equals("123"));
    assert filter.isPresent();
    assert filter.get().equals("123");
  }

  @Test
  public void test7() {
    final OptionalCharSequence of = OptionalCharSequence.ofNullable("");
    final OptionalCharSequence filter = of.notEmptyFilter(v -> v.equals("123"));
    assert filter.isEmpty();
    assert filter.isPresent();
    assert filter.isBlank();
  }

  @Test
  public void test8() {
    final OptionalCharSequence of = OptionalCharSequence.ofNullable("   ");
    final OptionalCharSequence filter = of.notBlankFilter(v -> v.equals("123"));
    assert !filter.isEmpty();
    assert filter.isPresent();
    assert filter.isBlank();
  }

  @Test
  public void test9() {
    OptionalCharSequence.of("测试")
        .ifNotBlank(
            v -> {
              String item = (String) v;
              assert item.contains("测");
            });
    OptionalCharSequence.of("测试")
        .ifNotBlankString(
            v -> {
              assert v.contains("测试");
            });
  }

  @Test
  public void test10() {
    assert OptionalCharSequence.of("string").isString();
    OptionalCharSequence.of("abc")
        .ifNotBlankOrElse(
            v -> {
              assert v.equals("abc");
            },
            () -> {
              throw new RuntimeException();
            });
    OptionalCharSequence.of("abc")
        .ifNotBlankStringOrElse(
            v -> {
              assert v.contains("ab");
            },
            () -> {
              throw new RuntimeException();
            });
    OptionalCharSequence.of("   ")
        .ifNotBlankOrElse(
            v -> {
              throw new RuntimeException();
            },
            () -> System.out.println("run this way"));
    OptionalCharSequence.of("   ")
        .ifNotBlankStringOrElse(
            v -> {
              throw new RuntimeException();
            },
            () -> System.out.println("run this way"));
    assert "abc".equals(OptionalCharSequence.of("abc").notBlankStringOrElse("bcd"));
    assert "bcd".equals(OptionalCharSequence.of("   ").notBlankStringOrElse("bcd"));
  }

  @Test(expected = EmptyCharSequenceException.class)
  public void testEmptyException() {
    OptionalCharSequence.ofNullable("").notEmptyOrElseThrow();
    assert "123".contentEquals(OptionalCharSequence.ofNullable("123").notEmptyOrElseThrow());
    assert "123".equals(OptionalCharSequence.ofNullable("123").notEmptyStringOrElseThrow());
  }

  @Test(expected = BlankCharSequenceException.class)
  public void testException() {
    assert "123"
        .contentEquals(
            OptionalCharSequence.of("123").notBlankOrElseThrow(BlankCharSequenceException::new));
    OptionalCharSequence.of("   ").notBlankOrElseThrow(BlankCharSequenceException::new);
  }

  @Test(expected = BlankCharSequenceException.class)
  public void testBlankException() {
    assert "123".contentEquals(OptionalCharSequence.ofNullable("123").notBlankOrElseThrow());
    OptionalCharSequence.ofNullable("  ").notBlankOrElseThrow();
  }

  @Test(expected = BlankCharSequenceException.class)
  public void testBlankException2() {
    assert "123".equals(OptionalCharSequence.ofNullable("123").notBlankStringOrElseThrow());
    OptionalCharSequence.ofNullable("  ").notBlankStringOrElseThrow();
  }

  @Test(expected = EmptyCharSequenceException.class)
  public void testSupplierException() {
    assert "123"
        .contentEquals(
            OptionalCharSequence.ofNullable("123")
                .notBlankOrElseThrow(EmptyCharSequenceException::new));
    OptionalCharSequence.ofNullable("  ").notBlankOrElseThrow(EmptyCharSequenceException::new);
  }
  @Test(expected = EmptyCharSequenceException.class)
  public void testSupplierException2() {
    assert "123"
        .equals(
            OptionalCharSequence.ofNullable("123")
                .notBlankStringOrElseThrow(EmptyCharSequenceException::new));
    OptionalCharSequence.ofNullable("  ").notBlankStringOrElseThrow(EmptyCharSequenceException::new);
  }

  @Test(expected = NotStringException.class)
  public void testNotString() {
    OptionalCharSequence.ofNullable(new StringBuilder("builder")).notEmptyStringOrElseThrow();
  }
}
