/**
 * Copyright (C) 2011 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.analytics.financial.instrument.annuity;

import static org.testng.AssertJUnit.assertEquals;

import org.testng.annotations.Test;
import org.threeten.bp.Period;
import org.threeten.bp.ZonedDateTime;

import com.opengamma.analytics.financial.instrument.index.IborIndex;
import com.opengamma.analytics.financial.instrument.index.IndexIborMaster;
import com.opengamma.analytics.financial.instrument.payment.CouponIborCompoundingDefinition;
import com.opengamma.analytics.financial.instrument.payment.CouponIborCompoundingFlatSpreadDefinition;
import com.opengamma.analytics.financial.instrument.payment.CouponIborCompoundingSpreadDefinition;
import com.opengamma.analytics.financial.instrument.payment.CouponIborDefinition;
import com.opengamma.analytics.financial.instrument.payment.CouponIborSpreadDefinition;
import com.opengamma.analytics.financial.schedule.ScheduleCalculator;
import com.opengamma.financial.convention.StubType;
import com.opengamma.financial.convention.calendar.Calendar;
import com.opengamma.financial.convention.calendar.MondayToFridayCalendar;
import com.opengamma.financial.convention.rolldate.RollDateAdjuster;
import com.opengamma.financial.convention.rolldate.RollDateAdjusterFactory;
import com.opengamma.util.time.DateUtils;

/**
 * Test related to the construction of annuities definition of different types.
 */
public class AnnuityDefinitionBuilderTest {

  private static final IndexIborMaster IBOR_MASTER = IndexIborMaster.getInstance();
  private static final IborIndex USDLIBOR3M = IBOR_MASTER.getIndex("USDLIBOR3M");
  private static final IborIndex USDLIBOR6M = IBOR_MASTER.getIndex("USDLIBOR6M");
  private static final Calendar NYC = new MondayToFridayCalendar("NYC");

  private static final double NOTIONAL = 10000000; // 10m
  private static final double SPREAD = 0.0010; // 10bps

  private static final RollDateAdjuster QUARTERLY_IMM_ADJUSTER = RollDateAdjusterFactory.getAdjuster(RollDateAdjusterFactory.QUARTERLY_IMM_ROLL_STRING);

  @Test
  public void annuityIborFromStandard() {
    ZonedDateTime settlementDate = DateUtils.getUTCDate(2013, 9, 20);
    ZonedDateTime maturityDate = settlementDate.plusYears(2);
    Period paymentPeriod = Period.ofMonths(6);
    final StubType stub = StubType.SHORT_START;
    AnnuityDefinition<CouponIborDefinition> leg = AnnuityDefinitionBuilder.annuityIborFrom(settlementDate, maturityDate, paymentPeriod, NOTIONAL,
        USDLIBOR6M, true, USDLIBOR6M.getBusinessDayConvention(), USDLIBOR6M.isEndOfMonth(), USDLIBOR6M.getDayCount(), NYC, stub);
    ZonedDateTime[] expectedPaymentDates = new ZonedDateTime[] {DateUtils.getUTCDate(2014, 3, 20), DateUtils.getUTCDate(2014, 9, 22),
      DateUtils.getUTCDate(2015, 3, 20), DateUtils.getUTCDate(2015, 9, 21) };
    assertEquals("AnnuityDefinitionBuilder: Coupon Ibor Spread", expectedPaymentDates.length, leg.getNumberOfPayments());
    assertEquals("AnnuityDefinitionBuilder: Coupon Ibor Spread", settlementDate, leg.getNthPayment(0).getAccrualStartDate());
    for (int loopcpn = 0; loopcpn < leg.getNumberOfPayments(); loopcpn++) {
      assertEquals("AnnuityDefinitionBuilder: Coupon Ibor Spread", expectedPaymentDates[loopcpn], leg.getNthPayment(loopcpn).getPaymentDate());
      assertEquals("AnnuityDefinitionBuilder: Coupon Ibor Spread", leg.getNthPayment(loopcpn).getAccrualStartDate(), leg.getNthPayment(loopcpn).getFixingPeriodStartDate());
      assertEquals("AnnuityDefinitionBuilder: Coupon Ibor Spread", leg.getNthPayment(loopcpn).getFixingPeriodEndDate(),
          ScheduleCalculator.getAdjustedDate(leg.getNthPayment(loopcpn).getFixingPeriodStartDate(), USDLIBOR6M, NYC));
      assertEquals("AnnuityDefinitionBuilder: Coupon Ibor Spread", NOTIONAL, -leg.getNthPayment(loopcpn).getNotional()); // Payer
    }
  }

