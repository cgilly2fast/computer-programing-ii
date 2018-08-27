// Colby Gilbert
// 1/28/2014
// TA: Karandir Singh
// 1362877
// Assignmnt #3

// DESCRIPTION:   program manages a gae of assaain user can see who is in kill 
//                ring and who has been killed (printKillRing()) (printGaveyard()),
//                check if a specific person is still in the kill ring or has 
//                been killed (killRingContains()) (graveyardContains()), check if
//                game id still going (isGameOver()), print out winner (winner()), 
//                and remove s pecifi name form the kill ring and ass them to the 
//                graveyard (kill())
 
import java.util.*;

public class AssassinManager {
   private AssassinNode front;
   private AssassinNode frontGrave;
   
   //PRE:   Assumes parameter names has non-empty "names", non-null strings 
   //       and that there are no duplicates.
   //POST:  Throws a IllegalArgumentException if list is null or has size <= 0
   //       otherwise creates a new list of AssassinNodes
   public AssassinManager(ArrayList<String> names) {
      if (names.size() <= 0 || names == null) {
         throw new IllegalArgumentException();
      }
      for(int i = names.size() - 1; i >= 0; i--) {
         this.front = new AssassinNode(names.get(i), this.front);
      }
   }
   
   //POST:  prints out still alive players in format "Name is stalking Name" 
   //       each pair on its own line
   public void printKillRing() {
      AssassinNode current = this.front;
      while(current != null){
         if(current.next == null){
            System.out.println("  " + current.name + " is stalking " + this.front.name);
            current = current.next;
         } else {   
            System.out.println("  " + current.name + " is stalking " + current.next.name);
            current = current.next;
         }     
      }
   }
   
   // POST: prints out all players that have been killed in format "Name was 
   //       killed by Name", each pair wil be on its own line
   public void printGraveyard() {
      AssassinNode current = this.frontGrave;
      while(current != null) {
         System.out.println("  " + current.name + " was killed by " + current.killer);
         current = current.next;
      }      
   }
   
   // PRE:  parameter name will be searched to see if still alive
   // POST: boolean will be returned if name is still alive
   public boolean killRingContains(String name) {
      AssassinNode current = this.front;
      return contains(name, current);      
   }
   
   // PRE: parameter name will be searched to see if killed
   // POST: boolean will be returned if name has been killed
   public boolean graveyardContains(String name) {
      AssassinNode current = this.frontGrave;
      return contains(name, current);    
   }
   
   // POST: returns boolean if game has been won(true) or is still being palyed
   //       (false)
   public boolean isGameOver() {
      return (this.front.next == null);
      
   }
   
   // POST: returns a string of the person who has won the game, returns null
   //       if game is still being played
   public String winner() {
      if(this.isGameOver()) {
         return this.front.name;
      }
      return null;   
   }
   
   // PRE:  String name must be non-null if game is over then a 
   //       IllegalStateException, if person is not in kill ring the
   //       IllegalArguementExpection will be thrown.
   // POST: will remove person (name) from kill ring and add them to the 
   //       graveyard  
   public void kill(String name) {
      if(isGameOver())  {
         throw new IllegalStateException("Game is over");
      } 
      
      boolean notKilled = true;
      AssassinNode current = this.front;
      AssassinNode last = this.front;
      AssassinNode graveCurrent = this.frontGrave;
         
      if(current.name.equalsIgnoreCase(name)) {
            while(last.next != null) {
               last = last.next;
            }
            current.killer = last.name;
            this.frontGrave = current;
            this.front = current.next; 
            this.frontGrave.next = graveCurrent;
            notKilled = false; 
      } else {
         while(notKilled && current != null && current.next != null) {
            if(current.next.name.equalsIgnoreCase(name)) {
               current.next.killer = current.name;
               this.frontGrave = current.next;
               current.next = current.next.next; 
               this.frontGrave.next = graveCurrent;
               notKilled = false; 
            }
            current = current.next;
         }
      }       
      if(notKilled) {
         throw new IllegalArgumentException("Person Not in kill ring");
      }   
   }
   
   // PRE:  Parameter name(to be searched person) and current (what linkedlist
   //       to look for name), name may not be null, current may be null
   // POST: returns a boolena if name is(true) or isnt(false) in passed list.  
   private boolean contains(String name, AssassinNode current) {
      while(current != null) {
         if(current.name.equalsIgnoreCase(name)) {
            return true;
         }
         current = current.next;
      }
      return false; 
   }                
}         