package com.abc;

import java.util.ArrayList;
import java.util.List;

import com.abc.Utils.Utils;
import com.abc.exception.AccountOperationException;

import static java.lang.Math.abs;

public class Customer {
    private String name;
    private List<Account> accounts;

    public Customer(String name) {
        this.name = name;
        this.accounts = new ArrayList<Account>();
    }

    public String getName() {
        return name;
    }

    public Customer openAccount(Account account) {
        accounts.add(account);
        return this;
    }

    public int getNumberOfAccounts() {
        return accounts.size();
    }

    public double totalInterestEarned() {
        double total = 0;
        for (Account a : accounts)
            total += a.dailyInterestEarned();
        return total;
    }

    public String getStatement() {
        String statement = null;
        statement = "Statement for " + name + "\n";
        double total = 0.0;
        for (Account a : accounts) {
            statement += "\n" + statementForAccount(a) + "\n";
            total += a.sumTransactions();
        }
        statement += "\nTotal In All Accounts " + toDollars(total);
        return statement;
    }

    /*This logic has been modified to make use of enum constants*/
    private String statementForAccount(Account a) {
        String s = "";

       //Translate to pretty account type
        switch(a.getAccountType()){
            case CHECKING:
                s += "Checking Account\n";
                break;
            case SAVINGS:
                s += "Savings Account\n";
                break;
            case MAXI_SAVINGS:
                s += "Maxi Savings Account\n";
                break;
        }

        //Now total up all the transactions
        double total = 0.0;
        for (Transaction t : a.getTransactions()) {
            s += "  " + (t.getAmount() < 0 ? "withdrawal" : "deposit") + " " + toDollars(t.getAmount()) + "\n";
            total += t.getAmount();
        }
        s += "Total " + toDollars(total);
        return s;
    }

    private String toDollars(double d){
        return String.format("$%,.2f", abs(d));
    }
    
    public  boolean transfer(Account source,Account destination,double amount) throws AccountOperationException{
    
    	if(accounts.contains(source) && accounts.contains(destination)){
    		synchronized(source){
    			synchronized (destination) {
    				source.withdraw(amount);
    	    		destination.deposit(amount);
					}
    			}
    		return true;
    		}
    	else{
    		 throw new AccountOperationException("Sorry, money transfer is not allowed between these accounts.");
       		}
    }
    
    
    public String toString(){
    	return  getName() + " (" + Utils.format(getNumberOfAccounts(), "account") + ")";
    	
    }
}
