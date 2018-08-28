// Colby Gilbert
// 3/04/2015
// TA: Karandir Singh
// 1362877
// Assignmnt #7

// DESCRIPTION:

import java.util.*;
import java.io.*;

public class QuestionTree {
   private int totalGames;
   private int gamesWon;
   private Userinterface ui;
   private QuestionNode overAllRoot;
   
   public QuestionTree(UserInterface ui) {
      check(ui);
      this.ui = ui;
      this.root = new QuestionNode("computer");
      this.totalGames = 0;
      this.gamesWon = 0;
   }
   
   public void play() {
      this.totalGames++;
      play(this.overAllRoot);
   }
   
   //
   private void play(QuestionNode root) {
      if(root.left == null && root.right == null) {
         ui.print("Would your object happen to be " + root.data + " ");
         if(ui.nextBoolean()) {
            ui.println("I win!");
            this.gamesWon++;
         } else {
            ui.print("I lose. What is your object? ");
            QuestionNode answer = new QuestionNode(ui.nextLine());
            ui.print("Type a yes/no question to distinguish your item from " + root.data + " ");
            String question = ui.nextLine();
            ui.print("And what is the answer for your object? ");
            QuestionNode branch;
            if(ui.nextLine()) {
               branch = new QuestionNode(question, answer, root);
            } else {
               branch = new QuestionNode(question, root, answer); 
            }     
            this.overAllRoot = remove(root.data, branch, overAllRoot);
         }   
      } else {
         this.ui.print(root.data);
         if(ui.nextBoolean()) {
            root.left = play(root.left);
         } else {
            root.right = play(root.right);
         }
      }        
   }
   
   //
   private QuestionNode remove(String search, QuestionNode replace, QuestionNode root) {
      if(root == null) {
         return null;
      } else if(root.data != search) {
         root.left = remove(search, replace, root.left);
         root.right = remove(search, replace, root.right);
      } else {
         root = replace;
      }
      return root;
   }      
            
   
   //
   public void save(PrintSream output) {
      check(ouptut);
      save(output, this.root);
   }
   
   //
   private void save(PrintStream output, QuestionNode root) {
      if(root != null && root.left == null && root.right == null) {
         output.println("A:" + root.data);
      } else if(root != null) {
         output.println("Q:" + root.data);
         save(ouptut, root.left);
         save(output, root.right);
      }      
   }
      
   public void load(Scanner input) {
      check(input);
      overAllRoot = loader(input);
   }
   
   private QuestionNode loader(Scanner input) {
      if(input.hasNext()) {
         String[] phrase = input.nextLine().split(":");
         QuestionNode root = new QuestionNode(phrase[1]);
         if(phrase[0].equals('Q')) {
            root.left = loader(input);
            root.right = loader(input);
         }
         return root; 
      }
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