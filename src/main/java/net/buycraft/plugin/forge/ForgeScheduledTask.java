package net.buycraft.plugin.forge;

public class ForgeScheduledTask {

    private Runnable task;
    private boolean async;

    private long delay;
    private long currentDelay;

    private long interval;
    private long currentIntervalTicks = 0;

    public Runnable getTask() {
        return task;
    }

    public boolean isAsync() {
        return async;
    }

    public long getDelay() {
        return delay;
    }

    public long getInterval() {
        return interval;
    }

    long getCurrentDelay() {
        return currentDelay;
    }

    void setCurrentDelay(long currentDelay) {
        this.currentDelay = currentDelay;
    }

    long getCurrentIntervalTicks() {
        return currentIntervalTicks;
    }

    void setCurrentIntervalTicks(long currentIntervalTicks) {
        this.currentIntervalTicks = currentIntervalTicks;
    }

    public static final class Builder {
        private Runnable task;
        private boolean async;
        private long delay = -1;
        private long interval = -1;

        private Builder() {
        }

        public static Builder create() {
            return new Builder();
        }

        public Builder withTask(Runnable task) {
            this.task = task;
            return this;
        }

        public Builder withAsync(boolean async) {
            this.async = async;
            return this;
        }

        public Builder withDelay(long delay) {
            this.delay = delay;
            return this;
        }

        public Builder withInterval(long interval) {
            this.interval = interval;
            return this;
        }

        public ForgeScheduledTask build() {
            if (delay <= -1 && interval <= -1)
                throw new IllegalStateException("Must either have a delay or an interval");
            ForgeScheduledTask forgeScheduledTask = new ForgeScheduledTask();
            forgeScheduledTask.interval = this.interval;
            forgeScheduledTask.async = this.async;
            forgeScheduledTask.delay = this.delay;
            forgeScheduledTask.currentDelay = this.delay;
            forgeScheduledTask.task = this.task;
            return forgeScheduledTask;
        }
    }
}
