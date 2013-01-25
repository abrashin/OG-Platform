/**
 * Copyright (C) 2012 - present by OpenGamma Inc. and the OpenGamma group of companies
 * 
 * Please see distribution for license.
 */
package com.opengamma.examples.generator;

import java.math.BigDecimal;
import java.text.DecimalFormat;

import org.threeten.bp.ZonedDateTime;

import com.opengamma.core.id.ExternalSchemes;
import com.opengamma.core.position.Counterparty;
import com.opengamma.core.position.PortfolioNode;
import com.opengamma.core.position.Position;
import com.opengamma.core.position.impl.SimplePortfolioNode;
import com.opengamma.financial.convention.businessday.BusinessDayConvention;
import com.opengamma.financial.convention.businessday.BusinessDayConventionFactory;
import com.opengamma.financial.convention.daycount.DayCount;
import com.opengamma.financial.convention.daycount.DayCountFactory;
import com.opengamma.financial.convention.frequency.PeriodFrequency;
import com.opengamma.financial.generator.AbstractPortfolioGeneratorTool;
import com.opengamma.financial.generator.PortfolioNodeGenerator;
import com.opengamma.financial.generator.SecurityGenerator;
import com.opengamma.financial.generator.SimplePositionGenerator;
import com.opengamma.financial.generator.StaticNameGenerator;
import com.opengamma.financial.generator.TreePortfolioNodeGenerator;
import com.opengamma.financial.security.capfloor.CapFloorCMSSpreadSecurity;
import com.opengamma.financial.security.capfloor.CapFloorSecurity;
import com.opengamma.financial.security.swap.FloatingInterestRateLeg;
import com.opengamma.financial.security.swap.FloatingRateType;
import com.opengamma.financial.security.swap.InterestRateNotional;
import com.opengamma.financial.security.swap.SwapSecurity;
import com.opengamma.id.ExternalId;
import com.opengamma.master.position.ManageableTrade;
import com.opengamma.util.money.Currency;
import com.opengamma.util.time.DateUtils;
import com.opengamma.util.time.Tenor;

/**
 * 
 */
public class MixedCMPortfolioGeneratorTool extends AbstractPortfolioGeneratorTool {
  private static final BusinessDayConvention FOLLOWING = BusinessDayConventionFactory.INSTANCE.getBusinessDayConvention("Following");
  private static final ExternalId REGION = ExternalSchemes.financialRegionId("US+GB");
  private static final DayCount ACT_360 = DayCountFactory.INSTANCE.getDayCount("Actual/360");
  private static final Currency CURRENCY = Currency.USD;
  private static final String COUNTERPARTY = "Cpty";
  private static final ExternalId LIBOR_3M = ExternalSchemes.syntheticSecurityId("USDLIBORP3M");
  private static final ExternalId LIBOR_6M = ExternalSchemes.syntheticSecurityId("USDLIBORP6M");
  private static final Tenor[] TENORS = new Tenor[] {Tenor.ONE_YEAR, Tenor.TWO_YEARS, Tenor.FIVE_YEARS, Tenor.TEN_YEARS, Tenor.ofYears(25)};
  private static final double[] STRIKES = new double[] {0.01, 0.02, 0.03};
  private static final ZonedDateTime TRADE_DATE = DateUtils.getUTCDate(2012, 8, 1);
  private static final Tenor[] PAY_TENORS = new Tenor[] {Tenor.ONE_YEAR, Tenor.FIVE_YEARS};
  private static final Tenor[] RECEIVE_TENORS = new Tenor[] {Tenor.TWO_YEARS, Tenor.TEN_YEARS};
  private static final DecimalFormat FORMAT = new DecimalFormat("##.##");

  @Override
  public PortfolioNodeGenerator createPortfolioNodeGenerator(final int size) {
    final CapFloorSecurityGenerator capFloors = new CapFloorSecurityGenerator(TRADE_DATE, TENORS, STRIKES);
    final CMSSwapSecurityGenerator cmsSwap = new CMSSwapSecurityGenerator(TRADE_DATE, TENORS);
    final CMSCapFloorSpreadSecurityGenerator cmsSpreads = new CMSCapFloorSpreadSecurityGenerator(TRADE_DATE, PAY_TENORS, RECEIVE_TENORS, TENORS, STRIKES);
    configure(capFloors);
    configure(cmsSwap);
    configure(cmsSpreads);
    final TreePortfolioNodeGenerator rootNode = new TreePortfolioNodeGenerator(new StaticNameGenerator("Mixed CM Portfolio"));
    rootNode.addChildNode(capFloors);
    rootNode.addChildNode(cmsSwap);
    rootNode.addChildNode(cmsSpreads);
    return rootNode;
  }

