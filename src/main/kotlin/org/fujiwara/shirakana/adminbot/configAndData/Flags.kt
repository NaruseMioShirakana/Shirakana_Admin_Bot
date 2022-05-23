package org.fujiwara.shirakana.adminbot.configAndData

import net.mamoe.mirai.console.data.AutoSavePluginData
import net.mamoe.mirai.console.data.ValueDescription
import net.mamoe.mirai.console.data.ValueName
import net.mamoe.mirai.console.data.value

public object ShirakanaDataFlags : AutoSavePluginData("Shirakana_AdminBot_GroupMember_Data") {
    @ValueName("flagSmallCleanStart")
    @ValueDescription("小清洗状态")
    var flagSmallCleanStart: Boolean by value(false)

    @ValueName("flagSmallCleanStart")
    @ValueDescription("小清洗目标群")
    var flagSmallCleanTarget: Long by value(0L)
}