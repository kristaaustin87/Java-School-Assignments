/*Lab 3 Node Class
Krista Delap
April 22, 2018
Nodes to hold the data for each letter*/

public class Node
{
    //variables
    String letter;
    int freq;
    Node leftPtr = null;
    Node rightPtr = null;
    Node parentPtr = null;
    Node nextPtr = null;
    Node treeParentPtr = null;
    boolean visitedRight = false;
    
    //create a new node object
	public void createNew(String letter, int freq)
	{
	    this.letter = letter;
	    this.freq = freq;
	}
	
}