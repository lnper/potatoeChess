package move;

import java.util.ArrayList;

import environnement.Environment;

public class Moves {

	public static ArrayList<String> legalMoves(Environment board, boolean isWhite){
		ArrayList<String> possibleMoveList= new ArrayList<String>();
		//On parcours le plateau pour ajouter a chaque fois les mouvements possibles
		String[][] chessBoard = board.getChessBoard();
		String[] pieces = myPieces(isWhite);


		for(int i=0; i<8; i++){
			for(int j=0; j<8; j++){
				
				String pieceAnalysee = chessBoard[i][j];
				
				if(pieceAnalysee.equals(pieces[0])) //Si c'est un pion
					possibleMoveList.addAll(legalMovesPawn(board, isWhite, i, j));
				
				else if(pieceAnalysee.equals(pieces[1])) //Si c'est une tour
					possibleMoveList.addAll(legalMovesRook(board, isWhite, i, j));
				
				else if(pieceAnalysee.equals(pieces[2])) //Si c'est un cavalier
					possibleMoveList.addAll(legalMovesKnight(board, isWhite, i, j));
				
				else if(pieceAnalysee.equals(pieces[3])) //si c'est un fou
					possibleMoveList.addAll(legalMovesBishop(board, isWhite, i, j));
				
				else if(pieceAnalysee.equals(pieces[4])) //Si c'est une reine
					possibleMoveList.addAll(legalMovesQueen(board, isWhite, i, j));
				
				else if(pieceAnalysee.equals(pieces[5])) //Si c'est un roi
					possibleMoveList.addAll(legalMovesKing(board, isWhite, i, j));
			}
		}
		return possibleMoveList;
	}

	//génère la liste des pieces du joueur
	public static String[] myPieces(boolean isWhite){
		if(isWhite)
			return new String[]{"P","R","N","B","Q","K"};
		return new String[]{"p","r","n","b","q","k"};

	}

	//mouvements legaux des pions en position i,j
	public static ArrayList<String> legalMovesPawn(Environment board, boolean isWhite, int i, int j){
		ArrayList<String> possiblePawnMoves = new ArrayList<>();
		String[][] chessBoard = board.getChessBoard();


		boolean firstPawnMove; //Est ce que le pion a boujé de sa pos initiale
		boolean penultimateLane; //Est ce que le pion est sur l'avant derniere ligne?
		boolean basicMoveArea; //Zone ou les pions n'avance que d'une case
		int moveDirection; //Direction de déplacement du pion (-1 blanc, 1 noir)
		int lastLane; //Est ce que le pion est sur la ligne de promotion
		String[] promot;
		if(isWhite){ //On règle les paramètres en fonction de la couleur du pion
			firstPawnMove=(i==6);
			penultimateLane=(i==1);
			basicMoveArea=(i>=2);
			moveDirection=-1;
			lastLane=0;
			promot=new String[]{"Q","R","B","K"};
		}
		else{
			firstPawnMove=(i==1);
			penultimateLane=(i==6);
			basicMoveArea=(i<=5);
			moveDirection=1;
			lastLane=7;
			promot=new String[]{"q","r","b","k"};
		}
		//mouvement 2 en avant
		if(firstPawnMove && isEmptyCase(chessBoard[i+moveDirection][j]) && isEmptyCase(chessBoard[i+2*moveDirection][j])){
			if(simulateMoveForKingCheck(board, isWhite, i, j, i+2*moveDirection, j)){
				possiblePawnMoves.add(""+i+j+(i+2*moveDirection)+(j)+" "+" ");
			}
		}
	
		//mouvement de 1 en avant sans promotion
		if(basicMoveArea && isEmptyCase(chessBoard[i+moveDirection][j])){
			if(simulateMoveForKingCheck(board, isWhite, i, j, i+moveDirection, j)){
				possiblePawnMoves.add(""+i+j+(i+moveDirection)+(j)+" "+" ");
			}
		}
		//mouvement de 1 en avant avec promotion
		if(penultimateLane && isEmptyCase(chessBoard[lastLane][j])){
			for(String piece:promot){
				if(simulateMoveForKingCheck(board, isWhite, i, j, lastLane, j)){
					possiblePawnMoves.add(""+i+j+lastLane+(j)+" "+piece);
				}
			}
		}
		//capture sans promotion
		if(basicMoveArea){
			for(int k=-1;k<=1;k+=2){
				if(isInBoard(i+moveDirection, j+k) && isEnnemy(chessBoard[i+moveDirection][j+k], isWhite)){
					String caseEvaluee = chessBoard[i+moveDirection][j+k];
					if(simulateMoveForKingCheck(board, isWhite, i, j, i+moveDirection, j+k)){
						possiblePawnMoves.add(""+i+j+(i+moveDirection)+(j+k)+caseEvaluee+" ");
					}
				}
			}
		}
		//capture avec promotion
		if(penultimateLane){
			for(int k=-1;k<=1;k+=2){
				if(isInBoard(lastLane, j+k) && isEnnemy(chessBoard[lastLane][j+k], isWhite)){
					String caseEvaluee = chessBoard[lastLane][j+k];
					for(String piece:promot){
						if(simulateMoveForKingCheck(board, isWhite, i, j, lastLane, j+k)){
							possiblePawnMoves.add(""+i+j+lastLane+(j+k)+caseEvaluee+piece);
						}
					}
				}
			}
		}
		return possiblePawnMoves;
	}

