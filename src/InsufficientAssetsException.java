public class InsufficientAssetsException extends Exception {
    private final Transaction tx;
    private final Patron p;

    public InsufficientAssetsException(Transaction tx, Patron p) {
        super("Insufficient Assets");
        this.tx = tx;
        this.p = p;
    }

    public Transaction getTx() {
        return this.tx;
    }

    public Patron getPatron() {
        return this.p;
    }
}