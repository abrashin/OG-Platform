/**
 * Copyright (C) 2011 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.master.holiday;

import java.util.Map;

import org.joda.beans.Bean;
import org.joda.beans.BeanBuilder;
import org.joda.beans.BeanDefinition;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.opengamma.master.AbstractMetaDataRequest;
import com.opengamma.util.PublicSPI;

/**
 * Request for meta-data about the holiday master.
 * <p>
 * This will return meta-data valid for the whole master.
 */
@PublicSPI
@BeanDefinition
public class HolidayMetaDataRequest extends AbstractMetaDataRequest {

  /**
   * Whether to fetch the holiday types meta-data, true by default.
   */
  @PropertyDefinition
  private boolean _holidayTypes = true;

  /**
   * Creates an instance.
   */
  public HolidayMetaDataRequest() {
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code HolidayMetaDataRequest}.
   * @return the meta-bean, not null
   */
  public static HolidayMetaDataRequest.Meta meta() {
    return HolidayMetaDataRequest.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(HolidayMetaDataRequest.Meta.INSTANCE);
  }

  @Override
  public HolidayMetaDataRequest.Meta metaBean() {
    return HolidayMetaDataRequest.Meta.INSTANCE;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets whether to fetch the holiday types meta-data, true by default.
   * @return the value of the property
   */
  public boolean isHolidayTypes() {
    return _holidayTypes;
  }

  /**
   * Sets whether to fetch the holiday types meta-data, true by default.
   * @param holidayTypes  the new value of the property
   */
  public void setHolidayTypes(boolean holidayTypes) {
    this._holidayTypes = holidayTypes;
  }

  /**
   * Gets the the {@code holidayTypes} property.
   * @return the property, not null
   */
  public final Property<Boolean> holidayTypes() {
    return metaBean().holidayTypes().createProperty(this);
  }

  //-----------------------------------------------------------------------
  @Override
  public HolidayMetaDataRequest clone() {
    return (HolidayMetaDataRequest) super.clone();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      HolidayMetaDataRequest other = (HolidayMetaDataRequest) obj;
      return (isHolidayTypes() == other.isHolidayTypes()) &&
          super.equals(obj);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash += hash * 31 + JodaBeanUtils.hashCode(isHolidayTypes());
    return hash ^ super.hashCode();
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(64);
    buf.append("HolidayMetaDataRequest{");
    int len = buf.length();
    toString(buf);
    if (buf.length() > len) {
      buf.setLength(buf.length() - 2);
    }
    buf.append('}');
    return buf.toString();
  }

  @Override
  protected void toString(StringBuilder buf) {
    super.toString(buf);
    buf.append("holidayTypes").append('=').append(isHolidayTypes()).append(',').append(' ');
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code HolidayMetaDataRequest}.
   */
  public static class Meta extends AbstractMetaDataRequest.Meta {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code holidayTypes} property.
     */
    private final MetaProperty<Boolean> _holidayTypes = DirectMetaProperty.ofReadWrite(
        this, "holidayTypes", HolidayMetaDataRequest.class, Boolean.TYPE);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
        this, (DirectMetaPropertyMap) super.metaPropertyMap(),
        "holidayTypes");

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case 15120129:  // holidayTypes
          return _holidayTypes;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends HolidayMetaDataRequest> builder() {
      return new DirectBeanBuilder<HolidayMetaDataRequest>(new HolidayMetaDataRequest());
    }

    @Override
    public Class<? extends HolidayMetaDataRequest> beanType() {
      return HolidayMetaDataRequest.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code holidayTypes} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Boolean> holidayTypes() {
      return _holidayTypes;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case 15120129:  // holidayTypes
          return ((HolidayMetaDataRequest) bean).isHolidayTypes();
      }
      return super.propertyGet(bean, propertyName, quiet);
    }

    @Override
    protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
      switch (propertyName.hashCode()) {
        case 15120129:  // holidayTypes
          ((HolidayMetaDataRequest) bean).setHolidayTypes((Boolean) newValue);
          return;
      }
      super.propertySet(bean, propertyName, newValue, quiet);
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
