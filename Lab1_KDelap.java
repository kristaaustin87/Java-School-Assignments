/*Lab 1
Krista Delap
March 3, 2018
Prints postfix expression instructions
from stack*/

//importing allowed libraries for input/output calls
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/*class to implement a stack
using an array*/
class Stack
{
	/*initialize the top of the stack
	at position -1, and can add one for 
	each push to track the top*/
	private int head = -1;	
	
	/*constructor for stack;
	set max to 16 to prevent 
	overflow of input and for 
	bounds checking*/
	char stack[] = new char[16];
		
	//to determine if the stack is empty
	//based on head position
	public boolean stackEmpty()
	{
		if(head < 0)
		{
			return true;
		}
			
		else
		{
			return false;
		}
	}
	
	//to determine if stack is full
	//based on head position
	public boolean stackFull()
	{
		if(head >= 15)
		{
			return true;
		}
			
		else
		{
			return false;
		}
	}
	
	/*to push must increase head 
	by one and set item at that
	location*/
	public void push(char item)
	{
		/*checks to see if stack is
		already full*/
		if(stackFull() == false)
		{
			head++;
			stack[head] = item;
		}
	}
	
	/*to pop must return item at
	head location and move
	head back*/
	public char pop()
	{
		char item;
		
		/*checks to make sure stack
		is not empty*/
		if(stackEmpty() == false)
		{
			item = stack[head];
			head--;

		}
		
		else
		{	
			item = 0;
		}
		
		return item;
	}
	
	/*empty the stack by resetting the 
	head pointer to -1 and overwriting*/
	public void emptyStack()
	{
		head = -1;
	}
	
}
	
public class Lab1_KDelap 
{
	
