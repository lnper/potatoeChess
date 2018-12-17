package communication;
import java.util.Scanner;
import algorithm.MinMax;
import environnement.Environment;



/*	
 * Cette classe permet la communication avec Arena. Il s'agit du langage UCI, pour Universal Chess Interface. 
 *Pour plus d'infos : http://wbec-ridderkerk.nl/html/UCIProtocol.html.
 */

public class UCI {

	// Configurations
	static String ENGINENAME="Potatoe Chess";
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

	public static Environment board;
	public static Boolean isWhite = true;

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
		System.out.println("uciok");
	}

	public static void inputSetOption(String inputString) {
		//Possibilite d'ajouter des options
	}

	public static void inputIsReady() {
		System.out.println("readyok");
	}

	public static void inputUCINewGame() {
		board = new Environment();		
	}

	public static void inputPosition(String input) {

		input = input.substring(POSITION.length()+1).concat(" ");

		if (input.contains(STARTPOSITION)) {
			input = input.substring(input.indexOf(STARTPOSITION) + STARTPOSITION.length()+1);
		
			board = new Environment();
			board.initialize();
		}
		// Prendre en consideration l'ensemble des mouvements ordonnes
		if (input.contains(MOVES)) {

			int comptMoves = 0;
			input = input.substring(input.indexOf(MOVES) + MOVES.length()+1);

			while (input.length() > 0) {
				String move;
				// Nous nous interessons au premier mouvement de la String

				move = input.substring(0,4);

				//Si on est dans le cas d'une promotion
				if(input.charAt(4) != ' '){ 
					move += input.charAt(4);
				}

				// Nous le traitons
				board.applyMoveFromArena(move);
				// Nous enlevons ce mouvement a la String puisqu'il a ete traite
				input = input.substring(input.indexOf(' ')+1);
				comptMoves++;
			}

			// Indique au premier coup si l'on est blanc ou noir
			if(comptMoves%2 == 0) {
				isWhite = true;
			}

			else if(comptMoves%2 == 1) {
				isWhite = false;
			}
		}
	}

	// Calculer le meilleur mouvement pour jouer
	public static void inputGo() {
		String move = "";

		move = MinMax.alphaBeta(board, isWhite);

		System.out.println("bestmove "+Environment.parseCBmoveToAmove(move));

	}

	// Quitter l'uci
	public static void inputQuit() {
		System.exit(0);
	}

	// Mettre a jour l'affichage
	public static void inputPrint() {
		if(board.getChessBoard().length>1) board.print();

		String player;
		if (isWhite) player = "blancs";
		else player = "noirs";
		System.out.println("Nous jouons les "+player);
	}
}