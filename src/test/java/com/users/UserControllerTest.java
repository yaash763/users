package com.users;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.stubbing.Answer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.users.controller.UserController;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserControllerTest {

	@InjectMocks
	UserController controller;
	
	@Mock
	RestTemplate restTemplate;
	
	@Test
	public void test()
	{
		System.out.println("Simple test");
		
		List<Map> reg = new ArrayList<Map>();
		 String regUserJson = "{\"id\":\"1\",\"city\":\"Jaydashire\","
		 		+ "\"company\":\"Goyette - Renner\",\"country\":\"South Africa\","
		 		+ "\"firstName\":\"firstName 1\",\"lastName\":\"lastName 1\","
		 		+ "\"organizationType\":\"organizationType 1\"}";
		 ResponseEntity<Object> regMap =  null;
		
		 List<Map> unReg = new ArrayList<Map>();
		 String unRegUserJson = "{\"id\":\"2\",\"city\":\"Jaydashire\","
		 		+ "\"company\":\"Goyette - Renner\",\"country\":\"South Africa\","
		 		+ "\"firstName\":\"firstName 2\",\"lastName\":\"lastName 2\","
		 		+ "\"organizationType\":\"organizationType 1\"}";
		 ResponseEntity<Object> unRegMap =  null;
			
		 
		 List<Map> userProjectList = new ArrayList<Map>();
		 String userProject1 = "{\"id\":\"1\",\"projectId\":\"1\",\"userId\":\"1\"}";
		 
		 String userProject2 = "{\"id\":\"2\",\"projectId\":\"1\",\"userId\":\"2\"}";
			 
		 ResponseEntity<Object> userProjMap =  null;
		
		 
		 ObjectMapper mapper = new ObjectMapper();

		  try {
	            //convert JSON string to Map
			  Map map = mapper.readValue(regUserJson, new TypeReference<HashMap<String, String>>() {});
			    reg.add(map);
			    regMap =  new ResponseEntity<Object>(reg,HttpStatus.OK);
	            System.out.println(regMap);
	            
	            map = mapper.readValue(unRegUserJson, new TypeReference<HashMap<String, String>>() {});
			    unReg.add(map);
			    unRegMap =  new ResponseEntity<Object>(unReg,HttpStatus.OK);
	            System.out.println(unRegMap);
	            
	            map = mapper.readValue(userProject1, new TypeReference<HashMap<String, String>>() {});
	            userProjectList.add(map);
	            userProjMap =  new ResponseEntity<Object>(userProjectList,HttpStatus.OK);
	            System.out.println(userProjMap);
	           
	            map = mapper.readValue(userProject2, new TypeReference<HashMap<String, String>>() {});
	            userProjectList.add(map);
	            userProjMap =  new ResponseEntity<Object>(userProjectList,HttpStatus.OK);
	            System.out.println(userProjMap);
	            
	           
		  } catch (Exception e) {
	            e.printStackTrace();
	        }
		  
		  when(restTemplate.exchange(
				    "https://5c3ce12c29429300143fe570.mockapi.io/api/registeredusers", 
				    HttpMethod.GET, 
				    null, 
				    new ParameterizedTypeReference<Object>() {
				    })).thenReturn(regMap);
		  
		  when(restTemplate.exchange(
				    "https://5c3ce12c29429300143fe570.mockapi.io/api/unregisteredusers", 
				    HttpMethod.GET, 
				    null, 
				    new ParameterizedTypeReference<Object>() {
				    })).thenReturn(unRegMap);
			
		  when(restTemplate.exchange(
				    "https://5c3ce12c29429300143fe570.mockapi.io/api/projectmemberships", 
				    HttpMethod.GET, 
				    null, 
				    new ParameterizedTypeReference<Object>() {
				    })).thenReturn(userProjMap);
			
		
		  ResponseEntity<Object> entity =  controller.test();
		List<Map> list =  (List<Map>) entity.getBody();
		assertEquals(( (List<String>)list.get(0).get("projectIds")).get(0),"1");
		
		System.out.println("Simple test");
	}
}
