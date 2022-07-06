package io.hongting.fcmtest.controller;

import io.hongting.fcmtest.dto.AlertOfficerRequest;
import io.hongting.fcmtest.model.PushNotificationRequest;
import io.hongting.fcmtest.model.PushNotificationResponse;
import io.hongting.fcmtest.model.SubscriptionRequest;
import io.hongting.fcmtest.model.SubscriptionResponse;
import io.hongting.fcmtest.service.PushNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author hongting
 * @create 2022 06 30 12:00 AM
 */
@Controller
@RequestMapping("/")
public class PushNotificationController {

    @Autowired
    private PushNotificationService pushNotificationService;



    @PostMapping("/push")
    @ResponseBody
    public PushNotificationResponse push(@RequestBody PushNotificationRequest request)  {
        return pushNotificationService.push(request);
    }

    @PostMapping("/subscribe")
    @ResponseBody
    public SubscriptionResponse push(@RequestBody SubscriptionRequest request)  {
        return pushNotificationService.subscribe(request);
    }

    @PostMapping("/pushtopic")
    @ResponseBody
    public PushNotificationResponse pushToTopic(@RequestBody PushNotificationRequest request) {
        return pushNotificationService.pushtoTopic(request);
    }

    @PostMapping("/httppush")
    @ResponseBody
    public  PushNotificationResponse pushHttp (@RequestBody AlertOfficerRequest request){
        return pushNotificationService.pushHttp(request.getToken());
    }
}