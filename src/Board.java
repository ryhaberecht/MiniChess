import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.Writer;

public class Board {
	char[][] squares = new char[6][5]; // number / letter -> row / column

	int moveNum; // 1 to 40 (40 = draw)

	char onMove; // B or W

	public static void main(String args[]) throws IOException {

		// Test

		/*BufferedReader bufferRead = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("enter starting state:");
		Board board2 = new Board(bufferRead);
		System.out.println(board2.toString());*/


		Board board = new Board();
		System.out.println(board.toString());

	
		board.move(new Move("b2-b3"));
		System.out.println(board.toString());
		
		board.move(new Move("a5-a4"));
		System.out.println(board.toString());
		
		board.move(new Move("b3-a4"));
		System.out.println(board.toString());
	}

	public Board(String state) {

		makeBoard(state);
	}

	public Board(Reader reader) throws IOException {

		char[] cbuf = new char[39];
		reader.read(cbuf);
		makeBoard(new String(cbuf));
	}

	public Board() {

		makeBoard("1 W\nkqbnr\nppppp\n.....\n.....\nPPPPP\nRNBQK");
	}

	private void makeBoard(String state) {

		if (state.length() != 39) {
			throw new Error("state does not have 39 characters but "
					+ state.length() + ". And is: " + state);
		}

		String[] lines = state.split("\n");

		if (lines.length != 7) {
			throw new Error("state does not have 7 lines");
		}

		this.moveNum = Integer.parseInt(lines[0].substring(0, 1));
		if (this.moveNum < 1 || this.moveNum > 40) {
			throw new Error("moveNum impossible, < 0 or > 40! moveNum = "
					+ this.moveNum);
		}

		this.onMove = lines[0].charAt(2);
		if (this.onMove != 'B' && this.onMove != 'W') {
			throw new Error("onMove is not B or W");
		}

		char currentChar;
		for (int row = 5, line = 1; row >= 0; row--, line++) {

			for (int column = 0; column < 5; column++) {

				currentChar = lines[line].charAt(column);

				switch (currentChar) {
				case '.':
					break;
				case 'k':
					break;
				case 'K':
					break;
				case 'q':
					break;
				case 'Q':
					break;
				case 'b':
					break;
				case 'B':
					break;
				case 'n':
					break;
				case 'N':
					break;
				case 'r':
					break;
				case 'R':
					break;
				case 'p':
					break;
				case 'P':
					break;

				default:
					throw new Error("this char does not exist on a board: "
							+ currentChar);
				}

				this.squares[row][column] = currentChar;
			}
		}
	}

	public String toString() {
		String result = moveNum + " " + onMove + "\n";
		for (int row = 5; row >= 0; row--) {
			for (int col = 0; col < 5; col++) {
				result += squares[row][col];
			}

			result += "\n";
		}

		return result;
	}

	public void print(Writer writer) throws IOException {
		writer.write(this.toString());
	}

	public void move(Move move) {
		char piece = squares[move.from.row][move.from.col]; // piece to be moved
		if (piece == '.') {
			throw new Error("No piece at " + move.from.toString() + " (row = "
					+ move.from.row + ", column = " + move.from.col + ")");
		}
		boolean pieceIsWhite = (piece >= 'A' && piece <= 'Z') ? true : false;

		if (pieceIsWhite && this.onMove == 'B') {
			throw new Error(
					"white piece to be moved although it is black's turn! Move = "
							+ move.toString());
		}
		if (!pieceIsWhite && this.onMove == 'W') {
			throw new Error(
					"black piece to be moved although it is white's turn! Move = "
							+ move.toString());
		}

		if (squares[move.to.row][move.to.col] != '.') { // new position already
														// taken by piece

			char pieceToBeTaken = squares[move.to.row][move.to.col]; // piece to
																		// be
																		// taken
			boolean pieceToBeTakenIsWhite = (pieceToBeTaken >= 'A' && pieceToBeTaken <= 'Z') ? true
					: false;

			if (pieceIsWhite && pieceToBeTakenIsWhite) {
				throw new Error(
						"white piece to be taken by white piece! Move = "
								+ move.toString());
			}
			if (!pieceIsWhite && !pieceToBeTakenIsWhite) {
				throw new Error(
						"black piece to be taken by black piece! Move = "
								+ move.toString());
			}

			// TODO figur schlagen
		}

		squares[move.to.row][move.to.col] = piece; // move piece?
		squares[move.from.row][move.from.col] = '.'; // remove old piece
														// position

		// update side for next turn
		if (this.onMove == 'W') {
			this.onMove = 'B';
		} else {
			this.onMove = 'W';
		}

		this.moveNum += 1; // increase number of turns (moves)
		if (this.moveNum == 40) { // tie!
			// TODO
		}
	}
}
