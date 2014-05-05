import java.util.ArrayList;
import java.util.Scanner;


public class Driver {

	public static void main(String[] args)
	{
		Board board = new Board("11 W\nkqbnr\npp.pp\n..p.P\n.....\nPPPPN\nR.BQK");
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
	
	void scan(ArrayList<Move> moves, Square start, int dr, int dc, boolean caputre, boolean single, boolean capture_only)
	{
		
	}
	
	public ArrayList<Move> moves()
	{
		return null;
	}
}
