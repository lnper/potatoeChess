package move;

import java.util.ArrayList;

import board.Board;

public class Moves {

	public static ArrayList<String> legalMove(Board board, boolean isWhite){
		ArrayList<String> possibleMoveList= new ArrayList<String>();
		//On parcours le plateau pour ajouter a chaque fois les mouvements possibles
		String[][] chessBoard = board.getChessBoard();
		
		for(int i=0; i<8; i++){
			for(int j=0; j<8; j++){

				switch(chessBoard[i][j]){
				case "P":
					possibleMoveList.addAll(legalMoveP(board, i, j));
					break;
				case "R":
					possibleMoveList.addAll(legalMoveR(board, i, j));
					break;
				case "N":
					possibleMoveList.addAll(legalMoveN(board, i, j));
					break;
				case "B":
					possibleMoveList.addAll(legalMoveB(board, i, j));
					break;
				case "Q":
					possibleMoveList.addAll(legalMoveQ(board, i, j));
					break;
				case "K":
					possibleMoveList.addAll(legalMoveK(board, i, j));
					break;
				}
			}
		}
		return possibleMoveList;
	}

	//mouvements legaux des pions en position i,j
	public static ArrayList<String> legalMoveP(Board board, int i, int j){
		ArrayList<String> possiblePawnMoves = new ArrayList<>();
		String[][] chessBoard = board.getChessBoard();
		//mouvement 2 en avant
		if(i==6 && isEmptyCase(chessBoard[i-1][j]) && isEmptyCase(chessBoard[i-2][j])){
			if(simulateMoveForKingCheck(board, i, j, i-2, j)){
				possiblePawnMoves.add(""+i+j+(i-2)+(j)+" ");
			}
		}
		//mouvement de 1 en avant sans promotion
		if(i>=2 && isEmptyCase(chessBoard[i-1][j])){
			if(simulateMoveForKingCheck(board, i, j, i-1, j)){
				possiblePawnMoves.add(""+i+j+(i-1)+(j)+" ");
			}
		}
		//mouvement de 1 en avant avec promotion
		if(i==1 && isEmptyCase(chessBoard[0][j])){
			String[] promot={"Q","R","B","K"};
			for(String piece:promot){
				if(simulateMoveForKingCheck(board, i, j, i-1, j)){
					possiblePawnMoves.add(""+i+j+(i-1)+(j)+" "+piece);
				}
			}
		}
		//capture sans promotion
		if(i>=2){
			for(int k=-1;k<=1;k+=2){
				if(isInBoard(i-1, j+k) && isEnnemy(chessBoard[i-1][j+k])){
					String caseEvaluee = chessBoard[i-1][j+k];
					if(simulateMoveForKingCheck(board, i, j, i-1, j+k)){
						possiblePawnMoves.add(""+i+j+(i-1)+(j+k)+caseEvaluee);
					}
				}
			}
		}
		//capture avec promotion
		if(i==1){
			for(int k=-1;k<=1;k+=2){
				if(isInBoard(0, j+k) && isEnnemy(chessBoard[0][j+k])){
					String caseEvaluee = chessBoard[0][j+k];
					String[] promot={"Q","R","B","K"};
					for(String piece:promot){
						if(simulateMoveForKingCheck(board, i, j, 0, j+k)){
							possiblePawnMoves.add(""+i+j+(0)+(j+k)+caseEvaluee+piece);
						}
					}
				}
			}
		}
		return possiblePawnMoves;
	}

	//mouvements legaux des tours en position i,j
	public static ArrayList<String> legalMoveR(Board board, int i, int j){
		ArrayList<String> possibleRookMoves = new ArrayList<>();
		String[][] chessBoard = board.getChessBoard();
		int distance=1;
		for(int k=-1; k<=1; k++){
			for(int l=-1; l<=1; l++){
				if(!(Math.abs(k)==Math.abs(l)) && isInBoard(i+k, j+l)){ //On regarde les cases autour du roi qui sont dans le plateau
					while(isInBoard(i+distance*k, j+distance*l) && isEmptyCase(chessBoard[i+distance*k][j+distance*l])){ //tant que la case évaluée est vide, on peut avancer la reine
						String caseEvaluee = chessBoard[i+distance*k][j+distance*l];
						if(simulateMoveForKingCheck(board, i, j, i+distance*k, j+distance*l)){
							possibleRookMoves.add(""+i+j+(i+distance*k)+(j+distance*l)+caseEvaluee);
						}
						distance++;
					}
					if(isInBoard(i+distance*k, j+distance*l) && isEnnemy(chessBoard[i+distance*k][j+distance*l])){
						String caseEvaluee = chessBoard[i+distance*k][j+distance*l];
						if(simulateMoveForKingCheck(board, i,j, i+distance*k, j+distance*l)){
							possibleRookMoves.add(""+i+j+(i+distance*k)+(j+distance*l)+caseEvaluee);
						}
					}
				}
				distance = 1;
			}
		}
		return possibleRookMoves;
	}

