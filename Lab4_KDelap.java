/*Lab 4
Krista Delap
May 1, 2018
Uses quicksort and heapsort to compare sort times for files of various sizes*/

//importing allowed libraries for input and output
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.DataInputStream;
import java.io.FileInputStream;

public class Lab4_KDelap
{

	public static int seperate(int array[], int i, int j)
	{
		//set pivot
		int pivot = array[i];
	
		//set low and high
		int l = i;
		int h = j;
	
		//set var to hold swap var
		int swap = 0;
	
		//set while loop tracking variable
		boolean sorted = false;
	
		//loop until sorted into the appropriate partitions
		while(sorted != true)
		{
			//check that low side numbers are less than pivot
			while(array[l] < pivot)
			{
				l++;
			}
		
			//check that numbers on the high side are bigger than pivot
			while(array[h] > pivot)
			{
				h--;
			}
		
			//determine size of partition
			if(l >= h)
			{
				//everything is partitioned
				sorted = true;
			}
		
			else
			{
			  //need to reverse low and high   
			  swap = array[l];
			  array[l] = array[h];
			  array[h] = swap;
		  
			  //update low and high vars
			  l++;
			  h--;
			}
		}
		
		//return the h value
		return h;
	}
	
	public static void sort1(int array[], int i, int j)
	{
	    //variable to hold returned partition num from separate method
	    int k = 0;
	    
	    if(i >= j)
	    {
	        //already sorted
	        return;
	    }
	        //gets the low partition
	        k = seperate(array, i, j);
	        
	        //recursive on the high and low partitions
	        sort1(array, i, k);
	        sort1(array, k+1, j);
	}
	
    public static void sort2(int array[], int i, int j)
	{
	    //stop at partition of 100 or less
	    if(j - i <= 100)
	    {
	        int length = j - i + 1;
	        insertSort(array, i, length);
	    }
	    else
	    {
			//variable to hold returned partition num from separate method
			int k = 0;
		
			if(i >= j)
			{
				//already sorted
				return;
			}
				//gets the low partition
				k = seperate(array, i, j);
			
				//recursive on the high and low partitions
				sort1(array, i, k);
				sort1(array, k+1, j);
	    }
	}
	
	public static void sort3(int array[], int i, int j)
	{
	    //stop as less than 50 partition
	    if(j - i <= 50)
	    {
	        int length = j - i + 1;
	        insertSort(array, i, length);
	    }
	    else
	    {
			//variable to hold returned partition num from separate method
			int k = 0;
		
			if(i >= j)
			{
				//already sorted
				return;
			}
				//gets the low partition
				k = seperate(array, i, j);
			
				//recursive on the high and low partitions
				sort1(array, i, k);
				sort1(array, k+1, j);
	    }
	}
	
	public static void sort4(int array[], int i, int j)
	{
			//variable to hold returned partition num from separate method
			int k = 0;
		
			if(i >= j)
			{
				//already sorted
				return;
			}
				//gets the low partition
				k = seperate2(array, i, j);
			
				//recursive on the high and low partitions
				sort1(array, i, k);
				sort1(array, k+1, j);
	    
	}
	
	public static int seperate2(int array[], int i, int j)
	{
		//set pivot
		int pivot = getPivot(array[i], array[j], array[((j-i)/2)+1]);
	
		//set low and high
		int l = i;
		int h = j;
	
		//set var to hold swap var
		int swap = 0;
	
		//set while loop tracking variable
		boolean sorted = false;
	
		//loop until sorted into the appropriate partitions
		while(sorted != true)
		{
			//check that low side numbers are less than pivot
			while(array[l] < pivot)
			{
				l++;
			}
		
			//check that numbers on the high side are bigger than pivot
			while(array[h] > pivot)
			{
				h--;
			}
		
			//determine size of partition
			if(l >= h)
			{
				//everything is partitioned
				sorted = true;
			}
		
			else
			{
			  //need to reverse low and high   
			  swap = array[l];
			  array[l] = array[h];
			  array[h] = swap;
		  
			  //update low and high vars
			  l++;
			  h--;
			}
		}
		
		//return the h value
		return h;
	}
	
	public static int getPivot(int a, int b, int c)
	{
	    //set up an array and set the three values into it
	    int medianArray[] = new int[3];
	    medianArray[0] = a;
	    medianArray[1] = b;
	    medianArray[2] = c;
	    
	    //to hold swapping var
	    int swap = 0;
     
        //use insert sort to find median value
    	for(int i = 1; i < 3; i++)
    	{
    	    while(i > 0 && medianArray[i] < medianArray[i-1])
    	    {
    	    
    	        swap = medianArray[i];
    	        medianArray[i] = medianArray[i-1];
    	        medianArray[i-1] = swap;
    	        i--;
    	    }
    	}	
    	
    	return medianArray[1];
	}
	
	public static void insertSort(int array[], int i, int length)
	{
     
        int swap = 0;
     
        //loop through each value in partition
    	for(int k = i + 1; k < length; k++)
    	{
    	    //while it is larger than the value in front, swap it out
    	    while(k > 0 && array[k] < array[k-1])
    	    {
    	    
    	        swap = array[k];
    	        array[k] = array[k-1];
    	        array[k-1] = swap;
    	        k--;
    	    }
    	}	

	}
	
