package org.cent.SchedulerDemo.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * 请求Restful服务的http client配置类
 * @author Vincent
 * @version 1.0 2019/11/23
 */
@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
//        RestTemplate restTemplate = new RestTemplate();
        RestTemplate restTemplate = new RestTemplate(clientHttpRequestFactory());

        List<HttpMessageConverter<?>> httpMessageConverters = restTemplate.getMessageConverters();
        List<HttpMessageConverter<?>> newHttpMessageConverters = new ArrayList<>(httpMessageConverters.size());
        for (HttpMessageConverter<?> msgConverter : httpMessageConverters) {
            // 使用fastjson替换默认http json报文转换器
            if (msgConverter instanceof MappingJackson2HttpMessageConverter) {
                // fastjson配置
                FastJsonConfig fastJsonConfig = new FastJsonConfig();
                fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteBigDecimalAsPlain,
                        SerializerFeature.WriteMapNullValue, SerializerFeature.QuoteFieldNames);
                FastJsonHttpMessageConverter fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
                fastJsonHttpMessageConverter.setFastJsonConfig(fastJsonConfig);
                List<MediaType> mediaTypes = new ArrayList<>(1);
                mediaTypes.add(MediaType.APPLICATION_JSON);
                fastJsonHttpMessageConverter.setSupportedMediaTypes(mediaTypes);
                newHttpMessageConverters.add(fastJsonHttpMessageConverter);
            }
            else {
                newHttpMessageConverters.add(msgConverter);
            }
        }
        restTemplate.setMessageConverters(newHttpMessageConverters);

        return restTemplate;
    }

    @Bean
    public ClientHttpRequestFactory clientHttpRequestFactory() {

        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory(httpClient());
        // 连接池连接不够用等待时间
        requestFactory.setConnectionRequestTimeout(30*1000);
        // tcp连接超时时间
        requestFactory.setConnectTimeout(30*1000);
        // 数据读取超时时间
        requestFactory.setReadTimeout(60*1000);

        return requestFactory;
    }

    @Bean
    public HttpClient httpClient() {

        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        // 连接池管理器
        PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();
        // 最大连接数
        poolingHttpClientConnectionManager.setMaxTotal(100);
        // 同路由并发数
        poolingHttpClientConnectionManager.setDefaultMaxPerRoute(20);
        httpClientBuilder.setConnectionManager(poolingHttpClientConnectionManager);

        return httpClientBuilder.build();
    }
}
