package com.fangxuele.tool.push.logic.msgsender;

import com.fangxuele.tool.push.App;
import com.fangxuele.tool.push.logic.PushControl;
import com.fangxuele.tool.push.logic.msgmaker.YunPianMsgMaker;
import com.yunpian.sdk.YunpianClient;
import com.yunpian.sdk.model.Result;
import com.yunpian.sdk.model.SmsSingleSend;

import java.util.Map;

/**
 * <pre>
 * 云片网短信发送器
 * </pre>
 *
 * @author <a href="https://github.com/rememberber">RememBerBer</a>
 * @since 2019/6/15.
 */
public class YunPianMsgSender implements IMsgSender {
    /**
     * 云片网短信client
     */
    public volatile static YunpianClient yunpianClient;

    private YunPianMsgMaker yunPianMsgMaker;

    public YunPianMsgSender() {
        yunPianMsgMaker = new YunPianMsgMaker();
        yunpianClient = getYunpianClient();
    }

    @Override
    public SendResult send(String[] msgData) {
        SendResult sendResult = new SendResult();

        try {
            Map<String, String> params = yunPianMsgMaker.makeMsg(msgData);
            String telNum = msgData[0];
            params.put(YunpianClient.MOBILE, telNum);
            Result<SmsSingleSend> result = yunpianClient.sms().single_send(params);
            if (result.getCode() == 0) {
                sendResult.setSuccess(true);
            } else {
                sendResult.setSuccess(false);
                sendResult.setInfo(result.toString());
            }
        } catch (Exception e) {
            sendResult.setSuccess(false);
            sendResult.setInfo(e.toString());
        }

        return sendResult;
    }

    /**
     * 获取云片网短信发送客户端
     *
     * @return YunpianClient
     */
    private static YunpianClient getYunpianClient() {
        if (yunpianClient == null) {
            synchronized (PushControl.class) {
                if (yunpianClient == null) {
                    String yunpianApiKey = App.config.getYunpianApiKey();

                    yunpianClient = new YunpianClient(yunpianApiKey).init();
                }
            }
        }
        return yunpianClient;
    }
}
