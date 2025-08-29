package linkedList;
public class TaskManagementClass {
    public static void main(String[] args) {
        
    	Queue prioTask = new Queue();
    	Stack normTask = new Stack();
    	
    	System.out.println("Priority Tasks:");//This is implemented through Queue
    	prioTask.enqueue( "Do DSA Assignment");
    	prioTask.enqueue("Review for Midterms");
    	prioTask.enqueue("Review for surprise Quiz tomorrow");
    	prioTask.show();
    	System.out.println("\nMost Urgent Task: "+prioTask.peek());
    	System.out.println();
    	System.out.println("Tasks:");//This is implemented through Stack
    	normTask.push( "Study Online Courses");
    	normTask.push( "Wash the dishes");
    	normTask.push("Skincare");
    	normTask.show();
    	System.out.println("\nFirst Task to do: "+ normTask.peek());
    	
    	
    	System.out.println("\n---------After doing an Urgent Task--------------");
    	System.out.println("Accomplised Priority Task: "+ prioTask.dequeue());
    	System.out.println("\nPriority Tasks:");
    	prioTask.show();
    	System.out.println("\nMost Urgent Task: "+prioTask.peek());
    	
    	System.out.println("\n---------After doing a Task--------------");
    	System.out.println("Accomplished Task: " + normTask.pop());
    	System.out.println("\nTasks:");
    	normTask.show();
    	System.out.println("\nFirst Task to do: "+normTask.peek());
    	
    	System.out.println("\n---------Printing all Tasks and check if Empty--------------");
    	System.out.println("Priority Tasks:");
    	prioTask.show();
    	System.out.println("Priority Tasks is Empty: "+ prioTask.isEmpty());
    	System.out.println();
    	System.out.println("Tasks:");
    	normTask.show();
    	System.out.println("Tasks is Empty: " + normTask.isEmpty());	
    }
}
