/**Mini Project 2 Main File Loads inputs and displays menu
@author Krista Delap*/

//importing libraries needed to read input and output
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Scanner;

public class MiniProject2_KDelap
{

    /**@param itemArray array of items from main
    @param cashArray array of cash items from main*/
    public static void display(Stock[] itemArray, Cash[] cashArray)
    {
    //Main display
			//var to track program state
			int state = 0;
			
			//vars to hold transaction vars
			int item;
			int moneyItem=0;
			int amount;
			int cont = 1;
			float runningTotal = 0;
			float itemPrice = 0;
			
			//scanner to get input
			Scanner input = new Scanner(System.in);
			
			//displays main menu until exit
			while(state != 5)
			{
			    System.out.println("\n" + "Vending Machine Program Menu" + "\n");
			    System.out.println("1 Display Menu of Commands" + "\n");
			    System.out.println("2 Display Inventory" + "\n");
			    System.out.println("3 Display Cash Reserves" + "\n");
			    System.out.println("4 Purchase an Item" + "\n");
			    System.out.println("5 Exit" + "\n");
			    System.out.print("Please enter the number of your selection: ");
			    state = input.nextInt();
			    
			    //case to handle command input
			    switch(state)
		        {
		            //print detailed instructions
		            case 1:
		                System.out.println("\n" + "2 - Lists the current inventory" 
		                + " with price and amount" + "\n" + "3 - Lists the current"
		                + " cash reserves by denomination" + "\n" + "4 - Make a "
		                + "selection and input cash to purchase" + "\n" + "5 - Exit");
		                break;
		                
		            case 2://print stock item inforamtion
		                System.out.println("Name                         Type         "
		                + " Price   Amount    Code");
		                System.out.println("____                         ____         "
		                + " _____   ______    ____");
		                for(int i = 0; i < itemArray.length; i++)
		                {
		                    itemArray[i].displayStock();
		                    System.out.printf("%8d" + "\n", i);
		                }
		                break;
		                
		            case 3://print cash item information
		            	System.out.println("Name               Type         "
		                + " Value   Amount    Code");
		                System.out.println("____               ____         "
		                + " _____   ______    ____");
		                for(int i = 0; i < cashArray.length; i++)
		                {
		                    cashArray[i].displayCash();
		                    System.out.printf("%8d" + "\n", i);
		                }
		                break;
		                
		            case 4://vend
		            	System.out.print("\n");
		            	//get item number
		            	System.out.print("Enter the number of the desired item: ");
		            	item = input.nextInt();
		            	
		            	//print item info
		            	System.out.println("Name                         Type         "
		                + " Price   Amount");
		                System.out.println("____                         ____         "
		                + " _____   ______");
		            	itemArray[item].displayStock();
		            	//get price or return error if out
		            	itemPrice = itemArray[item].getPrice();
		            	System.out.print("\n");
		            	
		            	//only loop if item in stock
		            	if(itemPrice != 0)
		            	{
		            	//get money, loop until all input
		            	while(cont == 1)
		            	{
		            	    System.out.print("Enter the number of the monetary unit: ");
		            	    moneyItem = input.nextInt();
		            	    System.out.print("Enter the number of the amount: ");
		            	    amount = input.nextInt();
		            	    //keep running total & set temp holding for that cash unit
		            	    runningTotal += cashArray[moneyItem].getValue(amount);
		            	    System.out.printf("Entered total: %.2f%n", runningTotal);
		            	    System.out.print("Enter 1 to add more money or 0 to vend: ");
		            	    cont = input.nextInt();
		            	}
		            	
		            	//check to make sure enough money input
		            	if(runningTotal >= itemPrice)
		            	{
		            		//if so, vend item & clean up 
		            	    vend(item, itemArray, runningTotal, itemPrice, cashArray);
		            	    cleanup(cashArray, true);
		            	}
		            	else
		            	{
		            		//print error and clean up
		            	    System.out.println("Not enough money entered");
		            	    cleanup(cashArray, false);
		            	}
		            	}
		            	
		            	//reset money loop value & total
		            	cont = 1;
		            	runningTotal = 0;
		            	break;
		        }
		    }
    }
    
    /**@param item item number
    @param itemArray array of stock items from main
    @param runningTotal total input money
    @param itemPrice price of the item
    @param cashArray array of cash unit items*/
    public static void vend(int item, Stock[] itemArray, float runningTotal, 
    float itemPrice, Cash[] cashArray)
    {
    	//vend the item
    	itemArray[item].vend();
    	//calculate change
    	float changeNeeded = runningTotal - itemPrice;
    	System.out.printf("Change Due: %.2f", changeNeeded);
    	if(changeNeeded != 0)
    	{
    		//determine how to make change
        	makeChange(changeNeeded, cashArray);
        }
    }
    
