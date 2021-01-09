package com.xh.db.doc;

import cn.smallbun.screw.core.Configuration;
import cn.smallbun.screw.core.engine.EngineConfig;
import cn.smallbun.screw.core.engine.EngineFileType;
import cn.smallbun.screw.core.engine.EngineTemplateType;
import cn.smallbun.screw.core.execute.DocumentationExecute;
import cn.smallbun.screw.core.process.ProcessConfig;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Title:
 * Description:
 *
 * @author H.Yang
 * @date 2020/12/30
 */
public class ScrewMain {

    public static void main(String[] args) {
// 创建 screw 的配置
        Configuration config = Configuration.builder()
                .version("1.1")  // 版本
                .description("res-文档描述") // 描述
                .dataSource(buildDataSource()) // 数据源
                .engineConfig(buildEngineConfig()) // 引擎配置
                .produceConfig(buildProcessConfig()) // 处理配置
                .build();

        // 执行 screw，生成数据库文档
        new DocumentationExecute(config).execute();
    }

    /**
     * 生成文件配置
     */
    private static EngineConfig buildEngineConfig() {
        return EngineConfig.builder()
                .fileOutputDir("D:/core") // 生成文件路径
                .openOutputDir(false) // 打开目录
                .fileType(EngineFileType.HTML) // 文件类型
//                .fileType(EngineFileType.WORD) // 文件类型
                .produceType(EngineTemplateType.freemarker) // 生成模板实现、文件类型
                .fileName("res-数据库文档") // 自定义文件名称
                .build();
    }

    /**
     * 创建数据源
     */
    private static DataSource buildDataSource() {
        // 创建 HikariConfig 配置类
        HikariConfig hikariConfig = new HikariConfig();
        hikariConfig.setDriverClassName("com.mysql.jdbc.Driver");
//        hikariConfig.setJdbcUrl("jdbc:mysql://127.0.0.1:3306/test?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&autoReconnect=true&serverTimezone=Asia/Shanghai");
//        hikariConfig.setUsername("root");
//        hikariConfig.setPassword("admini");
        hikariConfig.setJdbcUrl("jdbc:mysql://192.168.201.4:3306/res?characterEncoding=utf8&zeroDateTimeBehavior=convertToNull&autoReconnect=true&serverTimezone=Asia/Shanghai");
        hikariConfig.setUsername("usercenter");
        hikariConfig.setPassword("1332@ANCH.com");
//        hikariConfig.addDataSourceProperty("useInformationSchema", "true"); // 设置可以获取 tables remarks 信息
        // 创建数据源
        return new HikariDataSource(hikariConfig);
    }


//    /**
//     * 配置想要生成的表+ 配置想要忽略的表
//     * @return 生成表配置
//     */
//    public static ProcessConfig buildProcessConfig(){
////        // 忽略表名
////        List<String> ignoreTableName = Arrays.asList("aa","test_group");
////        // 忽略表前缀，如忽略a开头的数据库表
////        List<String> ignorePrefix = Arrays.asList("a","t");
////        // 忽略表后缀
////        List<String> ignoreSuffix = Arrays.asList("_test","czb_");
//
//        return ProcessConfig.builder()
//                //根据名称指定表生成
//                .designatedTableName(new ArrayList<>())
//                //根据表前缀生成
//                .designatedTablePrefix(new ArrayList<>())
//                //根据表后缀生成
//                .designatedTableSuffix(new ArrayList<>())
//                //忽略表名
//                .ignoreTableName(ignoreTableName)
//                //忽略表前缀
//                .ignoreTablePrefix(ignorePrefix)
//                //忽略表后缀
//                .ignoreTableSuffix(ignoreSuffix).build();
//    }

    /**
     * 创建 screw 的处理配置，一般可忽略
     * 指定生成逻辑、当存在指定表、指定表前缀、指定表后缀时，将生成指定表，其余表不生成、并跳过忽略表配置
     */
    private static ProcessConfig buildProcessConfig() {
        return ProcessConfig.builder()
                .designatedTableName(Collections.<String>emptyList())  // 根据名称指定表生成
                .designatedTablePrefix(Collections.<String>emptyList()) //根据表前缀生成
                .designatedTableSuffix(Collections.<String>emptyList()) // 根据表后缀生成
//                .ignoreTableName(Arrays.asList("test_user", "test_group")) // 忽略表名
//                .ignoreTablePrefix(Collections.singletonList("test_")) // 忽略表前缀
//                .ignoreTableSuffix(Collections.singletonList("_test")) // 忽略表后缀
                .build();
    }


}
