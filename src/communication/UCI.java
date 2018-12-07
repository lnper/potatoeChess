package communication;

import java.util.Scanner;

import board.ChessBoardGenerator;
import move.ThreadHandler;
import move.Moves;

/*
 * Cette classe permet la communication avec Arena. Il s'agit du langage UCI, pour Universal Chess Interface. Pour plus d'infos, aller sur la page officielle de l'uci.
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
        //set options
    }
    public static void inputIsReady() {
         System.out.println("readyok");
    }
    public static void inputUCINewGame() {
        //TODO
    }
    public static void inputPosition(String input) {
    	
    	// Enlever la commande "position "
    	input = input.substring(POSITION.length()+1).concat(" ");
        
    	if (input.contains(STARTPOSITION)) {
            input = input.substring(STARTPOSITION.length()+1);
            ChessBoardGenerator.initiateChessBoard();
        }
        else if (input.contains(FEN)) {
            input = input.substring(FEN.length()+1);
            ChessBoardGenerator.importFEN(input);
        }

    	// Prendre en consideration l'ensemble des mouvements ordonnes
        if (input.contains(MOVES)) {
            input = input.substring(input.indexOf(MOVES) + MOVES.length()+1);
            while (input.length() > 0) {
            	String moves;
                moves = makeMove(input);
                input=input.substring(input.indexOf(' ')+1);
            }
        }
    }
    
    // Calculer le meilleur mouvement avant de jouer
    public static void inputGo() {
    	ThreadHandler.calculateBestMove();
    }
    
    // Quitter l'uci
    public static void inputQuit() {
        System.exit(0);
    }
    
    // Mettre a jour l'affichage
    public static void inputPrint() {
        
    }
    
    
    private static String makeMove(String input) {
        int moveFrom_vertical = (input.charAt(0) - 'a');
        int moveFrom_horizontal = ('8' - input.charAt(1));
        int moveTo_vertical = input.charAt(2) - 'a';
        int moveTo_horizontal = '8' - input.charAt(3);
        String move = Integer.toString(moveFrom_horizontal) + Integer.toString(moveFrom_vertical) + Integer.toString(moveTo_horizontal) + Integer.toString(moveTo_vertical);
        ChessBoardGenerator.movePiece(move);
        return move;
    }
}