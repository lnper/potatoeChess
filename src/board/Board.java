package board;

public class Board {
	
	public static final boolean WhiteToMove = false;
	public static String chessBoard[][] = {}; 
	
	// Initialise le plateau 
	public Board(){
		chessBoard = new String[][]{
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
		for(int i = 0 ; i<chessBoard.length ; i++) {
			System.out.print("{");
			for(int j = 0 ; j<chessBoard.length ; j++) {
				if(j<chessBoard.length-1) System.out.print(chessBoard[i][j]+",");
				else System.out.print(chessBoard[i][j]);
			}
			System.out.println("}");
		}
		
	}


	public static void importFEN(String input) {
			
	}


}
