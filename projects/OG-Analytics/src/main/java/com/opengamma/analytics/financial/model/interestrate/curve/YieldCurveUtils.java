/**
 * Copyright (C) 2013 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.analytics.financial.model.interestrate.curve;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;

import com.opengamma.analytics.ShiftType;
import com.opengamma.analytics.math.curve.AddCurveSpreadFunction;
import com.opengamma.analytics.math.curve.ConstantDoublesCurve;
import com.opengamma.analytics.math.curve.CurveShiftFunctionFactory;
import com.opengamma.analytics.math.curve.CurveSpreadFunction;
import com.opengamma.analytics.math.curve.DoublesCurve;
import com.opengamma.analytics.math.curve.InterpolatedDoublesCurve;
import com.opengamma.analytics.math.curve.MultiplyCurveSpreadFunction;
import com.opengamma.analytics.math.curve.SpreadDoublesCurve;
import com.opengamma.analytics.math.interpolation.Interpolator1DFactory;
import com.opengamma.util.ArgumentChecker;
import com.opengamma.util.tuple.DoublesPair;

/**
 * Class containing utility methods for manipulating yield curves.
 */
public class YieldCurveUtils {
  /** Curve spread function that adds two curves */
  private static final CurveSpreadFunction ADD_SPREAD = new AddCurveSpreadFunction();
  /** Curve spread function that multiplies one curve by another */
  private static final CurveSpreadFunction MULTIPLY_SPREAD = new MultiplyCurveSpreadFunction();

  /**
   * Shifts a curve by a constant amount over all tenors. If the {@link ShiftType} is
   * absolute, the shift is added to the curve i.e. a shift of 0.0001 results in all
   * yields on the curve having one basis point added. If it is relative, then all yields on
   * are the curve are multiplied by the shift amount i.e. a relative shift of 0.01 will
   * result in all points on the curve being shifted upwards by 1% of the yield.
   * <p>
   * The original curve is unchanged and a new curve is returned.
   * @param curve The original curve, not null
   * @param shift The shift
   * @param shiftType The shift type, not null
   * @return A new curve with all values shifted by a constant amount
   */
  public static YieldCurve withParallelShift(final YieldCurve curve, final double shift, final ShiftType shiftType) {
    ArgumentChecker.notNull(curve, "curve");
    ArgumentChecker.notNull(shiftType, "shift type");
    final String newName = curve.getName() + "WithParallelShift"; //TODO better name
    final DoublesCurve underlyingCurve = curve.getCurve();
    switch (shiftType) {
      case ABSOLUTE:
        return new YieldCurve(newName, SpreadDoublesCurve.from(ADD_SPREAD, newName, underlyingCurve, ConstantDoublesCurve.from(shift)));
      case RELATIVE:
        return new YieldCurve(newName, SpreadDoublesCurve.from(MULTIPLY_SPREAD, newName, underlyingCurve, ConstantDoublesCurve.from(1 + shift)));
      default:
        throw new IllegalArgumentException("Cannot handle curve shift type " + shiftType + " for parallel shifts");
    }
  }

  /**
   * Performs bucketed shifts on curves. The buckets need not be continuous; if they are not,
   * then the curve is unchanged between the two times. The shifts include the lower time but
   * exclude the upper, and are applied as a step function (i.e. constant over the bucket).
   * The units of time of the buckets are years.
   * <p>
   * If the {@link ShiftType} is absolute, the shift is added to the curve; a shift of 0.0001
   * from one year to two years results in the curve being shifted upwards by one basis point
   * from the one year point to the two year point. If this shift is relative, the yields are
   * multiplied by the shift amount.
   * <p>
   * The original curve is unchanged and a new curve is returned.
   * @param curve The original curve, not null
   * @param buckets The buckets, not null
   * @param shifts The shifts, not null. There must be one shift per bucket.
   * @param shiftType The shift type, not null
   * @return A new curve with bucketed shifts applied
   */
  public static YieldCurve withBucketedShifts(final YieldCurve curve, final List<DoublesPair> buckets, final List<Double> shifts,
      final ShiftType shiftType) {
    ArgumentChecker.notNull(curve, "curve");
    ArgumentChecker.noNulls(buckets, "buckets");
    ArgumentChecker.noNulls(shifts, "shifts");
    ArgumentChecker.isTrue(buckets.size() == shifts.size(), "must have one shift per bucket");
    ArgumentChecker.notNull(shiftType, "shift type");
    final String newName = curve.getName() + "WithBucketedShifts"; //TODO
    final DoublesCurve underlyingCurve = curve.getCurve();
    final List<DoublesPair> stepCurvePoints = new ArrayList<>();
    final Iterator<DoublesPair> iter = buckets.iterator();
    DoublesPair oldPair = iter.next();
    switch (shiftType) {
      case ABSOLUTE: {
        if (Double.compare(0, oldPair.getFirstDouble()) != 0) {
          stepCurvePoints.add(DoublesPair.of(0., 0.));
        }
        stepCurvePoints.add(oldPair);
        while (iter.hasNext()) {
          final DoublesPair pair = iter.next();
          if (Double.compare(oldPair.getSecondDouble(), pair.getFirstDouble()) != 0) {
            stepCurvePoints.add(DoublesPair.of(pair.getFirstDouble(), 0.));
          }
          stepCurvePoints.add(pair);
          oldPair = pair;
        }
        final DoublesCurve spreadCurve = InterpolatedDoublesCurve.from(stepCurvePoints, Interpolator1DFactory.STEP_INSTANCE);
        return new YieldCurve(newName, SpreadDoublesCurve.from(ADD_SPREAD, newName, underlyingCurve, spreadCurve));
      } case RELATIVE: {
        if (Double.compare(0, oldPair.getFirstDouble()) != 0) {
          stepCurvePoints.add(DoublesPair.of(0., 1.));
        }
        stepCurvePoints.add(oldPair);
        while (iter.hasNext()) {
          final DoublesPair pair = iter.next();
          if (Double.compare(oldPair.getSecondDouble(), pair.getFirstDouble()) != 0) {
            stepCurvePoints.add(DoublesPair.of(pair.getFirstDouble(), 1.));
          }
          stepCurvePoints.add(DoublesPair.of(pair.getFirstDouble(), pair.getSecondDouble() + 1.));
          oldPair = pair;
        }
        final DoublesCurve spreadCurve = InterpolatedDoublesCurve.from(stepCurvePoints, Interpolator1DFactory.STEP_INSTANCE);
        return new YieldCurve(newName, SpreadDoublesCurve.from(MULTIPLY_SPREAD, newName, underlyingCurve, spreadCurve));
      } default:
        throw new IllegalArgumentException("Cannot handle curve shift type " + shiftType + " for bucketed shifts");
    }
  }

