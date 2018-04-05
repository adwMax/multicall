package net.adwiser.demo.multicall.controllers;

import net.adwiser.demo.multicall.exchanges.MultipleInvocationRequest;
import net.adwiser.demo.multicall.exchanges.MultipleInvocationResponse;
import net.adwiser.demo.multicall.exchanges.SingleInvocationRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

@RestController
public class MultipleInvocationTimingController {

    @PostMapping(value = "/multiple-invocation-timing")
    public MultipleInvocationResponse executeMultipleInvocations(@RequestBody MultipleInvocationRequest request) {
        switch (request.getInvocationStyle()) {
            case SEQUENTIAL:
                return measureInvocations(request, this::getSequentialInvocationResponse);
            case PARALLEL:
                return measureInvocations(request, this::getParallelInvocationResponse);
            default:
                throw new UnsupportedOperationException("Unsupported invocation style: " + request.getInvocationStyle());
        }
    }

    private MultipleInvocationResponse measureInvocations(MultipleInvocationRequest request,
                                                          Consumer<MultipleInvocationRequest> consumer) {
        long invocationStartTime = System.currentTimeMillis();

        consumer.accept(request);

        long totalActualInvocationDelay = System.currentTimeMillis() - invocationStartTime;
        long totalNominalInvocationDelay = request.getResponseDelaysMillis().stream()
                .mapToLong(e -> e)
                .sum();
        return new MultipleInvocationResponse(request.getInvocationStyle(),
                totalActualInvocationDelay, totalNominalInvocationDelay, request.getResponseDelaysMillis());
    }

    private void getSequentialInvocationResponse(MultipleInvocationRequest request) {
        try {
            for (final Long singleInvocationDelay : request.getResponseDelaysMillis()) {
                executeSingleInvocation(singleInvocationDelay).get();
            }
        } catch (Exception e) {
            System.out.println(String.format("Something bad happened: %s", e.getMessage()));
            ///TODO: add actual exception handling
        }
    }

    private void getParallelInvocationResponse(MultipleInvocationRequest request) {
        final Collection<CompletableFuture<Long>> responses = new ArrayList<>(request.getResponseDelaysMillis().size());
        for (final Long singleInvocationDelay : request.getResponseDelaysMillis()) {
            final CompletableFuture<Long> singleInvocationResult = executeSingleInvocation(singleInvocationDelay);
            responses.add(singleInvocationResult);
        }

        responses.stream().map(CompletableFuture::join).mapToLong(Long::longValue).sum();
    }

    private CompletableFuture<Long> executeSingleInvocation(Long singleInvocationDelay) {
        return CompletableFuture.supplyAsync(() -> {
            final RestTemplate restTemplate = new RestTemplate();
            return restTemplate
                    .postForObject(
                            "http://localhost:8080/single-invocation-timing",
                            new SingleInvocationRequest().setDelay(singleInvocationDelay),
                            Long.class);
        });
    }
}