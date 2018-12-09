package board;

import java.util.Arrays;

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


	public void print() {
		 for (int i=0;i<8;i++) {
				 System.out.println(Arrays.toString(chessBoard[i])+" "+i); 
	        }
		 	System.out.println(" 0  1  2  3  4  5  6  7 ");
	}


}
