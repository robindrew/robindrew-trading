package com.robindrew.trading.provider.histdata;

import static com.robindrew.trading.Instruments.ASX_200;
import static com.robindrew.trading.Instruments.AUD_CAD;
import static com.robindrew.trading.Instruments.AUD_CHF;
import static com.robindrew.trading.Instruments.AUD_JPY;
import static com.robindrew.trading.Instruments.AUD_NZD;
import static com.robindrew.trading.Instruments.AUD_USD;
import static com.robindrew.trading.Instruments.BRENT_CRUDE_OIL;
import static com.robindrew.trading.Instruments.CAC_40;
import static com.robindrew.trading.Instruments.CAD_CHF;
import static com.robindrew.trading.Instruments.CAD_JPY;
import static com.robindrew.trading.Instruments.CHF_JPY;
import static com.robindrew.trading.Instruments.DAX;
import static com.robindrew.trading.Instruments.EURO_STOXX_50;
import static com.robindrew.trading.Instruments.EUR_AUD;
import static com.robindrew.trading.Instruments.EUR_CAD;
import static com.robindrew.trading.Instruments.EUR_CHF;
import static com.robindrew.trading.Instruments.EUR_CZK;
import static com.robindrew.trading.Instruments.EUR_DKK;
import static com.robindrew.trading.Instruments.EUR_GBP;
import static com.robindrew.trading.Instruments.EUR_HUF;
import static com.robindrew.trading.Instruments.EUR_JPY;
import static com.robindrew.trading.Instruments.EUR_NOK;
import static com.robindrew.trading.Instruments.EUR_NZD;
import static com.robindrew.trading.Instruments.EUR_PLN;
import static com.robindrew.trading.Instruments.EUR_SEK;
import static com.robindrew.trading.Instruments.EUR_TRY;
import static com.robindrew.trading.Instruments.EUR_USD;
import static com.robindrew.trading.Instruments.FTSE_100;
import static com.robindrew.trading.Instruments.GBP_AUD;
import static com.robindrew.trading.Instruments.GBP_CAD;
import static com.robindrew.trading.Instruments.GBP_CHF;
import static com.robindrew.trading.Instruments.GBP_JPY;
import static com.robindrew.trading.Instruments.GBP_NZD;
import static com.robindrew.trading.Instruments.GBP_USD;
import static com.robindrew.trading.Instruments.HANG_SENG;
import static com.robindrew.trading.Instruments.NASDAQ_100;
import static com.robindrew.trading.Instruments.NIKKEI_225;
import static com.robindrew.trading.Instruments.NZD_CAD;
import static com.robindrew.trading.Instruments.NZD_CHF;
import static com.robindrew.trading.Instruments.NZD_JPY;
import static com.robindrew.trading.Instruments.NZD_USD;
import static com.robindrew.trading.Instruments.SGD_JPY;
import static com.robindrew.trading.Instruments.SP_500;
import static com.robindrew.trading.Instruments.USD_CAD;
import static com.robindrew.trading.Instruments.USD_CHF;
import static com.robindrew.trading.Instruments.USD_CZK;
import static com.robindrew.trading.Instruments.USD_DKK;
import static com.robindrew.trading.Instruments.USD_HKD;
import static com.robindrew.trading.Instruments.USD_HUF;
import static com.robindrew.trading.Instruments.USD_JPY;
import static com.robindrew.trading.Instruments.USD_MXN;
import static com.robindrew.trading.Instruments.USD_NOK;
import static com.robindrew.trading.Instruments.USD_PLN;
import static com.robindrew.trading.Instruments.USD_SEK;
import static com.robindrew.trading.Instruments.USD_SGD;
import static com.robindrew.trading.Instruments.USD_TRY;
import static com.robindrew.trading.Instruments.USD_ZAR;
import static com.robindrew.trading.Instruments.US_CRUDE_OIL;
import static com.robindrew.trading.Instruments.US_DOLLAR_INDEX;
import static com.robindrew.trading.Instruments.XAG_USD;
import static com.robindrew.trading.Instruments.XAU_AUD;
import static com.robindrew.trading.Instruments.XAU_CHF;
import static com.robindrew.trading.Instruments.XAU_EUR;
import static com.robindrew.trading.Instruments.XAU_GBP;
import static com.robindrew.trading.Instruments.XAU_USD;
import static com.robindrew.trading.Instruments.ZAR_JPY;