	//mouvements legaux des tours en position i,j
	public static ArrayList<String> legalMovesRook(Environment board, boolean isWhite, int i, int j){
		ArrayList<String> possibleRookMoves = new ArrayList<>();
		String[][] chessBoard = board.getChessBoard();
		int distance=1;
		for(int k=-1; k<=1; k++){
			for(int l=-1; l<=1; l++){
				if(!(Math.abs(k)==Math.abs(l)) && isInBoard(i+k, j+l)){ //On regarde les cases autour du roi qui sont dans le plateau
					while(isInBoard(i+distance*k, j+distance*l) && isEmptyCase(chessBoard[i+distance*k][j+distance*l])){ //tant que la case évaluée est vide, on peut avancer la reine
						String caseEvaluee = chessBoard[i+distance*k][j+distance*l];
						if(simulateMoveForKingCheck(board, isWhite, i, j, i+distance*k, j+distance*l)){
							possibleRookMoves.add(""+i+j+(i+distance*k)+(j+distance*l)+caseEvaluee+" ");
						}
						distance++;
					}
					if(isInBoard(i+distance*k, j+distance*l) && isEnnemy(chessBoard[i+distance*k][j+distance*l], isWhite)){
						String caseEvaluee = chessBoard[i+distance*k][j+distance*l];
						if(simulateMoveForKingCheck(board, isWhite, i,j, i+distance*k, j+distance*l)){
							possibleRookMoves.add(""+i+j+(i+distance*k)+(j+distance*l)+caseEvaluee+" ");
						}
					}
				}
				distance = 1;
			}
		}
		return possibleRookMoves;
	}

	//mouvements legaux des cavaliers en position i,j
	public static ArrayList<String> legalMovesKnight(Environment board, boolean isWhite, int i, int j){
		ArrayList<String> possibleKnightMoves = new ArrayList<>();
		String[][] chessBoard = board.getChessBoard();
		for(int k=-2; k<=2; k++){
			for(int l=-2; l<=2; l++){				
				if((Math.abs(k)+Math.abs(l)==3)&&!(k==0 || l==0) && isInBoard(i+k, j+l)){ //On regarde les cases autour du roi qui sont dans le plateau
					String caseEvaluee = chessBoard[i+k][j+l];
					if(isEmptyCase(caseEvaluee) || isEnnemy(caseEvaluee, isWhite)){  
						if(simulateMoveForKingCheck(board, isWhite, i, j, i+k, j+l)){//on vérifie que le roi ne se met pas en échec s'il fait ce mouvement
							possibleKnightMoves.add(""+i+j+(i+k)+(j+l)+caseEvaluee+" ");
						}
					}
				}
			}
		}
		return possibleKnightMoves;
	}

