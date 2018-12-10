package main;

import java.util.ArrayList;

import board.Board;
import move.Moves;

public class test {

	public static void main(String[] args) {
		Board.initialize();
		Board.print();
		ArrayList<String> legal = Moves.legalMove();
		for(String pos: legal)
		{
			System.out.print(pos+" ");
			System.out.print(Board.numToMove(pos)+" ");
			System.out.println(Board.moveToNum(Board.numToMove(pos)));
		}
		System.out.println(legal.size());
	}

}
