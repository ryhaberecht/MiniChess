import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;

public class Board
{

	char[][] squares = new char[6][5]; // number / letter -> row / column

	int moveNum; // number of next move taking place. 1 to 40 (40 = draw)

	char onMove; // Has B or W the next move?

	// test function, no error means it works Ok
	public static void main(String args[]) throws IOException
	{

		// Test

		// reader + writer
		Reader reader = new FileReader("input.txt");
		Board board2 = new Board(reader);
		System.out.println(board2.toString());
		System.out.println();
		reader.close();

		board2.move(new Move("b1-a3"));

		Writer writer = new FileWriter("output.txt");
		board2.print(writer);
		writer.close();

		// move
		Board board = new Board();
		System.out.println(board.toString());
		System.out.println();

		board.move(new Move("b2-b3"));
		System.out.println(board.toString());
		System.out.println();

		board.move(new Move("a5-a4"));
		System.out.println(board.toString());
		System.out.println();
	}

	// create new board from state in "state"
	public Board(String state)
	{

		makeBoard(state);
	}

	// create new board from state in "reader"
	public Board(Reader reader) throws IOException
	{

		char[] cbuf = new char[39];
		reader.read(cbuf);
		makeBoard(new String(cbuf));
	}

	// create new board with default state
	public Board()
	{

		makeBoard("1 W\nkqbnr\nppppp\n.....\n.....\nPPPPP\nRNBQK");
	}

	// create board helper function
	private void makeBoard(String state)
	{

		// check length of state string
		if (state.length() != 39 && state.length() != 40) {
			throw new Error("state does not have 39 or 40 characters but " + state.length() + ". And is: " + state);
		}

		String[] lines = state.split("\n");
		// check number of lines
		if (lines.length != 7) {
			throw new Error("state does not have 7 lines");
		}

		// check if movement number makes sense and save
		this.moveNum = Integer.parseInt(lines[0].substring(0, 2).trim());
		if (this.moveNum < 1 || this.moveNum > 40) {
			throw new Error("moveNum impossible, < 0 or > 40! moveNum = " + this.moveNum);
		}

		// check if whose next makes sense and save
		if (this.moveNum < 10) {
			this.onMove = lines[0].charAt(2);
		}
		else {
			this.onMove = lines[0].charAt(3);
		}
		if (this.onMove != 'B' && this.onMove != 'W') {
			throw new Error("onMove is not B or W");
		}

		// check if characters are a valid piece or empty and save
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
					throw new Error("this char does not exist on a board: " + currentChar);
				}

