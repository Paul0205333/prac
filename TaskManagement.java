package linkedList;

import java.util.InputMismatchException;
import java.util.Scanner;

public class TaskManagement {
	
	public static void main(String[] args) {
		
		LinkedList list = new LinkedList();
		Scanner s = new Scanner(System.in);
		int loop = 0;
		
		System.out.println("\tSimple Task Management System");
		
		do {
		System.out.print("Would you add or delete a task?");
		String response = s.next();
		
		s.nextLine();
		
		if(response.equalsIgnoreCase("add")) {
			System.out.print("Task to add:");
			String taskAdded = s.nextLine();
			list.insert(taskAdded);
		}else if(response.equalsIgnoreCase("delete")) {
			System.out.print("Task Accomplished:");
			String taskAccomplished = s.nextLine();
			list.delete(taskAccomplished);
		}else {
			System.out.println("Invalid input. Please try again");
		}	
		
		System.out.println("\nPending Tasks:");
		list.show();
		
		
		//input mismatch ihandle mo
		while(true) {
			System.out.print("\n1. Yes\n2. No\nWould you like to add or delete a task again? ");
			try {
				loop = s.nextInt();
				s.nextLine(); 
				
				if(loop == 1 || loop == 2) {
					break;
				} else {
					System.out.println("Please enter 1 for Yes or 2 for No.");
				}
			} catch(InputMismatchException e) {
				System.out.println("Invalid Input. Please enter a number (1 or 2).");
				s.nextLine(); 
			}
		}
		}while(loop == 1);
		
		s.close();
		System.out.println("\nPending Tasks:");
		list.show();
	}
}
