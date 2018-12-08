package board;

import java.util.Arrays;

import move.Moves;

public class ChessBoardGenerator {

	public static long WP=0L;
	public static long WN=0L;
	public static long WB=0L;
	public static long WR=0L;
	public static long WQ=0L;
	public static long WK=0L;
	public static long BP=0L;
	public static long BN=0L;
	public static long BB=0L;
	public static long BR=0L;
	public static long BQ=0L;
	public static long BK=0L;
	public static long EP=0L;
	public static boolean CWK = true, CWQ = true, CBK = true, CBQ = true, WhiteToMove = true;
	public static long UniversalWP = 0L, UniversalWN = 0L, UniversalWB = 0L, UniversalWR = 0L,
			UniversalWQ = 0L, UniversalWK = 0L, UniversalBP = 0L, UniversalBN = 0L,
			UniversalBB = 0L, UniversalBR = 0L, UniversalBQ = 0L, UniversalBK = 0L,
			UniversalEP = 0L;
	public static boolean UniversalCastleWK = true, UniversalCastleWQ = true,
			UniversalCastleBK = true, UniversalCastleBQ = true;

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
	}


	public static void movePiece(String move) {

	}

	public static void readMove(String move) {
		
	}


	public static void print() {
		
	}


}
