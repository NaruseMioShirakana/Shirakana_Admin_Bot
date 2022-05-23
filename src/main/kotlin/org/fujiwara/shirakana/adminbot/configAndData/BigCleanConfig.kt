package org.fujiwara.shirakana.adminbot.configAndData

import net.mamoe.mirai.console.data.ReadOnlyPluginConfig
import net.mamoe.mirai.console.data.ValueDescription
import net.mamoe.mirai.console.data.ValueName
import net.mamoe.mirai.console.data.value

object ShirakanaBigCleanSetting : ReadOnlyPluginConfig("Shirakana_AdminBot_Setting") {
    @ValueName("big_clean_switch")
    @ValueDescription("大清洗开关（偏执度达到100大清洗）")
    val big_clean_switch: Boolean by value(true)

    @ValueName("small_clean_switch")
    @ValueDescription("小清洗开关（偏执度为50时小清洗）")
    val small_clean_switch: Boolean by value(true)

    @ValueName("repeat_join_clean")
    @ValueDescription("重复加群自动拒绝及清理开关")
    val repeat_join_clean: Boolean by value(true)

    @ValueName("Standard_Name")
    @ValueDescription("重复加群自动拒绝及清理开关")
    val nameStandard: String by value("")
}