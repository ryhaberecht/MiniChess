
public class Square {
	public int col;
	public int row;
	
	Square(String command){
		this.row = Integer.getInteger(command);
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
		}else if(col == 'f' || col == 'F'){
			this.col = 5;
		}
	}
	
	Square(int col, int row){
		this.col = col;
		this.row = row-1;
	}
	
	public String toString(){
		char col = '\u0000';
		if(col == 0){
			col = 'a';
		}else if(col == 1){
			col = 'b';
		}else if(col == 2){
			col = 'c';
		}else if(col == 3){
			col = 'd';
		}else if(col == 4){
			col = 'e';
		}else if(col == 5){
			col = 'f';
		}
		return col+Integer.toString(row+1);
		
	}
}
