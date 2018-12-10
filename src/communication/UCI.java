package communication;

import java.util.Scanner;

import board.Board;
import move.Moves;
import move.ThreadHandler;


/*	
 * Cette classe permet la communication avec Arena. Il s'agit du langage UCI, pour Universal Chess Interface. Pour plus d'infos, aller sur la page http://wbec-ridderkerk.nl/html/UCIProtocol.html.
 */

public class UCI {

	// Configurations
	static String ENGINENAME="Nurblag Chess";
	static String AUTHORS = "Benoit Manhes, Lilian Pattier, Pierre Scalzo";

	// Entrees possibles
	static String UCI = "uci";
	static String OPTIONS = "setoption";
	static String ISREADY = "isready";
	static String NEWGAME = "ucinewgame";
	static String POSITION = "position";
	static String STARTPOSITION = "startpos";
	static String FEN = "fen";
	static String MOVES = "moves";
	static String GO = "go";
	static String QUIT = "quit";
	static String PRINT = "print";

	public static boolean PLAYER_WHITE = true;


	public static int COMPT = 0;

	public static void uciCommunication() {

		Scanner input = new Scanner(System.in);

		// Boucle infinie pour toute la duree d'une partie
		while (true) {

			// Lecture de l'entree
			String inputString = input.nextLine();

			if (UCI.equals(inputString)) {
				inputUCI();
			}

			else if (inputString.startsWith(OPTIONS)) {
				inputSetOption(inputString);
			}

			else if (ISREADY.equals(inputString)) {
				inputIsReady();
			}

			else if (NEWGAME.equals(inputString)) {
				inputUCINewGame();
			}

			else if (inputString.startsWith(POSITION)) {
				inputPosition(inputString);
			}

			else if (inputString.startsWith(GO)) {
				inputGo();
			}

			else if (inputString.equals(QUIT)) {
				inputQuit();
				break;
			}

			else if (PRINT.equals(inputString)) {
				inputPrint();
			}

			else {
				System.out.println("Commande incorrecte");
			}

		}

		input.close();
	}

	public static void inputUCI() {
		System.out.println("id name "+ENGINENAME);
		System.out.println("id author "+AUTHORS);
		//options go here
		System.out.println("uciok");
	}

	public static void inputSetOption(String inputString) {
	}

	public static void inputIsReady() {
		System.out.println("readyok");
	}

	public static void inputUCINewGame() {
		Board board = new Board();
	}

	public static void inputPosition(String input) {

		// Gerer l'erreur d'une mauvaise entree (uniquement pour les tests sur console)
		if(input.length() == 8) {
		}

		else {

			input = input.substring(POSITION.length()+1).concat(" ");

			boolean accepted = false;
			if (input.contains(STARTPOSITION)) {
				input = input.substring(input.indexOf(STARTPOSITION) + STARTPOSITION.length()+1);
				//ChessBoardGenerator.importFEN("rnbqkbnr/pppppppp/8/8/8/8/PPPPPPPP/RNBQKBNR w KQkq - 0 1");
				Board.initialize();
				accepted = true;
			}

			else if (input.contains(FEN)) {
				input = input.substring(FEN.length()+1);
				
				if(input.length()>1) {
					Board.importFEN(input);
					if(input.contains(" w ")) PLAYER_WHITE = true;
					else if(input.contains(" b ")) PLAYER_WHITE = false;
				}
				
			}

			// Prendre en consideration l'ensemble des mouvements ordonnes
			if (input.contains(MOVES)) {
				input = input.substring(input.indexOf(MOVES) + MOVES.length()+1);

				while (input.length() > 0) {
					String move;
					// Nous nous interessons au premier mouvement de la String
					move = input.substring(0,4);
					// Nous le traitons
					Board.readMove(move);
					// Nous enlevons ce mouvement a la String puisqu'il a ete traite
					input = input.substring(input.indexOf(' ')+1);
				}
				accepted = true;
			}

			else if(accepted == false) {
			}
		}


	}

	// Calculer le meilleur mouvement pour jouer
	public static void inputGo() {
		String move = "";

		move = ThreadHandler.calculateBestMove(); // => retourne un mouvement qu'il faut maintenant system.out
		COMPT++;

		if(COMPT==0) move = "b2b3";
		else if(COMPT==1) move = "b7b6";
		else if(COMPT==2) move = "b3b4";
		else if(COMPT==3) move = "b6b5";
		COMPT++;

		System.out.println("bestmove "+move);
	}


	// Quitter l'uci
	public static void inputQuit() {
		System.exit(0);
	}

	// Mettre a jour l'affichage
	public static void inputPrint() {
		if(Board.chessBoard.length>1) Board.print();
		
		String player;
		if (PLAYER_WHITE) player = "blancs";
		else if (!PLAYER_WHITE) player = "noirs";
		else player = "NOONE";
		System.out.println("Nous jouons les "+player);
	}



	/* ---------------------------------------------------------------------------------------------------------------------------------------------------
	 * ----------------------------------------------------------------- Methodes utiles ----------------------------------------------------------------- 
	 * ---------------------------------------------------------------------------------------------------------------------------------------------------
	 */ 


}