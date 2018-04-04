package net.adwiser.demo.multicall.controllers;

import net.adwiser.demo.multicall.InvocationRequest;
import net.adwiser.demo.multicall.InvocationResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

@RestController
public class MultipleInvocationTimingController {

    @PostMapping(value = "/multiple-invocation-timing")
    public InvocationResponse executeMultipleInvocations(@RequestBody InvocationRequest request) {
        switch (request.getInvocationStyle()) {
            case SEQUENTIAL:
                return getSequentialInvocationResponse(request);
            case PARALLEL:
                return getParallelInvocationResponse(request);
            default:
                throw new UnsupportedOperationException("Unsupported invocation style: " + request.getInvocationStyle());
        }
    }

    private InvocationResponse getSequentialInvocationResponse(@RequestBody InvocationRequest request) {
        long totalActualInvocationDelay = 0L;
        long totalNominalInvocationDelay = 0L;
        for (final Long singleInvocationDelay : request.getResponseDelaysMillis()) {
            final RestTemplate restTemplate = new RestTemplate();
            final URI uri = URI.create(String.format("http://localhost:8080/single-invocation-timing/nominal?responseDelayMillis=%s", singleInvocationDelay));
            long invocationStartTime = System.currentTimeMillis();
            final Long nominalSingleInvocationDelay = restTemplate.getForObject(uri, Long.class);
            long actualSingleInvocationTime = System.currentTimeMillis() - invocationStartTime;
            totalNominalInvocationDelay += nominalSingleInvocationDelay;
            totalActualInvocationDelay += actualSingleInvocationTime;
        }
        return new InvocationResponse(request.getInvocationStyle(),
                totalActualInvocationDelay, totalNominalInvocationDelay, request.getResponseDelaysMillis());
    }

    private InvocationResponse getParallelInvocationResponse(@RequestBody InvocationRequest request) {
        long invocationStartTime = System.currentTimeMillis();
        final Collection<CompletableFuture<Long>> responses = new ArrayList<>(request.getResponseDelaysMillis().size());

        for (final Long singleInvocationDelay : request.getResponseDelaysMillis()) {
            long delay = singleInvocationDelay;
            final CompletableFuture<Long> singleInvocationResult = CompletableFuture.supplyAsync(() -> {
                final RestTemplate restTemplate = new RestTemplate();
                final URI uri = URI.create(String.format("http://localhost:8080/single-invocation-timing/nominal?responseDelayMillis=%s", delay));
                return restTemplate.getForObject(uri, Long.class);
            });
            responses.add(singleInvocationResult);
        }
        long totalNominalInvocationDelay = responses.stream()
                .map(CompletableFuture::join)
                .mapToLong(Long::longValue)
                .sum();
        long totalActualInvocationDelay = System.currentTimeMillis() - invocationStartTime;
        return new InvocationResponse(request.getInvocationStyle(),
                totalActualInvocationDelay, totalNominalInvocationDelay, request.getResponseDelaysMillis());
    }
}