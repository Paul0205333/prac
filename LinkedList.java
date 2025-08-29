package linkedList;

public class LinkedList {
	Node head;
	
	// Inserts a new node at the end of the LinkedList
	public void insert( String task) {
		Node node = new Node( task);
		
		if(head==null) {
			// If list is empty, set new node as head
			head = node;
		} else {
			 // Traverse to the last node
			Node n = head;
			while(n.next!=null) {
				n = n.next;
			}
			// Link the new node at the end
			n.next = node;
		}
	}	
	
	// Displays all values in the LinkedList
	public void show() {
		Node node = head;
		
		if (node == null) {
			// If the list is empty, notify and exit
	        System.out.println("No tasks available.");
	        return;
	    }
		// Traverse through nodes until the last one
		while(node.next!=null) {
			System.out.println(node.task);
			node= node.next;
		}
		// Print the last node’s data
		// Without this, the last node’s data won’t be displayed
		System.out.println(node.task);
	}
	
	// Deletes a node from the LinkedList by matching its data
	public void delete(String data) {
	    if (head == null) {
	    	// If list is empty, nothing to delete
	        System.out.println("List is empty. Cannot delete.");
	        //exits the whole method
	        return;
	    }

	    // trim spaces and collapse multiple spaces between words into one
	    String normalizedData = data.trim().replaceAll("\\s+", " ");

	    // If the head node matches the input, delete it
	    if (head.task.trim().replaceAll("\\s+", " ").equalsIgnoreCase(normalizedData)) {
	        head = head.next; 
	        //exits the whole method
	        return;
	    }

	    Node current = head;
	    Node previous = null;
	    
	    // Traverse list until a matching node is found
	    while (current != null && !current.task.trim().replaceAll("\\s+", " ").equalsIgnoreCase(normalizedData)) {
	        previous = current;
	        current = current.next;
	    }
	    
	    //If done traversing and no match is found
	    if (current == null) {
	        System.out.println("The task: '" + data + "' not found.");
	        //exits the whole method
	        return;
	    }
	    
	    // Remove the node by skipping it in the chain
	    previous.next = current.next;
	}

 
}
