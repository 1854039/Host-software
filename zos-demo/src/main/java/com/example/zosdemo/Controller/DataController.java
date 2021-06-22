package com.example.zosdemo.Controller;

import com.example.zosdemo.Util.SslUtil;
import com.example.zosdemo.domain.DatasetInfo;
import com.example.zosdemo.domain.JobInfo;
import net.minidev.json.JSONObject;
import org.apache.http.impl.client.CloseableHttpClient;

import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
public class DataController {
        //获取数据集列表
        @RequestMapping(value = "/sms/getdslist", method = RequestMethod.GET)
        public ResponseEntity<String> getSDSList(@RequestParam String dsName, HttpServletRequest request) {
                String authorization = request.getHeader("Authorization").toString();

                if(!authorization.isEmpty()) {
                        String jsessionid = authorization.split(";")[0];
                        String token = authorization.split(";")[1];
                        String address = authorization.split(";")[2];
                        if(!jsessionid.isEmpty()&&!token.isEmpty()&&!address.isEmpty()) {
                                String url = "https://" + address + "/zosmf/restfiles/ds?dslevel="+dsName;
                                HttpHeaders headers = new HttpHeaders();
                                headers.setContentType(MediaType.TEXT_PLAIN);
                                headers.add("Cookie", jsessionid + ";" + token);
                                CloseableHttpClient httpClient = SslUtil.SslHttpClientBuild();
                                HttpComponentsClientHttpRequestFactory requestFactory
                                        = new HttpComponentsClientHttpRequestFactory();
                                requestFactory.setHttpClient(httpClient);

                                HttpEntity<String> requestList = new HttpEntity<>(headers);

                                ResponseEntity<String> responseList = new RestTemplate(requestFactory).exchange(url, HttpMethod.GET, requestList, String.class);
                                return ResponseEntity.ok(responseList.getBody());
                        }
                        else {
                                return ResponseEntity.status(401).body("unauthorized");
                        }
                }
                else {
                      return ResponseEntity.status(401).body("缺少必要的session或token");
                }

        }

        //获取分区数据集成员
        @RequestMapping(value = "/sms/getpdsmemberlist", method = RequestMethod.GET)
        public ResponseEntity<String> getPDSMember(@RequestParam String dsName, HttpServletRequest request) {
                String authorization = request.getHeader("Authorization").toString();

                if(!authorization.isEmpty()) {
                        String jsessionid = authorization.split(";")[0];
                        String token = authorization.split(";")[1];
                        String address = authorization.split(";")[2];
                        if(!jsessionid.isEmpty()&&!token.isEmpty()&&!address.isEmpty()) {
                                String url = "https://" + address + "/zosmf/restfiles/ds/"+dsName+"/member";
                                HttpHeaders headers = new HttpHeaders();
                                headers.setContentType(MediaType.TEXT_PLAIN);
                                headers.add("Cookie", jsessionid + ";" + token);
                                CloseableHttpClient httpClient = SslUtil.SslHttpClientBuild();
                                HttpComponentsClientHttpRequestFactory requestFactory
                                        = new HttpComponentsClientHttpRequestFactory();
                                requestFactory.setHttpClient(httpClient);

                                HttpEntity<String> requestList = new HttpEntity<>(headers);

                                ResponseEntity<String> responseList = new RestTemplate(requestFactory).exchange(url, HttpMethod.GET, requestList, String.class);
                                return ResponseEntity.ok(responseList.getBody());
                        }
                        else {
                                return ResponseEntity.status(401).body("unauthorized");
                        }
                }
                else {
                        return ResponseEntity.status(401).body("缺少session或token");
                }

        }

        //删除数据集
        @RequestMapping(value = "/sms/deleteds", method = RequestMethod.DELETE)
        public ResponseEntity<String> deleteSDS(@RequestParam String dsName, HttpServletRequest request) {
                //获取session
                String authorization = request.getHeader("Authorization");
                if(!authorization.isEmpty()) {
                        String jsessionid = authorization.split(";")[0];
                        String token = authorization.split(";")[1];
                        String address = authorization.split(";")[2];
                        if(!jsessionid.isEmpty()&&!token.isEmpty()&&!address.isEmpty()) {
                                String url = "https://" + address + "/zosmf/restfiles/ds/"+dsName;
                                HttpHeaders headers = new HttpHeaders();
                                headers.setContentType(MediaType.TEXT_PLAIN);
                                headers.add("Cookie", jsessionid + ";" + token);
                                CloseableHttpClient httpClient = SslUtil.SslHttpClientBuild();
                                HttpComponentsClientHttpRequestFactory requestFactory
                                        = new HttpComponentsClientHttpRequestFactory();
                                requestFactory.setHttpClient(httpClient);

                                HttpEntity<String> requestList = new HttpEntity<>(headers);

                                ResponseEntity<String> responseList = new RestTemplate(requestFactory).exchange(url, HttpMethod.DELETE, requestList, String.class);
                                return ResponseEntity.ok(responseList.getBody());
                        }
                        else {
                                return ResponseEntity.status(401).body("unauthorized");

                        }
                }
                else {
                        return ResponseEntity.status(401).body("缺少session或token");

                }
        }


