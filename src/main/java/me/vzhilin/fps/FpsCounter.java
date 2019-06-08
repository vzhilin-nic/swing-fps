package me.vzhilin.fps;

public final class FpsCounter {
    private long time = System.currentTimeMillis();
    private long tick = 0;

    private long period;
    private long ticks;


    public boolean tick() {
        ++tick;

        long delta = System.currentTimeMillis() - time;
        if (delta > EntryPoint.REFRESH_PERIOD_MILLIS) {
            this.period = delta;
            this.ticks = tick;

            reset();
            return true;
        }

        return false;
    }

    public void reset() {
        tick = 0;
        time = System.currentTimeMillis();
    }

    public long ticks() {
        return ticks;
    }

    public float fps() {
        return period == 0 ? 0 : (float) 1000 * ticks / period;
    }
}
