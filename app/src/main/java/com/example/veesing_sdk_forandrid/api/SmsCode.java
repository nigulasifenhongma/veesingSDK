package com.example.veesing_sdk_forandrid.api;


import com.example.veesing_sdk_forandrid.util.Constant;
import com.example.veesing_sdk_forandrid.util.HttpUtil;
import java.util.HashMap;

/**
 * Created by 小米 on 2018/9/21.
 */
public class SmsCode {
    /**
     * 提交短信验证码
     *
     * @param appId
     *            平台生成的appid
     * @param appKey
     *            平台生成的appkey
     * @param phone
     *            接收验证码的手机号码
     * @param templateId
     *            平台申请的模板id
     * @param variables
     *            验证码变量（4-6位随机数，最终用户接收到的验证码）
     * @return
     */
    public static String sendSmsCode(String appId, String appKey, String phone, String templateId,String variables) {
        HashMap<String,String> hashMap = new HashMap<>();
        hashMap.put("appId",appId);
        hashMap.put("appKey",appKey);
        hashMap.put("phone",phone);
        hashMap.put("templateId",templateId);
        hashMap.put("variables",variables);
        return HttpUtil.sendPost(Constant.SMS_CODE_URL, hashMap);
    }

}