	//mouvements legaux des cavaliers en position i,j
	public static ArrayList<String> legalMoveN(Board board, int i, int j){
		ArrayList<String> possibleKnightMoves = new ArrayList<>();
		String[][] chessBoard = board.getChessBoard();
		for(int k=-2; k<=2; k++){
			for(int l=-2; l<=2; l++){				
				if((Math.abs(k)+Math.abs(l)==3)&&!(k==0 || l==0) && isInBoard(i+k, j+l)){ //On regarde les cases autour du roi qui sont dans le plateau
					String caseEvaluee = chessBoard[i+k][j+l];
					if(isEmptyCase(caseEvaluee) || isEnnemy(caseEvaluee)){  
						if(simulateMoveForKingCheck(board,i,j,i+k, j+l)){//on vérifie que le roi ne se met pas en échec s'il fait ce mouvement
							possibleKnightMoves.add(""+i+j+(i+k)+(j+l)+caseEvaluee);
						}
					}
				}
			}
		}
		return possibleKnightMoves;
	}

	//mouvements legaux des fous en position i,j
	public static ArrayList<String> legalMoveB(Board board, int i, int j){
		ArrayList<String> possibleBishopMoves = new ArrayList<>();
		String[][] chessBoard = board.getChessBoard();
		int distance=1;
		for(int k=-1; k<=1; k+=2){
			for(int l=-1; l<=1; l+=2){				
				if(isInBoard(i+k, j+l)){ //On regarde les cases autour du roi qui sont dans le plateau
					while(isInBoard(i+distance*k, j+distance*l) && isEmptyCase(chessBoard[i+distance*k][j+distance*l])){ //tant que la case évaluée est vide, on peut avancer la reine
						String caseEvaluee = chessBoard[i+distance*k][j+distance*l];
						if(simulateMoveForKingCheck(board, i, j, i+distance*k, j+distance*l)){
							possibleBishopMoves.add(""+i+j+(i+distance*k)+(j+distance*l)+caseEvaluee);
						}
						distance++;
					}
					if(isInBoard(i+distance*k, j+distance*l) && isEnnemy(chessBoard[i+distance*k][j+distance*l])){
						String caseEvaluee = chessBoard[i+distance*k][j+distance*l];
						if(simulateMoveForKingCheck(board, i,j, i+distance*k, j+distance*l)){
							possibleBishopMoves.add(""+i+j+(i+distance*k)+(j+distance*l)+caseEvaluee);
						}
					}
				}
				distance = 1;
			}
		}
		return possibleBishopMoves;
	}

	//mouvements legaux de la reine en position i,j
	public static ArrayList<String> legalMoveQ(Board board, int i, int j){
		ArrayList<String> possibleQueenMoves = new ArrayList<>();
		String[][] chessBoard = board.getChessBoard();
		int distance=1;
		for(int k=-1; k<=1; k++){
			for(int l=-1; l<=1; l++){
				if(!(k==0 && l==0) && isInBoard(i+distance*k, j+distance*l)){ //On regarde les cases autour du roi qui sont dans le plateau
					while(isInBoard(i+distance*k, j+distance*l) && isEmptyCase(chessBoard[i+distance*k][j+distance*l])){ //tant que la case évaluée est vide, on peut avancer la reine
						String caseEvaluee = chessBoard[i+distance*k][j+distance*l];
						if(simulateMoveForKingCheck(board, i, j, i+distance*k, j+distance*l)){
							possibleQueenMoves.add(""+i+j+(i+distance*k)+(j+distance*l)+caseEvaluee);
						}
						distance++;
					}
					if(isInBoard(i+distance*k, j+distance*l) && isEnnemy(chessBoard[i+distance*k][j+distance*l])){
						String caseEvaluee = chessBoard[i+distance*k][j+distance*l];
						if(simulateMoveForKingCheck(board, i,j, i+distance*k, j+distance*l)){
							possibleQueenMoves.add(""+i+j+(i+distance*k)+(j+distance*l)+caseEvaluee);
						}
					}
				}
				distance = 1;
			}
		}
		return possibleQueenMoves;
	}