  private class CapFloorSecurityGenerator extends SecurityGenerator<CapFloorSecurity> implements PortfolioNodeGenerator {
    private final double _notional = 10000000;
    private final ZonedDateTime _tradeDate;
    private final Tenor[] _tenors;
    private final double[] _strikes;

    public CapFloorSecurityGenerator(final ZonedDateTime tradeDate, final Tenor[] tenors, final double[] strikes) {
      _tradeDate = tradeDate;
      _tenors = tenors;
      _strikes = strikes;
    }

    @Override
    public PortfolioNode createPortfolioNode() {
      final SimplePortfolioNode node = new SimplePortfolioNode("CM Cap/Floor");
      for (final Tenor tenor : _tenors) {
        for (final double strike : _strikes) {
          final CapFloorSecurity cap = createCapFloor(tenor, strike);
          final ManageableTrade trade = new ManageableTrade(BigDecimal.ONE, getSecurityPersister().storeSecurity(cap), _tradeDate.getDate(),
              _tradeDate.toOffsetDateTime().toOffsetTime(), ExternalId.of(Counterparty.DEFAULT_SCHEME, COUNTERPARTY));
          trade.setPremium(0.);
          trade.setPremiumCurrency(CURRENCY);
          final Position position = SimplePositionGenerator.createPositionFromTrade(trade);
          node.addPosition(position);
        }
      }
      return node;
    }

    @Override
    public CapFloorSecurity createSecurity() {
      return null;
    }

    private CapFloorSecurity createCapFloor(final Tenor tenor, final double strike) {
      final ZonedDateTime maturityDate = _tradeDate.plus(tenor.getPeriod());
      final boolean payer = Math.random() < 0.5 ? true : false;
      final boolean cap = Math.random() < 0.5 ? true : false;
      final String ticker = "USDISDA10" + tenor.getPeriod().toString();
      final ExternalId underlyingIdentifier = ExternalSchemes.syntheticSecurityId(ticker);
      final CapFloorSecurity security = new CapFloorSecurity(_tradeDate, maturityDate, _notional, underlyingIdentifier, strike, PeriodFrequency.SEMI_ANNUAL,
          CURRENCY, ACT_360, payer, cap, false);
      security.setName(CURRENCY.getCode() + " " + FORMAT.format(_notional / 1000000) + (cap ? "MM cap " : "MM floor ") + "@ " + FORMAT.format(strike) +
          (payer ? "%, pay " : "%, receive ") + tenor.getPeriod().normalizedMonthsISO().getYears() + "Y ISDA fixing" +
          " (" + _tradeDate.getDate().toString() + " - " + maturityDate.getDate().toString() + ")");
      return security;
    }
  }

  private class CMSSwapSecurityGenerator extends SecurityGenerator<SwapSecurity> implements PortfolioNodeGenerator {
    private final InterestRateNotional _notional = new InterestRateNotional(Currency.USD, 150000000);
    private final ZonedDateTime _tradeDate;
    private final Tenor[] _tenors;

    public CMSSwapSecurityGenerator(final ZonedDateTime tradeDate, final Tenor[] tenors) {
      _tradeDate = tradeDate;
      _tenors = tenors;
    }

    @Override
    public PortfolioNode createPortfolioNode() {
      final SimplePortfolioNode node = new SimplePortfolioNode("CM Swap");
      for (final Tenor tenor : _tenors) {
        final SwapSecurity swap = createSwap(tenor);
        final ManageableTrade trade = new ManageableTrade(BigDecimal.ONE, getSecurityPersister().storeSecurity(swap), _tradeDate.getDate(),
            _tradeDate.toOffsetDateTime().toOffsetTime(), ExternalId.of(Counterparty.DEFAULT_SCHEME, COUNTERPARTY));
        trade.setPremium(0.);
        trade.setPremiumCurrency(CURRENCY);
        final Position position = SimplePositionGenerator.createPositionFromTrade(trade);
        node.addPosition(position);
      }
      return node;
    }

    @Override
    public SwapSecurity createSecurity() {
      return null;
    }

