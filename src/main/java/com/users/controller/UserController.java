package com.users.controller;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.websocket.server.PathParam;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class UserController {
	
	@Autowired
	private RestTemplate restTemplate;
	
	@RequestMapping(method = RequestMethod.GET, value = "/api/allusers")
	
	public @ResponseBody ResponseEntity<Object>  test()
	{
		ResponseEntity<Object> retisteredEntity = restTemplate.exchange(
			    "https://5c3ce12c29429300143fe570.mockapi.io/api/registeredusers", 
			    HttpMethod.GET, 
			    null, 
			    new ParameterizedTypeReference<Object>() {
			    });
		ResponseEntity<Object> unRetisteredEntity = restTemplate.exchange(
			    "https://5c3ce12c29429300143fe570.mockapi.io/api/unregisteredusers", 
			    HttpMethod.GET, 
			    null, 
			    new ParameterizedTypeReference<Object>() {
			    });
		
		ResponseEntity<Object> userProjects = restTemplate.exchange(
			    "https://5c3ce12c29429300143fe570.mockapi.io/api/projectmemberships", 
			    HttpMethod.GET, 
			    null, 
			    new ParameterizedTypeReference<Object>() {
			    });
		
			List<Map> regUserDetails = (List<Map>) retisteredEntity.getBody();
			List<Map> unRegUserDetails = (List<Map>) unRetisteredEntity.getBody();
			List<Map> userProjectDetails = (List<Map>) userProjects.getBody();
			regUserDetails.addAll(unRegUserDetails);
			
			
			
			for(Map  map: regUserDetails)
			{
				List<String> list = new ArrayList<String>();
			
				
				String userId = (String) map.get("id");
				
				for(Map userProjMap : userProjectDetails)
				{
					if(userId.equalsIgnoreCase(userProjMap.get("userId").toString()))
						{
						list.add(userProjMap.get("projectId").toString());
						}
				}
				
				map.put("projectIds", list);
			}
			
		   return new ResponseEntity<Object>(regUserDetails,HttpStatus.OK);
	}
}