	//mouvements legaux des fous en position i,j
	public static ArrayList<String> legalMovesBishop(Environment board, boolean isWhite, int i, int j){
		ArrayList<String> possibleBishopMoves = new ArrayList<>();
		String[][] chessBoard = board.getChessBoard();
		int distance=1;
		for(int k=-1; k<=1; k+=2){
			for(int l=-1; l<=1; l+=2){				
				if(isInBoard(i+k, j+l)){ //On regarde les cases autour du roi qui sont dans le plateau
					while(isInBoard(i+distance*k, j+distance*l) && isEmptyCase(chessBoard[i+distance*k][j+distance*l])){ //tant que la case évaluée est vide, on peut avancer la reine
						String caseEvaluee = chessBoard[i+distance*k][j+distance*l];
						if(simulateMoveForKingCheck(board, isWhite, i, j, i+distance*k, j+distance*l)){
							possibleBishopMoves.add(""+i+j+(i+distance*k)+(j+distance*l)+caseEvaluee+" ");
						}
						distance++;
					}
					if(isInBoard(i+distance*k, j+distance*l) && isEnnemy(chessBoard[i+distance*k][j+distance*l], isWhite)){
						String caseEvaluee = chessBoard[i+distance*k][j+distance*l];
						if(simulateMoveForKingCheck(board, isWhite, i,j, i+distance*k, j+distance*l)){
							possibleBishopMoves.add(""+i+j+(i+distance*k)+(j+distance*l)+caseEvaluee+" ");
						}
					}
				}
				distance = 1;
			}
		}
		return possibleBishopMoves;
	}

	//mouvements legaux de la reine en position i,j
	public static ArrayList<String> legalMovesQueen(Environment board, boolean isWhite, int i, int j){
		ArrayList<String> possibleQueenMoves = new ArrayList<>();
		String[][] chessBoard = board.getChessBoard();
		int distance=1;
		for(int k=-1; k<=1; k++){
			for(int l=-1; l<=1; l++){
				if(!(k==0 && l==0) && isInBoard(i+distance*k, j+distance*l)){ //On regarde les cases autour du roi qui sont dans le plateau
					while(isInBoard(i+distance*k, j+distance*l) && isEmptyCase(chessBoard[i+distance*k][j+distance*l])){ //tant que la case évaluée est vide, on peut avancer la reine
						String caseEvaluee = chessBoard[i+distance*k][j+distance*l];
						if(simulateMoveForKingCheck(board, isWhite, i, j, i+distance*k, j+distance*l)){
							possibleQueenMoves.add(""+i+j+(i+distance*k)+(j+distance*l)+caseEvaluee+" ");
						}
						distance++;
					}
					if(isInBoard(i+distance*k, j+distance*l) && isEnnemy(chessBoard[i+distance*k][j+distance*l], isWhite)){
						String caseEvaluee = chessBoard[i+distance*k][j+distance*l];
						if(simulateMoveForKingCheck(board, isWhite, i, j, i+distance*k, j+distance*l)){
							possibleQueenMoves.add(""+i+j+(i+distance*k)+(j+distance*l)+caseEvaluee+" ");
						}
					}
				}
				distance = 1;
			}
		}
		return possibleQueenMoves;
	}

	//mouvements legaux du roi en position i,j
	public static ArrayList<String> legalMovesKing(Environment board, boolean isWhite, int i, int j){
		ArrayList<String> possibleKingMoves= new ArrayList<>();
		String[][] chessBoard = board.getChessBoard();
		for(int k=-1; k<=1; k++){
			for(int l=-1; l<=1; l++){
				if(!(k==0 && l==0) && isInBoard(i+k, j+l)){ //On regarde les cases autour du roi qui sont dans le plateau
					String caseEvaluee = chessBoard[i+k][j+l];
					if(isEmptyCase(caseEvaluee) || isEnnemy(caseEvaluee, isWhite)){ //si la case évaluee est vide ou est un ennemi
						//On simule le déplacement puis on verifie que cela ne met pas en echec
						if(simulateMoveForKingCheck(board, isWhite, i,j,i+k, j+l)){//on vérifie que le roi ne se met pas en échec s'il fait ce mouvement
							possibleKingMoves.add(""+i+j+(i+k)+(j+l)+caseEvaluee+" ");
						}
					}
				}		
			}
		}
		return possibleKingMoves;
	}

