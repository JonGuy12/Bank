import java.util.*;

public class StockExchange {   
    private Map<String, StockListing> stocks;

    protected StockExchange() {
        this.stocks = new HashMap<String, StockListing>();
    }

    /**
     *
     * @param tickerSymbol symbol of the new stock to be created, e.g. "IBM", "GOOG", etc.
     * @param initialPrice price of a single share of the stock
     * @param availableShares how many shares of the stock are available initially
     * @throws IllegalArgumentException if there's already a listing with that tickerSymbol
     */
    public void createNewListing(String tickerSymbol, double initialPrice, int availableShares) {
        if(this.stocks.containsKey(tickerSymbol))
            throw new IllegalArgumentException("There already exists a listing with that ticker symbol");

        this.stocks.put(tickerSymbol, new StockListing(tickerSymbol, initialPrice, availableShares));
    }

    /**
     * @param tickerSymbol
     * @return the StockListing object for the given tickerSymbol, or null if there is none
     */
    public StockListing getStockListing(String tickerSymbol) {
        return this.stocks.get(tickerSymbol);
    }

    /**
     * @return an umodifiable list of all the StockListings currently found on this exchange
     * @see Collections#unmodifiableList(List)
     */
    public List<StockListing> getAllCurrentListings() {
        return Collections.unmodifiableList(new ArrayList<StockListing>(this.stocks.values()));
    }
}