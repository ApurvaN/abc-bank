package com.abc;

import org.junit.Ignore;
import org.junit.Test;

import com.abc.exception.AccountOperationException;

import static org.junit.Assert.assertEquals;

public class CustomerTest {

    @Test //Test customer statement generation
    public void testApp() throws AccountOperationException{

        Account checkingAccount = new Account(Account.AccountType.CHECKING);
        Account savingsAccount = new Account(Account.AccountType.SAVINGS);

        Customer henry = new Customer("Henry").openAccount(checkingAccount).openAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);

        assertEquals("Statement for Henry\n" +
                "\n" +
                "Checking Account\n" +
                "  deposit $100.00\n" +
                "Total $100.00\n" +
                "\n" +
                "Savings Account\n" +
                "  deposit $4,000.00\n" +
                "  withdrawal $200.00\n" +
                "Total $3,800.00\n" +
                "\n" +
                "Total In All Accounts $3,900.00", henry.getStatement());
    }

    @Test
    public void testOneAccount(){
        Customer oscar = new Customer("Oscar").openAccount(new Account(Account.AccountType.SAVINGS));
        assertEquals(1, oscar.getNumberOfAccounts());
    }

    @Test
    public void testTwoAccounts(){
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.AccountType.SAVINGS));
        oscar.openAccount(new Account(Account.AccountType.CHECKING));
        assertEquals(2, oscar.getNumberOfAccounts());
    }

    @Ignore
    public void testThreeAcounts() {
        Customer oscar = new Customer("Oscar")
                .openAccount(new Account(Account.AccountType.SAVINGS));
        oscar.openAccount(new Account(Account.AccountType.CHECKING));
        assertEquals(3, oscar.getNumberOfAccounts());
    }
    
    @Test
    public void testAccountTransferSuccess() throws AccountOperationException{
        Customer oscar = new Customer("Oscar");
        Account checkingAccount = new Account(Account.AccountType.CHECKING);
        Account savingsAccount = new Account(Account.AccountType.SAVINGS);

        oscar.openAccount(checkingAccount);
        oscar.openAccount(savingsAccount);

        checkingAccount.deposit(100.0);
        savingsAccount.deposit(4000.0);
        savingsAccount.withdraw(200.0);
        assertEquals(true, oscar.transfer(checkingAccount,savingsAccount,100.0));
    }
    
    
    @Test(expected=AccountOperationException.class)
    public void testAccountTransferFailure() throws AccountOperationException{
        Customer oscar = new Customer("Oscar");
        Customer henry = new Customer("Henry");
        
        Account checkingAccountForOscar = new Account(Account.AccountType.CHECKING);
        Account savingsAccountForHenry = new Account(Account.AccountType.SAVINGS);

        oscar.openAccount(checkingAccountForOscar);
        henry.openAccount(savingsAccountForHenry);

        oscar.transfer(checkingAccountForOscar,savingsAccountForHenry,100.0);
        
    }
}
