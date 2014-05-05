
public class Board
{
	char[][] squares = new char[6][5];	// number / letter -> row / column
	
	int moveNumber;	// 1 to 40 (40 = draw)
	
	char whoseMove;	// B or W
	
	
	public Board(String boardString)
	{
		
	}
	
	public String toString()
	{
		String boardString = "";
		
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 5; j++) {
				boardString += squares[i][j];
			}
			boardString += "\n";
		}
		
		return boardString += moveNumber + " " + whoseMove;
	}
}