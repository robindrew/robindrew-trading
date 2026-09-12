package com.robindrew.trading.price.candle.io.list.filter;

import static com.robindrew.trading.util.text.TradingStrings.number;

import com.robindrew.trading.price.candle.IPriceCandle;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PriceCandleListDuplicateFilter implements IPriceCandleListFilter {

    @Override
    public List<IPriceCandle> filter(List<? extends IPriceCandle> candles) {
        int size = candles.size();
        Set<IPriceCandle> set = new LinkedHashSet<>(candles);
        int duplicates = size - set.size();
        if (duplicates > 0) {
            log.warn("Filtered " + number(duplicates) + " duplicate candles");
        }
        return new ArrayList<>(set);
    }
}
