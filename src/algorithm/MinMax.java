package algorithm;

import move.Moves;
import environnement.Environment;
import move.Evaluation;

public class MinMax {
	
	public static final int DEPTH = 4;
	private String bestMove;
	
	public MinMax() {
		bestMove ="";
	}
	
	///////////////// fonction static ///////////////////////////////////////////////////////////
	public static String alphaBeta(Environment board, boolean player) {
		MinMax algo = new MinMax();
		int score = algo.minmax (board, DEPTH, -1000000, 1000000, true, player);
		
		return algo.getBestMove();
	}

	/////////////// algo min max avec alpha beta ////////////////////////////////////////////////
	private int minmax(Environment board, int depth, int alpha, int beta, boolean maximizingPlayer, boolean player) {
		
		if (0 == depth || board.gameOver()) 
			return Evaluation.evaluate(board, player);

		int eval;
		int maxEval;
		int minEval;

		// algorithme max
		if (maximizingPlayer) {
			maxEval = -1000000;

			for (String m : Moves.legalMoves(board, player)) {
				board.move(m);
				eval = minmax(board, depth - 1, alpha, beta , false, player); 
				if(eval > maxEval && depth == DEPTH) this.bestMove = m;	// sauvegarde du meilleur mouvement
				maxEval = Math.max(maxEval, eval);
				alpha = Math.max(alpha, maxEval);
				board.undoMove(m);
				if (beta <= alpha) 
					break;
			}
			return maxEval;	

		// algorithme min
		}else {
			minEval = 1000000;
			for (String m : Moves.legalMoves(board, !player)) {
				board.move(m);
				eval = minmax(board, depth - 1, alpha, beta , true, player);
				minEval = Math.min(minEval, eval);
				beta = Math.min(beta, minEval);
				board.undoMove(m);
				if (beta <= alpha) 
					break;
			}
			return minEval;
		}
	}

	public String getBestMove() {
		return bestMove;
	}
}