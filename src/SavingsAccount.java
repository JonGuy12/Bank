public class SavingsAccount extends Account {
    private double balance;
   
    protected SavingsAccount(int accountNumber, Patron patron) {
        super(accountNumber, patron);
        this.balance = 0;
    }

    /**
     * for a DEPOSIT transaction: increase the balance by transaction amount
     * for a WITHDRAW transaction: decrease the balance by transaction amount
     * add the transaction to the transaction history of this account
     * @param tx
     * @return
     * @throws InvalidTransactionException thrown if tx is not a CashTransaction
     */
    @Override
    public void executeTransaction(Transaction tx) throws InsufficientAssetsException,InvalidTransactionException {
        if(tx instanceof CashTransaction t) {
            switch (tx.getType()) {
                case DEPOSIT -> this.balance += t.getAmount();
                case WITHDRAW -> this.balance -= t.getAmount();
                default -> {
                }
            }
            this.transactions.add(tx);
        } else {
            throw new InvalidTransactionException("Must be a Cash Transaction", tx.getType());
        }
    }

    /**
     * @return the account's balance
     */
    @Override
    public double getValue() {
        return this.balance;
    }
}