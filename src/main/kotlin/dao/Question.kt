package moe.ruabbit.Dao

import org.jetbrains.exposed.sql.Table

/**
 * 关于问答库的数据库object
 */
object Question: Table() {
    val id = integer("id").autoIncrement()
    val question = varchar("question",100)
    val answer = varchar("answer",100)

    override val primaryKey = PrimaryKey(id, name = "Question_ID")
}