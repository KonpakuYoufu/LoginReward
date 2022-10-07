package com.github.konpakuyoufu;

import emu.grasscutter.game.mail.Mail.MailItem;

import java.util.Arrays;
import java.util.List;

public final class PluginConfig {
    public String title = "登录奖励";
    public String content = "亲爱的履刑者，你又在打电动哦";
    public String sender = "Paimon";
    public long expireTime = 604800;
    public boolean importance = false;
    public List<MailItem> itemList = Arrays.asList(new MailItem(201,9999),new MailItem(202, 99999));
}