	public static boolean simulateMoveForKingCheck(Environment board, boolean isWhite, int i, int j, int ni, int nj){
		//Cette fonction simule le mouvement d'une piece de (i,j) à (ni, nj) pour s'assurer 
		//que le mouvement ne met pas le roi en echec
		//Elle renvoie true si le mouvement est légal et false sinon

		String[][] chessBoard = board.getChessBoard();

		//On mémorise la configuration actuelle dans des buffers
		String bufferIJ = chessBoard[i][j];
		String bufferNiNj = chessBoard[ni][nj];

		//On effectue le mouvement 
		chessBoard[ni][nj] = bufferIJ; 
		chessBoard[i][j] = " ";

		//Y'a t'il un échec à la fin du mouvement?
		boolean kingCheck = kingCheck(board, isWhite);

		//On annule le mouvement
		chessBoard[i][j] = bufferIJ;
		chessBoard[ni][nj] = bufferNiNj;

		//On retourne true si la configuration testée ne mène pas à un échec, false sinon.
		return !kingCheck;

	}

	public static boolean kingCheck(Environment board, boolean isWhite){ //cette fonction regarde les échecs au roi en fonction de la position des pièces ennemis
		//position du roi du joueur actuel
		int kingPosI = myKingPosition(board, isWhite)[0];
		int kingPosJ = myKingPosition(board, isWhite)[1];
		
		//on génère les différents booleens correspondant au possibilités d'échec au roi 
		//Echec par une pièce à mouvement diagonal (fous, reines)
		//Echec par une pièce à mouvement droit (tours, reines)
		//Echec par les cavaliers
		//Echecs par les pions
		boolean isCheck1 = checkByDiagonallyMovingPieces(board, isWhite, kingPosI, kingPosJ);
		boolean isCheck2 = checkcheckByAlongRankMovingPieces(board, isWhite, kingPosI, kingPosJ);
		boolean isCheck3 = checkByKnights(board, isWhite, kingPosI, kingPosJ);
		boolean isCheck4 = checkByPawns(board, isWhite, kingPosI, kingPosJ);

		return (isCheck1 || isCheck2 || isCheck3 || isCheck4);
	}

	private static boolean checkByPawns(Environment board, boolean isWhite, int kingPosI, int kingPosJ) {
		//calcul en echec par les pions
		String[][] chessBoard = board.getChessBoard();
		int pawnDirection;
		if(isWhite){
			pawnDirection=-1;
		}
		else{
			pawnDirection=1;
		}
		for(int k=-1; k<=1; k+=2){
			if(isInBoard(kingPosI+pawnDirection, kingPosJ+k)){ 
				String caseEvaluee = chessBoard[kingPosI+pawnDirection][kingPosJ+k];
				if(caseEvaluee.equals(myPieces(!isWhite)[0])){
					return true;
				}
			}
		}
		return false;
	}

	private static boolean checkByKnights(Environment board, boolean isWhite, int kingPosI, int kingPosJ) {
		String[][] chessBoard = board.getChessBoard();
		//calcul en echec par les cavaliers :
		for(int k=-2; k<=2; k++){
			for(int l=-2; l<=2; l++){				
				if((Math.abs(k)+Math.abs(l)==3)&&!(k==0 || l==0) && isInBoard(kingPosI+k, kingPosJ+l)){
					String caseEvaluee = chessBoard[kingPosI+k][kingPosJ+l];
					if(caseEvaluee.equals(myPieces(!isWhite)[2])){ //case evalue = n si ia joue blanc, N sinon
						return true;
					}
				}
			}
		}
		return false;
	}

