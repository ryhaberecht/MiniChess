import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Scanner;

public class Driver
{
	private static final boolean cleanOffers = false;

	public static void main(String[] args) throws IOException
	{
		// determine type of game
		System.out.println("Which kind of game? (human-human(1)/human-ai(2)/ai-ai(3)/ai-telnet(4)):");
		Scanner scanIn = new Scanner(System.in);
		String gameType = scanIn.nextLine().trim();

		// initialize varaibles
		Board board = new Board();
		String move;
		char winCondition;

		if ("human-human".matches(gameType) || "1".matches(gameType)) { // human on human

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
		else if ("ai-ai".matches(gameType) || "2".matches(gameType)) { // ai on ai

			Writer writer = new FileWriter("output.txt");

			writer.write(board.toHumanReadableString());
			writer.write("\n");
			writer.write("\n");
			writer.write(board.printValidMovesForNextTurn());
			writer.write("\n");
			writer.write("Next move: ");

			do {
				Move nextMove = getAiMove(board);
				writer.write(nextMove.toString());
				winCondition = board.move(nextMove);
				writer.write("\n");
				writer.write("\n");
				writer.write("\n");
				writer.write(board.toHumanReadableString());
				writer.write("\n");
				if (winCondition == '=') { // tie
					writer.write("\n");
					writer.write("Tie! Nobody wins.");
					break;
				}
				else if (winCondition == 'W') { // white wins
					writer.write("\n");
					writer.write("White wins!");
					break;
				}
				else if (winCondition == 'B') { // black wins
					writer.write("\n");
					writer.write("Black wins!");
					break;
				}
				else if (winCondition == '?') { // nobody won yet
					writer.write("\n");
					writer.write(board.printValidMovesForNextTurn());
					writer.write("\n");
					writer.write("Next move: ");
				}
				else { // invalid win condition
					scanIn.close();
					writer.close();
					throw new Error("Invalid win condition returned by move()");
				}
			}
			while (true);
			writer.close();
		}
		else if ("human-ai".matches(gameType) || "3".matches(gameType)) { // human on ai

			System.out.println(board.toHumanReadableString());
			System.out.println();
			System.out.println(board.printValidMovesForNextTurn());
			System.out.println("Next move:");

			System.out.println("Choose your color: ");
			String humanColor = scanIn.nextLine();
			char humanColorChar = humanColor.trim().toUpperCase().charAt(0);
			do {
				// is it human's or ai's turn?
				if (humanColorChar == board.onMove) { // human is next
					move = scanIn.nextLine();
					winCondition = board.checkedMove(new Move(move));
				}
				else { // ai is next
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
		else if ("ai-telnet".matches(gameType) || "4".matches(gameType)) { // ai on telnet

			// create connection to ICMS
			System.out.println("Trying to connect to ICMS server...");
			TelnetClient telnetClient = new TelnetClient(Constants.SERVER, Constants.PORT, Constants.USERNAME, Constants.PASSWORD);

			// delete all old offers
			if (cleanOffers) {
				telnetClient.send("clean", true);
				telnetClient.expect("204", true);
			}

			// offer or accept game
			System.out.println("Offer (o) or accept (a) game?");
			char offerOrAccept = scanIn.nextLine().toLowerCase().charAt(0);
			char colorToPlay;

			if (offerOrAccept == 'o') { // offer

				System.out.println("Choose color (W/B/?):");
				char chosenColor = scanIn.nextLine().toUpperCase().charAt(0);
				colorToPlay = telnetClient.offer(chosenColor);
			}
			else if (offerOrAccept == 'a') { // accept

				System.out.println("Accept game nr.:");
				String gameNr = scanIn.nextLine();
				System.out.println("Choose color (W/B/?):");
				char chosenColor = scanIn.nextLine().toUpperCase().charAt(0);
				colorToPlay = telnetClient.accept(gameNr, chosenColor);
			}
			else { // wrong input
				telnetClient.close();
				scanIn.close();
				throw new Error("Action does not exist!");
			}

			// print initial board TODO optimize
			System.out.println("\n" + board.toHumanReadableString() + "\n");
			System.out.println(board.printValidMovesForNextTurn() + "\nNext move:");

			// maybe wait for adversary
			String adversaryMove;
			Move ownMove;
			if (colorToPlay == 'B') { // start second, adversary is White
				adversaryMove = telnetClient.getMove(); // wait for adversary's move
				if (adversaryMove == null) {
					telnetClient.close();
					scanIn.close();
					throw new Error("game or connection ended before first move of adversary.");
				}
				winCondition = board.move(new Move(adversaryMove));
				// TODO optimize
				System.out.println(adversaryMove + "\n\n" + board.toHumanReadableString() + "\n");
				System.out.println(board.printValidMovesForNextTurn() + "\nNext move:");
				if (winCondition == '=') { // tie
					System.out.println("Tie! Nobody wins.");
				}
				else if (winCondition == 'W') { // white wins
					System.out.println("White wins!");
				}
				else if (winCondition == 'B') { // black wins
					System.out.println("Black wins!");
				}
			}

			while (true) {

				// make own move
				ownMove = getAiMove(board);
				telnetClient.sendMove(ownMove.toString());
				winCondition = board.move(ownMove);
				// TODO optimize
				System.out.println(ownMove + "\n\n" + board.toHumanReadableString() + "\n");
				System.out.println(board.printValidMovesForNextTurn() + "\nNext move:");
				if (winCondition == '=') { // tie
					System.out.println("Tie! Nobody wins.");
				}
				else if (winCondition == 'W') { // white wins
					System.out.println("White wins!");
				}
				else if (winCondition == 'B') { // black wins
					System.out.println("Black wins!");
				}

				// wait for adversary
				adversaryMove = telnetClient.getMove(); // wait for adversary's move
				if (adversaryMove == null) {
					break;
				}
				winCondition = board.move(new Move(adversaryMove));
				// TODO optimize
				System.out.println(adversaryMove + "\n\n" + board.toHumanReadableString() + "\n");
				System.out.println(board.printValidMovesForNextTurn() + "\nNext move:");
				if (winCondition == '=') { // tie
					System.out.println("Tie! Nobody wins.");
				}
				else if (winCondition == 'W') { // white wins
					System.out.println("White wins!");
				}
				else if (winCondition == 'B') { // black wins
					System.out.println("Black wins!");
				}
			}
			telnetClient.close(); // close ICMS connection
		}
		else { // unknown game type
			scanIn.close();
			throw new Error("Unknown game type: " + gameType);
		}
		scanIn.close();
	}

	private static Move getRandomAiMove(Board board)
	{
		int listLength = board.legalMovesForNextTurn.size();
		int randomIndex = (int) (Math.random() * listLength);
		return board.legalMovesForNextTurn.get(randomIndex);
	}

	private static Move getAiMove(Board board)
	{
		return getRandomAiMove(board);
	}
}
