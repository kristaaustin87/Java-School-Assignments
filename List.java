/*Lab 3 List Structure
Krista Delap
April 22, 2018
Trying to make a priority queue implemented
as a doubly linked list*/

public class List
{
    //variables
    Node head = null;
    Node tail = null;
    int size = 0;
    
	public void addNode(Node node, int pri)
	{
	    //increase size each time a node is added
	    size++;
	    
	    //check if list is empty
	    if(isEmpty() == true)
	    {
	        //if it is, the node is the head and tail
	        head = node;
	        tail = node;
	    }  
	    
	    //find where it should go
	    else
	    {
	        //get priorities of head and tail
	        int tailPri = tail.freq;
	        int headPri = head.freq;
	        
	        //if bigger than tail, add to tail
	        if(pri > tailPri)
	        {
	            tail.nextPtr = node;
	            node.parentPtr = tail;
	            tail = node;
	        }
	        
	        else if (pri == tailPri)
	        {
	            //figure out which is first alphabetically
	            if(node.letter.compareTo(tail.letter) > 0)
	            {
	                node.parentPtr = tail.parentPtr;
	                tail.parentPtr = node;
	                node.nextPtr = tail;
	            }
	            
	            else
	            {
	                tail.nextPtr = node;
	                node.parentPtr = tail;
	                tail = node;
	            }
	        }
	        
	        //if less than head, add to beginning
	        else if(pri < headPri)
	        {
	            node.nextPtr = head;
	            head.parentPtr = node;
	            head = node;
	        }
	        
	        else if (pri == headPri)
	        {
	            //figure alphabetical order
	            if(node.letter.compareTo(tail.letter) < 0)
	            {
	                node.nextPtr = head;
	                head.parentPtr = node;
	                head = node;
	            }
	            
	            else
	            {
	                node.nextPtr = head.nextPtr;
	                head.nextPtr = node;
	                node.parentPtr = head;
	            }
	        }
	        
	        //else traverse the list and place where it goes
	        else
	        {
	            //set to first node
	            Node nextNode = head.nextPtr;
	            //use a for loop to traverse the list
	            for(int i = 1; i < size; i++)
	            {
	                //if found location, add there
	                if(pri < nextNode.freq)
	                {
	                    node.nextPtr = nextNode;
	                    node.parentPtr = nextNode.parentPtr;
	                    Node parent = nextNode.parentPtr;
	                    parent.nextPtr = node;
	                    nextNode.parentPtr = node;
	                    break;
	                }
	                
	                //else move to next node
	                else
	                {
	                    nextNode = nextNode.nextPtr;
	                }
	            }
	        }
	    } 
	}
	
	//determine if list is empty
	public boolean isEmpty()
	{
	    if(this.head == null)
	    {
	        return true;
	    }
	    else
	    {
	        return false;
	    }
	}
	
	//print list, used for testing
	public void printList()
	{
	    Node node = head;
	    for(int i = 0; i < size; i++)
	    {
	        System.out.print(node.letter);
	        System.out.print(node.freq);
	        System.out.print("\n");
	        node = node.nextPtr;
	    }
	}
	
	//remove top node
	public Node pop()
	{
	    //check if empty first
	    if(isEmpty() == false)
	    {
	        Node popped = head;
	        
	        //return the head of the queue & reset if at end
	        if(head == tail)
	        {
	            head = null;
	            tail = null;
	        }
	        
	        //otherwise set new head
	        else
	        {
	            Node nextHead = head.nextPtr;
	            head.nextPtr = null;
	            head = nextHead;
	            head.parentPtr = null;
	        }
	        //decrease size of list
	        size--;
	        return popped;
	    }
	    
	    else
	    {
	        return null;
	    }
	}
}
	