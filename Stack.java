package linkedList;
public class Stack extends LinkedList {
    // Push adds to the front (top) of the stack
    public void push( String task) {
        Node newNode = new Node(task);
        newNode.next = head;
        head = newNode;
    }
    // Pop removes from the front (top) of the stack
    public String pop() {
        if (head == null) {
            System.out.println("Stack is empty. Nothing to pop.");
            return null;
        }
        String topData = head.task;
        head = head.next;
        return topData;
    }
    // Peek at the top element without removing it
    public String peek() {
        if (head == null) {
            System.out.println("Stack is empty.");
            return null;
        } 
        return head.task;
        
    }
    // Override show to display stack elements from top to bottom
    @Override
    public void show() {
        if (head == null) {
            System.out.println("Stack is empty.");
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