	private static boolean checkcheckByAlongRankMovingPieces(Environment board, boolean isWhite, int kingPosI, int kingPosJ) {
		String[][] chessBoard = board.getChessBoard();
		//Calcul en echec par ligne droite (Reine, Tours)
		int distance=1;
		for(int k=-1; k<=1; k++){
			for(int l=-1; l<=1; l++){
				if(!(Math.abs(k)==Math.abs(l)) && isInBoard(kingPosI+k, kingPosJ+l)){ //On regarde les cases autour du roi qui sont dans le plateau
					while(isInBoard(kingPosI+distance*k, kingPosJ+distance*l) && isEmptyCase(chessBoard[kingPosI+distance*k][kingPosJ+distance*l])){ //tant que la case évaluée est vide, on peut avancer la reine
						distance++;
					}
					if(isInBoard(kingPosI+distance*k, kingPosJ+distance*l)){
						String caseEvaluee = chessBoard[kingPosI+distance*k][kingPosJ+distance*l];
						if(caseEvaluee.equals(myPieces(!isWhite)[1]) || caseEvaluee.equals(myPieces(!isWhite)[4])){
							//si on est en ligne de mir d'une tour ou de la reine, le roi est en echec
							return true;
						}
					}
				}
				distance=1;
			}
		}
		return false;
	}

	private static boolean checkByDiagonallyMovingPieces(Environment board, boolean isWhite, int kingPosI, int kingPosJ) {
		String[][] chessBoard = board.getChessBoard();
		//calcul des echecs en diagonale (Reine et Fou) : 
		int distance = 1;
		for(int k=-1; k<=1; k+=2){
			for(int l=-1; l<=1; l+=2){				
				if(!(k==0 && l==0) && isInBoard(kingPosI+k, kingPosJ+l)){ //On regarde les cases autour du roi qui sont dans le plateau
					while(isInBoard(kingPosI+distance*k, kingPosJ+distance*l) && isEmptyCase(chessBoard[kingPosI+distance*k][kingPosJ+distance*l])){ //tant que la case évaluée est vide, on peut avancer la reine
						distance++;
					}
					if(isInBoard(kingPosI+distance*k, kingPosJ+distance*l)){
						String caseEvaluee = chessBoard[kingPosI+distance*k][kingPosJ+distance*l];
						if(caseEvaluee.equals(myPieces(!isWhite)[3]) || caseEvaluee.equals(myPieces(!isWhite)[4])){
							return true;
						}
					}
				}
				distance = 1;
			}
		}

		return false;
	}

	//retourne la position du roi du joueur actuel
	public static int[] myKingPosition(Environment board, boolean isWhite){
		String myKing;
		if(isWhite)
			myKing="K";
		else{
			myKing="k";
		}
		String[][] chessBoard = board.getChessBoard();
		int pos[] = {};
		for(int i=0; i<= 7; i++){
			for(int j=0; j<=7; j++){
				if(chessBoard[i][j].equals(myKing)){
					pos = new int[]{i,j};
				}
			}
		}
		return pos;
	}

	//fonction qui verifie si deux coord sont dans le palteau
	public static boolean isInBoard(int i, int j){ 
		return(i>=0 && i<8 && j>=0 && j<8);
	}

	//fonction qui verifie si une case évaluée contient une pièce de l'ennemi courant
	public static boolean isEnnemy(String caseEvaluee, boolean isWhite) { //il y a un ennmi sur la case evaluee
		if(isWhite) //Si je joue les blancs
			return Character.isLowerCase(caseEvaluee.charAt(0)); 
		//Sinon
		return Character.isUpperCase(caseEvaluee.charAt(0));
	}

	//fonction qui vérifie si la case testée est vide
	public static boolean isEmptyCase(String caseEvaluee){//la case evaluee est vide
		return caseEvaluee.equals(" ");
	}
}