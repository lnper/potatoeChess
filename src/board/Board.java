package board;

import java.util.Arrays;

public class Board {

	public static final boolean WhiteToMove = false;
	public static String chessBoard[][] = {}; 
	

	// Initialise le plateau 
	public static void initialize(){
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

	// Lit les mouvements executes dans Arena. La liste de mouvements recus par Arena sont deja isoles dans la classe UCI. Ici le but est de les etudier et de mettre a jour le chessboard
	public static void readMove(String move) {

		// On fait la tranformation pour que l'information puisse etre lue dans notre tableau
		move = moveToNum(move);

		//On transforme en int les donnees de move
		int aStart = Character.getNumericValue(move.charAt(0));
		int bStart = Character.getNumericValue(move.charAt(1));
		int aEnd = Character.getNumericValue(move.charAt(2));
		int bEnd = Character.getNumericValue(move.charAt(3));
		

		// On enregistre temporairement le contenu de la case de depart et on la vide
		String temp = chessBoard[aStart][bStart];
		chessBoard[aStart][bStart] = " ";

		// On place ca dans la case de destination
		//si il s'agit d'un mouvement contenant une promotion :
		if(move.length()==5){
			chessBoard[aEnd][bEnd] = move.substring(4);
		}
		else{
		chessBoard[aEnd][bEnd] = temp;
		}
	}

	
	
	// Permet de transformer un mouvement en nombre pour notre tableau. Par exemple : b2b3 => 6151. Cela sera compris par le mouvement chessBoard[6][1] => chessBoard[5][1]
	public static String moveToNum(String move) {

		String result = "";

		// Pour la position X de depart
		int asciiStart = (int) move.charAt(0);
		int positionXStart = asciiStart-97;
		String resultPositionXStart = String.valueOf(positionXStart);
		// Pour la position Y de depart
		int positionYStart = 8-Character.getNumericValue(move.charAt(1));
		String resultPositionYStart = String.valueOf(positionYStart);

		// Pour la position X d'arrivee
		int asciiEnd = (int) move.charAt(2);
		int positionXEnd = asciiEnd-97;
		String resultPositionXEnd = String.valueOf(positionXEnd);
		// Pour la position Y d'arrivee	
		int positionYEnd = 8-Character.getNumericValue(move.charAt(3));
		String resultPositionYEnd = String.valueOf(positionYEnd);
		
		//Si le mouvement contient une information de promotion
		String promot = "";
		if(move.length()==5){
			promot = move.substring(4);
		}

		result = resultPositionYStart+resultPositionXStart+resultPositionYEnd+resultPositionXEnd+promot;

		return result;
	}

	// Permet de transformer une action de notre moteur en un format classique. Par exemple : 6151 => b2b3.
	public static String numToMove(String num) {
		
		String result = "";

		//Pour la position A de depart
		int positionAStart = Character.getNumericValue(num.charAt(0));
		int valeurAStart = 8-Integer.valueOf(positionAStart);
		String resultAStart = String.valueOf(valeurAStart);
		
		// Pour la position B de depart
		int positionBStart = Character.getNumericValue(num.charAt(1))+97;
		char asciiBStart = (char) positionBStart;
		String resultBStart = Character.toString(asciiBStart);

		//Pour la position A d'arrivee
		int positionAEnd = Character.getNumericValue(num.charAt(2));
		int valeurAEnd = 8-Integer.valueOf(positionAEnd);
		String resultAEnd = String.valueOf(valeurAEnd);
		
		// Pour la position B de depart
		int positionBEnd = Character.getNumericValue(num.charAt(3))+97;
		char asciiBEnd = (char) positionBEnd;
		String resultBEnd = Character.toString(asciiBEnd);

		result = resultBStart + resultAStart + resultBEnd + resultAEnd;

		return result;
	}

	// Affiche l'etat du tableau
	public static void print() {
		for(int i = 0 ; i<chessBoard.length ; i++) {
			System.out.print("{");
			for(int j = 0 ; j<chessBoard.length ; j++) {
				if(j<chessBoard.length-1) System.out.print(chessBoard[i][j]+",");
				else System.out.print(chessBoard[i][j]);
			}
			System.out.println("} "+i);
		}
		System.out.println(" 0 1 2 3 4 5 6 7");

	}


	public static void importFEN(String input) {

	}


}
