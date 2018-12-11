package move;

import board.Board;

/* 
 * Nous attribuons un score pour chaque joueur en fonction :
 * - de la valeur de chaque piece presentes sur le plateau
 * - de la position de chaque piece presentes sur le plateau
 * 
 * L'evaluation porte sur un plateau tel qu'il est au moment de l'appel de la methode evaluate()
 * Il pourrait etre ajoute un but de recherche d'echec au roi, ici pas implemente
 */

public class Evaluation {

	// Permet d'affiner la position du roi en fonction de l'avancement de la partie
	public static boolean ENDGAME = false;

	
	/*
	 * Evalue le plateau a un etat donne et y attribu un score pour les blancs (consideres en bas) dans le premier terme, et les noirs (consideres en haut) dans le second
	 * evaluate [0] = score en bas (blancs)
	 * evaluate [1] = score en haut (noirs)
	*/ 
	public static int evaluate(Board board, boolean player) {

		// Analyse et attribution des points
		int[] analyse = analyse(board);
		int pointsPiecesWhite = calculatePointsPiecesWhite(analyse);
		int pointsPiecesBlack = calculatePointsPiecesBlack(analyse);
		int pointsPositionWhite = calculatePointsPositionWhite(analyse);
		int pointsPositionBlack = calculatePointsPositionBlack(analyse);
		
		int resultWhite = pointsPiecesWhite + pointsPositionWhite;
		int resultBlack = pointsPiecesBlack + pointsPositionBlack;
		
		if(player) return resultWhite - resultBlack;
		else return resultBlack - resultWhite;
	}




	// Permet de calculer le nombre de pieces restantes sur le plateau => liste d'elements avec en premier le nombre de pieces blanches et en deuxieme le nombre de pieces noires
	public static int [] analyse(Board b) {

		// Chargement unique du plateau (puisqu'il ne change pas pendant l'evaluation)
		String [][] plateau = b.getChessBoard();

		int [] result = new int [26] ;

		// Compter le nombre de pieces pour chaque couleur
		int comptBlack = 0;
		int comptWhite = 0;

		// Compter les pieces specifiques pour chaque couleur
		int comptr = 0;
		int comptn = 0;
		int comptb = 0;
		int comptq = 0;
		int comptk = 0;
		int comptp = 0;

		int comptR = 0;
		int comptN = 0;
		int comptB = 0;
		int comptQ = 0;
		int comptK = 0;
		int comptP = 0;


		// Compter les points pour chaque piece
		int comptPointsr = 0;
		int comptPointsn = 0;
		int comptPointsb = 0;
		int comptPointsq = 0;
		int comptPointsk = 0;
		int comptPointsp = 0;

		int comptPointsR = 0;
		int comptPointsN = 0;
		int comptPointsB = 0;
		int comptPointsQ = 0;
		int comptPointsK = 0;
		int comptPointsP = 0;


		for(int i = 0; i<plateau.length ; i++) {
			for(int j = 0 ; j<plateau.length ; j++) {
				if(plateau[i][j] == "r" || plateau[i][j] == "n" || plateau[i][j] == "b" || plateau[i][j] == "q" || plateau[i][j] == "k" || plateau[i][j] == "p") {
					comptBlack++;
					if(plateau[i][j] == "r") {
						comptr++;
						comptPointsr += ROOK[7-i][7-j];
					}
					if(plateau[i][j] == "n") {
						comptn++;
						comptPointsn += KNIGHT[7-i][7-j];
					}
					if(plateau[i][j] == "b") {
						comptb++;
						comptPointsb += BISHOP[7-i][7-j];
					}
					if(plateau[i][j] == "q") {
						comptq++;
						comptPointsq += QUEEN[7-i][7-j];
					}
					if(plateau[i][j] == "k") {
						comptk++;
						if(ENDGAME) comptPointsk += KING_ENDGAME[7-i][7-j];
						else comptPointsk += KING[7-i][7-j];
					}
					if(plateau[i][j] == "p") {
						comptp++;
						comptPointsp += PAWN[7-i][7-j];
					}
				}
				else if(plateau[i][j] == "R" || plateau[i][j] == "N" || plateau[i][j] == "B" || plateau[i][j] == "Q" || plateau[i][j] == "K" || plateau[i][j] == "P") {
					comptWhite++;
					if(plateau[i][j] == "R") {
						comptR++;
						comptPointsR += ROOK[i][j];
					}
					if(plateau[i][j] == "N") {
						comptN++;
						comptPointsN += KNIGHT[i][j];
					}
					if(plateau[i][j] == "B") {
						comptB++;
						comptPointsB += BISHOP[i][j];
					}
					if(plateau[i][j] == "Q") {
						comptQ++;
						comptPointsQ += QUEEN[i][j];
					}
					if(plateau[i][j] == "K") {
						comptK++;
						if(ENDGAME) comptPointsK += KING_ENDGAME[i][j];
						else comptPointsK += KING[i][j];
					}
					if(plateau[i][j] == "P") {
						comptP++;
						comptPointsP += PAWN[i][j];
					}
				}
			}
		}

		result[0] = comptWhite;
		result[1] = comptBlack;
		result[2] = comptR;
		result[3] = comptN;
		result[4] = comptB;
		result[5] = comptQ;
		result[6] = comptK;
		result[7] = comptP;
		result[8] = comptr;
		result[9] = comptn;
		result[10] = comptb;
		result[11] = comptq;
		result[12] = comptk;
		result[13] = comptp;

		result[14] = comptPointsR;
		result[15] = comptPointsN;
		result[16] = comptPointsB;
		result[17] = comptPointsQ;
		result[18] = comptPointsK;
		result[19] = comptPointsP;
		result[20] = comptPointsr;
		result[21] = comptPointsn;
		result[22] = comptPointsb;
		result[23] = comptPointsq;
		result[24] = comptPointsk;
		result[25] = comptPointsp;

		return result;
	}


