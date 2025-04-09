# springboot脚手架工程
如果你是springboot初学者或是在校学生，此工程能帮你较为系统性的了解企业级springboot应用。<br>

## 一、技术栈

- **Spring Boot 3.4.4**：用于构建 Web 应用，快速集成各种功能。
- **Java 21**：项目使用 Java 21 版本进行开发。
- **MySQL**：项目默认使用 MySQL 数据库，集成了 MySQL JDBC 驱动和 Druid 数据库连接池。
- **MyBatis-Plus**：增强版 MyBatis，简化数据库操作，自动生成 CRUD 方法。
- **OpenFeign**：声明式 HTTP 客户端，简化微服务间调用。
- **Knife4j**：接口文档生成工具，增强 Swagger 体验。
- **Lombok**：消除样板代码，自动生成 getter、setter、toString 等方法。
- **MapStruct**：高效的 Java Bean 映射工具，减少手动转换代码。

## 二、项目功能
- **demo功能**：提供了一个简单的用户信息查询api。
- **全局统一返回**：提供了一个封装性的全局api返回，企业级必备。
- **日志切面**：利用aop书写的一个记录请求返回日志的切面，企业级必备。
- **全局异常处理**：全局异常捕获处理流程，企业级必备。
- **统一日志打印配置**：提供了一套logback日志打印格式（logback-spring.xml），企业级必备。

## 三、代码结构
不同于简单的springboot工程，一般企业级的应用代码层级都较多。因为企业级应用普遍代码繁多，开发者也较多，如果不规定好层级，往往对开发维护者都是一种较大的挑战。eg:项目的所有工具类都应该放在utils包下，而不是放在service包下，那么大家开发的时候需要工具类的时候去utils包下维护即可。<br>
需要注意的是代码结构因人，公司而异，以下是本人比较整理出觉得较为合适简约的结构：
```
├── api
│   ├── request
│   └── response
├── common
├── config
├── convert
├── feign
│   ├── request
│   └── response
├── job
├── mapper
├── model
├── service
│   ├── impl
├── utils
```
### 1. `api/`

该目录用于存放与外部交互的接口，同时有请求和响应两个子目录：

- **request**：包含发起的请求参数的结构定义，通常为请求体数据模型。
- **response**：包含从服务或接口返回的数据结构定义，通常为响应数据模型。

### 2. `common/`

该目录包含一些公共模块或工具，通常是项目中的通用组件或基础功能，例如：

- **常量定义**：如 HTTP 状态码、消息代码等。
- **自定义异常处理**：项目中可能会用到的基础异常类及其处理。
- **基础类**：例如基础的返回对象、分页类等。

### 3. `config/`

此目录用于存放项目的配置信息：

- **配置类**：比如 Spring 配置类、数据库连接配置、第三方服务配置等。
- **环境配置**：例如 `application.yml` 或 `application.properties` 等环境配置文件的加载类。

### 4. `convert/`

该目录存放用于数据转换的工具或类，通常用于将不同的数据结构进行相互转换：

- **对象转换**：如将 DTO（数据传输对象）与实体类（Entity）之间的转换。
- **类型转换**：如基本数据类型与包装类之间的转换。

### 5. `feign/`

该目录包含与 Feign 客户端相关的代码，Feigin 用于简化服务之间的远程调用：

- **request**：包含 Feign 请求体的结构定义，通常是外部服务接口请求所需的数据模型。
- **response**：包含 Feign 调用返回的数据模型。

### 6. `job/`

该目录存放与定时任务或后台任务相关的代码。通常是一些需要周期性执行的任务，类似于：

- **定时任务类**：如使用 `@Scheduled` 注解配置的任务。
- **任务调度器**：用于调度、管理定时任务的服务。

### 7. `mapper/`

该目录用于存放 MyBatis 映射文件和 Mapper 接口：

- **Mapper 接口**：用于定义与数据库的交互方法。
- **XML 映射文件**：如果使用 XML 配置的方式，这里存放与 SQL 相关的 XML 文件。

### 8. `model/`

该目录存放与数据模型相关的类，例如：

- **实体类**：项目的核心数据模型类，用于数据库映射。
- **DTO（数据传输对象）**：用于数据交换和传输的对象，通常用于接口请求和响应。

### 9. `service/`

该目录用于存放业务逻辑层的代码，包含各类业务处理类和接口：

- **impl**：该子目录用于存放具体的业务逻辑实现类，每个接口都有对应的实现类。

  例如：
    - `UserServiceImpl`：实现 `UserService` 接口的具体业务逻辑类。

### 10. `utils/`

该目录用于存放项目中常用的工具类：

- **字符串工具类**：如 `StringUtils`。
- **日期工具类**：如 `DateUtils`。
- **加密工具类**：如 `EncryptionUtils`。
- **文件处理工具类**：如 `FileUtils`。



## 四、项目依赖
### 1. Web 功能

- **spring-boot-starter-web**：Spring Boot Web 核心依赖。
- **spring-boot-starter-aop**：提供 AOP 支持，用于日志、事务等功能。
- **spring-cloud-starter-openfeign**：集成 Feign 用于微服务间的远程调用。

### 2. 数据库相关

- **mysql-connector-j**：MySQL 数据库 JDBC 驱动。
- **spring-boot-starter-jdbc**：Spring JDBC 支持，提供 DataSource、事务等功能。
- **druid-spring-boot-starter**：高效数据库连接池 Druid。
- **mybatis-plus-boot-starter**：增强版 MyBatis，简化数据库操作。

### 3. 工具类与开发效率

- **commons-lang3**：Apache 工具库，包含 StringUtils、ObjectUtils 等常用工具类。
- **commons-collections4**：Apache 提供的扩展集合类工具包。
- **commons-io**：文件和流的工具类。
- **gson**：Google 提供的 JSON 序列化和反序列化工具。
- **hutool-all**：一个综合性的 Java 工具类库，提供日期、文件、加密等功能。
- **lombok**：简化 Java 代码，减少样板代码。

## 五、如何使用
### 1、克隆项目到本地
### 2、配置数据库连接
打开项目中的配置文件 src/main/resources/application-local.yml，并配置您的数据库连接信息：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/your_database_name
    username: your_username
    password: your_password
    driver-class-name: com.mysql.cj.jdbc.Driver
```
### 3、在自己的数据库中建表
```sql
CREATE TABLE `user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(255) DEFAULT NULL,
  `age` int DEFAULT NULL,
);
insert into users(name,age) VALUES ('吴邪',28);
```
### 4、IDEA之类工具启动即可
