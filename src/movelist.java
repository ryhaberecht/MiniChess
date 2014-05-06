import java.util.ArrayList;


public class movelist {
	
	public void getAllMoves(char piece, Square coordinate, ArrayList<Move> allMoves){
		
		switch (piece){
			case 'q':
			case 'Q':
				for(int x = -1; x <= 1; x++){
					for(int y = -1; y <= 1; y++){
						new Board().scan(allMoves, coordinate, y, x, true, false, false);
					}
				}
				break;
			case 'k':
			case 'K': 
				for(int x = -1; x <= 1; x++){
					for(int y = -1; y <= 1; y++){
						new Board().scan(allMoves, coordinate, y, x, true, true, false);
					}
				}
				break;
			case 'r':
			case 'R':
				for(int x = -1; x <= 1; x++){
					new Board().scan(allMoves, coordinate, x, 0, true, false, false);
				}
				for(int y = -1; y <= 1; y++){
					new Board().scan(allMoves, coordinate, 0, y, true, false, false);
				}
				break;
			case 'b':
			case 'B':
				for(int x = -1; x <= 1; x++){
					new Board().scan(allMoves, coordinate, x, x, true, false, false);
				}
				new Board().scan(allMoves, coordinate, 1, 0, false, true, false);
				new Board().scan(allMoves, coordinate, -1, 0, false, true, false);
				new Board().scan(allMoves, coordinate, 0, 1, false, true, false);
				new Board().scan(allMoves, coordinate, 0, -1, false, true, false);
				break;
			case 'n':
			case 'N':
				Board b = new Board();
				b.scan(allMoves, coordinate, 1, 2, true, true, false);
				b.scan(allMoves, coordinate, -1, 2, true, true, false);
				b.scan(allMoves, coordinate, 1, -2, true, true, false);
				b.scan(allMoves, coordinate, -1, -2, true, true, false);
				b.scan(allMoves, coordinate, 2, 1, true, true, false);
				b.scan(allMoves, coordinate, 2, -1, true, true, false);
				b.scan(allMoves, coordinate, -2, -1, true, true, false);
				b.scan(allMoves, coordinate, -2, 1, true, true, false);
				break;
			case 'p':
				new Board().scan(allMoves, coordinate, 0, -1, false, true, false);
				new Board().scan(allMoves, coordinate, 1, -1, true, true, true);
				new Board().scan(allMoves, coordinate, -1, -1, true, true, true);
				break;
			case 'P':
				new Board().scan(allMoves, coordinate, 0, 1, false, true, false);
				new Board().scan(allMoves, coordinate, 1, 1, true, true, true);
				new Board().scan(allMoves, coordinate, -1, 1, true, true, true);
		}
	}
}
