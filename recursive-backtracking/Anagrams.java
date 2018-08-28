// Colby Gilbert
// 2/23/2014
// TA: Karandir Singh
// 1362877
// Assignmnt #6

// DESCRIPTION: Creates all possible anagrams of given length, and passed
//              passed dictionary. Anagarams() intializes object for use of 
//              methods, getWords() obtans all word in dictionary that could 
//              possibly used for an anagram of the passed phrase. print() 
//              prints all anagrams out that are possible for passed phrase
//              print() a secondary method that also takes in a max words 
//              parameter, so anagrams with words <= max are printed out.

import java.util.*;

public class Anagrams {
   private Set<String> allPossible;
   
   // PRE:  must pass in a collection of words, if passed dictionary is null 
   //       throws IllegalArgumentException 
   //POST:  Intializes an Anagrams object, does not modify passed object.
   public Anagrams(Set<String> dictionary) {
      if(dictionary == null) {
         throw new IllegalArgumentException("passed set is null");
      }
      this.allPossible = new TreeSet<String>();
      this.allPossible.addAll(dictionary);  // adds all words to object
   }
   
   // PRE:  must pass a string to be searched, if passed string is null 
   //       throws IllegalArgumentException
   // POST: returns a set of all the possible words spelt with the letters
   //       of the passed string
   public Set<String> getWords(String phrase) {
      if(phrase == null) {
         throw new IllegalArgumentException("passed phrase is null");
      }
      Set<String> possible = new TreeSet<String>();   
      LetterInventory word = new LetterInventory(phrase);
      
      for(String check : this.allPossible) {
         LetterInventory other = new LetterInventory(check);
         // if the passed phrase subtract other is >= 0, then word can be 
         // spelled by phrase
         if(word.subtract(other) != null) {
            possible.add(check);
         }
      }
      return possible;
   }         
   
   // PRE:  must pass a string to be searched, if passed string is null 
   //       throws IllegalArgumentException
   // POST: prints all anagrams possible in the given phrase, if phrase
   //       is empty no output will be produced.
   public void print(String phrase) {
      print(phrase, 0);
   }
   
   // PRE:  must pass a string to be searched, and a max word length of 
   //       anagram, if max is <0 or if passed string is null throws 
   //       IllegalArgumentException
   // POST: prints all anagrams possible within the max length, if max is
   //       zero all possible anagrams will be printed out, if phrase
   //       is empty no output will be produced.
   public void print(String phrase, int max) {
      if(phrase == null || max < 0) {
         throw new IllegalArgumentException();
      }
      
      Set<String> options = this.getWords(phrase);
      ArrayList<String> anagram = new ArrayList<String>();
      
      if(max == 0) {
         max = allPossible.size(); // any really big number
      }       
      printer(phrase, max, options, anagram);        
   }
   
   // PRE:  must pass a string to be searched, a max word length of 
   //       anagram, a set of options for anagram, and arraylist
   // POST: prints all anagrams possible within the max length, if max is
   //       zero all possible anagrams will be printed out, if phrase
   //       is empty no output will be produced.
   private void printer(String phrase, int max, Set<String> options, 
                        ArrayList<String> anagram) {
      
      LetterInventory toChoose = new LetterInventory(phrase);
      // if toChoose.isEmpty that means all letters have been used and an 
      // anagram has been found
      if(toChoose.isEmpty()) {
         System.out.println(anagram);           
      } else if(anagram.size() < max) {   
         for(String chosen : options) { // traverse all options 
            LetterInventory word = new LetterInventory(chosen);
            LetterInventory dif = toChoose.subtract(word);
            // compares word with possible letters to be used 
            if(dif != null) {
               anagram.add(chosen); // choose
               printer(dif.toString(), max, options, anagram); //explore next option
               anagram.remove(chosen); // unchoose: move to set of options
            }
         }
      }            
   }     
}              