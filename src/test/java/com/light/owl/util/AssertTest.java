package com.light.owl.util;

import com.light.owl.exceptions.Assert;
import com.light.owl.exceptions.BlankCharSequenceException;
import com.light.owl.exceptions.validator.BlankCharSequenceValidator;
import org.junit.Test;

public class AssertTest {

  @Test(expected = BlankCharSequenceException.class)
  public void test() {
    Assert.validate(new BlankCharSequenceValidator("item"));
    Assert.validate(new BlankCharSequenceValidator(" "));

  }
}