  @Test
  public void annuityIborFromShortFirst() {
    ZonedDateTime settlementDate = DateUtils.getUTCDate(2013, 8, 20);
    ZonedDateTime maturityDate = settlementDate.plusYears(2).plusMonths(1); // 2Y 1M
    Period paymentPeriod = Period.ofMonths(6);
    final StubType stub = StubType.SHORT_START;
    AnnuityDefinition<CouponIborDefinition> leg = AnnuityDefinitionBuilder.annuityIborFrom(settlementDate, maturityDate, paymentPeriod, NOTIONAL,
        USDLIBOR6M, true, USDLIBOR6M.getBusinessDayConvention(), USDLIBOR6M.isEndOfMonth(), USDLIBOR6M.getDayCount(), NYC, stub);
    ZonedDateTime[] expectedPaymentDates = new ZonedDateTime[] {DateUtils.getUTCDate(2013, 9, 20), DateUtils.getUTCDate(2014, 3, 20),
      DateUtils.getUTCDate(2014, 9, 22), DateUtils.getUTCDate(2015, 3, 20), DateUtils.getUTCDate(2015, 9, 21) };
    assertEquals("AnnuityDefinitionBuilder: Coupon Ibor Spread", expectedPaymentDates.length, leg.getNumberOfPayments());
    assertEquals("AnnuityDefinitionBuilder: Coupon Ibor Spread", settlementDate, leg.getNthPayment(0).getAccrualStartDate());
    for (int loopcpn = 0; loopcpn < leg.getNumberOfPayments(); loopcpn++) {
      assertEquals("AnnuityDefinitionBuilder: Coupon Ibor Spread", expectedPaymentDates[loopcpn], leg.getNthPayment(loopcpn).getPaymentDate());
      assertEquals("AnnuityDefinitionBuilder: Coupon Ibor Spread", leg.getNthPayment(loopcpn).getAccrualStartDate(), leg.getNthPayment(loopcpn).getFixingPeriodStartDate());
      assertEquals("AnnuityDefinitionBuilder: Coupon Ibor Spread", leg.getNthPayment(loopcpn).getFixingPeriodEndDate(),
          ScheduleCalculator.getAdjustedDate(leg.getNthPayment(loopcpn).getFixingPeriodStartDate(), USDLIBOR6M, NYC));
      assertEquals("AnnuityDefinitionBuilder: Coupon Ibor Spread", NOTIONAL, -leg.getNthPayment(loopcpn).getNotional()); // Payer
    }
  }

