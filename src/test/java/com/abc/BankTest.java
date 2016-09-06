package com.abc;

import org.junit.Test;

import com.abc.Utils.Utils;
import com.abc.exception.AccountOperationException;

import static org.junit.Assert.assertEquals;

import java.util.Calendar;

public class BankTest {
   
    @Test
    public void customerSummary() {
        Bank bank = new Bank();
        Customer john = new Customer("John");
        john.openAccount(new Account(Account.AccountType.CHECKING));
        bank.addCustomer(john);

        assertEquals("Customer Summary\n - John (1 account)", bank.customerSummary());
    }

    @Test
    public void checkingAccount() throws AccountOperationException {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.AccountType.CHECKING);
        Customer bill = new Customer("Bill").openAccount(checkingAccount);
        bank.addCustomer(bill);

        checkingAccount.deposit(100.0);

        assertEquals(0.0, bank.totalInterestPaid(), Utils.DOUBLE_DELTA);
    }

    @Test
    public void savingsAccount() throws AccountOperationException {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.AccountType.SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        //Setting up the payment date as 04/2016, so that 4 months interest will be calculated
        checkingAccount.deposit(1500.0);
        Calendar c=Calendar.getInstance();
        c.set(2016, 4, 06);
        checkingAccount.payIntrest(200,c.getTime());
        
        assertEquals(1.14, bank.totalInterestPaid(), Utils.DOUBLE_DELTA);
    }

    @Test
    public void maxiSavingsAccount() throws AccountOperationException {
        Bank bank = new Bank();
        Account checkingAccount = new Account(Account.AccountType.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        //Setting up the payment date as 04/2016, so that 4 months interest will be calculated
        checkingAccount.deposit(3000.0);
        Calendar c=Calendar.getInstance();
        c.set(2016, 4, 06);
        checkingAccount.payIntrest(200,c.getTime());

        assertEquals(53.91, bank.totalInterestPaid(), Utils.DOUBLE_DELTA);
    }
    
    @Test
    public void testFirstCustomer() throws AccountOperationException {
        Bank bank = new Bank();
        
        Account checkingAccount = new Account(Account.AccountType.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Bill").openAccount(checkingAccount));

        Account savingAccount = new Account(Account.AccountType.MAXI_SAVINGS);
        bank.addCustomer(new Customer("Oscar").openAccount(savingAccount));
        
        assertEquals( bank.getFirstCustomer(), "Bill");
    }
    

}
