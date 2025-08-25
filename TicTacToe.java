package ttToe;

import java.util.*;


public class TicTacToe {
	
	static String[] board;
	static String turn;
	static String winner;
	
	
	static void printBoard() {
		System.out.println();
		System.out.println("|---|---|---|");
	    System.out.println("| " + board[0] + " | " + board[1] + " | " + board[2] + " |");
	    System.out.println("|-----------|");
	    System.out.println("| " + board[3] + " | " + board[4] + " | " + board[5] + " |");
	    System.out.println("|-----------|");
	    System.out.println("| " + board[6] + " | " + board[7] + " | " + board[8] + " |");
	    System.out.println("|---|---|---|");
	}
	
	
	static void input() {
		System.out.print("Player " + turn +"'s turn. Input where you want to put " + turn + ": ");
	}
	
	static String checkWinner() {
		
		String pattern = null;
		
		for(int i=0;i<8;i++) {
			
			switch(i) {
				case 0:
					pattern = board[0] + board[1] + board[2];
					break;
				case 1:
					pattern = board[3] + board[4] + board[5];
					break;
				case 2:
					pattern = board[6] + board[7] + board[8];
					break;
				case 3:
					pattern = board[0] + board[3] + board[6];
					break;
				case 4:
					pattern = board[1] + board[4] + board[7];
					break;
				case 5:
					pattern = board[2] + board[5] + board[8];
					break;
				case 6:
					pattern = board[0] + board[4] + board[8];
					break;
				case 7:
					pattern = board[2] + board[4] + board[6];
					break;
			}	
			
			if(pattern.equals("XXX")) {
				winner = "X";	
			}else if(pattern.equals("OOO")) {
				winner = "O";
			}
		}
		
		return winner;
	}
	
	public static void main(String[] args) {
		
		
		Scanner s = new Scanner(System.in);
		board = new String[9];
		turn = "X";
		int placeInput;
		int turnNum = 0;
		 
		for(int a = 0; a<9; a++) {
			board[a] = String.valueOf(a+1);
		 }
	
		System.out.println("Welcome to a Simple TicTacToe Game");
		printBoard();
		System.out.print("X will play first. Input where you want to put X: ");
		
		while(winner == null) {
			try {
				placeInput = s.nextInt();
				
				if(board[placeInput-1].equals(String.valueOf(placeInput))) {
					board[placeInput-1] = turn;
					turnNum++;
					printBoard();
					turn = turn.equals("X")? "O": "X";
					
					if(turnNum == 10) {
						System.out.println("It's a draw! Thank you for playing.");
					}else if(turnNum>=5) {
						checkWinner();
					}
					input();
				}else {
					System.out.print("Space occupied. Re-enter Slot Number: ");
					continue;
				}

				
			}catch(InputMismatchException e) {
				System.out.print("Input must be an Integer. Re-enter Slot Number: ");
				s.nextLine();
			}	
		}
		s.close();
		System.out.println();
		System.out.println("The Winner: Player " + winner);
	}
}

