package io.github.davidchild.bitter.test.generator;

import com.baomidou.mybatisplus.core.exceptions.MybatisPlusException;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.generator.AutoGenerator;
import com.baomidou.mybatisplus.generator.InjectionConfig;
import com.baomidou.mybatisplus.generator.config.*;
import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import com.baomidou.mybatisplus.generator.config.rules.DateType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

// auto create code utilTools for bitter or mybatis
// MYBATIS 生的实体, 只要继承 Bitter 框架中的 BaseModel,数据库的实体就可以使用bitter 框架了.
// 下面的 GeneratorCode 生成器完全支持BITTER 框架以及 MYBATIS
// 使用bitter 完全可以 忽略 MAPPER 以及 SERVICE 以及 DAO  分层, 但是,为了规范,还是生成下 .

public class CodeGenerator {
    // 设置代码生成的路径
    private static String createToPath = "/bitter-samples/src/main/java/"; //根据自己的项目实际情况
    private static String mapperPath = "/bitter-samples/src/main/resources/mapper/";
    private static String rootPackageName = "io.github.davidchild.bitter.samples";

    private static String subBusPackage = "business";

    //Generator 数据源配置
    public static String jdbcUrl = "jdbc:mysql://localhost:3306/bitter?useUnicode=true&useSSL=false&characterEncoding=utf8&servertimezone=UTC";
    public static String jdbcDrive = "com.mysql.cj.jdbc.Driver";
    public static String jdbcUser = "root";
    public static String jdbcPwd = "123456";
    //设置作者
    private static String auth = "davidChild";

    public static void main(String[] args) {
        // 代码生成器
        AutoGenerator mpg = new AutoGenerator();

        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        String projectPath = System.getProperty("user.dir");
        gc.setOutputDir(projectPath + createToPath);
        gc.setAuthor(auth);
        gc.setOpen(false);
        gc.setSwagger2(true);
        gc.setFileOverride(false);
        gc.setDateType(DateType.ONLY_DATE);
        //gc.setIdType(IdType.ASSIGN_ID);//分配ID (主键类型为number或string）
        mpg.setGlobalConfig(gc);
        // 数据源配置
        DataSourceConfig dsc = new DataSourceConfig();
        //TODO 手动修改数据库配置信息
        dsc.setUrl(jdbcUrl);
        dsc.setDriverName(jdbcDrive);
        dsc.setUsername(jdbcUser);
        dsc.setPassword(jdbcPwd);
        mpg.setDataSource(dsc);

        // package setting
        PackageConfig pc = new PackageConfig();
        //pc.setModuleName(scanner("business package name"));
        pc.setModuleName(subBusPackage);
        pc.setParent(rootPackageName);
        mpg.setPackageInfo(pc);

        // custom setting
        InjectionConfig cfg = new InjectionConfig() {
            @Override
            public void initMap() {
                // to do nothing
            }
        };

        // 如果模板引擎是 freemarker
        String templatePath = "/templates/mapper.xml.ftl";
        List<FileOutConfig> focList = new ArrayList<>();

        focList.add(new FileOutConfig(templatePath) {
            @Override
            public String outputFile(TableInfo tableInfo) {
                return projectPath + mapperPath + pc.getModuleName()
                        + "/" + tableInfo.getEntityName() + "Mapper" + StringPool.DOT_XML;
            }
        });

        cfg.setFileOutConfigList(focList);
        mpg.setCfg(cfg);
        TemplateConfig templateConfig = new TemplateConfig();
        templateConfig.setXml(null);
        mpg.setTemplate(templateConfig);

        StrategyConfig strategy = new StrategyConfig();
        strategy.setNaming(NamingStrategy.underline_to_camel);
        strategy.setColumnNaming(NamingStrategy.underline_to_camel);
        strategy.setSuperEntityClass("BaseModel");
        strategy.setEntityLombokModel(true);
        strategy.setRestControllerStyle(true);
        strategy.setInclude(scanner("table name" + "").split(","));
        strategy.setControllerMappingHyphenStyle(true);
        strategy.setEntityTableFieldAnnotationEnable(true);
        strategy.setTablePrefix(pc.getModuleName() + "_");
        mpg.setStrategy(strategy);
        mpg.setTemplateEngine(new FreemarkerTemplateEngine());
        mpg.execute();
    }

    public static String scanner(String tip) {
        Scanner scanner = new Scanner(System.in);
        StringBuilder help = new StringBuilder();
        help.append("pls enter " + tip + "：");
        System.out.println(help.toString());
        if (scanner.hasNext()) {
            String ipt = scanner.next();
            if (StringUtils.isNotBlank(ipt)) {
                return ipt;
            }
        }
        throw new MybatisPlusException("pls enter the real table name" + tip + "！");
    }
}

