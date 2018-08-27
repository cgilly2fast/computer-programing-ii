// Colby Gilbert
// 2/15/2014
// TA: Karandir Singh
// 1362877
// Assignmnt #5

// DESCRIPTION: Manages a a game of "evil" hangman, where the word  will change
//              after each letter guess, method that can be call are words(), 
//              will return the set of words that the game is considering to
//              be an option for the guess, guessLeft() returns the number of 
//              guesses remaining, guesses() returns string of letters guessed,
//              pattern() returns a string that represnts the correct letters 
//              guessed and thier placementin the word, record() accepts the 
//              guess of the user. 

import java.util.*;

public class HangmanManager {
   private int length;
   private int max;
   private Set<String> words;
   private SortedSet<Character> guessed;
   private String pattern;
   
   // PRE:  paramter list of words passed is non-empty and is all lower case, 
   //       length must be >= 1, and max must be > 0 or 
   //       IllegallArgumentException will be thrown
   // POST: Intializes manager and feilds to preform methods below
   public HangmanManager(List<String> dictionary, int length, int max) {
      if(length < 1 || max < 0) {
          throw new IllegalArgumentException("length or max out of bounds");
      }    
      this.length = length;
      this.max = max;
      this.words = new TreeSet<String>();
      this.guessed = new TreeSet<Character>();
      for(String element : dictionary) {
         if(element.length() == length) {
            this.words.add(element);
         }
      }
      this.pattern = "-";
      for(int i = 0; i < length - 1; i++) {
         this.pattern += " -" ;
      }            
      
   }
   
   //POST:  will return the set of words that the game is considering to
   //       be an option for the guess
   public Set<String> words() {
      return this.words;
   }
   
   //POST:  returns the number of guesses remaining
   public int guessesLeft() {
      return max;
      
   }
   
   //POST:  returns string of letters guessed
   public SortedSet<Character> guesses() {
      return this.guessed;
   }
   
   //PRE:  if word set is empty throws IllegalStateException. 
   //POST: returns a string that represnts the correct letters guessed and 
   //       thier placement in the word.
   public String pattern() {
      setCheck();    
      return this.pattern;
   }
   
   // PRE:  Paramater guess(char) is the guess of the user, if guess left is
   //       less than 1 or list of words is empty throws IllegalStateException
   //       wil be thrown. if parameter guess has already been guessed throws
   //       IllegallArgumentException.
   // POST: adjusts set of possible words to to exclude guess, then returns 
   //       number of guesses remaining.      
   public int record(char guess) {
      setCheck();
      if(this.length < 1) {
        throw new IllegalStateException();
      }
      if(this.guessed.contains(guess)) {
         throw new IllegalArgumentException();
      }
      // update what has been gueesed
      guessed.add(guess);
      // to store words to each pattern
      Map<String, Set<String>> shorted = new TreeMap<String, Set<String>>();
      // to store number of occurance of guess for each pattern
      Map<String, Integer> counts = new TreeMap<String, Integer>();
      
      makeSets(guess, shorted, counts);
      String stored = chooseSet(shorted);
      // updates number of guesses
      this.words = shorted.get(stored);      
      if(stored.equals(this.pattern)) {
         this.max--;
      }
      //updates pattern
      this.pattern = stored;
      return counts.get(this.pattern);        
   }
   
   //POST: Checks if the set of words is empty throws IllegalStateException
   private void setCheck() {
      if(this.words.isEmpty()) {
         throw new IllegalStateException();
      }
   }
   
   // PRE:  record() was called, and passed the guess, a set that will store word
   //       patterns to words, and a set that will store patterns to letter counts
   // POST: populates sets with values   
   private void makeSets(char guess, Map<String, Set<String>> shorted, 
                         Map<String, Integer> counts) {
      // chooses a word
      for(String element : this.words) {
         int count = 0;
         // converts strings into arrays
         char[] update = this.pattern.toCharArray();
         char[] letters = element.toCharArray();
         for(int i = 0; i < letters.length; i++) {
            // compares each letter to chossen word
            if(letters[i] == guess) {
               // updates pattern dashes "-" to guess
               update[2 * i] = guess;
               count++;
            }
         }
         // converts new pattern array back to string
         String key = new String(update);
         // places patterns to counts and word    
         if(!shorted.containsKey(key)) {
            shorted.put(key, new TreeSet<String>());
            counts.put(key, count);
         }
         shorted.get(key).add(element);
      }
   }
   
   // PRE: record() was called, and passed in set of patterns to words
   // POST: returns set with the most word options
   private String chooseSet(Map<String, Set<String>> shorted) {
      Iterator<String> keys = shorted.keySet().iterator();
      String stored = keys.next();           
      while(keys.hasNext()) {
         String next = keys.next();
         if(shorted.get(stored).size() < shorted.get(next).size()) {
            stored = next;
         }
      }
      return stored;
   }          
}             