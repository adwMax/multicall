package net.adwiser.demo.multicall.exchanges;

public class SingleInvocationRequest {
    private long delay;

    public long getDelay() {
        return delay;
    }

    public SingleInvocationRequest setDelay(long delay) {
        this.delay = delay;
        return this;
    }
}