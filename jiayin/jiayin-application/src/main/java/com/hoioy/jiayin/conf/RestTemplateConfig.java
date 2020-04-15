package com.hoioy.jiayin.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemplateConfig {

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate(simpleClientHttpRequestFactory());
    }

    @Bean
    public ClientHttpRequestFactory simpleClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setReadTimeout(15000);// ms
        factory.setConnectTimeout(15000);// ms
        return factory;
    }
    
   /* @Bean
    public HttpsValidationDisable getHttpsURLConnection() throws Exception
    {
    HttpsValidationDisable httpDisable = new HttpsValidationDisable();
	HttpsValidationDisable.trustAllHttpsCertificates();  
	HttpsURLConnection.setDefaultHostnameVerifier(httpDisable.hv);  
    return httpDisable;
    }*/

}
