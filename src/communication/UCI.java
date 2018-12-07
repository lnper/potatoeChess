package communication;

import java.util.Scanner;

import board.ChessBoardGenerator;
import move.ThreadHandler;
import communication.NoneUCICommunication;
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
            
            else NoneUCICommunication.noneUCICommunication(inputString);
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
            	/*if (Main.WhiteToMove) {
                    moves=Moves.possibleMovesW(Main.WP,Main.WN,Main.WB,Main.WR,Main.WQ,Main.WK,Main.BP,Main.BN,Main.BB,Main.BR,Main.BQ,Main.BK,Main.EP,Main.CWK,Main.CWQ,Main.CBK,Main.CBQ);
                } 
                else {
                    moves=Moves.possibleMovesB(Main.WP,Main.WN,Main.WB,Main.WR,Main.WQ,Main.WK,Main.BP,Main.BN,Main.BB,Main.BR,Main.BQ,Main.BK,Main.EP,Main.CWK,Main.CWQ,Main.CBK,Main.CBQ);
                }*/
            	
                algebraToMove(input, moves);
                input=input.substring(input.indexOf(' ')+1);
            }
        }
    }
    
    public static void inputGo() {
    	ThreadHandler.calculateBestMove();
    }
    
    public static String moveToAlgebra(String move) {
        
    	String append = "";
        int start = 0, end = 0;
        
        if (Character.isDigit(move.charAt(3))) {//'regular' move
            start=(Character.getNumericValue(move.charAt(0))*8)+(Character.getNumericValue(move.charAt(1)));
            end=(Character.getNumericValue(move.charAt(2))*8)+(Character.getNumericValue(move.charAt(3)));
        } else if (move.charAt(3)=='P') {//pawn promotion
            if (Character.isUpperCase(move.charAt(2))) {
                start=Long.numberOfTrailingZeros(Moves.FileMasks8[move.charAt(0)-'0']&Moves.RankMasks8[1]);
                end=Long.numberOfTrailingZeros(Moves.FileMasks8[move.charAt(1)-'0']&Moves.RankMasks8[0]);
            } else {
                start=Long.numberOfTrailingZeros(Moves.FileMasks8[move.charAt(0)-'0']&Moves.RankMasks8[6]);
                end=Long.numberOfTrailingZeros(Moves.FileMasks8[move.charAt(1)-'0']&Moves.RankMasks8[7]);
            }
            append=""+Character.toLowerCase(move.charAt(2));
        } else if (move.charAt(3)=='E') {//en passant
            if (move.charAt(2)=='W') {
                start=Long.numberOfTrailingZeros(Moves.FileMasks8[move.charAt(0)-'0']&Moves.RankMasks8[3]);
                end=Long.numberOfTrailingZeros(Moves.FileMasks8[move.charAt(1)-'0']&Moves.RankMasks8[2]);
            } else {
                start=Long.numberOfTrailingZeros(Moves.FileMasks8[move.charAt(0)-'0']&Moves.RankMasks8[4]);
                end=Long.numberOfTrailingZeros(Moves.FileMasks8[move.charAt(1)-'0']&Moves.RankMasks8[5]);
            }
        }
        String returnMove="";
        returnMove+=(char)('a'+(start%8));
        returnMove+=(char)('8'-(start/8));
        returnMove+=(char)('a'+(end%8));
        returnMove+=(char)('8'-(end/8));
        returnMove+=append;
        return returnMove;
    }
    
    public static void algebraToMove(String input, String moves) {
        
    	int start = 0, end = 0;
    	// Prendre les deux premiers termes du movement
        int from = (input.charAt(0)-'a')+(8*('8'-input.charAt(1)));
        // Prendre les deux derniers termes du mouvement
        int to = (input.charAt(2)-'a')+(8*('8'-input.charAt(3)));
        
        // 
        for (int i = 0 ; i < moves.length() ; i+=4) {
        	
        	// Pour un mouvement "regulier"
            if (Character.isDigit(moves.charAt(i+3))) {
                start = (Character.getNumericValue(moves.charAt(i+0))*8)+(Character.getNumericValue(moves.charAt(i+1)));
                end = (Character.getNumericValue(moves.charAt(i+2))*8)+(Character.getNumericValue(moves.charAt(i+3)));
            } 
            
            // Dans le cas d'une promotion de pion
            else if (moves.charAt(i+3)=='P') {
                if (Character.isUpperCase(moves.charAt(i+2))) {
                    start = Long.numberOfTrailingZeros(Moves.FileMasks8[moves.charAt(i+0)-'0'] & Moves.RankMasks8[1]);
                    end = Long.numberOfTrailingZeros(Moves.FileMasks8[moves.charAt(i+1)-'0'] & Moves.RankMasks8[0]);
                } 
                else {
                    start = Long.numberOfTrailingZeros(Moves.FileMasks8[moves.charAt(i+0)-'0'] & Moves.RankMasks8[6]);
                    end = Long.numberOfTrailingZeros(Moves.FileMasks8[moves.charAt(i+1)-'0'] & Moves.RankMasks8[7]);
                }
            } 
            
            // Dans le cas d'un "en passant"
            else if (moves.charAt(i+3) == 'E') {
                if (moves.charAt(i+2) == 'W') {
                    start=Long.numberOfTrailingZeros(Moves.FileMasks8[moves.charAt(i+0)-'0'] & Moves.RankMasks8[3]);
                    end=Long.numberOfTrailingZeros(Moves.FileMasks8[moves.charAt(i+1)-'0'] & Moves.RankMasks8[2]);
                } 
                else {
                    start=Long.numberOfTrailingZeros(Moves.FileMasks8[moves.charAt(i+0)-'0'] & Moves.RankMasks8[4]);
                    end=Long.numberOfTrailingZeros(Moves.FileMasks8[moves.charAt(i+1)-'0'] & Moves.RankMasks8[5]);
                }
            }
            
            // Initialisation du plateau
            if ((start==from) && (end==to)) {
                if ((input.charAt(4)==' ') || (Character.toUpperCase(input.charAt(4))==Character.toUpperCase(moves.charAt(i+2)))) {
                    if (Character.isDigit(moves.charAt(i+3))) {//'regular' move
                        start=(Character.getNumericValue(moves.charAt(i))*8)+(Character.getNumericValue(moves.charAt(i+1)));
                        if (((1L<<start)&Main.WK)!=0) {Main.CWK=false; Main.CWQ=false;}
                        else if (((1L<<start)&Main.BK)!=0) {Main.CBK=false; Main.CBQ=false;}
                        else if (((1L<<start)&Main.WR&(1L<<63))!=0) {Main.CWK=false;}
                        else if (((1L<<start)&Main.WR&(1L<<56))!=0) {Main.CWQ=false;}
                        else if (((1L<<start)&Main.BR&(1L<<7))!=0) {Main.CBK=false;}
                        else if (((1L<<start)&Main.BR&1L)!=0) {Main.CBQ=false;}
                    }
                    Main.EP=Moves.makeMoveEP(Main.WP|Main.BP,moves.substring(i,i+4));
                    Main.WR=Moves.makeMoveCastle(Main.WR, Main.WK|Main.BK, moves.substring(i,i+4), 'R');
                    Main.BR=Moves.makeMoveCastle(Main.BR, Main.WK|Main.BK, moves.substring(i,i+4), 'r');
                    Main.WP=Moves.makeMove(Main.WP, moves.substring(i,i+4), 'P');
                    Main.WN=Moves.makeMove(Main.WN, moves.substring(i,i+4), 'N');
                    Main.WB=Moves.makeMove(Main.WB, moves.substring(i,i+4), 'B');
                    Main.WR=Moves.makeMove(Main.WR, moves.substring(i,i+4), 'R');
                    Main.WQ=Moves.makeMove(Main.WQ, moves.substring(i,i+4), 'Q');
                    Main.WK=Moves.makeMove(Main.WK, moves.substring(i,i+4), 'K');
                    Main.BP=Moves.makeMove(Main.BP, moves.substring(i,i+4), 'p');
                    Main.BN=Moves.makeMove(Main.BN, moves.substring(i,i+4), 'n');
                    Main.BB=Moves.makeMove(Main.BB, moves.substring(i,i+4), 'b');
                    Main.BR=Moves.makeMove(Main.BR, moves.substring(i,i+4), 'r');
                    Main.BQ=Moves.makeMove(Main.BQ, moves.substring(i,i+4), 'q');
                    Main.BK=Moves.makeMove(Main.BK, moves.substring(i,i+4), 'k');
                    Main.WhiteToMove=!Main.WhiteToMove;
                    break;
                }
            }
        }
    }
    public static void inputQuit() {
        System.exit(0);
    }
    public static void inputPrint() {
        BoardGeneration.drawArray(Main.WP,Main.WN,Main.WB,Main.WR,Main.WQ,Main.WK,Main.BP,Main.BN,Main.BB,Main.BR,Main.BQ,Main.BK);
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