package JavaNs2.Models;

import java.util.Calendar;

public class Helper {
    private static long startTime;

    public static String getCurrentTime()
    {
        Calendar now = Calendar.getInstance();
        String CurrentTime;
        CurrentTime = (now.get(Calendar.HOUR) + "" + now.get(Calendar.MINUTE) + "" + now.get(Calendar.SECOND));
        return CurrentTime;
    }

    public static void startPerformanceTest()
    {
        startTime = System.currentTimeMillis();
        System.out.println("Start at: " + startTime);
    }

    public static void endPerformanceTest()
    {
        long endTime = System.currentTimeMillis();
        float execTime = (float)(endTime - startTime) / 1000;
        System.out.println("End at: " + endTime);
        System.out.println("Performance: " + execTime + "s");
    }
}
