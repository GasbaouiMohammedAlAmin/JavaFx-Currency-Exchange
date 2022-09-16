package tutocurrencyjavafx.repository;

import java.io.IOException;
import java.net.MalformedURLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import tutocurrencyjavafx.connectionApi.ApiConnection;
import tutocurrencyjavafx.connectionApi.JsonConversion;
import tutocurrencyjavafx.models.Currency;
import tutocurrencyjavafx.models.HistoricalCurrency;

/**
 *
 * @author amine gasa
 */
public class ConverterImp implements Converter{
   private ApiConnection apiConnection;
   private JsonConversion jsonConversion;

    public ConverterImp(ApiConnection ApiConnection, JsonConversion JsonConversion) {
        this.apiConnection = ApiConnection;
        this.jsonConversion = JsonConversion;
    }
   
    @Override
    public Double convert(String from, String to, Double value)
            throws MalformedURLException, IOException {
      StringBuilder sb=apiConnection.getRate(from, to, value);
      Double result=jsonConversion.getDataConversion(sb);
    return result;}

    @Override
    public List<Currency> getAllCurrencies()
            throws MalformedURLException,IOException{
     List<Currency> listCurrencies=new ArrayList();
      StringBuilder sb=apiConnection.getSymbolsWithSignification();
      String SymbolsSignification[]=jsonConversion.getDataSymbols(sb);
        for (String line : SymbolsSignification) {
            String tmp[]=line.split(":");
            String symbol=tmp[0].replace("{", "").replace("\"", "");
            String signification=tmp[1].replace("}", "").replace("\"", "");
            Currency currency=new Currency(symbol, signification);
            listCurrencies.add(currency);
        }
    return listCurrencies;}

    @Override
    public List<String> getAllSymbols(List<Currency> list) {
      List<String> listSymbols=list.stream().map(currency->currency.getSymbol())
              .collect(Collectors.toList());
      Collections.sort(listSymbols);
    return listSymbols;}

    @Override
    public List<HistoricalCurrency> getHistoricalVlues(String base, Integer durationDays, String symbol) throws IOException {
     LocalDate today=LocalDate.now();
     LocalDate dayAgo=today.minusDays(durationDays);
     
     StringBuilder sb=apiConnection.getHistoricalCurrency(base, dayAgo, today, symbol);
     String historicalValue[]=jsonConversion.getDataHistoricalCurrency(sb);
     List<HistoricalCurrency> listhistCurr=new ArrayList();
        for (String line : historicalValue) {
         String dayHis=line.subSequence(1, 12).toString().replace("\"", "");
        Double value= Double.parseDouble(line.subSequence(20, 27).toString().replace(":", ""));
        HistoricalCurrency historicalCurrency=new 
        HistoricalCurrency(base, symbol, dayHis, value);
        listhistCurr.add(historicalCurrency);
        }
        Collections.sort(listhistCurr,(o1,o2)->{
        return o1.getDay().compareTo(o2.getDay());
        });
    return listhistCurr;}
    
}
