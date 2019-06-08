package me.vzhilin.fps;

public class EntryPoint {
    public static final int REFRESH_PERIOD_MILLIS = 500;

    /** window */
    private SwingFpsFrame jframe;


    public static void main(String... argv) {
        new EntryPoint().start();
    }

    private void start() {
        jframe = new SwingFpsFrame();
        jframe.setVisible(true);
    }

}
