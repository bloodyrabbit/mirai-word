package moe.ruabbit

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import moe.ruabbit.Dao.Question
import moe.ruabbit.config.QuestionConfig
import moe.ruabbit.register.WordRegister
import net.mamoe.mirai.console.permission.Permission
import net.mamoe.mirai.console.permission.PermissionId
import net.mamoe.mirai.console.permission.PermissionService
import net.mamoe.mirai.console.permission.PermissionService.Companion.hasPermission
import net.mamoe.mirai.console.permission.PermitteeId.Companion.permitteeId
import net.mamoe.mirai.console.plugin.jvm.JvmPluginDescription
import net.mamoe.mirai.console.plugin.jvm.KotlinPlugin
import net.mamoe.mirai.console.plugin.name
import net.mamoe.mirai.contact.User
import net.mamoe.mirai.utils.error
import net.mamoe.mirai.utils.info
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction
import java.io.File

object MiraiWord : KotlinPlugin(
    JvmPluginDescription(
        id = "moe.ruabbit.mirai.word",
        name = "mirai-word",
        version = "0.1.0",
    ) {
        author("ruabbit")
    }
) {
    private lateinit var adminPermission: Permission
    override fun onEnable() {
        logger.info { "DICE插件加载中" }

        // 初始化配置数据
        QuestionConfig.reload()

        // 初始化数据库
        if (QuestionConfig.Database == "sqlite") {
            logger.info { "使用sqlite数据库" }
            // 创建数据库的文件夹
            File("./data/moe.ruabbit.mirai.dice").mkdirs()
            Database.connect("jdbc:sqlite:data/moe.ruabbit.mirai.dice/data.db", "org.sqlite.JDBC")
        } else if (QuestionConfig.Database == "mysql") {
            logger.info { "使用mysql数据库" }
            // 配置mysql链接的数据
            val config = HikariConfig().apply {
                jdbcUrl = QuestionConfig.MYSQLConfig.jdbcUrl
                driverClassName = QuestionConfig.MYSQLConfig.driverClassName
                username = QuestionConfig.MYSQLConfig.username
                password = QuestionConfig.MYSQLConfig.password
                maximumPoolSize = QuestionConfig.MYSQLConfig.maximumPoolSize
            }
            val dataSource = HikariDataSource(config)
            Database.connect(dataSource)
        } else {
            logger.error { "数据库配置错误，无法识别的数据库" }
            throw Exception("数据库配置错误，无法识别的数据库")
        }
        transaction {
            SchemaUtils.create(Question)
        }

        // 注册权限
        adminPermission = PermissionService.INSTANCE.register(
            PermissionId(name, "admin"),
            "管理员权限"
        )

        // 问答库注册
        WordRegister()
    }

    /**
     * 权限检查，这是一个模仿我的世界的权限系统制作的
     */
    fun checkAdminPermission(sender: User): Boolean {
        return sender.permitteeId.hasPermission(adminPermission)
    }

}