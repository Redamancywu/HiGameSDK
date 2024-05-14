package com.hi.base.plugin;

import java.util.List;

public class PluginInfo {

    /**
     * 插件类型， 比如analytics
     */
    private String type;

    /**
     * 插件名称
     */
    private String name;

    /**
     * 插件实现类，完整类路径
     */
    private String clazz;

    /**
     * 插件参数
     */
    private HiGameConfig gameConfig;

    /**
     * clazz实例化后的插件对象
     */
    private IPlugin plugin;
    /**
     * 子插件对象
     */
    private List<PluginInfo> children;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public HiGameConfig getGameConfig() {
        return gameConfig;
    }

    public void setGameConfig(HiGameConfig gameConfig) {
        this.gameConfig = gameConfig;
    }

    public IPlugin getPlugin() {
        return plugin;
    }

    public void setPlugin(IPlugin plugin) {
        this.plugin = plugin;
    }

    public List<PluginInfo> getChildren() {
        return children;
    }

    public void setChildren(List<PluginInfo> children) {
        this.children = children;
    }
    public HiGameConfig getConfig() {
        return gameConfig;
    }
}
