import java.util.Scanner;

public class Driver
{
	public static void main(String[] args)
	{
		Board board = new Board("39 W\nkqbnr\npp.pp\n..p.P\n.....\nPPPPN\nR.BQK");
		System.out.println(board.toHumanReadableString());
		System.out.println();
		System.out.println(board.printValidMovesForNextTurn());
		System.out.println("Next move:");

		String move;
		char winCondition;
		Scanner scanIn = new Scanner(System.in);
		do {
			move = scanIn.nextLine();
			winCondition = board.checkedMove(new Move(move));
			System.out.println();
			System.out.println(board.toHumanReadableString());
			System.out.println();
			if (winCondition == '=') {	// tie
				System.out.println("Tie! Nobody wins.");
				break;
			}
			else if (winCondition == 'W') {	// white wins
				System.out.println("White wins!");
				break;
			}
			else if (winCondition == 'B') {	// black wins
				System.out.println("Black wins!");
				break;
			}
			else if (winCondition == '?') {	// nobody won yet
				System.out.println(board.printValidMovesForNextTurn());
				System.out.println("Next move:");
			}
			else {	// invalid win condition
				scanIn.close();
				throw new Error("Invalid win condition returned by move()");
			}
		}
		while (scanIn.hasNextLine());
		scanIn.close();
	}
}
