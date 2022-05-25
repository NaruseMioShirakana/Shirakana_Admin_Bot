package org.fujiwara.shirakana.adminbot.configAndData

import net.mamoe.mirai.console.data.AutoSavePluginData
import net.mamoe.mirai.console.data.ValueDescription
import net.mamoe.mirai.console.data.ValueName
import net.mamoe.mirai.console.data.value

public object ShirakanaAdministratorList : AutoSavePluginData("Shirakana_AdminBot_AdministratorList_Data") {
    @ValueName("AdministratorList")
    @ValueDescription("管理员群")
    var adminGroup: Long by value(123L)
}