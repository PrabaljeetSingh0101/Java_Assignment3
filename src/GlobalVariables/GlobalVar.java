package GlobalVariables;

public class GlobalVar {
    public static volatile int Total_distance_travelled_by_all = 0;
    public static volatile boolean Play = false;
    public static volatile boolean fixed = false;
    public static final Object distanceLock = new Object();
}
