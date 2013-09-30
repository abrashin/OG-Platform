/**
 * Copyright (C) 2012 - present by OpenGamma Inc. and the OpenGamma group of companies
 *
 * Please see distribution for license.
 */
package com.opengamma.component.factory.engine;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import org.joda.beans.BeanBuilder;
import org.joda.beans.BeanDefinition;
import org.joda.beans.JodaBeanUtils;
import org.joda.beans.MetaProperty;
import org.joda.beans.Property;
import org.joda.beans.PropertyDefinition;
import org.joda.beans.impl.direct.DirectBeanBuilder;
import org.joda.beans.impl.direct.DirectMetaProperty;
import org.joda.beans.impl.direct.DirectMetaPropertyMap;

import com.opengamma.component.ComponentInfo;
import com.opengamma.component.ComponentRepository;
import com.opengamma.component.factory.AbstractComponentFactory;
import com.opengamma.component.factory.ComponentInfoAttributes;
import com.opengamma.engine.function.blacklist.DataFunctionBlacklistProviderResource;
import com.opengamma.engine.function.blacklist.DataManageableFunctionBlacklistProviderResource;
import com.opengamma.engine.function.blacklist.FunctionBlacklistProvider;
import com.opengamma.engine.function.blacklist.InMemoryFunctionBlacklistProvider;
import com.opengamma.engine.function.blacklist.ManageableFunctionBlacklistProvider;
import com.opengamma.engine.function.blacklist.RemoteFunctionBlacklistProvider;
import com.opengamma.engine.function.blacklist.RemoteManageableFunctionBlacklistProvider;
import com.opengamma.util.fudgemsg.OpenGammaFudgeContext;
import com.opengamma.util.jms.JmsConnector;
import org.joda.beans.Bean;

/**
 * Component factory for an in-memory function blacklist provider.
 */
@BeanDefinition
public class InMemoryFunctionBlacklistProviderComponentFactory extends AbstractComponentFactory {

  /**
   * The classifier that the factory should publish under.
   */
  @PropertyDefinition(validate = "notNull")
  private String _classifier;
  /**
   * The flag determining whether the component should be published by REST (default true).
   */
  @PropertyDefinition
  private boolean _publishRest = true;
  /**
   * The JMS connector.
   */
  @PropertyDefinition
  private JmsConnector _jmsConnector;
  /**
   * The scheduled executor to use for housekeeping operations
   */
  @PropertyDefinition
  private ScheduledExecutorService _executor = Executors.newSingleThreadScheduledExecutor();

  @Override
  public void init(final ComponentRepository repo, final LinkedHashMap<String, String> configuration) {
    final ManageableFunctionBlacklistProvider provider = new InMemoryFunctionBlacklistProvider(getExecutor());
    final ComponentInfo infoRO = new ComponentInfo(FunctionBlacklistProvider.class, getClassifier());
    infoRO.addAttribute(ComponentInfoAttributes.LEVEL, 1);
    infoRO.addAttribute(ComponentInfoAttributes.REMOTE_CLIENT_JAVA, RemoteFunctionBlacklistProvider.class);
    repo.registerComponent(infoRO, provider);
    
    final ComponentInfo infoMng = new ComponentInfo(ManageableFunctionBlacklistProvider.class, getClassifier());
    infoMng.addAttribute(ComponentInfoAttributes.LEVEL, 1);
    infoMng.addAttribute(ComponentInfoAttributes.REMOTE_CLIENT_JAVA, RemoteManageableFunctionBlacklistProvider.class);
    repo.registerComponent(infoMng, provider);
    if (isPublishRest()) {
      repo.getRestComponents().publish(infoRO, new DataFunctionBlacklistProviderResource(provider, OpenGammaFudgeContext.getInstance(), getJmsConnector()));
      repo.getRestComponents().publish(infoMng, new DataManageableFunctionBlacklistProviderResource(provider, OpenGammaFudgeContext.getInstance(), getJmsConnector()));
    }
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code InMemoryFunctionBlacklistProviderComponentFactory}.
   * @return the meta-bean, not null
   */
  public static InMemoryFunctionBlacklistProviderComponentFactory.Meta meta() {
    return InMemoryFunctionBlacklistProviderComponentFactory.Meta.INSTANCE;
  }

