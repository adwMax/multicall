package net.adwiser.demo.multicall.controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/single-invocation-timing")
public class SingleInvocationTimingController {

    @RequestMapping("nominal")
    public @ResponseBody
    Long getNominalTimingValue(Long responseDelayMillis) throws InterruptedException {
        long delay = responseDelayMillis != null ? responseDelayMillis : 0;
        TimeUnit.MILLISECONDS.sleep(delay);
        return delay;
    }
}