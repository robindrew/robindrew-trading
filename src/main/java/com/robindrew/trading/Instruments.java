package com.robindrew.trading;

import static com.robindrew.trading.InstrumentType.COMMODITIES;
import static com.robindrew.trading.InstrumentType.CURRENCIES;
import static com.robindrew.trading.InstrumentType.INDICES;

public class Instruments {

	/** AUD/CAD. */
	public static final IInstrument AUD_CAD = new Instrument("AUDCAD", CURRENCIES);
	/** AUD/CHF. */
	public static final IInstrument AUD_CHF = new Instrument("AUDCHF", CURRENCIES);
	/** AUD/HKD. */
	public static final IInstrument AUD_HKD = new Instrument("AUDHKD", CURRENCIES);
	/** AUD/JPY. */
	public static final IInstrument AUD_JPY = new Instrument("AUDJPY", CURRENCIES);
	/** AUD/NZD. */
	public static final IInstrument AUD_NZD = new Instrument("AUDNZD", CURRENCIES);
	/** AUD/SGD. */
	public static final IInstrument AUD_SGD = new Instrument("AUDSGD", CURRENCIES);
	/** AUD/USD. */
	public static final IInstrument AUD_USD = new Instrument("AUDUSD", CURRENCIES);

	/** CAD/CHF. */
	public static final IInstrument CAD_CHF = new Instrument("CADCHF", CURRENCIES);
	/** CAD/HKD. */
	public static final IInstrument CAD_HKD = new Instrument("CADHKD", CURRENCIES);
	/** CAD/JPY. */
	public static final IInstrument CAD_JPY = new Instrument("CADJPY", CURRENCIES);
	/** CAD/SGD. */
	public static final IInstrument CAD_SGD = new Instrument("CADSGD", CURRENCIES);

	/** CHF/HKD. */
	public static final IInstrument CHF_HKD = new Instrument("CHFHKD", CURRENCIES);
	/** CHF/JPY. */
	public static final IInstrument CHF_JPY = new Instrument("CHFJPY", CURRENCIES);
	/** CHF/ZAR. */
	public static final IInstrument CHF_ZAR = new Instrument("CHFZAR", CURRENCIES);

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
	/** EUR/HKD. */
	public static final IInstrument EUR_HKD = new Instrument("EURHKD", CURRENCIES);
	/** EUR/SGD. */
	public static final IInstrument EUR_SGD = new Instrument("EURSGD", CURRENCIES);
	/** EUR/ZAR. */
	public static final IInstrument EUR_ZAR = new Instrument("EURZAR", CURRENCIES);

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
	/** GBP/HKD. */
	public static final IInstrument GBP_HKD = new Instrument("GBPHKD", CURRENCIES);
	/** GBP/PLN. */
	public static final IInstrument GBP_PLN = new Instrument("GBPPLN", CURRENCIES);
	/** GBP/SGD. */
	public static final IInstrument GBP_SGD = new Instrument("GBPSGD", CURRENCIES);
	/** GBP/ZAR. */
	public static final IInstrument GBP_ZAR = new Instrument("GBPZAR", CURRENCIES);

	/** HKD/JPY. */
	public static final IInstrument HKD_JPY = new Instrument("HKDJPY", CURRENCIES);

	/** NZD/CAD. */
	public static final IInstrument NZD_CAD = new Instrument("NZDCAD", CURRENCIES);
	/** NZD/CHF. */
	public static final IInstrument NZD_CHF = new Instrument("NZDCHF", CURRENCIES);
	/** NZD/JPY. */
	public static final IInstrument NZD_JPY = new Instrument("NZDJPY", CURRENCIES);
	/** NZD/USD. */
	public static final IInstrument NZD_USD = new Instrument("NZDUSD", CURRENCIES);
	/** NZD/HKD. */
	public static final IInstrument NZD_HKD = new Instrument("NZDHKD", CURRENCIES);
	/** NZD/SGD. */
	public static final IInstrument NZD_SGD = new Instrument("NZDSGD", CURRENCIES);

