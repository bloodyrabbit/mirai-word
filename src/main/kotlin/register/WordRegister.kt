package moe.ruabbit.register

import moe.ruabbit.Dao.Question
import moe.ruabbit.MiraiWord
import moe.ruabbit.config.QuestionConfig
import moe.ruabbit.service.*
import net.mamoe.mirai.event.GlobalEventChannel
import net.mamoe.mirai.event.subscribeMessages
import net.mamoe.mirai.utils.info

/**
 * 问答库注册模块
 */
fun WordRegister() {
    MiraiWord.logger.info { "注册问答库模块" }
    GlobalEventChannel.subscribeMessages {
        // 添加指令
        startsWith(QuestionConfig.ADD_COMMAND) {
            if (!MiraiWord.checkAdminPermission(sender))
                return@startsWith

            val str = it.split(Regex("""\s+"""))

            if (str.size == 2){
                addquestion(str[0],str[1])
                subject.sendMessage("添加问答库\n问：${str[0]}\n答: ${str[1]}")
            }else{
                subject.sendMessage("格式错误，请检查输入内容\n例：${QuestionConfig.ADD_COMMAND} 衬衫的价格是 九磅十五便士")
            }
        }

        // 删除指令
        startsWith(QuestionConfig.DEL_COMMAND) {
            if (!MiraiWord.checkAdminPermission(sender))
                return@startsWith

            subject.sendMessage("成功删除${delquestion(it)}条问答")
        }

        // 删除指令，通过ID
        startsWith(QuestionConfig.DEL_ID_COMMAND) {
            if (!MiraiWord.checkAdminPermission(sender))
                return@startsWith

            if(delidquestion(it.toInt())==1)
                subject.sendMessage("成功删除编号${it}的问答")
            else
                subject.sendMessage("没有找到编号为${it}的问答")
        }

        // 遍历指令，输出所有的指令
        startsWith(QuestionConfig.List_COMMAND) {
            if (!MiraiWord.checkAdminPermission(sender))
                return@startsWith

            var string = ""
            var i = 0
            listquestion().forEach {
                string += "ID: " + it[Question.id] + " 问: " + it[Question.question] + " 答: " + it[Question.answer]+ "\n"
                i++
                // 20个为一组发出，避免字数过多被腾讯屏蔽
                if (i==20){
                    subject.sendMessage(string)
                    string = ""
                }
            }
            subject.sendMessage(string)
        }

        // 问答信息，匹配所有信息
        always {
            val list =  selquestion(it)
            if (list.size !=0)
                subject.sendMessage(list.random()[Question.answer])
        }
    }
}