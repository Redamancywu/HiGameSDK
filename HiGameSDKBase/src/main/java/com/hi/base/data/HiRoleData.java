package com.hi.base.data;

/***
 * 用户角色数据
 * 已经登录的角色相关数据
 * 有的渠道需要统计角色相关数据
 *
 */
public class HiRoleData {

    public final static int TYPE_CREATE_ROLE = 1;			//创建角色
    public final static int TYPE_ENTER_GAME = 2;			//进入游戏
    public final static int TYPE_LEVEL_UP = 3;			//等级升级
    public final static int TYPE_EXIT_GAME = 4;			//退出游戏

    private Integer type;               //上报类型，1：创建角色；2：进入游戏；3：等级升级；4：退出游戏；其他类型

    private String uid;                   //对应UGUser中的id


    private String serverID;             //服务器ID

    private String roleID;               //角色ID

    private Integer channelID;          //当前用户所属渠道ID
    private String roleName;             //角色名称
    private String roleLevel;            //角色等级
    private String serverName;           //服务器名称
    private String vip;                  //VIP等级
    private Long createTime;             //创建时间, 单位s
    private Long lastLevelUpTime;        //最后升级时间，单位s

    private String extraData;           // 扩展数据，json格式，暂时无用

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getServerID() {
        return serverID;
    }

    public void setServerID(String serverID) {
        this.serverID = serverID;
    }

    public String getRoleID() {
        return roleID;
    }

    public void setRoleID(String roleID) {
        this.roleID = roleID;
    }

    public Integer getChannelID() {
        return channelID;
    }

    public void setChannelID(Integer channelID) {
        this.channelID = channelID;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleLevel() {
        return roleLevel;
    }

    public void setRoleLevel(String roleLevel) {
        this.roleLevel = roleLevel;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getVip() {
        return vip;
    }

    public void setVip(String vip) {
        this.vip = vip;
    }

    public Long getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Long createTime) {
        this.createTime = createTime;
    }

    public Long getLastLevelUpTime() {
        return lastLevelUpTime;
    }

    public void setLastLevelUpTime(Long lastLevelUpTime) {
        this.lastLevelUpTime = lastLevelUpTime;
    }

    public String getExtraData() {
        return extraData;
    }

    public void setExtraData(String extraData) {
        this.extraData = extraData;
    }
}