        //删除分区数据集成员
        @RequestMapping(value = "/sms/deletepdsmember", method = RequestMethod.DELETE)
        public ResponseEntity<String> deletePDSMenber(@RequestParam String dsName, HttpServletRequest request) {
                String authorization = request.getHeader("Authorization");
                if(!authorization.isEmpty()) {
                        String jsessionid = authorization.split(";")[0];
                        String token = authorization.split(";")[1];
                        String address = authorization.split(";")[2];
                        if(!jsessionid.isEmpty()&&!token.isEmpty()&&!address.isEmpty()) {
                                String url = "https://" + address + "/zosmf/restfiles/ds/"+dsName;
                                HttpHeaders headers = new HttpHeaders();
                                headers.setContentType(MediaType.TEXT_PLAIN);
                                headers.add("Cookie", jsessionid + ";" + token);
                                CloseableHttpClient httpClient = SslUtil.SslHttpClientBuild();
                                HttpComponentsClientHttpRequestFactory requestFactory
                                        = new HttpComponentsClientHttpRequestFactory();
                                requestFactory.setHttpClient(httpClient);

                                HttpEntity<String> requestList = new HttpEntity<>(headers);

                                ResponseEntity<String> responseList = new RestTemplate(requestFactory).exchange(url, HttpMethod.DELETE, requestList, String.class);
                                return ResponseEntity.ok(responseList.getBody());
                        }
                        else {
                                return ResponseEntity.status(401).body("unauthorized");

                        }
                }
                else {
                        return ResponseEntity.status(401).body("缺少session或token");

                }
        }

        //获取数据集内容
        @RequestMapping(value = "/sms/getds", method = RequestMethod.GET)
        public ResponseEntity<String> getDataset(@RequestParam String dsName, HttpServletRequest request) {
                //获取session
                String authorization = request.getHeader("Authorization");
                if(!authorization.isEmpty()) {
                        String jsessionid = authorization.split(";")[0];
                        String token = authorization.split(";")[1];
                        String address = authorization.split(";")[2];
                        if(!jsessionid.isEmpty()&&!token.isEmpty()&&!address.isEmpty()) {
                                String url = "https://" + address + "/zosmf/restfiles/ds/"+dsName;
                                HttpHeaders headers = new HttpHeaders();
                                headers.setContentType(MediaType.TEXT_PLAIN);
                                headers.add("Cookie", jsessionid + ";" + token);
                                CloseableHttpClient httpClient = SslUtil.SslHttpClientBuild();
                                HttpComponentsClientHttpRequestFactory requestFactory
                                        = new HttpComponentsClientHttpRequestFactory();
                                requestFactory.setHttpClient(httpClient);

                                HttpEntity<String> requestCont = new HttpEntity<>(headers);

                                ResponseEntity<String> responseCont = new RestTemplate(requestFactory).exchange(url, HttpMethod.GET, requestCont, String.class);
                                return ResponseEntity.ok(responseCont.getBody());
                        }
                        else {
                                return ResponseEntity.status(401).body("unauthorized");

                        }
                }
                else {
                        return ResponseEntity.status(401).body("缺少session或token");

                }
        }

