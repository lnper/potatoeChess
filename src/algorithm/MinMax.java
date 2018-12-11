package algorithm;

import board.Board;
import move.Moves;
import move.Evaluation;

public class MinMax {
	
	public static final int DEPTH = 3;
	private String bestMove;

	public MinMax() {
		bestMove ="";
	}
	
	///////////////// fonction static ///////////////////////////////////////////////////////////
	public static String alphaBeta(Board board, boolean player) {
		MinMax algo = new MinMax();
		int score = algo.minmax (board,DEPTH, -1000000, 1000000, true, player);
		
		return algo.getBestMove();
	}

	/////////////// algo min max avec alpha beta ////////////////////////////////////////////////
	private int minmax(Board board, int depth, int alpha, int beta, boolean maximizingPlayer, boolean player) {

		int eval;
		int maxEval;
		int minEval;

		if(!board.gameOver() && depth>0) {
			// algorithme max
			if (maximizingPlayer) {
				maxEval = -1000000;

				for (String m : Moves.legalMove(board, player)) {
					board.move(m);
					eval = minmax(board, depth - 1, alpha, beta , false, player); 
					if(eval > maxEval && depth == DEPTH) this.bestMove = m;	// sauvegarde du meilleur mouvement
					maxEval = Math.max(maxEval, eval);
					alpha = Math.max(alpha, eval);
					board.undoMove(m);
					if (beta <= alpha)
						break;// so cut here (pruning)
				}
				return maxEval;	

			// algorithme min
			}
			else {
				minEval = 1000000;
				for (String m : Moves.legalMove(board, !player)) {
					board.move(m);
					eval = minmax(board, depth - 1, alpha, beta , true, player);
					minEval = Math.min(minEval, eval);
					beta = Math.min(beta, eval);
					board.undoMove(m);
					if(beta <= alpha)
						break;
				}
				return minEval;
			}
		}
		
		else {
			return Evaluation.evaluate(board, player);
		}
		
		
	}

	//////////////////////// getter /////////////////////////////////////////////////////////////
	public String getBestMove() {
		return bestMove;
	}
}