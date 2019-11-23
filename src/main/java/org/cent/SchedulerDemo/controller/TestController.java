package org.cent.SchedulerDemo.controller;

import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Vincent
 * @version 1.0 2019/11/23
 */
@RestController
public class TestController {

    @GetMapping("/getString")
    public String getStr() {
        return "hello world!";
    }

    @GetMapping("/get/{time}")
    public String getTime(@PathVariable String time) {
        return time;
    }

    @GetMapping("/getJson")
    public Object getJson() {
        Map<String, String> result = new HashMap<>();
        result.put("method", "get");
        result.put("body", "json");
        return result;
    }

    @PostMapping("/postString")
    public String postStr(@RequestBody String body) {
        return body;
    }

    @PostMapping("postJson")
    public Object postJson(@RequestBody Object body) {
        return body;
    }
}
