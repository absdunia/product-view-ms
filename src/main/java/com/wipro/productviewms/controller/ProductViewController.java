package com.wipro.productviewms.controller;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.wipro.productviewms.bean.Inventory;
import com.wipro.productviewms.bean.Price;
import com.wipro.productviewms.bean.Product;
import com.wipro.productviewms.bean.ProductView;
import com.wipro.productviewms.bean.Promotion;

@RestController
public class ProductViewController {
	
	@Autowired
	private EurekaClient eurekaClient;
	
	@Autowired
	private RestTemplateBuilder restTemplateBuilder;
		
		
	@RequestMapping("/welcome")
	@LoadBalanced
	@HystrixCommand(fallbackMethod = "defaultGreeting")
	public String invokeService2() {
		RestTemplate restTemplate =  restTemplateBuilder.build();
		
		//Using Zuul-Gateway
		InstanceInfo instanceInfo = eurekaClient.getNextServerFromEureka("zuul-gateway", false);
		String baseUrl = instanceInfo.getHomePageUrl();
		baseUrl = baseUrl+ "/api/serviceapp/eCommerce/product/welcome";
		return restTemplate.getForObject(baseUrl, String.class);
		
	}
	
	@RequestMapping("/hello")
	@LoadBalanced
	@HystrixCommand(fallbackMethod = "defaultGreeting")
	public String invokeService3() {
		RestTemplate restTemplate =  restTemplateBuilder.build();
		
		//Using Zuul-Gateway
		InstanceInfo instanceInfo = eurekaClient.getNextServerFromEureka("zuul-gateway", false);
		String baseUrl = instanceInfo.getHomePageUrl();
		baseUrl = baseUrl+ "/api/serviceapp/eCommerce/product/";
		return restTemplate.getForObject(baseUrl, String.class);
		
	}
	
	
	@RequestMapping("/getAllProducts")
	@LoadBalanced
	@HystrixCommand(fallbackMethod = "defaultProductService")
	public String invokeService() {
		RestTemplate restTemplate =  restTemplateBuilder.build();
		
		//Using Zuul-Gateway
		InstanceInfo instanceInfo = eurekaClient.getNextServerFromEureka("zuul-gateway", false);
		String baseUrl = instanceInfo.getHomePageUrl();
		baseUrl = baseUrl+ "/api/serviceapp/eCommerce/product/getAllProducts";
		return restTemplate.getForObject(baseUrl, String.class);
		
	}
	
	@RequestMapping("/getAllPrices")
	@LoadBalanced
	@HystrixCommand(fallbackMethod = "defaultPriceService")
	public String invokeService4() {
		RestTemplate restTemplate =  restTemplateBuilder.build();
		
		//Using Zuul-Gateway
		InstanceInfo instanceInfo = eurekaClient.getNextServerFromEureka("zuul-gateway", false);
		String baseUrl = instanceInfo.getHomePageUrl();
		baseUrl = baseUrl+ "/api/serviceapp2/eCommerce/price/getAllPrices";
		return restTemplate.getForObject(baseUrl, String.class); 
		
	}
	
	
	@RequestMapping("/getAllInventoryDetails")
	@LoadBalanced
	@HystrixCommand(fallbackMethod = "defaultInventoryService")
	public String invokeService6() {
		RestTemplate restTemplate =  restTemplateBuilder.build();
		
		//Using Zuul-Gateway
		InstanceInfo instanceInfo = eurekaClient.getNextServerFromEureka("zuul-gateway", false);
		String baseUrl = instanceInfo.getHomePageUrl();
		baseUrl = baseUrl+ "/api/serviceapp4/eCommerce/inventory/getAllInventoryDetails";
		return restTemplate.getForObject(baseUrl, String.class); 
		
	}
	
	
	@RequestMapping("/getAllPromotions")
	@LoadBalanced
	@HystrixCommand(fallbackMethod = "defaultPromotionService")
	public String invokeService7() {
		RestTemplate restTemplate =  restTemplateBuilder.build();
		
		//Using Zuul-Gateway
		InstanceInfo instanceInfo = eurekaClient.getNextServerFromEureka("zuul-gateway", false);
		String baseUrl = instanceInfo.getHomePageUrl();
		baseUrl = baseUrl+ "/api/serviceapp3/eCommerce/promotion/getAllPromotions";
		return restTemplate.getForObject(baseUrl, String.class);
	}
	