  @Test
  public void annuityIborFromRollDate() {
    final ZonedDateTime[] expectedDates = new ZonedDateTime[] {DateUtils.getUTCDate(2013, 9, 18), DateUtils.getUTCDate(2013, 12, 18), DateUtils.getUTCDate(2014, 3, 19),
      DateUtils.getUTCDate(2014, 6, 18), DateUtils.getUTCDate(2014, 9, 17), DateUtils.getUTCDate(2014, 12, 17), DateUtils.getUTCDate(2015, 3, 18),
      DateUtils.getUTCDate(2015, 6, 17), DateUtils.getUTCDate(2015, 9, 16), DateUtils.getUTCDate(2015, 12, 16), DateUtils.getUTCDate(2016, 3, 16),
      DateUtils.getUTCDate(2016, 6, 15) };
    ZonedDateTime referenceDate = DateUtils.getUTCDate(2013, 8, 20);
    final int startRoll1 = 1;
    final int endRoll1 = 5; // 1Y swap with 4 quarterly coupons.
    AnnuityDefinition<CouponIborDefinition> leg1 = AnnuityDefinitionBuilder.annuityIborFromRollDate(referenceDate, startRoll1, endRoll1,
        QUARTERLY_IMM_ADJUSTER, NOTIONAL, USDLIBOR3M, true, USDLIBOR6M.getDayCount(), NYC);
    assertEquals("AnnuityDefinitionBuilder: annuityIborFromRollDate", endRoll1 - startRoll1, leg1.getNumberOfPayments()); // Number of coupons
    for (int loopcpn = 0; loopcpn < leg1.getNumberOfPayments(); loopcpn++) {
      assertEquals("AnnuityDefinitionBuilder: annuityIborFromRollDate", expectedDates[loopcpn], leg1.getNthPayment(loopcpn).getAccrualStartDate());
      assertEquals("AnnuityDefinitionBuilder: annuityIborFromRollDate", expectedDates[loopcpn], leg1.getNthPayment(loopcpn).getFixingPeriodStartDate());
      assertEquals("AnnuityDefinitionBuilder: annuityIborFromRollDate", expectedDates[loopcpn + 1], leg1.getNthPayment(loopcpn).getAccrualEndDate());
      assertEquals("AnnuityDefinitionBuilder: Coupon Ibor Spread", leg1.getNthPayment(loopcpn).getFixingPeriodEndDate(),
          ScheduleCalculator.getAdjustedDate(leg1.getNthPayment(loopcpn).getFixingPeriodStartDate(), USDLIBOR3M, NYC));
      assertEquals("AnnuityDefinitionBuilder: Coupon Ibor Spread", NOTIONAL, -leg1.getNthPayment(loopcpn).getNotional()); // Payer
    }
    final int startRoll2 = 3;
    final int endRoll2 = 11; // 1Y swap with 4 quarterly coupons.
    AnnuityDefinition<CouponIborDefinition> leg2 = AnnuityDefinitionBuilder.annuityIborFromRollDate(referenceDate, startRoll2, endRoll2,
        QUARTERLY_IMM_ADJUSTER, NOTIONAL, USDLIBOR3M, false, USDLIBOR6M.getDayCount(), NYC);
    assertEquals("AnnuityDefinitionBuilder: annuityIborFromRollDate", endRoll2 - startRoll2, leg2.getNumberOfPayments()); // Number of coupons
    for (int loopcpn = 0; loopcpn < leg2.getNumberOfPayments(); loopcpn++) {
      assertEquals("AnnuityDefinitionBuilder: annuityIborFromRollDate", expectedDates[loopcpn + 2], leg2.getNthPayment(loopcpn).getAccrualStartDate());
      assertEquals("AnnuityDefinitionBuilder: annuityIborFromRollDate", expectedDates[loopcpn + 2], leg2.getNthPayment(loopcpn).getFixingPeriodStartDate());
      assertEquals("AnnuityDefinitionBuilder: annuityIborFromRollDate", expectedDates[loopcpn + 3], leg2.getNthPayment(loopcpn).getAccrualEndDate());
      assertEquals("AnnuityDefinitionBuilder: Coupon Ibor Spread", leg2.getNthPayment(loopcpn).getFixingPeriodEndDate(),
          ScheduleCalculator.getAdjustedDate(leg2.getNthPayment(loopcpn).getFixingPeriodStartDate(), USDLIBOR3M, NYC));
      assertEquals("AnnuityDefinitionBuilder: Coupon Ibor Spread", NOTIONAL, leg2.getNthPayment(loopcpn).getNotional()); // Receiver
    }
  }

  @Test
  public void annuityIborSpreadFromStandard() {
    ZonedDateTime settlementDate = DateUtils.getUTCDate(2013, 9, 20);
    ZonedDateTime maturityDate = settlementDate.plusYears(2);
    Period paymentPeriod = Period.ofMonths(6);
    final StubType stub = StubType.SHORT_START;
    AnnuityDefinition<CouponIborSpreadDefinition> leg = AnnuityDefinitionBuilder.annuityIborSpreadFrom(settlementDate, maturityDate, paymentPeriod, NOTIONAL, SPREAD,
        USDLIBOR6M, true, USDLIBOR6M.getBusinessDayConvention(), USDLIBOR6M.isEndOfMonth(), USDLIBOR6M.getDayCount(), NYC, stub);
    ZonedDateTime[] expectedPaymentDates = new ZonedDateTime[] {DateUtils.getUTCDate(2014, 3, 20), DateUtils.getUTCDate(2014, 9, 22),
      DateUtils.getUTCDate(2015, 3, 20), DateUtils.getUTCDate(2015, 9, 21) };
    assertEquals("AnnuityDefinitionBuilder: Coupon Ibor Spread", expectedPaymentDates.length, leg.getNumberOfPayments());
    assertEquals("AnnuityDefinitionBuilder: Coupon Ibor Spread", settlementDate, leg.getNthPayment(0).getAccrualStartDate());
    for (int loopcpn = 0; loopcpn < leg.getNumberOfPayments(); loopcpn++) {
      assertEquals("AnnuityDefinitionBuilder: Coupon Ibor Spread", expectedPaymentDates[loopcpn], leg.getNthPayment(loopcpn).getPaymentDate());
      assertEquals("AnnuityDefinitionBuilder: Coupon Ibor Spread", leg.getNthPayment(loopcpn).getAccrualStartDate(), leg.getNthPayment(loopcpn).getFixingPeriodStartDate());
      assertEquals("AnnuityDefinitionBuilder: Coupon Ibor Spread", leg.getNthPayment(loopcpn).getFixingPeriodEndDate(),
          ScheduleCalculator.getAdjustedDate(leg.getNthPayment(loopcpn).getFixingPeriodStartDate(), USDLIBOR6M, NYC));
      assertEquals("AnnuityDefinitionBuilder: Coupon Ibor Spread", NOTIONAL, -leg.getNthPayment(loopcpn).getNotional()); // Payer
      assertEquals("AnnuityDefinitionBuilder: Coupon Ibor Spread", SPREAD, leg.getNthPayment(loopcpn).getSpread());
    }
  }