  /**
   * Performs point shifts on curves. The units of time are years.
   * <p>
   * If the {@link ShiftType} is absolute, the shift is added to the curve; a shift of 0.0001
   * results in the curve being shifted upwards by one basis point at the time point. If this
   * shift is relative, the yields are multiplied by the shift amount.
   * <p>
   * The original curve is unchanged and a new curve is returned.
   * <p>
   * This method only works for interpolated yield curves.
   * @param curve The original curve, not null
   * @param t The times, not null
   * @param shifts The shifts, not null. There must be one shift per time.
   * @param shiftType The shift type, not null
   * @return A new curve with point shifts applied
   * @throws IllegalArgumentException if the curve is not an interpolated curve
   */
  public static YieldCurve withPointShifts(final YieldCurve curve, final List<Double> t, final List<Double> shifts,
      final ShiftType shiftType) {
    ArgumentChecker.notNull(curve, "curve");
    ArgumentChecker.noNulls(t, "times");
    ArgumentChecker.noNulls(shifts, "shifts");
    ArgumentChecker.isTrue(t.size() == shifts.size(), "must have one shift per point");
    final String newName = curve.getName() + "WithPointShifts";
    final int n = t.size();
    final double[] tArray = ArrayUtils.toPrimitive(t.toArray(new Double[n]));
    final double[] shiftArray = ArrayUtils.toPrimitive(shifts.toArray(new Double[n]));
    switch (shiftType) {
      case ABSOLUTE: {
        return new YieldCurve(newName, CurveShiftFunctionFactory.getShiftedCurve(curve.getCurve(), tArray, shiftArray, newName));
      } case RELATIVE: {
        final InterpolatedDoublesCurve interpolatedCurve = (InterpolatedDoublesCurve) curve.getCurve();
        return new YieldCurve(newName, getRelativeShiftedCurve(interpolatedCurve, shiftArray, shiftArray, newName));
      } default:
        throw new IllegalArgumentException("Cannot handle curve shift type " + shiftType + " for point shifts");
    }
  }

  /**
   * Performs relative shifts on a yield curve
   * @param curve The curve
   * @param t The times
   * @param yShift The shifts
   * @param newName The new curve name
   * @return A shifted curve
   */
  //TODO this should be moved into a separate CurveShiftFunction
  private static InterpolatedDoublesCurve getRelativeShiftedCurve(final InterpolatedDoublesCurve curve, final double[] t, final double[] yShift,
      final String newName) {
    ArgumentChecker.notNull(curve, "curve");
    ArgumentChecker.notNull(t, "x shifts");
    ArgumentChecker.notNull(yShift, "y shifts");
    ArgumentChecker.isTrue(t.length == yShift.length, "number of x shifts {} must equal number of y shifts {}", t.length, yShift.length);
    if (t.length == 0) {
      return InterpolatedDoublesCurve.from(curve.getXDataAsPrimitive(), curve.getYDataAsPrimitive(), curve.getInterpolator(), newName);
    }
    final List<Double> newX = new ArrayList<>(Arrays.asList(curve.getXData()));
    final List<Double> newY = new ArrayList<>(Arrays.asList(curve.getYData()));
    for (int i = 0; i < t.length; i++) {
      final int index = newX.indexOf(t[i]);
      if (index >= 0) {
        newY.set(index, newY.get(index) * (1 + yShift[i]));
      } else {
        newX.add(t[i]);
        newY.add(curve.getYValue(t[i]) * (1 + yShift[i]));
      }
    }
    return InterpolatedDoublesCurve.from(newX, newY, curve.getInterpolator(), newName);
  }

}