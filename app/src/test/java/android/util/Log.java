package android.util;

public class Log {

    public static int e(String tag, String message) {
        System.out.println(tag + ": " + message);
        return 0;
    }
}