    private SwapSecurity createSwap(final Tenor tenor) {
      final ZonedDateTime maturityDate = _tradeDate.plus(tenor.getPeriod());
      ExternalId iborReferenceRate;
      PeriodFrequency frequency;
      if (Math.random() < 0.5) {
        iborReferenceRate = LIBOR_3M;
        frequency = PeriodFrequency.QUARTERLY;
      } else {
        iborReferenceRate = LIBOR_6M;
        frequency = PeriodFrequency.SEMI_ANNUAL;
      }
      final FloatingInterestRateLeg iborLeg = new FloatingInterestRateLeg(ACT_360, frequency, REGION, FOLLOWING, _notional, true,
          iborReferenceRate, FloatingRateType.IBOR);
      final String ticker = "USDISDA10" + tenor.getPeriod().toString();
      final ExternalId cmsReferenceRate = ExternalSchemes.syntheticSecurityId(ticker);
      final FloatingInterestRateLeg cmsLeg = new FloatingInterestRateLeg(ACT_360, frequency, REGION, FOLLOWING, _notional, true,
          cmsReferenceRate, FloatingRateType.CMS);
      SwapSecurity security;
      boolean payIbor;
      if (Math.random() < 0.5) {
        security = new SwapSecurity(_tradeDate, _tradeDate, maturityDate, COUNTERPARTY, iborLeg, cmsLeg);
        payIbor = true;
      } else {
        security = new SwapSecurity(_tradeDate, _tradeDate, maturityDate, COUNTERPARTY, cmsLeg, iborLeg);
        payIbor = false;
      }
      security.setName(CURRENCY.getCode() + " " + FORMAT.format(_notional.getAmount() / 1000000) + "MM Swap, pay " +
          (payIbor ? frequency.getPeriod().getMonths() + "M Libor, receive " + tenor.getPeriod().getYears() + "Y ISDA fixing (" :
            tenor.getPeriod().getYears() + "Y ISDA fixing, receive " + frequency.getPeriod().getMonths() + "M Libor (") +
            _tradeDate.getDate().toString() + " - " + maturityDate.getDate().toString() + ")");
      return security;
    }
  }

  private class CMSCapFloorSpreadSecurityGenerator extends SecurityGenerator<CapFloorSecurity> implements PortfolioNodeGenerator {
    private final double _notional = 34000000;
    private final ZonedDateTime _tradeDate;
    private final Tenor[] _payTenors;
    private final Tenor[] _receiveTenors;
    private final Tenor[] _maturities;
    private final double[] _strikes;

    public CMSCapFloorSpreadSecurityGenerator(final ZonedDateTime tradeDate, final Tenor[] payTenors, final Tenor[] receiveTenors,
        final Tenor[] maturities, final double[] strikes) {
      _tradeDate = tradeDate;
      _payTenors = payTenors;
      _receiveTenors = receiveTenors;
      _maturities = maturities;
      _strikes = strikes;
    }

    @Override
    public PortfolioNode createPortfolioNode() {
      final SimplePortfolioNode node = new SimplePortfolioNode("CM Cap / Floor Spread");
      for (final Tenor payTenor : _payTenors) {
        for (final Tenor receiveTenor : _receiveTenors) {
          for (final Tenor maturity : _maturities) {
            for (final double strike : _strikes) {
              final CapFloorCMSSpreadSecurity cap = createCMSCapFloorSpread(payTenor, receiveTenor, maturity, strike);
              final ManageableTrade trade = new ManageableTrade(BigDecimal.ONE, getSecurityPersister().storeSecurity(cap), _tradeDate.getDate(),
                  _tradeDate.toOffsetDateTime().toOffsetTime(), ExternalId.of(Counterparty.DEFAULT_SCHEME, COUNTERPARTY));
              trade.setPremium(0.);
              trade.setPremiumCurrency(CURRENCY);
              final Position position = SimplePositionGenerator.createPositionFromTrade(trade);
              node.addPosition(position);
            }
          }
        }
      }
      return node;
    }

    @Override
    public CapFloorSecurity createSecurity() {
      return null;
    }

    private CapFloorCMSSpreadSecurity createCMSCapFloorSpread(final Tenor payTenor, final Tenor receiveTenor, final Tenor maturity, final double strike) {
      final ZonedDateTime maturityDate = _tradeDate.plus(maturity.getPeriod());
      final boolean payer = Math.random() < 0.5 ? true : false;
      final boolean cap = Math.random() < 0.5 ? true : false;
      final String payTicker = "USDISDA10" + payTenor.getPeriod().toString();
      final ExternalId payIdentifier = ExternalSchemes.syntheticSecurityId(payTicker);
      final String receiveTicker = "USDISDA10" + receiveTenor.getPeriod().toString();
      final ExternalId receiveIdentifier = ExternalSchemes.syntheticSecurityId(receiveTicker);
      final CapFloorCMSSpreadSecurity security = new CapFloorCMSSpreadSecurity(_tradeDate, maturityDate, _notional, payIdentifier, receiveIdentifier, strike,
          PeriodFrequency.ANNUAL, CURRENCY, ACT_360, payer, cap);
      security.setName(CURRENCY.getCode() + " " + FORMAT.format(_notional / 1000000) + (cap ? "MM cap spread " : "MM floor spread ") + "@ " + FORMAT.format(strike) +
          "%, pay " + payTenor.getPeriod().normalizedMonthsISO().getYears() + "Y ISDA fixing" + ", receive " +
          receiveTenor.getPeriod().normalizedMonthsISO().getYears() + "Y ISDA fixing" +
          " (" + _tradeDate.getDate().toString() + " - " + maturityDate.getDate().toString() + ")");
      return security;
    }
  }
}
