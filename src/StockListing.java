/**
 * represents the stock of a single company that is listed on the StockExchange
 */
public class StockListing {
    private int availableShares;
    private double price;
    private String tickerSymbol;
    

    /**
     *
     * @param tickerSymbol
     * @param initialPrice
     * @param availableShares
     * @throws IllegalArgumentException if the tickerSymbol is null or empty, if the initial price is <= 0, of if availableShares <= 0
     */
    protected StockListing(String tickerSymbol, double initialPrice, int availableShares) {
        if(tickerSymbol == null || tickerSymbol == "")
            throw new IllegalArgumentException("Ticker symbol cannot be null or empty");
        if(initialPrice <= 0)
            throw new IllegalArgumentException("Initial price must be > 0");
        if(availableShares <= 0)
            throw new IllegalArgumentException("Available shares must be > 0");

        this.tickerSymbol = tickerSymbol;
        this.price = initialPrice;  
        this.availableShares = availableShares;
    }

    public String getTickerSymbol() {
        return this.tickerSymbol;
    }
    public double getPrice() {
        return this.price;
    }
    public int getAvailableShares() {
        return this.availableShares;
    }

    /**
     * set the price for a single share of this stock
     * @param price
     */
    protected void setPrice(double price) {
        if(price <= 0)
            throw new IllegalArgumentException("Price must be > 0");
        this.price = price;
    }
    /**
     * increase the number of shares available
     * @param availableShares
     * @return the total number of shares after adding availableShares
     * @throws IllegalArgumentException if availableShares <= 0
     */
    protected int addAvailableShares(int availableShares) {
        if(availableShares <= 0)
            throw new IllegalArgumentException("Available shares must be > 0");
        this.availableShares += availableShares;
        return this.availableShares;
    }
    /**
     * reduce the number of shares available
     * @param quantityToSubtract
     * @return the total number of shares after reducing availableShares
     * @throws IllegalArgumentException if quantityToSubtract > the number of available shares
     */
    protected int reduceAvailableShares(int quantityToSubtract){
        if(quantityToSubtract > this.availableShares)
            throw new IllegalArgumentException("Quantity must be <= the available shares");
        this.availableShares -= quantityToSubtract;
        return this.availableShares;
    }

    @Override
    public boolean equals(Object other) {
        return this.tickerSymbol.equals(((StockListing) other).tickerSymbol);
    }

    @Override
    public int hashCode() {
        return this.availableShares + this.tickerSymbol.hashCode();
    }
}