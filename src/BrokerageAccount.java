import java.util.*;

/**
 * Models a brokerage account, i.e. an account used to buy, sell, and own stocks
 */
public class BrokerageAccount extends Account {
    private Map<String, StockShares> sharesMap;

    /**
     * This will be called by the Bank class.
     * @param accountNumber the account number assigned by the bank to this new account
     * @param patron the Patron who owns this account
     * @see Bank#openNewBrokerageAccount(Patron)
     */
    protected BrokerageAccount(int accountNumber, Patron patron) {
        super(accountNumber, patron);
        this.sharesMap = new HashMap<>();
    }

    /**
     * @return an unmodifiable list of all the shares of stock currently owned by this account
     * @see Collections#unmodifiableList(List)
     */
    public List<StockShares> getListOfShares() {
        return Collections.unmodifiableList(new ArrayList<>(this.sharesMap.values()));
    }

    /**
     * If the transaction is not an instanceof StockTransaction, throw an IllegalArgumentException.
     *
     * If tx.getType() is BUY, do the following:
     *         If there aren't enough shares of the stock available for purchase, throw an InvalidTransactionException.
     *         The total amount of cash needed for the tx  = tx.getQuantity() * tx.getStock().getPrice(). If the patron doesn't have enough cash in his SavingsAccount for this transaction, throw InsufficientAssetsException.
     *         If he does have enough cash, do the following:
     *         1) reduce available share of StockListing by tx.getQuantity()
     *         2) reduce cash in patron's savings account by tx.getQuantity() * StockListing.getPrice()
     *         3) create a new StockShare for this stock with the quantity set to tx.getQuantity() and listing set to tx.getStock() (or increase StockShare quantity, if there already is a StockShare instance in this account, by tx.getQuantity())
     *         4) add this to the set of transactions recorded in this account
     *
     * If tx.getType() is SELL, do the following:
     *          //If this account doesn't have the specified number of shares in the given stock, throw an InsufficientAssetsException.
     *          //Reduce the patron's shares in the stock by the tx.getQuantity()
     *          //The revenue from the sale = the current price per share of the stock * number of shares to be sold. Use a DEPOSIT transaction to add the revenue to the Patron's savings account.
     *
     * @param tx the transaction to execute on this account
     * @see StockTransaction
     */
    @Override
    public void executeTransaction(Transaction tx) throws InsufficientAssetsException,InvalidTransactionException {
        if(tx instanceof StockTransaction t) {
            switch(tx.getType()) {
                case BUY:
                    if(t.getQuantity() > t.getStock().getAvailableShares())
                        throw new InvalidTransactionException("Not enough available stocks", t.getType());
                    if(this.getPatron().getSavingsAccount().getValue() < t.getQuantity() * t.getStock().getPrice())
                        throw new InsufficientAssetsException(t, this.getPatron());
                    
                    t.getStock().reduceAvailableShares(t.getQuantity());
                    this.getPatron().getSavingsAccount().executeTransaction(new CashTransaction(Transaction.TxType.WITHDRAW, t.getQuantity() * t.getStock().getPrice()));
                    
                    if(this.sharesMap.containsKey(t.getStock().getTickerSymbol()))
                        this.sharesMap.get(t.getStock().getTickerSymbol()).setQuantity(this.sharesMap.get(t.getStock().getTickerSymbol()).getQuantity() + t.getQuantity());
                    else
                        this.sharesMap.put(t.getStock().getTickerSymbol(), new StockShares(t.getStock(), t.getQuantity()));
                    break;
                case SELL:
                    if(this.sharesMap.get(t.getStock().getTickerSymbol()).getQuantity() < t.getQuantity())
                        throw new InsufficientAssetsException(tx, this.getPatron());

                    this.sharesMap.get(t.getStock().getTickerSymbol()).setQuantity(this.sharesMap.get(t.getStock().getTickerSymbol()).getQuantity() - t.getQuantity());
                    this.getPatron().getSavingsAccount().executeTransaction(new CashTransaction(Transaction.TxType.DEPOSIT, t.getQuantity() * t.getStock().getPrice()));
                    break;
                default:
                    break;
            }
            this.transactions.add(tx);
        } else
            throw new IllegalArgumentException("Must be a Stock Transaction");
    }

    /**
     * the value of a BrokerageAccount is calculated by adding up the values of each StockShare.
     * The value of a StockShare is calculated by multiplying the StockShare quantity by its listing's price.
     * @return
     */
    @Override
    public double getValue() {
        double value = 0;
        for(StockShares share : getListOfShares()) {
            value += share.getQuantity() * share.getListing().getPrice();
        }
        return value;
    }
}
