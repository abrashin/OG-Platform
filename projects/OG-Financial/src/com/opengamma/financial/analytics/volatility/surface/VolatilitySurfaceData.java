/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.financial.analytics.volatility.surface;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.Validate;

import com.opengamma.util.money.Currency;
import com.opengamma.util.tuple.Pair;

/**
 * Data structure to hold a particular volatility surface's data points.  Note no interpolation or fitting is done in this code.
 * @param <X> Type of the x-data
 * @param <Y> Type of the y-data
 */
public class VolatilitySurfaceData<X, Y> {

  private String _definitionName;
  private String _specificationName;
  private Currency _currency;
  private Map<Pair<X, Y>, Double> _values;
  private X[] _xs;
  private Y[] _ys;;

  public VolatilitySurfaceData(final String definitionName, final String specificationName, final Currency currency,
                               final X[] xs, final Y[] ys, final Map<Pair<X, Y>, Double> values) {
    Validate.notNull(definitionName, "Definition Name");
    Validate.notNull(specificationName, "Specification Name");
    Validate.notNull(currency, "Currency");
    Validate.notNull(ys, "Y axis values");
    Validate.notNull(xs, "X axis values");
    Validate.notNull(values, "Volatility Values Map");
    _definitionName = definitionName;
    _specificationName = specificationName;
    _currency = currency;
    _values = new HashMap<Pair<X, Y>, Double>(values);
    _xs = xs;
    _ys = ys;
  }

  public X[] getXs() {
    return _xs;
  }

  public Y[] getYs() {
    return _ys;
  }

  public Double getVolatility(final X x, final Y y) {
    return _values.get(Pair.of(x, y));
  }

  public Map<Pair<X, Y>, Double> asMap() {
    return _values;
  }

  public String getDefinitionName() {
    return _definitionName;
  }

  public String getSpecificationName() {
    return _specificationName;
  }

  public Currency getCurrency() {
    return _currency;
  }

  @Override
  public boolean equals(final Object o) {
    if (o == null) {
      return false;
    }
    if (!(o instanceof VolatilitySurfaceData)) {
      return false;
    }
    final VolatilitySurfaceData<?, ?> other = (VolatilitySurfaceData<?, ?>) o;
    return getDefinitionName().equals(other.getDefinitionName()) &&
           getSpecificationName().equals(other.getSpecificationName()) &&
           getCurrency().equals(other.getCurrency()) &&
           Arrays.equals(getXs(), other.getXs()) &&
           Arrays.equals(getYs(), other.getYs()) &&
           _values.equals(other._values);
  }

  @Override
  public int hashCode() {
    return getDefinitionName().hashCode() * getSpecificationName().hashCode() * getCurrency().hashCode();
  }
}