	/** SGD/JPY. */
	public static final IInstrument SGD_JPY = new Instrument("SGDJPY", CURRENCIES);
	/** SGD/CHF. */
	public static final IInstrument SGD_CHF = new Instrument("SGDCHF", CURRENCIES);
	/** SGD/HKD. */
	public static final IInstrument SGD_HKD = new Instrument("SGDHKD", CURRENCIES);

	/** SPX/USD. */
	public static final IInstrument SPX_USD = new Instrument("SPXUSD", CURRENCIES);

	/** TRY/JPY. */
	public static final IInstrument TRY_JPY = new Instrument("TRYJPY", CURRENCIES);

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
	/** USD/CNH. */
	public static final IInstrument USD_CNH = new Instrument("USDCNH", CURRENCIES);
	/** USD/INR. */
	public static final IInstrument USD_INR = new Instrument("USDINR", CURRENCIES);
	/** USD/SAR. */
	public static final IInstrument USD_SAR = new Instrument("USDSAR", CURRENCIES);
	/** USD/THB. */
	public static final IInstrument USD_THB = new Instrument("USDTHB", CURRENCIES);

	/** WTI/USD. */
	public static final IInstrument WTI_USD = new Instrument("WTIUSD", CURRENCIES);

	/** ZAR/JPY. */
	public static final IInstrument ZAR_JPY = new Instrument("ZARJPY", CURRENCIES);

	/** XAG_AUD (Silver/AUD). */
	public static final IInstrument XAG_AUD = new Instrument("XAGAUD", CURRENCIES);
	/** XAG_CAD (Silver/CAD). */
	public static final IInstrument XAG_CAD = new Instrument("XAGCAD", CURRENCIES);
	/** XAG_CHF (Silver/CHF). */
	public static final IInstrument XAG_CHF = new Instrument("XAGCHF", CURRENCIES);
	/** XAG_EUR (Silver/EUR). */
	public static final IInstrument XAG_EUR = new Instrument("XAGEUR", CURRENCIES);
	/** XAG_GBP (Silver/GBP). */
	public static final IInstrument XAG_GBP = new Instrument("XAGGBP", CURRENCIES);
	/** XAG_HKD (Silver/HKD). */
	public static final IInstrument XAG_HKD = new Instrument("XAGHKD", CURRENCIES);
	/** XAG_JPY (Silver/JPY). */
	public static final IInstrument XAG_JPY = new Instrument("XAGJPY", CURRENCIES);
	/** XAG_NZD (Silver/NZD). */
	public static final IInstrument XAG_NZD = new Instrument("XAGNZD", CURRENCIES);
	/** XAG_SGD (Silver/SGD). */
	public static final IInstrument XAG_SGD = new Instrument("XAGSGD", CURRENCIES);
	/** XAG_USD (Silver). */
	public static final IInstrument XAG_USD = new Instrument("XAGUSD", CURRENCIES);

	/** XAU_AUD (Gold/AUD). */
	public static final IInstrument XAU_AUD = new Instrument("XAUAUD", CURRENCIES);
	/** XAU_CAD (Gold/CAD). */
	public static final IInstrument XAU_CAD = new Instrument("XAUCAD", CURRENCIES);
	/** XAU_CHF (Gold/CHF). */
	public static final IInstrument XAU_CHF = new Instrument("XAUCHF", CURRENCIES);
	/** XAU_EUR (Gold/EUR). */
	public static final IInstrument XAU_EUR = new Instrument("XAUEUR", CURRENCIES);
	/** XAU_GBP (Gold/GBP). */
	public static final IInstrument XAU_GBP = new Instrument("XAUGBP", CURRENCIES);
	/** XAU_HKD (Gold/HKD). */
	public static final IInstrument XAU_HKD = new Instrument("XAUHKD", CURRENCIES);
	/** XAU_JPY (Gold/JPY). */
	public static final IInstrument XAU_JPY = new Instrument("XAUJPY", CURRENCIES);
	/** XAU_NZD (Gold/NZD). */
	public static final IInstrument XAU_NZD = new Instrument("XAUNZD", CURRENCIES);
	/** XAU_SGD (Gold/SGD). */
	public static final IInstrument XAU_SGD = new Instrument("XAUSGD", CURRENCIES);
	/** XAU_USD (Gold). */
	public static final IInstrument XAU_USD = new Instrument("XAUUSD", CURRENCIES);
	/** XAU_XAG (Gold/Silver). */
	public static final IInstrument XAU_XAG = new Instrument("XAUXAG", CURRENCIES);

