package main;

import java.util.ArrayList;

import algorithm.MinMax;
import board.Board;
import move.Moves;

public class test {

	public static void main(String[] args) {
		Board board = new Board();
		board.initialize();
		board.print();
		board.readMove("b2b3 ");
		board.print();
		//MinMax.alphaBeta(board, true);
	}
}
