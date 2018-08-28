// Colby Gilbert
// 3/11/2015
// TA: Karandir Singh
// 1362877
// Assignmnt #8

// DESCRIPTION: used to make a key(binary tree)for huffman encoding

import java.util.*;

public class HuffmanNode implements Comparable<HuffmanNode> {
   public int freq;
   public int data;
   public HuffmanNode left;
   public HuffmanNode right;
      
   // PRE:  char data being the character that was used in file, 
   // POST: Intializes a HuffmanNode
   public HuffmanNode(int data, int freq) {
      this(data, freq, null, null);
   }
   
   // PRE:  char data being being the character that was used in file
   //       int freq is the frequency of the charater from file
   //       left and right can be null or another QuestionNode.
   // POST: Intializes a HuffmanNode
   public HuffmanNode(int data, int freq, HuffmanNode left, HuffmanNode right) {
      this.data = data;
      this.freq = freq;
      this.left = left;
      this.right = right;
   }
   
   // PRE:  parameter other is another node to compare.
   // POST: if this nodes freq is less than other then this node is great. if 
   //       other's freq is less it is greater
   public int compareTo(HuffmanNode other) {
      return this.freq - other.freq;  
   }
}