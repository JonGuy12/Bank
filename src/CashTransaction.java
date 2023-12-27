/**
 * A CashTransaction is immutable. Value of nanoTimeStamp must be set at time of construction to the return value of System.nanoTime().
 */
public class CashTransaction implements Transaction {
    private final TxType type;
    private final double amount;
    private final long nanoTimeStamp;

    /**
     *
     * @param type
     * @param amount
     * @throws InvalidTransactionException thrown if type is neither DEPOSIT nor WITHDRAW, or if amount <= 0
     */
    public CashTransaction(TxType type, double amount) throws InvalidTransactionException {
        if(!(type == TxType.DEPOSIT || type == TxType.WITHDRAW))
            throw new InvalidTransactionException("Invalid Transaction Type", type);
        if(amount <= 0)
            throw new InvalidTransactionException("Invalid Amount", type);
        
        this.type = type;
        this.amount = amount;
        this.nanoTimeStamp = System.nanoTime();
    }

    public double getAmount(){
        return this.amount;
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