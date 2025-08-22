package linkedList;

import java.util.InputMismatchException;
import java.util.Scanner;

public class TaskManagement {
	
	public static void main(String[] args) {
		//creates an instance of linkedList.
		LinkedList list = new LinkedList();
		//creates Scanner instance for user input
		Scanner s = new Scanner(System.in);
		// Condition container for the do-while loop
		int loop = 0;
		
		System.out.println("\tSimple Task Management System");
		
		do {
		System.out.print("Would you add or delete a task?");
		String response = s.next();
		
		s.nextLine();
		
		//Checks if the user wants to add or delete a task
		if(response.equalsIgnoreCase("add")) {
			System.out.print("Task to add:");
			//Input the task and insert it in linkedList
			String taskAdded = s.nextLine();
			list.insert(taskAdded);
		}else if(response.equalsIgnoreCase("delete")) {
			System.out.print("Task Accomplished:");
			//Input the task and deletes it in linkedList
			String taskAccomplished = s.nextLine();
			list.delete(taskAccomplished);
		}else {
			// Prints when the user inputs neither "add" nor "delete"
			System.out.println("Invalid input. Please try again");
		}	
		//Shows Pending tasks
		System.out.println("\nPending Tasks:");
		list.show();
		
		// Loop to ensure that the user input is valid
		while(true) {
			System.out.print("\n1. Yes\n2. No\nWould you like to add or delete a task again? ");
			
			// Handles InputMismatch when user enters a non-integer
			try {
				loop = s.nextInt();
				s.nextLine(); 
				
				if(loop == 1 || loop == 2) {
					// Exits this while loop when the input is valid
					break;
				} else {
					// Prints when the user enters an invalid integer (e.g., 3, 4, 5)
					System.out.println("Please enter 1 for Yes or 2 for No.");
				}
			} catch(InputMismatchException e) {
				System.out.println("Invalid Input. Please enter a number (1 or 2).");
				s.nextLine(); 
			}
		}
		
		// Loops while loop == 1, otherwise exits the do-while loop
		}while(loop == 1);
		
		s.close();
		//Prints pending tasks one last time
		System.out.println("\nPending Tasks:");
		list.show();
	}
}
