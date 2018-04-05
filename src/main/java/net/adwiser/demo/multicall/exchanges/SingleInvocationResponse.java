package net.adwiser.demo.multicall.exchanges;

public class SingleInvocationResponse {
    private long nominalDelay;
    private long actualDelay;

    public long getNominalDelay() {
        return nominalDelay;
    }

    public void setNominalDelay(long nominalDelay) {
        this.nominalDelay = nominalDelay;
    }

    public long getActualDelay() {
        return actualDelay;
    }

    public void setActualDelay(long actualDelay) {
        this.actualDelay = actualDelay;
    }
}