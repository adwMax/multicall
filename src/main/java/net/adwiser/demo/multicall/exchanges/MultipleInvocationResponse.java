package net.adwiser.demo.multicall.exchanges;

import java.util.Collections;
import java.util.List;

@SuppressWarnings("unused")
public class MultipleInvocationResponse {
    private final InvocationStyle invocationStyle;
    private long actualTotalInvocationTime;
    private long nominalTotalInvocationTime;
    private final List<Long> responseDelays;

    public MultipleInvocationResponse(InvocationStyle invocationStyle, long actualTotalInvocationTime, long nominalTotalInvocationTime, List<Long> responseDelays) {
        this.invocationStyle = invocationStyle;
        this.actualTotalInvocationTime = actualTotalInvocationTime;
        this.nominalTotalInvocationTime = nominalTotalInvocationTime;
        this.responseDelays = Collections.unmodifiableList(responseDelays);
    }

    public InvocationStyle getInvocationStyle() {
        return invocationStyle;
    }

    public long getActualTotalInvocationTime() {
        return actualTotalInvocationTime;
    }

    public long getNominalTotalInvocationTime() {
        return nominalTotalInvocationTime;
    }

    public List<Long> getResponseDelays() {
        return responseDelays;
    }
}