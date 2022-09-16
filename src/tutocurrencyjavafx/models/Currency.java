package tutocurrencyjavafx.models;

/**
 *
 * @author amine gasa
 */
public class Currency {
    private String symbol;
    private String signification;

    public Currency(String symbol, String siginification) {
        this.symbol = symbol;
        this.signification = siginification;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getSignification() {
        return signification;
    }

    @Override
    public String toString() {
        return "Currency{" + "symbol=" + symbol + ", signification=" + signification + '}';
    }
    
}