	/** XPD_USD (Palladium). */
	public static final IInstrument XPD_USD = new Instrument("XPDUSD", CURRENCIES);
	/** XPT_USD (Platinum). */
	public static final IInstrument XPT_USD = new Instrument("XPTUSD", CURRENCIES);

	/** Bitcoin. */
	public static final IInstrument BITCOIN = new Instrument("BITCOIN", CURRENCIES);
	/** Ether. */
	public static final IInstrument ETHER = new Instrument("ETHER", CURRENCIES);
	/** Ripple. */
	public static final IInstrument RIPPLE = new Instrument("RIPPLE", CURRENCIES);
	/** Litecoin. */
	public static final IInstrument LITECOIN = new Instrument("LITECOIN", CURRENCIES);

	/** FTSE 100 (UK 100). */
	public static final IInstrument FTSE_100 = new Instrument("FTSE100", INDICES);
	/** CAC 40 (France 40). */
	public static final IInstrument CAC_40 = new Instrument("CAC40", INDICES);
	/** CHINA A50. */
	public static final IInstrument CHINA_A50 = new Instrument("CHINAA50", INDICES);
	/** DAX (German 30). */
	public static final IInstrument DAX_30 = new Instrument("DAX30", INDICES);
	/** S&amp;P 500. */
	public static final IInstrument SP_500 = new Instrument("SP500", INDICES);
	/** DOW JONES. */
	public static final IInstrument DOW_JONES_30 = new Instrument("DOWJONES", INDICES);
	/** NIFTY_50 (India 50). */
	public static final IInstrument NIFTY_50 = new Instrument("NIFTY50", INDICES);
	/** HANG SENG. */
	public static final IInstrument HANG_SENG_33 = new Instrument("HANGSENG33", INDICES);
	/** AEX 25 (Netherlands 25) */
	public static final IInstrument AEX_25 = new Instrument("AEX25", INDICES);
	/** TAIEX (Taiwan ALL) */
	public static final IInstrument TAIEX = new Instrument("TAIEX", INDICES);
	/** ASX 200 (Australia 200). */
	public static final IInstrument ASX_200 = new Instrument("ASX200", INDICES);
	/** STRAITS TIMES (Singapore 30). */
	public static final IInstrument STRAITS_TIMES_30 = new Instrument("STRAITSTIMES30", INDICES);
	/** EURO STOXX 50. */
	public static final IInstrument EURO_STOXX_50 = new Instrument("EUROSTOXX50", INDICES);
	/** NASDAQ 100. */
	public static final IInstrument NASDAQ_100 = new Instrument("NASDAQ100", INDICES);
	/** NIKKEI 225. */
	public static final IInstrument NIKKEI_225 = new Instrument("NIKKEI225", INDICES);
	/** US DOLLAR INDEX. */
	public static final IInstrument US_DOLLAR_INDEX = new Instrument("USDOLLARINDEX", INDICES);
	/** RUSSELL 2000. */
	public static final IInstrument RUSSELL_2000 = new Instrument("RUSSELL2000", INDICES);