	/*
	 * Calculer les points des pieces en fonction de l'etat du plateau
	 */

	private static int calculatePointsPiecesWhite(int [] analyse) {

		int result = 0;

		int comptR = analyse[2];
		int comptN = analyse[3];
		int comptB = analyse[4];
		int comptQ = analyse[5];
		int comptK = analyse[6];
		int comptP = analyse[7];

		int valueR = 50;
		int valueN = 30;
		int valueB = 30;
		int valueQ = 90;
		int valueK = 1000;
		int valueP = 10;

		result += comptR*valueR;
		result += comptN*valueN;
		result += comptB*valueB;
		result += comptQ*valueQ;
		result += comptK*valueK;
		result += comptP*valueP;

		return result;
	}

	private static int calculatePointsPiecesBlack(int [] analyse) {

		int result = 0;

		int comptr = analyse[8];
		int comptn = analyse[9];
		int comptb = analyse[10];
		int comptq = analyse[11];
		int comptk = analyse[12];
		int comptp = analyse[13];

		int valuer = 50;
		int valuen = 30;
		int valueb = 30;
		int valueq = 90;
		int valuek = 1000;
		int valuep = 10;

		result += comptr*valuer;
		result += comptn*valuen;
		result += comptb*valueb;
		result += comptq*valueq;
		result += comptk*valuek;
		result += comptp*valuep;

		return result;
	}



	private static int calculatePointsPositionWhite(int[] analyse) {
		
		int result = 0;
		
		int pointsR = analyse[14];
		int pointsN = analyse[15];
		int pointsB = analyse[16];
		int pointsQ = analyse[17];
		int pointsK = analyse[18];
		int pointsP = analyse[19];
		
		result = pointsR + pointsN + pointsB + pointsQ + pointsK + pointsP;
		
		return result;
	}
	
	
	private static int calculatePointsPositionBlack(int[] analyse) {
		
		int result = 0;
		
		int pointsr = analyse[20];
		int pointsn = analyse[21];
		int pointsb = analyse[22];
		int pointsq = analyse[23];
		int pointsk = analyse[24];
		int pointsp = analyse[25];
		
		result = pointsr + pointsn + pointsb + pointsq + pointsk + pointsp;
		
		return result;
	}

	/*
	 * Estimation des positions a favoriser durant la partie
	 */

