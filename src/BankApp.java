import java.io.Console;
import java.io.FileWriter;
import java.util.*;
import java.io.File;

class BankApp{
    public static void main(String[] args)
    {
        int customerCount=0;
        try {
            File bankCustomers = new File("bankData.txt");
            bankCustomers.createNewFile();
            Scanner bankReader=new Scanner(bankCustomers);
            while(bankReader.hasNextLine())
            {
                String[] temp=bankReader.nextLine().split(" ");
                customerCount++;
                String name=temp[0],accountNumber=temp[1];
                Double amount=Double.parseDouble(temp[2]);
                Account tempAccount=new Account(amount);
                Customer tempCustomer=new Customer(name,tempAccount);
                Bank.customers.add(tempCustomer);
            }
            bankReader.close();
        }
        catch(Exception e)
        {
            System.err.println("Error!!");
            System.exit(0);
        }
        Scanner in=new Scanner(System.in);
        while (true)
        {
            System.err.println("***WELCOME TO MOBILE BANKING***");
            System.out.println(" ");
            System.out.println("1.Add Customer");
            System.out.println("2.Make a withdrawal");
            System.out.println("3.Deposit Money");
            System.out.println("4.Check Balance");
            System.out.println("5.View all Accounts");
            System.out.println("6.Exit");
            int choice;
            try {
                choice=Integer.parseInt(in.nextLine());
            }
            catch (Exception e)
            {
                System.err.println("Invalid Input!!!");
                System.out.println("");
                continue;
            }
            switch (choice)
            {
                case 1:
                    System.out.println("Enter deposit amount to begin:");
                    double amount=Double.parseDouble(in.nextLine());
                    if(amount<100)
                    {
                        System.err.println("Initial deposit should be more than 100");
                        break;
                    }
                    Account tempAccount=new Account(amount);
                    System.out.println("Enter your name:");
                    String name=in.nextLine();
                    Customer tempCustomer=new Customer(name,tempAccount);
                    Bank.customers.add(tempCustomer);
                    customerCount++;
                    System.err.println("Dear customer, account was created successfully with Account number "+tempAccount.getAccountNumber()+". Thank you for banking with us!");
                    System.err.println("Your account balance is "+amount);
                    try {
                        FileWriter bankWriter= new FileWriter("bankData.txt",true);
                        bankWriter.write(name+" "+tempAccount.getAccountNumber()+" "+amount+"\n");
                        bankWriter.close();
                    }
                    catch(Exception e)
                    {
                        System.err.println("Error updating info!");
                    }
                    break;
                case 2:
                    System.out.println("Enter account number to begin:");
                    String accNum=in.nextLine();
                    if(customerCount==0)
                        System.err.println("Account number "+accNum+" was not found! \nPlease try again! :)");
                    else
                    {
                        boolean isFound=false;
                        int customerPos=0;
                        for(int i=0;i<Bank.customers.size();i++)
                        {
                            if(Bank.customers.get(i).getAccount().getAccountNumber().equals(accNum)) {
                                isFound = true;
                                customerPos=i;
                                break;
                            }
                        }
                        if (isFound)
                        {
                            System.out.println("Enter amount you wish to withdraw:");
                            amount=Double.parseDouble(in.nextLine());
                            Bank.customers.get(customerPos).getAccount().withdrawMoney(amount);
                            updateRecord(accNum,Bank.customers.get(customerPos).getAccount().getBalance());
                        }
                        else
                            System.err.println("Account with account number "+accNum+" was not found. Withdrawal failed :(");

                    }
                    break;
                case 3:
                    System.out.println("Enter account number to begin:");
                    accNum=in.nextLine();
                    if(customerCount==0)
                        System.err.println("Account number "+accNum+" was not found! \nPlease try again! :)");
                    else
                    {
                        boolean isFound=false;
                        int customerPos=0;
                        for(int i=0;i<Bank.customers.size();i++)
                        {
                            if(Bank.customers.get(i).getAccount().getAccountNumber().equals(accNum)) {
                                isFound = true;
                                customerPos=i;
                                break;
                            }
                        }
                        if (isFound)
                        {
                            System.out.println("Enter amount you wish to deposit:");
                            amount=Double.parseDouble(in.nextLine());
                            Bank.customers.get(customerPos).getAccount().deposit(amount);
                            updateRecord(accNum,Bank.customers.get(customerPos).getAccount().getBalance());
                        }
                        else
                            System.err.println("Account with account number "+accNum+" was not found. Deposit failed :(");
                    }
                    break;
                case 4:
                    System.out.println("Enter account number to begin:");
                    accNum=in.nextLine();
                    if(customerCount==0)
                        System.err.println("Account number "+accNum+" was not found! \nPlease try again! :)");
                    else
                    {
                        boolean isFound=false;
                        int customerPos=0;
                        for(int i=0;i<Bank.customers.size();i++)
                        {
                            if(Bank.customers.get(i).getAccount().getAccountNumber().equals(accNum)) {
                                isFound = true;
                                customerPos=i;
                                break;
                            }
                        }
                        if (isFound)
                            Bank.customers.get(customerPos).getDetails();
                            //System.out.println("Dear customer, your current account balance is "+Bank.customers.get(customerPos).getAccount().getBalance());
                        else
                            System.err.println("Account with account number "+accNum+" was not found :(");
                    }
                    break;
                case 5:
                    boolean isAdmin=false;
                    Console console= System.console();
                    char[] temp;
                    if(console!=null)
                        temp = console.readPassword("Provide Administrator Password to Continue :");
                    else
                        break;
                    String password=new String(temp);    //Administrator password: ABCD@YTN
                    //System.out.println(password);
                    if(password.equals("ABCD@YTN"))
                        isAdmin=true;
                    if(isAdmin) {
                        for (int i = 0; i < Bank.customers.size(); i++)
                            Bank.customers.get(i).getDetails();
                    }
                    else {
                        System.err.println("Invalid Administrator Password!");
                    }
                    break;
                case 6:
                    System.exit(0);
                    break;

                default:
                    System.err.println("Please enter a valid choice!");
            }
        }
    }
    public static void updateRecord(String accNumber,double balance)
    {
        File tempFile=new File("temp.txt");
        File originalFile=new File("bankData.txt");
        try{
            FileWriter bankWriter=new FileWriter(tempFile,true);
            Scanner bankReader=new Scanner(originalFile);
            while(bankReader.hasNextLine())
            {
                String line=bankReader.nextLine();
                String[] pieces=line.split(" ");
                if(pieces[1].equals(accNumber))
                    bankWriter.write(pieces[0]+" "+pieces[1]+" "+balance+"\n");
                else
                    bankWriter.write(line+"\n");
            }
            bankReader.close();
            bankWriter.close();
            originalFile.delete();
            File dump=new File("bankData.txt");
            tempFile.renameTo(dump);
            //tempFile.delete();
        }
        catch (Exception e){
            System.err.println("Record updating failed!!");
        }
    }
}