  @Test
  public void annuityIborCompoundingFromStandard() {
    ZonedDateTime settlementDate = DateUtils.getUTCDate(2013, 9, 20);
    ZonedDateTime maturityDate = settlementDate.plusYears(2);
    Period paymentPeriod = Period.ofMonths(6);
    final StubType stub = StubType.SHORT_START;
    AnnuityDefinition<CouponIborCompoundingDefinition> leg = AnnuityDefinitionBuilder.annuityIborCompoundingFrom(settlementDate, maturityDate, paymentPeriod, NOTIONAL,
        USDLIBOR3M, stub, true, USDLIBOR3M.getBusinessDayConvention(), USDLIBOR3M.isEndOfMonth(), NYC, stub);
    ZonedDateTime[] expectedPaymentDates = new ZonedDateTime[] {DateUtils.getUTCDate(2014, 3, 20), DateUtils.getUTCDate(2014, 9, 22),
      DateUtils.getUTCDate(2015, 3, 20), DateUtils.getUTCDate(2015, 9, 21) };
    assertEquals("AnnuityDefinitionBuilder: Coupon Ibor Compounding", expectedPaymentDates.length, leg.getNumberOfPayments());
    assertEquals("AnnuityDefinitionBuilder: Coupon Ibor Compounding", settlementDate, leg.getNthPayment(0).getAccrualStartDate());
    for (int loopcpn = 0; loopcpn < leg.getNumberOfPayments(); loopcpn++) {
      assertEquals("AnnuityDefinitionBuilder: Coupon Ibor Compounding", expectedPaymentDates[loopcpn], leg.getNthPayment(loopcpn).getPaymentDate());
      assertEquals("AnnuityDefinitionBuilder: Coupon Ibor Compounding", leg.getNthPayment(loopcpn).getAccrualStartDate(), leg.getNthPayment(loopcpn).getFixingPeriodStartDates()[0]);
      for (int loopsub = 0; loopsub < leg.getNthPayment(loopcpn).getAccrualEndDates().length; loopsub++) {
        assertEquals("AnnuityDefinitionBuilder: Coupon Ibor Compounding", leg.getNthPayment(loopcpn).getFixingPeriodEndDates()[loopsub],
            ScheduleCalculator.getAdjustedDate(leg.getNthPayment(loopcpn).getFixingPeriodStartDates()[loopsub], USDLIBOR3M, NYC));
      }
      assertEquals("AnnuityDefinitionBuilder: Coupon Ibor Compounding", NOTIONAL, -leg.getNthPayment(loopcpn).getNotional()); // Payer
    }
  }

