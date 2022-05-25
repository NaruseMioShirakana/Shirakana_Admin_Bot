package org.fujiwara.shirakana.adminbot.command

import net.mamoe.mirai.console.command.CommandSender
import net.mamoe.mirai.console.command.SimpleCommand
import org.fujiwara.shirakana.adminbot.ShirakanaAdminBot

val str_num = arrayOf("((1-1)*4514)","(11/(45-1)*4)","(-11+4-5+14)","(11*-4+51-4)","(-11-4+5+14)","(11-4*5+14)","(1-14+5+14)","(11-4+5-1-4)","(11-4+5/1-4)","(11-4+5+1-4)","(-11/4+51/4)")

fun getDigit (Temp : Long) : Long {
    return if (Temp==0L) {
        0L
    }
    else 1 + getDigit(Temp / 10L);
}

fun to114514string (Input : Long) : String{

    var n = getDigit(Input)
    var tempStr = "("
    if(Input < 0){
        return ""
    }
    var numStr = Input.toString()
    for (i in n downTo 1L){
        if (i > 11) {
            tempStr += "(" + str_num[numStr[(n - i).toInt()] - '0'] + "*" + str_num[10] + "^" + to114514string(i - 1L) + ")";
            if (i != 1L) {
                tempStr += "+";
            }
        }
        else {
            tempStr += "(" + str_num[numStr[(n - i).toInt()] - '0'] + "*" + str_num[10] + "^" + str_num[(i - 1L).toInt()] + ")";
            if (i != 1L) {
                tempStr += "+";
            }
        }
    }
    tempStr += ")";
    return tempStr;
}

object To114514Str : SimpleCommand(
    ShirakanaAdminBot, "To114514",
    description = "将一串字符转换为114514序列"
) {
    @Handler
    suspend fun CommandSender.to114514Str(input : Long) {
        if (input<0){
            val msg = to114514string(-input)+" * -1"
            sendMessage("$input=$msg")
        }else{
            val msg = to114514string(input)
            sendMessage("$input=$msg")
        }
    }
}
