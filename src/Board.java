
public class Board
{
	char[][] squares = new char[6][5];	// number / letter -> row / column
	
	int moveNumber;	// 1 to 40 (40 = draw)
	
	char whoseMove;	// B or W
	
	
	public Board(String state) throws Exception
	{
		if (state.length() < 40) {
			throw new Exception("state is shorter than 40");
		}
		
	}
	
	public String toString()
	{
		String state = "";
		
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 5; j++) {
				state += squares[i][j];
			}
			state += "\n";
		}
		
		return state += moveNumber + " " + whoseMove;
	}
}