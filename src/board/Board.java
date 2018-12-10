package board;

import move.Evaluation;

import java.util.Arrays;

import communication.UCI;

public class Board {

	private String chessBoard[][] = {}; 

	public Board() {
		initialize();
	}
	// Initialise le plateau 
	public void initialize(){
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
	public void readMove(String move) {

		// On fait la tranformation pour que l'information puisse etre lue dans notre tableau
		move = moveToNum(move);

		//On transforme en int les donnees de move
		int aStart = Character.getNumericValue(move.charAt(0));
		int bStart = Character.getNumericValue(move.charAt(1));
		int aEnd = Character.getNumericValue(move.charAt(2));
		int bEnd = Character.getNumericValue(move.charAt(3));
		

		// On enregistre temporairement le contenu de la case de depart et on la vide
		String temp = chessBoard[aStart][bStart];
		this.chessBoard[aStart][bStart] = " ";

		// On place ca dans la case de destination
		
		//si il s'agit d'un mouvement contenant une promotion :
		if(move.length()==5){
			chessBoard[aEnd][bEnd] = move.substring(4);
		}
		else{
		chessBoard[aEnd][bEnd] = temp;
		}
	}

	// Prend en entree un String move de la forme idepart jdepart iarrivee jarrivee piececapturee promotionverspiece
	public void move(String move) {
		// Nous devons envoyer dans cette methode le formalisme en 6 donnees
		if(move.length() == 6) {
			int aStart = Character.getNumericValue(move.charAt(0));
			int bStart = Character.getNumericValue(move.charAt(1));
			int aEnd = Character.getNumericValue(move.charAt(2));
			int bEnd = Character.getNumericValue(move.charAt(3));
			String capt = String.valueOf(move.charAt(4));
			String prom = String.valueOf(move.charAt(5));

			// On enregistre temporairement le contenu de la case de depart et on la vide
			String temp = this.chessBoard[aStart][bStart];
			this.chessBoard[aStart][bStart] = " ";

			if (prom == " ") {
				// On place ca dans la case de destination
				this.chessBoard[aEnd][bEnd] = temp;
			}
			
			else if (prom != " ") {
				this.chessBoard[aEnd][bEnd] = prom;
			}
		}
	}


	// Prend en entree un String move de la forme idepart jdepart iarrivee jarrivee piececapturee promotionverspiece
	public void undoMove(String move) {

		// Nous devons envoyer dans cette methode le formalisme en 6 donnees
		
			int aStart = Character.getNumericValue(move.charAt(0));
			int bStart = Character.getNumericValue(move.charAt(1));
			int aEnd = Character.getNumericValue(move.charAt(2));
			int bEnd = Character.getNumericValue(move.charAt(3));
			String capt = String.valueOf(move.charAt(4));
			String prom = String.valueOf(move.charAt(5));

			// On enregistre temporairement le contenu de la case d'arrivee et on la vide
			String temp = this.chessBoard[aEnd][bEnd];
			this.chessBoard[aEnd][bEnd] = " ";
			
			if (capt == " " && prom == " ") {
				this.chessBoard[aStart][bStart] = temp;
			}
			
			else if (capt != " " && prom == " ") {
				this.chessBoard[aEnd][bEnd] = capt;
			}
			
			else if (capt == " " && prom != " ") {
				if(prom == prom.toUpperCase()) {
					this.chessBoard[aStart][bStart] = "P";
				}
				else if(prom == prom.toLowerCase()) {
					this.chessBoard[aStart][bStart] = "p";
				}
			}
			
			else if (capt != " " && prom != " ") {
				this.chessBoard[aEnd][bEnd] = capt;
				if(prom == prom.toUpperCase()) {
					this.chessBoard[aStart][bStart] = "P";
				}
				else if(prom == prom.toLowerCase()) {
					this.chessBoard[aStart][bStart] = "p";
				}
			}
		}

	// Permet de detecter les pieces du plateau, a continuer...
	public static void importFEN(String fenString) {


	}


	// Permet de transformer un mouvement en nombre pour notre tableau. Par exemple : b2b3 => 1615. Cela sera compris par le mouvement chessBoard[6][1] => chessBoard[5][1]
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

		result = resultPositionYStart+resultPositionXStart+resultPositionYEnd+resultPositionXEnd+" "+promot;

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
	public void print() {
		for(int i = 0 ; i<this.chessBoard.length ; i++) {
			System.out.print("{");
			for(int j = 0 ; j<this.chessBoard.length ; j++) {
				if(j<this.chessBoard.length-1) System.out.print(this.chessBoard[i][j]+",");
				else System.out.print(this.chessBoard[i][j]);
			}
			System.out.println("} "+i);
		}
		System.out.println(" 0 1 2 3 4 5 6 7");

		System.out.println("");
		System.out.println("Evaluation des points blancs : "+Evaluation.evaluate(this, true));
		System.out.println("Evaluation des points noirs : "+Evaluation.evaluate(this, false));
	}

	public String[][] getChessBoard() {return this.chessBoard;}

}
