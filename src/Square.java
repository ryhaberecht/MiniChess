
public class Square {
	public int col;
	public int row;
	
	Square(String command){
		this.row = Integer.parseInt(command.substring(1, 2)) - 1;
		char col = command.charAt(0);
		if(col == 'a' || col == 'A'){
			this.col = 0;
		}else if(col == 'b' || col == 'B'){
			this.col = 1;
		}else if(col == 'c' || col == 'C'){
			this.col = 2;
		}else if(col == 'd' || col == 'D'){
			this.col = 3;
		}else if(col == 'e' || col == 'E'){
			this.col = 4;
		}else {
			throw new Error("Unknown column: " + col);
		}
	}
	
	Square(int col, int row){
		this.col = col-1;
		this.row = row-1;
	}
	
	public String toString(){
		char col = '\u0000';
		
		if(this.col == 0){
			col = 'a';
		}else if(this.col == 1){
			col = 'b';
		}else if(this.col == 2){
			col = 'c';
		}else if(this.col == 3){
			col = 'd';
		}else if(this.col == 4){
			col = 'e';
		}
		return col+Integer.toString(row+1);
	}
}
