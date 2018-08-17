/*Lab 3
Krista Delap
April 22, 2018
Creates Huffman Tree from frequency table and encodes & decodes
input files*/

//importing allowed libraries for input and output
import java.io.FileReader;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.IOException;

public class Lab3_KDelap
{
	public static void main(String args[]) throws IOException
	{
	
	    //setting up readers and writers
	    BufferedReader freqInput = null;
	    FileReader encodeInput = null;
	    BufferedReader decodeInput = null;
	    FileWriter outputTree = null;
	    FileWriter outputEncode = null;
        FileWriter outputDecode = null;
		
		try
		{
		    //get the frequency table
		    freqInput = new BufferedReader(new FileReader(args[0]));
		    
		    //get first line
		    String freqRecord = freqInput.readLine();
		    
		    //set up holding vars for each input line
		    String letter;
		    int freq;
		    
		    //create list
		    List list = new List();
		    
		    //loop through lines and process each
		    while(freqRecord != null)
		    {
		        //array for string processing
		        //new each time
		        String record[];
		        record = freqRecord.split("\\s+");
		        
		        //make sure valid line
		        if(record.length > 1)
		        {
		            //parse out information
		            letter = record[0].toUpperCase();
		            freq = Integer.parseInt(record[2]);
		            
		            //set up new node
		            Node newNode = new Node();
		            newNode.createNew(letter, freq);
		            
		            //add to list
		            list.addNode(newNode, freq);
		        }
		        
		        //get next line
		        freqRecord = freqInput.readLine();
		    }
		    
		    //prep for tree creation
		    Node popped1 = null;
		    Node popped2 = null;
		    int size = list.size;
		    
		    //create tree
		    Tree tree = new Tree();
		    
		    //loop to build tree
		    while(size > 1)
		    {
		        //pop two smallest values
		        popped1 = list.pop();
		        popped2 = list.pop();
		        
		        //create new node from popped
		        Node treeNode = new Node();
		        String treeeNodeLetters = popped1.letter + popped2.letter;
		        String treeNodeLetter = "";
		        String hold = "";
		        
		        //alphabetize the new parent node letters
                String lettersArray[] = treeeNodeLetters.split("");
                for(int i = 0; i < lettersArray.length; i++)
                {
                    for(int j = i+1; j < lettersArray.length; j++)
                    {
                        if(lettersArray[j].compareTo(lettersArray[i]) < 0)
                        {
                            hold = lettersArray[i];
                            lettersArray[i] = lettersArray[j];
                            lettersArray[j] = hold;
                        }
                    }
                }
                
                //combine the alphabetized array into one string
                for(int k = 0; k < lettersArray.length; k++)
                {
                    treeNodeLetter += lettersArray[k];
                }

                //create the new tree node
		        int treeNodeFreq = popped1.freq + popped2.freq;
		        treeNode.createNew(treeNodeLetter, treeNodeFreq);
		        
		        //set root when at last two items
		        if(size == 2)
		        {
		            tree.setRoot(treeNode);
		        }
		        
		        //add new treeNode to queue
		        list.addNode(treeNode, treeNodeFreq);
		        
		        //build tree
		        //break ties
		        if(popped1.freq == popped2.freq)
		        {
		            //if 1 or 2 has only one letter, set left
		            if(popped1.letter.length() == 1)
		            {
		                tree.setLeft(treeNode, popped1);
		                tree.setRight(treeNode, popped2);
		            }
		            
		            else if(popped2.letter.length() == 1)
		            {
		                tree.setLeft(treeNode, popped2);
		                tree.setRight(treeNode, popped1);
		            }
		            
		            //otherwise set by alphabetical order
		            else if(popped1.letter.compareTo(popped2.letter) < 0)
		            {
		                tree.setLeft(treeNode, popped1);
		                tree.setRight(treeNode, popped2);
		            }
		            
		            else
		            {
		                tree.setLeft(treeNode, popped2);
		                tree.setRight(treeNode, popped1);
		            }
		        }
		        
		        //if freqs not equal, popped 1 is less than popped 2 by design
		        else
		        {
		            tree.setLeft(treeNode, popped1);
		            tree.setRight(treeNode, popped2);
		        }
		        
		        //get updated size
		        size = list.size;
		        //list.printList();
		        //System.out.print("\n");
		    }
		    
		    //print preOrder
		    outputTree = new FileWriter(args[1]);
		    tree.travelTree(tree.root, outputTree);
		    String code = "";
            outputTree.write("\n");
            //print codes
		    tree.printCodes(tree.root, outputTree, code);
		    
		    //get strings to encode from file
		    encodeInput = new FileReader(args[2]);
		    outputEncode = new FileWriter(args[3]);
		    int inputInt;
		    String inputLetter;
		    char inputChar;
		    
		    //read through input and print code
		    while((inputInt = encodeInput.read())!= -1)
			{
				inputChar = (char)inputInt;
				if(Character.isWhitespace(inputChar) == true)
				{
				    outputEncode.write(inputChar);
				}
				else
				{
				    //change to uppercase for comparison
				    inputLetter = (Character.toString(inputChar)).toUpperCase();
		            tree.findCode(tree.root, outputEncode, code, inputLetter);
		        }
		    }
		    
		    //get strings to decode from file
		    decodeInput = new BufferedReader(new FileReader(args[4]));
		    outputDecode = new FileWriter(args[5]);
		    
		    String decodeLine = decodeInput.readLine();
		    //read through input and print code
		    while(decodeLine != null)
			{
		            tree.findLetter(tree.root, outputDecode, decodeLine);
		            outputDecode.write("\n");
		        
		        decodeLine = decodeInput.readLine();
		    }

		}
		
		//close readers & writers
		finally
		{
		    if(freqInput != null)
		    {
		        freqInput.close();
		    }
		    if(encodeInput != null)
		    {
		        encodeInput.close();
		    }
		    if(decodeInput != null)
		    {
		        decodeInput.close();
		    }
		    if(outputTree != null)
		    {
		        outputTree.close();
		    }
		    if(outputEncode != null)
		    {
		        outputEncode.close();
		    }
		    if(outputDecode != null)
		    {
		        outputDecode.close();
		    }
		}
	
	}
}