import com.robindrew.trading.IInstrument;
import com.robindrew.trading.Instrument;
import com.robindrew.trading.price.precision.IPricePrecision;
import com.robindrew.trading.price.precision.PricePrecision;

public class HistDataInstrument extends Instrument {

	/** AUD/CAD. */
	public static final HistDataInstrument AUDCAD = new HistDataInstrument("AUDCAD", AUD_CAD, 6, 40000, 190000);
	/** AUD/CHF. */
	public static final HistDataInstrument AUDCHF = new HistDataInstrument("AUDCHF", AUD_CHF, 6, 40000, 190000);
	/** AUD/JPY. */
	public static final HistDataInstrument AUDJPY = new HistDataInstrument("AUDJPY", AUD_JPY, 6, 40000, 190000);
	/** AUD/NZD. */
	public static final HistDataInstrument AUDNZD = new HistDataInstrument("AUDNZD", AUD_NZD, 6, 40000, 190000);
	/** AUD/USD. */
	public static final HistDataInstrument AUDUSD = new HistDataInstrument("AUDUSD", AUD_USD, 6, 40000, 190000);
	/** CAD/CHF. */
	public static final HistDataInstrument CADCHF = new HistDataInstrument("CADCHF", CAD_CHF, 6, 40000, 190000);
	/** CAD/JPY. */
	public static final HistDataInstrument CADJPY = new HistDataInstrument("CADJPY", CAD_JPY, 6, 40000, 190000);
	/** CHF/JPY. */
	public static final HistDataInstrument CHFJPY = new HistDataInstrument("CHFJPY", CHF_JPY, 6, 40000, 190000);
	/** EUR/AUD. */
	public static final HistDataInstrument EURAUD = new HistDataInstrument("EURAUD", EUR_AUD, 6, 40000, 190000);
	/** EUR/CAD. */
	public static final HistDataInstrument EURCAD = new HistDataInstrument("EURCAD", EUR_CAD, 6, 40000, 190000);
	/** EUR/CHF. */
	public static final HistDataInstrument EURCHF = new HistDataInstrument("EURCHF", EUR_CHF, 6, 40000, 190000);
	/** EUR/CZK. */
	public static final HistDataInstrument EURCZK = new HistDataInstrument("EURCZK", EUR_CZK, 6, 40000, 190000);
	/** EUR/DKK. */
	public static final HistDataInstrument EURDKK = new HistDataInstrument("EURDKK", EUR_DKK, 6, 40000, 190000);
	/** EUR/GBP. */
	public static final HistDataInstrument EURGBP = new HistDataInstrument("EURGBP", EUR_GBP, 6, 40000, 190000);
	/** EUR/HUF. */
	public static final HistDataInstrument EURHUF = new HistDataInstrument("EURHUF", EUR_HUF, 6, 40000, 190000);
	/** EUR/JPY. */
	public static final HistDataInstrument EURJPY = new HistDataInstrument("EURJPY", EUR_JPY, 6, 40000, 190000);
	/** EUR/NOK. */
	public static final HistDataInstrument EURNOK = new HistDataInstrument("EURNOK", EUR_NOK, 6, 40000, 190000);
	/** EUR/NZD. */
	public static final HistDataInstrument EURNZD = new HistDataInstrument("EURNZD", EUR_NZD, 6, 40000, 190000);
	/** EUR/PLN. */
	public static final HistDataInstrument EURPLN = new HistDataInstrument("EURPLN", EUR_PLN, 6, 40000, 190000);
	/** EUR/SEK. */
	public static final HistDataInstrument EURSEK = new HistDataInstrument("EURSEK", EUR_SEK, 6, 40000, 190000);
	/** EUR/TRY. */
	public static final HistDataInstrument EURTRY = new HistDataInstrument("EURTRY", EUR_TRY, 6, 40000, 190000);
	/** EUR/USD. */
	public static final HistDataInstrument EURUSD = new HistDataInstrument("EURUSD", EUR_USD, 6, 40000, 190000);
	/** GBP/AUD. */
	public static final HistDataInstrument GBPAUD = new HistDataInstrument("GBPAUD", GBP_AUD, 6, 40000, 190000);
	/** GBP/CAD. */
	public static final HistDataInstrument GBPCAD = new HistDataInstrument("GBPCAD", GBP_CAD, 6, 40000, 190000);
	/** GBP/CHF. */
	public static final HistDataInstrument GBPCHF = new HistDataInstrument("GBPCHF", GBP_CHF, 6, 40000, 190000);
	/** GBP/JPY. */
	public static final HistDataInstrument GBPJPY = new HistDataInstrument("GBPJPY", GBP_JPY, 6, 40000, 190000);
	/** GBP/NZD. */
	public static final HistDataInstrument GBPNZD = new HistDataInstrument("GBPNZD", GBP_NZD, 6, 40000, 190000);
	/** GBP/USD. */
	public static final HistDataInstrument GBPUSD = new HistDataInstrument("GBPUSD", GBP_USD, 6, 40000, 190000);
	/** NZD/CAD. */
	public static final HistDataInstrument NZDCAD = new HistDataInstrument("NZDCAD", NZD_CAD, 6, 40000, 190000);
	/** NZD/CHF. */
	public static final HistDataInstrument NZDCHF = new HistDataInstrument("NZDCHF", NZD_CHF, 6, 40000, 190000);
	/** NZD/JPY. */
	public static final HistDataInstrument NZDJPY = new HistDataInstrument("NZDJPY", NZD_JPY, 6, 40000, 190000);
	/** NZD/USD. */
	public static final HistDataInstrument NZDUSD = new HistDataInstrument("NZDUSD", NZD_USD, 6, 40000, 190000);
	/** SGD/JPY. */
	public static final HistDataInstrument SGDJPY = new HistDataInstrument("SGDJPY", SGD_JPY, 6, 40000, 190000);
	/** USD/CAD. */
	public static final HistDataInstrument USDCAD = new HistDataInstrument("USDCAD", USD_CAD, 6, 40000, 190000);
	/** USD/CHF. */
	public static final HistDataInstrument USDCHF = new HistDataInstrument("USDCHF", USD_CHF, 6, 40000, 190000);
	/** USD/CZK. */
	public static final HistDataInstrument USDCZK = new HistDataInstrument("USDCZK", USD_CZK, 6, 40000, 190000);
	/** USD/DKK. */
	public static final HistDataInstrument USDDKK = new HistDataInstrument("USDDKK", USD_DKK, 6, 40000, 190000);
	/** USD/HKD. */
	public static final HistDataInstrument USDHKD = new HistDataInstrument("USDHKD", USD_HKD, 6, 40000, 190000);
	/** USD/HUF. */
	public static final HistDataInstrument USDHUF = new HistDataInstrument("USDHUF", USD_HUF, 6, 40000, 190000);
	/** USD/JPY. */
	public static final HistDataInstrument USDJPY = new HistDataInstrument("USDJPY", USD_JPY, 6, 40000, 190000);
	/** USD/MXN. */
	public static final HistDataInstrument USDMXN = new HistDataInstrument("USDMXN", USD_MXN, 6, 40000, 190000);
	/** USD/NOK. */
	public static final HistDataInstrument USDNOK = new HistDataInstrument("USDNOK", USD_NOK, 6, 40000, 190000);
	/** USD/PLN. */
	public static final HistDataInstrument USDPLN = new HistDataInstrument("USDPLN", USD_PLN, 6, 40000, 190000);
	/** USD/SEK. */
	public static final HistDataInstrument USDSEK = new HistDataInstrument("USDSEK", USD_SEK, 6, 40000, 190000);
	/** USD/SGD. */
	public static final HistDataInstrument USDSGD = new HistDataInstrument("USDSGD", USD_SGD, 6, 40000, 190000);
	/** USD/TRY. */
	public static final HistDataInstrument USDTRY = new HistDataInstrument("USDTRY", USD_TRY, 6, 40000, 190000);
	/** USD/ZAR. */
	public static final HistDataInstrument USDZAR = new HistDataInstrument("USDZAR", USD_ZAR, 6, 40000, 190000);
	/** XAG/USD. */
	public static final HistDataInstrument XAGUSD = new HistDataInstrument("XAGUSD", XAG_USD, 6, 40000, 190000);
	/** XAU/AUD. */
	public static final HistDataInstrument XAUAUD = new HistDataInstrument("XAUAUD", XAU_AUD, 6, 40000, 190000);
	/** XAU/CHF. */
	public static final HistDataInstrument XAUCHF = new HistDataInstrument("XAUCHF", XAU_CHF, 6, 40000, 190000);
	/** XAU/EUR. */
	public static final HistDataInstrument XAUEUR = new HistDataInstrument("XAUEUR", XAU_EUR, 6, 40000, 190000);
	/** XAU/GBP. */
	public static final HistDataInstrument XAUGBP = new HistDataInstrument("XAUGBP", XAU_GBP, 6, 40000, 190000);
	/** XAU/USD. */
	public static final HistDataInstrument XAUUSD = new HistDataInstrument("XAUUSD", XAU_USD, 6, 40000, 190000);
	/** ZAR/JPY. */
	public static final HistDataInstrument ZARJPY = new HistDataInstrument("ZARJPY", ZAR_JPY, 6, 40000, 190000);

