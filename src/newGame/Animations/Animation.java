package newGame.Animations;

/**
 * This abstract class will define
 * some data members and functions that will
 * be required for animations.
 */
public abstract class Animation {

    public static final int MINIMUM_DURATION = 100; // The minimum length of an animation.
    public static final int MAXIMUM_DURATION = 5000; // The maximum length of an animation.

    public static final int MINIMUM_MILLISECONDS_PER_TICK = 10; // The minimum time for each tick.
    public static final int MAXIMUM_MILLISECONDS_PER_TICK = 500; // The maximum time for each tick.

    /*
     * Data members of this Animation class.
     */
    private boolean isRunning = false;
    private int durationMilliseconds = 0;
    private int millisecondsPerTick = 1000 / 12;

    private int currentTickCount = 0;
    private int elapsedMilliseconds = 0;

    /**
     * Default constructor for the
     * Animation class.
     */
    public Animation()  {

    }

    /**
     * Says whether this animations is currently running.
     * @return Whether this animation is running.
     */
    public boolean isRunning() {
        return isRunning;
    }

    /**
     * Sets the state of this animation on whether this is running.
     * @param nIsRunning A new state of the runtime of this animation.
     */
    public void setIsRunning(boolean nIsRunning) {
        isRunning = nIsRunning;
    }

    /**
     * Gets the set duration of this animation in milliseconds.
     * @return Set duration of this animation in milliseconds.
     */
    public int getDurationInMilliseconds() {
        return durationMilliseconds;
    }

    /**
     * Sets the duration of this animation in milliseconds.
     * @param newDurationMilliseconds New duration in milliseconds.
     */
    public void setDurationInMilliseconds(int newDurationMilliseconds) {
        durationMilliseconds = Math.max(Math.min(newDurationMilliseconds, MAXIMUM_DURATION), MINIMUM_DURATION);
    }

    /**
     * Gets the current amount of time for each tick.
     * @return The amount of time per tick.
     */
    public int getMillisecondsPerTick() {
        return millisecondsPerTick;
    }

    /**
     * Sets a new milliseconds per tick value.
     * @param newMillisecondsPerTick New milliseconds per tick value.
     */
    public void setMillisecondsPerTick(int newMillisecondsPerTick) {
        millisecondsPerTick = Math.max(Math.min(newMillisecondsPerTick, MAXIMUM_MILLISECONDS_PER_TICK), MINIMUM_MILLISECONDS_PER_TICK);
    }

    /**
     * Starts this animation on the current
     * thread.
     */
    public void start() {
        double delta = 0;

        isRunning = true;
        onBegin();

        double old = System.currentTimeMillis();

        while(isRunning && elapsedMilliseconds <= durationMilliseconds) {
            double now = System.currentTimeMillis();
            delta += (now - old) / millisecondsPerTick;
            elapsedMilliseconds += Math.round(now - old);
            old = now;

            if(delta >= 1) {
                onTick();
                delta--;
            }
        }

        onEnd();
    }

    /**
     * This function will be called
     * just before thetimer
     */
    public abstract void onBegin();

    /**
     * This function will execute code when
     * the timer passes each tick in the
     * 'start' function.
     */
    public abstract void onTick();

    /**
     * This function will be called when
     * the timer has finished and the time
     * has elapsed.
     */
    public abstract void onEnd();
}
