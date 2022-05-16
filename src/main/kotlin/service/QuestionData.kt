package moe.ruabbit.service

import moe.ruabbit.Dao.Question
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

/**
 * 添加新问题
 * @param que 问题
 * @param ans 答案
 */
fun addquestion(que: String, ans: String) {
    transaction {
        Question.insert {
            it[question] = que
            it[answer] = ans
        }
    }
}

/**
 * 删除问题通过答案
 * @param que 问题
 * @return num 删除的数量
 */
fun delquestion(que: String): Int {
    var num: Int = -1
    transaction {
        num = Question.deleteWhere { Question.question eq que }
    }
    return num
}

/**
 * 删除问题通过id
 * @param id 问题的序号
 * @return num 删除的数量 只有0和1的区分
 */
fun delidquestion(id: Int): Int {
    var num: Int = -1
    transaction {
        num = Question.deleteWhere { Question.id eq id }
    }
    return num
}

/**
 * 查询所有问题
 * @return list 返回查询的数据
 */
fun listquestion(): MutableList<ResultRow> {
    var list: MutableList<ResultRow> = mutableListOf()
    transaction {
        list = Question.selectAll().toMutableList()
    }
    return list
}

/**
 * 查询单个问题
 * @param que 问题
 * @return list 返回查询的数据，可能包含没有或者多个
 */
fun selquestion(que: String): MutableList<ResultRow> {
    var list: MutableList<ResultRow> = mutableListOf()
    transaction {
        list = Question.select { Question.question eq que }.toMutableList()
    }
    return list
}
