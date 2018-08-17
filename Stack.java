/*class to implement a stack
using an array; reutilized from Lab 1*/
class Stack
{
	/*initialize the top of the stack
	at position -1, and can add one for 
	each push to track the top*/
	private int head = -1;	
	
	/*constructor for stack;
	set max to 50 for bounds checking
	so that tower movements
	do not time out*/
	int stack[] = new int[100];
		
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
		if(head >= 99)
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
	public void push(int item)
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
	public int pop()
	{
		int item;
		
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
	
	public int peek()
	{
	
		int item;
		
		/*checks to make sure stack
		is not empty*/
		if(stackEmpty() == false)
		{
			item = stack[head];
		}
		
		else
		{	
			item = 0;
		}
		
		return item;
		
	}
	
}