  static {
    JodaBeanUtils.registerMetaBean(InMemoryFunctionBlacklistProviderComponentFactory.Meta.INSTANCE);
  }

  @Override
  public InMemoryFunctionBlacklistProviderComponentFactory.Meta metaBean() {
    return InMemoryFunctionBlacklistProviderComponentFactory.Meta.INSTANCE;
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
  /**
   * Gets the flag determining whether the component should be published by REST (default true).
   * @return the value of the property
   */
  public boolean isPublishRest() {
    return _publishRest;
  }

  /**
   * Sets the flag determining whether the component should be published by REST (default true).
   * @param publishRest  the new value of the property
   */
  public void setPublishRest(boolean publishRest) {
    this._publishRest = publishRest;
  }

  /**
   * Gets the the {@code publishRest} property.
   * @return the property, not null
   */
  public final Property<Boolean> publishRest() {
    return metaBean().publishRest().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the JMS connector.
   * @return the value of the property
   */
  public JmsConnector getJmsConnector() {
    return _jmsConnector;
  }

  /**
   * Sets the JMS connector.
   * @param jmsConnector  the new value of the property
   */
  public void setJmsConnector(JmsConnector jmsConnector) {
    this._jmsConnector = jmsConnector;
  }

  /**
   * Gets the the {@code jmsConnector} property.
   * @return the property, not null
   */
  public final Property<JmsConnector> jmsConnector() {
    return metaBean().jmsConnector().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the scheduled executor to use for housekeeping operations
   * @return the value of the property
   */
  public ScheduledExecutorService getExecutor() {
    return _executor;
  }

  /**
   * Sets the scheduled executor to use for housekeeping operations
   * @param executor  the new value of the property
   */
  public void setExecutor(ScheduledExecutorService executor) {
    this._executor = executor;
  }

  /**
   * Gets the the {@code executor} property.
   * @return the property, not null
   */
  public final Property<ScheduledExecutorService> executor() {
    return metaBean().executor().createProperty(this);
  }

  //-----------------------------------------------------------------------
  @Override
  public InMemoryFunctionBlacklistProviderComponentFactory clone() {
    return (InMemoryFunctionBlacklistProviderComponentFactory) super.clone();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      InMemoryFunctionBlacklistProviderComponentFactory other = (InMemoryFunctionBlacklistProviderComponentFactory) obj;
      return JodaBeanUtils.equal(getClassifier(), other.getClassifier()) &&
          (isPublishRest() == other.isPublishRest()) &&
          JodaBeanUtils.equal(getJmsConnector(), other.getJmsConnector()) &&
          JodaBeanUtils.equal(getExecutor(), other.getExecutor()) &&
          super.equals(obj);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash += hash * 31 + JodaBeanUtils.hashCode(getClassifier());
    hash += hash * 31 + JodaBeanUtils.hashCode(isPublishRest());
    hash += hash * 31 + JodaBeanUtils.hashCode(getJmsConnector());
    hash += hash * 31 + JodaBeanUtils.hashCode(getExecutor());
    return hash ^ super.hashCode();
  }

  @Override
  public String toString() {
    StringBuilder buf = new StringBuilder(160);
    buf.append("InMemoryFunctionBlacklistProviderComponentFactory{");
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
    buf.append("publishRest").append('=').append(isPublishRest()).append(',').append(' ');
    buf.append("jmsConnector").append('=').append(getJmsConnector()).append(',').append(' ');
    buf.append("executor").append('=').append(getExecutor()).append(',').append(' ');
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code InMemoryFunctionBlacklistProviderComponentFactory}.
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
        this, "classifier", InMemoryFunctionBlacklistProviderComponentFactory.class, String.class);
    /**
     * The meta-property for the {@code publishRest} property.
     */
    private final MetaProperty<Boolean> _publishRest = DirectMetaProperty.ofReadWrite(
        this, "publishRest", InMemoryFunctionBlacklistProviderComponentFactory.class, Boolean.TYPE);
    /**
     * The meta-property for the {@code jmsConnector} property.
     */
    private final MetaProperty<JmsConnector> _jmsConnector = DirectMetaProperty.ofReadWrite(
        this, "jmsConnector", InMemoryFunctionBlacklistProviderComponentFactory.class, JmsConnector.class);
    /**
     * The meta-property for the {@code executor} property.
     */
    private final MetaProperty<ScheduledExecutorService> _executor = DirectMetaProperty.ofReadWrite(
        this, "executor", InMemoryFunctionBlacklistProviderComponentFactory.class, ScheduledExecutorService.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
        this, (DirectMetaPropertyMap) super.metaPropertyMap(),
        "classifier",
        "publishRest",
        "jmsConnector",
        "executor");

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
        case -614707837:  // publishRest
          return _publishRest;
        case -1495762275:  // jmsConnector
          return _jmsConnector;
        case 2043017427:  // executor
          return _executor;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends InMemoryFunctionBlacklistProviderComponentFactory> builder() {
      return new DirectBeanBuilder<InMemoryFunctionBlacklistProviderComponentFactory>(new InMemoryFunctionBlacklistProviderComponentFactory());
    }

    @Override
    public Class<? extends InMemoryFunctionBlacklistProviderComponentFactory> beanType() {
      return InMemoryFunctionBlacklistProviderComponentFactory.class;
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

    /**
     * The meta-property for the {@code publishRest} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<Boolean> publishRest() {
      return _publishRest;
    }

    /**
     * The meta-property for the {@code jmsConnector} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<JmsConnector> jmsConnector() {
      return _jmsConnector;
    }

    /**
     * The meta-property for the {@code executor} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<ScheduledExecutorService> executor() {
      return _executor;
    }

    //-----------------------------------------------------------------------
    @Override
    protected Object propertyGet(Bean bean, String propertyName, boolean quiet) {
      switch (propertyName.hashCode()) {
        case -281470431:  // classifier
          return ((InMemoryFunctionBlacklistProviderComponentFactory) bean).getClassifier();
        case -614707837:  // publishRest
          return ((InMemoryFunctionBlacklistProviderComponentFactory) bean).isPublishRest();
        case -1495762275:  // jmsConnector
          return ((InMemoryFunctionBlacklistProviderComponentFactory) bean).getJmsConnector();
        case 2043017427:  // executor
          return ((InMemoryFunctionBlacklistProviderComponentFactory) bean).getExecutor();
      }
      return super.propertyGet(bean, propertyName, quiet);
    }

    @Override
    protected void propertySet(Bean bean, String propertyName, Object newValue, boolean quiet) {
      switch (propertyName.hashCode()) {
        case -281470431:  // classifier
          ((InMemoryFunctionBlacklistProviderComponentFactory) bean).setClassifier((String) newValue);
          return;
        case -614707837:  // publishRest
          ((InMemoryFunctionBlacklistProviderComponentFactory) bean).setPublishRest((Boolean) newValue);
          return;
        case -1495762275:  // jmsConnector
          ((InMemoryFunctionBlacklistProviderComponentFactory) bean).setJmsConnector((JmsConnector) newValue);
          return;
        case 2043017427:  // executor
          ((InMemoryFunctionBlacklistProviderComponentFactory) bean).setExecutor((ScheduledExecutorService) newValue);
          return;
      }
      super.propertySet(bean, propertyName, newValue, quiet);
    }

    @Override
    protected void validate(Bean bean) {
      JodaBeanUtils.notNull(((InMemoryFunctionBlacklistProviderComponentFactory) bean)._classifier, "classifier");
      super.validate(bean);
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
