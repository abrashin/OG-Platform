/**
 * Copyright (C) 2013 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.financial.analytics.model.credit.isda.cds;

import java.util.Collections;
import java.util.Set;

import org.threeten.bp.Period;
import org.threeten.bp.ZonedDateTime;

import com.opengamma.analytics.financial.credit.creditdefaultswap.definition.vanilla.CreditDefaultSwapDefinition;
import com.opengamma.analytics.financial.credit.isdastandardmodel.CDSAnalytic;
import com.opengamma.analytics.financial.credit.isdastandardmodel.CDSAnalyticFactory;
import com.opengamma.analytics.financial.credit.isdastandardmodel.HedgeRatioCalculator;
import com.opengamma.analytics.financial.credit.isdastandardmodel.ISDACompliantCreditCurve;
import com.opengamma.analytics.financial.credit.isdastandardmodel.ISDACompliantYieldCurve;
import com.opengamma.analytics.financial.credit.isdastandardmodel.fastcalibration.CreditCurveCalibrator;
import com.opengamma.analytics.math.matrix.DoubleMatrix1D;
import com.opengamma.engine.ComputationTarget;
import com.opengamma.engine.function.FunctionInputs;
import com.opengamma.engine.value.ComputedValue;
import com.opengamma.engine.value.ValueProperties;
import com.opengamma.engine.value.ValueRequirementNames;
import com.opengamma.engine.value.ValueSpecification;
import com.opengamma.financial.analytics.TenorLabelledMatrix1D;
import com.opengamma.util.time.Tenor;

/**
 * 
 */
public class StandardVanillaHedgeNotionalCDSFunction extends StandardVanillaCDSFunction {

  private static HedgeRatioCalculator s_hedgeRatioCalculator = new HedgeRatioCalculator();

  public StandardVanillaHedgeNotionalCDSFunction() {
    super(ValueRequirementNames.HEDGE_NOTIONAL);
  }

  @Override
  protected Set<ComputedValue> getComputedValue(final CreditDefaultSwapDefinition definition,
                                                final ISDACompliantYieldCurve yieldCurve,
                                                final ZonedDateTime[] times,
                                                final double[] marketSpreads,
                                                final ZonedDateTime valuationDate,
                                                final ComputationTarget target,
                                                final ValueProperties properties,
                                                final FunctionInputs inputs,
                                                ISDACompliantCreditCurve hazardCurve, CDSAnalytic analytic) {
    final TenorLabelledMatrix1D hedgeNotional = getHedgeNotional(definition, yieldCurve, times, analytic, marketSpreads);
    final ValueSpecification spec = new ValueSpecification(ValueRequirementNames.HEDGE_NOTIONAL, target.toSpecification(), properties);
    return Collections.singleton(new ComputedValue(spec, hedgeNotional));
  }

  public static TenorLabelledMatrix1D getHedgeNotional(CreditDefaultSwapDefinition definition, ISDACompliantYieldCurve yieldCurve,
                                                        ZonedDateTime[] times, CDSAnalytic analytic, double[] spreads) {
    final CDSAnalyticFactory analyticFactory = new CDSAnalyticFactory(definition.getRecoveryRate(), definition.getCouponFrequency().getPeriod())
        .with(definition.getBusinessDayAdjustmentConvention())
        .with(definition.getCalendar()).with(definition.getStubType())
        .withAccrualDCC(definition.getDayCountFractionConvention());

    double coupon = getCoupon(definition);
    Period[] periods = new Period[times.length];
    Tenor[] tenors = new Tenor[periods.length];
    double[] hedgeCoupons = new double[periods.length];
    for (int i = 0; i < times.length; i++) {
      periods[i] = Period.between(definition.getStartDate().toLocalDate(), times[i].toLocalDate()).withDays(0);
      tenors[i] = Tenor.of(periods[i]);
      hedgeCoupons[i] = coupon;
    }
    CDSAnalytic[] buckets = analyticFactory.makeIMMCDS(definition.getStartDate().toLocalDate(), periods);

    // Can't use flat hazard curve here as that doesn't make much sense - hence generate full hazard curve and use that
    ISDACompliantCreditCurve hazardCurve2 = new CreditCurveCalibrator(buckets, yieldCurve).calibrate(spreads);

    DoubleMatrix1D hedgeNotionals = s_hedgeRatioCalculator.getHedgeRatios(analytic, coupon, buckets, hedgeCoupons, hazardCurve2, yieldCurve);
    double[] notionals = hedgeNotionals.getData();
    //TODO: Buy sell protection sign effect?
    for (int i = 0; i < notionals.length; i++) {
      notionals[i] = notionals[i] * definition.getNotional();
    }
    final TenorLabelledMatrix1D labeledHedgeNotionals = new TenorLabelledMatrix1D(tenors, notionals);
    return labeledHedgeNotionals;
  }

  @Override
  protected ValueProperties.Builder getCommonResultProperties() {
    return createValueProperties();
  }

  @Override
  protected boolean labelResultWithCurrency() {
    return true;
  }
}
