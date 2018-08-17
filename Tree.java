/*Lab 3 Tree Structure
Krista Delap
April 22, 2018
Making the Tree*/

import java.io.FileWriter;
import java.io.IOException;

public class Tree
{
    Node root;
    int size;
    
    //set left pointer
    public void setLeft(Node parentNode, Node leftChild)
    {
        parentNode.leftPtr = leftChild;
        leftChild.treeParentPtr = parentNode;
        //increase size of tree
        size++;
    }
    
    //set right pointer
    public void setRight(Node parentNode, Node rightChild)
    {
        parentNode.rightPtr = rightChild;
        rightChild.treeParentPtr = parentNode;
        //increase size of tree
        size++;
    }
    
    //set the root of the tree
    public void setRoot(Node root)
    {
        this.root = root;
    }
    
    //for printing the tree uses recursive call
    public void travelTree(Node node, FileWriter outputTree) throws IOException
    {       
            if(node == null)
            {
                return;
            }
            
            //write each node
            outputTree.write(node.letter + " : " + node.freq + "\n");
            //recursive call to each child
                travelTree(node.leftPtr, outputTree);
                travelTree(node.rightPtr, outputTree);   
    }
    
    //to print the codes
    public void printCodes(Node node, FileWriter outputTree, String code) throws IOException
    {
        //if reached a child node, print the code and the letter
        if(node.leftPtr == null && node.rightPtr == null)
        {
            outputTree.write(node.letter + " " + code + "\n");
            return;
        }
        
        //call to the left and right
        printCodes(node.leftPtr, outputTree, code + "0");
        printCodes(node.rightPtr, outputTree, code + "1");
    }
    
    //searching for a code by letter
    public void findCode(Node node, FileWriter outputTree, String code, String letter) throws IOException
    {
        //if leaf node found and the node is the right letter, print code
        if(node.leftPtr == null && node.rightPtr == null && node.letter.equals(letter))
        {
            outputTree.write(code);
            return;
        }
        
        //if leaf node found that does not match, return nothing
        else if(node.leftPtr == null && node.rightPtr == null)
        {
            return;
        }
        
        //call again & add either 0 or 1 for left or right
        findCode(node.leftPtr, outputTree, code + "0", letter);
        findCode(node.rightPtr, outputTree, code + "1", letter);
    }
    
    //find letter by code
    public void findLetter(Node node, FileWriter outputDecode, String decodeLine) throws IOException
    {
        Node root = node;
        
        //loop through the input line
        for(int i = 0; i < decodeLine.length(); i++)
        {
            //go left for 0
            if(decodeLine.charAt(i) == '0')
            {
                node = node.leftPtr;
            }
            
            //go right for 1
            else
            {
                node = node.rightPtr;
            }
            
            //if leaf node found, the path is complete, print letter & reset to root
            if(node.leftPtr == null && node.rightPtr == null)
            {
                outputDecode.write(node.letter);
                node = root;
            }
        }
        
    }
    
}