        @RequestMapping(value="/sms/job/outputlist",method = RequestMethod.GET)
        public ResponseEntity<String> qurejoboutputlist(@RequestParam("jobName") String jobName,
                                              @RequestParam("jobId") String jobId,HttpServletRequest request) {
                String authorization = request.getHeader("Authorization");
                if(!authorization.isEmpty()) {
                        String jsessionid = authorization.split(";")[0];
                        String token = authorization.split(";")[1];
                        String address = authorization.split(";")[2];
                        if (!jsessionid.isEmpty() && !token.isEmpty() && !address.isEmpty()) {
                                HttpHeaders headers = new HttpHeaders();
                                headers.setContentType(MediaType.TEXT_PLAIN);
                                headers.add("Cookie", jsessionid + ";" + token);
                                CloseableHttpClient httpClient = SslUtil.SslHttpClientBuild();
                                HttpComponentsClientHttpRequestFactory requestFactory
                                        = new HttpComponentsClientHttpRequestFactory();
                                requestFactory.setHttpClient(httpClient);
                                String url="https://10.60.43.8:8800/zosmf/restjobs/jobs/"+jobName+"/"+jobId;
                                //查询结果的request

                                HttpEntity<String> requestQur = new HttpEntity<>(headers);

                                ResponseEntity<String> responseQur = new RestTemplate(requestFactory).exchange(url,HttpMethod.GET,requestQur,String.class);
                                System.out.println(responseQur.getBody().getBytes(StandardCharsets.UTF_8));
                                System.out.println(responseQur);

                                return responseQur;
                        }
                        else {
                                return ResponseEntity.status(401).body("unauthorized");

                        }
                }
                else {
                        return ResponseEntity.status(401).body("缺少session或token");

                }
        }

        @RequestMapping(value="/sms/job/output",method = RequestMethod.GET)
        public ResponseEntity<String> qurejoboutput(@RequestParam("jobName") String jobName,
                                                    @RequestParam("jobId") String jobId,
                                                        @RequestParam("id") String id,
                                                    HttpServletRequest request) {
                String authorization = request.getHeader("Authorization");
                if(!authorization.isEmpty()) {
                        String jsessionid = authorization.split(";")[0];
                        String token = authorization.split(";")[1];
                        String address = authorization.split(";")[2];
                        if (!jsessionid.isEmpty() && !token.isEmpty() && !address.isEmpty()) {
                                HttpHeaders headers = new HttpHeaders();
                                headers.setContentType(MediaType.TEXT_PLAIN);
                                headers.add("Cookie", jsessionid + ";" + token);
                                CloseableHttpClient httpClient = SslUtil.SslHttpClientBuild();
                                HttpComponentsClientHttpRequestFactory requestFactory
                                        = new HttpComponentsClientHttpRequestFactory();
                                requestFactory.setHttpClient(httpClient);
                                String url="https://10.60.43.8:8800/zosmf/restjobs/jobs/"+jobName+"/"+jobId+"/";
                                url+="files/"+id+"/records";
                                //查询结果的request

                                HttpEntity<String> requestQur = new HttpEntity<>(headers);
                                ResponseEntity<String> result = new RestTemplate(requestFactory).exchange(url,HttpMethod.GET,requestQur,String.class);
                                return ResponseEntity.ok(result.getBody());
                        }
                        else {
                                return ResponseEntity.status(401).body("unauthorized");

                        }
                }
                else {
                        return ResponseEntity.status(401).body("缺少session或token");

                }
        }

        //提交作业
        @RequestMapping(value = "/sms/subjob",method = RequestMethod.PUT)
        public ResponseEntity<JobInfo> subJob(@RequestBody Map<String,String> map, HttpServletRequest request) {
                String authorization = request.getHeader("Authorization");
                if (!authorization.isEmpty()) {
                        String jsessionid = authorization.split(";")[0];
                        String token = authorization.split(";")[1];
                        String address = authorization.split(";")[2];
                        if (!jsessionid.isEmpty() && !token.isEmpty() && !address.isEmpty()) {
                                CloseableHttpClient httpClient = SslUtil.SslHttpClientBuild();
                                HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
                                requestFactory.setHttpClient(httpClient);
                                //url
                                String url = "https://" + address + "/zosmf/restjobs/jobs";
                                //hearders
                                HttpHeaders headers = new HttpHeaders();
                                headers.setContentType(MediaType.TEXT_PLAIN);
                                headers.add("Cookie", jsessionid + ";" + token);
                                //body

                                String content = map.get("jclList").toString();
                                HttpEntity<String> requestSub = new HttpEntity<>(content, headers);
                                //响应内容
                                ResponseEntity<JobInfo> responseSub = new RestTemplate(requestFactory).exchange(url, HttpMethod.PUT, requestSub, JobInfo.class);
                                return responseSub;
                        }
                        else {
                                return null;

                        }
                }
                else {
                        return null;

                }
        }

