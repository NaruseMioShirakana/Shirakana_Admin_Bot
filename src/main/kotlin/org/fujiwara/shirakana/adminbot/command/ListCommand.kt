package org.fujiwara.shirakana.adminbot.command

import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.CompositeCommand
import net.mamoe.mirai.console.command.SimpleCommand
import org.fujiwara.shirakana.adminbot.configAndData.*
import org.fujiwara.shirakana.adminbot.*



object ShirakanaSelectGroups : CompositeCommand(
    ShirakanaAdminBot, "SelectGroups",
    description = "在指定群聊中启用/关闭功能",
) {
    @SubCommand
    @Description("在指定群中启用")
    suspend fun CommandSender.add(vararg GroupTarget: String) {
        if (bot == null) {
            sendMessage("未指定机器人")
            return
        }
        var addedGroups = ""
        for(group_id in GroupTarget){
            if(group_id==null||group_id==""){
                continue
            }
            if(bot?.getGroup(group_id.toLong())!=null){
                if(ShirakanaDataGroupMember.selectedGroups.add(group_id)) {
                    addedGroups += group_id
                }
            }
        }
        if(addedGroups==""){
            sendMessage("指定的群都已被添加或添加失败")
        }else{
            sendMessage("群号:"+addedGroups+"添加完毕")
        }
        sendMessage("当前指定的群号："+ShirakanaDataGroupMember.selectedGroups)
    }
    @SubCommand
    @Description("在指定群中禁用")
    suspend fun CommandSender.del(vararg GroupTarget: String) {
        if (bot == null) {
            sendMessage("未指定机器人")
            return
        }
        var addedGroups = ""
        for(group_id in GroupTarget){
            if(group_id==null||group_id==""){
                continue
            }
            if(ShirakanaDataGroupMember.selectedGroups.remove(group_id)) {
                addedGroups += group_id
            }
        }
        if(addedGroups==""){
            sendMessage("指定的群都不在列表中或删除失败")
        }else{
            sendMessage("群号:"+addedGroups+"删除完毕")
        }
        sendMessage("当前指定的群号："+ShirakanaDataGroupMember.selectedGroups)
    }
    @SubCommand
    @Description("列出启用的群")
    suspend fun CommandSender.list() {
        if (bot == null) {
            sendMessage("未指定机器人")
            return
        }
        var addedGroups = ""
        for(group_id in ShirakanaDataGroupMember.selectedGroups){
            val thisGroup=bot?.getGroup(group_id.toLong())
            if(thisGroup!=null) {
                addedGroups += "群名称："+thisGroup.name+" 群号："+group_id+"\n"
            }
        }
        if(addedGroups==""){
            sendMessage("当前没有群启用功能")
        }else{
            sendMessage("以下群聊启用了功能:\n$addedGroups")
        }
    }
    @SubCommand
    @Description("不重复群员数量")
    suspend fun CommandSender.amount() {
        var tempI =0
        for(Temp_id in ShirakanaDataGroupMember.selectedGroupMembers){
            tempI++
        }
        sendMessage("所有功能启用群聊的总人数：$tempI")
    }
    @SubCommand
    @Description("重载不重复群员信息")
    suspend fun CommandSender.reload() {
        if (bot == null) {
            sendMessage("未指定机器人")
            return
        }
        ShirakanaDataGroupMember.selectedGroupMembers.clear()
        for(group_id in ShirakanaDataGroupMember.selectedGroups){
            val selectedGroup = bot?.getGroup(group_id.toLong())?.members
            if (selectedGroup != null) {
                for(member in selectedGroup){
                    ShirakanaDataGroupMember.selectedGroupMembers.add(member.id.toString())
                }
            }
        }
        sendMessage("重载群成员完毕")
    }
}

