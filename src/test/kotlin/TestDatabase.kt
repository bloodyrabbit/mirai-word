package moe.ruabbit

import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.TransactionManager
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File
import java.sql.Connection

object Question: Table(){

    private val id = integer("id").autoIncrement()
    val question = varchar("question",100)
    val answer = varchar("answer",100)

    override val primaryKey = PrimaryKey(id, name = "Question_ID")
}

fun main(args: Array<String>) {
    Database.connect("jdbc:sqlite:data.db", "org.sqlite.JDBC")
    // Database.connect("jdbc:sqlite:file:test?mode=memory&cache=shared", "org.sqlite.JDBC")



    TransactionManager.manager.defaultIsolationLevel = Connection.TRANSACTION_SERIALIZABLE

    transaction {

        SchemaUtils.create(Question)

        Question.insert {
            it[question] = "哈哈哈"
            it[answer] = "嘿嘿嘿"
        }

        Question.insert {
            it[question] = "好耶"
            it[answer] = "坏耶"
        }


    }

    list()

}

fun list(){
    transaction {
        println( Question.selectAll().toList().random())

    }
}