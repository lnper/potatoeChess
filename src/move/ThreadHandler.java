package move;

import exceptions.InvalidMoveException;

import java.util.concurrent.*;

public class ThreadHandler {
    private static ExecutorService executor;
    private static Future<String> future;

    public static String calculateBestMove() {
        executor = Executors.newSingleThreadExecutor();
        future = executor.submit(new MoveIterator());
        String result = "";

        try {
            result = future.get(30, TimeUnit.SECONDS);
            System.out.println("result "+ result + ", reached depth: "+MoveIterator.REACHED_DEPTH);
            result = MoveConverter.toCoordinateMove(result.substring(result.length()-4, result.length()));
        } catch (InvalidMoveException e) {
            System.out.println("CAUGHT MOVE EXCEPTION");
        } catch (InterruptedException| ExecutionException e ) {
            e.printStackTrace();
            System.out.println("THREAD ERROR");
        } catch (TimeoutException e) {
            future.cancel(true);
            System.out.println("TIMEOUT");
            result = MoveIterator.result;
            System.out.println("result "+ result + ", reached depth: " + MoveIterator.REACHED_DEPTH);
            try {
                result = MoveConverter.toCoordinateMove(result.substring(result.length() - 4, result.length()));
            } catch (InvalidMoveException m) {
                System.out.println("CAUGHT MOVE EXCEPTION");
            }
        } finally {
            System.out.println("bestmove "+ result);
        }
        executor.shutdownNow();
        return result;
    }
}
