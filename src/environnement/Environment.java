package environnement;

import move.Evaluation;
import move.Moves;

import java.util.Arrays;

import communication.UCI;

//INFO : 
//Dans cette classe, on gère les infos reçu via l'UCI de la part d'Arena


public class Environment {

	private String chessBoard[][] = {}; 

	public Environment() {
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
	
	

	//Lit les mouvements transmis par Arena et les applique au chessBoard. 
	//La liste de mouvements recus par Arena sont deja isoles dans la classe UCI. 
	//Ici le but est de les etudier et de mettre a jour le chessboard
	public void applyMoveFromArena(String move) {

		// On fait la tranformation pour que l'information puisse etre lue dans notre tableau
		move = parseAmoveToCBmove(move);

		//On transforme en int les donnees de move
		int iStart = Character.getNumericValue(move.charAt(0)); //position i_départ
		int jStart = Character.getNumericValue(move.charAt(1)); //position j_départ
		int iEnd = Character.getNumericValue(move.charAt(2)); //position i_arrivé
		int jEnd = Character.getNumericValue(move.charAt(3)); //position j_arrivée

		//Si le mouvement est un petit roque ou un grand roque
		if(isCastlingMove(move)){
			System.out.println("roque");
			makeCastlingMove(iStart, jStart, iEnd, jEnd);
		}
		else{

			// On enregistre temporairement le contenu de la case de depart et on la vide
			String temp = chessBoard[iStart][jStart];
			this.chessBoard[iStart][jStart] = " ";

			//Puis on traite la case d'arivee
			//si il s'agit d'un mouvement contenant une promotion...
			if(move.charAt(5) != ' '){
				if(Character.isLowerCase(temp.charAt(0))) {
					//d'une pièce blanche, on place la piece promu correspondante à l'arrivee
					chessBoard[iEnd][jEnd] = Character.toString(move.charAt(5));
				}
				else {
					//d'une pièce noire, idem
					chessBoard[iEnd][jEnd] = Character.toString(Character.toUpperCase(move.charAt(5)));
				}
			//Si non, si c'est un mouvement classique, on place simplement la pièce à l'arrivee
			}
			else{
				chessBoard[iEnd][jEnd] = temp;
			}
		}
	}

	// Prend en entree un String move de la forme :
	//idepart jdepart iarrivee jarrivee piececapturee promotionverspiece
	
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

		//Si c'est un mouvement "classique" (sans promotion)
		if (prom == ' ') {
			// On place la piece dans la case de destination
			this.chessBoard[aEnd][bEnd] = temp;
		}

		//S'il s'agit d'une promotion
		else if (prom != ' ') {
			//On place la piece promue dans la case de destination
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

		// On enregistre temporairement le contenu de la case d'arrivee
		String temp = this.chessBoard[aEnd][bEnd];

		//Puis on distingue les 4 cas suivants:
		//Mouvement classique (sans capture ni promotion)
		//Capture sans promotion
		//Mouvement avec promotion
		//Capture et promotion

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

	//Cette fonction renvoie true si le mouvement en entrée concerne un roque
	public static boolean isCastlingMove(String move){
		return (move.equals("7476  ") || move.equals("0406  ") ||
				move.equals("7472  ") || move.equals("0402  "));
	}
	
	//Cette fonction effectue le mouvement de roque (petit ou grand)
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
	public static String parseAmoveToCBmove(String move) {

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
	public static String parseCBmoveToAmove(String num) {

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

	//Teste si on est dans une situation de fin de jeu 
	//(ie. plus de mouvement possible pour un des joueurs : echec et mat)
	public boolean gameOver() {
		return Moves.legalMove(this, true).isEmpty() || Moves.legalMove(this, false).isEmpty();
	}

	// Affiche l'etat du tableau dans la console
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

	//Accesseur du plateau
	public String[][] getChessBoard() {return this.chessBoard;}

}
