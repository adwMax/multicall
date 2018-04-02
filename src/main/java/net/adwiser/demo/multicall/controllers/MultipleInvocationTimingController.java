package net.adwiser.demo.multicall.controllers;

import net.adwiser.demo.multicall.InvocationRequest;
import net.adwiser.demo.multicall.InvocationResponse;
import net.adwiser.demo.multicall.PortProvider;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@RestController
public class MultipleInvocationTimingController {

    private final PortProvider portProvider;

    public MultipleInvocationTimingController(PortProvider portProvider){
        this.portProvider = portProvider;
    }

    @PostMapping(value = "/measure")
    public InvocationResponse measure(@RequestBody InvocationRequest request) {
        switch (request.getInvocationStyle()) {
            case SEQUENTIAL:
                long totalActualInvocationDelay = 0L;
                long totalNominalInvocationDelay = 0L;
                for (final Long singleInvocationDelay : request.getResponseDelaysMillis()) {
                    final RestTemplate restTemplate = new RestTemplate();
                    final URI uri = URI.create(String.format("http://localhost:%s/single-invocation/nominal?responseDelayMillis=%s", portProvider.getPort(), singleInvocationDelay));
                    long invocationStartTime = System.currentTimeMillis();
                    final Long nominalSingleInvocationDelay = restTemplate.getForObject(uri, Long.class);
                    long actualSingleInvocationTime = System.currentTimeMillis() - invocationStartTime;
                    totalNominalInvocationDelay += nominalSingleInvocationDelay;
                    totalActualInvocationDelay += actualSingleInvocationTime;
                }
                return new InvocationResponse(request.getInvocationStyle(), totalActualInvocationDelay, totalNominalInvocationDelay, request.getResponseDelaysMillis());
            default:
                throw new UnsupportedOperationException("Unsupported invocation style: " + request.getInvocationStyle());
        }
    }
}