	@RequestMapping("/viewAllDetails")
	public List<ProductView> invokeService5() {
		 
	   List<ProductView> pv = new ArrayList<ProductView>();
		
		
		RestTemplate restTemplate =  restTemplateBuilder.build();
		
		//Get All Products
		InstanceInfo instanceInfo = eurekaClient.getNextServerFromEureka("zuul-gateway", false);
		String baseUrl = instanceInfo.getHomePageUrl();
		baseUrl = baseUrl+ "/api/serviceapp/eCommerce/product/getAllProducts";
		Product[] response  = restTemplate.getForObject(baseUrl, Product[].class);
		ProductView productView = null;
		
		//Get All Prices
		InstanceInfo instanceInfo1 = eurekaClient.getNextServerFromEureka("zuul-gateway", false);
		String baseUrl1 = instanceInfo1.getHomePageUrl();
		baseUrl1 = baseUrl1+ "/api/serviceapp2/eCommerce/price/getAllPrices";
		Price[] response1  = restTemplate.getForObject(baseUrl1, Price[].class);
		
		//Get All Promotion
		InstanceInfo instanceInfo2 = eurekaClient.getNextServerFromEureka("zuul-gateway", false);
		String baseUrl2 = instanceInfo2.getHomePageUrl();
		baseUrl2 = baseUrl2+ "/api/serviceapp3/eCommerce/promotion/getAllPromotions";
		Promotion[] response2  = restTemplate.getForObject(baseUrl2, Promotion[].class);
		
		//Get All Inventory
		InstanceInfo instanceInfo3 = eurekaClient.getNextServerFromEureka("zuul-gateway", false);
		String baseUrl3 = instanceInfo3.getHomePageUrl();
		baseUrl3 = baseUrl3+ "/api/serviceapp4/eCommerce/inventory/getAllInventoryDetails";
		Inventory[] response3  = restTemplate.getForObject(baseUrl3, Inventory[].class);

		
		//Forming ProductView service
		for(int i =0; i<response.length; i++) {
			productView = new ProductView();
			productView.setId(response[i].getId());
			productView.setProductName(response[i].getProductName());
			productView.setProductCode(response[i].getProductCode());
			productView.setProductDesc(response[i].getProductDesc());
			productView.setProductAddedOn(response[i].getProductAddedOn());
			productView.setPrice(response1[i].getPrice());
			productView.setPromotion1(response2[i].getPromotion1());
			productView.setPromotion2(response2[i].getPromotion2());
			productView.setPromotion3(response2[i].getPromotion3());
			productView.setStartDate(response2[i].getStartDate());
			productView.setEndDate(response2[i].getEndDate());
			productView.setQuantity(response3[i].getQuantity());
			productView.setSupplierDetails(response3[i].getSupplierDetails());
			pv.add(i, productView);
		}
		return pv;
	}	
	
	
	@RequestMapping("/viewAllDetailById")
	public List<ProductView> invokeService6(@RequestParam String productID) {
		 
	   List<ProductView> pv = new ArrayList<ProductView>();
		
		
		RestTemplate restTemplate =  restTemplateBuilder.build();
		
		//Get All Products
		InstanceInfo instanceInfo = eurekaClient.getNextServerFromEureka("zuul-gateway", false);
		String baseUrl = instanceInfo.getHomePageUrl();
		baseUrl = baseUrl+ "/api/serviceapp/eCommerce/product/getAllProducts";
		Product[] response  = restTemplate.getForObject(baseUrl, Product[].class);
		ProductView productView = null;
		
		//Get All Prices
		InstanceInfo instanceInfo1 = eurekaClient.getNextServerFromEureka("zuul-gateway", false);
		String baseUrl1 = instanceInfo1.getHomePageUrl();
		baseUrl1 = baseUrl1+ "/api/serviceapp2/eCommerce/price/getAllPrices";
		Price[] response1  = restTemplate.getForObject(baseUrl1, Price[].class);
		
		//Get All Promotion
		InstanceInfo instanceInfo2 = eurekaClient.getNextServerFromEureka("zuul-gateway", false);
		String baseUrl2 = instanceInfo2.getHomePageUrl();
		baseUrl2 = baseUrl2+ "/api/serviceapp3/eCommerce/promotion/getAllPromotions";
		Promotion[] response2  = restTemplate.getForObject(baseUrl2, Promotion[].class);
		
		//Get All Inventory
		InstanceInfo instanceInfo3 = eurekaClient.getNextServerFromEureka("zuul-gateway", false);
		String baseUrl3 = instanceInfo3.getHomePageUrl();
		baseUrl3 = baseUrl3+ "/api/serviceapp4/eCommerce/inventory/getAllInventoryDetails";
		Inventory[] response3  = restTemplate.getForObject(baseUrl3, Inventory[].class);

		
		//Forming ProductView service
		for(int i =0; i<response.length; i++) {
			if(response[i].getId().toString().equalsIgnoreCase(productID)) {
				productView = new ProductView();
				productView.setId(response[i].getId());
				productView.setProductName(response[i].getProductName());
				productView.setProductCode(response[i].getProductCode());
				productView.setProductDesc(response[i].getProductDesc());
				productView.setProductAddedOn(response[i].getProductAddedOn());
				productView.setPrice(response1[i].getPrice());
				productView.setPromotion1(response2[i].getPromotion1());
				productView.setPromotion2(response2[i].getPromotion2());
				productView.setPromotion3(response2[i].getPromotion3());
				productView.setStartDate(response2[i].getStartDate());
				productView.setEndDate(response2[i].getEndDate());
				productView.setQuantity(response3[i].getQuantity());
				productView.setSupplierDetails(response3[i].getSupplierDetails());
				pv.add(productView);
				break;
		}
			
		}
		return pv;
	}	
	private String defaultGreeting() {
		return "Error in Service !!!!! Please try later";
	}
	private String defaultProductService() {
		return "Product Service is down!!!!! Please try later";
	}
	private String defaultPriceService() {
		return "Price Service is down!!!!! Please try later";
	}
	private String defaultInventoryService() {
		return "Inventory Service is down!!!!! Please try later";
	}
	private String defaultPromotionService() {
		return "Promotion Service is down !!!!! Please try later";
	}	
	/*private String defaultViewAllService() {
		return "Issue in calling multiple services!!!!! Please try later";
	}
	private String defaultViewAllByIdService() {
		return "Issue in calling multiple services!!!!! Please try later";
	}*/

}
