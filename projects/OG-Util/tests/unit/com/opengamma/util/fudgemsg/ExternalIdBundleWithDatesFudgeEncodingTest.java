/**
 * Copyright (C) 2011 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.util.fudgemsg;

import javax.time.calendar.LocalDate;
import javax.time.calendar.MonthOfYear;

import org.testng.annotations.Test;

import com.opengamma.id.ExternalId;
import com.opengamma.id.ExternalIdBundleWithDates;
import com.opengamma.id.ExternalIdWithDates;
import com.opengamma.util.test.AbstractBuilderTestCase;

/**
 * Test Fudge encoding.
 */
@Test
public class ExternalIdBundleWithDatesFudgeEncodingTest extends AbstractBuilderTestCase {

  private static final LocalDate VALID_FROM = LocalDate.of(2010, MonthOfYear.JANUARY, 1);
  private static final LocalDate VALID_TO = LocalDate.of(2010, MonthOfYear.DECEMBER, 1);

  public void test_noDates() {
    ExternalIdWithDates basic1 = ExternalIdWithDates.of(ExternalId.of("A", "B"), null, null);
    ExternalIdWithDates basic2 = ExternalIdWithDates.of(ExternalId.of("C", "D"), null, null);
    ExternalIdBundleWithDates object = ExternalIdBundleWithDates.of(basic1, basic2);
    assertEncodeDecodeCycle(ExternalIdBundleWithDates.class, object);
  }

  public void test_withDates() {
    ExternalIdWithDates basic1 = ExternalIdWithDates.of(ExternalId.of("A", "B"), VALID_FROM, VALID_TO);
    ExternalIdWithDates basic2 = ExternalIdWithDates.of(ExternalId.of("C", "D"), VALID_FROM, VALID_TO);
    ExternalIdBundleWithDates object = ExternalIdBundleWithDates.of(basic1, basic2);
    assertEncodeDecodeCycle(ExternalIdBundleWithDates.class, object);
  }

}
