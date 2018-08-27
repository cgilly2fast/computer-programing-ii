// Colby Gilbert
// 3/04/2015
// TA: Karandir Singh
// 1362877
// Assignmnt #7

// DESCRIPTION: used for a game of 20 question(binary tree)

public class QuestionNode {
   public String data;
   public QuestionNode left;
   public QuestionNode right;
   
   // PRE:  String data being ether a question or answer can be non null and
   //       still function
   // POST: Intializes a QuestionNode
   public QuestionNode(String data) {
      this(data, null, null);
   }
   
   // PRE:  String data being ether a question or answer, left and right can be
   //       null or another QuestionNode
   // POST: Intializes a QuestionNode
   public QuestionNode(String data, QuestionNode left, QuestionNode right) {
      this.data = data;
      this.left = left;
      this.right = right;
   }
}         