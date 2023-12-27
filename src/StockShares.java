/**
 * represents the quantity of shares a single Patron owns of single stock/listing
 */
public class StockShares {
    private StockListing stock;
    private int quantity;
    

    /**
     * @param listing the stock listing this instance is tracking the Patron's shares of
     * @throws IllegalArgumentException if listing is null
     */
    protected StockShares(StockListing listing, int quantity) {
        this.stock = listing;
        this.quantity = quantity;
    }

    public int getQuantity() {
        return this.quantity;
    }

    protected void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public StockListing getListing() {
        return this.stock;
    }

    @Override
    public boolean equals(Object other) {
        return this.stock.equals(((StockShares) other).stock);
    }

    @Override
    public int hashCode() {
        return stock.getTickerSymbol().hashCode() + stock.getAvailableShares() + this.quantity;
    }
}