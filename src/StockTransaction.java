/**
 * A StockTransaction is immutable. Value of nanoTimeStamp must be set at time of construction to the return value of System.nanoTime().
 */
public class StockTransaction implements Transaction {
    private final StockListing listing;
    private final TxType type;
    private final int quantity;
    private final long nanoTimeStamp;

    /**
     *
     * @param listing
     * @param type
     * @param quantity
     * @throws InvalidTransactionException thrown if TxType is neither BUY nor SELL, or if quantity <= 0, or if listing == null
     */
    public StockTransaction(StockListing listing, TxType type, int quantity) throws InvalidTransactionException {
        if(!(type == TxType.BUY || type == TxType.SELL)) 
            throw new InvalidTransactionException("Transaction type must be BUY or SELL", type);
        if(quantity <= 0)
            throw new InvalidTransactionException("Quantity must be > 0", type);
        if(listing == null) 
            throw new InvalidTransactionException("Listing cannot be null", type);
        
        this.listing = listing;
        this.type = type;
        this.quantity = quantity;
        this.nanoTimeStamp = System.nanoTime();
    }

    public StockListing getStock(){
        return this.listing;
    }

    public int getQuantity(){
        return this.quantity;
    }

    @Override
    public TxType getType() {
        return this.type;
    }

    @Override
    public long getNanoTimestamp() {
       return this.nanoTimeStamp;
    }
}
