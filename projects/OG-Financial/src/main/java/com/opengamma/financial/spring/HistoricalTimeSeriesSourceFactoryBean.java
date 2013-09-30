/**
 * Copyright (C) 2009 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.financial.spring;

import java.util.Map;

import net.sf.ehcache.CacheManager;

import org.joda.beans.BeanBuilder;
import org.joda.beans.BeanDefinition;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.opengamma.core.historicaltimeseries.HistoricalTimeSeriesSource;
import com.opengamma.core.historicaltimeseries.impl.EHCachingHistoricalTimeSeriesSource;
import com.opengamma.master.historicaltimeseries.HistoricalTimeSeriesMaster;
import com.opengamma.master.historicaltimeseries.HistoricalTimeSeriesResolver;
import com.opengamma.master.historicaltimeseries.impl.MasterHistoricalTimeSeriesSource;
import com.opengamma.util.spring.SpringFactoryBean;
import org.joda.beans.Bean;

/**
 * Spring factory bean to create the HTS source.
 */
@BeanDefinition
public class HistoricalTimeSeriesSourceFactoryBean extends SpringFactoryBean<HistoricalTimeSeriesSource> {

  /**
   * The HTS master.
   */
  @PropertyDefinition
  private HistoricalTimeSeriesMaster _historicalTimeSeriesMaster;
  /**
   * The HTS resolver.
   */
  @PropertyDefinition
  private HistoricalTimeSeriesResolver _historicalTimeSeriesResolver;
  /**
   * The cache manager.
   */
  @PropertyDefinition
  private CacheManager _cacheManager;

  /**
   * Creates an instance.
   */
  public HistoricalTimeSeriesSourceFactoryBean() {
    super(HistoricalTimeSeriesSource.class);
  }

  //-------------------------------------------------------------------------
  @Override
  protected HistoricalTimeSeriesSource createObject() {
    HistoricalTimeSeriesSource source = new MasterHistoricalTimeSeriesSource(getHistoricalTimeSeriesMaster(), getHistoricalTimeSeriesResolver());
    if (getCacheManager() != null) {
      source = new EHCachingHistoricalTimeSeriesSource(source, getCacheManager());
    }
    return source;
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code HistoricalTimeSeriesSourceFactoryBean}.
   * @return the meta-bean, not null
   */
  public static HistoricalTimeSeriesSourceFactoryBean.Meta meta() {
    return HistoricalTimeSeriesSourceFactoryBean.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(HistoricalTimeSeriesSourceFactoryBean.Meta.INSTANCE);
  }

