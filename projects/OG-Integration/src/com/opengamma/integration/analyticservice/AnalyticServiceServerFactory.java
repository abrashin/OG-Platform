/**
 * Copyright (C) 2012 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.integration.analyticservice;

import java.util.LinkedHashMap;
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
import org.springframework.jms.listener.DefaultMessageListenerContainer;

import com.opengamma.component.ComponentRepository;
import com.opengamma.component.factory.AbstractComponentFactory;
import com.opengamma.core.config.ConfigSource;
import com.opengamma.core.position.PositionSource;
import com.opengamma.engine.view.ViewProcessor;
import com.opengamma.livedata.UserPrincipal;
import com.opengamma.master.portfolio.PortfolioMaster;
import com.opengamma.master.position.PositionMaster;
import com.opengamma.transport.ByteArrayFudgeMessageReceiver;
import com.opengamma.transport.jms.JmsByteArrayMessageDispatcher;
import com.opengamma.util.fudgemsg.OpenGammaFudgeContext;
import com.opengamma.util.jms.JmsConnector;

/**
 * 
 */
@BeanDefinition
public class AnalyticServiceServerFactory extends AbstractComponentFactory {
  
  @PropertyDefinition(validate = "notNull")
  private JmsConnector _listenJmsConnector;
  
  @PropertyDefinition(validate = "notNull")
  private String _listenTopicName;
    
  @PropertyDefinition(validate = "notNull")
  private String _viewName;
      
  @PropertyDefinition(validate = "notNull")
  private PositionMaster _positionMaster;
  
  @PropertyDefinition(validate = "notNull")
  private PortfolioMaster _portfolioMaster;
  
  @PropertyDefinition(validate = "notNull")
  private ConfigSource _configSource;
  
  @PropertyDefinition(validate = "notNull")
  private ViewProcessor _viewProcessor;
  
  @PropertyDefinition(validate = "notNull")
  private String _providerIdName;
  
  @PropertyDefinition
  private UserPrincipal _user;
 
  @PropertyDefinition(validate = "notNull")
  private PositionSource _positionSource;
    
  @Override
  public void init(ComponentRepository repo, LinkedHashMap<String, String> configuration) throws Exception {
    
    final AnalyticServiceServer server = new AnalyticServiceServer(getViewProcessor(), getPositionMaster(), getPortfolioMaster(), getConfigSource());
    server.setUser(getUser());
    server.setViewName(getViewName());
    server.setProviderIdName(getProviderIdName());
    
    AnalyticServiceTradeProducer tradeProducer = new AnalyticServiceTradeProducer();
    
    DefaultMessageListenerContainer container = new DefaultMessageListenerContainer();
    container.setMessageListener(
        new JmsByteArrayMessageDispatcher(
            new ByteArrayFudgeMessageReceiver(tradeProducer)));
    container.setDestinationName(getListenTopicName());
    container.setPubSubDomain(true);
    container.setConnectionFactory(getListenJmsConnector().getConnectionFactory());
    
    JmsAnalyticsDistributor analyticsDistributor = new JmsAnalyticsDistributor(new DefaultJmsTopicNameResolver(getPositionSource()), OpenGammaFudgeContext.getInstance(), _listenJmsConnector);
    server.setAnalyticResultReceiver(analyticsDistributor);
    
    tradeProducer.addTradeListener(server);
    repo.registerLifecycle(server);
    repo.registerLifecycle(container);
    
  }

  //------------------------- AUTOGENERATED START -------------------------
  ///CLOVER:OFF
  /**
   * The meta-bean for {@code AnalyticServiceServerFactory}.
   * @return the meta-bean, not null
   */
  public static AnalyticServiceServerFactory.Meta meta() {
    return AnalyticServiceServerFactory.Meta.INSTANCE;
  }
  static {
    JodaBeanUtils.registerMetaBean(AnalyticServiceServerFactory.Meta.INSTANCE);
  }

  @Override
  public AnalyticServiceServerFactory.Meta metaBean() {
    return AnalyticServiceServerFactory.Meta.INSTANCE;
  }

