package board;

<<<<<<< HEAD:src/board/ChessBoardGenerator.java
import java.util.Arrays;

import move.Moves;

public class ChessBoardGenerator {


	public static final boolean WhiteToMove = false;


	// Initialisation du plateau de jeu
	public static void initiateChessBoard() {
		String chessBoard[][]={
				{"r","n","b","q","k","b","n","r"},
				{"p","p","p","p","p","p","p","p"},
				{" "," "," "," "," "," "," "," "},
				{" "," "," "," "," "," "," "," "},
				{" "," "," "," "," "," "," "," "},
				{" "," "," "," "," "," "," "," "},
				{"P","P","P","P","P","P","P","P"},
				{"R","N","B","Q","K","B","N","R"}};
=======
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
>>>>>>> legalmoves:src/board/Board.java
	}


	public static void movePiece(String move) {

	}

	public static void readMove(String move) {
		
	}


	public static void print() {
		
	}


	public static void importFEN(String input) {
			
	}


}
