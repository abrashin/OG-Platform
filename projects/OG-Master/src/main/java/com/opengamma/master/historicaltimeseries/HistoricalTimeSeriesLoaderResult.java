/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.master.historicaltimeseries;

import java.util.Map;

import org.joda.beans.Bean;
import org.joda.beans.BeanBuilder;
import org.joda.beans.BeanDefinition;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectBean;
import org.joda.beans.impl.direct.DirectBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaBean;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.google.common.collect.Maps;
import com.opengamma.id.ExternalId;
import com.opengamma.id.UniqueId;
import com.opengamma.util.PublicSPI;

/**
 * Result from loading one or more time-series.
 * <p>
 * This class is mutable and not thread-safe.
 */
@PublicSPI
@BeanDefinition
public class HistoricalTimeSeriesLoaderResult extends DirectBean {

  /**
   * The unique IDs of the time-series that were obtained.
   * The external ID is the original search key.
   */
  @PropertyDefinition
  private final Map<ExternalId, UniqueId> _resultMap = Maps.newHashMap();

  /**
   * Creates an instance.
   */
  public HistoricalTimeSeriesLoaderResult() {
  }

  /**
   * Creates an instance.
   * 
   * @param result  the map of results, not null
   */
  public HistoricalTimeSeriesLoaderResult(Map<ExternalId, UniqueId> result) {
    getResultMap().putAll(result);
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code HistoricalTimeSeriesLoaderResult}.
   * @return the meta-bean, not null
   */
  public static HistoricalTimeSeriesLoaderResult.Meta meta() {
    return HistoricalTimeSeriesLoaderResult.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(HistoricalTimeSeriesLoaderResult.Meta.INSTANCE);
  }

  @Override
  public HistoricalTimeSeriesLoaderResult.Meta metaBean() {
    return HistoricalTimeSeriesLoaderResult.Meta.INSTANCE;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the unique IDs of the time-series that were obtained.
   * The external ID is the original search key.
   * @return the value of the property
   */
  public Map<ExternalId, UniqueId> getResultMap() {
    return _resultMap;
  }

  /**
   * Sets the unique IDs of the time-series that were obtained.
   * The external ID is the original search key.
   * @param resultMap  the new value of the property
   */
  public void setResultMap(Map<ExternalId, UniqueId> resultMap) {
    this._resultMap.clear();
    this._resultMap.putAll(resultMap);
  }

  /**
   * Gets the the {@code resultMap} property.
   * The external ID is the original search key.
   * @return the property, not null
   */
  public final Property<Map<ExternalId, UniqueId>> resultMap() {
    return metaBean().resultMap().createProperty(this);
  }

  //-----------------------------------------------------------------------
  @Override
  public HistoricalTimeSeriesLoaderResult clone() {
    BeanBuilder<? extends HistoricalTimeSeriesLoaderResult> builder = metaBean().builder();
    for (MetaProperty<?> mp : metaBean().metaPropertyIterable()) {
      if (mp.style().isBuildable()) {
        Object value = mp.get(this);
        if (value instanceof Bean) {
          value = ((Bean) value).clone();
        }
        builder.set(mp.name(), value);
      }
    }
    return builder.build();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      HistoricalTimeSeriesLoaderResult other = (HistoricalTimeSeriesLoaderResult) obj;
      return JodaBeanUtils.equal(getResultMap(), other.getResultMap());
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = getClass().hashCode();
    hash += hash * 31 + JodaBeanUtils.hashCode(getResultMap());
    return hash;
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(64);
    buf.append("HistoricalTimeSeriesLoaderResult{");
    int len = buf.length();
    toString(buf);
    if (buf.length() > len) {
      buf.setLength(buf.length() - 2);
    }
    buf.append('}');
    return buf.toString();
  }

  protected void toString(StringBuilder buf) {
    buf.append("resultMap").append('=').append(getResultMap()).append(',').append(' ');
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code HistoricalTimeSeriesLoaderResult}.
   */
  public static class Meta extends DirectMetaBean {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code resultMap} property.
     */
    @SuppressWarnings({"unchecked", "rawtypes" })
    private final MetaProperty<Map<ExternalId, UniqueId>> _resultMap = DirectMetaProperty.ofReadWrite(
        this, "resultMap", HistoricalTimeSeriesLoaderResult.class, (Class) Map.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
        this, null,
        "resultMap");

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case -1819569153:  // resultMap
          return _resultMap;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends HistoricalTimeSeriesLoaderResult> builder() {
      return new DirectBeanBuilder<HistoricalTimeSeriesLoaderResult>(new HistoricalTimeSeriesLoaderResult());
    }

    @Override
    public Class<? extends HistoricalTimeSeriesLoaderResult> beanType() {
      return HistoricalTimeSeriesLoaderResult.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code resultMap} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Map<ExternalId, UniqueId>> resultMap() {
      return _resultMap;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case -1819569153:  // resultMap
          return ((HistoricalTimeSeriesLoaderResult) bean).getResultMap();
      }
      return super.propertyGet(bean, propertyName, quiet);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
      switch (propertyName.hashCode()) {
        case -1819569153:  // resultMap
          ((HistoricalTimeSeriesLoaderResult) bean).setResultMap((Map<ExternalId, UniqueId>) newValue);
          return;
      }
      super.propertySet(bean, propertyName, newValue, quiet);
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