	public static int[][] KING = {
			{-30, -40, -40, -50, -50, -40, -40, -30},
			{-30, -40, -40, -50, -50, -40, -40, -30},
			{-30, -40, -40, -50, -50, -40, -40, -30},
			{-30, -40, -40, -50, -50, -40, -40, -30},
			{-20, -30, -30, -40, -40, -30, -30, -20},
			{-10, -20, -20, -20, -20, -20, -20, -10},
			{ 20,  20,   0,   0,   0,   0,  20,  20},
			{ 20,  30,  10,   0,   0,  10,  30,  20}};

	public static int[][] KING_ENDGAME = {
			{-50, -40, -30, -20, -20, -30, -40, -50},
			{-30, -20, -10,   0,   0, -10, -20, -30},
			{-30, -10,  20,  30,  30,  20, -10, -30},
			{-30, -10,  30,  40,  40,  30, -10, -30},
			{-30, -10,  30,  40,  40,  30, -10, -30},
			{-30, -10,  20,  30,  30,  20, -10, -30},
			{-30, -30,   0,   0,   0,   0, -30, -30},
			{-50, -30, -30, -30, -30, -30, -30, -50}};

	public static int[][] QUEEN = {
			{-20, -10, -10, -5, -5, -10, -10, -20},
			{-10,   0,   0,  0,  0,   0,   0, -10},
			{-10,   0,   5,  5,  5,   5,   0, -10},
			{- 5,   0,   5,  5,  5,   5,   0, - 5},
			{  0,   0,   5,  5,  5,   5,   0, - 5},
			{-10,   5,   5,  5,  5,   5,   0, -10},
			{-10,   0,   5,  0,  0,   0,   0, -10},
			{-20, -10, -10, -5, -5, -10, -10, -20}};

	public static int[][] ROOK  = {
			{ 0,  0,  0,  0,  0,  0,  0,  0},
			{ 5, 10, 10, 10, 10, 10, 10,  5},
			{-5,  0,  0,  0,  0,  0,  0, -5},
			{-5,  0,  0,  0,  0,  0,  0, -5},
			{-5,  0,  0,  0,  0,  0,  0, -5},
			{-5,  0,  0,  0,  0,  0,  0, -5},
			{-5,  0,  0,  0,  0,  0,  0, -5},
			{ 0,  0,  0,  5,  5,  0,  0,  0}};

	public static int[][] BISHOP = {
			{-20, -10, -10, -10, -10, -10, -10, -20},
			{-10,   0,   0,   0,   0,   0,   0, -10},
			{-10,   0,   5,  10,  10,   5,   0, -10},
			{-10,   5,   5,  10,  10,   5,   5, -10},
			{-10,   0,  10,  10,  10,  10,   0, -10},
			{-10,  10,  10,  10,  10,  10,  10, -10},
			{-10,   5,   0,   0,   0,   0,   5, -10},
			{-20, -10, -10, -10, -10, -10, -10, -20}};

	public static int[][] KNIGHT = {
			{-50, -40, -30, -30, -30, -30, -40, -50},
			{-40, -20,   0,   0,   0,   0, -20, -40},
			{-30,   0,  10,  15,  15,  10,   0, -30},
			{-30,   5,  15,  20,  20,  15,   5, -30},
			{-30,   0,  15,  20,  20,  15,   0, -30},
			{-30,   5,  10,  15,  15,  10,   5, -30},
			{-40, -20,   0,   5,   5,   0, -20, -40},
			{-50, -40, -30, -30, -30, -30, -40, -50}};

	public static int[][] PAWN = {
			{0,   0,   0,   0,   0,   0,  0,  0},
			{50, 50,  50,  50,  50,  50, 50, 50},
			{10, 10,  20,  30,  30,  20, 10, 10},
			{5,   5,  10,  25,  25,  10,  5,  5},
			{0,   0,   0,  20,  20,   0,  0,  0},
			{5,  -5, -10,   0,   0, -10, -5,  5},
			{5,  10,  10, -20, -20,  10, 10,  5},
			{0,   0,   0,   0,   0,   0,  0,  0}};


}
