import java.util.Scanner;


public class Driver {

	public static void main(String[] args)
	{
		Board board = new Board();
		System.out.println(board.toString());
		System.out.println();
		
		String move;
		Scanner scanIn = new Scanner(System.in);
		do {
			System.out.println("Next move:");
			move = scanIn.nextLine();
			board.move(new Move(move));
			System.out.println();
			System.out.println(board.toString());
			System.out.println();
		} while (scanIn.hasNextLine());
		scanIn.close();
	}
}
