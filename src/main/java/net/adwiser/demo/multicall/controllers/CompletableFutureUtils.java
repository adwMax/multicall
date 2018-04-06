package net.adwiser.demo.multicall.controllers;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

class CompletableFutureUtils {

    //borrowed from http://www.nurkiewicz.com/2013/05/java-8-completablefuture-in-action.html
    public static <T> CompletableFuture<List<T>> sequence(Collection<CompletableFuture<T>> futures) {
        CompletableFuture<Void> allDoneFuture =
                CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
        return allDoneFuture.thenApply(v ->
                futures.stream().
                        map(CompletableFuture::join).
                        collect(Collectors.toList())
        );
    }
}