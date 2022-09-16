package tutocurrencyjavafx.services;

import java.io.IOException;
import java.util.List;
import tutocurrencyjavafx.models.HistoricalCurrency;
import tutocurrencyjavafx.repository.Converter;

/**
 *
 * @author amine gasa
 */
public class HistoricalCurrencyService {
    private final Converter converter;

    public HistoricalCurrencyService(Converter converter) {
        this.converter = converter;
    }
    public List<HistoricalCurrency> HistoricalDataCurrency(String base
            ,Integer durationDay,String symbol) throws IOException{
        return converter.getHistoricalVlues(base, durationDay, symbol);
    }
}
