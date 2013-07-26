/**
 * Copyright (C) 2013 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.financial.analytics.model.equity;

import java.util.Collections;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.opengamma.core.security.Security;
import com.opengamma.core.value.MarketDataRequirementNames;
import com.opengamma.engine.ComputationTarget;
import com.opengamma.engine.function.AbstractFunction;
import com.opengamma.engine.function.FunctionCompilationContext;
import com.opengamma.engine.function.FunctionExecutionContext;
import com.opengamma.engine.function.FunctionInputs;
import com.opengamma.engine.target.ComputationTargetType;
import com.opengamma.engine.value.ComputedValue;
import com.opengamma.engine.value.ValueProperties;
import com.opengamma.engine.value.ValuePropertyNames;
import com.opengamma.engine.value.ValueRequirement;
import com.opengamma.engine.value.ValueRequirementNames;
import com.opengamma.engine.value.ValueSpecification;
import com.opengamma.financial.security.FinancialSecurity;
import com.opengamma.financial.security.FinancialSecurityUtils;
import com.opengamma.financial.security.MarketSecurityVisitor;
import com.opengamma.financial.security.equity.EquitySecurity;
import com.opengamma.id.ExternalId;
import com.opengamma.util.async.AsynchronousExecution;
import com.opengamma.util.money.Currency;

/**
 * Provides the market value for the underlying of a position, when applicable.<p>
 * For securities that do not have an underlying, such as the {@link EquitySecurity}, their own market value is provided
 */
public class UnderlyingMarketPriceFunction extends AbstractFunction.NonCompiledInvoker {

  private static MarketSecurityVisitor s_judgeOfMarketSecurities = new MarketSecurityVisitor();
  private static final Logger s_logger = LoggerFactory.getLogger(UnderlyingMarketPriceFunction.class);
  
  @Override
  public ComputationTargetType getTargetType() {
    return ComputationTargetType.POSITION_OR_TRADE;
  }
  
  @Override
  public Set<ComputedValue> execute(FunctionExecutionContext executionContext, FunctionInputs inputs, ComputationTarget target, Set<ValueRequirement> desiredValues) throws AsynchronousExecution {
    final double marketValue = (Double) inputs.getValue(MarketDataRequirementNames.MARKET_VALUE);
    final ValueRequirement desiredValue = desiredValues.iterator().next();
    return Collections.singleton(new ComputedValue(new ValueSpecification(ValueRequirementNames.UNDERLYING_MARKET_PRICE, target.toSpecification(), desiredValue.getConstraints()), marketValue));
  }
  
  /**
   * Currently set to apply to any market-traded security in {@link MarketSecurityVisitor}.<p>
   * TODO Constrain this further to those {@link FinancialSecurity}'s that have underlyings..
   * @param context The compilation context with view-specific parameters and configurations.
   * @param target the Target for which capability is to be tested
   * @return true if FinancialSecurity underlying the Position or Trade is a market-traded Security, else false 
   */
  @Override
  public boolean canApplyTo(final FunctionCompilationContext context, final ComputationTarget target) {
    if (!(target.getPositionOrTrade().getSecurity() instanceof FinancialSecurity)) {
      return false;
    }
    final FinancialSecurity security = (FinancialSecurity) target.getPositionOrTrade().getSecurity();
    try {
      return security.accept(s_judgeOfMarketSecurities);
    } catch (Exception e) {
      return false;
    }
  }

  @Override
  public Set<ValueSpecification> getResults(FunctionCompilationContext context, ComputationTarget target) {
    final Currency ccy = FinancialSecurityUtils.getCurrency(target.getPositionOrTrade().getSecurity());
    ValueProperties valueProperties;
    if (ccy == null) {
      valueProperties = createValueProperties().get();
    } else {
      valueProperties = createValueProperties().with(ValuePropertyNames.CURRENCY, ccy.getCode()).get();
    }
    return Collections.singleton(new ValueSpecification(ValueRequirementNames.UNDERLYING_MARKET_PRICE, target.toSpecification(), valueProperties));
  }

  @Override
  public Set<ValueRequirement> getRequirements(FunctionCompilationContext context, ComputationTarget target, ValueRequirement desiredValue) {
    final Security security = target.getPositionOrTrade().getSecurity();
    try {
      final ExternalId underlyingId = FinancialSecurityUtils.getUnderlyingId(security);
      return Collections.singleton(new ValueRequirement(MarketDataRequirementNames.MARKET_VALUE, ComputationTargetType.SECURITY, underlyingId));
    } catch (Exception e) {
      s_logger.info("No underlying found for {}. The security itself will be used as its own underlying", security.getName());
      return Collections.singleton(new ValueRequirement(MarketDataRequirementNames.MARKET_VALUE, ComputationTargetType.SECURITY, security.getUniqueId()));
    }
  }

}
