package net.adwiser.demo.multicall;

import java.util.Collections;
import java.util.List;

public class InvocationResponse {
    private InvocationStyle invocationStyle;
    private long actualTotalInvocationTime;
    private long nominalTotalInvocationTime;
    private final List<Long> responseDelays;

    public InvocationResponse(InvocationStyle invocationStyle, long actualTotalInvocationTime, long nominalTotalInvocationTime, List<Long> responseDelays) {
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