	/** UKX/GBP. */
	public static final HistDataInstrument UKXGBP = new HistDataInstrument("UKXGBP", FTSE_100, 6, 40000, 190000);
	/** SPX/USD. */
	public static final HistDataInstrument SPXUSD = new HistDataInstrument("SPXUSD", SP_500, 6, 40000, 190000);
	/** GRX/EUR. */
	public static final HistDataInstrument GRXEUR = new HistDataInstrument("GRXEUR", DAX, 6, 40000, 190000);
	/** HKX/HKD. */
	public static final HistDataInstrument HKXHKD = new HistDataInstrument("HKXHKD", HANG_SENG, 6, 40000, 190000);
	/** AUX/AUD. */
	public static final HistDataInstrument AUXAUD = new HistDataInstrument("AUXAUD", ASX_200, 6, 40000, 190000);
	/** ETX/EUR. */
	public static final HistDataInstrument ETXEUR = new HistDataInstrument("ETXEUR", EURO_STOXX_50, 6, 40000, 190000);
	/** JPX/JPY. */
	public static final HistDataInstrument JPXJPY = new HistDataInstrument("JPXJPY", NIKKEI_225, 6, 40000, 190000);
	/** NSX/USD. */
	public static final HistDataInstrument NSXUSD = new HistDataInstrument("NSXUSD", NASDAQ_100, 6, 40000, 190000);
	/** UDX/USD. */
	public static final HistDataInstrument UDXUSD = new HistDataInstrument("UDXUSD", US_DOLLAR_INDEX, 6, 40000, 190000);
	/** FRX/EUR. */
	public static final HistDataInstrument FRXEUR = new HistDataInstrument("FRXEUR", CAC_40, 6, 40000, 190000);