  @Override
  protected Object propertyGet(String propertyName, boolean quiet) {
    switch (propertyName.hashCode()) {
      case 1486580228:  // listenJmsConnector
        return getListenJmsConnector();
      case 1916939859:  // listenTopicName
        return getListenTopicName();
      case 1195658960:  // viewName
        return getViewName();
      case -1840419605:  // positionMaster
        return getPositionMaster();
      case -772274742:  // portfolioMaster
        return getPortfolioMaster();
      case 195157501:  // configSource
        return getConfigSource();
      case -1697555603:  // viewProcessor
        return getViewProcessor();
      case 675409815:  // providerIdName
        return getProviderIdName();
      case 3599307:  // user
        return getUser();
      case -1655657820:  // positionSource
        return getPositionSource();
    }
    return super.propertyGet(propertyName, quiet);
  }

  @Override
  protected void propertySet(String propertyName, Object newValue, boolean quiet) {
    switch (propertyName.hashCode()) {
      case 1486580228:  // listenJmsConnector
        setListenJmsConnector((JmsConnector) newValue);
        return;
      case 1916939859:  // listenTopicName
        setListenTopicName((String) newValue);
        return;
      case 1195658960:  // viewName
        setViewName((String) newValue);
        return;
      case -1840419605:  // positionMaster
        setPositionMaster((PositionMaster) newValue);
        return;
      case -772274742:  // portfolioMaster
        setPortfolioMaster((PortfolioMaster) newValue);
        return;
      case 195157501:  // configSource
        setConfigSource((ConfigSource) newValue);
        return;
      case -1697555603:  // viewProcessor
        setViewProcessor((ViewProcessor) newValue);
        return;
      case 675409815:  // providerIdName
        setProviderIdName((String) newValue);
        return;
      case 3599307:  // user
        setUser((UserPrincipal) newValue);
        return;
      case -1655657820:  // positionSource
        setPositionSource((PositionSource) newValue);
        return;
    }
    super.propertySet(propertyName, newValue, quiet);
  }

  @Override
  protected void validate() {
    JodaBeanUtils.notNull(_listenJmsConnector, "listenJmsConnector");
    JodaBeanUtils.notNull(_listenTopicName, "listenTopicName");
    JodaBeanUtils.notNull(_viewName, "viewName");
    JodaBeanUtils.notNull(_positionMaster, "positionMaster");
    JodaBeanUtils.notNull(_portfolioMaster, "portfolioMaster");
    JodaBeanUtils.notNull(_configSource, "configSource");
    JodaBeanUtils.notNull(_viewProcessor, "viewProcessor");
    JodaBeanUtils.notNull(_providerIdName, "providerIdName");
    JodaBeanUtils.notNull(_positionSource, "positionSource");
    super.validate();
  }

  @Override
  public boolean equals(Object obj) {
    if (obj == this) {
      return true;
    }
    if (obj != null && obj.getClass() == this.getClass()) {
      AnalyticServiceServerFactory other = (AnalyticServiceServerFactory) obj;
      return JodaBeanUtils.equal(getListenJmsConnector(), other.getListenJmsConnector()) &&
          JodaBeanUtils.equal(getListenTopicName(), other.getListenTopicName()) &&
          JodaBeanUtils.equal(getViewName(), other.getViewName()) &&
          JodaBeanUtils.equal(getPositionMaster(), other.getPositionMaster()) &&
          JodaBeanUtils.equal(getPortfolioMaster(), other.getPortfolioMaster()) &&
          JodaBeanUtils.equal(getConfigSource(), other.getConfigSource()) &&
          JodaBeanUtils.equal(getViewProcessor(), other.getViewProcessor()) &&
          JodaBeanUtils.equal(getProviderIdName(), other.getProviderIdName()) &&
          JodaBeanUtils.equal(getUser(), other.getUser()) &&
          JodaBeanUtils.equal(getPositionSource(), other.getPositionSource()) &&
          super.equals(obj);
    }
    return false;
  }