  @Test
  public void annuityIborCompoundingSpreadFromStandard() {
    ZonedDateTime settlementDate = DateUtils.getUTCDate(2013, 9, 20);
    ZonedDateTime maturityDate = settlementDate.plusYears(2);
    Period paymentPeriod = Period.ofMonths(6);
    final StubType stub = StubType.SHORT_START;
    AnnuityDefinition<CouponIborCompoundingSpreadDefinition> leg = AnnuityDefinitionBuilder.annuityIborCompoundingSpreadFrom(settlementDate, maturityDate, paymentPeriod, NOTIONAL, SPREAD,
        USDLIBOR3M, stub, true, USDLIBOR3M.getBusinessDayConvention(), USDLIBOR3M.isEndOfMonth(), NYC, stub);
    ZonedDateTime[] expectedPaymentDates = new ZonedDateTime[] {DateUtils.getUTCDate(2014, 3, 20), DateUtils.getUTCDate(2014, 9, 22),
      DateUtils.getUTCDate(2015, 3, 20), DateUtils.getUTCDate(2015, 9, 21) };
    assertEquals("AnnuityDefinitionBuilder: Coupon Ibor Spread", expectedPaymentDates.length, leg.getNumberOfPayments());
    assertEquals("AnnuityDefinitionBuilder: Coupon Ibor Spread", settlementDate, leg.getNthPayment(0).getAccrualStartDate());
    for (int loopcpn = 0; loopcpn < leg.getNumberOfPayments(); loopcpn++) {
      assertEquals("AnnuityDefinitionBuilder: Coupon Ibor Spread", expectedPaymentDates[loopcpn], leg.getNthPayment(loopcpn).getPaymentDate());
      assertEquals("AnnuityDefinitionBuilder: Coupon Ibor Spread", leg.getNthPayment(loopcpn).getAccrualStartDate(), leg.getNthPayment(loopcpn).getFixingPeriodStartDates()[0]);
      for (int loopsub = 0; loopsub < leg.getNthPayment(loopcpn).getAccrualEndDates().length; loopsub++) {
        assertEquals("AnnuityDefinitionBuilder: Coupon Ibor Spread", leg.getNthPayment(loopcpn).getFixingPeriodEndDates()[loopsub],
            ScheduleCalculator.getAdjustedDate(leg.getNthPayment(loopcpn).getFixingPeriodStartDates()[loopsub], USDLIBOR3M, NYC));
      }
      assertEquals("AnnuityDefinitionBuilder: Coupon Ibor Spread", NOTIONAL, -leg.getNthPayment(loopcpn).getNotional()); // Payer
      assertEquals("AnnuityDefinitionBuilder: Coupon Ibor Spread", SPREAD, leg.getNthPayment(loopcpn).getSpread());
    }
  }

  @Test
  public void annuityIborCompoundingFlatSpreadFromStandard() {
    ZonedDateTime settlementDate = DateUtils.getUTCDate(2013, 9, 20);
    ZonedDateTime maturityDate = settlementDate.plusYears(2);
    Period paymentPeriod = Period.ofMonths(6);
    final StubType stub = StubType.SHORT_START;
    AnnuityDefinition<CouponIborCompoundingFlatSpreadDefinition> leg = AnnuityDefinitionBuilder.annuityIborCompoundingFlatSpreadFrom(settlementDate, maturityDate, paymentPeriod, NOTIONAL, SPREAD,
        USDLIBOR3M, stub, true, USDLIBOR3M.getBusinessDayConvention(), USDLIBOR3M.isEndOfMonth(), NYC, stub);
    ZonedDateTime[] expectedPaymentDates = new ZonedDateTime[] {DateUtils.getUTCDate(2014, 3, 20), DateUtils.getUTCDate(2014, 9, 22),
      DateUtils.getUTCDate(2015, 3, 20), DateUtils.getUTCDate(2015, 9, 21) };
    assertEquals("AnnuityDefinitionBuilder: Coupon Ibor Spread", expectedPaymentDates.length, leg.getNumberOfPayments());
    assertEquals("AnnuityDefinitionBuilder: Coupon Ibor Spread", settlementDate, leg.getNthPayment(0).getAccrualStartDate());
    for (int loopcpn = 0; loopcpn < leg.getNumberOfPayments(); loopcpn++) {
      assertEquals("AnnuityDefinitionBuilder: Coupon Ibor Spread", expectedPaymentDates[loopcpn], leg.getNthPayment(loopcpn).getPaymentDate());
      assertEquals("AnnuityDefinitionBuilder: Coupon Ibor Spread", leg.getNthPayment(loopcpn).getAccrualStartDate(), leg.getNthPayment(loopcpn).getFixingSubperiodStartDates()[0]);
      for (int loopsub = 0; loopsub < leg.getNthPayment(loopcpn).getSubperiodsAccrualStartDates().length; loopsub++) {
        assertEquals("AnnuityDefinitionBuilder: Coupon Ibor Spread", leg.getNthPayment(loopcpn).getFixingSubperiodEndDates()[loopsub],
            ScheduleCalculator.getAdjustedDate(leg.getNthPayment(loopcpn).getFixingSubperiodStartDates()[loopsub], USDLIBOR3M, NYC));
      }
      assertEquals("AnnuityDefinitionBuilder: Coupon Ibor Spread", NOTIONAL, -leg.getNthPayment(loopcpn).getNotional()); // Payer
      assertEquals("AnnuityDefinitionBuilder: Coupon Ibor Spread", SPREAD, leg.getNthPayment(loopcpn).getSpread());
    }
  }

}
