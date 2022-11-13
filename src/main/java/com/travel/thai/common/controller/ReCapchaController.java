package com.travel.thai.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;

@Controller
public class ReCapchaController {
    public static final String SECRET_KEY = "6LeIHAIjAAAAABvyEG1gY_z2RmrUzCHjdSvatJ5f";
    public static final String SITE_VERIFY_URL = "https://www.google.com/recaptcha/api/siteverify";

    @Autowired
    private RestTemplateBuilder builder;

    @RequestMapping(value = "/captcha", method = RequestMethod.POST)
    public ResponseEntity<String> ajax(@RequestParam("token") String token){
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        RestTemplate restTemplate = new RestTemplate();

        MultiValueMap<String, String> map= new LinkedMultiValueMap<String, String>();
        map.add("secret", SECRET_KEY);
        map.add("response", token);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<MultiValueMap<String, String>>(map, headers);

        ResponseEntity<String> response = builder.build().postForEntity( SITE_VERIFY_URL, request , String.class );

        return response;
    }
}
