// Colby Gilbert
// 1/28/2014
// TA: Karandir Singh
// 1362877
// Assignmnt #4

// DESCRIPTION:   methods that takes in a BNF grammar scheme and gives 
//                functionailty to rules. user can print out the set of rules
//                i.e. non terminals (getSymbols), see is a term is a terminal
//                non-termianl in grammar (conatains), and genreate a sentence
//                with passed grammar (genreate)  

import java.util.*;

public class GrammarSolver {
   private Map<String, HashSet<String>> grammar;
   
   // PRE:  must have a list of rules passed to contructor, throws
   //       IllegalArgumentException if list isEmpty, has a size of 0, or
   //       Multiple definations of grammar rules, white space inbetween 
   //       teriminals and non-terimals will be romved.
   // POST: Intailizes GrammarSolver   
   public GrammarSolver (List<String> rules) {
      if(rules.isEmpty() || rules.size() == 0) {
         throw new IllegalArgumentException();
      }   
      this.grammar =  new HashMap<String, HashSet<String>>();
      for(String line : rules) {
         // split non-terminal form terminals
         String[] rule = line.trim().split("::="); 
         if (this.grammar.containsKey(rule[0])) {
            throw new IllegalArgumentException("Multiple definations of grammar rules");
         }
         // split each terminal up into individual terminals
         String[] terminals = rule[1].trim().split("[|]");
         this.grammar.put(rule[0].trim(), new HashSet<String>());
         // assign each terminal to it corresponding non-terminal 
         for(String term : terminals) {
            this.grammar.get(rule[0]).add(term.trim());
         }
      }       
   }
   
   // PRE:  String (symbol) parameter of desired search, throws 
   //       IllegalArgumentException if string is null or size 0.
   // POST: returns true if symbol is non-terminal in the grammar and false for 
   //       terminals.
   public boolean contains(String symbol) {
      this.check(symbol);
      return this.grammar.containsKey(symbol);
   }
   
   // PRE:  GrammarSolver must be intialized.
   // POST: returns string of all non-terminals if format of ["non-t", "non-t",
   //       ...,"non-t"]
   public Set<String> getSymbols() {
      return this.grammar.keySet();
   }
   
   // PRE:  GrammarSolver must be intialized.
   // POST: returns sentence from grammar with no white space on front or end 
   public String generate(String symbol) {
      // trims white space of sentence
      return this.makeSentence(symbol).trim();
   }
   
   // PRE:  will only be called when user calls generate method. throws 
   //       IllegalArgumentException if string is null or size 0.
   // POST: returns sentence following rules of grammar
   private String makeSentence(String symbol) {
      this.check(symbol);
      Random rand = new Random();
      String result = "";
      // check if symbol is terinmal or non-terminal  
      if(this.grammar.containsKey(symbol)){
        // creates random number within 0 and number of terimals assigned to 
        // terminal
        int randIndex = rand.nextInt(grammar.get(symbol).size());
        // creates array of all terminals assigned to non-terminal
        String[] terms = this.grammar.get(symbol).toArray(new String[0]);
        // splits called terminal up to remove white space
        String[] split = terms[randIndex].split("[ \t]+");
        // adds each token of terminals to result
        for(String rule : split) {
           result += this.makeSentence(rule);
        }   
      } else {
        result += symbol + " ";
      }   
      return result;  
   }
   
   // PRE:  Paramter of type string must be passed
   // POST: throws IllegalArgumentException if string is null or size 0.
   private static void check(String symbol) {
      if(symbol == null || symbol.length() == 0) {
        throw new IllegalArgumentException();
      }          
   }
}           