package com.peng.saishiserver.utils;

import java.util.HashMap;
import java.util.Map;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

public class JpushUtils {

	public static void PushAll(Message msg) {
		JPushClient client = new JPushClient("3d4327a038cd597857e12a66",
				"51f0391c24d59fee5e94c28e");
		PushPayload load = PushPayload.newBuilder().setMessage(msg)
				.setPlatform(Platform.android()).setAudience(Audience.all())
				.build();
		try {
			PushResult result = client.sendPush(load);
		} catch (APIConnectionException | APIRequestException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
