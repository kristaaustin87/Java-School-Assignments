/*Lab 2
Krista Delap
March 24, 2018
Compares run times for solving the Towers of Hanoi
iteratively and also recursively*/

//importing allowed libraries for output, input, and math calculation
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.Math;

public class Lab2_KDelap 
{
	public static void recursiveSolution(int n, String a, String b, String c, FileWriter output, int total) throws IOException
	{
	
		//base case
		if(n == 1)
		{
			if (total <= 5)
			{
				output.write("Move disk 1 from " + a + " to " + c + "\n" + "\n");
			}
			return;
		}
		
		//recursive call for n-1, until n = 1
		//flips towers b and c from the previous pass
		//to move disks from a to b or c alternating
		recursiveSolution(n-1, a, c, b, output, total);
		
		//print instructions from this round above call
		//above call will be for previous step, since we are looking for
		//n = 1
		if (total <= 5)
		{
			output.write("Move disk " + n + " from " + a + " to " + c + "\n" + "\n");
		}
		//recursive call to move n-1 from the holding tower
		//rotates through towers
		recursiveSolution(n-1, b, a, c, output, total);
	}
	
	public static void main(String args[]) throws IOException
	{
	
		//preparing for the output file
		FileWriter output = null;
		BufferedReader input = null;
				
		try
		{
			//set up output file, accepts argument, bufferreader to read by line
			output = new FileWriter(args[1]);
			input = new BufferedReader(new FileReader(args[0]));
			
			//set up time vars
			long startTime;
			long stopTime;
			long totalTime = 0;
			long maxTime = 300000000000L;
			//to track n disks
			String line = input.readLine();
			int n;
			double moves;
			//vars for the three towers
			String a = "Tower A";//where the disks start
			String b = "Tower B";
			String c = "Tower C";//where the disks should all go
			//vars for tracking top disks and movements
			int topA;
			int topB;
			int topC;
			int disk; 
			String fromTower; 
			String toTower;
			
			//stacks for tracking disks are where
			Stack stackA = new Stack();
			Stack stackB = new Stack();
			Stack stackC = new Stack();
			//for tracking where time out occurs
			boolean maxReached = false;
			boolean maxStepsReached = false;
				
			//while loop stops trying to work problem
			//when file read is done
			
			while(line != null)
			{	
				
				//check to make sure its a number
				if(line.matches("-?\\d+"))
				{
				
				//convert line to int
				n = Integer.parseInt(line);
				
				
				//need to calculate moves for loop
				moves = Math.pow(2,n) - 1;
				output.write("\n" + "For n is " + n + ", " + moves + " moves are required." + "\n");
				output.write("____________________________________" + "\n" + "\n");
				
				//for iterative loop
				//initialize stack A
				for(int i = n; i > 0; i--)
				{
					stackA.push(i);
				}
				
				stackB.push(0);
				stackC.push(0);
				
				//initialize top tracking vars
				topA = stackA.peek();
				topB = 0;
				topC = 0; 
				disk = 0;
				fromTower = "";
				toTower = "";
					
					//to keep the file from getting too big
					if(n >= 6 && maxStepsReached==false)
					{
						output.write("Steps threshold reached - further steps will not be printed" + "\n" + "\n");	
						maxStepsReached = true;
					}
					
					if(maxReached == false)
					{
					
					output.write("Iterative Solution" + "\n");
					output.write("------------------" + "\n" + "\n");
					
					//towers b and c are reversed when n is even when tested
					//all disks ended up on b
					//need to reverse b and c for evens
					if(n % 2 == 0)
					{
						b = "Tower C";
						c = "Tower B";
					}
					
					//start timing
					startTime = System.nanoTime();
					
						/*each move completes the following steps:
						1 - sets disk to top value of source tower
						for printing moves
						2 - pops value of source tower and pushes
						to dest tower + sets top value of that tower
						for tracking
						3 - sets top value of source tower thru peek
						4 - sets tower vars for printing moves
						Moves are determined based on place in iteration
						as moves occur in pattern  between sets of towers*/
						for(int i = 1; i <= moves; i++)
						{
						
							//make the move between b and c towers
							if(i % 3 == 0)
							{
								if(topB < topC)
								{
									disk = topB; 
									topC = stackB.pop();
									stackC.push(topC);
									topB = stackB.peek();
									fromTower = b;
									toTower = c;
								}
								
								else
								{
									disk = topC; 
									topB = stackC.pop();
									stackB.push(topB);
									topC = stackC.peek();
									fromTower = c;
									toTower = b;
									
								}
							}
							
							//for moves between a and c towers
							if(i % 3 == 1)
							{
							
								if((topA < topC  && topA !=0) || topC == 0)
								{
									disk = topA;
									topC = stackA.pop();
									stackC.push(topC);
									topA = stackA.peek();
									fromTower = a;
									toTower = c;
								}
								
								else
								{
									disk = topC; 
									topA = stackC.pop();
									stackA.push(topA);
									topC = stackC.peek();
									fromTower = c;
									toTower = a;
								}
	
							}
							
							//for moves between a and b towers
							if(i % 3 == 2)
							{
							
								if((topA < topB && topA !=0) || topB == 0)
								{
									disk = topA; 
									topB = stackA.pop();
									stackB.push(topB);
									topA = stackA.peek();
									fromTower = a;
									toTower = b;
								}
								
								else
								{
									disk = topB; 
									topA = stackB.pop();
									stackA.push(topA);
									topB = stackB.peek();
									fromTower = b;
									toTower = a;
								}
								
							}
							
							//print out move
							if (n <= 5)
							{
								output.write("Move disk " + disk + " from " + fromTower + " to " + toTower + "\n");
								output.write("\n");
							}
							//following line for testing
							//output.write("TopA " + topA + " TopB " + topB + " TopC " + topC + "\n");
							
						}
			
					//stop timing
					stopTime = System.nanoTime();
					
					//need to clear stacks
					stackA.emptyStack();
					stackB.emptyStack();
					stackC.emptyStack();
					
					//reset towers
					b = "Tower B";
					c = "Tower C";
			
					//calculate time elapsed for the last loop
					totalTime = stopTime - startTime;
			
					//print time elapsed
					output.write("* " + Long.toString(totalTime) + " nanoseconds *" + "\n" + "\n");
					
					}
					
				//set up for next loop or break
				//if time too high
				if(totalTime >= maxTime)
				{
					if(maxReached == false)
					{
						output.write("Total Time maximum threshold reached" + "\n");
					}
					maxReached = true;
				}
					
				else
				{
					totalTime = 0;
				}
					
					//recursive loop
					if(maxReached == false)
					{
					
					output.write("Recursive Solution" + "\n");
					output.write("------------------" + "\n" + "\n");
					
					//start timing
					startTime = System.nanoTime();
					
					//make call to recursive method
					recursiveSolution(n, a, b, c, output, n);
			
					//stop timing
					stopTime = System.nanoTime();
			
					//calculate time elapsed for the last loop
					totalTime = stopTime - startTime;
			
					//print time elapsed
					output.write("* " + Long.toString(totalTime) + " nanoseconds *" + "\n" + "\n");
					}
					}
					else
					{
						output.write("n is not a number" + "\n" + "\n");
					}
					
				//set up for next loop or break
				//if time too high
				if(totalTime >= maxTime)
				{
					if(maxReached == false)
					{
						output.write("Total Time maximum threshold reached" + "\n");
					}
					maxReached = true;
				}
					
				else
				{
					totalTime = 0;
				}
					
				//read next line	
				line = input.readLine();
			}
		
		}
		
		finally
		{
		
			//close file
			input.close();
			output.close();
			
		}
		
	}
	
}