        //将code写入到数据集中
        @RequestMapping(value = "/sms/writeds",method = RequestMethod.PUT)
        //para jcl-content + dataset-name
        public ResponseEntity<String> writeDS(@RequestBody Map<String,Object> map, HttpServletRequest request){
                //获取session数据
                String authorization = request.getHeader("Authorization");
                if(!authorization.isEmpty()) {
                        String jsessionid = authorization.split(";")[0];
                        String token = authorization.split(";")[1];
                        String address = authorization.split(";")[2];
                        if(!jsessionid.isEmpty()&&!token.isEmpty()&&!address.isEmpty()) {
                                CloseableHttpClient httpClient = SslUtil.SslHttpClientBuild();
                                HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
                                requestFactory.setHttpClient(httpClient);
                                //url
                                String url = "https://" + address + "/zosmf/restfiles/ds/"+map.get("dsName");
                                //headers
                                HttpHeaders headers = new HttpHeaders();
                                headers.setContentType(MediaType.TEXT_PLAIN);
                                headers.add("Cookie",jsessionid+";"+token);
                                //body
                                StringBuffer sb = new StringBuffer();
                                Collection<Object> jclList = (Collection<Object>) map.get("jclList");
                                System.out.println(jclList);
                                for(Object item:jclList){
                                        sb.append(item+"\n");
                                }
                                String jclStr = sb.toString();
                                //start request
                                HttpEntity<String> requestWrite = new HttpEntity<>(jclStr, headers);
                                //get response
                                ResponseEntity<String> responseWritte = new RestTemplate(requestFactory).exchange(url,HttpMethod.PUT,requestWrite,String.class);
                                return ResponseEntity.ok(responseWritte.getBody());
                        }
                        else {
                                return ResponseEntity.status(401).body("unauthorized");

                        }
                }
                else {
                        return ResponseEntity.status(401).body("缺少session或token");

                }
        }



        //创建数据集
        @RequestMapping(value="/sms/createds",method = RequestMethod.POST)
        public ResponseEntity<String> createDS(@RequestBody DatasetInfo ds, HttpServletRequest request){
                //获取session数据
                String authorization = request.getHeader("Authorization");
                if(!authorization.isEmpty()) {
                        String jsessionid = authorization.split(";")[0];
                        String token = authorization.split(";")[1];
                        String address = authorization.split(";")[2];
                        if(!jsessionid.isEmpty()&&!token.isEmpty()&&!address.isEmpty()) {
                                CloseableHttpClient httpClient = SslUtil.SslHttpClientBuild();
                                HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
                                requestFactory.setHttpClient(httpClient);
                                //接收前端数据
        //            String url = "https://" + ZOSMF_Address.toString() + "/zosmf/restfiles/ds/ST007.AAAAE.TEST";
                                String url = "https://" + address + "/zosmf/restfiles/ds/"+ds.getDsname();
                                System.out.println(ds.getDsname());
                                //headers
                                HttpHeaders headers = new HttpHeaders();
                                headers.setContentType(MediaType.APPLICATION_JSON);
                                headers.add("Cookie",jsessionid+";"+token);
                                //body
                                JSONObject object = new JSONObject();
                                object.put("volser",ds.getVolser());
                                object.put("unit", ds.getUnit());
                                object.put("dsorg", ds.getDsorg());
                                object.put("alcunit",ds.getAlcunit());
                                object.put("primary", ds.getPrimary());
                                object.put("secondary", ds.getSecondary());
                                object.put("dirblk", ds.getDirblk());
                                object.put("avgblk", ds.getAvgblk());
                                object.put("recfm", ds.getRecfm());
                                object.put("blksize", ds.getBlksize());
                                object.put("lrecl", ds.getLrecl());
        //            object.put("volser","BYWK00");
        //            object.put("unit","3390");
        //            object.put("dsorg", "PO");
        //            object.put("alcunit","TRK");
        //            object.put("primary", 10);
        //            object.put("secondary",10);
        //            object.put("dirblk", 5);
        //            object.put("avgblk", 500);
        //            object.put("recfm","FB");
        //            object.put("blksize", 400);
        //            object.put("lrecl", 80);


                                //start request
                                HttpEntity<JSONObject> requestcrt = new HttpEntity<>(object, headers);
                                //get response
                                ResponseEntity<String > responseCrt = new RestTemplate(requestFactory).postForEntity(url, requestcrt, String.class);

                                return ResponseEntity.ok(responseCrt.getBody());
                        }
                        else {
                                return ResponseEntity.status(401).body("unauthorized");

                        }
                }
                else {
                        return ResponseEntity.status(401).body("缺少session或token");

                }
        }
}
