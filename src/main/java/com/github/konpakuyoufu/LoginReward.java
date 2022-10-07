package com.github.konpakuyoufu;

import emu.grasscutter.plugin.Plugin;
import emu.grasscutter.server.event.EventHandler;
import emu.grasscutter.server.event.HandlerPriority;
import emu.grasscutter.server.event.player.PlayerJoinEvent;
import emu.grasscutter.utils.JsonUtils;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

// 插件主类 The main class for the plugin
public class LoginReward extends Plugin {
    private static LoginReward instance;

    private PluginConfig configuration = new PluginConfig();
    public static RewardedList rewarded;
    public static LoginReward getInstance() {
        return instance;
    }

    @Override
    public void onLoad(){
        instance = this;

        try {
            // 获取配置文件 Get the configuration file
            File config = new File(this.getDataFolder(), "config.json");

            if (config.exists()){
                // 把配置加入config类的实例 Put the configuration into an instance of the config class
                this.configuration = JsonUtils.loadToClass(new FileReader(config), PluginConfig.class);

            }else if(!config.exists() && !config.createNewFile()) {
                this.getLogger().error("Failed to create config file.");

            } else {
                FileWriter writer = new FileWriter(config);
                writer.write(JsonUtils.encode(configuration));
                writer.close();

                this.getLogger().info("Saved default config file.");
            }

            // 加载配置文件 Load the configuration
            EventListeners.loadConfig();

            // 获取已奖励玩家列表 Get the rewarded list file
            File reward = new File(this.getDataFolder(), "rewarded.json");

            if (!reward.exists()){
                if (reward.createNewFile()){
                    rewarded = new RewardedList();
                    FileWriter writer = new FileWriter(reward);
                    writer.write(JsonUtils.encode(rewarded));
                }else {
                    this.getLogger().error("Failed to create rewarded list file.");
                }
            }else {
                rewarded = JsonUtils.loadToClass(new FileReader(reward), RewardedList.class);
            }

        } catch (IOException exception) {
            this.getLogger().error("Failed to create config file.", exception);
        }

        // 记录插件状态 Log a plugin status message
        this.getLogger().info("The LoginReward plugin has been loaded.");
    }

    @Override
    public void onEnable() {
        // 注册事件监听器 Register event listeners
        new EventHandler<>(PlayerJoinEvent.class)
                .priority(HandlerPriority.LOW)
                .listener(EventListeners::onJoin)
                .register(this);

        // 记录插件状态 Log a plugin status message
        this.getLogger().info("The LoginReward plugin has been enabled.");
    }

    @Override
    public void onDisable() {
        // 记录插件状态 Log a plugin status message
        this.getLogger().info("The LoginReward plugin has been disabled.");
    }

    public PluginConfig getConfiguration() {
        return this.configuration;
    }
}
