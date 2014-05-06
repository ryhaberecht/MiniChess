public class Move{

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

	@Override
	public String toString()
	{

		return this.from.toString() + "-" + this.to.toString();
	}
}
