package com.generator;


import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author JohanChan
 * @ProjectName Generator
 * @Description MySQL/PostGre 生成演示
 * @time 2021/7/7 16:49
 */
public class GeneratorCommon {

    public static void main(String[] args) {
        GeneratorCommon.generatorTable(new String[]{"user"});
    }

    private static String canonicalPath;

    public static void generatorTable(String[] tables) {
        // 获取项目绝对路径
        try {
            canonicalPath = new File("D:\\workplace\\mysSpringCloudAlibaba\\generatorModule").getCanonicalPath();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.println("canonicalPath = " + canonicalPath);
        String filePath = canonicalPath+"\\mybatisPlusGer\\src\\main\\";

        // 可选择Freemarker引擎，默认Veloctiy引擎 mpg.setTemplateEngine(new FreemarkerTemplateEngine());

        AutoGenerator mpg = new AutoGenerator();
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        // 作者名
        gc.setAuthor("liguang");
        // 实体属性Swagger2注解
        gc.setSwagger2(true);
        // 代码生成路径
        gc.setOutputDir(filePath + "java");
        // 是否覆盖同名文件，默认是false
        gc.setFileOverride(true);
        // 不需要ActiveRecord特性的请改为false
        gc.setActiveRecord(true);
        // XML 二级缓存
        gc.setEnableCache(false);
        // XML ResultMap
        gc.setBaseResultMap(true);
        // XML columList
        gc.setBaseColumnList(false);
        // 生成后打开文件夹
        gc.setOpen(false);
        /* 自定义文件命名，注意 %s 会自动填充表实体属性！ */
        gc.setControllerName("%sController");
        gc.setServiceName("%sService");
        gc.setServiceImplName("%sServiceImpl");
        gc.setMapperName("%sMapper");
        gc.setXmlName("%sMapper");
        mpg.setGlobalConfig(gc);

        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        // 指定数据库类型，DbType中有对应的枚举类型，pom文件中要添加对应的数据库引用
        // MySQL数据库
        dsc.setDbType(DbType.MYSQL);
        dsc.setTypeConvert(new MySqlTypeConvert() {
            // 自定义数据库表字段类型转换【可选】
            @Override
            public IColumnType processTypeConvert(GlobalConfig gc, String fieldType) {
                System.out.println("转换类型：" + fieldType);
                // 注意！！processTypeConvert 存在默认类型转换，如果不是你要的效果请自定义返回、非如下直接返回。
                //默认会把日期类型 转为LocalDateTime ，在查询的时候会报错，这里改为Date
                String t = fieldType.toLowerCase();
                if (t.contains("date") || t.contains("time") || t.contains("year")) {
                    return DbColumnType.DATE;
                } else {
                    return super.processTypeConvert(gc, fieldType);
                }
            }
        });
        //数据库连接配置
        // mysql8.0使用 --- 8.0之前使用：dsc.setDriverName("com.mysql.jdbc.Driver");
        dsc.setDriverName("com.mysql.cj.jdbc.Driver");
        dsc.setUsername("liguang");
        dsc.setPassword("16899199");
        dsc.setUrl("jdbc:mysql://119.91.136.140:3306/my_database?useUnicode=true&characterEncoding=utf8");

        mpg.setDataSource(dsc);

        // 策略配置
        StrategyConfig strategy = new StrategyConfig();

        // 全局大写命名 ORACLE注意
        // strategy.setCapitalMode(true);
        // 此处可以修改为您的表前缀
        // strategy.setTablePrefix("tb_");

        // 表名生成策略
        strategy.setNaming(NamingStrategy.underline_to_camel);
        // 采用驼峰映射
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        //【实体】是否为lombok模型 默认false
        strategy.setEntityLombokModel(true);
        // 需要生成的表.如果需要生成所有的, 注释掉此行就可以
        strategy.setInclude(tables == null || tables.length == 0 ? new String[]{} : tables);

        // 排除生成的表
        // strategy.setExclude(new String[]{"test"});
        // 自定义实体父类
        // strategy.setSuperEntityClass("com.baomidou.demo.TestEntity");
        // 自定义实体，公共字段
        // strategy.setSuperEntityColumns(new String[] { "test_id", "age" });
        // 自定义 mapper 父类
        // strategy.setSuperMapperClass("com.baomidou.demo.TestMapper");
        // 自定义 service 父类
        // strategy.setSuperServiceClass("com.baomidou.demo.TestService");
        // 自定义 service 实现类父类
        // strategy.setSuperServiceImplClass("com.baomidou.demo.TestServiceImpl");
        // 自定义 controller 父类
        // strategy.setSuperControllerClass("com.baomidou.demo.TestController");
        // 【实体】是否生成字段常量（默认 false）
        // public static final String ID = "test_id";
        // strategy.setEntityColumnConstant(true);
        // 【实体】是否为构建者模型（默认 false）
        // public User setName(String name) {this.name = name; return this;}
        // strategy.setEntityBuilderModel(true);

        mpg.setStrategy(strategy);

        // 包配置
        PackageConfig pc = new PackageConfig();
        // 自定义包路径
        pc.setParent("com.liguang.generator.manage.modules");
        // 这里是控制器包名，默认为web
        pc.setController("controller");
        // 设置Mapper包名，默认为mapper
        pc.setMapper("mapper");
        // 设置Service包名，默认为service
        pc.setService("service");
        // 设置Entity包名，默认为entity,继承的父类已序列化
        pc.setEntity("model.entity");
        // 设置Mapper XML包名，默认为mapper.xml
        pc.setXml("mapper.xml");
        mpg.setPackageInfo(pc);

        // 注入自定义配置，可以在 VM 中使用 cfg.abc 设置的值
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("abc", this.getConfig().getGlobalConfig().getAuthor() + "-mp");
                this.setMap(map);
            }
        };

        // 调整xml生成目录演示
        List<FileOutConfig> focList = new ArrayList<>();

        focList.add(new FileOutConfig("/templates/mapper.xml.vm") {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return filePath + "\\resources\\mapper\\xml\\" + tableInfo.getEntityName() + "Mapper.xml";
            }
        });
        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);

        // 执行生成
        mpg.execute();
    }

}
