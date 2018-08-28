// Colby Gilbert
// 1/22/2014
// TA: Karandir Singh
// 1362877
// Assignmnt #2

// DESCRIPTION:   this class creates a melody that can be played (play()), can
//                view length in seconds(getLength()), be viewed as a string 
//                (toString()). class has some editing abilties, reverse() to 
//                reverse order of notes in melody, append() to add another 
//                melody to the end of this melody,and changeTemppo() to change 
//                tempo of all notes by a percent

import java.util.*;

public class Melody {
   private Queue<Note> notes;
   private double length;   
   
   // POST: Constructs and intializes a class melody with a queue(parameter) 
   //       that has stored notes, a queue must be passed or object will 
   //       intialize to null. 
   public Melody(Queue<Note> song) {
      notes = new LinkedList<Note>();
      int factor = 1;
      int count = 0;
      
      while(!song.isEmpty()) {
         Note temp = song.remove();
         if(temp.isRepeat()) { // if note isRepeat count notes length as double
                               // intill next isRepeat
            count++;           
            factor = count % 2 + 1;// acts as switch between 1 & 2  
         }  
         length += factor * temp.getDuration();
         this.notes.add(temp);
      }   
   }
   
   // RETURNS: the length of a song in seconds rounded to the first decimel 
   //          place. example: 2.699999999 will be rounded to 2.7
   public double getLength() {
         return (double)Math.round(length*100) / 100.0;
   
   }
   
   // PRE:  file containing song that was passed in to contructor should have
   //       notes in that file. if not method will return a string of ""
   // POST: a string will be returned, if no note in song then a empty string
   //       will be returned. if song has notes, each will be printed in its
   //       own line in the format "<duration> <pitch> <repeat>" if the note 
   //       is a rest,if a standard note a string in the format: "<duration> 
   //       <pitch> <octave> <accidental> <repeat> For example "0.2 C 4 
   //       NATURAL false true" and ".5 R false".
   public String toString() {
      String result = "";
      for(int i = 0; i < this.notes.size(); i++) {
         result += (this.next().toString() + "\n");
      }   
      return result;
   
   }
   
   // PRE:  this method changeTempo accpets a paaramter that is a double that
   //       changes the tempo by the percent the parameter represets. for 
   //       example, if 2.0 is passed through the tempo of each note will be 
   //       double. if 0.5 is passsed the the tempo will be halfed.   
   public void changeTempo(double tempo) {
      for(int i = 0; i < this.notes.size(); i++) {
         Note temp = notes.remove();
         temp.setDuration(temp.getDuration() * tempo);
         this.notes.add(temp);
      }
      this.length *= tempo;
   }
   
   // POST: Reverses the prder fod notes in a song. for example is A, G, D, C are
   //       stored as a song then it will be changed to C, D, G, A.
   public void reverse() {
      Stack<Note> reverse = new Stack<Note>();
      while(!this.notes.isEmpty()) {
         reverse.push(this.notes.remove());
      }
      while(!reverse.isEmpty()) {
         this.notes.add(reverse.pop());
      }       
   
   }
   // POST: adds the notes of Melody other(paramter) to this melody. Example: 
   //       this song is A, G, D, C and other is F, D, B. then this melody 
   //       will becomeA, G, D, C, F, D, B. state of other will remained 
   //       unchanged. 
   public void append(Melody other) {
      for(int i = 0; i < other.notes.size(); i++) {
         this.notes.add(other.next());
      }   
   }
   // POST: plays notes in this melody with mp3 like functionality (audio 
   //       thorugh the speakers) 
   public void play() {
      boolean check = false;
      Queue<Note> repeat= new LinkedList<Note>();
      
      for(int i = 0; i < notes.size(); i++) {
         Note play = this.next();
          
         if(play.isRepeat()) {
            check = (!check && play.isRepeat());
         }
         if(check) {
            repeat.add(play);
         }
         play.play();
         if(!check && play.isRepeat()) {// program will only proceed past unless the STOP boolean
            repeat.add(play);           // has appeared   
            while(!repeat.isEmpty()) {  // plays queue of repated notes
               Note temp = repeat.remove();
               temp.play();
                  
            }
         }  
      }
   }   
   // RETURNS: helper that returns the first note in queue and then move said 
   //          note to back of the queue, any modifications made to returned 
   //          note will not be reflect in this melody's notes.  
   private Note next() {
      Note value = this.notes.peek();
      this.notes.add(this.notes.remove());
      return value;
   }      
}   