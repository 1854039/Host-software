package com.example.zosdemo.Controller;


import com.example.zosdemo.Util.SslUtil;
import org.apache.http.impl.client.CloseableHttpClient;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpSession;
import java.util.Base64;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
public class UserController {
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<String> login(@RequestBody Map<String,String> account, HttpSession session){
        Object ZOSMF_JSESSIONID = session.getAttribute("ZOSMF_JSESSIONID");
        Object ZOSMF_LtpaToken2 = session.getAttribute("ZOSMF_LtpaToken2");
        Object ZOSMF_Address = session.getAttribute("ZOSMF_Address");
        Object ZOSMF_Account = session.getAttribute("ZZOSMF_Account");
        if (ZOSMF_JSESSIONID==null || ZOSMF_LtpaToken2==null || ZOSMF_Address==null || ZOSMF_Account==null) {
            ZOSMF_Address = account.get("address");
            ZOSMF_Account = account.get("account");
            CloseableHttpClient httpClient = SslUtil.SslHttpClientBuild();
            HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
            requestFactory.setHttpClient(httpClient);

            String zosmfUrlOverHttps = "https://"+ZOSMF_Address.toString()+"/zosmf/";
            HttpHeaders httpHeaders = new RestTemplate(requestFactory).headForHeaders(zosmfUrlOverHttps);
            List<String> setCookie = httpHeaders.get("Set-Cookie");
            if (setCookie!=null) {
                ZOSMF_JSESSIONID = setCookie.get(0).split(";")[0];
                System.out.println(setCookie.get(0));
            }else {
                System.out.println("header 中没有set-cookie信息");
            }

            String loginUrlOverHttps = zosmfUrlOverHttps + "LoginServlet";

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            headers.add("Cookie",ZOSMF_JSESSIONID.toString());
            headers.add("Referer",zosmfUrlOverHttps); //欺骗服务器不是csrf

           // String str=account.get("account")+":"+account.get("password");
            //String base64encodedString = "Basic "+Base64.getEncoder().encodeToString(str.getBytes("utf-8"));
            //headers.add("Authorization",base64encodedString);
            //System.out.println(base64encodedString);
            //ZOSMF_Authorization = base64encodedString;
            MultiValueMap<String,String> map = new LinkedMultiValueMap<>();
            map.add("requestType","Login");
            map.add("username",account.get("account"));
            map.add("password",account.get("password"));
            HttpEntity<MultiValueMap<String,String>> request = new HttpEntity<>(map,headers);
            ResponseEntity<String> response = new RestTemplate(requestFactory).postForEntity(loginUrlOverHttps,request,String.class);
            if (!response.toString().contains("Set-Cookie:")) {
                return ResponseEntity.status(401).body("unauthorized");
            }
            ZOSMF_LtpaToken2 = response.toString().split("Set-Cookie:\"|; Path")[1];
            session.setAttribute("ZOSMF_LtpaToken2",ZOSMF_LtpaToken2);
            session.setAttribute("ZOSMF_JSESSIONID",ZOSMF_JSESSIONID);
            session.setAttribute("ZOSMF_Address",ZOSMF_Address);
            session.setAttribute("ZOSMF_Account",ZOSMF_Account);

        }
        else {
            System.out.println("session已经存在");
        }
        return ResponseEntity.ok("successful");
    }

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ResponseEntity<String> loginInfo(HttpSession session) {
        //获取session
        Object ZOSMF_Address = session.getAttribute("ZOSMF_Address");
        Object ZOSMF_Account = session.getAttribute("ZOSMF_Account");
        Object ZOSMF_JSESSIONID = session.getAttribute("ZOSMF_JSESSIONID");
        Object ZOSMF_LtpaToken2 = session.getAttribute("ZOSMF_LtpaToken2");
        if (ZOSMF_JSESSIONID == null || ZOSMF_LtpaToken2 == null || ZOSMF_Address == null || ZOSMF_Account == null) {
            return ResponseEntity.status(401).body("unauthorized");
        } else {
            return ResponseEntity.ok(ZOSMF_Account.toString().toUpperCase());
        }
    }

    @RequestMapping(value = "/logoff", method = RequestMethod.DELETE)
    public ResponseEntity<String> logout(HttpSession session) {
        session.removeAttribute("ZOSMF_JSESSIONID");
        session.removeAttribute("ZOSMF_LtpaToken2");
        session.removeAttribute("ZOSMF_Address");
        System.out.println("登出");
        return ResponseEntity.ok("successful");
    }

}
