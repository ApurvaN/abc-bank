package com.abc;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.abc.Transaction.TransactionType;
import com.abc.Utils.DateProvider;
import com.abc.exception.AccountOperationException;

public class Account {

	/*enum added to remove hard coding of the account types with numbers 0,1,2
	 */
	enum AccountType{
		CHECKING,SAVINGS,MAXI_SAVINGS;
	}
	
   
	private final AccountType accountType;
    private List<Transaction> transactions;
    private Date lastPaymentDate;

    public void setLastPaymentDate(Date lastPaymentDate) {
		this.lastPaymentDate = lastPaymentDate;
	}

	public Date getLastPaymentDate() {
		return lastPaymentDate;
	}

	public List<Transaction> getTransactions() {
		return transactions;
	}

	public Account(AccountType accountType) {
        this.accountType = accountType;
        this.transactions = new ArrayList<Transaction>();
        //Setting up the last payment date to account opening day.
        this.lastPaymentDate=DateProvider.getInstance().now();
    }

    public void deposit(double amount) throws AccountOperationException {
        if (amount <= 0) {
            throw new AccountOperationException("amount must be greater than zero");
        } else {
            transactions.add(new Transaction(amount,TransactionType.DEPOSIT));
        }
    }

    public void withdraw(double amount) throws AccountOperationException {
	
    if (amount <= 0) {
        throw new AccountOperationException("amount must be greater than zero");
    }
    else if(amount>sumTransactions()){
    	  throw new AccountOperationException("amount exceeds available balance");
    }
    else {
        transactions.add(new Transaction(-amount,TransactionType.WITHDRAW));
    }
}

    /*This method calculates the daily interest including weekends
    * Formula - Principal Balance X (Annual Interest Rate* / Year Count**) X Number of Days Since Last Payment
    */
    public double dailyInterestEarned() {
        double amount = sumTransactions();
        int noOfDaysSinceLastPayement=(int)((DateProvider.getInstance().now().getTime() - getLastPaymentDate().getTime()) 
                / (1000 * 60 * 60 * 24) );
        
        switch(accountType){
            case SAVINGS:
                if (amount <= 1000)
                    return amount * (0.001/365) * noOfDaysSinceLastPayement;
                else
                    return amount * (0.002/365) * noOfDaysSinceLastPayement;
            case MAXI_SAVINGS:
            	Transaction lastWithdrawTransaction=getLastWithdrawTransaction();
            	if(lastWithdrawTransaction==null)
            		return  amount * (0.05/365) * noOfDaysSinceLastPayement;
            	
               	int lastTransactionDay=(int)((DateProvider.getInstance().now().getTime() - lastWithdrawTransaction.getTransactionDate().getTime()) 
                        / (1000 * 60 * 60 * 24) );
               	
               	if(lastTransactionDay>10)
               		return  amount * (0.05/365) * noOfDaysSinceLastPayement;
               	else
               		return  amount * (0.001/365) * noOfDaysSinceLastPayement;
            	
            default:
                return amount * (0.001/365) * noOfDaysSinceLastPayement;
        }
    }

    private Transaction getLastWithdrawTransaction() {
    	Transaction lastTransaction= null;
    	
    	int lastIndex=transactions.size()-1;
    	lastTransaction = transactions.get(lastIndex);
    	while(lastIndex>=0){
	    	if(lastTransaction.getTransactionType()==TransactionType.WITHDRAW)
	    	{
	    		return lastTransaction;
	    	}
	    	else
	    	{
	    		lastIndex--;
	    	}	
    	}
    	return null;
    	
	}

	/*Removing checkIfTransactionsExist separate method call , the call not doing anything additional
     *  other than just forwarding the call.
     *  Removing parameter checkAll , its not used*/
    public double sumTransactions() {
    	double amount = 0.0;
        for (Transaction t: transactions)
            amount += t.getAmount();
        return amount;
    }

   
    public AccountType getAccountType() {
        return accountType;
    }
    /*
     * This method is added , which will be called during the interest payments for the accounts.
     * This will set the lastPaymentDate.
     * lastPayment date will be used to calculate the daily interest rates 
    */
    public boolean payIntrest(double amount,Date date){
    	transactions.add(new Transaction(amount,TransactionType.DEPOSIT));
    	if(date==null)
    		setLastPaymentDate(DateProvider.getInstance().now());
    	else
    		setLastPaymentDate(date);
    	return true;
    }

    
}
