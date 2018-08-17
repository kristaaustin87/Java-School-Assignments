/*Assignment 4
Krista Delap
February 25, 2018
Part 1 
Displays calendar month based on user input*/

import java.util.Scanner;

public class Assignment4_Part2_KDelap 
{

	public static boolean isLeapYear(int year)
	{
	
		if (year % 4 == 0)//leap year if only div by 4
		{
		
			if (year % 100 == 0)//but not if also div by 100
			{
			
				if (year % 400 == 0)//unless also div by 400
				{
				 	return true;	
				}
				
				else return false;
			}
			
			else return true;
		}
		
		else return false;
	}
	
	public static int getNumDaysInMonth(int month, int year)
	{
	
		if(month == 2)//Feb depends on leap year
		{
			boolean leap = isLeapYear(year);
			if(leap == true)
			{
				return 29;
			}
			else return 28;
		}
		
		//Sep, April, June, Nov
		else if (month == 9 || month == 4 || month == 6 || month == 11)
		{
			return 30;
		} 
		
		//otherwise is 31
		else return 31;
	
	}
	
	public static int getStartDay( int month,  int year )
    {
        final int day = 1; // Must be set to day 1 for this to work.  JRD.


        // Adjust month number & year to fit Zeller's numbering system
        if ( month < 3 ) 
        {
            month = month + 12;
            year = year - 1;
        }
        
        int yearInCent = year % 100;      // k Calculate year within century
        int century = year / 100;      // j Calculate century term
        int firstDay  = 0;            // h Day number of first day in month 'm'
        
        firstDay = (day + (13 * (month + 1) / 5) + yearInCent +
            (yearInCent / 4) + (century / 4) + (5 * century)) % 7;
        
        // Convert Zeller's value to ISO value (1 = Mon, ... , 7 = Sun )
        int dayNum = ((firstDay + 5) % 7) + 1;     
        
        return dayNum;
  }
  
  	public static String getMonthName(int month)
  	{
  		switch (month)//convert int to string for month27
  		{
  			case 1: return "       January";
  			case 2: return "       February";
  			case 3: return "        March";
  			case 4: return "        April";
  			case 5: return "         May";
  			case 6: return "        June";
  			case 7: return "        July";
  			case 8: return "       August";
  			case 9: return "      September";
  			case 10: return "      October";
  			case 11: return "      November";
  			case 12: return "      December";
  			default: return "Please enter valid month number.";
  		}
  	}
  
 	 public static void printMonthBody (int month, int year)
  	{
 	 	int numDays = getNumDaysInMonth(month, year);
 	 	int startDay = getStartDay(month, year);
 	 	int dayOfWeek = startDay;
  
 	 	for(int i = 1; i <= numDays; i++)//loop thru all days
 	 	{
 	 	
 	 		if(i == 1)//set blanks before 1
 	 		{
 	 			for(int j = 0; j < startDay; j++)
 	 			{
 	 				System.out.print("    ");
 	 			}
 	 		}
 	 		
 	 		if(dayOfWeek % 7 == 0)//break for week
 	 		{
 	 			System.out.println();
 	 		}
 	 		
			if(i < 10)//set extra spaces
 	 		{
 	 			System.out.print("   ");
 	 		}
 	 		else System.out.print("  ");
 	 		
  			System.out.print(i);//print day
  			dayOfWeek++;//increase week day counter
 	 	}
  
  			System.out.println("\n");
 	 }
 	 
 	 public static void printMonthHeader(int month, int year)
 	 {
 	 
 	 	String monthName = getMonthName(month);//get string for month
 	 	System.out.println();
 	 	System.out.println(monthName + " " + year);//print header
 	 	System.out.println("---------------------------");
 	 	System.out.println(" Sun Mon Tue Wed Thu Fri Sat");
 	 	//System.out.println();
 	 
 	 }
 	 
 	 public static void printMonthCalendar(int month, int year)
 	 {
 	 	printMonthHeader(month, year);
 	 	printMonthBody(month, year);
 	 }
 	 
 	 public static void printYearCalendar(int year)
 	 {
 	 	for(int i = 1; i <= 12; i++)
 	 	{
 	 		printMonthCalendar(i,year);
 	 	}
 	 }

	public static void main (String args[])
	{
	
		Scanner input = new Scanner(System.in);
	
		//get year from user
		System.out.println("\n");
		System.out.println("Please enter year: ");
		int year = input.nextInt();
	
		//Test
		printYearCalendar(year);
	
	}

}