	public static void heapBuild(int array[])
	{
	    int length = array.length;
	    
	    //have to do initial sort to get root
	    for(int i = length/2; i >= 0; i--)
	    {
	        sortHeap(array, length, i);
	    }
	    
	    //var to store end value/root
	    int end = 0;
	    //take away the root and redo heap each time
	    for(int i = length - 1; i >= 0; i--)
	    {
	        end = array[0];
	        array[0] = array[i];
	        array[i] = end;
	        
	        //redo heap for new root
	        sortHeap(array, i, 0);
	    }
	}
	
	public static void sortHeap(int array[], int length, int i)
	{
	    //set the largest to the current root passed in and the left and right of it
	    int root = i;
	    int left = 2 * i + 1; 
	    int right = 2 * i + 2; 
	    
	    //check if left child is bigger
	    if(left < length && array[left] > array[root])
	    {
	        root = left;
	    }
	    
	    //check if right child is bigger
	    if(right < length && array[right] > array[root])
	    {
	        root = right; 
	    }
	    
	    //set to hold variable to hold
	    int hold = 0;
	    
	    //reset root if no longer the same as orignally
	    if(root != i)
	    {
	       hold = array[i];
	       array[i] = array[root];
	       array[root] = hold;
	       
	       //need to call recursive on new root
	       sortHeap(array, length, root);
	    }
	}

	public static void main(String args[]) throws IOException
	{
	
	    //setting up readers and writers
	    BufferedReader input = null;
	    FileWriter output = null;
     
     	try
		{
			//input provided when program is called
			input = new BufferedReader(new FileReader(args[0]));
			output = new FileWriter(args[1]);

			
			//variables for timing
			long startTime;
			long stopTime;
			long totalTime = 0;
			
			
			//get line
		    String inputLine = input.readLine().trim();
		    String stringArray[];
		    stringArray = inputLine.split("\\s+");
		    
		    //set up int array
		    int intArray1[] = new int[stringArray.length];
		    int intArray2[] = new int[stringArray.length];
		    int intArray3[] = new int[stringArray.length];
		    int intArray4[] = new int[stringArray.length];
		    int intArray5[] = new int[stringArray.length];
		    
		    for(int i = 0; i < stringArray.length; i++)
		    {
		        intArray1[i] = Integer.parseInt(stringArray[i]);
		        intArray2[i] = Integer.parseInt(stringArray[i]);
		        intArray3[i] = Integer.parseInt(stringArray[i]);
		        intArray4[i] = Integer.parseInt(stringArray[i]);
		        intArray5[i] = Integer.parseInt(stringArray[i]);
		    }

			//quicksort w/ first item as pivot and stopping at 1-2
			startTime = System.nanoTime();
			sort1(intArray1, 0, intArray1.length-1);
			stopTime = System.nanoTime();
			totalTime = stopTime - startTime;
			output.write("Pivot on First Value, stopping at 1-2 time is " + 
			Long.toString(totalTime) + " nanoseconds" + "\n");
			
            for(int i = 0; i < intArray1.length; i++)
            {
                output.write(String.valueOf(intArray1[i]));
                output.write("\n");
            }
            
            //reset timing vars
            startTime = 0;
            stopTime = 0;
            totalTime = 0;
            
            //quicksort w/ pivot at first item and stopping at 100
            startTime = System.nanoTime();
			sort2(intArray2, 0, intArray2.length-1);
			stopTime = System.nanoTime();
			totalTime = stopTime - startTime;
			output.write("Pivot on First Value, stopping at 100 time is " + 
			Long.toString(totalTime) + " nanoseconds" + "\n");
			
            for(int i = 0; i < intArray2.length; i++)
            {
                output.write(String.valueOf(intArray2[i]));
                output.write("\n");
            }
            
            //reset timing vars
            startTime = 0;
            stopTime = 0;
            totalTime = 0;
            
            //quicksort w/ pivot at first item and stopping at 50
            startTime = System.nanoTime();
			sort3(intArray3, 0, intArray3.length-1);
			stopTime = System.nanoTime();
			totalTime = stopTime - startTime;
			output.write("Pivot on First Value, stopping at 50 time is " + 
			Long.toString(totalTime) + " nanoseconds" + "\n");
			
            for(int i = 0; i < intArray3.length; i++)
            {
                output.write(String.valueOf(intArray3[i]));
                output.write("\n");
            }
           
            //reset timing vars
            startTime = 0;
            stopTime = 0;
            totalTime = 0;
            
            //quicksort w/ pivot at median of three item and stopping at 1-2
            startTime = System.nanoTime();
			sort4(intArray4, 0, intArray4.length-1);
			stopTime = System.nanoTime();
			totalTime = stopTime - startTime;
			output.write("Pivot on median of three, stopping at 1-2 time is " + 
			Long.toString(totalTime) + " nanoseconds" + "\n");
			
            for(int i = 0; i < intArray4.length; i++)
            {
                output.write(String.valueOf(intArray4[i]));
                output.write("\n");
            } 
            
            //reset timing vars
            startTime = 0;
            stopTime = 0;
            totalTime = 0;
            
            //heapsort
            startTime = System.nanoTime();
			heapBuild(intArray5);
			stopTime = System.nanoTime();
			totalTime = stopTime - startTime;
			output.write("Heap Sort time is  " + 
			Long.toString(totalTime) + " nanoseconds" + "\n");
			
            for(int i = 0; i < intArray5.length; i++)
            {
                output.write(String.valueOf(intArray5[i]));
                output.write("\n");
            } 
			
		}
		
		finally
		{
			//close files
			input.close();
			output.close();
		}
        
    }
    
}