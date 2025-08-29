package linkedList;
public class Queue extends LinkedList {
    // Add to the end of the queue
    public void enqueue( String task) {
        insert( task);  // insert adds to the end in your LinkedList
    }
    // Remove from the front of the queue
    public String dequeue() {
        if (head == null) {
            System.out.println("Queue is empty. Cannot dequeue.");
            return null;
        }
        String frontData = head.task;
        head = head.next;  // remove the front node
        return frontData;
    }
    // Peek at the front element without removing it
    public String peek() {
        if (head == null) {
            System.out.println("Queue is empty.");
            return null;
        }
        return head.task;
    }
    // Show all tasks in the queue
    @Override
    public void show() {
        if (head == null) {
            System.out.println("No pending tasks!");
            return;
        }
        Node current = head;
        int position = 1;
        while (current != null) {
            System.out.println(position + ". " + current.task);
            current = current.next;
            position++;
        }
    }
    
    public boolean isEmpty() {
    	return head==null;
    }
    
}