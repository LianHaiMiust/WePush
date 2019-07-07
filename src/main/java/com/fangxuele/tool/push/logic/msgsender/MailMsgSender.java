package com.fangxuele.tool.push.logic.msgsender;

import cn.hutool.core.io.FileUtil;
import cn.hutool.extra.mail.MailAccount;
import cn.hutool.extra.mail.MailUtil;
import com.fangxuele.tool.push.App;
import com.fangxuele.tool.push.bean.MailMsg;
import com.fangxuele.tool.push.logic.PushControl;
import com.fangxuele.tool.push.logic.msgmaker.MailMsgMaker;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.List;

/**
 * <pre>
 * E-Mail发送器
 * </pre>
 *
 * @author <a href="https://github.com/rememberber">RememBerBer</a>
 * @since 2019/6/23.
 */
@Slf4j
public class MailMsgSender implements IMsgSender {

    public volatile static MailAccount mailAccount;

    private MailMsgMaker mailMsgMaker;

    public MailMsgSender() {
        mailMsgMaker = new MailMsgMaker();
        mailAccount = getMailAccount();
    }

    @Override
    public SendResult send(String[] msgData) {
        SendResult sendResult = new SendResult();

        try {
            MailMsg mailMsg = mailMsgMaker.makeMsg(msgData);
            String tos = msgData[0];
            if (PushControl.dryRun) {
                sendResult.setSuccess(true);
                return sendResult;
            } else {
                if (StringUtils.isEmpty(mailMsg.getMailFiles())) {
                    MailUtil.send(mailAccount, tos, mailMsg.getMailTitle(), mailMsg.getMailContent(), true);
                } else {
                    MailUtil.send(mailAccount, tos, mailMsg.getMailTitle(), mailMsg.getMailContent(), true, FileUtil.file(mailMsg.getMailFiles()));
                }
                sendResult.setSuccess(true);
            }
        } catch (Exception e) {
            sendResult.setSuccess(false);
            sendResult.setInfo(e.getMessage());
            log.error(e.toString());
        }

        return sendResult;
    }

    @Override
    public SendResult asyncSend(String[] msgData) {
        return null;
    }

    public SendResult sendTestMail(String tos) {
        SendResult sendResult = new SendResult();

        try {
            MailUtil.send(mailAccount, tos, "这是一封来自WePush的测试邮件",
                    "<h1>恭喜，配置正确，邮件发送成功！</h1><p>来自WePush，一款专注于批量推送的小而美的工具。</p>", true);
            sendResult.setSuccess(true);
        } catch (Exception e) {
            sendResult.setSuccess(false);
            sendResult.setInfo(e.getMessage());
            log.error(e.toString());
        }

        return sendResult;
    }

    /**
     * 发送推送结果
     *
     * @param tos
     * @return
     */
    public SendResult sendPushResultMail(List<String> tos, String title, String content, File[] files) {
        SendResult sendResult = new SendResult();

        try {
            MailUtil.send(mailAccount, tos, title, content, true, files);
            sendResult.setSuccess(true);
        } catch (Exception e) {
            sendResult.setSuccess(false);
            sendResult.setInfo(e.getMessage());
            log.error(e.toString());
        }

        return sendResult;
    }

    /**
     * 获取E-Mail发送客户端
     *
     * @return MailAccount
     */
    private static MailAccount getMailAccount() {
        if (mailAccount == null) {
            synchronized (MailMsgSender.class) {
                if (mailAccount == null) {
                    String mailHost = App.config.getMailHost();
                    String mailPort = App.config.getMailPort();
                    String mailFrom = App.config.getMailFrom();
                    String mailUser = App.config.getMailUser();
                    String mailPassword = App.config.getMailPassword();

                    mailAccount = new MailAccount();
                    mailAccount.setHost(mailHost);
                    mailAccount.setPort(Integer.valueOf(mailPort));
                    mailAccount.setAuth(true);
                    mailAccount.setFrom(mailFrom);
                    mailAccount.setUser(mailUser);
                    mailAccount.setPass(mailPassword);
                    mailAccount.setSslEnable(App.config.isMailUseSSL());
                    mailAccount.setStartttlsEnable(App.config.isMailUseStartTLS());
                }
            }
        }
        return mailAccount;
    }
}
