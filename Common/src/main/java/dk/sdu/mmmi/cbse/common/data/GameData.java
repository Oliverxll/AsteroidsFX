package dk.sdu.mmmi.cbse.common.data;

public class GameData {

    private int displayWidth  = 800 ;
    private int displayHeight = 800;
    private final GameKeys keys = new GameKeys();

    private long lastFrameTime;

    private double deltaTime;


    public GameKeys getKeys() {
        return keys;
    }

    public void setDisplayWidth(int width) {
        this.displayWidth = width;
    }

    public int getDisplayWidth() {
        return displayWidth;
    }

    public void setDisplayHeight(int height) {
        this.displayHeight = height;
    }

    public int getDisplayHeight() {
        return displayHeight;
    }

    public void setDeltaTime() {
        long currentTime = System.nanoTime();
        if (lastFrameTime > 0) {
            long deltaTime = currentTime - lastFrameTime;
            this.deltaTime = deltaTime / 1_000_000_000.0; // Convert from nanoseconds to seconds
        }
        lastFrameTime = currentTime;
    }

    public double getDeltaTime() {
        return deltaTime;
    }

}
