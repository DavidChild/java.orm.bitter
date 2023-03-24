> bitter 框架 站在开发者的角度设计的一款高性能，符合开发习惯的快速业务迭代ORM-MAPPING 框架。bitter 旨在 在写持久化应用时，变得更灵活，书写的更加顺畅,性能更加友好. bitter 旨在 拥抱 coding in
> coding 的快乐中。



**项目集成集成 bitter**

------

1: 在项目POM 引入 Bitter:

```java
<dependency>
<groupId>io.github.davidchild</groupId>
<artifactId>bitter</artifactId>
<version>1.3.9-RELEASE</version>
</dependency>
```

2: yml 配置：

```yaml
bitter:
  sqlLog: true # 是否配置输出最终执行的SQL语句，默认 为 false
  cache:
    enabledRedis: true  # 是否启用redis缓存, 如有开启缓存机制， 远程和本地选其一，默认两者都关闭 
    enabledLocalCache: false # 是否启用 本地缓存,本地缓存组件基于caffeine实现, 如有开启缓存机制， 远程和本地选其一，默认两者都关闭 
    redis: # 如果启用redis 缓存机制,配置redis 单机或者集群的连接信息,以及基础配置
      hosts:
        - ip: localhost
          port: 6379
        - ip: 127.0.0.1
          port: 6379
      database: 1
      password:
      timeout: 100
      lettuce:
        pool:
          minIdle: 0
          maxIdle: 10
          maxActive: 30
          maxTotal: 30
          maxWait: 200
```

**0 成本 集成 bitter**

------

1. 完全兼容 mybatis,不影响 mybatis 任何写法
2. 完全兼容 mybatis-plus,不影响 mybatis-plus 任何写法
3. 兼容并且使用mybatis 生成的实体直接在 bitter.orm 框架上使用
4. 跟当前的 的 spring 项目无缝集成，并且无需做任何修改
5. bitter 没有自己的数据源配置，bitter 的数据源： 可以将 任何 spring 的 实现 抽象对象 `AbstractRoutingDataSource`注入到bitter 中,来作为 bitter 的 datasource

**bitter 的优势**

------

1. bitter 代码非常简单，而且轻量,轻量； bitter 的设计原则 轻量,灵活，随处可用。

2. bitter 的业务代码非常清晰，便于快速的业务迭代

3. bitter 不需要 mapper, 不需要 xml，不需要 约定分层。 分层理论上属于应用层规范的事情。

4. bitter 使得 query by sql 变得更加容易，便捷，没有繁琐的mapper,分层，约定

5. bitter 基于对象以及对象实例驱动的设计原则

6. bitter 具有更加灵活的数据mpping,没有特殊情况，不需要 编写类型映射规则，大部分数据类型，bitter 将自动转换

7. bitter 没有繁琐的 分页插件

8. bitter 注重链式表达编写

**Case Code**

------

- 根据实体查询

   ```java
   Date s = DateUtils.parseDate("2022-8-10");
   List<TUserInfo> list_0 = db.findQuery(TUserInfo.class).thenAsc(TUserInfo::getId).find();
   
   select fileds，select sigle page by top size
   List<TUserInfo> list_2 =db.findQuery(TUserInfo.class).select(TUserInfo::getId,TUserInfo::getUsername, TUserInfo::getAvatar).setSize(10).thenAsc(TUserInfo::getId).find();
   // where sample
   List<TUserInfo> list_4 = db.findQuery(TUserInfo.class).where(t-> t.getCreateTime().after(s)).thenAsc(TUserInfo::getId).find();
   // where and where,order by
   List<TUserInfo> list_5 = db.findQuery(TUserInfo.class).
            where(t-> t.getCreateTime().after(s)).where(t.getUsername().contains("123"))
            .thenAsc(TUserInfo::getId).find();
   ```

- 根据ID 查询具体的数据

  ```Java
  TUserInfo sysuser =  db.findQuery(TUserInfo.class,"1552178014981849090").find(); // 不管数据库有没有具体的 "1552178014981849090" 数据，bitter 都会返回一个对象实例，数据库没有的话
  if(sysuser.haveKeyValue())// 数据库没有的话，可以通过 此 haveKeyValue 方法判断主键 是否存在有值
  {
      String t = "";
  }else{
  
  }
  ```

- 根据sql 查询

  ```java
  List<Map<String,Object>> mapOrgin = db.findQuery("select * from sys_user").find();
  //exuctequery
  List<TUserInfo>  lt = db.findQuery("select * from sys_user").find().toBList(TUserInfo.class);
  //exuctequery and mapUnderscoreToCamelCase
  List<TUserInfo>  list = db.findQuery("select * from sys_user").find().toBList(TUserInfo.class,true);
  ExcuteQuery query = db.findQuery("select * from sys_user");
  query.beginWhere(" 1=1 ");
  query.andWhere("id>'123456789'");
  query.andWhere("id>'1552205343773896705'");
  List<TUserInfo>  ls = query.find().toBList(TUserInfo.class);
  // 获取第一行第一列的数据,并且转到指定类型,并且给出默认值
  Integer count = db.findQuery("select count(0) from sys_user").find().tryCase(0);
  // 获取第一行第一列指定的列的字段值
  String  id = db.findQuery("select * from sys_user limit 0,1").find().getFirstRowSomeData("id").toString();
  ```

