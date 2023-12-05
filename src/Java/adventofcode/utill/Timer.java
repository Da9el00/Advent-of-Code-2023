package adventofcode.utill;

import java.time.Duration;
import java.time.Instant;

public class Timer {

    public static void timeChecker(Runnable runnable){
        Instant startTime = Instant.now();
        runnable.run();
        Instant endTime = Instant.now();

        // Calculate and print the elapsed time
        Duration elapsedTime = Duration.between(startTime, endTime);
        System.out.println("Elapsed time: " + elapsedTime.toMillis() / 1000 + " seconds");
    }
}
