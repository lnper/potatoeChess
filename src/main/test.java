package main;

import java.util.ArrayList;

import algorithm.MinMax;
import environnement.Environment;
import move.Moves;

public class test {

	public static void main(String[] args) {
		Environment board = new Environment();
		board.initialize();
		board.print();
		board.applyMoveFromArena("f2f1q");
		board.print();
		
		System.out.println(Environment.parseAmoveToCBmove("f2f1q"));
		System.out.println(Environment.parseAmoveToCBmove("f7f8q"));
		//System.out.println(Character.isLowerCase()board.getChessBoard()[4][4]);
		//MinMax.alphaBeta(board, true);
	}
}
