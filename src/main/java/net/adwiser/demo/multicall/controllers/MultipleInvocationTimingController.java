package net.adwiser.demo.multicall.controllers;

import net.adwiser.demo.multicall.InvocationRequest;
import net.adwiser.demo.multicall.InvocationResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
public class MultipleInvocationTimingController {

    @PostMapping(value = "/multiple-invocation-timing")
    public InvocationResponse executeMultipleInvocations(@RequestBody InvocationRequest request) {
        switch (request.getInvocationStyle()) {
            case SEQUENTIAL:
                long totalActualInvocationDelay = 0L;
                long totalNominalInvocationDelay = 0L;
                for (final Long singleInvocationDelay : request.getResponseDelaysMillis()) {
                    final RestTemplate restTemplate = new RestTemplate();
                    final URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath()
                            .path("/single-invocation-timing")
                            .path("/nominal")
                            .queryParam("responseDelayMillis", singleInvocationDelay)
                            .build()
                            .toUriString()
                    );
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