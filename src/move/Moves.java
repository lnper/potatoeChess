package move;

import java.util.ArrayList;

import board.Board;

public class Moves {
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
		return null;
	}

	//mouvements legaux des tours en position i,j
	public static ArrayList<String> legalMoveR(Board board, int i, int j){
		return null;
	}

	//mouvements legaux des cavaliers en position i,j
	public static ArrayList<String> legalMoveN(Board board, int i, int j){
		return null;
	}

	//mouvements legaux des fous en position i,j
	public static ArrayList<String> legalMoveB(Board board, int i, int j){
		return null;
	}

	//mouvements legaux de la reine en position i,j
	public static ArrayList<String> legalMoveQ(Board board, int i, int j){
		return null;
	}

	//mouvements legaux du roi en position i,j
	public static ArrayList<String> legalMoveK(Board board, int i, int j){
		ArrayList<String> possibleKingMoves= new ArrayList<>();
		for(int k=-1; k<=1; k++){
			for(int l=-1; l<=1; l++){
				if(!(k==0 && l==0)){ //On regarde les cases autour du roi
					String caseEvaluee = board.chessBoard[i+k][j+l];
					if(Character.isLowerCase(caseEvaluee.charAt(0)) || caseEvaluee.equals(" ")){ //si la case évaluee est vide ou est un ennemi
						if(!kingCheck(board, i+k, j+1)){//on vérifie que le roi ne se met pas en échec s'il fait ce mouvement
							possibleKingMoves.add(""+i+j+k+l);
						}
					}
				}
				
			}
		}
		return possibleKingMoves;
	}

	public static boolean kingCheck(Board board, int i, int j){ //echec au roi
		return false;
	}

}