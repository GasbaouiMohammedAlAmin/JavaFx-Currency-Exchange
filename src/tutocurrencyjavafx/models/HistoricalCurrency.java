package tutocurrencyjavafx.models;

/**
 *
 * @author amine gasa
 */
public class HistoricalCurrency {
    private String base;
    private String symbol;
    private String day;
    private Double value;

    public HistoricalCurrency(String base, String symbol, String day, Double value) {
        this.base = base;
        this.symbol = symbol;
        this.day = day;
        this.value = value;
    }

    public String getDay() {
        return day;
    }

    public Double getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "HistoricalCurrency{" + "base=" + base + ", symbol=" + symbol + ", day=" + day + ", value=" + value + '}';
    }
    
}