	public static void main(String args[]) throws IOException
	{
		//preparing for the input and output files
		FileReader input = null;
		FileWriter output = null;
		
		try
		{
			//input provided when program is called
			input = new FileReader(args[0]);
			output = new FileWriter(args[1]);
			
			//to track from file
			int expressionInt;
			//to hold converted int
			char expressionChar;
			//user this var as counter for temps
			int n = 1;
			//use these to store popped values
			char operand1;
			char operand2;
			//used to check for errors
			boolean error = false;
			
			//creating a new stack
			Stack stack = new Stack();
			
			/*using a while loop to process input
			one character at a time*/
			while((expressionInt=input.read())!= -1)
			{
				expressionChar = (char)expressionInt;
				
				/*reset error code if new line is detected
				and remove last value from the stack
				leftover from last expression as well as 
				reset n value for temps*/
				if(expressionChar == '\n')
				{
					if(error == false)
					{
						stack.pop();
						n = 1;
					}
					
					error = false;
				}
				
				/*skip remaining input for an expression
				if error has been detected*/
				if(error == false)
				{
				/*using a switch to control operations
				based on current expressionChar*/

				switch (expressionChar)
				{
				
				/*each operator case checks to be sure stack is 
				not empty before popping two values
				then prints instructions for those,
				then push temp value placeholder,
				increase n counter,
				and print instruction to 
				place temp value in stack*/
					case '+' : 
						if(stack.stackEmpty() == true)
						{
							output.write("**Error - Operator Found But Stack Empty** \n");
							error = true;
							break;
						}
						operand1 = stack.pop();
						if(stack.stackEmpty() == true)
						{
							output.write("**Error - Operator Found But Stack Empty** \n");
							error = true;
							break;
						}
						operand2 = stack.pop();
						
						if(Character.isDigit(operand2) == true)
						{
							output.write("LD TEMP" + operand2 + "\n");
						}
						else
						{
							output.write("LD " + operand2 + "\n");
						}
						if(Character.isDigit(operand1) == true)
						{
							output.write("AD TEMP" + operand1 + "\n");
						}
						else
						{
							output.write("AD " + operand1 + "\n");
						}
						
						stack.push((char)(n + '0'));
						output.write("ST TEMP" + n + "\n");
						n++;
					
						break;
					
					case '-':
						if(stack.stackEmpty() == true)
						{
							output.write("**Error - Operator Found But Stack Empty** \n");
							error = true;
							break;
						}
						
						operand1 = stack.pop();
						if(stack.stackEmpty() == true)
						{
							output.write("**Error - Operator Found But Stack Empty** \n");
							error = true;
							break;
						}
						operand2 = stack.pop();
						
						if(Character.isDigit(operand2) == true)
						{
							output.write("LD TEMP" + operand2 + "\n");
						}
						else
						{
							output.write("LD " + operand2 + "\n");
						}
						if(Character.isDigit(operand1) == true)
						{
							output.write("SB TEMP" + operand1 + "\n");
						}
						else
						{
							output.write("SB " + operand1 + "\n");
						}

						stack.push((char)(n + '0'));
						output.write("ST TEMP" + n + "\n");
						n++;
					
						break;
						
					case '*':
						if(stack.stackEmpty() == true)
						{
							output.write("**Error - Operator Found But Stack Empty** \n");
							error = true;
							break;
						}
						operand1 = stack.pop();
						
						if(stack.stackEmpty() == true)
						{
							output.write("**Error - Operator Found But Stack Empty** \n");
							error = true;
							break;
						}
						
						operand2 = stack.pop();
			
						if(Character.isDigit(operand2) == true)
						{
							output.write("LD TEMP" + operand2 + "\n");
						}
						else
						{
							output.write("LD " + operand2 + "\n");
						}
						if(Character.isDigit(operand1) == true)
						{
							output.write("ML TEMP" + operand1 + "\n");
						}
						else
						{
							output.write("ML " + operand1 + "\n");
						}
						
						stack.push((char)(n + '0'));
						output.write("ST TEMP" + n + "\n");
						n++;
					
						break;
						
					case '/':
						if(stack.stackEmpty() == true)
						{
							output.write("**Error - Operator Found But Stack Empty** \n");
							error = true;
							break;
						}
						operand1 = stack.pop();
						if(stack.stackEmpty() == true)
						{
							output.write("**Error - Operator Found But Stack Empty** \n");
							error = true;
							break;
						}
						operand2 = stack.pop();
						
						if(Character.isDigit(operand2) == true)
						{
							output.write("LD TEMP" + operand2 + "\n");
						}
						else
						{
							output.write("LD " + operand2 + "\n");
						}
						if(Character.isDigit(operand1) == true)
						{
							output.write("DV TEMP" + operand1 + "\n");
						}
						else
						{
							output.write("DV " + operand1 + "\n");
						}
						
						stack.push((char)(n + '0'));
						output.write("ST TEMP" + n + "\n");
						n++;
					
						break;
						
					case '$':
						if(stack.stackEmpty() == true)
						{
							output.write("**Error - Operator Found But Stack Empty** \n");
							error = true;
							break;
						}
						operand1 = stack.pop();
						if(stack.stackEmpty() == true)
						{
							output.write("**Error - Operator Found But Stack Empty** \n");
							error = true;
							break;
						}
						operand2 = stack.pop();
						
						if(Character.isDigit(operand2) == true)
						{
							output.write("LD TEMP" + operand2 + "\n");
						}
						else
						{
							output.write("LD " + operand2 + "\n");
						}
						//RP for raise to power of
						if(Character.isDigit(operand1) == true)
						{
							output.write("RP TEMP" + operand1 + "\n");
						}
						else
						{
							output.write("RP " + operand1 + "\n");
						}
						
						stack.push((char)(n + '0'));
						output.write("ST TEMP" + n + "\n");
						n++;
					
						break;
					
					//for breaking between expressions
					case '\n':
						//checking to make sure expression complete
						if(stack.stackEmpty() == false)
						{
							output.write("**Error - Stack Not Empty**" + "\n"); 
							//empty the stack for next expression 
							stack.emptyStack(); 
						}
						
						output.write("**End Expression**" + "\n");
						output.write("\n");
						break;
					
					//for non-operator input
					default : 
						/*if digit, return error for the purposes of 
						this lab assignment, numbers are represented
						by letters only*/
						if(Character.isDigit(expressionChar) == true)
						{
							error = true;
							output.write("**Error - Digit Found** \n");
						}
						//return error for unrecognized operators
						else if(expressionChar == '.' || expressionChar == '?' || expressionChar == '='
						|| expressionChar == '|' || expressionChar == '<' || expressionChar == '>'
						|| expressionChar == '%' || expressionChar == '^')
						{
							error = true;
							output.write("**Error - Unrecognized Operator** \n");
						}
						//skip whitespaces
						else if(Character.isWhitespace(expressionChar) == true)
						{
						}
						//otherwise push
						else
						{
							//unless stack is already full
							if(stack.stackFull() == true)
							{
								output.write("**Error - Operand Found But Stack Full** \n");
								error = true;
								break;
							}
							else
							{
								stack.push(expressionChar);
							}
						}
						break;
				}
				
				}
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