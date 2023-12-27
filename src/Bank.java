import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
public class Bank {
    private Set<Account> accounts;
    private Set<Patron> patrons;
    private StockExchange exchange;

    /**
     * @param exchange the stock exchange on which all stock are listed
     * @throws IllegalArgumentException if exchange is null
     */
    protected Bank(StockExchange exchange) {
        if(exchange == null)
            throw new IllegalArgumentException("Exchange cannot be null");
        this.exchange = exchange;
        this.accounts = new HashSet<Account>();
        this.patrons = new HashSet<Patron>();
    }
    /**
     * Create a new Patron whose ID is the next unique available Patron ID and whose Bank is set to this bank.
     * Add the new Patron to the Bank's Set of Patrons.
     * No two Patrons can have the same ID. Each ID which is assigned should be greater than the previous ID.
     * @return a new Patron with a unique ID, but no accounts
     */
    public Patron createNewPatron() throws ApplicationDeniedException {
        int prevId = 0;
        Patron newPatron = new Patron(prevId++, this);
        this.patrons.add(newPatron);
        return newPatron;
    }

    /**
     * Create a new SavingsAccount for the Patron.
     * The SavingsAccount's id must be the next unique account ID available.
     * No two accounts of ANY KIND can have the same ID. Each ID which is assigned should be greater than the previous ID.
     * Add the new SavingsAccount to the Bank's Set of Accounts.
     * @param p the Patron for whom the account is being created
     * @return the SavingsAccount's id
     * @throws ApplicationDeniedException thrown if Patron already has a SavingsAccount
     * @throws IllegalArgumentException if p is null
     */
    public int openNewSavingsAccount(Patron p) throws ApplicationDeniedException, IllegalArgumentException {
        if(p == null)
            throw new IllegalArgumentException("Patron cannot be null");
        if(p.getSavingsAccount() != null)
            throw new ApplicationDeniedException("Patron already has a savings account");
        
        int savingsId = 0;
        SavingsAccount savingsAcc = new SavingsAccount(savingsId, p);
        p.setSavingsAccount(savingsAcc);
        accounts.add(savingsAcc);
        savingsId += 2;
        return savingsId - 2;
    }

    /**
     * Create a new BrokerageAccount for the Patron.
     * The BrokerageAccount's id must be the next unique account ID available.
     * No two accounts of ANY KIND can have the same ID. Each ID which is assigned should be greater than the previous ID.
     * Add the new BrokerageAccount to the Bank's Set of Accounts.
     * @param p the Patron for whom the account is being created
     * @return the BrokerageAccount's id
     * @throws ApplicationDeniedException thrown if the Patron doesn't have a SavingsAccount or DOES already have a BorkerageAccount
     * @throws IllegalArgumentException if p is null
     */
    public int openNewBrokerageAccount(Patron p) throws ApplicationDeniedException, IllegalArgumentException {
        if(p == null)
            throw new IllegalArgumentException("Patron cannot be null");
        if(p.getBrokerageAccount() != null)
            throw new ApplicationDeniedException("Patron already has a brokerage account");
        
        int brokerageId = 1;
        BrokerageAccount brokerageAcc = new BrokerageAccount(brokerageId, p);
        p.setBrokerageAccount(brokerageAcc);
        accounts.add(brokerageAcc);
        brokerageId += 2;
        return brokerageId - 2;
    }

    /**
     *
     * @return an unmodifiable set of all the accounts (both Savings and Brokerage)
     * @see Collections#unmodifiableSet(Set)
     */
    protected Set<Account> getAllAccounts() {
        return Collections.unmodifiableSet(accounts);
    }

    /**
     *
     * @return an unmodifiable set of all the Patrons
     * @see Collections#unmodifiableSet(Set)
     */
    protected Set<Patron> getAllPatrons() {
        return Collections.unmodifiableSet(patrons);
    }

    /**
     * @return the exchange used by this Bank
     */
    protected StockExchange getExchange() {
        return this.exchange;
    }
}