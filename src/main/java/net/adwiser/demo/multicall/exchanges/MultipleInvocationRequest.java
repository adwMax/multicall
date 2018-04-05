package net.adwiser.demo.multicall.exchanges;

import java.util.Collections;
import java.util.List;

public class MultipleInvocationRequest {
    private InvocationStyle invocationStyle;
    private List<Long> responseDelaysMillis;

    public MultipleInvocationRequest() {
    }

    public MultipleInvocationRequest(InvocationStyle invocationStyle, List<Long> responseDelaysMillis) {
        this.invocationStyle = invocationStyle;
        this.responseDelaysMillis = Collections.unmodifiableList(responseDelaysMillis);
    }

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