package main;

import java.util.ArrayList;

import board.Board;
import move.Moves;

public class test {

	public static void main(String[] args) {
		Board board = new Board();
		board.print();
		ArrayList legal = Moves.legalMove(board);
		System.out.println(legal);

	}

}
