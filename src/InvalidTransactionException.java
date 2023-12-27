public class InvalidTransactionException extends Exception {
    private final Transaction.TxType type;

    public InvalidTransactionException(String message, Transaction.TxType type) {
        super(message);
        this.type = type;
    }

    public Transaction.TxType getType() {
        return this.type;
    }
}
