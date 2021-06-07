public class Account{
    double balance;
    static int id=100;
    String accNumber="A";
    boolean firstTime=true;
    Account(double balance)
    {
        this.balance=balance;
        accNumber+=Integer.toString(id++);
    }

    public void withdrawMoney(double amount)
    {
        boolean success=false;
        double fee=Bank.withdrawalFee;
        if(firstTime) {
            fee = 0;
            firstTime=false;
        }
        if(balance-amount-fee>=100)
        {
            success=true;
            balance-=(amount+fee);
            System.err.println(amount+" was successfully withdrawn from your account.Your new account balance is "+balance);
            System.err.println("You were charged "+fee+" for this service.");
        }
        if(!success)
            System.err.println(amount+" cannot be withdrawn from your account.Your account balance is "+balance);
    }

    public void deposit(double amount)
    {
        if(amount>0)
        {
            balance+=amount;
            System.err.println(amount+" was successfully deposited to your account.Your new account balance is "+balance);
        }
        else
            System.err.println("Please enter a valid amount to be deposited");
    }

    public double getBalance()
    {
        return balance;
    }

    public String getAccountNumber()
    {
        return accNumber;
    }

}

