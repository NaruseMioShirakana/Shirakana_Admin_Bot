# Shirakana_Admin_Bot
一个基于mirai，针对粉丝群管理的插件
插件介绍：
本插件实现的功能主要有：

1、指定启用的群聊。
2、识别所有启用的群聊中非重复成员并统计之。
3、用一个可以由拥有相关权限用户操作的“黑名单”来实现自动清理重复加群的用户（防止同一个人为了报复在多个群里实行炸群行动）。
4、大清洗，清洗名单（警告作用），名单加急（开始清洗），群内点名枪毙公决（踢出群）。
5、清理指定时间内未发言的群员（清理定时炸弹），这个过程排除处于保护列表或拥有专属头衔亦或是在群备注中注明了指定语句的群员。
6、(TODO)XX偏执度系统，创建一个XX偏执度变量，该变量逐日自增，也可由管理员控制，到达50开始小清洗，到达100进行大清洗并清零，XX偏执度会通过图片的方式发送，如同下图：
d455e54e-cb9f-48af-8884-4b5a784c5cdb-image.png
7、(TODO)根据群友以及社区的建议写的其他功能。
8、彩蛋功能。
插件指令及其作用
([]括起来的代表可选参数，<>括起来的代表必填参数)：
org.fujiwara.shirakana.adminbot.plugin:command.listhelp
   /ListHelp：列出插件帮助
org.fujiwara.shirakana.adminbot.plugin:command.selectgroups
/SelectGroups <add/del> <群号>：添加/删除功能启用的群聊
/SelectGroups list：查看启用功能的群
/SelectGroups reload：重载非重复群员统计
/SelectGroups amount：查看非重复群员个数
org.fujiwara.shirakana.adminbot.plugin:command.cleanlist
/CleanList bigclean <add/del/list> <GroupId> [GroupMembersTarget]
#大清洗保护名单操作，将群聊GroupId的指定群员GroupMembersTarget添加/删除入功能5的例外名单（保护名单）
/CleanList repeat <add/del/list> <GroupId> [GroupMembersTarget]    
#禁止重复加群用户名单操作，将群聊GroupId的指定群员GroupMembersTarget添加/删除入禁止重复加群的名单
#（在此名单内的用户无法添加一个以上的插件启用群聊）
/CleanList smallclean <add/del/list> <GroupId> [GroupMembersTarget]
#小清洗列表操作，将群聊GroupId的指定群员GroupMembersTarget添加/删除入清洗名单
#注：GroupMembersTarget为QQ号，可指定多个QQ，中间用空格隔开
org.fujiwara.shirakana.adminbot.plugin:command.tutu
全都给图图乐

/Tutu SmallClean <群号>：
#在指定群开启小清洗（清洗目标为CleanList smallclean指定的人，执行后可以从中选择
#任意人数进行清洗
/Tutu BigClean：
#开启大清洗（会跳过CleanList bigclean指定的人、有群专属头衔的人、群备注包含
#ShirakanaBigCleanSetting.nameStandard的人）默认目标为2个月不发言的群员
/Tutu List：
#查看被清洗者名单
/Tutu Add <QQ>：
#将某人加入已被清洗名单
/Tutu Del <QQ>：
#将某人删除出已被清洗名单
/Tutu kick：
#在所有的群踢出“已被清洗”名单中的群员
/Tutu help：
#查看帮助
#注：使用小清洗指令踢出的群员会被加入“已被清洗”名单（相当于本群黑名单）
#此名单中用户无法加入任何启用该插件的群（可以找管理协商解决问题）
org.fujiwara.shirakana.adminbot.plugin:command.cleanrepeat
/CleanRepeat <GroupTarget>：若禁止重复加群名单中的用户已添加指定群聊（GroupTarget）
则将其从插件启用的其他群聊中踢出
数字恶臭化工具（彩蛋）
/To114514 <input>
#将input（Long）转换为一个114514序列组成的表达式
实现方法:我在B站的文章
第一次学着写mirai插件，还是用一个不太熟悉的语言（），希望社区的各位狠狠的批评，来促使我改进。
Mirai框架的许多内容还有我未了解的地方，许多类和函数的使用方法也不是太了解，所以插件可能写的不怎么好，希望各位能够指导我改进，以后可能会搞一大堆奇奇怪怪功能的插件。

Language : Kotlin等。
Tag: 群管，其他功能。
