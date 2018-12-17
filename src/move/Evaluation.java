package move;

import environnement.Environment;

/* 
 * Nous attribuons un score pour chaque joueur en fonction :
 * - de la valeur de chaque piece presentes sur le plateau
 * - de la position de chaque piece presentes sur le plateau
 * 
 * L'evaluation porte sur un plateau tel qu'il est au moment de l'appel de la methode evaluate()
 * Il pourrait etre ajoute un but de recherche d'echec au roi, ici pas implemente
 */

public class Evaluation {
	/*
	 * Evalue le plateau a un etat donne et y attribue un score pour les blancs et les noirs 
	 * Calcul la différence entre ces deux score en fonction du jour (booleen isWhite)
	 * Cette evaluation sera ensuite utilisée pour étiqueter les noeuds lors de MiniMax
	 */
	public static int evaluate(Environment board, boolean isWhite) {

		// Analyse et attribution des points
		int[] analyse = analyse(board);
		int whitePiecesPoints = calculatePointsPiecesWhite(analyse);
		int blackPiecesPoints = calculateBlackPiecesPoints(analyse);
		int whitePositionPoints = calculateWhitePositionPoints(analyse);
		int blackPositionPoints = calculateBlackPositionPoints(analyse);

		int whiteResult = whitePiecesPoints + whitePositionPoints;
		int blackResult = blackPiecesPoints + blackPositionPoints;

		if(isWhite) return whiteResult - blackResult;
		else return blackResult - whiteResult;
	}




	// Permet de calculer le nombre de pieces restantes sur le plateau => liste d'elements avec en premier le nombre de pieces blanches et en deuxieme le nombre de pieces noires
	public static int [] analyse(Environment b) {

		String [][] plateau = b.getChessBoard();

		int [] result = new int [26] ;
		//result[i] contient : 
		//i=0 : le nombre de pieces blanches encore en jeu
		//i=1 : le nombre de pieces noires encore en jeu (Nous ne prenons finalement pas se core en compte pour notre heuristique)
		//i=2 à 7 : le nombre de pieces blanches de chaque type (R,N,B,Q,K, P)
		//i=8 à 13 : le nombre de pieces noires de chaque type (r, n, b, q, k, p)
		//i=14 à 19 : le nombre de point par type de pièces blanches en fonction de leur positions 
		//i=20 à 25 : le nombre de point par type de pièces noires en fonction de leur poisitions

		for(int i=0; i<8; i++){
			for(int j=0; j<8; j++){
				switch(plateau[i][j]){
				case "R":
					result[0]++; //on incrémente le nombre de pièces blanches encore en jeu
					result[2]++; //on incrémente le nombre de tours blanches encore en jeu
					result[14] += rookPointsAccordingToPos[i][j];
					break;
				case "N":
					result[0]++; //on incrémente le nombre de pièces blanches encore en jeu
					result[3]++; //on incrémente le nombre de cavaliers blancs encore en jeu
					result[15] += knightPointsAccordingToPos[i][j];
					break;
				case "B":
					result[0]++; //on incrémente le nombre de pièces blanches encore en jeu
					result[4]++; //on incrémente le nombre de fous blancs encore en jeu
					result[16] += bishopPointsAccordingToPos[i][j];
					break;
				case "Q":
					result[0]++; //on incrémente le nombre de pièces blanches encore en jeu
					result[5]++; //on incrémente le nombre de reines blanches encore en jeu
					result[17] += queenPointsAccordingToPos[i][j];
					break;
				case "K":
					result[0]++; //on incrémente le nombre de pièces blanches encore en jeu
					result[6]++; //on incrémente le nombre de rois blancs encore en jeu
					result[18] += kingPointsAccordingToPos[i][j];
					break;
				case "P":
					result[0]++; //on incrémente le nombre de pièces blanches encore en jeu
					result[7]++; //on incrémente le nombre de pions blancs encore en jeu
					result[19] += pawnPointsAccordingToPos[i][j];
					break;
				case "r":
					result[1]++; //on incrémente le nombre de pièces noires encore en jeu
					result[8]++; //on incrémente le nombre de tours noires encore en jeu
					result[20] += rookPointsAccordingToPos[7-i][7-j];
					break;
				case "n":
					result[1]++; //on incrémente le nombre de pièces noires encore en jeu
					result[9]++; //on incrémente le nombre de cavaliers noirs encore en jeu
					result[21] += knightPointsAccordingToPos[7-i][7-j];
					break;
				case "b":
					result[1]++; //on incrémente le nombre de pièces noires encore en jeu
					result[10]++; //on incrémente le nombre de fous noirs encore en jeu
					result[22] += bishopPointsAccordingToPos[7-i][7-j];
					break;
				case "q":
					result[1]++; //on incrémente le nombre de pièces noires encore en jeu
					result[11]++; //on incrémente le nombre de reines noires encore en jeu
					result[23] += queenPointsAccordingToPos[7-i][7-j];
					break;
				case "k":
					result[1]++; //on incrémente le nombre de pièces noires encore en jeu
					result[12]++; //on incrémente le nombre de rois noirs encore en jeu
					result[24] += kingPointsAccordingToPos[7-i][7-j];
					break;
				case "p":
					result[1]++; //on incrémente le nombre de pièces noires encore en jeu
					result[13]++; //on incrémente le nombre de pions noirs encore en jeu
					result[25] += pawnPointsAccordingToPos[7-i][7-j];
					break;
				}
			}
		}

		return result;
	}


