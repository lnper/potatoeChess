package board;

public class Board {
	
	public String chessBoard[][]; 
	
	//initialise le plateau 
	public Board(){
		this.chessBoard = new String[][]{
			{"r","n","b","q","k","b","n","r"},
			{"p","p","p","p","p","p","p","p"},
			{" "," "," "," "," "," "," "," "},
			{" "," "," "," "," "," "," "," "},
			{" "," "," "," "," "," "," "," "},
			{" "," "," "," "," "," "," "," "},
			{"P","P","P","P","P","P","P","P"},
			{"R","N","B","Q","K","B","N","R"}};
	}


	public static void movePiece(String move) {

	}

	public static void readMove(String move) {
		
	}


	public static void print() {
		
	}


}
