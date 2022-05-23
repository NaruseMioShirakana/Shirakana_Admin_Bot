package org.fujiwara.shirakana.adminbot.configAndData


import net.mamoe.mirai.console.data.AutoSavePluginData
import net.mamoe.mirai.console.data.ValueDescription
import net.mamoe.mirai.console.data.ValueName
import net.mamoe.mirai.console.data.value

public object ShirakanaDataGroupMember : AutoSavePluginData("Shirakana_AdminBot_GroupMember_Data") {
    @ValueName("selected_group_members")
    @ValueDescription("统计群员")
    val selectedGroupMembers: MutableSet<String> by value()

    @ValueName("group_members_target")
    @ValueDescription("统计群员（重复加群重点清理目标）")
    val groupMembersTarget: MutableSet<String> by value()

    @ValueName("small_clean_target")
    @ValueDescription("统计群员（管理指定的小清洗目标）")
    val smallCleanTarget: MutableSet<String> by value()

    @ValueName("big_clean_target")
    @ValueDescription("统计群员（管理指定的大清洗目标）")
    val bigCleanTarget: MutableSet<String> by value()

    @ValueName("selected_groups")
    @ValueDescription("统计开启功能的群聊")
    val selectedGroups: MutableSet<String> by value()

    @ValueName("big_clean_paranoia")
    @ValueDescription("偏执度（管理员San值）")
    var bigCleanParanoia: Long by value(0L)

    @ValueName("black_list")
    @ValueDescription("黑名单")
    val ShirakanaBlackListGroup: MutableSet<String> by value()
}