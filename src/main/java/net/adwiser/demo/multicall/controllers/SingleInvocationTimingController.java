package net.adwiser.demo.multicall.controllers;

import net.adwiser.demo.multicall.exchanges.SingleInvocationRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@SuppressWarnings("unused")
public class SingleInvocationTimingController {

    @PostMapping(value = "/single-invocation-timing")
    public long getNominalTimingValue(@RequestBody SingleInvocationRequest request) throws InterruptedException {
        long delay = request.getDelay();
        TimeUnit.MILLISECONDS.sleep(delay);
        return delay;
    }
}