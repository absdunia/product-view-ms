package com.wipro.productviewms;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ProductViewMsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductViewMsApplication.class, args);
	}

}

@RestController
class ClientController{
	
	@Autowired
	private EurekaClient eurekaClient;
	
	@Autowired
	private RestTemplateBuilder restTemplateBuilder;
	
	@RequestMapping("/getAllProducts")
	public String invokeService() {
		RestTemplate restTemplate =  restTemplateBuilder.build();
		
		
		
		//Using Zuul-Gateway
				InstanceInfo instanceInfo = eurekaClient.getNextServerFromEureka("zuul-gateway", false);
				String baseUrl = instanceInfo.getHomePageUrl();
				baseUrl = baseUrl+ "/api/serviceapp/product/all";
				return restTemplate.getForObject(baseUrl, String.class);
		
	}
	
	
		
		
	@RequestMapping("/welcome")	
	public String invokeService2() {
		RestTemplate restTemplate =  restTemplateBuilder.build();
		
		
		
		//Using Zuul-Gateway
				InstanceInfo instanceInfo = eurekaClient.getNextServerFromEureka("zuul-gateway", false);
				String baseUrl = instanceInfo.getHomePageUrl();
				baseUrl = baseUrl+ "/api/serviceapp/product/welcome";
				return restTemplate.getForObject(baseUrl, String.class);
		
	}
	
	@RequestMapping("/hello")
	public String invokeService3() {
		RestTemplate restTemplate =  restTemplateBuilder.build();
		
		
		
		//Using Zuul-Gateway
				InstanceInfo instanceInfo = eurekaClient.getNextServerFromEureka("zuul-gateway", false);
				String baseUrl = instanceInfo.getHomePageUrl();
				baseUrl = baseUrl+ "/api/serviceapp/product/";
				return restTemplate.getForObject(baseUrl, String.class);
		
	}

}
