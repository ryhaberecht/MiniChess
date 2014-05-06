public class Move
{

	public Square from, to;

	public Move(Square from, Square to)
	{
		this.from = from;
		this.to = to;
	}

	public Move(String move)
	{

		if (move.length() != 5) {
			throw new Error("move string has invalid length = " + move.length());
		}

		this.from = new Square(move.substring(0, 2));
		this.to = new Square(move.substring(3, 5));
	}

	public String toString()
	{

		return this.from.toString() + "-" + this.to.toString();
	}

	public boolean equals(Move o)
	{
		if (o == null) {
			return false;
		}
		else if (o.from.col == this.from.col 
				&& o.from.row == this.from.row 
				&& o.to.col == this.to.col
				&& o.to.row == this.to.row) {
			return true;
		}
		else {
			return false;
		}
	}
}
