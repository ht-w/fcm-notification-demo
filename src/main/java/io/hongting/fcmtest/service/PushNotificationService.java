package io.hongting.fcmtest.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.messaging.*;
import io.hongting.fcmtest.model.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.util.EntityUtils;
import org.eclipse.persistence.jaxb.JAXBContextFactory;
import org.eclipse.persistence.jaxb.MarshallerProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.Arrays;

/**
 * @author hongting
 * @create 2022 06 29 9:59 PM
 */


@Service
public class PushNotificationService {



    @Value("${app.firebase-configuration-file}")
    private String firebaseConfigPath;

    @Value("${fcm.token}")
    private String fcmToken;

    @Value("${fcm.url}")
    private String fcmUrl;


    private FirebaseApp firebaseApp;

    private static final String SCOPES = "https://www.googleapis.com/auth/firebase.messaging";

    @PostConstruct
    public void init() {
        try {

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(new ClassPathResource(firebaseConfigPath).getInputStream())).build();
            if (FirebaseApp.getApps().isEmpty()) {
                this.firebaseApp = FirebaseApp.initializeApp(options);
                LOG.info("Firebase application has been initialized");
            }else{
                this.firebaseApp = FirebaseApp.getInstance();
            }
        } catch (IOException e) {
            LOG.error(e.getMessage());
        }
    }


    private static final Logger LOG = LoggerFactory.getLogger(PushNotificationService.class);


    public String getAccessToken() throws  IOException {
        GoogleCredential googleCredential = GoogleCredential
                .fromStream(new FileInputStream("src/main/resources/firebaseconfig/fcm_service_account.json"))
                .createScoped(Arrays.asList(SCOPES));
        googleCredential.refreshToken();
        LOG.info(googleCredential.getAccessToken());
        return  googleCredential.getAccessToken();
    }

    public PushNotificationResponse push (PushNotificationRequest request){
        PushNotificationResponse res = new PushNotificationResponse();
        String response = null;
        try {
            Message message = Message.builder()
                    .setNotification(Notification.builder()
                            .setTitle(request.getTitle())
                            .setBody(request.getMessage())
                            .build())
                    .setToken(request.getToken())
                    .setWebpushConfig(WebpushConfig.builder()
                            .setNotification(WebpushNotification.builder()
                                    .setTitle(request.getTitle())
                                    .setBody(request.getMessage())
                                    .setImage("https://raw.githubusercontent.com/ht-w/blog-images/main/img/testpost.jpg")
                                    .setIcon("https://raw.githubusercontent.com/ht-w/blog-images/main/img/yeah.png")
                                    .build())
                            .setFcmOptions(WebpushFcmOptions.withLink("/test"))
                            .build())
                    .build();
            response = FirebaseMessaging.getInstance().send(message);
            LOG.info("Successfully sent message: " + response);
            res.setMessage("Notification has been sent");
            res.setStatus(HttpStatus.OK.value());
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error(e.getMessage());
            res.setMessage("Error");
            res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return res;
        }
            return  res;

    }

    public SubscriptionResponse subscribe (SubscriptionRequest request){
        SubscriptionResponse res = new SubscriptionResponse();
        try {
            FirebaseMessaging.getInstance(firebaseApp).subscribeToTopic(request.getTokens(), request.getTopic());
            res.setStatus(HttpStatus.OK.value());
            res.setMessage("Succesfully subscribe");
            return res;
        } catch (FirebaseMessagingException e) {
            LOG.error("Failed to subscribe", e);
            res.setMessage("Error");
            res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return res;
        }
    }

    public PushNotificationResponse pushtoTopic (PushNotificationRequest request){
        PushNotificationResponse res = new PushNotificationResponse();
        String response = null;
        try {
            Message message = Message.builder()
                    .setNotification(Notification.builder()
                            .setTitle(request.getTitle())
                            .setBody(request.getMessage())
                            .build())
                    .setTopic(request.getTopic())
                    .setWebpushConfig(WebpushConfig.builder()
                            .setNotification(WebpushNotification.builder()
                                    .setTitle(request.getTitle())
                                    .setBody(request.getMessage())
                                    .setImage("https://raw.githubusercontent.com/ht-w/blog-images/main/img/deyiwangxin.jpg")
                                    .setIcon("https://raw.githubusercontent.com/ht-w/blog-images/main/img/yeah.png")
                                    .build())
                            .setFcmOptions(WebpushFcmOptions.withLink("/test"))
                            .build())
                    .build();
            response = FirebaseMessaging.getInstance().send(message);
            LOG.info("Successfully sent message: " + response);
            res.setMessage("Notification has been sent");
            res.setStatus(HttpStatus.OK.value());
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error(e.getMessage());
            res.setMessage("Error");
            res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return res;
        }
        return  res;

    }


    public PushNotificationResponse pushHttp(String token) {
        PushNotificationResponse res = new PushNotificationResponse();
        try {

            if (fcmToken != null || !StringUtils.isEmpty(fcmToken)){
                FcmRequest fcmRequest = new FcmRequest();
                FcmNotification notification = new FcmNotification();
                notification.setTitle("Champion Leauge champion");
                notification.setBody("Real Modrid won the champion league 1 - 0");
                FcmData data = new FcmData();
                data.setName("Luka Modric");
                data.setGender("Male");
                fcmRequest.setNotification(notification);
                fcmRequest.setData(data);
                fcmRequest.setTo(token);
                fcmRequest.setCollapse_key("type_a");
                asyncFcm (fcmRequest, fcmToken);
            }else{
                LOG.info("Access token is empty");
            }
        } catch (Exception e) {
            e.printStackTrace();
            LOG.error(e.getMessage());
            res.setMessage("Error");
            res.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
            return res;

        }
        res.setMessage("Notification has been sent");
        res.setStatus(HttpStatus.OK.value());
        return res;

    }

    private void asyncFcm (FcmRequest request, String token){

        int timeout = 30;

        RequestConfig config = RequestConfig.custom().setConnectionRequestTimeout(timeout*1000).setConnectTimeout(timeout*1000).setSocketTimeout(timeout*1000).build();

        CloseableHttpClient httpClient = HttpClients.custom().setDefaultRequestConfig(config).build();

        HttpPost httpPost = new HttpPost(fcmUrl);
        httpPost.addHeader("Content-Type", "application/json");
        httpPost.addHeader("Authorization", "Bearer "+ token);
        StringWriter writer = new StringWriter();

        HttpEntity entity = null;

        try {
            JAXBContext context = JAXBContextFactory.createContext(new Class[]{FcmRequest.class}, null);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(MarshallerProperties.MEDIA_TYPE, "application/json");
            marshaller.setProperty(MarshallerProperties.JSON_INCLUDE_ROOT, false);
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            marshaller.marshal(request, writer);
            StringEntity jsonEntity = new StringEntity(writer.getBuffer().toString());
            httpPost.setEntity(jsonEntity);
            String s = jsonEntity.toString();
            LOG.info(s);
            LOG.info("fcm request:" + writer.toString());
            CloseableHttpResponse response = httpClient.execute(httpPost);
            entity = response.getEntity();
            int statusCode  = response.getStatusLine().getStatusCode();
            LOG.info("HttpClient completed:" + response.getStatusLine());
            LOG.info("HttpClient completed:" + statusCode);
            LOG.info("HttpClient HTTP Status Code:" + statusCode);
            String apiResponse = EntityUtils.toString(entity, "UTF-8");
            if (response.getStatusLine().getStatusCode() == 200){
                LOG.info("HttpClient Response:" + apiResponse);
            }

//            httpClient.execute(httpPost, new FutureCallback<HttpResponse>() {
//
//                int statusCode = 0;
//                String apiResponse = null;
//
//                @Override
//                public void completed(HttpResponse res) {
//                    statusCode  = res.getStatusLine().getStatusCode();
//                    LOG.info("HttpAsyncClients completed:" + res.getStatusLine());
//                    LOG.info("HttpAsyncClients completed:" + statusCode);
//                    LOG.info("HttpAsyncClients HTTP Status Code:" + statusCode);
//                    if (statusCode == 200){
//                        HttpEntity httpEntity = res.getEntity();
//                        try {
//                            apiResponse = EntityUtils.toString(httpEntity);
//                        } catch (IOException e) {
//                            e.printStackTrace();
//                            LOG.info("Exception: " + e.toString());
//                        }
//
//                    }
//                    LOG.info(HttpAsyncCl"ients Response:" + apiResponse);
//                }
//
//
//                @Override
//                public void failed(Exception e) {
//                    LOG.info("HttpAsyncClients failed:" + e);
//                }
//
//                @Override
//                public void cancelled() {
//                    LOG.info("HttpAsyncClients cancelled");
//                }
//            });
        } catch (Exception e) {
            e.printStackTrace();
            LOG.info("Exception: " + e.toString());
        }finally{
            try {
                EntityUtils.consume(entity);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }
}
