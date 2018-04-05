package net.adwiser.demo.multicall.controllers;

import net.adwiser.demo.multicall.exchanges.SingleInvocationRequest;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.concurrent.TimeUnit;

@RestController
public class SingleInvocationTimingController {

    @PostMapping(value = "/single-invocation-timing")
    public long getNominalTimingValue(@RequestBody SingleInvocationRequest request) throws InterruptedException {
        long delay = request.getDelay();
        System.out.println(String.format("single invocation nominal delay: %s", delay));
        TimeUnit.MILLISECONDS.sleep(delay);
        return delay;
    }
}