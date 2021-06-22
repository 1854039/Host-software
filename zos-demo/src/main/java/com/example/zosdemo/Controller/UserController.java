package com.example.zosdemo.Controller;


import com.example.zosdemo.Util.SslUtil;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Base64;
import java.util.List;
import java.util.Map;
@CrossOrigin
@RestController
public class UserController {

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<String> login(@RequestBody Map<String,String> account, HttpServletRequest request){
        String authorization = request.getHeader("Authorization").toString();
        if(authorization.isEmpty()) {
            CloseableHttpClient httpClient = SslUtil.SslHttpClientBuild();
            HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
            requestFactory.setHttpClient(httpClient);
            String zosmfUrlOverHttps = "https://"+account.get("address").toString()+"/zosmf/";
            HttpHeaders httpHeaders = new RestTemplate(requestFactory).headForHeaders(zosmfUrlOverHttps);
            List<String> setCookie = httpHeaders.get("Set-Cookie");
            String jsessionid="";
            if (setCookie!=null) {
                jsessionid = setCookie.get(0).split(";")[0];
                System.out.println(setCookie.get(0));
            }else {
                System.out.println("header 中没有set-cookie信息");
            }
            String loginUrlOverHttps = zosmfUrlOverHttps + "LoginServlet";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            if (!jsessionid.isEmpty()) {
                headers.add("Cookie", jsessionid);
            }

            headers.add("Referer",zosmfUrlOverHttps); //欺骗服务器不是csrf
            MultiValueMap<String,String> map = new LinkedMultiValueMap<>();
            map.add("requestType","Login");
            map.add("username",account.get("account"));
            map.add("password",account.get("password"));
            HttpEntity<MultiValueMap<String,String>> requestZosmf = new HttpEntity<>(map,headers);
            ResponseEntity<String> response = new RestTemplate(requestFactory).postForEntity(loginUrlOverHttps,requestZosmf,String.class);
            if (!response.toString().contains("Set-Cookie:")) {
                return ResponseEntity.status(401).body("unauthorized");
            }
            String token = response.toString().split("Set-Cookie:\"|; Path")[1];

            String res = jsessionid+";"+token+";"+account.get("address");
            return ResponseEntity.status(200).body(res);
        }
        else {
            String jsessionid=authorization.split(";")[0];
            String token=authorization.split(";")[1];

            if(jsessionid.isEmpty()||token.isEmpty()) {
                return ResponseEntity.status(401).body("缺少session或token");
            }
            else {
                return ResponseEntity.status(200).body("已登录");
            }
        }


    }

}
