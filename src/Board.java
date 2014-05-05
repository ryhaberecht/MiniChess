
public class Board
{
	char[][] squares = new char[6][5];	// number / letter -> row / column
	
	int moveNum;	// 1 to 40 (40 = draw)
	
	char onMove;	// B or W
	
	
	public Board(String state) throws Exception
	{
		if (state.length() < 40) {
			throw new Exception("state is shorter than 40");
		}
		
	}
	
	void makeBoard(String state)
	{
		String [] lines = state.split("\n");
		
	}
	
	public String toString()
	{
		String result = moveNum + " " + onMove + "\n";
		for (int row = 5; row >= 0; --row) {
			for (int col = 0; col < 5; col++) {
				result += squares[row][col];
			}
			
			result += "\n";
		}
		
		return result;
	}
}