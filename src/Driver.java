import java.util.Scanner;


public class Driver {

	public static void main(String[] args)
	{
		Board board = new Board();
		System.out.println(board.toHumanReadableString());
		System.out.println();
		System.out.println("Next move:");
		
		String move;
		Scanner scanIn = new Scanner(System.in);
		do {
			move = scanIn.nextLine();
			board.move(new Move(move));
			System.out.println();
			System.out.println(board.toHumanReadableString());
			System.out.println();
			System.out.println("Next move:");
		} while (scanIn.hasNextLine());
		scanIn.close();
	}
}
