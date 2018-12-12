package board;

import move.Evaluation;
import move.Moves;

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
			{"r"," "," "," ","k"," "," ","r"},
			{"p","p","p","p","p","p","p","p"},
			{" "," "," "," "," "," "," "," "},
			{" "," "," "," "," "," "," "," "},
			{" "," "," "," "," "," "," "," "},
			{" "," "," "," "," "," "," "," "},
			{"P","P","P","P","P","P","P","P"},
			{"R"," "," "," ","K"," "," ","R"}};

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

		//Si le mouvement est un petit roque ou un grand roque
		if(isCastlingMove(move)){
			System.out.println("roque");
			makeCastlingMove(aStart, bStart, aEnd, bEnd);
		}
		else{

			// On enregistre temporairement le contenu de la case de depart et on la vide
			String temp = chessBoard[aStart][bStart];
			this.chessBoard[aStart][bStart] = " ";

			// On place ca dans la case de destination

			//si il s'agit d'un mouvement contenant une promotion :
			if(move.charAt(5) != ' '){
				if(Character.isLowerCase(temp.charAt(0))) {
					chessBoard[aEnd][bEnd] = Character.toString(move.charAt(5));
				}
				else {
					chessBoard[aEnd][bEnd] = Character.toString(Character.toUpperCase(move.charAt(5)));
				}

			}
			else{
				chessBoard[aEnd][bEnd] = temp;
			}
		}
	}

	// Prend en entree un String move de la forme idepart jdepart iarrivee jarrivee piececapturee promotionverspiece
	public void move(String move) {
		// Nous devons envoyer dans cette methode le formalisme en 6 donnees

		int aStart = Character.getNumericValue(move.charAt(0));
		int bStart = Character.getNumericValue(move.charAt(1));
		int aEnd = Character.getNumericValue(move.charAt(2));
		int bEnd = Character.getNumericValue(move.charAt(3));
		char capt = move.charAt(4);
		char prom = move.charAt(5);

		// On enregistre temporairement le contenu de la case de depart et on la vide
		String temp = this.chessBoard[aStart][bStart];
		this.chessBoard[aStart][bStart] = " ";

		if (prom == ' ') {
			// On place ca dans la case de destination
			this.chessBoard[aEnd][bEnd] = temp;
		}

		else if (prom != ' ') {
			this.chessBoard[aEnd][bEnd] = Character.toString(prom);
		}
	}


	// Prend en entree un String move de la forme idepart jdepart iarrivee jarrivee piececapturee promotionverspiece
	public void undoMove(String move) {

		// Nous devons envoyer dans cette methode le formalisme en 6 donnees

		int aStart = Character.getNumericValue(move.charAt(0));
		int bStart = Character.getNumericValue(move.charAt(1));
		int aEnd = Character.getNumericValue(move.charAt(2));
		int bEnd = Character.getNumericValue(move.charAt(3));
		char capt = move.charAt(4);
		char prom = move.charAt(5);

		// On enregistre temporairement le contenu de la case d'arrivee et on la vide
		String temp = this.chessBoard[aEnd][bEnd];
		//this.chessBoard[aEnd][bEnd] = " ";

		if (capt == ' ' && prom == ' ') {
			this.chessBoard[aStart][bStart] = temp;
			this.chessBoard[aEnd][bEnd] = " ";
		}

		else if (capt != ' ' && prom == ' ') {
			this.chessBoard[aStart][bStart] = temp;
			this.chessBoard[aEnd][bEnd] = Character.toString(capt);
		}

		else if (capt == ' ' && prom != ' ') {
			if(Character.isUpperCase(prom)) {
				this.chessBoard[aEnd][bEnd] = " ";
				this.chessBoard[aStart][bStart] = "P";
			}
			else if(Character.isLowerCase(prom)) {
				this.chessBoard[aEnd][bEnd] = " ";
				this.chessBoard[aStart][bStart] = "p";
			}
		}

		else if (capt != ' ' && prom != ' ') {
			this.chessBoard[aEnd][bEnd] = Character.toString(capt);
			if(Character.isUpperCase(prom)) {
				this.chessBoard[aStart][bStart] = "P";
			}
			else if(Character.isLowerCase(prom)) {
				this.chessBoard[aStart][bStart] = "p";
			}
		}
	}

	// Permet de detecter les pieces du plateau, a continuer...
	public static void importFEN(String fenString) {


	}

	//Roque :
	public static boolean isCastlingMove(String move){
		return (move.equals("7476  ") || move.equals("0406  ") ||
				move.equals("7472  ") || move.equals("0402  "));
	}
	
	public void makeCastlingMove(int aStart, int bStart, int aEnd, int bEnd){
		this.chessBoard[aEnd][bEnd] = this.chessBoard[aStart][bStart]; //position du roi
		this.chessBoard[aStart][bStart] = " "; //ancienne position du roi
		if(bEnd == 6) // dans le cas d'un petit roque
		{
			this.chessBoard[aEnd][bEnd-1] = this.chessBoard[aEnd][7]; //position de la tour
			this.chessBoard[aEnd][7] = " "; //ancienneposition de la tour
		}
		else{ //sinon, si c'est un grand roque
			this.chessBoard[aEnd][bEnd+1] = this.chessBoard[aEnd][0]; //position de la tour
			this.chessBoard[aEnd][0] = " ";
		}	
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
		String promot = " ";
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

	public boolean gameOver() {
		return Moves.legalMove(this, true).isEmpty() || Moves.legalMove(this, false).isEmpty();
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
