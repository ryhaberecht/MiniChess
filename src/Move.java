
public class Move {

	public Square from, to;
	
	public Move(String move)
	{
		if (move.length() != 5) {
			throw new Error("move string has invalid length = " + move.length());
		}
		
		this.from = Square(move.substring(0, 2));
		this.to = Square(move.substring(3, 5));
	}
	
	public String toString()
	{
		return this.from.toString() + "-" + this.to.toString();
	}
}