object AddRemTarget : CompositeCommand(
    ShirakanaAdminBot, "CleanList",
    description = "添加/删除重复排查名单",
) {
    @SubCommand
    @Description("重复加群清理列表操作")
    suspend fun CommandSender.repeat(parameter1 : String ,GroupId : String ,vararg GroupMembersTarget: String) {
        if (bot == null) {
            sendMessage("未指定机器人")
            return
        }
        if(ShirakanaDataGroupMember.selectedGroups.contains(GroupId)){
            if(parameter1=="add"){
                //var message="以下群友被加入禁止重复加群名单："
                var targets=""
                for(target in GroupMembersTarget){
                    if(ShirakanaDataGroupMember.selectedGroupMembers.contains(target)){
                        if(ShirakanaDataGroupMember.groupMembersTarget.add(target)){
                            val groupThis=bot?.getGroup(GroupId.toLong())?.members
                            if (groupThis != null) {
                                for(member_this in groupThis){
                                    if(member_this.id==target.toLong()){
                                        targets+= "昵称："+member_this.nick+" QQ号："+member_this.id+"\n"
                                        break
                                    }
                                }
                            }
                        }
                    }
                }
                if (targets==""){
                    sendMessage("列表中无新增成员")
                }else{
                    val groupThis=bot?.getGroup(GroupId.toLong())
                    if (groupThis != null) {
                        sendMessage("在群名称："+groupThis.name+"群号："+groupThis.id+"\n以下群友被加入禁止重复加群名单：\n$targets")
                    }else{
                        sendMessage("以下群友被加入禁止重复加群名单：\n$targets")
                    }
                }
            }
            if(parameter1=="del"){
                //var message="以下群友被加入禁止重复加群名单："
                var targets=""
                for(target in GroupMembersTarget){
                    if(ShirakanaDataGroupMember.groupMembersTarget.remove(target)){
                        val groupThis=bot?.getGroup(GroupId.toLong())?.members
                        if (groupThis != null) {
                            for(member_this in groupThis){
                                if(member_this.id==target.toLong()){
                                    targets+= "昵称："+member_this.nick+" QQ号："+member_this.id+"\n"
                                    break
                                }
                            }
                        }
                    }
                }
                if (targets==""){
                    sendMessage("列表中无减少的成员")
                }else{
                    val groupThis=bot?.getGroup(GroupId.toLong())
                    if (groupThis != null) {
                        sendMessage("在群名称："+groupThis.name+"群号："+groupThis.id+"\n以下群友被移除出禁止重复加群名单：\n$targets")
                    }else{
                        sendMessage("以下群友被移除出禁止重复加群名单：\n$targets")
                    }
                }
            }
            if(parameter1=="list"){
                var targets = ""
                for(target in ShirakanaDataGroupMember.groupMembersTarget){
                    val groupThis=bot?.getGroup(GroupId.toLong())?.members
                    if (groupThis != null) {
                        for(member_this in groupThis){
                            if(member_this.id==target.toLong()){
                                targets+= "昵称："+member_this.nick+" QQ号："+member_this.id+"\n"
                                break
                            }
                        }
                    }
                }
                if (targets==""){
                    sendMessage("列表中无成员")
                }else{
                    val groupThis=bot?.getGroup(GroupId.toLong())
                    if (groupThis != null) {
                        sendMessage("在群名称："+groupThis.name+"群号："+groupThis.id+"\n以下群友在禁止重复加群名单中：\n$targets")
                    }else{
                        sendMessage("以下群友在禁止重复加群名单中：\n$targets")
                    }
                }
            }
            if(parameter1!="list"&&parameter1!="del"&&parameter1!="add"){
                sendMessage("<parameter1>处参数错误，正确的参数：add（添加）/del（删除）/list（查看列表）")
            }
        }else{
            sendMessage("该群未启用功能")
        }
    }
    @SubCommand
    @Description("小清洗列表操作")
    suspend fun CommandSender.smallclean(parameter1 : String ,GroupId : String ,vararg GroupMembersTarget: String) {
        if (bot == null) {
            sendMessage("未指定机器人")
            return
        }
        if(ShirakanaDataGroupMember.selectedGroups.contains(GroupId)) {
            if (parameter1 == "add") {
                //var message="以下群友被加入禁止重复加群名单："
                var targets = ""
                for (target in GroupMembersTarget) {
                    if (ShirakanaDataGroupMember.selectedGroupMembers.contains(target)) {
                        if (ShirakanaDataGroupMember.smallCleanTarget.add(target)) {
                            val groupThis = bot?.getGroup(GroupId.toLong())?.members
                            if (groupThis != null) {
                                for (member_this in groupThis) {
                                    if (member_this.id == target.toLong()) {
                                        targets += "昵称：" + member_this.nick + " QQ号：" + member_this.id + "\n"
                                        break
                                    }
                                }
                            }
                        }
                    }
                }
                if (targets == "") {
                    sendMessage("列表中无新增成员")
                } else {
                    val groupThis = bot?.getGroup(GroupId.toLong())
                    if (groupThis != null) {
                        sendMessage("在群名称：" + groupThis.name + "群号：" + groupThis.id + "\n以下群友被加入小清洗名单：\n$targets")
                    } else {
                        sendMessage("以下群友被加入小清洗名单：\n$targets")
                    }
                }
            }
            if (parameter1 == "del") {
                //var message="以下群友被加入禁止重复加群名单："
                var targets = ""
                for (target in GroupMembersTarget) {
                    if (ShirakanaDataGroupMember.smallCleanTarget.remove(target)) {
                        val groupThis = bot?.getGroup(GroupId.toLong())?.members
                        if (groupThis != null) {
                            for (member_this in groupThis) {
                                if (member_this.id == target.toLong()) {
                                    targets += "昵称：" + member_this.nick + " QQ号：" + member_this.id + "\n"
                                    break
                                }
                            }
                        }
                    }
                }
                if (targets == "") {
                    sendMessage("列表中无减少的成员")
                } else {
                    val groupThis = bot?.getGroup(GroupId.toLong())
                    if (groupThis != null) {
                        sendMessage("在群名称：" + groupThis.name + "群号：" + groupThis.id + "\n以下群友被移除出小清洗名单：\n$targets")
                    } else {
                        sendMessage("以下群友被移除出小清洗名单：\n$targets")
                    }
                }
            }
            if (parameter1 == "list") {
                var targets = ""
                for (target in ShirakanaDataGroupMember.smallCleanTarget) {
                    val groupThis = bot?.getGroup(GroupId.toLong())?.members
                    if (groupThis != null) {
                        for (member_this in groupThis) {
                            if (member_this.id == target.toLong()) {
                                targets += "昵称：" + member_this.nick + " QQ号：" + member_this.id + "\n"
                                break
                            }
                        }
                    }
                }
                if (targets == "") {
                    sendMessage("列表中无成员")
                } else {
                    val groupThis = bot?.getGroup(GroupId.toLong())
                    if (groupThis != null) {
                        sendMessage("在群名称：" + groupThis.name + "群号：" + groupThis.id + "\n以下群友在小清洗名单中：\n$targets")
                    } else {
                        sendMessage("以下群友在小清洗名单中：\n$targets")
                    }
                }
            }
            if (parameter1 != "list" && parameter1 != "del" && parameter1 != "add") {
                sendMessage("<parameter1>处参数错误，正确的参数：add（添加）/del（删除）/list（查看列表）")
            }
        }else{
            sendMessage("该群未启用功能")
        }
    }
    @SubCommand
    @Description("大清洗保护名单操作")
    suspend fun CommandSender.bigclean(parameter1 : String ,GroupId : String ,vararg GroupMembersTarget: String) {
        if (bot == null) {
            sendMessage("未指定机器人")
            return
        }
        if(ShirakanaDataGroupMember.selectedGroups.contains(GroupId)) {
            if (parameter1 == "add") {
                //var message="以下群友被加入禁止重复加群名单："
                var targets = ""
                for (target in GroupMembersTarget) {
                    if (ShirakanaDataGroupMember.selectedGroupMembers.contains(target)) {
                        if (ShirakanaDataGroupMember.bigCleanTarget.add(target)) {
                            val groupThis = bot?.getGroup(GroupId.toLong())?.members
                            if (groupThis != null) {
                                for (member_this in groupThis) {
                                    if (member_this.id == target.toLong()) {
                                        targets += "昵称：" + member_this.nick + " QQ号：" + member_this.id + "\n"
                                        break
                                    }
                                }
                            }
                        }
                    }
                }
                if (targets == "") {
                    sendMessage("列表中无新增成员")
                } else {
                    val groupThis = bot?.getGroup(GroupId.toLong())
                    if (groupThis != null) {
                        sendMessage("在群名称：" + groupThis.name + "群号：" + groupThis.id + "\n以下群友被加入大清洗保护名单：\n$targets")
                    } else {
                        sendMessage("以下群友被加入大清洗保护名单：\n$targets")
                    }
                }
            }
            if (parameter1 == "del") {
                //var message="以下群友被加入禁止重复加群名单："
                var targets = ""
                for (target in GroupMembersTarget) {
                    if (ShirakanaDataGroupMember.bigCleanTarget.remove(target)) {
                        val groupThis = bot?.getGroup(GroupId.toLong())?.members
                        if (groupThis != null) {
                            for (member_this in groupThis) {
                                if (member_this.id == target.toLong()) {
                                    targets += "昵称：" + member_this.nick + " QQ号：" + member_this.id + "\n"
                                    break
                                }
                            }
                        }
                    }
                }
                if (targets == "") {
                    sendMessage("列表中无减少的成员")
                } else {
                    val groupThis = bot?.getGroup(GroupId.toLong())
                    if (groupThis != null) {
                        sendMessage("在群名称：" + groupThis.name + "群号：" + groupThis.id + "\n以下群友被移除出大清洗保护名单：\n$targets")
                    } else {
                        sendMessage("以下群友被移除出大清洗保护名单：\n$targets")
                    }
                }
            }
            if (parameter1 == "list") {
                var targets = ""
                for (target in ShirakanaDataGroupMember.bigCleanTarget) {
                    val groupThis = bot?.getGroup(GroupId.toLong())?.members
                    if (groupThis != null) {
                        for (member_this in groupThis) {
                            if (member_this.id == target.toLong()) {
                                targets += "昵称：" + member_this.nick + " QQ号：" + member_this.id + "\n"
                                break
                            }
                        }
                    }
                }
                if (targets == "") {
                    sendMessage("列表中无成员")
                } else {
                    val groupThis = bot?.getGroup(GroupId.toLong())
                    if (groupThis != null) {
                        sendMessage("在群名称：" + groupThis.name + "群号：" + groupThis.id + "\n以下群友在大清洗保护名单中：\n$targets")
                    } else {
                        sendMessage("以下群友在大清洗保护名单中：\n$targets")
                    }
                }
            }
            if (parameter1 != "list" && parameter1 != "del" && parameter1 != "add") {
                sendMessage("<parameter1>处参数错误，正确的参数：add（添加）/del（删除）/list（查看列表）")
            }
        }else{
            sendMessage("该群未启用功能")
        }
    }
    @SubCommand
    @Description("查看指令帮助")
    suspend fun CommandSender.help() {
        sendMessage("列表相关操作的帮助：\n[]括起来的代表可选参数\n<>括起来的代表必填参数\n/Tutu help：查看大清洗相关功能的帮助\n/CleanList qa [QQ号]快速添加至阻止重复加群列表（但不安全，容易出BUG）\n/CleanList qd [QQ号]快速删除出阻止重复加群列表（但不安全，容易出BUG）\n/CleanRepeat <QQ群>在启用的所有群聊中（除了指定的QQ群）清理不允许重复加群的群员\n/SelectGroups <add/del> <群号>：添加/删除功能启用的群聊\n/SelectGroups list：查看启用功能的群\n/SelectGroups reload：重载非重复群员统计\n/SelectGroups amount：查看功能启用的群聊中非重复群员个数\n/CleanList <repeat/smallclean/bigclean> <add/del/list> <群号> [QQ号]：重复/小清洗/大清洗保护名单的增加/删除/查看，使用时必须指定一个该ID所在群号（之后再所有启用的群聊中通用），QQ号可以设置多个，需要用空格隔开")
    }
    @SubCommand
    @Description("快速添加名单")
    suspend fun CommandSender.qa(vararg GroupMembersTarget: String) {
        var msgThisA = ""
        for(target in GroupMembersTarget){
            if (ShirakanaDataGroupMember.selectedGroupMembers.contains(target)) {
                if (ShirakanaDataGroupMember.groupMembersTarget.add(target)) {
                    msgThisA += "$target "
                }
            }
        }
        sendMessage("加入的QQ号：$msgThisA")
    }
    @SubCommand
    @Description("快速减少名单")
    suspend fun CommandSender.qd(vararg GroupMembersTarget: String) {
        var msgThisA = ""
        for(target in GroupMembersTarget){
            if (ShirakanaDataGroupMember.selectedGroupMembers.contains(target)) {
                if (ShirakanaDataGroupMember.groupMembersTarget.remove(target)) {
                    msgThisA += "$target "
                }
            }
        }
        sendMessage("减少的QQ号：$msgThisA")
    }
}
