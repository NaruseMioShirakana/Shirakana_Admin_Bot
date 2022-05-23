package org.fujiwara.shirakana.adminbot

import net.mamoe.mirai.contact.isAdministrator
import net.mamoe.mirai.event.*
import net.mamoe.mirai.event.events.*
import net.mamoe.mirai.message.data.At
import net.mamoe.mirai.utils.info
import org.fujiwara.shirakana.adminbot.configAndData.*

public object ShirakanaEventListener : SimpleListenerHost() {
    @EventHandler
    internal suspend fun MemberJoinRequestEvent.handle() {
        if(ShirakanaBigCleanSetting.repeat_join_clean){
            if(ShirakanaDataGroupMember.selectedGroups.contains(groupId.toString())){
                if(ShirakanaDataGroupMember.selectedGroupMembers.contains(fromId.toString())){
                    if(ShirakanaDataGroupMember.groupMembersTarget.contains(fromId.toString())){
                        reject(false,"你已经加入了另一个群，且不处于跨群白名单内，如有异议可找管理协商。")
                        return
                    }
                }
            }
        }
        if(ShirakanaDataGroupMember.ShirakanaBlackListGroup.contains(fromId.toString())){
            reject(false,"你已被清洗，无法再次加入群聊，如有异议可找管理协商。")
        }
    }
    @EventHandler
    internal suspend fun MemberJoinEvent.handle() {
        if(ShirakanaDataGroupMember.selectedGroups.contains(groupId.toString())) {
            ShirakanaDataGroupMember.selectedGroupMembers.add(member.id.toString())
        }
    }
    @EventHandler
    internal suspend fun GroupMessageEvent.handle() {
        if(ShirakanaDataFlags.flagSmallCleanStart) {
            if(group.id==ShirakanaDataFlags.flagSmallCleanTarget&&sender.isAdministrator()){
                if(message.contentToString() == "结束清洗"){
                    ShirakanaDataFlags.flagSmallCleanStart = false
                    ShirakanaDataFlags.flagSmallCleanTarget = 0L
                    group.sendMessage("清洗已经结束，请管理员输入/Tutu kick来确保其他群也清洗完毕")
                    return
                }
                for(memberId in ShirakanaDataGroupMember.smallCleanTarget){
                    if(message.contentToString()== At(memberId.toLong()).contentToString()){
                        val member = group.get(memberId.toLong())
                        group.sendMessage("群友："+member?.nick+"已被清洗\n记得输入“结束清洗”来结束大清洗")
                        member?.kick("你已被清洗")
                        ShirakanaDataGroupMember.ShirakanaBlackListGroup.add(memberId)
                    }
                }
            }
        }
    }
    @EventHandler
    internal suspend fun MemberLeaveEvent.handle() {
        if(ShirakanaDataGroupMember.selectedGroups.contains(groupId.toString())) {
            for(groupId in ShirakanaDataGroupMember.selectedGroups){
                val groupThis = bot.getGroup(groupId.toLong())
                if(groupThis!=null){
                    for(memberThis in groupThis.members){
                        if(member.id==memberThis.id){
                            return
                        }
                    }
                }
            }
            ShirakanaDataGroupMember.selectedGroupMembers.remove(member.id.toString())
            ShirakanaDataGroupMember.bigCleanTarget.remove(member.id.toString())
            ShirakanaDataGroupMember.smallCleanTarget.remove(member.id.toString())
        }
    }
    @EventHandler
    internal suspend fun BotOnlineEvent.handle(){
        for(group_id in ShirakanaDataGroupMember.selectedGroups){
            val selectedGroup = bot.getGroup(group_id.toLong())?.members
            ShirakanaDataGroupMember.selectedGroupMembers.clear()
            if (selectedGroup != null) {
                for(member in selectedGroup){
                    ShirakanaDataGroupMember.selectedGroupMembers.add(member.id.toString())
                }
            }
        }
        ShirakanaAdminBot.logger.info { "群员信息重载完毕" }
    }
}