  @Override
  public HistoricalTimeSeriesSourceFactoryBean.Meta metaBean() {
    return HistoricalTimeSeriesSourceFactoryBean.Meta.INSTANCE;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the HTS master.
   * @return the value of the property
   */
  public HistoricalTimeSeriesMaster getHistoricalTimeSeriesMaster() {
    return _historicalTimeSeriesMaster;
  }

  /**
   * Sets the HTS master.
   * @param historicalTimeSeriesMaster  the new value of the property
   */
  public void setHistoricalTimeSeriesMaster(HistoricalTimeSeriesMaster historicalTimeSeriesMaster) {
    this._historicalTimeSeriesMaster = historicalTimeSeriesMaster;
  }

  /**
   * Gets the the {@code historicalTimeSeriesMaster} property.
   * @return the property, not null
   */
  public final Property<HistoricalTimeSeriesMaster> historicalTimeSeriesMaster() {
    return metaBean().historicalTimeSeriesMaster().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the HTS resolver.
   * @return the value of the property
   */
  public HistoricalTimeSeriesResolver getHistoricalTimeSeriesResolver() {
    return _historicalTimeSeriesResolver;
  }

  /**
   * Sets the HTS resolver.
   * @param historicalTimeSeriesResolver  the new value of the property
   */
  public void setHistoricalTimeSeriesResolver(HistoricalTimeSeriesResolver historicalTimeSeriesResolver) {
    this._historicalTimeSeriesResolver = historicalTimeSeriesResolver;
  }

  /**
   * Gets the the {@code historicalTimeSeriesResolver} property.
   * @return the property, not null
   */
  public final Property<HistoricalTimeSeriesResolver> historicalTimeSeriesResolver() {
    return metaBean().historicalTimeSeriesResolver().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the cache manager.
   * @return the value of the property
   */
  public CacheManager getCacheManager() {
    return _cacheManager;
  }

  /**
   * Sets the cache manager.
   * @param cacheManager  the new value of the property
   */
  public void setCacheManager(CacheManager cacheManager) {
    this._cacheManager = cacheManager;
  }

  /**
   * Gets the the {@code cacheManager} property.
   * @return the property, not null
   */
  public final Property<CacheManager> cacheManager() {
    return metaBean().cacheManager().createProperty(this);
  }

  //-----------------------------------------------------------------------
  @Override
  public HistoricalTimeSeriesSourceFactoryBean clone() {
    return (HistoricalTimeSeriesSourceFactoryBean) super.clone();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      HistoricalTimeSeriesSourceFactoryBean other = (HistoricalTimeSeriesSourceFactoryBean) obj;
      return JodaBeanUtils.equal(getHistoricalTimeSeriesMaster(), other.getHistoricalTimeSeriesMaster()) &&
          JodaBeanUtils.equal(getHistoricalTimeSeriesResolver(), other.getHistoricalTimeSeriesResolver()) &&
          JodaBeanUtils.equal(getCacheManager(), other.getCacheManager()) &&
          super.equals(obj);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash += hash * 31 + JodaBeanUtils.hashCode(getHistoricalTimeSeriesMaster());
    hash += hash * 31 + JodaBeanUtils.hashCode(getHistoricalTimeSeriesResolver());
    hash += hash * 31 + JodaBeanUtils.hashCode(getCacheManager());
    return hash ^ super.hashCode();
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(128);
    buf.append("HistoricalTimeSeriesSourceFactoryBean{");
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
    buf.append("historicalTimeSeriesMaster").append('=').append(getHistoricalTimeSeriesMaster()).append(',').append(' ');
    buf.append("historicalTimeSeriesResolver").append('=').append(getHistoricalTimeSeriesResolver()).append(',').append(' ');
    buf.append("cacheManager").append('=').append(getCacheManager()).append(',').append(' ');
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code HistoricalTimeSeriesSourceFactoryBean}.
   */
  public static class Meta extends SpringFactoryBean.Meta<HistoricalTimeSeriesSource> {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code historicalTimeSeriesMaster} property.
     */
    private final MetaProperty<HistoricalTimeSeriesMaster> _historicalTimeSeriesMaster = DirectMetaProperty.ofReadWrite(
        this, "historicalTimeSeriesMaster", HistoricalTimeSeriesSourceFactoryBean.class, HistoricalTimeSeriesMaster.class);
    /**
     * The meta-property for the {@code historicalTimeSeriesResolver} property.
     */
    private final MetaProperty<HistoricalTimeSeriesResolver> _historicalTimeSeriesResolver = DirectMetaProperty.ofReadWrite(
        this, "historicalTimeSeriesResolver", HistoricalTimeSeriesSourceFactoryBean.class, HistoricalTimeSeriesResolver.class);
    /**
     * The meta-property for the {@code cacheManager} property.
     */
    private final MetaProperty<CacheManager> _cacheManager = DirectMetaProperty.ofReadWrite(
        this, "cacheManager", HistoricalTimeSeriesSourceFactoryBean.class, CacheManager.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
        this, (DirectMetaPropertyMap) super.metaPropertyMap(),
        "historicalTimeSeriesMaster",
        "historicalTimeSeriesResolver",
        "cacheManager");

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case 173967376:  // historicalTimeSeriesMaster
          return _historicalTimeSeriesMaster;
        case -946313676:  // historicalTimeSeriesResolver
          return _historicalTimeSeriesResolver;
        case -1452875317:  // cacheManager
          return _cacheManager;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends HistoricalTimeSeriesSourceFactoryBean> builder() {
      return new DirectBeanBuilder<HistoricalTimeSeriesSourceFactoryBean>(new HistoricalTimeSeriesSourceFactoryBean());
    }

    @Override
    public Class<? extends HistoricalTimeSeriesSourceFactoryBean> beanType() {
      return HistoricalTimeSeriesSourceFactoryBean.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code historicalTimeSeriesMaster} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<HistoricalTimeSeriesMaster> historicalTimeSeriesMaster() {
      return _historicalTimeSeriesMaster;
    }

    /**
     * The meta-property for the {@code historicalTimeSeriesResolver} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<HistoricalTimeSeriesResolver> historicalTimeSeriesResolver() {
      return _historicalTimeSeriesResolver;
    }

    /**
     * The meta-property for the {@code cacheManager} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<CacheManager> cacheManager() {
      return _cacheManager;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case 173967376:  // historicalTimeSeriesMaster
          return ((HistoricalTimeSeriesSourceFactoryBean) bean).getHistoricalTimeSeriesMaster();
        case -946313676:  // historicalTimeSeriesResolver
          return ((HistoricalTimeSeriesSourceFactoryBean) bean).getHistoricalTimeSeriesResolver();
        case -1452875317:  // cacheManager
          return ((HistoricalTimeSeriesSourceFactoryBean) bean).getCacheManager();
      }
      return super.propertyGet(bean, propertyName, quiet);
    }

    @Override
    protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
      switch (propertyName.hashCode()) {
        case 173967376:  // historicalTimeSeriesMaster
          ((HistoricalTimeSeriesSourceFactoryBean) bean).setHistoricalTimeSeriesMaster((HistoricalTimeSeriesMaster) newValue);
          return;
        case -946313676:  // historicalTimeSeriesResolver
          ((HistoricalTimeSeriesSourceFactoryBean) bean).setHistoricalTimeSeriesResolver((HistoricalTimeSeriesResolver) newValue);
          return;
        case -1452875317:  // cacheManager
          ((HistoricalTimeSeriesSourceFactoryBean) bean).setCacheManager((CacheManager) newValue);
          return;
      }
      super.propertySet(bean, propertyName, newValue, quiet);
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
