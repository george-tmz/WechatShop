# WechatShop
#### 简介
这是一个小型的微信商城。这个项目的目的是练习使用自动化工具搭建项目，数据库迁移，模型生成，构建与发布。 
并学习商城的业务逻辑。
#### 需求分析
#### 项目搭建
使用[Spring Initializr](https://start.spring.io/)快速生成项目骨架。 版本：2.7.13，JDK8
初始选择的依赖：
- Spring Web
- MySQL Driver
- JDBC API
- Mybatis Framework
- Lombok
应用配置文件：application.yml

#### 数据库
##### 数据库搭建
通过docker创建MySql容器。创建wxshop数据库。
```shell
docker pull mysql
docker run --name mysql_dev -p 3306:3306 -e MYSQL_ROOT_PASSWORD=123456 -d mysql:latest
```
```mysql
create database wx_shop DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;
```
> mysql 在Linux环境下大小写敏感。使用全小写，用_分割

##### 数据库迁移 Flyway
- 添加依赖
- 编写数据迁移sql。文件名命名规则**版本号+双下划线+名称**，例如`V1__CreateUser.sql`。  
- 在pom中配置迁移文件路径，配置数据库链接
- 执行命令 `mvn flyway:migrate`

##### MyBatis Generator自动生成DAO层代码
- 添加依赖
- 配置生成文件位置，DO与数据表对应关系。
- 执行命令 `mvn mybatis-generator:generate`