  @Override
  public int hashCode() {
    int hash = 7;
    hash += hash * 31 + JodaBeanUtils.hashCode(getListenJmsConnector());
    hash += hash * 31 + JodaBeanUtils.hashCode(getListenTopicName());
    hash += hash * 31 + JodaBeanUtils.hashCode(getViewName());
    hash += hash * 31 + JodaBeanUtils.hashCode(getPositionMaster());
    hash += hash * 31 + JodaBeanUtils.hashCode(getPortfolioMaster());
    hash += hash * 31 + JodaBeanUtils.hashCode(getConfigSource());
    hash += hash * 31 + JodaBeanUtils.hashCode(getViewProcessor());
    hash += hash * 31 + JodaBeanUtils.hashCode(getProviderIdName());
    hash += hash * 31 + JodaBeanUtils.hashCode(getUser());
    hash += hash * 31 + JodaBeanUtils.hashCode(getPositionSource());
    return hash ^ super.hashCode();
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the listenJmsConnector.
   * @return the value of the property, not null
   */
  public JmsConnector getListenJmsConnector() {
    return _listenJmsConnector;
  }

  /**
   * Sets the listenJmsConnector.
   * @param listenJmsConnector  the new value of the property, not null
   */
  public void setListenJmsConnector(JmsConnector listenJmsConnector) {
    JodaBeanUtils.notNull(listenJmsConnector, "listenJmsConnector");
    this._listenJmsConnector = listenJmsConnector;
  }

  /**
   * Gets the the {@code listenJmsConnector} property.
   * @return the property, not null
   */
  public final Property<JmsConnector> listenJmsConnector() {
    return metaBean().listenJmsConnector().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the listenTopicName.
   * @return the value of the property, not null
   */
  public String getListenTopicName() {
    return _listenTopicName;
  }

  /**
   * Sets the listenTopicName.
   * @param listenTopicName  the new value of the property, not null
   */
  public void setListenTopicName(String listenTopicName) {
    JodaBeanUtils.notNull(listenTopicName, "listenTopicName");
    this._listenTopicName = listenTopicName;
  }

  /**
   * Gets the the {@code listenTopicName} property.
   * @return the property, not null
   */
  public final Property<String> listenTopicName() {
    return metaBean().listenTopicName().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the viewName.
   * @return the value of the property, not null
   */
  public String getViewName() {
    return _viewName;
  }

  /**
   * Sets the viewName.
   * @param viewName  the new value of the property, not null
   */
  public void setViewName(String viewName) {
    JodaBeanUtils.notNull(viewName, "viewName");
    this._viewName = viewName;
  }

  /**
   * Gets the the {@code viewName} property.
   * @return the property, not null
   */
  public final Property<String> viewName() {
    return metaBean().viewName().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the positionMaster.
   * @return the value of the property, not null
   */
  public PositionMaster getPositionMaster() {
    return _positionMaster;
  }

  /**
   * Sets the positionMaster.
   * @param positionMaster  the new value of the property, not null
   */
  public void setPositionMaster(PositionMaster positionMaster) {
    JodaBeanUtils.notNull(positionMaster, "positionMaster");
    this._positionMaster = positionMaster;
  }

  /**
   * Gets the the {@code positionMaster} property.
   * @return the property, not null
   */
  public final Property<PositionMaster> positionMaster() {
    return metaBean().positionMaster().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the portfolioMaster.
   * @return the value of the property, not null
   */
  public PortfolioMaster getPortfolioMaster() {
    return _portfolioMaster;
  }

  /**
   * Sets the portfolioMaster.
   * @param portfolioMaster  the new value of the property, not null
   */
  public void setPortfolioMaster(PortfolioMaster portfolioMaster) {
    JodaBeanUtils.notNull(portfolioMaster, "portfolioMaster");
    this._portfolioMaster = portfolioMaster;
  }

  /**
   * Gets the the {@code portfolioMaster} property.
   * @return the property, not null
   */
  public final Property<PortfolioMaster> portfolioMaster() {
    return metaBean().portfolioMaster().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the configSource.
   * @return the value of the property, not null
   */
  public ConfigSource getConfigSource() {
    return _configSource;
  }

  /**
   * Sets the configSource.
   * @param configSource  the new value of the property, not null
   */
  public void setConfigSource(ConfigSource configSource) {
    JodaBeanUtils.notNull(configSource, "configSource");
    this._configSource = configSource;
  }

  /**
   * Gets the the {@code configSource} property.
   * @return the property, not null
   */
  public final Property<ConfigSource> configSource() {
    return metaBean().configSource().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the viewProcessor.
   * @return the value of the property, not null
   */
  public ViewProcessor getViewProcessor() {
    return _viewProcessor;
  }

  /**
   * Sets the viewProcessor.
   * @param viewProcessor  the new value of the property, not null
   */
  public void setViewProcessor(ViewProcessor viewProcessor) {
    JodaBeanUtils.notNull(viewProcessor, "viewProcessor");
    this._viewProcessor = viewProcessor;
  }

  /**
   * Gets the the {@code viewProcessor} property.
   * @return the property, not null
   */
  public final Property<ViewProcessor> viewProcessor() {
    return metaBean().viewProcessor().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the providerIdName.
   * @return the value of the property, not null
   */
  public String getProviderIdName() {
    return _providerIdName;
  }

  /**
   * Sets the providerIdName.
   * @param providerIdName  the new value of the property, not null
   */
  public void setProviderIdName(String providerIdName) {
    JodaBeanUtils.notNull(providerIdName, "providerIdName");
    this._providerIdName = providerIdName;
  }

  /**
   * Gets the the {@code providerIdName} property.
   * @return the property, not null
   */
  public final Property<String> providerIdName() {
    return metaBean().providerIdName().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the user.
   * @return the value of the property
   */
  public UserPrincipal getUser() {
    return _user;
  }

  /**
   * Sets the user.
   * @param user  the new value of the property
   */
  public void setUser(UserPrincipal user) {
    this._user = user;
  }

  /**
   * Gets the the {@code user} property.
   * @return the property, not null
   */
  public final Property<UserPrincipal> user() {
    return metaBean().user().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * Gets the positionSource.
   * @return the value of the property, not null
   */
  public PositionSource getPositionSource() {
    return _positionSource;
  }

  /**
   * Sets the positionSource.
   * @param positionSource  the new value of the property, not null
   */
  public void setPositionSource(PositionSource positionSource) {
    JodaBeanUtils.notNull(positionSource, "positionSource");
    this._positionSource = positionSource;
  }

  /**
   * Gets the the {@code positionSource} property.
   * @return the property, not null
   */
  public final Property<PositionSource> positionSource() {
    return metaBean().positionSource().createProperty(this);
  }

  //-----------------------------------------------------------------------
  /**
   * The meta-bean for {@code AnalyticServiceServerFactory}.
   */
  public static class Meta extends AbstractComponentFactory.Meta {
    /**
     * The singleton instance of the meta-bean.
     */
    static final Meta INSTANCE = new Meta();

    /**
     * The meta-property for the {@code listenJmsConnector} property.
     */
    private final MetaProperty<JmsConnector> _listenJmsConnector = DirectMetaProperty.ofReadWrite(
        this, "listenJmsConnector", AnalyticServiceServerFactory.class, JmsConnector.class);
    /**
     * The meta-property for the {@code listenTopicName} property.
     */
    private final MetaProperty<String> _listenTopicName = DirectMetaProperty.ofReadWrite(
        this, "listenTopicName", AnalyticServiceServerFactory.class, String.class);
    /**
     * The meta-property for the {@code viewName} property.
     */
    private final MetaProperty<String> _viewName = DirectMetaProperty.ofReadWrite(
        this, "viewName", AnalyticServiceServerFactory.class, String.class);
    /**
     * The meta-property for the {@code positionMaster} property.
     */
    private final MetaProperty<PositionMaster> _positionMaster = DirectMetaProperty.ofReadWrite(
        this, "positionMaster", AnalyticServiceServerFactory.class, PositionMaster.class);
    /**
     * The meta-property for the {@code portfolioMaster} property.
     */
    private final MetaProperty<PortfolioMaster> _portfolioMaster = DirectMetaProperty.ofReadWrite(
        this, "portfolioMaster", AnalyticServiceServerFactory.class, PortfolioMaster.class);
    /**
     * The meta-property for the {@code configSource} property.
     */
    private final MetaProperty<ConfigSource> _configSource = DirectMetaProperty.ofReadWrite(
        this, "configSource", AnalyticServiceServerFactory.class, ConfigSource.class);
    /**
     * The meta-property for the {@code viewProcessor} property.
     */
    private final MetaProperty<ViewProcessor> _viewProcessor = DirectMetaProperty.ofReadWrite(
        this, "viewProcessor", AnalyticServiceServerFactory.class, ViewProcessor.class);
    /**
     * The meta-property for the {@code providerIdName} property.
     */
    private final MetaProperty<String> _providerIdName = DirectMetaProperty.ofReadWrite(
        this, "providerIdName", AnalyticServiceServerFactory.class, String.class);
    /**
     * The meta-property for the {@code user} property.
     */
    private final MetaProperty<UserPrincipal> _user = DirectMetaProperty.ofReadWrite(
        this, "user", AnalyticServiceServerFactory.class, UserPrincipal.class);
    /**
     * The meta-property for the {@code positionSource} property.
     */
    private final MetaProperty<PositionSource> _positionSource = DirectMetaProperty.ofReadWrite(
        this, "positionSource", AnalyticServiceServerFactory.class, PositionSource.class);
    /**
     * The meta-properties.
     */
    private final Map<String, MetaProperty<?>> _metaPropertyMap$ = new DirectMetaPropertyMap(
      this, (DirectMetaPropertyMap) super.metaPropertyMap(),
        "listenJmsConnector",
        "listenTopicName",
        "viewName",
        "positionMaster",
        "portfolioMaster",
        "configSource",
        "viewProcessor",
        "providerIdName",
        "user",
        "positionSource");

    /**
     * Restricted constructor.
     */
    protected Meta() {
    }

    @Override
    protected MetaProperty<?> metaPropertyGet(String propertyName) {
      switch (propertyName.hashCode()) {
        case 1486580228:  // listenJmsConnector
          return _listenJmsConnector;
        case 1916939859:  // listenTopicName
          return _listenTopicName;
        case 1195658960:  // viewName
          return _viewName;
        case -1840419605:  // positionMaster
          return _positionMaster;
        case -772274742:  // portfolioMaster
          return _portfolioMaster;
        case 195157501:  // configSource
          return _configSource;
        case -1697555603:  // viewProcessor
          return _viewProcessor;
        case 675409815:  // providerIdName
          return _providerIdName;
        case 3599307:  // user
          return _user;
        case -1655657820:  // positionSource
          return _positionSource;
      }
      return super.metaPropertyGet(propertyName);
    }

    @Override
    public BeanBuilder<? extends AnalyticServiceServerFactory> builder() {
      return new DirectBeanBuilder<AnalyticServiceServerFactory>(new AnalyticServiceServerFactory());
    }

    @Override
    public Class<? extends AnalyticServiceServerFactory> beanType() {
      return AnalyticServiceServerFactory.class;
    }

    @Override
    public Map<String, MetaProperty<?>> metaPropertyMap() {
      return _metaPropertyMap$;
    }

    //-----------------------------------------------------------------------
    /**
     * The meta-property for the {@code listenJmsConnector} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<JmsConnector> listenJmsConnector() {
      return _listenJmsConnector;
    }

    /**
     * The meta-property for the {@code listenTopicName} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<String> listenTopicName() {
      return _listenTopicName;
    }

    /**
     * The meta-property for the {@code viewName} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<String> viewName() {
      return _viewName;
    }

    /**
     * The meta-property for the {@code positionMaster} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<PositionMaster> positionMaster() {
      return _positionMaster;
    }

    /**
     * The meta-property for the {@code portfolioMaster} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<PortfolioMaster> portfolioMaster() {
      return _portfolioMaster;
    }

    /**
     * The meta-property for the {@code configSource} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<ConfigSource> configSource() {
      return _configSource;
    }

    /**
     * The meta-property for the {@code viewProcessor} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<ViewProcessor> viewProcessor() {
      return _viewProcessor;
    }

    /**
     * The meta-property for the {@code providerIdName} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<String> providerIdName() {
      return _providerIdName;
    }

    /**
     * The meta-property for the {@code user} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<UserPrincipal> user() {
      return _user;
    }

    /**
     * The meta-property for the {@code positionSource} property.
     * @return the meta-property, not null
     */
    public final MetaProperty<PositionSource> positionSource() {
      return _positionSource;
    }

  }

  ///CLOVER:ON
  //-------------------------- AUTOGENERATED END --------------------------
}
