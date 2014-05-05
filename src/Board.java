import java.io.IOException;
import java.io.Reader;
import java.io.Writer;

public class Board {
	char[][] squares = new char[6][5]; // number / letter -> row / column

	int moveNum; // 1 to 40 (40 = draw)

	char onMove; // B or W

	public static void main(String args[]) {
		System.out.println(new Board());
	}

	public Board(String state) {
		
		makeBoard(state);
	}

	public Board(Reader reader) throws IOException {
		
		char[] cbuf = new char[40];
		reader.read(cbuf);
		makeBoard(cbuf.toString());
	}

	public Board() {
		
		makeBoard("1 W\nkqbnr\nppppp\n.....\n.....\nPPPPP\nRNBQK\n");
	}

	private void makeBoard(String state) {

		if (state.length() != 40) {
			throw new Error("state does not have 40 characters but "
					+ state.length());
		}

		String[] lines = state.split("\n");

		if (lines.length != 7) {
			throw new Error("state does not have 7 lines");
		}

		this.moveNum = Integer.parseInt(lines[0].substring(0, 1));

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
	
	public void print(Writer writer) throws IOException
	{
		writer.write(this.toString());
	}
}
