/**Mini Project 2 Cash Class contains methods for stocking cash and making change
@author Krista Delap*/

public class Cash
{

    //Stock variables
    public String name;
    public String type;
    public float value;
    public int amount;
    public int inputAmount = 0;
    public int changeAmount = 0;

    /**@param name The name of the item
    @param type The type of the item
    @param value The value of the item
    @param amount The amount of the item*/
    public void setCash(String name, String type, float value, int amount)
    {
       this.name = name;
       this.type = type;
       this.value = value;
       this.amount = amount;
    }
    
    public void displayCash()
    {
    	//display unit information
        System.out.printf("%-15s    %-10s    %5.2f    %5d", this.name, 
        this.type, this.value, this.amount);
    }
    
    /**@param amount input amount for the unit
    @return total value of that input amount*/
    public float getValue(int amount)
    {
        this.inputAmount = amount;
        float totalVal = this.value * amount;
        return totalVal;
    }
    
    /**@return returns reserve of that unit*/
    public int getAmount()
    {
        return this.amount;
    }
    
    /**@return returns value of the unit per piece*/
    public float getIndValue()
    {
        return this.value;
    }
    
    /**@param changeNeeded the total amount needed
    @return returns value of unit per piece*/
    public float getChange(float changeNeeded)
    {
        //increase temp holding for change
    	this.changeAmount++;
    	return this.value;
    }
    
    /**@return name of unit*/
    public String getName()
    {
    	return this.name;
    }
    
    /**@return the number of units returned*/
    public int getChangeAmount()
    {
    	return this.changeAmount;
    }
    
    //clean up temp holding values
    public void cleanCash(boolean isComplete)
    {
        //if vend complete, update amount values
        if(isComplete)
        {
            this.amount += this.inputAmount;
            this.amount -= this.changeAmount;
        }
        
        //reset temp values
        this.inputAmount = 0;
        this.changeAmount = 0;
    }
}