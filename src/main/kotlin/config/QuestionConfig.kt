package moe.ruabbit.config

import kotlinx.serialization.Serializable
import net.mamoe.mirai.console.data.ReadOnlyPluginConfig
import net.mamoe.mirai.console.data.ValueDescription
import net.mamoe.mirai.console.data.value

/**
 * 命令的设置文件，第一次运行会自动生成配置文件,并根据默认值生成默认配置，以后运行会读取文件中的配置
 * @return
 */
object QuestionConfig : ReadOnlyPluginConfig("Question") {
    @ValueDescription("配置命令")
    val ADD_COMMAND: String by value("-add")
    val DEL_ID_COMMAND: String by value("-delid")
    val DEL_COMMAND: String by value("-del")
    val List_COMMAND: String by value("-list")

    @ValueDescription(
        """
        数据库配置
        可选sqlite和mysql
        mysql需要配置数据库配置，sqlite无需配置
    """
    )
    val Database: String by value("sqlite")
    val MYSQLConfig:MysqlConfig by value(MysqlConfig())

    @Serializable
    data class MysqlConfig(
        val jdbcUrl: String = "jdbc:mysql://localhost:3306/dbname",
        val driverClassName: String = "com.mysql.cj.jdbc.Driver",
        val username: String = "MagicRabbit",
        val password: String = "123456",
        val maximumPoolSize: Int = 10
    )
}