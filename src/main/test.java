package main;

import java.util.ArrayList;

import board.Board;
import move.Moves;

public class test {

	public static void main(String[] args) {
		Board.initialize();
		Board.print();
		Board.readMove("g2g1q");
		Board.print();
	}

}
