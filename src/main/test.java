package main;

import java.util.ArrayList;

import board.Board;
import move.Moves;

public class test {

	public static void main(String[] args) {
		Board board = new Board();
		board.initialize();
		board.print();
		
		ArrayList<String> coupBlanc = Moves.legalMove(board, true);
		ArrayList<String> coupNoirs = Moves.legalMove(board, false);
		System.out.println("blancs");
		for(String coup: coupBlanc){
			System.out.print(board.numToMove(coup));
			System.out.println(" "+coup.length());
		}
		System.out.println("");
		System.out.println("noirs");
		for(String coup: coupNoirs){
			System.out.print(board.numToMove(coup));
			System.out.println(" "+coup.length());
		}
		
		
		System.out.println(coupBlanc);
		System.out.println(coupNoirs);
	}

}
