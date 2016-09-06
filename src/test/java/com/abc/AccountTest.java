package com.abc;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import com.abc.Utils.Utils;
import com.abc.exception.AccountOperationException;

public class AccountTest {

	@Test(expected=AccountOperationException.class)
	public void testDeposit() throws AccountOperationException{
		 Account checkingAccount = new Account(Account.AccountType.CHECKING);
		 checkingAccount.deposit(-100.0);
	}

	@Test(expected=AccountOperationException.class)
	public void testWithdraw() throws AccountOperationException{
		 Account checkingAccount = new Account(Account.AccountType.CHECKING);
		 checkingAccount.withdraw(-100.0);
	}
	
	
	@Test(expected=AccountOperationException.class)
	public void testWithdrawWithLowBalance() throws AccountOperationException{
		 Account checkingAccount = new Account(Account.AccountType.CHECKING);
		 checkingAccount.deposit(100.0);
		 checkingAccount.withdraw(-200.0);
	}
	
	@Test
	public void testInterestEarned() throws AccountOperationException{
		 Account savingsAccount = new Account(Account.AccountType.SAVINGS);
		 savingsAccount.deposit(100.0);
		 assertEquals(0.00000,savingsAccount.dailyInterestEarned(),Utils.DOUBLE_DELTA);
	}
	
		
	
     
}