- sql 分页查询

  ```java
  String sql =    "select \n" +
                  "us.username,\n" +
                  "us.id as user_id,\n" +
                  "dept.dept_name as dept_name\n" +
                  " from \n" +
                  "t_user us \n" +
                  "left join t_dept dept on dept.dept_id= us.dept_id";
  
  PageQuery page = new PageQuery(sql);
  page.where("IFNULL(us.username,'') = ?", "123");
  page.thenASC("us.username");
  page.thenDESC("us.create_time");
  page.skip(1).take(10);
  List<Map<String,Object>> mapList = page.getData();
  Integer count = page.getCount();
  Integer count2 = page.getCount();
  int l = db.findQuery(TUserInfo.class).FindCount();
  ```

    - 删除操作

  ```java
  List<TUserInfo>  list = db.findQuery("select * from sys_user").find().toBList(TUserInfo.class,true);
  // 直接对实体进行删除
  TUserInfo user =   list.get(list.size()-1);
  long count_1 = user.delete().submit();
  String id = list.get(list.size()-2).id;
  // 构建删除
  long count_2 =  db.delete(TUserInfo.class).where(t->t.getId() == id).submit();
  ```

- 更新操作

  ```java
  TUserInfo sysuser =  db.findQuery(TUserInfo.class,"1552178014981849090").find();
  sysuser.setUsername("MyUser");
  sysuser.update().submit();更新操作
  ```

- 插入操作

  ```Java
  Date s = DateUtils.parseDate("2022-8-10");
  List<TUserInfo> list = db.findQuery(TUserInfo.class).thenAsc(TUserInfo::getId).find();
  TUserInfo user = list.get(0);
  user.setId("12345680");
  user.setUsername("hjb12344");
  user.setCreateTime(new Date());
  user.insert().submit();
  String test = "";
  
  TStudentInfo studentInfo = new TStudentInfo();
  studentInfo.setName("davidchild");
  // studentInfo.setSex("男");
  long id = studentInfo.insert().submit();
  List<TUserInfo>  lt = db.findQuery("select * from sys_user").find().toBList(TUserInfo.class);
  ```

- 批量插入操作

  ```java
  db.bachInsert().doBachInsert((list)->{
      for(int i = 0; i<10;i++){
          TStudentInfo studentInfo = new TStudentInfo();
          studentInfo.setName("hjb"+i);
          studentInfo.insert().addInBachInsertPool((List<Insert>)list);
      }
  }).submit();
  ```

- 事务

  ```java
    DbScope dbScope =  db.doScope();
         
  boolean isSuccess =  dbScope.create((list)->{
  TStudentInfo sys =  db.findQuery(TStudentInfo.class).where(t->t.getName().equals("hjb5")).find().fistOrDefault();
              sys.setName("jjbbbbb");
              sys.update().addInScope(list);  // 将更新语句塞入事务收集器中
              TStudentInfo studentInfo = new TStudentInfo();
              studentInfo.setName("hello hjb");
              studentInfo.insert().addInScope(list); // 将插入语句塞入事务收集器中
              TStudentInfo sys_2 =  db.findQuery(TStudentInfo.class).where(t->t.getName().equals("hjb4")).find().fistOrDefault();
              sys_2.delete().addInScope(list);// 将删除语句塞入收集器中
  
           }).submit()>-1l; // 最终提交事务,如果成功，则返回>=0, 事务如果执行失败，则返回-1,异常原因由日志输出
           String kkk = "";
  ```

!多数据源 可查看 [**
changing-over-demo**](https://github.com/DavidChild/java.orm.bitter/blob/main/src/test/java/com/bitter/op/TestBitter.java#L209)

**性能**

------ 

##### bitter 的性能 要比   mybatis  强： 例如查询 100个字段的表

1. ###### 批量插入 每次执行 100 条事务性插入，持续执行100次的结果，构建100个实体，批量插入 bitter 每次 执行的时间消耗 平均 70ms.

<img src="https://user-images.githubusercontent.com/18735238/210936047-b2ac036b-5d8a-4e45-90a1-d5eef304566c.png" alt="image-20230106130949736" style="zoom:50%;" />
      而 mybatis  执行的性能消耗 为 平均为 100ms;

2. ###### 查询到VO映射性能： 同样的查询 10 条，连续执行 100 次查询： bitter 总共消耗的时间为：1500ms

![image-20230106131856877](https://user-images.githubusercontent.com/18735238/210936189-7b01c4fc-ee69-4fa9-8843-4bb9dce38ac0.png)

而 mybatis 消耗的时间 总共为 1669 ms：

![image-20230106131945288](https://user-images.githubusercontent.com/18735238/210936209-da388b2d-0bcf-49bf-9a7c-a5c474a8038a.png)

3. ###### 查询到VO映射性能： 同样的查询 1000 条，连续执行 100 次查询： bitter 总共消耗的时间为：38954ms

![image-20230106132118317](https://user-images.githubusercontent.com/18735238/210936249-39b498c2-e96d-406b-91bf-60bee01eaa50.png)

###### 而 mybatis 消耗的时间 总共为 39236 ms：

![image-20230106132152933](https://user-images.githubusercontent.com/18735238/210936273-b777ac6e-ca03-4c69-baa9-ddb6632d030a.png)