	/*
	 * Calculer les points des pieces en fonction de l'etat du plateau
	 */
	
	//valeurs de chaques pieces :
	//Tours : 50
	//Cavaliers: 30
	//Fous : 30
	//Reines : 90
	//Rois : 1000
	//Pions : 10

	//https://fr.wikipedia.org/wiki/Valeur_relative_des_pi%C3%A8ces_d%27%C3%A9checs
	
	private static int calculatePointsPiecesWhite(int [] analyse) {

		int result = 0;
		
		//on stocke les valeurs de chaque pieces ans un tableau:
		int[] piecesValue = new int[]{50, 30, 30, 90, 1000, 10};

		//On pondère le nombre de chaque pièces par la valeur respective de la piece
		for(int i=2; i<8; i++){
			result += analyse[i]*piecesValue[i-2];
		}

		return result;

	}

	private static int calculateBlackPiecesPoints(int [] analyse) {

		int result = 0;

		int[] piecesValue = new int[]{50, 30, 30, 90, 1000, 10};

		//On pondère le nombre de chaque pièces par la valeur respective de la piece
		for(int i=8; i<14; i++){
			result += analyse[i]*piecesValue[i-8];
		}
		
		return result;
	}



	private static int calculateWhitePositionPoints(int[] analyse) {

		int result = 0;
		
		for(int i=14; i<20; i++){
			result += analyse[i];
		}

		return result;
	}


	private static int calculateBlackPositionPoints(int[] analyse) {

		int result = 0;
		
		for(int i=20; i<26; i++){
			result += analyse[i];
		}

		return result;
	}

	
	/*
	 * Estimation des positions a favoriser durant la partie
	 */
	//https://www.chessprogramming.org/Simplified_Evaluation_Function

	public static int[][] kingPointsAccordingToPos = {
			{-30, -40, -40, -50, -50, -40, -40, -30},
			{-30, -40, -40, -50, -50, -40, -40, -30},
			{-30, -40, -40, -50, -50, -40, -40, -30},
			{-30, -40, -40, -50, -50, -40, -40, -30},
			{-20, -30, -30, -40, -40, -30, -30, -20},
			{-10, -20, -20, -20, -20, -20, -20, -10},
			{ 20,  20,   0,   0,   0,   0,  20,  20},
			{ 20,  30,  10,   0,   0,  10,  30,  20}};

	public static int[][] queenPointsAccordingToPos = {
			{-20, -10, -10, -5, -5, -10, -10, -20},
			{-10,   0,   0,  0,  0,   0,   0, -10},
			{-10,   0,   5,  5,  5,   5,   0, -10},
			{- 5,   0,   5,  5,  5,   5,   0, - 5},
			{  0,   0,   5,  5,  5,   5,   0, - 5},
			{-10,   5,   5,  5,  5,   5,   0, -10},
			{-10,   0,   5,  0,  0,   0,   0, -10},
			{-20, -10, -10, -5, -5, -10, -10, -20}};

	public static int[][] rookPointsAccordingToPos  = {
			{ 0,  0,  0,  0,  0,  0,  0,  0},
			{ 5, 10, 10, 10, 10, 10, 10,  5},
			{-5,  0,  0,  0,  0,  0,  0, -5},
			{-5,  0,  0,  0,  0,  0,  0, -5},
			{-5,  0,  0,  0,  0,  0,  0, -5},
			{-5,  0,  0,  0,  0,  0,  0, -5},
			{-5,  0,  0,  0,  0,  0,  0, -5},
			{ 0,  0,  0,  5,  5,  0,  0,  0}};

	public static int[][] bishopPointsAccordingToPos = {
			{-20, -10, -10, -10, -10, -10, -10, -20},
			{-10,   0,   0,   0,   0,   0,   0, -10},
			{-10,   0,   5,  10,  10,   5,   0, -10},
			{-10,   5,   5,  10,  10,   5,   5, -10},
			{-10,   0,  10,  10,  10,  10,   0, -10},
			{-10,  10,  10,  10,  10,  10,  10, -10},
			{-10,   5,   0,   0,   0,   0,   5, -10},
			{-20, -10, -10, -10, -10, -10, -10, -20}};

	public static int[][] knightPointsAccordingToPos = {
			{-50, -40, -30, -30, -30, -30, -40, -50},
			{-40, -20,   0,   0,   0,   0, -20, -40},
			{-30,   0,  10,  15,  15,  10,   0, -30},
			{-30,   5,  15,  20,  20,  15,   5, -30},
			{-30,   0,  15,  20,  20,  15,   0, -30},
			{-30,   5,  10,  15,  15,  10,   5, -30},
			{-40, -20,   0,   5,   5,   0, -20, -40},
			{-50, -40, -30, -30, -30, -30, -40, -50}};

	public static int[][] pawnPointsAccordingToPos = {
			{0,   0,   0,   0,   0,   0,  0,  0},
			{50, 50,  50,  50,  50,  50, 50, 50},
			{10, 10,  20,  30,  30,  20, 10, 10},
			{5,   5,  10,  25,  25,  10,  5,  5},
			{0,   0,   0,  20,  20,   0,  0,  0},
			{5,  -5, -10,   0,   0, -10, -5,  5},
			{5,  10,  10, -20, -20,  10, 10,  5},
			{0,   0,   0,   0,   0,   0,  0,  0}};

}