    /**
    @param changeNeeded the change due
    @param cashArray cash unit array*/
    public static void makeChange(float changeNeeded, Cash[] cashArray)
    {
    	//loop through cash array
        for(int i = 0; i < cashArray.length; i++)
        {
        	int cashAmount = cashArray[i].getAmount();
        	float cashValue = cashArray[i].getIndValue();
        	//if unit in reserve and less than or equal change due
            if(cashAmount > 0 && cashValue <= changeNeeded)
            {
            	//subtract from changeneeded
                changeNeeded -= cashArray[i].getChange(changeNeeded);
                //repeat for same unit until it is larger than remaining change due
                i--;
            }
        }
        
        System.out.println();
        //print returned change
        System.out.print("Returning ");
        for(int j = 0; j < cashArray.length; j++)
        {
            int unitAmount = cashArray[j].getChangeAmount();
            //print if the holding output amount is greater than 0
            if(unitAmount > 0)
            {
                System.out.print(unitAmount + " ");
                System.out.print(cashArray[j].getName() + " ");
            }
        }
    }
    
    /**@param cashArray cash unit array
    @param isComplete to determine if transaction occurred*/
    public static void cleanup(Cash[] cashArray, boolean isComplete)
    {
    	//clean up holding values for cash units
    	for(int i = 0; i < cashArray.length; i++)
        {
           cashArray[i].cleanCash(isComplete);
        }
    }
    
    public static void main(String args[]) throws IOException
    {
        //set up I/O variables
		BufferedReader itemsInput = null;
		BufferedReader itemsCount = null;
		BufferedReader moneyInput = null;
		BufferedReader moneyCount = null;
		
		//try block
		try
		{
		    String line;
		    int count = 0;
		    //set up bufferedreader to count items
            itemsCount = new BufferedReader(new FileReader(args[0]));
            line = itemsCount.readLine();
            
            //while loop to get count for itemArray
            while(line != null)
            {
                count++;
                line = itemsCount.readLine();
            }
            
            //array to hold stock items
            Stock itemArray[] = new Stock[count];
            //reset count
            count = 0;
            //counter to set in array
            int stockArrayCount = 0;
		    
		    //set up bufferedreader to load items
			itemsInput = new BufferedReader(new FileReader(args[0]));
			
			line = itemsInput.readLine();
		    
		    //while loop to initialize stock
		    while(line != null)
		    {
		        //create new array to split input by space
			    String lineArray[] = line.split(",");
			    //set new Stock object
			    Stock stock = new Stock();
			    //initialize stock object
			    stock.setStock(lineArray[0], lineArray[1], Float.parseFloat(lineArray[2]), 
			    Integer.parseInt(lineArray[3]));
			    //set object into array and increase counter
			    itemArray[stockArrayCount] = stock;
			    stockArrayCount++;
			    line = itemsInput.readLine();
		    }
			
			//to count items
			moneyCount = new BufferedReader(new FileReader(args[1]));
			line = moneyCount.readLine();
			
			//while loop to get count for moneyArray
            while(line != null)
            {
                count++;
                line = moneyCount.readLine();
            }
            
            //array to hold money items
			Cash cashArray[] = new Cash[count];
			
			int cashArrayCount = 0;
			
			//set up bufferedreader to load cash
			moneyInput = new BufferedReader(new FileReader(args[1]));
			
			line = moneyInput.readLine();
			
			while(line != null)
			{
			    //create new array to split input by space
			    String lineArray[] = line.split(",");
			    //set new Stock object
			    Cash cash = new Cash();
			    //initialize stock object
			    cash.setCash(lineArray[0], lineArray[1], Float.parseFloat(lineArray[2]), 
			    Integer.parseInt(lineArray[3]));
			    //set object into array and increase counter
			    cashArray[cashArrayCount] = cash;
			    cashArrayCount++;
			    line = moneyInput.readLine();
			}
			
			display(itemArray, cashArray);
			
		}
			
				
		//catch input error
		catch(IOException e)
		{
		    System.out.println("Program requires input files" + "\n");   
		}
		
		//catch non integer input
		catch(Exception e)
		{
		    System.out.println("Requires integer input" + "\n");   
		}
		
		//finally block to close readers
		finally
		{
		    if(itemsInput != null)
		    {
		        itemsInput.close();
		    }
		    
		    if(itemsCount != null)
		    {
		        itemsCount.close();
		    }
		    
		    if(moneyCount != null)
		    {
		        moneyCount.close();
		    }
		    
		    if(moneyInput != null)
		    {
		        moneyInput.close();
		    }
		}
	}
}