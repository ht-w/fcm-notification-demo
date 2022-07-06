package io.hongting.fcmtest;

;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.auth.oauth2.GoogleCredentials;
import io.hongting.fcmtest.service.PushNotificationService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;

/**
 * @author hongting
 * @create 2022 07 01 2:30 PM
 */

@SpringBootTest
@RunWith(SpringRunner.class)
public class PushNoticationTest {


    private static final String SCOPES = "https://www.googleapis.com/auth/firebase.messaging";

    @Autowired
    PushNotificationService pushNotificationService;

    @Test
    public void accessTokenTest() throws IOException {
        GoogleCredential googleCredential = GoogleCredential
                .fromStream(new FileInputStream("D:\\workspace_idea1\\fcm_test\\src\\test\\java\\io\\hongting\\fcmtest\\fcm_service_account.json"))
                .createScoped(Arrays.asList(SCOPES));
        googleCredential.refreshToken();
        System.out.println(googleCredential.getAccessToken());

    }


}
