package tutocurrencyjavafx.connectionApi;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.LocalDate;

/**
 *
 * @author amine gasa
 */
public class ApiConnection {
    private  final String url;
    private final String apiKey;

    public ApiConnection(String url, String apiKey) {
        this.url = url;
        this.apiKey = apiKey;
    }
    public StringBuilder getRate(String from,String to,Double amount) 
            throws MalformedURLException, IOException {
        URL objUrl=new URL(url+"/convert?to="+to+"&from="+from+"&amount="+amount);
        HttpURLConnection http=(HttpURLConnection) objUrl.openConnection();
        http.setRequestProperty("apikey", apiKey);
        StringBuilder sb=getStringbuilder(http);
    return sb;}
    public StringBuilder getSymbolsWithSignification() throws MalformedURLException
            , IOException{
        URL objUrl=new URL(url+"/symbols");
        HttpURLConnection http=(HttpURLConnection) objUrl.openConnection();
        http.setRequestProperty("apikey", apiKey);
        StringBuilder sb=getStringbuilder(http);
    return sb;}
    public StringBuilder getHistoricalCurrency(String base,LocalDate stratDate
            ,LocalDate endDate,String symbol) throws MalformedURLException, IOException{
        URL objUrl=new URL(url+"/timeseries?start_date="+stratDate+"&end_date="
                +endDate+"&base="+base+"&symbols="+symbol);
        HttpURLConnection http=(HttpURLConnection) objUrl.openConnection();
        http.setRequestProperty("apikey", apiKey);
        StringBuilder sb=getStringbuilder(http);
    return sb;}

    private StringBuilder getStringbuilder(HttpURLConnection http) throws IOException {
       if(http.getResponseCode()!=200)
           throw new IllegalStateException(" connection does not success");
        BufferedReader br=new BufferedReader(new InputStreamReader(http.getInputStream()));
        String inputLine;
        StringBuilder sb=new StringBuilder();
        while((inputLine=br.readLine())!=null){
            sb.append(inputLine);
        }
       
    return sb;}
    
}
