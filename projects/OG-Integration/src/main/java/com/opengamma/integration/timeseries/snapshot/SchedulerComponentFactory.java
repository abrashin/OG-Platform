/**
 * Copyright (C) 2012 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.integration.timeseries.snapshot;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;

import org.joda.beans.BeanBuilder;
import org.joda.beans.BeanDefinition;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;
import org.quartz.Scheduler;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import com.opengamma.component.ComponentInfo;
import com.opengamma.component.ComponentRepository;
import com.opengamma.component.factory.AbstractComponentFactory;
import org.joda.beans.Bean;

/**
 * 
 */
@BeanDefinition
public class SchedulerComponentFactory extends AbstractComponentFactory {
  
  /**
   * The classifier that the factory should publish under.
   */
  @PropertyDefinition(validate = "notNull")
  private String _classifier;
    
  @Override
  public void init(ComponentRepository repo, LinkedHashMap<String, String> configuration) throws Exception {
    ComponentInfo info = new ComponentInfo(Scheduler.class, getClassifier());
    
    Properties quartzProperties = new Properties();
    quartzProperties.setProperty(StdSchedulerFactory.PROP_SCHED_JMX_EXPORT, "true");
    quartzProperties.setProperty(StdSchedulerFactory.PROP_SCHED_SKIP_UPDATE_CHECK, "true");
    
    SchedulerFactoryBean schedulerBean = new SchedulerFactoryBean();
    schedulerBean.setWaitForJobsToCompleteOnShutdown(true);
    schedulerBean.setQuartzProperties(quartzProperties);
        
    repo.registerComponent(info, schedulerBean);
    repo.registerLifecycle(schedulerBean);
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code SchedulerComponentFactory}.
   * @return the meta-bean, not null
   */
  public static SchedulerComponentFactory.Meta meta() {
    return SchedulerComponentFactory.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(SchedulerComponentFactory.Meta.INSTANCE);
  }

  @Override
  public SchedulerComponentFactory.Meta metaBean() {
    return SchedulerComponentFactory.Meta.INSTANCE;
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the classifier that the factory should publish under.
   * @return the value of the property, not null
   */
  public String getClassifier() {
    return _classifier;
  }

  /**
   * Sets the classifier that the factory should publish under.
   * @param classifier  the new value of the property, not null
   */
  public void setClassifier(String classifier) {
    JodaBeanUtils.notNull(classifier, "classifier");
    this._classifier = classifier;
  }

  /**
   * Gets the the {@code classifier} property.
   * @return the property, not null
   */
  public final Property<String> classifier() {
    return metaBean().classifier().createProperty(this);
  }

  //-----------------------------------------------------------------------
  @Override
  public SchedulerComponentFactory clone() {
    return (SchedulerComponentFactory) super.clone();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      SchedulerComponentFactory other = (SchedulerComponentFactory) obj;
      return JodaBeanUtils.equal(getClassifier(), other.getClassifier()) &&
          super.equals(obj);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash += hash * 31 + JodaBeanUtils.hashCode(getClassifier());
    return hash ^ super.hashCode();
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(64);
    buf.append("SchedulerComponentFactory{");
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
    buf.append("classifier").append('=').append(getClassifier()).append(',').append(' ');
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code SchedulerComponentFactory}.
   */
  public static class Meta extends AbstractComponentFactory.Meta {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code classifier} property.
     */
    private final MetaProperty<String> _classifier = DirectMetaProperty.ofReadWrite(
        this, "classifier", SchedulerComponentFactory.class, String.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
        this, (DirectMetaPropertyMap) super.metaPropertyMap(),
        "classifier");

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case -281470431:  // classifier
          return _classifier;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends SchedulerComponentFactory> builder() {
      return new DirectBeanBuilder<SchedulerComponentFactory>(new SchedulerComponentFactory());
    }

    @Override
    public Class<? extends SchedulerComponentFactory> beanType() {
      return SchedulerComponentFactory.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code classifier} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<String> classifier() {
      return _classifier;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case -281470431:  // classifier
          return ((SchedulerComponentFactory) bean).getClassifier();
      }
      return super.propertyGet(bean, propertyName, quiet);
    }

    @Override
    protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
      switch (propertyName.hashCode()) {
        case -281470431:  // classifier
          ((SchedulerComponentFactory) bean).setClassifier((String) newValue);
          return;
      }
      super.propertySet(bean, propertyName, newValue, quiet);
    }

    @Override
    protected void validate(Bean bean) {
      JodaBeanUtils.notNull(((SchedulerComponentFactory) bean)._classifier, "classifier");
      super.validate(bean);
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
