package com.robindrew.trading;

import static com.robindrew.trading.InstrumentType.COMMODITIES;
import static com.robindrew.trading.InstrumentType.CURRENCIES;
import static com.robindrew.trading.InstrumentType.INDICES;

public class Instruments {

	/** AUD/CAD. */
	public static final IInstrument AUD_CAD = new Instrument("AUDCAD", CURRENCIES);
	/** AUD/CHF. */
	public static final IInstrument AUD_CHF = new Instrument("AUDCHF", CURRENCIES);
	/** AUD/JPY. */
	public static final IInstrument AUD_JPY = new Instrument("AUDJPY", CURRENCIES);
	/** AUD/NZD. */
	public static final IInstrument AUD_NZD = new Instrument("AUDNZD", CURRENCIES);
	/** AUD/USD. */
	public static final IInstrument AUD_USD = new Instrument("AUDUSD", CURRENCIES);
	/** CAD/CHF. */
	public static final IInstrument CAD_CHF = new Instrument("CADCHF", CURRENCIES);
	/** CAD/JPY. */
	public static final IInstrument CAD_JPY = new Instrument("CADJPY", CURRENCIES);
	/** CHF/JPY. */
	public static final IInstrument CHF_JPY = new Instrument("CHFJPY", CURRENCIES);
	/** EUR/AUD. */
	public static final IInstrument EUR_AUD = new Instrument("EURAUD", CURRENCIES);
	/** EUR/CAD. */
	public static final IInstrument EUR_CAD = new Instrument("EURCAD", CURRENCIES);
	/** EUR/CHF. */
	public static final IInstrument EUR_CHF = new Instrument("EURCHF", CURRENCIES);
	/** EUR/CZK. */
	public static final IInstrument EUR_CZK = new Instrument("EURCZK", CURRENCIES);
	/** EUR/DKK. */
	public static final IInstrument EUR_DKK = new Instrument("EURDKK", CURRENCIES);
	/** EUR/GBP. */
	public static final IInstrument EUR_GBP = new Instrument("EURGBP", CURRENCIES);
	/** EUR/HUF. */
	public static final IInstrument EUR_HUF = new Instrument("EURHUF", CURRENCIES);
	/** EUR/JPY. */
	public static final IInstrument EUR_JPY = new Instrument("EURJPY", CURRENCIES);
	/** EUR/NOK. */
	public static final IInstrument EUR_NOK = new Instrument("EURNOK", CURRENCIES);
	/** EUR/NZD. */
	public static final IInstrument EUR_NZD = new Instrument("EURNZD", CURRENCIES);
	/** EUR/PLN. */
	public static final IInstrument EUR_PLN = new Instrument("EURPLN", CURRENCIES);
	/** EUR/SEK. */
	public static final IInstrument EUR_SEK = new Instrument("EURSEK", CURRENCIES);
	/** EUR/TRY. */
	public static final IInstrument EUR_TRY = new Instrument("EURTRY", CURRENCIES);
	/** EUR/USD. */
	public static final IInstrument EUR_USD = new Instrument("EURUSD", CURRENCIES);
	/** GBP/AUD. */
	public static final IInstrument GBP_AUD = new Instrument("GBPAUD", CURRENCIES);
	/** GBP/CAD. */
	public static final IInstrument GBP_CAD = new Instrument("GBPCAD", CURRENCIES);
	/** GBP/CHF. */
	public static final IInstrument GBP_CHF = new Instrument("GBPCHF", CURRENCIES);
	/** GBP/JPY. */
	public static final IInstrument GBP_JPY = new Instrument("GBPJPY", CURRENCIES);
	/** GBP/NZD. */
	public static final IInstrument GBP_NZD = new Instrument("GBPNZD", CURRENCIES);
	/** GBP/USD. */
	public static final IInstrument GBP_USD = new Instrument("GBPUSD", CURRENCIES);
	/** NZD/CAD. */
	public static final IInstrument NZD_CAD = new Instrument("NZDCAD", CURRENCIES);
	/** NZD/CHF. */
	public static final IInstrument NZD_CHF = new Instrument("NZDCHF", CURRENCIES);
	/** NZD/JPY. */
	public static final IInstrument NZD_JPY = new Instrument("NZDJPY", CURRENCIES);
	/** NZD/USD. */
	public static final IInstrument NZD_USD = new Instrument("NZDUSD", CURRENCIES);
	/** SGD/JPY. */
	public static final IInstrument SGD_JPY = new Instrument("SGDJPY", CURRENCIES);
	/** SPX/USD. */
	public static final IInstrument SPX_USD = new Instrument("SPXUSD", CURRENCIES);
	/** UDX/USD. */
	public static final IInstrument UDX_USD = new Instrument("UDXUSD", CURRENCIES);
	/** USD/CAD. */
	public static final IInstrument USD_CAD = new Instrument("USDCAD", CURRENCIES);
	/** USD/CHF. */
	public static final IInstrument USD_CHF = new Instrument("USDCHF", CURRENCIES);
	/** USD/CZK. */
	public static final IInstrument USD_CZK = new Instrument("USDCZK", CURRENCIES);
	/** USD/DKK. */
	public static final IInstrument USD_DKK = new Instrument("USDDKK", CURRENCIES);
	/** USD/HKD. */
	public static final IInstrument USD_HKD = new Instrument("USDHKD", CURRENCIES);
	/** USD/HUF. */
	public static final IInstrument USD_HUF = new Instrument("USDHUF", CURRENCIES);
	/** USD/JPY. */
	public static final IInstrument USD_JPY = new Instrument("USDJPY", CURRENCIES);
	/** USD/MXN. */
	public static final IInstrument USD_MXN = new Instrument("USDMXN", CURRENCIES);
	/** USD/NOK. */
	public static final IInstrument USD_NOK = new Instrument("USDNOK", CURRENCIES);
	/** USD/PLN. */
	public static final IInstrument USD_PLN = new Instrument("USDPLN", CURRENCIES);
	/** USD/SEK. */
	public static final IInstrument USD_SEK = new Instrument("USDSEK", CURRENCIES);
	/** USD/SGD. */
	public static final IInstrument USD_SGD = new Instrument("USDSGD", CURRENCIES);
	/** USD/TRY. */
	public static final IInstrument USD_TRY = new Instrument("USDTRY", CURRENCIES);
	/** USD/ZAR. */
	public static final IInstrument USD_ZAR = new Instrument("USDZAR", CURRENCIES);
	/** WTI/USD. */
	public static final IInstrument WTI_USD = new Instrument("WTIUSD", CURRENCIES);
	/** XAG/USD. */
	public static final IInstrument XAG_USD = new Instrument("XAGUSD", CURRENCIES);
	/** XAU/AUD. */
	public static final IInstrument XAU_AUD = new Instrument("XAUAUD", CURRENCIES);
	/** XAU/CHF. */
	public static final IInstrument XAU_CHF = new Instrument("XAUCHF", CURRENCIES);
	/** XAU/EUR. */
	public static final IInstrument XAU_EUR = new Instrument("XAUEUR", CURRENCIES);
	/** XAU/GBP. */
	public static final IInstrument XAU_GBP = new Instrument("XAUGBP", CURRENCIES);
	/** XAU/USD. */
	public static final IInstrument XAU_USD = new Instrument("XAUUSD", CURRENCIES);
	/** ZAR/JPY. */
	public static final IInstrument ZAR_JPY = new Instrument("ZARJPY", CURRENCIES);