				this.squares[row][column] = currentChar;
			}
		}
	}

	// print state into standardized string (39 or 40 characters, depending on
	// nr. of move)
	@Override
	public String toString()
	{

		String result = moveNum + " " + onMove;

		for (int row = 5; row >= 0; row--) {
			result += "\n";
			for (int col = 0; col < 5; col++) {
				result += squares[row][col];
			}
		}

		return result;
	}

	// print state into human readable string, nicer to the eye than toString()
	public String toHumanReadableString()
	{

		String result = "Move Nr.: " + moveNum + "\nFor Player: " + (onMove == 'W' ? "White" : "Black") + "\n";

		for (int row = Constants.MAX_ROW; row >= Constants.MIN_ROW; row--) {
			result += "\n" + (row + 1) + "|";
			for (int col = Constants.MIN_COLUMN; col <= Constants.MAX_COLUMN; col++) {
				result += squares[row][col];
			}
		}

		return result += "\n  -----\n  abcde";
	}

	// print standardized string into writer
	public void print(Writer writer) throws IOException
	{

		writer.write(this.toString());
	}

	// commence movement, check if it is valid
	public void move(Move move)
	{

		// get piece about to be moved
		char piece = squares[move.from.row][move.from.col];

		// moving empty space is not allowed
		if (piece == '.') {
			throw new Error("No piece at " + move.from.toString() + " (row = " + move.from.row + ", column = " + move.from.col + ")");
		}
		// determine color of piece
		boolean pieceIsWhite = isPieceWhite(piece);

		// check if piece color matches the color whose turn it is
		if (pieceIsWhite && this.onMove == 'B') {
			throw new Error("white piece to be moved although it is black's turn! Move = " + move.toString());
		}
		if (!pieceIsWhite && this.onMove == 'W') {
			throw new Error("black piece to be moved although it is white's turn! Move = " + move.toString());
		}

		// new position already taken by piece
		if (squares[move.to.row][move.to.col] != '.') {

			// get piece to be taken
			char pieceToBeTaken = squares[move.to.row][move.to.col];
			boolean pieceToBeTakenIsWhite = isPieceWhite(pieceToBeTaken) ? true : false;

			// taking a piece of the same color is not allowed
			if (pieceIsWhite && pieceToBeTakenIsWhite) {
				throw new Error("white piece to be taken by white piece! Move = " + move.toString());
			}
			if (!pieceIsWhite && !pieceToBeTakenIsWhite) {
				throw new Error("black piece to be taken by black piece! Move = " + move.toString());
			}

			// take piece
			// TODO figur schlagen
		}

		// move piece to new position
		squares[move.to.row][move.to.col] = piece;
		// remove old piece position
		squares[move.from.row][move.from.col] = '.';

		// update side for next turn
		if (this.onMove == 'W') {
			this.onMove = 'B';
		}
		else {
			this.onMove = 'W';
		}

		// increase number of turns (moves)
		this.moveNum += 1;
		// tie?
		if (this.moveNum == 40) {
			// TODO

		}
	}

	// add all legal moves for a piece from "start" in direction "dr"/"dc" into
	// "moves".
	// "capture" tells that the piece may capture during a move or not.
	// "single" tells that the piece may only move one square.
	// "capture_only" tells that the piece may only move if capturing.
	public void scan(ArrayList<Move> moves, Square start, int dr, int dc, boolean capture, boolean single, boolean capture_only)
	{
		// initialise next square with old square
		int nextColumn = start.col;
		int nextRow = start.row;

		do {
			// walk along column
			nextColumn += dc;
			// check if off board in column
			if (nextColumn < Constants.MIN_COLUMN || nextColumn > Constants.MAX_COLUMN) {
				return; // off board, stop search
			}

			// walk along row
			nextRow += dr;
			// check if off board in row
			if (nextRow < Constants.MIN_ROW || nextRow > Constants.MAX_ROW) {
				return; // off board, stop search
			}

			// check if run into piece
			char nextPiece = squares[nextRow][nextColumn];
			if (nextPiece != '.') { // have run into other piece

				// in what kind of piece have i run?
				// determine color of piece
				boolean pieceIsWhite = isPieceWhite(squares[start.row][start.col]);
				boolean pieceToBeTakenIsWhite = isPieceWhite(squares[nextRow][nextColumn]);

				if ((pieceToBeTakenIsWhite ^ pieceIsWhite)) { // hit enemy piece

					if (capture) { // may capture enemy

						moves.add(new Move(start, new Square(nextColumn, nextRow))); // capture
						break; // stop
					}
					else { // may not capture enemy
						break; // stop
					}
				}
				else { // hit own piece
					break; // stop
				}
			}
			else { // have not run into other piece

				if (capture_only) { // may only move if capturing
					continue; // try next scan step
				}
				else { // may move without capturing
					moves.add(new Move(start, new Square(nextColumn, nextRow))); // not capturing but legal
				}
			}
		}
		while (!single); // repeat scanning steps until stop condition met or single step taken
	}

	// returns a list of all legal moves
	public ArrayList<Move> getAllLegalMoves(char color)
	{
		// color does not exist
		if (color != 'B' && color != 'W') {
			throw new Error("color is not B or W");
		}
		
		ArrayList<Move> legalMoves = new ArrayList<Move>();
		
		// scan all squares for a piece of my color
		char piece;
		for (int row = Constants.MIN_ROW; row <= Constants.MAX_ROW; row++) {
			
			for (int column = Constants.MIN_COLUMN; column <= Constants.MAX_COLUMN; column++) {
				
				piece = squares[row][column];	// potential piece
				// there is a piece
				if (piece != '.') {
					
					// piece is of my color
					if ((isPieceWhite(piece) && color == 'W') || (!isPieceWhite(piece) && color == 'B')) {
						
						getLegalMoves(piece, new Square(column, row), legalMoves);
					}
				}
			}
		}

		return legalMoves;
	}

	// appends legal moves of a piece at coordinate to allMoves
	public void getLegalMoves(char piece, Square squarecoordinate, ArrayList<Move> allMoves)
	{
		// TODO Auto-generated method stub
		
	}

	private boolean isPieceWhite(char piece)
	{

		return (piece >= 'A' && piece <= 'Z') ? true : false;
	}
}
