package net.adwiser.demo.multicall.exchanges;

import java.util.List;

@SuppressWarnings("unused")
public class MultipleInvocationRequest {
    private InvocationStyle invocationStyle;
    private List<Long> responseDelaysMillis;

    public InvocationStyle getInvocationStyle() {
        return invocationStyle;
    }

    public void setInvocationStyle(InvocationStyle invocationStyle) {
        this.invocationStyle = invocationStyle;
    }

    public List<Long> getResponseDelaysMillis() {
        return responseDelaysMillis;
    }

    public void setResponseDelaysMillis(List<Long> responseDelaysMillis) {
        this.responseDelaysMillis = responseDelaysMillis;
    }
}