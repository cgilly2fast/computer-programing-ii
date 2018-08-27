// Colby Gilbert
// 1/15/2014
// TA: Karandir Singh
// 1362877
// Assignmnt #1
//
// LetterInventory holds a inventory of letters,and can give letter percentages
// (how many a's compared to the rest of the alphabet). user can see who many 
// total letters are stored, can set asny singel letter to a given count,add/
// subtract to differnt LetterInventories
  
public class LetterInventory {
   public static final int NUM_LETTERS = 26;
   public static final int LET_INDEX = 97;   
   private int[] letters;
   private boolean isEmpty; 
   private int count;
   
   // creates an empty inventory when the letter inventory when no parameters 
   // are passed
   public LetterInventory() {
      this.letters = new int[NUM_LETTERS];
      this.isEmpty = true;
      this.count = 0;
   }
   
   // Constructs an inventory (a count) of letters stored in the string data 
   // Case is ignored(a and A) and counted the same. Non-alphabetic charaters 
   // are ingnored. will contruct a empty inventory if string data is null 
   public LetterInventory(String data) {
      this();
      if (data.length() > 0) {
         data = data.toLowerCase();
         for(int i = 0; i < data.length(); i++) {
            if(check(data.charAt(i))){
               this.letters[toIndex(data.charAt(i))]++;
               this.count++;
               this.isEmpty = false;
            }   
         }   
      }   
   }
   
   // Returns the total number of letters stored in this inventory
   public int size() {
      return this.count;
   }
      
   // Returns status of LetterInventory. true if this inventory is empty (all 
   // counts are 0). or false if any letter has a count > 0
   public boolean isEmpty() {
      return this.isEmpty;
   }
      
   // Paramters: letter indicates which letter the user wants(returns) to get 
   // the count for. passed letter can be upper case or lower case. If a 
   // non-alphabetic character is passed, method will throw an 
   // IllegalArgumentException.
   public int get(char letter) {
      checkExpection(letter);   
      return this.letters[toIndex(letter)]; 
   }
   
   // Sets the count for the given letter to the given value.parameter letter's
   // case is ignored. parameter value isndicates what count he user would like 
   // to set passed letter to be. If nonalphabetic character is passed or if 
   // value is negative, your method should throw an IllegalArgumentException.
   public void set(char letter, int value) {
      checkExpection(letter);
      if(value < 0) {
         throw new IllegalArgumentException();
      }
      this.count -= this.letters[toIndex(letter)];
      this.count += value;     
      this.letters[toIndex(letter)] = value;
      this.isEmpty = (count == 0);   
   }
   
   // Returns a String representation of the inventory(lowercase) sorted order, 
   // and surrounded by square brackets. For example, an inventory of 4 a, 
   // 3 b, 1 l, and 1 m would be represented as [aaaabbblm]. if inventory then
   // empty brackets wiil be returned "[]"
   public String toString() {
      String data = "";
      if (!this.isEmpty) {
         for(int i = 0; i < NUM_LETTERS; i++) {
            for(int j = 0; j < letters[i]; j++) {
            data += toChar(i);
            }
         }
      }      
      return "[" + data + "]";   
   }
   
   // Method takes in a LetterInventory, the method will add passed 
   // LetterInventory with called LetterInventory(this) and will construct and 
   // return a new LetterInventory object that stores the counts of the sum of
   // letter inventories. Sate of the two LetterInventory objects being 
   // subtracted (this and other) will be unchanged by this method. 
   public LetterInventory add(LetterInventory other) {
      return addSub(other,1); // 1 (positive to indicate addition.  
   }
   
   // Method takes in a LetterInventory, the method will subtract passed 
   // LetterInventory with called LetterInventory(this) and will construct and 
   // return a new LetterInventory object that stores the counts of the 
   // difference of letter inventories. Sate of the two LetterInventory objects
   // being subtracted (this and other) will be unchanged by this method. If 
   // any resulting count would be negative, will return null. 
   public LetterInventory subtract(LetterInventory other) {
      return addSub(other, -1); // -1 is passed to indicate subtraction.
     
   }
   
   // Returns a double percentage of letters in the inventory 
   // that are parameter letter. If there are no letters in the inventory for
   // that letter, method return 0.0 If a non-alphabetic character is passed, 
   // method will throw an IllegalArgumentException.
   public double getLetterPercentage(char letter) {
      checkExpection(letter);
      if(this.letters[toIndex(letter)] == 0) {
         return 0.0;
      }
      return this.letters[toIndex(letter)]/(double)count;
   }
      
   // checks(ignore case) to see if the character passed is a letter a-z
   // post: returns true if letter a-z false if not               
   private boolean check(char letter) {
      return toIndex(letter)>= 0 && toIndex(letter) < NUM_LETTERS;       
   }
   
   // checks(ignore case) to see if the character passed is a letter a-z
   // post: if test for letter a-z returns true nothing will happen
   // if test reurns false, IllegalArgumentException will be thrown 
   private void checkExpection(char letter) {
      if(!(check(letter))) {
         throw new IllegalArgumentException();
      }
   }
   
   // takes in a desired index for an array and returns the corresponding
   // letter that is represented by such index
   private char toChar(int index) {
      return (char)(index + LET_INDEX);
   }
   
   // takes in a letter that can be lower case or upper case and returns the
   // corresponding index in the array that letter will be.
   // CAUTION: only letters a-z (upper or lower case) should be passed
   // method will still return an answer if condition is not met 
   private int toIndex(char letter) {
      letter = Character.toLowerCase(letter);
      return letter - LET_INDEX;
   }
   
   // Method takes in a LetterInventory and an integer, the method will add or
   // subtracted passed LetterInventory with called LetterInventory(this)
   // CAUTION: only a positive or negative 1 can be passed through as the int
   // this will change the method from an adding the methos to subtracting them 
   private LetterInventory addSub(LetterInventory other, int sign) {
      if(!(sign == 1 || sign == -1)) {
         throw new IllegalArgumentException("int other than 1 or -1 paased");
      }
      LetterInventory added = new LetterInventory();
      for(int i = 0; i < NUM_LETTERS; i++) {
         int value = this.letters[i] + (sign * other.get(toChar(i)));
         if(value < 0) {
            return null;
         }    
         added.set(toChar(i), value);
      }
      return added;  
   }
  
}