	/** Bitcoin. */
	public static final IInstrument BITCOIN = new Instrument("BITCOIN", CURRENCIES);
	/** Ether. */
	public static final IInstrument ETHER = new Instrument("ETHER", CURRENCIES);
	/** Ripple. */
	public static final IInstrument RIPPLE = new Instrument("RIPPLE", CURRENCIES);
	/** Litecoin. */
	public static final IInstrument LITECOIN = new Instrument("LITECOIN", CURRENCIES);

	/** FTSE 100. */
	public static final IInstrument FTSE_100 = new Instrument("FTSE100", INDICES);
	/** FTSE 100. */
	public static final IInstrument CAC_40 = new Instrument("CAC40", INDICES);
	/** DAX. */
	public static final IInstrument DAX = new Instrument("DAX", INDICES);
	/** S&P 500. */
	public static final IInstrument SP_500 = new Instrument("SP500", INDICES);
	/** DOW JONES. */
	public static final IInstrument DOW_JONES = new Instrument("DOWJONES", INDICES);
	/** HANG SENG. */
	public static final IInstrument HANG_SENG = new Instrument("HANGSENG", INDICES);
	/** ASX 200. */
	public static final IInstrument ASX_200 = new Instrument("ASX200", INDICES);
	/** EURO STOXX 50. */
	public static final IInstrument EURO_STOXX_50 = new Instrument("EUROSTOXX50", INDICES);
	/** NASDAQ 100. */
	public static final IInstrument NASDAQ_100 = new Instrument("NASDAQ100", INDICES);
	/** NIKKEI 225. */
	public static final IInstrument NIKKEI_225 = new Instrument("NIKKEI225", INDICES);
	/** US DOLLAR INDEX. */
	public static final IInstrument US_DOLLAR_INDEX = new Instrument("USDOLLARINDEX", INDICES);