	/** UK GILT (10Y). */
	public static final IInstrument UK_GILT_10Y = new Instrument("UKGILT10Y", INDICES);
	/** German BUND (10Y). */
	public static final IInstrument DE_BUND_10Y = new Instrument("DEBUND10Y", INDICES);
	/** US T-NOTE (2Y). */
	public static final IInstrument US_TNOTE_2Y = new Instrument("USTNOTE2Y", INDICES);
	/** US T-NOTE (5Y). */
	public static final IInstrument US_TNOTE_5Y = new Instrument("USTNOTE5Y", INDICES);
	/** US T-NOTE (10Y). */
	public static final IInstrument US_TNOTE_10Y = new Instrument("USTNOTE10Y", INDICES);
	/** US T-BOND (30Y). */
	public static final IInstrument US_TBOND_30Y = new Instrument("USTBOND30Y", INDICES);

	/** BRENT CRUDE OIL (Brent Crude). */
	public static final IInstrument BRENT_CRUDE_OIL = new Instrument("BRENTCRUDEOIL", COMMODITIES);
	/** US CRUDE OIL (West Texas Intermediate). */
	public static final IInstrument US_CRUDE_OIL = new Instrument("USCRUDEOIL", COMMODITIES);
	/** NATURAL GAS. */
	public static final IInstrument NATURAL_GAS = new Instrument("NATURALGAS", COMMODITIES);

	/** GOLD. */
	public static final IInstrument GOLD = new Instrument("GOLD", COMMODITIES);
	/** SILVER. */
	public static final IInstrument SILVER = new Instrument("SILVER", COMMODITIES);
	/** PLATINUM. */
	public static final IInstrument PLATINUM = new Instrument("PLATINUM", COMMODITIES);
	/** PALLADIUM. */
	public static final IInstrument PALLADIUM = new Instrument("PALLADIUM", COMMODITIES);
	/** COPPER. */
	public static final IInstrument COPPER = new Instrument("COPPER", COMMODITIES);

	/** SUGAR. */
	public static final IInstrument SUGAR = new Instrument("SUGAR", COMMODITIES);
	/** CORN. */
	public static final IInstrument CORN = new Instrument("CORN", COMMODITIES);
	/** WHEAT. */
	public static final IInstrument WHEAT = new Instrument("WHEAT", COMMODITIES);
	/** SOYBEANS. */
	public static final IInstrument SOYBEANS = new Instrument("SOYBEANS", COMMODITIES);

