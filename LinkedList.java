package linkedList;

public class LinkedList {
	Node head;
	
	public void insert(String data) {
		Node node = new Node(data);
		
		if(head==null) {
			head = node;
		} else {
			Node n = head;
			while(n.next!=null) {
				n = n.next;
			}
			n.next = node;
		}
	}
	
	public void show() {
		Node node = head;
		
		if (node == null) {
	        System.out.println("No tasks available.");
	        return;
	    }
		
		while(node.next!=null) {
			System.out.println(node.data);
			node= node.next;
		}
		System.out.println(node.data);
	}
	
	public void delete(String data) {
	    if (head == null) {
	        System.out.println("List is empty. Cannot delete.");
	        return;
	    }

	    if (head.data.equals(data)) {
	        head = head.next; 
	        return;
	    }

	    Node current = head;
	    Node previous = null;

	    while (current != null && !current.data.equals(data)) {
	        previous = current;
	        current = current.next;
	    }

	    if (current == null) {
	        System.out.println("Node with data '" + data + "' not found.");
	        return;
	    }

	    previous.next = current.next;
	}

 
}
