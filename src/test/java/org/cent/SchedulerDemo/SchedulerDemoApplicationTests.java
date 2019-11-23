package org.cent.SchedulerDemo;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SchedulerDemoApplicationTests {

    @LocalServerPort
    private int port;

	@Autowired
    private RestTemplate restTemplate;

	@Test
	void restClientExec() {
	    String host = "http://localhost:"+port;
	    String url = host + "/getString";
		System.out.println(restTemplate.getForObject(url, String.class));

		url = host+"/get/{1}";
        System.out.println(restTemplate.getForObject(url, String.class, new Date()));

		url = host + "/getJson";
        System.out.println(restTemplate.getForObject(url, String.class));

        url = host + "/postString";
        System.out.println(restTemplate.postForObject(url, "Hello World!", String.class));

        url = host + "/postJson";
        Map<String, String> body = new HashMap<>();
        body.put("method", "post");
        body.put("content-type", "json");
        System.out.println(restTemplate.postForObject(url, body, String.class));
	}

	@Test
	void contextLoads() {
	}

}
