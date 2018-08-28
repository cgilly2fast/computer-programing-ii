import java.util.*;

public class AssassanTest {
   public static void main(String args[]){
      ArrayList<String> ogList = new ArrayList<String>();
      ogList.add("Colby");
      ogList.add("Keith");
      ogList.add("Derby");
      ogList.add("AJ");
      ogList.add("Megan");
      ogList.add("Anna"); 
      ArrayList<String> list = new ArrayList<String>();
      ArrayList<String> list1;
   
      //testConstructor(ogList);
      //testConstructor(list);
      //testConstructor(list1);
      testPrintKillRing();
      testPrintGraveyard();
      testKillRingContains();
      testIsGameOver();
      testWinner();
      testKill();
   }
   
   public static String testConstructor(ArrayList<String> list) {
      if(list == null || list.size() = 0) {
         
      }
      AssassinManager test1 = new AssassinManager(list);
      
}         