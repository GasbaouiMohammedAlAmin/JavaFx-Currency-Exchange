package tutocurrencyjavafx.repository;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;
import tutocurrencyjavafx.models.Currency;
import tutocurrencyjavafx.models.HistoricalCurrency;

/**
 *
 * @author amine gasa
 */
public interface Converter {
    Double convert(String from,String to,Double value) 
            throws MalformedURLException, IOException;
    List<Currency> getAllCurrencies() throws MalformedURLException,IOException;
    List<String> getAllSymbols(List<Currency> list);
    List<HistoricalCurrency> getHistoricalVlues(String base,Integer durationDays,String symbol)
            throws MalformedURLException,IOException;
}