	/** BRENT CRUDE OIL (Brent Crude). */
	public static final IInstrument BRENT_CRUDE_OIL = new Instrument("BRENTCRUDEOIL", COMMODITIES);
	/** US CRUDE OIL (West Texas Intermediate). */
	public static final IInstrument US_CRUDE_OIL = new Instrument("USCRUDEOIL", COMMODITIES);

	/** GOLD. */
	public static final IInstrument GOLD = new Instrument("GOLD", COMMODITIES);
	/** SILVER. */
	public static final IInstrument SILVER = new Instrument("SILVER", COMMODITIES);
	/** PLATINUM. */
	public static final IInstrument PLATINUM = new Instrument("PLATINUM", COMMODITIES);

	public static IInstrument valueOf(String name) {
		switch (name) {
			case "AUDCAD":
				return AUD_CAD;
			case "AUDCHF":
				return AUD_CHF;
			case "AUDJPY":
				return AUD_JPY;
			case "AUDNZD":
				return AUD_NZD;
			case "AUDUSD":
				return AUD_USD;
			case "CADCHF":
				return CAD_CHF;
			case "CADJPY":
				return CAD_JPY;
			case "CHFJPY":
				return CHF_JPY;
			case "EURAUD":
				return EUR_AUD;
			case "EURCAD":
				return EUR_CAD;
			case "EURCHF":
				return EUR_CHF;
			case "EURCZK":
				return EUR_CZK;
			case "EURDKK":
				return EUR_DKK;
			case "EURGBP":
				return EUR_GBP;
			case "EURHUF":
				return EUR_HUF;
			case "EURJPY":
				return EUR_JPY;
			case "EURNOK":
				return EUR_NOK;
			case "EURNZD":
				return EUR_NZD;
			case "EURPLN":
				return EUR_PLN;
			case "EURSEK":
				return EUR_SEK;
			case "EURTRY":
				return EUR_TRY;
			case "EURUSD":
				return EUR_USD;
			case "GBPAUD":
				return GBP_AUD;
			case "GBPCAD":
				return GBP_CAD;
			case "GBPCHF":
				return GBP_CHF;
			case "GBPJPY":
				return GBP_JPY;
			case "GBPNZD":
				return GBP_NZD;
			case "GBPUSD":
				return GBP_USD;
			case "NZDCAD":
				return NZD_CAD;
			case "NZDCHF":
				return NZD_CHF;
			case "NZDJPY":
				return NZD_JPY;
			case "NZDUSD":
				return NZD_USD;
			case "SGDJPY":
				return SGD_JPY;
			case "SPXUSD":
				return SPX_USD;
			case "UDXUSD":
				return UDX_USD;
			case "USDCAD":
				return USD_CAD;
			case "USDCHF":
				return USD_CHF;
			case "USDCZK":
				return USD_CZK;
			case "USDDKK":
				return USD_DKK;
			case "USDHKD":
				return USD_HKD;
			case "USDHUF":
				return USD_HUF;
			case "USDJPY":
				return USD_JPY;
			case "USDMXN":
				return USD_MXN;
			case "USDNOK":
				return USD_NOK;
			case "USDPLN":
				return USD_PLN;
			case "USDSEK":
				return USD_SEK;
			case "USDSGD":
				return USD_SGD;
			case "USDTRY":
				return USD_TRY;
			case "USDZAR":
				return USD_ZAR;
			case "WTIUSD":
				return WTI_USD;
			case "XAGUSD":
				return XAG_USD;
			case "XAUAUD":
				return XAU_AUD;
			case "XAUCHF":
				return XAU_CHF;
			case "XAUEUR":
				return XAU_EUR;
			case "XAUGBP":
				return XAU_GBP;
			case "XAUUSD":
				return XAU_USD;
			case "ZARJPY":
				return ZAR_JPY;
			case "BITCOIN":
				return BITCOIN;
			case "ETHER":
				return ETHER;
			case "RIPPLE":
				return RIPPLE;
			case "LITECOIN":
				return LITECOIN;
			case "FTSE100":
				return FTSE_100;
			case "CAC40":
				return CAC_40;
			case "DAX":
				return DAX;
			case "SP500":
				return SP_500;
			case "DOWJONES":
				return DOW_JONES;
			case "HANGSENG":
				return HANG_SENG;
			case "ASX200":
				return ASX_200;
			case "EUROSTOXX50":
				return EURO_STOXX_50;
			case "NASDAQ100":
				return NASDAQ_100;
			case "NIKKEI225":
				return NIKKEI_225;
			case "USDOLLARINDEX":
				return US_DOLLAR_INDEX;
			case "BRENTCRUDEOIL":
				return BRENT_CRUDE_OIL;
			case "USCRUDEOIL":
				return US_CRUDE_OIL;
			default:
				throw new IllegalArgumentException("Unknown instrument: '" + name + "'");
		}
	}

}
