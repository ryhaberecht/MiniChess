import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.Writer;
import java.util.ArrayList;

public class Board
{
	private static boolean abortCalculation = false;
	
	private static long startTime;
	
	private static int numberOfRecursions = 0;

	char[][] squares = new char[Constants.MAX_ROW + 1][Constants.MAX_COLUMN + 1]; // number / letter -> row / column

	int moveNum; // number of next move taking place. 1 to 40 (40 = draw)

	char onMove; // Has B or W the next move?

	ArrayList<Move> legalMovesForNextTurn; // list of legal moves for next turn

	float score; // the score for the color to move next

	Move moveTaken;

	// test function, no error means it works Ok
	public static void main(String args[]) throws IOException
	{
		// Test

		System.out.println();
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

	public Board(Board board)
	{
		// copy squares
		this.squares = new char[Constants.MAX_ROW + 1][Constants.MAX_COLUMN + 1];
		for (int row = Constants.MAX_ROW; row >= Constants.MIN_ROW; row--) {
			for (int col = Constants.MIN_COLUMN; col <= Constants.MAX_COLUMN; col++) {
				this.squares[row][col] = board.squares[row][col];
			}
		}
		// copy rest
		this.moveNum = board.moveNum;
		this.onMove = board.onMove;
		// this.legalMovesForNextTurn = deepCopyArrayList(board.legalMovesForNextTurn);
		this.score = board.score;
	}

	// create new board from state in "state"
	public Board(String state)
	{
		makeBoard(state);
	}

	// create new board from state in "reader"
	public Board(Reader reader) throws IOException
	{
		char[] cbuf = new char[40];
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
		if (this.moveNum < 1 || this.moveNum > 80) {
			throw new Error("moveNum impossible, < 0 or > 80! moveNum = " + this.moveNum);
		}
		//System.out.println(moveNum);

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
		//System.out.println(onMove);

		// correct this.moveNum because it is 40 moves each
		this.moveNum = ((this.onMove == 'W') ? ((this.moveNum * 2) - 1) : (this.moveNum * 2));
		//System.out.println(moveNum);

		// check if characters are a valid piece or empty and save
		char currentChar;
		for (int row = Constants.MAX_ROW, line = 1; row >= Constants.MIN_ROW; row--, line++) {

			for (int column = Constants.MIN_COLUMN; column <= Constants.MAX_COLUMN; column++) {

				//System.out.println(lines[line].length());
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

		// calculate all valid moves for next turn
		this.legalMovesForNextTurn = getAllLegalMoves(this.onMove);

		// calculate initial board score
		this.score = calculateHeuristicScore(this);
	}

	// print state into standardized string (39 or 40 characters, depending on nr. of move)
	@Override
	public String toString()
	{
		String result = ((this.onMove == 'W') ? ((this.moveNum + 1) / 2) : (this.moveNum / 2)) + " " + this.onMove;

		for (int row = Constants.MAX_ROW; row >= Constants.MIN_ROW; row--) {
			result += "\n";
			for (int col = Constants.MIN_COLUMN; col <= Constants.MAX_COLUMN; col++) {
				result += squares[row][col];
			}
		}

		return result;
	}

	// print state into human readable string, nicer to the eye than toString()
	public String toHumanReadableString()
	{
		String result = "Move Nr.: " + ((this.onMove == 'W') ? ((this.moveNum + 1) / 2) : (this.moveNum / 2)) + "\nFor Player: " + (this.onMove == 'W' ? "White" : "Black") + "\n";

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

	// print human readable string into writer
	public void humanReadablePrint(Writer writer) throws IOException
	{
		writer.write(this.toHumanReadableString());
	}

	public char checkedMove(Move move)
	{
		// check if move is legal
		if (!this.legalMovesForNextTurn.contains(move)) { // desired move is not contained in list of legal moves for next turn
			throw new Error("Illegal move: " + move.toString());
		}
		else { // move is legal
			return move(move);
		}
	}

	// commence movement, check if it is valid.
	// returns '?' if game did not end, '=' for a tie, 'W' if white wins and 'B' if black wins.
	public char move(Move move)
	{
		char returnValue = '?'; // per default no winning-condition is met, should continue game

		// get piece about to be moved
		char piece = squares[move.from.row][move.from.col];

		/*
		 * // moving empty space is not allowed if (piece == '.') { throw new Error("No piece at " + move.from.toString() + " (row = " + move.from.row
		 * + ", column = " + move.from.col + ")"); } // determine color of piece boolean pieceIsWhite = isPieceWhite(piece); // check if piece color
		 * matches the color whose turn it is if (pieceIsWhite && this.onMove == 'B') { throw new
		 * Error("white piece to be moved although it is black's turn! Move = " + move.toString()); } if (!pieceIsWhite && this.onMove == 'W') { throw
		 * new Error("black piece to be moved although it is white's turn! Move = " + move.toString()); }
		 */

		// new position already taken by piece
		if (squares[move.to.row][move.to.col] != '.') {

			// get piece to be taken
			char pieceToBeTaken = squares[move.to.row][move.to.col];
			/*
			 * boolean pieceToBeTakenIsWhite = isPieceWhite(pieceToBeTaken) ? true : false; // taking a piece of the same color is not allowed if
			 * (pieceIsWhite && pieceToBeTakenIsWhite) { throw new Error("white piece to be taken by white piece! Move = " + move.toString()); } if
			 * (!pieceIsWhite && !pieceToBeTakenIsWhite) { throw new Error("black piece to be taken by black piece! Move = " + move.toString()); }
			 */

			// take piece
			if (pieceToBeTaken == 'k') {
				returnValue = 'W'; // black king taken, white wins!
			}
			else if (pieceToBeTaken == 'K') {
				returnValue = 'B'; // white king taken, black wins!
			}
		}

		// turn pawn into queen at the respective end of the board
		if (piece == 'P' && move.to.row == Constants.MAX_ROW) { // white pawn reached upper end of board
			piece = 'Q'; // turn pawn into queen
		}
		else if (piece == 'p' && move.to.row == Constants.MIN_ROW) { // black pawn reached lower end of board
			piece = 'q'; // turn pawn into queen
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
		if (this.moveNum == 81 && (returnValue != 'W' || returnValue != 'B')) {
			returnValue = '='; // game ended with tie
		}

		// calculate all valid moves for next turn
		this.legalMovesForNextTurn = getAllLegalMoves(this.onMove);

		// if there are no valid moves for next turn, current turns player wins
		if (this.legalMovesForNextTurn.isEmpty()) {

			if (this.onMove == 'W') {
				returnValue = 'B';
			}
			else {
				returnValue = 'W';
			}
			// System.out.println("No valid turns for " + this.onMove + " in next turn. " + returnValue + " wins!");
		}

		return returnValue;
	}

	// returns a String of valid moves the current player could make next
	public String printValidMovesForNextTurn()
	{
		String output = "Valid moves: ";

		for (Move move : this.legalMovesForNextTurn) {
			output += move.toString() + ", ";
		}

		return output;
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

		ArrayList<Move> list = new ArrayList<Move>();

		// scan all squares for a piece of my color
		char piece;
		for (int row = Constants.MIN_ROW; row <= Constants.MAX_ROW; row++) {

			for (int column = Constants.MIN_COLUMN; column <= Constants.MAX_COLUMN; column++) {

				piece = this.squares[row][column]; // potential piece
				// there is a piece
				if (piece != '.') {

					// piece is of my color
					if ((isPieceWhite(piece) && color == 'W') || (!isPieceWhite(piece) && color == 'B')) {

						getLegalMoves(piece, new Square(column, row), list);
					}
				}
			}
		}

		return list;
	}

	// appends legal moves of a piece at coordinate to allMoves
	public void getLegalMoves(char piece, Square coordinate, ArrayList<Move> allMoves)
	{
		switch (piece) {
		case 'q':
		case 'Q':
			scan(allMoves, coordinate, 0, 1, true, false, false);
			scan(allMoves, coordinate, 0, -1, true, false, false);
			scan(allMoves, coordinate, 1, 0, true, false, false);
			scan(allMoves, coordinate, -1, 0, true, false, false);
			scan(allMoves, coordinate, 1, 1, true, false, false);
			scan(allMoves, coordinate, 1, -1, true, false, false);
			scan(allMoves, coordinate, -1, 1, true, false, false);
			scan(allMoves, coordinate, -1, -1, true, false, false);
			break;
		case 'k':
		case 'K':
			scan(allMoves, coordinate, 0, 1, true, true, false);
			scan(allMoves, coordinate, 0, -1, true, true, false);
			scan(allMoves, coordinate, 1, 0, true, true, false);
			scan(allMoves, coordinate, -1, 0, true, true, false);
			scan(allMoves, coordinate, 1, 1, true, true, false);
			scan(allMoves, coordinate, 1, -1, true, true, false);
			scan(allMoves, coordinate, -1, 1, true, true, false);
			scan(allMoves, coordinate, -1, -1, true, true, false);
			break;
		case 'r':
		case 'R':
			scan(allMoves, coordinate, 0, 1, true, false, false);
			scan(allMoves, coordinate, 0, -1, true, false, false);
			scan(allMoves, coordinate, 1, 0, true, false, false);
			scan(allMoves, coordinate, -1, 0, true, false, false);
			break;
		case 'b':
		case 'B':
			// diagonal
			scan(allMoves, coordinate, -1, -1, true, false, false);
			scan(allMoves, coordinate, -1, 1, true, false, false);
			scan(allMoves, coordinate, 1, -1, true, false, false);
			scan(allMoves, coordinate, 1, 1, true, false, false);
			// straight
			scan(allMoves, coordinate, 1, 0, false, true, false);
			scan(allMoves, coordinate, -1, 0, false, true, false);
			scan(allMoves, coordinate, 0, 1, false, true, false);
			scan(allMoves, coordinate, 0, -1, false, true, false);
			break;
		case 'n':
		case 'N':
			scan(allMoves, coordinate, 1, 2, true, true, false);
			scan(allMoves, coordinate, 1, -2, true, true, false);
			scan(allMoves, coordinate, -1, 2, true, true, false);
			scan(allMoves, coordinate, -1, -2, true, true, false);
			scan(allMoves, coordinate, 2, 1, true, true, false);
			scan(allMoves, coordinate, 2, -1, true, true, false);
			scan(allMoves, coordinate, -2, 1, true, true, false);
			scan(allMoves, coordinate, -2, -1, true, true, false);
			break;
		case 'p':
			scan(allMoves, coordinate, -1, 0, false, true, false);
			scan(allMoves, coordinate, -1, 1, true, true, true);
			scan(allMoves, coordinate, -1, -1, true, true, true);
			break;
		case 'P':
			scan(allMoves, coordinate, 1, 0, false, true, false);
			scan(allMoves, coordinate, 1, 1, true, true, true);
			scan(allMoves, coordinate, 1, -1, true, true, true);
			break;
		}
	}

	private boolean isPieceWhite(char piece)
	{

		return (piece >= 'A' && piece <= 'Z') ? true : false;
	}

	public Move getRandomAiMove()
	{
		int listLength = this.legalMovesForNextTurn.size();
		int randomIndex = (int) (Math.random() * listLength);
		return this.legalMovesForNextTurn.get(randomIndex);
	}

	public Move getRandomHeuristicAiMove()
	{
		// create move-indexed map for board copies
		ArrayList<Board> boardsForNextLegalMoves = new ArrayList<Board>(this.legalMovesForNextTurn.size());
		float lowestScore = Float.POSITIVE_INFINITY;

		// for every legal move
		for (Move move : this.legalMovesForNextTurn) {
			Board boardCopy = new Board(this); // create board copy
			boardCopy.move(move); // make move on copy
			boardCopy.score = calculateHeuristicScore(boardCopy); // calculate new board score
			boardCopy.moveTaken = move;
			boardsForNextLegalMoves.add(boardCopy); // add board copy to map
			if (boardCopy.score < lowestScore) { // save board score if lowest
				lowestScore = boardCopy.score;
			}
		}

		// only keep boards with lowest score
		ArrayList<Board> survivingBoardsList = new ArrayList<Board>();
		for (Board board : boardsForNextLegalMoves) {
			if (board.score <= lowestScore) {
				survivingBoardsList.add(board);
			}
		}

		// return random one of the moves left
		int listLength = survivingBoardsList.size();
		int randomIndex = (int) (Math.random() * listLength);
		return survivingBoardsList.get(randomIndex).moveTaken;
	}

	public Move getAiMove()
	{
		return this.getNegamaxAiMoveWithTimeLimit();
		// return this.getRandomHeuristicAiMove();
	}

	// returns points for the current board and for the color who will take the next turn.
	// positive points show that the color taking the next turn is winning, negative that it is losing.
	public static float calculateHeuristicScore(Board board)
	{
		int counter_white = 0;
		int counter_black = 0;
		float result = 0;
		char position;

		for (int i = 0; i <= Constants.MAX_ROW; i++) {
			for (int j = 0; j <= Constants.MAX_COLUMN; j++) {
				position = board.squares[i][j];
				switch (position) {
				case 'K':
					counter_white += 100000;
					break;
				case 'Q':
					counter_white += 9;
					break;
				case 'R':
					counter_white += 5;
					break;
				case 'N':
					counter_white += 3;
					break;
				case 'B':
					counter_white += 3;
					break;
				case 'P':
					counter_white += 1;
					break;
				case 'k':
					counter_black += 100000;
					break;
				case 'q':
					counter_black += 9;
					break;
				case 'r':
					counter_black += 5;
					break;
				case 'n':
					counter_black += 3;
					break;
				case 'b':
					counter_black += 3;
					break;
				case 'p':
					counter_black += 1;
					break;
				}
			}
		}
		if (board.onMove == 'B') {
			result = counter_black - counter_white;
		}
		else {
			result = counter_white - counter_black;
		}
		// System.out.println("Heuristic score: "+result);
		return result;
	}

	public Move getNegamaxAiMoveWithTimeLimit()
	{
		int depth = 0;
		Move bestMoveYet = null;
		Move lastMove = null;
		
		Board.startTime = System.currentTimeMillis();	// save current system time

		while (Board.abortCalculation == false) {	// still got time
			
			bestMoveYet = lastMove;
			
			lastMove = getNegamaxAiMove(depth);	// calculate with specific depth
			
			depth++;	// increase depth for next run
		}

		//System.out.println("Depth used: " + (depth - 1));
		
		Board.abortCalculation = false;	// reset aborting calculation for future call
		Board.numberOfRecursions = 0;	// reset number of recursions for future call
		
		if (bestMoveYet == null) {
			throw new Error("Was too slow to calculate move, bestMoveYet == null");
		}
		return bestMoveYet;
	}

	public Move getNegamaxAiMove(int depth)
	{
		// create move-indexed map for board copies
		ArrayList<Board> boardsForNextLegalMoves = new ArrayList<Board>(this.legalMovesForNextTurn.size());

		float highestScore = Float.NEGATIVE_INFINITY;
		char winCondition;

		// for every legal move
		for (Move move : this.legalMovesForNextTurn) {

			// System.out.println("Considering move: "+move.toString());

			float currentScore;
			Board boardCopy = new Board(this); // create board copy
			winCondition = boardCopy.move(move); // make move on copy

			// act on game-over
			if (winCondition == '=') { // tie
				currentScore = 0;
			}
			else if (winCondition == 'B' || winCondition == 'W') { // some side wins
				if (winCondition == this.onMove) { // I win
					currentScore = 100000;
				}
				else { // opponent wins
					throw new Error("Can not capture own king.");
				}
			}
			else {
				currentScore = -getNegamaxScore(boardCopy, depth); // calculate new board score
			}

			// save board score if highest
			if (currentScore > highestScore) {
				highestScore = currentScore;
				// System.out.println("new highest score in move: " + currentScore);
			}

			boardCopy.score = currentScore;
			boardCopy.moveTaken = move;

			boardsForNextLegalMoves.add(boardCopy); // add board copy to map
		}

		// only keep boards with largest score
		ArrayList<Board> survivingBoardsList = new ArrayList<Board>();
		for (Board board : boardsForNextLegalMoves) {
			if (board.score >= highestScore) {
				survivingBoardsList.add(board);
				// System.out.println("board mit score " + board.score + " zur survivingBoardsList hinzugefügt.");
			}
		}

		// if there are several equally good moves
		if (survivingBoardsList.size() > 0) {
			// return random one of the moves left
			int listLength = survivingBoardsList.size();
			int randomIndex = (int) (Math.random() * listLength);
			return survivingBoardsList.get(randomIndex).moveTaken;
		}
		else {
			return null; // should never ever happen
		}
	}

	// search for the highest score
	public float getNegamaxScore(Board board, int recursionDepth)
	{
		// count number of recursions
		Board.numberOfRecursions += 1;
		if (Board.numberOfRecursions >= 10000) {	// should check time
			
			// took longer than I am allowed to
			if (System.currentTimeMillis() - Board.startTime >= Constants.MILLISECONDS_PER_MOVE) {
				Board.abortCalculation = true;	// signal abort calculation
			}
			Board.numberOfRecursions = 0;	// reset recursion counter
		}
		
		if (Board.abortCalculation == true) {	// should abort calculation
			return Float.NEGATIVE_INFINITY;
		}
		
		float highestScore = Float.NEGATIVE_INFINITY;
		char winCondition;

		// if recursion reached 0, return score
		if (recursionDepth == 0) {
			return calculateHeuristicScore(board);
		}

		// for every legal move
		for (Move move : board.legalMovesForNextTurn) {

			float currentScore;
			Board boardCopy = new Board(board); // create board copy
			winCondition = boardCopy.move(move); // make move on copy

			// act on game-over
			if (winCondition == '=') { // tie
				currentScore = 0;
			}// increase depth for next run
			else if (winCondition == 'B' || winCondition == 'W') { // some side wins
				if (winCondition == board.onMove) { // I win
					currentScore = 100000;
				}
				else { // opponent wins
					throw new Error("Can not capture own king.");
				}
			}
			else {
				currentScore = -getNegamaxScore(boardCopy, (recursionDepth - 1)); // calculate new board score
			}

			// save board score if highest
			if (currentScore >= highestScore) {
				highestScore = currentScore;
				// System.out.println("new highest score: " + highestScore);
			}
		}

		return highestScore;
	}
}