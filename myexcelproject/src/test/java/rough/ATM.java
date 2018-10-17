package rough;

import java.util.Scanner;

public class ATM {

	public static void main(String[] args) {
		int CorrectPin = 1234; 
		for(int count = 1; count<=3;count++){
			int EnteredPin = 0;
			System.out.println("Enter the PIN");
			Scanner s = new Scanner(System.in);
			EnteredPin = s.nextInt();
			if(count<3 && CorrectPin!=EnteredPin){
				System.out.println("You have entered incorrect PIN. Please try again.");
				continue;
			}
			else if(count==3 && CorrectPin!=EnteredPin){
				System.out.println("Transaction suspended. Please try again later.");
			}
			else{
				System.out.println("Transaction successful. You have entered the correct PIN.");
				break;
			}
		}

	}

}
