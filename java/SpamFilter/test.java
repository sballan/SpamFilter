package spam;

import java.util.Scanner;

public class test {
	public static void main(String[] args) {
		Scanner scan = new Scanner(System.in);
		while(true) {
			System.out.print("Enter message:");
			Message m = new Message(scan.nextLine().replace("\n", ""));
			MessageReader mr = new MessageReader();
			mr.setMessage(m);
			System.out.println(mr.isSpam());
		}
	}
}
