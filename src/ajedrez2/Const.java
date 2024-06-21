package ajedrez2;

public class Const {

    private static final String os = System.getProperty("os.name").toLowerCase();

    public static final int SUP_BAR_HEIGHT = (os.contains("win")) ? 39 : 25;
    public static final int SQR_SIDE = 60;
    public static final int H_SQR_SIDE =  (os.contains("win")) ? 37 : 30;
    public static final int H_SQR_SIDE_Y =  (os.contains("win")) ? 23 : 30;
}
