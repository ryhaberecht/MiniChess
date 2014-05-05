
public class Square {
	public int col;
	public int row;
	
	Square(String command){
		char row = command.charAt(0);
		this.col = Integer.getInteger(command);
		if(row == 'a'){
			this.row = 1;
		}else if(row == 'b'){
			this.row = 2;
		}else if(row == 'c'){
			this.row = 3;
		}else if(row == 'd'){
			this.row = 4;
		}else if(row == 'e'){
			this.row = 5;
		}else if(row == 'f'){
			this.row = 6;
		}
	}
	
	Square(int col, int row){
		this.col = col;
		this.row = row;
	}
	
	public String toString(){
		char row = '\u0000';
		
		if(row == 1){
			row = 'a';
		}else if(row == 2){
			row = 'b';
		}else if(row == 3){
			row = 'c';
		}else if(row == 4){
			row = 'd';
		}else if(row == 5){
			row = 'e';
		}else if(row == 6){
			row = 'f';
		}
	}
}