	//mouvements legaux du roi en position i,j
	public static ArrayList<String> legalMoveK(Board board, int i, int j){
		ArrayList<String> possibleKingMoves= new ArrayList<>();
		String[][] chessBoard = board.getChessBoard();
		for(int k=-1; k<=1; k++){
			for(int l=-1; l<=1; l++){
				if(!(k==0 && l==0) && isInBoard(i+k, j+l)){ //On regarde les cases autour du roi qui sont dans le plateau
					String caseEvaluee = chessBoard[i+k][j+l];
					if(isEmptyCase(caseEvaluee) || isEnnemy(caseEvaluee)){ //si la case évaluee est vide ou est un ennemi
						//On simule le déplacement puis on verifie que cela ne met pas en echec
						if(simulateMoveForKingCheck(board,i,j,i+k, j+l)){//on vérifie que le roi ne se met pas en échec s'il fait ce mouvement
							possibleKingMoves.add(""+i+j+(i+k)+(j+l)+caseEvaluee);
						}
					}
				}		
			}
		}
		return possibleKingMoves;
	}

	public static boolean simulateMoveForKingCheck(Board board, int i, int j, int ni, int nj){
		//Cette fonction simule le mouvement d'une piece de (i,j) à (ni, nj) pour s'assurer 
		//que le mouvement ne met pas le roi en echec
		//Elle renvoie true si le mouvement est légal et false sinon

		//TODO : réécrire la fonction avec les méthodes move et unmove
		
		String[][] chessBoard = board.getChessBoard();

		String bufferIJ = chessBoard[i][j];
		String bufferNiNj = chessBoard[ni][nj];

		chessBoard[ni][nj] = bufferIJ; 
		chessBoard[i][j] = " ";


		boolean kingCheck = kingCheck(board);

		chessBoard[i][j] = bufferIJ;
		chessBoard[ni][nj] = bufferNiNj;

		return !kingCheck;

	}

	public static boolean kingCheck(Board board){ //cette fonction regarde les échecs au roi en fonction de la position des pièces ennemis
		int kingPosI = whiteKingPosition(board)[0];
		int kingPosJ = whiteKingPosition(board)[1];

		boolean isCheck1 = checkByDiagonallyMovingPieces(board, kingPosI, kingPosJ);
		boolean isCheck2 = checkcheckByAlongRankMovingPieces(board, kingPosI, kingPosJ);
		boolean isCheck3 = checkByNights(board, kingPosI, kingPosJ);
		boolean isCheck4 = checkByPawns(board, kingPosI, kingPosJ);

		//calcul des échecs en ligne droite (Reine et tours)

		return (isCheck1 || isCheck2 || isCheck3 || isCheck4);
	}

	private static boolean checkByPawns(Board board, int kingPosI, int kingPosJ) {
		String[][] chessBoard = board.getChessBoard();
		for(int k=-1; k<=1; k+=2){
			if(isInBoard(kingPosI-1, kingPosJ+k)){
				String caseEvaluee = chessBoard[kingPosI-1][kingPosJ+k];
				if(caseEvaluee.equals("p")){
					return true;
				}
			}
		}
		return false;
	}

	private static boolean checkByNights(Board board, int kingPosI, int kingPosJ) {
		String[][] chessBoard = board.getChessBoard();
		//calcul en echec par les cavaliers :
		for(int k=-2; k<=2; k++){
			for(int l=-2; l<=2; l++){				
				if((Math.abs(k)+Math.abs(l)==3)&&!(k==0 || l==0) && isInBoard(kingPosI+k, kingPosJ+l)){
					String caseEvaluee = chessBoard[kingPosI+k][kingPosJ+l];
					if(caseEvaluee.equals("n")){
						return true;
					}
				}
			}
		}
		return false;
	}

	private static boolean checkcheckByAlongRankMovingPieces(Board board, int kingPosI, int kingPosJ) {
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
						if(caseEvaluee.equals("r") || caseEvaluee.equals("q")){
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

	private static boolean checkByDiagonallyMovingPieces(Board board, int kingPosI, int kingPosJ) {
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
						if(caseEvaluee.equals("b") || caseEvaluee.equals("q")){
							return true;
						}
					}
				}
				distance = 1;
			}
		}

		return false;
	}

	public static int[] whiteKingPosition(Board board){
		String[][] chessBoard = board.getChessBoard();
		int pos[] = {};
		for(int i=7; i>= 0; i--){
			for(int j=7; j>=0; j--){
				if(chessBoard[i][j].equals("K")){
					pos = new int[]{i,j};
				}
			}
		}
		return pos;
	}


	public static boolean isInBoard(int i, int j){ //fonction qui verifie si deux coord sont dans le palteau
		return(i>=0 && i<8 && j>=0 && j<8);
	}

	public static boolean isEnnemy(String caseEvaluee) { //il y a un ennmi sur la case evaluee
		return Character.isLowerCase(caseEvaluee.charAt(0));
	}

	public static boolean isEmptyCase(String caseEvaluee){//la case evaluee est vide
		return caseEvaluee.equals(" ");
	}
}