// Colby Gilbert
// 3/04/2015
// TA: Karandir Singh
// 1362877
// Assignmnt #7

// DESCRIPTION: a 20 question object that intializes functinality, play() plays
//              a game, save() saves current memory question/answers of object
//              load() loads questions/answers to object, totalGames() returns
//              number of games played, gamesWon() returns number of games won
//              by the computer. 

import java.util.*;
import java.io.*;

public class QuestionTree {
   private int totalGames;
   private int gamesWon;
   private UserInterface ui;
   private QuestionNode overAllRoot;
   
   // PRE:  a valid UserInterface object must be passed if object is null throws
   //       IllegalArgumentException 
   // POST: Intializes fields for process in this object
   public QuestionTree(UserInterface ui) {
      check(ui);
      this.ui = ui;
      this.overAllRoot = new QuestionNode("computer");
      this.totalGames = 0;
      this.gamesWon = 0;
   }
   
   // POST: plays one game with user, if computer loses, program will ask for 
   //       info to be able to find answer on the next play
   public void play() {
      this.totalGames++;
      play(this.overAllRoot);
   }
   
   // PRE:  overAllRoot of current tree stored in object must be passed
   // POST: plays one game with user, if computer loses, program will ask for 
   //       info to be able to find answer on the next play
   private void play(QuestionNode root) {
      // if node is a leaf(answer) else node is question
      if(root.left == null && root.right == null) {
         ui.print("Would your object happen to be " + root.data + "?");
         // if true computer wins else computer losses
         if(ui.nextBoolean()) {
            ui.println("I win!");
            this.gamesWon++;
         } else {
            ui.print("I lose. What is your object?");
            // creates new QuestionNode of user answer as data field
            QuestionNode answer = new QuestionNode(ui.nextLine());
            ui.print("Type a yes/no question to distinguish your item from " + root.data + ":");
            String question = ui.nextLine();
            ui.print("And what is the answer for your object?");
            // intialize node to maintane scope
            QuestionNode branch;
            // if user answer is yes then answer to question is yes(left on tree) else inverse
            if(ui.nextBoolean()) {
               branch = new QuestionNode(question, answer, root);
            } else {
               branch = new QuestionNode(question, root, answer); 
            }     
            this.overAllRoot = remove(root.data, branch, overAllRoot);
         }   
      } else {
         this.ui.print(root.data);
         // if user answer is yes(true) go left on tree else go right on tree
         if(ui.nextBoolean()) {
            play(root.left);
         } else {
            play(root.right);
         }
      }        
   }
   
   // PRE:  paramater of String Search to search tree for, and QuestionNode 
   //       replace to replace leaf with new tree, and root of current positon of tree
   //       only claaed when computer has lost and is updating its question/answer set
   // POST: returns an updated tree with new questions/answers
   private QuestionNode remove(String search, QuestionNode replace, QuestionNode root) {
      // root is null do nothing
      if(root != null) {
         // if not found keep seraching
         if(root.data != search) {
            root.left = remove(search, replace, root.left);
            root.right = remove(search, replace, root.right);
         } else {
            // otherwise switch root with repalcement tree (replace)
            root = replace;
         }
      }   
      return root;
   }      
            
   
   // PRE:  a precontructed PrintStream object must be passed if ouptut is null
   //       throws IllegalArgumentException
   // POST: outputs data of current questions/answers to passed PrintStream 
   //       object   
   public void save(PrintStream output) {
      check(output);
      save(output, this.overAllRoot);
   }
   
   // PRE:  parameters of a constructed PrintStream object to output data and a
   //       QuestionNode of a tree.
   // POST: saves momery of questions/ answers in format of preorder traversal 
   //       and "A: (answer)" and "Q:(queation)" 
   private void save(PrintStream output, QuestionNode root) {
      if(root.left == null && root.right == null) {
         output.println("A:" + root.data);
      } else {
         output.println("Q:" + root.data);
         save(output, root.left);
         save(output, root.right);
      }      
   }
   
   // PRE:  paramater of a preconscructed scanner file of on a memory.txt file 
   //       must be passed, if input is null throws IllegalArgumentException
   // POST: loads question/answers to QuestionTree object to use for game     
   public void load(Scanner input) {
      check(input);
      overAllRoot = loader(input);
   }
   
   // PRE:  paramater of a preconscructed scanner file of on a memory.txt file 
   //       must be passed file must be in format of pre-order traversal and of
   //       "A:(answer)" and "Q:(question)"  
   // POST: loads question/answers to QuestionTree object to use for game, 
   //       returns tree of questions to answers 
   private QuestionNode loader(Scanner input) {
      if(input.hasNext()) {
         // EX: "Q:(Question) in to ["Q","(question)"]
         String[] phrase = input.nextLine().split(":");
         QuestionNode root = new QuestionNode(phrase[1]);
         if(phrase[0].equals("Q")) {
            // x = change(x);
            root.left = loader(input);
            root.right = loader(input);
         }
         return root; 
      }
      // end of file
      return null;        
   } 
   // POST: returns the number of games played
   public int totalGames() {
      return this.totalGames;
   }
   
   // POST: returns number of games won by the computer
   public int gamesWon() {
      return this.gamesWon;
   }
   // PRE:  an Object (E) must be passed
   // POST: if E is null throws IllegalArgumentException          
   private void check(Object E) {
      if(E == null) {
         throw new IllegalArgumentException("is null");
      }
   }      
}   