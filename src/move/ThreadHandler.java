package move;

import java.util.concurrent.*;

import communication.UCI;

public class ThreadHandler {

    public static String calculateBestMoveWhite() {

        String move = "b"+String.valueOf(UCI.COMPT+2)+"b"+String.valueOf(UCI.COMPT+3);
        

        return move;
    }

	public static String calculateBestMoveBlack() {
		// TODO Auto-generated method stub
		return null;
	}
}
