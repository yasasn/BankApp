public class Customer{
    String name;
    Account account;

    Customer(String name,Account account){
        this.name=name;
        this.account=account;
    }

    public void getDetails()
    {
        System.err.println("Name: "+name+" | Account Number: "+account.getAccountNumber()+" | Balance: "+account.getBalance());
    }

    public Account getAccount()
    {
        return account;
    }

}