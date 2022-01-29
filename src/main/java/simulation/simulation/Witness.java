package simulation.simulation;

import java.util.Random;

public class Witness {
    // Delay between each step and accident seek
    final int THREAD_SLEEP_TIME = 10;
    final int STEP_LENGTH = 5;

    static final Random rand = new Random();
    final Runnable witnessRunnable = new Runnable() {
        public void run() {
            witnessAction();
        }
    };

    static int borderWidth = -1, borderHeight = -1;
    int X, Y;
    boolean threadShouldStop = false;
    Thread witnessThread;

    static void setBorder(int borderWidth, int borderHeight) throws Exception {
        if (borderWidth < 1 || borderHeight < 1)
            throw new Exception("Border values must be greater");
        Witness.borderWidth = borderWidth;
        Witness.borderHeight = borderHeight;
    }

    Witness() throws Exception {
        if (borderWidth == -1 || borderHeight == -1) {
            throw new Exception("Border not specified, use setBorder()");
        }

        X = rand.nextInt(borderWidth);
        Y = rand.nextInt(borderHeight);

        witnessThread = new Thread(witnessRunnable);
        witnessThread.start();
    }

    void witnessAction() {
        while (!threadShouldStop) {
            // Randomly choose direction of step
            if (rand.nextBoolean()) {
                // Offset the step to move in both directions
                X += rand.nextInt(STEP_LENGTH * 2) - STEP_LENGTH;
            }
            if (rand.nextBoolean()) {
                Y += rand.nextInt(STEP_LENGTH * 2) - STEP_LENGTH;
            }
            // Check bounds
            if (Y > borderHeight)
                Y = borderHeight;
            if (X > borderWidth)
                X = borderWidth;

            // TODO: check for accidents and report to Operator

            try {
                Thread.sleep(THREAD_SLEEP_TIME);
            } catch (InterruptedException e) {
            }
        }
    }

    void threadStop() {
        threadShouldStop = true;
    }
}