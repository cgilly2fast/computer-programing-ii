// Colby Gilbert
// 3/11/2015
// TA: Karandir Singh
// 1362877
// Assignmnt #8

// DESCRIPTION: data structure used for encoding and decoding text files that 
//              uses ASCII characters  

import java.io.*;
import java.util.*;

public class HuffmanTree {
   private HuffmanNode overallRoot;
   private char eof;
   public final static int BRANCH = -1; 
   
   // PRE:  paramter counts is the frequencies of chararcters from text file if
   //       counts is null throws IllegalArgumentException
   // POST: Intailizes a huffman tree ready for enncoding.  
   public HuffmanTree(int[] counts) {
      Queue<HuffmanNode> list = new PriorityQueue<HuffmanNode>();
      
      for(int i = 0; i < counts.length; i++) {
         // if that character appeared in file then add to priority queue 
         if(counts[i] != 0) {   
            HuffmanNode leaf = new HuffmanNode(i,counts[i]);
            list.add(leaf);
         }   
      }
      // eof character added to que
      list.add(new HuffmanNode(counts.length, 1));
      // keep running intill queue only has one root left
      while(list.size() > 1) {
         HuffmanNode left = list.remove();
         HuffmanNode right = list.remove();
         list.add(new HuffmanNode(BRANCH, left.freq + right.freq, left, right));
         
      }
      this.overallRoot = list.remove();
   }
   
   // PRE:  paramatar output is where the write method will save the 
   //       code file
   // Post: codefile outputed to output   
   public void write(PrintStream output) {
      write(output, overallRoot, "");
   }
   
   // PRE:  paramatar output is where the write method will save the 
   //       code file, root is current root, and binary is the code 
   //       for the ascii character 
   // Post: code file outputed to output
   private void write(PrintStream output, HuffmanNode root, String binary) {
      // if you havent gone to far then 
      if(root != null) {
         // if root is a leaf then output to data and code else explore
         if(root.data != BRANCH) {
            output.println(root.data);
            output.println(binary);
         } else {
            write(output, root.left, binary + "0"); 
            write(output, root.right, binary + "1");
         }
      }         
   }
      
   // PRE:  paramter input is a scanner contructed on a text file, if scanner is
   //       null throws IllegalArgumentException
   // POST: Intailizes a huffman tree ready for enncoding. 
   public HuffmanTree(Scanner input) {
      // keep going intill no more code in file
      while(input.hasNextLine()) {
         int ascii = Integer.parseInt(input.nextLine());
         String binary = input.nextLine();
       
         this.overallRoot = huffmanTree(overallRoot, binary, ascii);
      }   
   }
   
   // PRE:  paramter input is a scanner contructed on a text file, String Binary
   //       is the to be read code for ascii character, and int ascii is the 
   //       ascii charcter for the binary(code)  
   // POST: Intailizes a huffman tree ready for enncoding.
   private HuffmanNode huffmanTree(HuffmanNode root, String binary, int ascii) {
      // is branch hasnt not been created for binary path then make one 
      if(root == null) {
         root = new HuffmanNode(BRANCH, 0);
      }
      // if there is still more binary to be read then read it else ascii to root 
      if(binary.length() > 0) {
         // if it is zero then go left else go right
         if(binary.charAt(0) == '0') {
            root.left = huffmanTree(root.left, binary.substring(1), ascii);
         } else {
            root.right = huffmanTree(root.right, binary.substring(1), ascii);
         }
      } else {
         root.data = (char)ascii;
      }         
      return root;               
   }
      
   // PRE:  parameter input is a stream of bits to be decoded, output is where 
   //       decoded is saved to and eof is the end of file charater that tell 
   //       the program to stop
   // POST: decode file outputed to output
   public void decode(BitInputStream input, PrintStream output, int eof) {
      HuffmanNode current = overallRoot;
      // keep running intil eof is found
      while(current.data != eof) {
         // if current is not a branch then write it acsii symbol 
         if (current.data != BRANCH) {
            output.write(current.data);
            // start from top again for next ascii
            current = overallRoot;
         // if next bit is zero go left else go right
         } else if (input.readBit() == 0) {
            current = current.left;
         } else {
            current = current.right;  
         }
      }   
   }         
}     