	/** BCO/USD. */
	public static final HistDataInstrument BCOUSD = new HistDataInstrument("BCOUSD", BRENT_CRUDE_OIL, 6, 40000, 190000);
	/** WTI/USD. */
	public static final HistDataInstrument WTIUSD = new HistDataInstrument("WTIUSD", US_CRUDE_OIL, 6, 40000, 190000);

	public static HistDataInstrument valueOf(String name) {
		switch (name) {
			case "AUDCAD":
				return AUDCAD;
			case "AUDCHF":
				return AUDCHF;
			case "AUDJPY":
				return AUDJPY;
			case "AUDNZD":
				return AUDNZD;
			case "AUDUSD":
				return AUDUSD;
			case "CADCHF":
				return CADCHF;
			case "CADJPY":
				return CADJPY;
			case "CHFJPY":
				return CHFJPY;
			case "EURAUD":
				return EURAUD;
			case "EURCAD":
				return EURCAD;
			case "EURCHF":
				return EURCHF;
			case "EURCZK":
				return EURCZK;
			case "EURDKK":
				return EURDKK;
			case "EURGBP":
				return EURGBP;
			case "EURHUF":
				return EURHUF;
			case "EURJPY":
				return EURJPY;
			case "EURNOK":
				return EURNOK;
			case "EURNZD":
				return EURNZD;
			case "EURPLN":
				return EURPLN;
			case "EURSEK":
				return EURSEK;
			case "EURTRY":
				return EURTRY;
			case "EURUSD":
				return EURUSD;
			case "GBPAUD":
				return GBPAUD;
			case "GBPCAD":
				return GBPCAD;
			case "GBPCHF":
				return GBPCHF;
			case "GBPJPY":
				return GBPJPY;
			case "GBPNZD":
				return GBPNZD;
			case "GBPUSD":
				return GBPUSD;
			case "NZDCAD":
				return NZDCAD;
			case "NZDCHF":
				return NZDCHF;
			case "NZDJPY":
				return NZDJPY;
			case "NZDUSD":
				return NZDUSD;
			case "SGDJPY":
				return SGDJPY;
			case "USDCAD":
				return USDCAD;
			case "USDCHF":
				return USDCHF;
			case "USDCZK":
				return USDCZK;
			case "USDDKK":
				return USDDKK;
			case "USDHKD":
				return USDHKD;
			case "USDHUF":
				return USDHUF;
			case "USDJPY":
				return USDJPY;
			case "USDMXN":
				return USDMXN;
			case "USDNOK":
				return USDNOK;
			case "USDPLN":
				return USDPLN;
			case "USDSEK":
				return USDSEK;
			case "USDSGD":
				return USDSGD;
			case "USDTRY":
				return USDTRY;
			case "USDZAR":
				return USDZAR;
			case "XAGUSD":
				return XAGUSD;
			case "XAUAUD":
				return XAUAUD;
			case "XAUCHF":
				return XAUCHF;
			case "XAUEUR":
				return XAUEUR;
			case "XAUGBP":
				return XAUGBP;
			case "XAUUSD":
				return XAUUSD;
			case "ZARJPY":
				return ZARJPY;
			case "UKXGBP":
				return UKXGBP;
			case "SPXUSD":
				return SPXUSD;
			case "GRXEUR":
				return GRXEUR;
			case "HKXHKD":
				return HKXHKD;
			case "AUXAUD":
				return AUXAUD;
			case "ETXEUR":
				return ETXEUR;
			case "JPXJPY":
				return JPXJPY;
			case "NSXUSD":
				return NSXUSD;
			case "UDXUSD":
				return UDXUSD;
			case "FRXEUR":
				return FRXEUR;
			case "BCOUSD":
				return BCOUSD;
			case "WTIUSD":
				return WTIUSD;
			default:
				throw new IllegalArgumentException("Unknown instrument: '" + name + "'");
		}
	}

	private final IPricePrecision precision;

	public HistDataInstrument(String name, IInstrument underlying, int decimalPlaces, int minPrice, int maxPrice) {
		super(name, underlying);
		this.precision = new PricePrecision(decimalPlaces, minPrice, maxPrice);
	}

	public IPricePrecision getPricePrecision() {
		return precision;
	}

}
