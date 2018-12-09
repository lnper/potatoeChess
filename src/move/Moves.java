package move;

import java.util.ArrayList;

import board.Board;

public class Moves {
	private static final String caseEvaluee = null;

	public static ArrayList legalMove(Board board){
		ArrayList<String> possibleMoveList= new ArrayList<String>();
		//On parcours le plateau pour ajouter a chaque fois les mouvements possibles
		for(int i=0; i<8; i++)
		{
			for(int j=0; j<8; j++)
			{
				switch(board.chessBoard[i][j]){
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
		return new ArrayList<String>();
	}

	//mouvements legaux des tours en position i,j
	public static ArrayList<String> legalMoveR(Board board, int i, int j){
		ArrayList<String> possibleRookMoves = new ArrayList<>();
		int distance=1;
		for(int k=-1; k<=1; k++){
			for(int l=-1; l<=1; l++){
				if(!(Math.abs(k)==Math.abs(l)) && isInBoard(i+distance*k, j+distance*l)){ //On regarde les cases autour du roi qui sont dans le plateau
					while(isInBoard(i+distance*k, j+distance*l) && isEmptyCase(board.chessBoard[i+distance*k][j+distance*l])){ //tant que la case évaluée est vide, on peut avancer la reine
						String caseEvaluee = board.chessBoard[i+distance*k][j+distance*l];
						if(simulateMoveForKingCheck(board, i, j, i+distance*k, j+distance*l)){
							possibleRookMoves.add(""+i+j+(i+distance*k)+(j+distance*l)+caseEvaluee);
						}
						distance++;
					}
					if(isInBoard(i+distance*k, j+distance*l) && isEnnemy(board.chessBoard[i+distance*k][j+distance*l])){
						String caseEvaluee = board.chessBoard[i+distance*k][j+distance*l];
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
		for(int k=-2; k<=2; k++){
			for(int l=-2; l<=2; l++){				
				if((Math.abs(k)+Math.abs(l)==3)&&!(k==0 || l==0) && isInBoard(i+k, j+l)){ //On regarde les cases autour du roi qui sont dans le plateau
					String caseEvaluee = board.chessBoard[i+k][j+l];
					if(isEmptyCase(caseEvaluee) || isEnnemy(caseEvaluee)){  
						if(simulateMoveForKingCheck(board,i,j,i+k,j+l)){//on vérifie que le roi ne se met pas en échec s'il fait ce mouvement
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
		int distance=1;
		for(int k=-1; k<=1; k+=2){
			for(int l=-1; l<=1; l+=2){				
				if(!(k==0 && l==0) && isInBoard(i+distance*k, j+distance*l)){ //On regarde les cases autour du roi qui sont dans le plateau
					while(isInBoard(i+distance*k, j+distance*l) && isEmptyCase(board.chessBoard[i+distance*k][j+distance*l])){ //tant que la case évaluée est vide, on peut avancer la reine
						String caseEvaluee = board.chessBoard[i+distance*k][j+distance*l];
						if(simulateMoveForKingCheck(board, i, j, i+distance*k, j+distance*l)){
							possibleBishopMoves.add(""+i+j+(i+distance*k)+(j+distance*l)+caseEvaluee);
						}
						distance++;
					}
					if(isInBoard(i+distance*k, j+distance*l) && isEnnemy(board.chessBoard[i+distance*k][j+distance*l])){
						String caseEvaluee = board.chessBoard[i+distance*k][j+distance*l];
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
		int distance=1;
		for(int k=-1; k<=1; k++){
			for(int l=-1; l<=1; l++){
				if(!(k==0 && l==0) && isInBoard(i+distance*k, j+distance*l)){ //On regarde les cases autour du roi qui sont dans le plateau
					while(isInBoard(i+distance*k, j+distance*l) && isEmptyCase(board.chessBoard[i+distance*k][j+distance*l])){ //tant que la case évaluée est vide, on peut avancer la reine
						String caseEvaluee = board.chessBoard[i+distance*k][j+distance*l];
						if(simulateMoveForKingCheck(board, i, j, i+distance*k, j+distance*l)){
							possibleQueenMoves.add(""+i+j+(i+distance*k)+(j+distance*l)+caseEvaluee);
						}
						distance++;
					}
					if(isInBoard(i+distance*k, j+distance*l) && isEnnemy(board.chessBoard[i+distance*k][j+distance*l])){
						String caseEvaluee = board.chessBoard[i+distance*k][j+distance*l];
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
		for(int k=-1; k<=1; k++){
			for(int l=-1; l<=1; l++){
				if(!(k==0 && l==0) && isInBoard(i+k, j+l)){ //On regarde les cases autour du roi qui sont dans le plateau
					String caseEvaluee = board.chessBoard[i+k][j+l];
					if(isEmptyCase(caseEvaluee) || isEnnemy(caseEvaluee)){ //si la case évaluee est vide ou est un ennemi
						//On simule le déplacement puis on verifie que cela ne met pas en echec
						if(simulateMoveForKingCheck(board,i,j,i+k,j+l)){//on vérifie que le roi ne se met pas en échec s'il fait ce mouvement
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
		String bufferIJ = board.chessBoard[i][j];
		String bufferNiNj = board.chessBoard[ni][nj];

		board.chessBoard[ni][nj] = bufferIJ; 
		board.chessBoard[i][j] = " ";

		boolean kingCheck = kingCheck(board);

		board.chessBoard[i][j] = bufferIJ;
		board.chessBoard[ni][nj] = bufferNiNj;

		return !kingCheck;

	}

	public static boolean kingCheck(Board board){ //echec au roi
		return false;
	}

	public static boolean isInBoard(int i, int j){ //fonction qui verifie si deux coord sont dans le palteau
		return(i>=0 && i<8 && j>=0 && j<8);
	}

	public static String convertMove(String move) //converti un mouvement du plateau pour le formater en mouvement d'echec
	{
		return move;
	}

	public static boolean isEnnemy(String caseEvaluee) { //il y a un ennmi sur la case evaluee
		return Character.isLowerCase(caseEvaluee.charAt(0));
	}

	public static boolean isEmptyCase(String caseEvaluee){//la case evaluee est vide
		return caseEvaluee.equals(" ");
	}
}