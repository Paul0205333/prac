package linkedList;

public class Node {
	String task;
	String priority;
	Node next; 
	
	// Constructor that assigns values to each Node object
	 public Node(String task) {
	       this.task = task;
	       this.next = null; 
	   }
}