	public static IInstrument valueOf(String name) {
		switch (name) {
		case "AUDCAD":
			return AUD_CAD;
		case "AUDCHF":
			return AUD_CHF;
		case "AUDHKD":
			return AUD_HKD;
		case "AUDJPY":
			return AUD_JPY;
		case "AUDNZD":
			return AUD_NZD;
		case "AUDSGD":
			return AUD_SGD;
		case "AUDUSD":
			return AUD_USD;
		case "CADCHF":
			return CAD_CHF;
		case "CADHKD":
			return CAD_HKD;
		case "CADJPY":
			return CAD_JPY;
		case "CADSGD":
			return CAD_SGD;
		case "CHFHKD":
			return CHF_HKD;
		case "CHFJPY":
			return CHF_JPY;
		case "CHFZAR":
			return CHF_ZAR;
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
		case "EURHKD":
			return EUR_HKD;
		case "EURSGD":
			return EUR_SGD;
		case "EURZAR":
			return EUR_ZAR;
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
		case "GBPHKD":
			return GBP_HKD;
		case "GBPPLN":
			return GBP_PLN;
		case "GBPSGD":
			return GBP_SGD;
		case "GBPZAR":
			return GBP_ZAR;
		case "HKDJPY":
			return HKD_JPY;
		case "NZDCAD":
			return NZD_CAD;
		case "NZDCHF":
			return NZD_CHF;
		case "NZDJPY":
			return NZD_JPY;
		case "NZDUSD":
			return NZD_USD;
		case "NZDHKD":
			return NZD_HKD;
		case "NZDSGD":
			return NZD_SGD;
		case "SGDJPY":
			return SGD_JPY;
		case "SGDCHF":
			return SGD_CHF;
		case "SGDHKD":
			return SGD_HKD;
		case "SPXUSD":
			return SPX_USD;
		case "TRYJPY":
			return TRY_JPY;
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
		case "USDCNH":
			return USD_CNH;
		case "USDINR":
			return USD_INR;
		case "USDSAR":
			return USD_SAR;
		case "USDTHB":
			return USD_THB;
		case "WTIUSD":
			return WTI_USD;
		case "ZARJPY":
			return ZAR_JPY;
		case "XAGAUD":
			return XAG_AUD;
		case "XAGCAD":
			return XAG_CAD;
		case "XAGCHF":
			return XAG_CHF;
		case "XAGEUR":
			return XAG_EUR;
		case "XAGGBP":
			return XAG_GBP;
		case "XAGHKD":
			return XAG_HKD;
		case "XAGJPY":
			return XAG_JPY;
		case "XAGNZD":
			return XAG_NZD;
		case "XAGSGD":
			return XAG_SGD;
		case "XAGUSD":
			return XAG_USD;
		case "XAUAUD":
			return XAU_AUD;
		case "XAUCAD":
			return XAU_CAD;
		case "XAUCHF":
			return XAU_CHF;
		case "XAUEUR":
			return XAU_EUR;
		case "XAUGBP":
			return XAU_GBP;
		case "XAUHKD":
			return XAU_HKD;
		case "XAUJPY":
			return XAU_JPY;
		case "XAUNZD":
			return XAU_NZD;
		case "XAUSGD":
			return XAU_SGD;
		case "XAUUSD":
			return XAU_USD;
		case "XAUXAG":
			return XAU_XAG;
		case "XPDUSD":
			return XPD_USD;
		case "XPTUSD":
			return XPT_USD;
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
		case "CHINAA50":
			return CHINA_A50;
		case "DAX30":
			return DAX_30;
		case "SP500":
			return SP_500;
		case "DOWJONES":
			return DOW_JONES_30;
		case "NIFTY50":
			return NIFTY_50;
		case "HANGSENG33":
			return HANG_SENG_33;
		case "AEX25":
			return AEX_25;
		case "TAIEX":
			return TAIEX;
		case "ASX200":
			return ASX_200;
		case "STRAITSTIMES30":
			return STRAITS_TIMES_30;
		case "EUROSTOXX50":
			return EURO_STOXX_50;
		case "NASDAQ100":
			return NASDAQ_100;
		case "NIKKEI225":
			return NIKKEI_225;
		case "USDOLLARINDEX":
			return US_DOLLAR_INDEX;
		case "RUSSELL2000":
			return RUSSELL_2000;
		case "UKGILT10Y":
			return UK_GILT_10Y;
		case "DEBUND10Y":
			return DE_BUND_10Y;
		case "USTNOTE2Y":
			return US_TNOTE_2Y;
		case "USTNOTE5Y":
			return US_TNOTE_5Y;
		case "USTNOTE10Y":
			return US_TNOTE_10Y;
		case "USTBOND30Y":
			return US_TBOND_30Y;
		case "BRENTCRUDEOIL":
			return BRENT_CRUDE_OIL;
		case "USCRUDEOIL":
			return US_CRUDE_OIL;
		case "NATURALGAS":
			return NATURAL_GAS;
		case "GOLD":
			return GOLD;
		case "SILVER":
			return SILVER;
		case "PLATINUM":
			return PLATINUM;
		case "PALLADIUM":
			return PALLADIUM;
		case "COPPER":
			return COPPER;
		case "SUGAR":
			return SUGAR;
		case "CORN":
			return CORN;
		case "WHEAT":
			return WHEAT;
		case "SOYBEANS":
			return SOYBEANS;
		default:
			throw new IllegalArgumentException("Unknown instrument: '" + name + "'");
		}
	}

}
