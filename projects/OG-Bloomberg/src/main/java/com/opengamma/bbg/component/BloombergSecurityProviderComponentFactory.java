/**
 * Copyright (C) 2012 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.bbg.component;

import java.util.Map;

import org.joda.beans.BeanBuilder;
import org.joda.beans.BeanDefinition;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.opengamma.bbg.referencedata.ReferenceDataProvider;
import com.opengamma.bbg.security.BloombergSecurityProvider;
import com.opengamma.component.ComponentRepository;
import com.opengamma.component.factory.provider.SecurityProviderComponentFactory;
import com.opengamma.financial.timeseries.exchange.DefaultExchangeDataProvider;
import com.opengamma.financial.timeseries.exchange.ExchangeDataProvider;
import org.joda.beans.Bean;

/**
 * Component factory for the Bloomberg security provider.
 */
@BeanDefinition
public class BloombergSecurityProviderComponentFactory extends SecurityProviderComponentFactory {

  /**
   * The Bloomberg reference data.
   */
  @PropertyDefinition(validate = "notNull")
  private ReferenceDataProvider _referenceDataProvider;

  //-------------------------------------------------------------------------
  @Override
  protected BloombergSecurityProvider createSecurityProvider(ComponentRepository repo) {
    ExchangeDataProvider exchangeDataProvider = initExchangeDataProvider(repo);
    return new BloombergSecurityProvider(getReferenceDataProvider(), exchangeDataProvider);
  }

  protected ExchangeDataProvider initExchangeDataProvider(ComponentRepository repo) {
    return DefaultExchangeDataProvider.getInstance();
  }
  
  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code BloombergSecurityProviderComponentFactory}.
   * @return the meta-bean, not null
   */
  public static BloombergSecurityProviderComponentFactory.Meta meta() {
    return BloombergSecurityProviderComponentFactory.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(BloombergSecurityProviderComponentFactory.Meta.INSTANCE);
  }

  @Override
  public BloombergSecurityProviderComponentFactory.Meta metaBean() {
    return BloombergSecurityProviderComponentFactory.Meta.INSTANCE;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the Bloomberg reference data.
   * @return the value of the property, not null
   */
  public ReferenceDataProvider getReferenceDataProvider() {
    return _referenceDataProvider;
  }

  /**
   * Sets the Bloomberg reference data.
   * @param referenceDataProvider  the new value of the property, not null
   */
  public void setReferenceDataProvider(ReferenceDataProvider referenceDataProvider) {
    JodaBeanUtils.notNull(referenceDataProvider, "referenceDataProvider");
    this._referenceDataProvider = referenceDataProvider;
  }

  /**
   * Gets the the {@code referenceDataProvider} property.
   * @return the property, not null
   */
  public final Property<ReferenceDataProvider> referenceDataProvider() {
    return metaBean().referenceDataProvider().createProperty(this);
  }

  //-----------------------------------------------------------------------
  @Override
  public BloombergSecurityProviderComponentFactory clone() {
    return (BloombergSecurityProviderComponentFactory) super.clone();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      BloombergSecurityProviderComponentFactory other = (BloombergSecurityProviderComponentFactory) obj;
      return JodaBeanUtils.equal(getReferenceDataProvider(), other.getReferenceDataProvider()) &&
          super.equals(obj);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash += hash * 31 + JodaBeanUtils.hashCode(getReferenceDataProvider());
    return hash ^ super.hashCode();
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(64);
    buf.append("BloombergSecurityProviderComponentFactory{");
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
    buf.append("referenceDataProvider").append('=').append(getReferenceDataProvider()).append(',').append(' ');
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code BloombergSecurityProviderComponentFactory}.
   */
  public static class Meta extends SecurityProviderComponentFactory.Meta {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code referenceDataProvider} property.
     */
    private final MetaProperty<ReferenceDataProvider> _referenceDataProvider = DirectMetaProperty.ofReadWrite(
        this, "referenceDataProvider", BloombergSecurityProviderComponentFactory.class, ReferenceDataProvider.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
        this, (DirectMetaPropertyMap) super.metaPropertyMap(),
        "referenceDataProvider");

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case -1788671322:  // referenceDataProvider
          return _referenceDataProvider;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends BloombergSecurityProviderComponentFactory> builder() {
      return new DirectBeanBuilder<BloombergSecurityProviderComponentFactory>(new BloombergSecurityProviderComponentFactory());
    }

    @Override
    public Class<? extends BloombergSecurityProviderComponentFactory> beanType() {
      return BloombergSecurityProviderComponentFactory.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code referenceDataProvider} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<ReferenceDataProvider> referenceDataProvider() {
      return _referenceDataProvider;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case -1788671322:  // referenceDataProvider
          return ((BloombergSecurityProviderComponentFactory) bean).getReferenceDataProvider();
      }
      return super.propertyGet(bean, propertyName, quiet);
    }

    @Override
    protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
      switch (propertyName.hashCode()) {
        case -1788671322:  // referenceDataProvider
          ((BloombergSecurityProviderComponentFactory) bean).setReferenceDataProvider((ReferenceDataProvider) newValue);
          return;
      }
      super.propertySet(bean, propertyName, newValue, quiet);
    }

    @Override
    protected void validate(Bean bean) {
      JodaBeanUtils.notNull(((BloombergSecurityProviderComponentFactory) bean)._referenceDataProvider, "referenceDataProvider");
      super.validate(bean);
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
