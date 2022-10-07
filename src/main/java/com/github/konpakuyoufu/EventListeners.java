package com.github.konpakuyoufu;

import emu.grasscutter.game.mail.Mail;
import emu.grasscutter.game.player.Player;
import emu.grasscutter.server.event.player.PlayerJoinEvent;
import emu.grasscutter.utils.JsonUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Instant;
import java.util.Calendar;

//事件监听器 Event listeners
public final class EventListeners {
    private static Mail mail = new Mail();

    // 当玩家加入游戏时调用 Called when the player joins the server
    public static void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        Calendar refresh_calendar = LoginReward.rewarded.calendar;
        refresh_calendar.add(Calendar.DATE, 1);

        // 检查是否应该刷新已奖励玩家列表 Check if the rewarded list should be refreshed
        if (Calendar.getInstance().after(refresh_calendar)){

            // 清空已奖励玩家列表 Clear the rewarded list
            LoginReward.rewarded.list.clear();

            // 记录今日凌晨四点整的时间 Record the time at 4am today
            Calendar calendar = Calendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 4);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            LoginReward.rewarded.calendar = calendar;
        }else {

            // 判断玩家是否在已奖励列表中 Check if the player is in the rewarded list
            for (int i : LoginReward.rewarded.list){
                if (i == player.getUid()){
                    return;
                }
            }

        }

        // 给玩家发送邮件奖励 Send mail rewards to the player
        player.sendMail(mail);

        // 将玩家加入已奖励列表 Add the player to the rewarded list
        LoginReward.rewarded.list.add(player.getUid());

        // 将已奖励列表保存至rewarded.json Save the rewarded list to rewarded.json
        File reward = new File(LoginReward.getInstance().getDataFolder(), "rewarded.json");
        try {
            FileWriter writer = new FileWriter(reward);
            writer.write(JsonUtils.encode(LoginReward.rewarded));
        } catch (IOException exception) {
            LoginReward.getInstance().getLogger().error("Failed to save rewarded list file.", exception);
        }

    }

    public static void loadConfig(){
        PluginConfig config = LoginReward.getInstance().getConfiguration();
        mail.mailContent.title = config.title;
        mail.mailContent.content = config.content;
        mail.mailContent.sender = config.sender;
        mail.expireTime = Instant.now().getEpochSecond() + config.expireTime;
        mail.importance = config.importance ? 1 : 0;
        mail.itemList = config.itemList;
    }
}
