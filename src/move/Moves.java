package move;

import java.util.ArrayList;

import board.Board;

public class Moves {

	public static ArrayList<String> legalMove(Board board, boolean isWhite){
		ArrayList<String> possibleMoveList= new ArrayList<String>();
		//On parcours le plateau pour ajouter a chaque fois les mouvements possibles
		String[][] chessBoard = board.getChessBoard();
		String[] pieces = myPieces(isWhite);


		for(int i=0; i<8; i++){
			for(int j=0; j<8; j++){
				
				String pieceAnalysee = chessBoard[i][j];
				
				if(pieceAnalysee.equals(pieces[0])) //Si c'est un pion
					possibleMoveList.addAll(legalMoveP(board, isWhite, i, j));
				
				else if(pieceAnalysee.equals(pieces[1])) //Si c'est une tours
					possibleMoveList.addAll(legalMoveR(board, isWhite, i, j));
				
				else if(pieceAnalysee.equals(pieces[2])) //Si c'est un cavalier
					possibleMoveList.addAll(legalMoveN(board, isWhite, i, j));
				
				else if(pieceAnalysee.equals(pieces[3])) //si c'est un fou
					possibleMoveList.addAll(legalMoveB(board, isWhite, i, j));
				
				else if(pieceAnalysee.equals(pieces[4])) //Si c'est une reine
					possibleMoveList.addAll(legalMoveQ(board, isWhite, i, j));
				
				else if(pieceAnalysee.equals(pieces[5])) //Si c'est un roi
					possibleMoveList.addAll(legalMoveK(board, isWhite, i, j));
			}
		}
		return possibleMoveList;
	}

	public static String[] myPieces(boolean isWhite){
		if(isWhite)
			return new String[]{"P","R","N","B","Q","K"};
		return new String[]{"p","r","n","b","q","k"};

	}

	//mouvements legaux des pions en position i,j
	public static ArrayList<String> legalMoveP(Board board, boolean isWhite, int i, int j){
		ArrayList<String> possiblePawnMoves = new ArrayList<>();
		String[][] chessBoard = board.getChessBoard();


		boolean firstPawnMove; 
		boolean penultimateLane;
		boolean basicMoveArea;
		int moveDirection;
		int lastLane;
		String[] promot;
		if(isWhite){
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
		//N:1 resp B:6
		if(firstPawnMove && isEmptyCase(chessBoard[i+moveDirection][j]) && isEmptyCase(chessBoard[i+2*moveDirection][j])){
			if(simulateMoveForKingCheck(board, isWhite, i, j, i+2*moveDirection, j)){
				possiblePawnMoves.add(""+i+j+(i+2*moveDirection)+(j)+" "+" ");
			}
		}
		// N: i<=5   B:i>=2
		//mouvement de 1 en avant sans promotion
		if(basicMoveArea && isEmptyCase(chessBoard[i+moveDirection][j])){
			if(simulateMoveForKingCheck(board, isWhite, i, j, i+moveDirection, j)){
				possiblePawnMoves.add(""+i+j+(i+moveDirection)+(j)+" "+" ");
			}
		}
		//N:6  B:1
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
	public static ArrayList<String> legalMoveR(Board board, boolean isWhite, int i, int j){
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
	public static ArrayList<String> legalMoveN(Board board, boolean isWhite, int i, int j){
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
	public static ArrayList<String> legalMoveB(Board board, boolean isWhite, int i, int j){
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
	public static ArrayList<String> legalMoveQ(Board board, boolean isWhite, int i, int j){
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
	public static ArrayList<String> legalMoveK(Board board, boolean isWhite, int i, int j){
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

	public static boolean simulateMoveForKingCheck(Board board, boolean isWhite, int i, int j, int ni, int nj){
		//Cette fonction simule le mouvement d'une piece de (i,j) à (ni, nj) pour s'assurer 
		//que le mouvement ne met pas le roi en echec
		//Elle renvoie true si le mouvement est légal et false sinon

		//TODO : réécrire la fonction avec les méthodes move et unmove

		String[][] chessBoard = board.getChessBoard();

		String bufferIJ = chessBoard[i][j];
		String bufferNiNj = chessBoard[ni][nj];

		chessBoard[ni][nj] = bufferIJ; 
		chessBoard[i][j] = " ";


		boolean kingCheck = kingCheck(board, isWhite);

		chessBoard[i][j] = bufferIJ;
		chessBoard[ni][nj] = bufferNiNj;

		return !kingCheck;

	}

	public static boolean kingCheck(Board board, boolean isWhite){ //cette fonction regarde les échecs au roi en fonction de la position des pièces ennemis
		int kingPosI = myKingPosition(board, isWhite)[0];
		int kingPosJ = myKingPosition(board, isWhite)[1];
		

		boolean isCheck1 = checkByDiagonallyMovingPieces(board, isWhite, kingPosI, kingPosJ);
		boolean isCheck2 = checkcheckByAlongRankMovingPieces(board, isWhite, kingPosI, kingPosJ);
		boolean isCheck3 = checkByNights(board, isWhite, kingPosI, kingPosJ);
		boolean isCheck4 = checkByPawns(board, isWhite, kingPosI, kingPosJ);

		//calcul des échecs en ligne droite (Reine et tours)
		//System.out.println(isCheck1 || isCheck2 || isCheck3 || isCheck4);
		return (isCheck1 || isCheck2 || isCheck3 || isCheck4);
	}

	private static boolean checkByPawns(Board board, boolean isWhite, int kingPosI, int kingPosJ) {
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

	private static boolean checkByNights(Board board, boolean isWhite, int kingPosI, int kingPosJ) {
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

	private static boolean checkcheckByAlongRankMovingPieces(Board board, boolean isWhite, int kingPosI, int kingPosJ) {
		String[][] chessBoard = board.getChessBoard();
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

	private static boolean checkByDiagonallyMovingPieces(Board board, boolean isWhite, int kingPosI, int kingPosJ) {
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

	public static int[] myKingPosition(Board board, boolean isWhite){
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


	public static boolean isInBoard(int i, int j){ //fonction qui verifie si deux coord sont dans le palteau
		return(i>=0 && i<8 && j>=0 && j<8);
	}

	public static boolean isEnnemy(String caseEvaluee, boolean isWhite) { //il y a un ennmi sur la case evaluee
		if(isWhite) //Si je joue les blancs
			return Character.isLowerCase(caseEvaluee.charAt(0)); 
		//Sinon
		return Character.isUpperCase(caseEvaluee.charAt(0));
	}

	public static boolean isEmptyCase(String caseEvaluee){//la case evaluee est vide
		return caseEvaluee.equals(" ");
	}
}