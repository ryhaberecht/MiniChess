import java.util.Scanner;

public class Driver
{
	public static void main(String[] args)
	{
		// determine type of game
		System.out.println("Which kind of game? (human-human/human-ai/ai-ai/ai-telnet):");
		Scanner scanIn = new Scanner(System.in);
		String gameType = scanIn.nextLine().trim();
		
		// initialize varaibles
		Board board = new Board("11 W\nkqbnr\npp.pp\n..p.P\n.....\nPPPPN\nR.BQK");
		String move;
		char winCondition;
		
		if ("human-human".matches(gameType)) {	// human on human
			
			System.out.println(board.toHumanReadableString());
			System.out.println();
			System.out.println(board.printValidMovesForNextTurn());
			System.out.println("Next move:");
			
			do {
				move = scanIn.nextLine();
				winCondition = board.checkedMove(new Move(move));
				System.out.println();
				System.out.println(board.toHumanReadableString());
				System.out.println();
				if (winCondition == '=') { // tie
					System.out.println("Tie! Nobody wins.");
					break;
				}
				else if (winCondition == 'W') { // white wins
					System.out.println("White wins!");
					break;
				}
				else if (winCondition == 'B') { // black wins
					System.out.println("Black wins!");
					break;
				}
				else if (winCondition == '?') { // nobody won yet
					System.out.println(board.printValidMovesForNextTurn());
					System.out.println("Next move:");
				}
				else { // invalid win condition
					scanIn.close();
					throw new Error("Invalid win condition returned by move()");
				}
			}
			while (scanIn.hasNextLine());
			scanIn.close();
		}
		else if ("ai-ai".matches(gameType)) {	// ai on ai
			
			System.out.println(board.toHumanReadableString());
			System.out.println();
			System.out.println(board.printValidMovesForNextTurn());
			System.out.println("Next move:");

			do {
				winCondition = board.move(getAiMove(board));
				System.out.println();
				System.out.println(board.toHumanReadableString());
				System.out.println();
				if (winCondition == '=') { // tie
					System.out.println("Tie! Nobody wins.");
					break;
				}
				else if (winCondition == 'W') { // white wins
					System.out.println("White wins!");
					break;
				}
				else if (winCondition == 'B') { // black wins
					System.out.println("Black wins!");
					break;
				}
				else if (winCondition == '?') { // nobody won yet
					System.out.println(board.printValidMovesForNextTurn());
					System.out.println("Next move:");
				}
				else { // invalid win condition
					scanIn.close();
					throw new Error("Invalid win condition returned by move()");
				}
			}
			while (true);
		}
		else if ("human-ai".matches(gameType)) {	// human on ai
			
			System.out.println(board.toHumanReadableString());
			System.out.println();
			System.out.println(board.printValidMovesForNextTurn());
			System.out.println("Next move:");

			System.out.println("Choose your color: ");
			String humanColor = scanIn.nextLine();
			char humanColorChar = humanColor.trim().charAt(0);
			do {
				// is it human's or ai's turn?
				if (humanColorChar == board.onMove) {	// human is next
					move = scanIn.nextLine();
					winCondition = board.checkedMove(new Move(move));
				}
				else {	// ai is next
					winCondition = board.move(getAiMove(board));
				}
				
				System.out.println();
				System.out.println(board.toHumanReadableString());
				System.out.println();
				if (winCondition == '=') { // tie
					System.out.println("Tie! Nobody wins.");
					break;
				}
				else if (winCondition == 'W') { // white wins
					System.out.println("White wins!");
					break;
				}
				else if (winCondition == 'B') { // black wins
					System.out.println("Black wins!");
					break;
				}
				else if (winCondition == '?') { // nobody won yet
					System.out.println(board.printValidMovesForNextTurn());
					System.out.println("Next move:");
				}
				else { // invalid win condition
					scanIn.close();
					throw new Error("Invalid win condition returned by move()");
				}
			}
			while ((board.onMove != humanColorChar) | scanIn.hasNextLine());
			scanIn.close();
		}
		else if ("ai-telnet".matches(gameType)) {	// ai on telnet TODO
			
			System.out.println(board.toHumanReadableString());
			System.out.println();
			System.out.println(board.printValidMovesForNextTurn());
			System.out.println("Next move:");

			System.out.println("Choose your color: ");
			String humanColor = scanIn.nextLine();
			char humanColorChar = humanColor.trim().charAt(0);
			do {
				// is it human's or ai's turn?
				if (humanColorChar == board.onMove) {	// human is next
					move = scanIn.nextLine();
					winCondition = board.checkedMove(new Move(move));
				}
				else {	// ai is next
					winCondition = board.move(getAiMove(board));
				}
				
				System.out.println();
				System.out.println(board.toHumanReadableString());
				System.out.println();
				if (winCondition == '=') { // tie
					System.out.println("Tie! Nobody wins.");
					break;
				}
				else if (winCondition == 'W') { // white wins
					System.out.println("White wins!");
					break;
				}
				else if (winCondition == 'B') { // black wins
					System.out.println("Black wins!");
					break;
				}
				else if (winCondition == '?') { // nobody won yet
					System.out.println(board.printValidMovesForNextTurn());
					System.out.println("Next move:");
				}
				else { // invalid win condition
					scanIn.close();
					throw new Error("Invalid win condition returned by move()");
				}
			}
			while ((board.onMove != humanColorChar) | scanIn.hasNextLine());
			scanIn.close();
		}
		else {	// unknown game type
			scanIn.close();
			throw new Error("Unknown game type: " + gameType);
		}
	}

	private static Move getRandomAiMove(Board board)
	{
		int listLength = board.legalMovesForNextTurn.size();
		int randomIndex = (int)(Math.random() * listLength);
		return board.legalMovesForNextTurn.get(randomIndex);
	}
	
	private static Move getAiMove(Board board)
	{
		return getRandomAiMove(board);
	}
}
