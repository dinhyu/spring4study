# Spring4概述

1. 轻量级开源的J2EE应用程序框架
2. 解决企业应用的复杂性
3. 两大核心部分:IOC和Aop
   * IOC：控制反转，把创建对象过程交给 Spring 进行管理
   * Aop：面向切面，不修改源代码进行功能增强 
4. Spring 特点 
   1. 方便解耦，简化开发
   2. Aop 编程支持
   3. 方便程序测试
   4. 方便和其他框架进行整合
   5. 方便进行事务操作
   6. 降低 API 开发难度

# 入门案例

## 导包

* ```java
  核心容器
  spring-beans-4.0.0.RELEASE.jar
  spring-context-4.0.0.RELEASE.jar
  spring-core-4.0.0.RELEASE.jar
  spring-expression-4.0.0.RELEASE.jar
  commons-logging-1.1.3.jar
  Spring运行的时候依赖一个日志包；没有就报错；
  ```

## 准备类

* ```java
  package pers.spring4.n1_helloworld;
  
  /**
   * @author dinhyu
   * @version v1.0
   * date 2021-07-18-10:23
   */
  public class Student {
      private Integer studentId;
      private String stuName;
      private int age;
  
      public Student() {
      }
  
      public Student(Integer studentId, String stuName, int age) {
          this.studentId = studentId;
          this.stuName = stuName;
          this.age = age;
      }
      
      public Student(Integer studentId, int age, String stuName) {
          this.studentId = studentId;
          this.stuName = stuName;
          this.age = age;
      }
  
      public Integer getStudentId() {
          return studentId;
      }
  
      public void setStudentId(Integer studentId) {
          this.studentId = studentId;
      }
  
      public String getStuName() {
          return stuName;
      }
  
      public void setStuName(String stuName) {
          this.stuName = stuName;
      }
  
      public int getAge() {
          return age;
      }
  
      public void setAge(int age) {
          this.age = age;
      }
  
      @Override
      public String toString() {
          return "Student{" +
                  "studentId=" + studentId +
                  ", stuName='" + stuName + '\'' +
                  ", age=" + age +
                  '}';
      }
  }
  ```

## 创建Spring配置文件

1. 在配置文件中配置创建的对象

   * ```xml
     <?xml version="1.0" encoding="UTF-8"?>
     <beans xmlns="http://www.springframework.org/schema/beans"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
         <bean id="student" class="pers.spring4.n1_helloworld.Student">
             <property name="studentId" value="1"/>
             <property name="stuName" value="vivy"/>
             <property name="age" value="100"/>
         </bean>
     </beans>
     ```

## 使用Spring5创建对象

* ```java
  public class Spring4Test {
      @Test
      public void testStudentBean(){
          //加载spring配置文件
          ApplicationContext applicationContext = new ClassPathXmlApplicationContext("springconfig_n1.xml");
          //获取配置创建的对象
          Student student = applicationContext.getBean("student", Student.class);
          System.out.println(student);
          assertNotNull(student);
      }
  }
  ```

# IOC容器

## IOC概念及底层原理

### 1.什么是IOC

1. 控制反转:把对象的创建和对象之间的调用过程,交给Spring进行管理
2. 使用目的:为了耦合度降低
3. 入门案例就是IOC的实现

## IOC接口

1. IOC思想基于IOC容器完成,IOC容器底层就是对象工厂
2. Spring提供IOC容器实现两种方式(2个接口)
   1. BeanFactory
      1. IOC容器基本实现,是Spring内部的使用接口,不提供开发人员进行使用
      2. 加载配置文件时不会创建对象,在使用对象时才去创建对象(懒汉式)
   2. ApplicationContext
      1. BeanFactory接口的子接口,提供更多更强大的功能,一般由开发人员使用
      2. 加载配置文件时就就创建了对象(饿汉式)
   3. ApplicationContext实现类
        1. FileSystemXmlApplicationContext
           1. 带盘符的路径(绝对路径)
        2. ClassPathXmlApplicationContext
           1. 可简单理解为相对src的路径(相对路径)
           2. 实际上是工程部署后的类路径
              1. 类路径
                 1. 源码包开始的路径，称为类路径的开始
                 2. idea普通工程中的src文件夹
                 3. maven工程中的resources文件夹
        3. ConfigurableApplicationContext
           1. 是ApplicationContext的子接口，包含一些扩展方法
           2. refresh()和close()让ApplicationContext具有启动、关闭和刷新上下文的能力
        4. WebApplicationContext
           1. 专门为WEB应用而准备的，它允许从相对于WEB根目录的路径中完成初始化工作

## 获取bean

1. 通过id值获取

   * ```java
     public class Spring4Test {
         @Test
         public void testStudentBean(){
             //加载spring配置文件
             ApplicationContext applicationContext = new ClassPathXmlApplicationContext("springconfig_n1.xml");
             //获取配置创建的对象
             Student student = (Student)applicationContext.getBean("student");
             System.out.println(student);
             assertNotNull(student);
         }
     }
     ```

2. 通过bean的类型获取

   1. 如果在xml文件中配置了多个同类型的bean,则获取时抛出异常

   2. 使用该方法获取bean时要求该类型bean在容器中必须是唯一的

   3. ```java
      public class Spring4Test {
          @Test
          public void testStudentBean(){
              //加载spring配置文件
              ApplicationContext applicationContext = new ClassPathXmlApplicationContext("springconfig_n1.xml");
              //获取配置创建的对象
              Student student = applicationContext.getBean(Student.class);
              System.out.println(student);
              assertNotNull(student);
          }
      }
      ```

3. bean的创建细节

   1. 容器中对象的创建在容器创建完成的时候就已经创建好了
   2. 同一个组件在ioc容器中是单实例的；容器启动完成都已经创建准备好的
   3. 容器中如果没有这个组件，获取组件？报异常
   4. ioc容器在创建这个组件对象的时候，(property)会利用setter方法为javaBean的属性进行赋值
   5. javaBean的属性名是由什么决定的？getter/setter方法是属性名;set去掉后面那一串首字母小写就是属性名;因此应当所有getter/setter都自动生成

## IOC操作,Bean管理(基于xml配置文件)

### 1.什么是Bean管理

1. Bean 管理指的是两个操作
   1. Spring 创建对象
   2. Spirng 注入属性
2. Bean 管理操作有两种方式
   1. 基于xml配置文件方式
   2. 基于注解方式方式

### 2.基于xml配置文件方式

1. 创建对象

   * ```xml
        * ```xml
          <?xml version="1.0" encoding="UTF-8"?>
          <beans xmlns="http://www.springframework.org/schema/beans"
                 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                 xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
              <!-- 配置bean -->
              <bean id="student" class="pers.spring4.n1_helloworld.Student">
                  <property name="studentId" value="1"/>
                  <property name="stuName" value="vivy"/>
                  <property name="age" value="100"/>
              </bean>
          </beans>
        ```
     ```
     
   * 属性介绍

     * id属性:唯一标识
     * class属性:类全路径(包类路径)

   * 创建对象时默认执行无参构造

2. 注入属性

   1. DI:依赖注入,就是注入属性

      1. 第一种注入方式:使用set方法进行注入

         * ```xml
           <?xml version="1.0" encoding="UTF-8"?>
           <beans xmlns="http://www.springframework.org/schema/beans"
                  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                  xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
               <!--2 set方法注入属性-->
               <bean id="book" class="pers.spring5.Book">
                   <!--使用property完成属性注入
                       name:类中的属性名
                       value:注入的属性值
                   -->
                   <property name="bname" value="时间简史"></property>
                   <property name="bauthor" value="霍金"></property>
               </bean>
           </beans>
           ```

      2. 第二种注入方式:使用有参构造器注入

         1. 默认按构造器形参顺序赋值

            * ```xml
              <?xml version="1.0" encoding="UTF-8"?>
              <beans xmlns="http://www.springframework.org/schema/beans"
                     xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                     xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
                  <!--通过bean的构造器赋值-->
                  <bean id="student" class="pers.spring4.n1_helloworld.Student">
                      <!--默认按构造器形参顺序赋值-->
                      <constructor-arg value="1"/>
                      <constructor-arg value="vivy"/>
                      <constructor-arg value="100"/>
                  </bean>
              </beans>
              ```

         2. 通过索引值指定参数位置

            * ```xml
              <?xml version="1.0" encoding="UTF-8"?>
              <beans xmlns="http://www.springframework.org/schema/beans"
                     xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                     xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
                  <!--通过bean的构造器赋值-->
                  <bean id="student" class="pers.spring4.n1_helloworld.Student">
                      <!--通过索引值指定参数位置-->
                      <constructor-arg index="0" value="1"/>
                      <constructor-arg index="2" value="100"/>
                      <constructor-arg index="1" value="vivy"/>
                  </bean>
              </beans>
              ```

         3. 通过参数名指定参数位置

            * ```xml
              <?xml version="1.0" encoding="UTF-8"?>
              <beans xmlns="http://www.springframework.org/schema/beans"
                     xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                     xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
                  <!--通过bean的构造器赋值-->
                  <bean id="student" class="pers.spring4.n1_helloworld.Student">
                      <!--通过参数名指定参数位置-->
                      <constructor-arg name="studentId" value="1"/>
                      <constructor-arg name="stuName" value="vivy"/>
                      <constructor-arg name="age" value="100"/>
                  </bean>
              </beans>
              ```

         4. 通过类型不同区分重载的构造器

            * ```xml
              <?xml version="1.0" encoding="UTF-8"?>
              <beans xmlns="http://www.springframework.org/schema/beans"
                     xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                     xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
                  <!--通过bean的构造器赋值-->
                  <bean id="student" class="pers.spring4.n1_helloworld.Student">
                      <!--通过类型不同区分重载的构造器-->
                      <constructor-arg index="0" value="1" type="java.lang.Integer"/>
                      <constructor-arg index="2" value="100"  type="int"/>
                      <constructor-arg index="1" value="123"  type="java.lang.String"/>
                  </bean>
              </beans>
              ```

         5. 属性说明

            1. name:有参构造器参数列表的参数名
            2. index:有参构造器参数列表的参数位置(从0开始)
            3. type:数据类型
            4. value:注入的属性值

      3. 给bean的级联属性赋值

         * ```xml
           <?xml version="1.0" encoding="UTF-8"?>
           <beans xmlns="http://www.springframework.org/schema/beans"
                  xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                  xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
               <!--通过bean的构造器赋值-->
               <bean id="student" class="pers.spring4.n1_helloworld.Student">
                   <!--通过类型不同区分重载的构造器-->
                   <constructor-arg index="0" value="1" type="java.lang.Integer"/>
                   <constructor-arg index="2" value="100"  type="int"/>
                   <constructor-arg index="1" value="123"  type="java.lang.String"/>
           
                   <!--给bean的级联属性赋值-->
                   <property name="teacher" ref="teacher"/>
                   <property name="teacher.teaName" value="John"/>
               </bean>
               <bean id="teacher" class="pers.spring4.n1_helloworld.Teacher"></bean>
           </beans>
           ```

         * ref: 使用bean给属性赋值

         * 使用 `.` 为级联属性赋值

      4. p名称空间注入

         1. 简化基于xml配置方式

         2. 步骤

            1. 添加p名称空间注入

               * ```xml
                 xmlns:p="http://www.springframework.org/schema/p"
                 ```

            2. 进行属性注入

               * ```xml
                 <!--2 set方法注入属性 p名称空间简化注入-->
                 <?xml version="1.0" encoding="UTF-8"?>
                 <beans xmlns="http://www.springframework.org/schema/beans"
                        xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:p="http://www.springframework.org/schema/p"
                        xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
                     <!--p名称空间注入-->
                     <bean id="student" class="pers.spring4.n1_helloworld.Student"
                           p:studentId="1" p:stuName="vivy" p:age="100" p:teacher-ref="teacher"
                     />
                     <bean id="teacher" class="pers.spring4.n1_helloworld.Teacher"></bean>
                 </beans>
                 ```

### 3.xml注入其他类型属性

#### 1.字面量

1. null值

   * ```xml
     <?xml version="1.0" encoding="UTF-8"?>
     <beans xmlns="http://www.springframework.org/schema/beans"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:p="http://www.springframework.org/schema/p"
            xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
         <!--通过bean的set方法赋值-->
         <bean id="student" class="pers.spring4.n1_helloworld.Student">
             <!--<property name="studentId" value="1"/>-->
             <!--null值-->
             <property name="studentId">
                 <null/>
             </property>
             <property name="stuName" value="vivy"/>
             <property name="age" value="100"/>
         </bean>
     </beans>
     ```

2. 属性值包含特殊符号

   * ```xml
     <?xml version="1.0" encoding="UTF-8"?>
     <beans xmlns="http://www.springframework.org/schema/beans"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:p="http://www.springframework.org/schema/p"
            xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
         <!--通过bean的set方法赋值-->
         <bean id="student" class="pers.spring4.n1_helloworld.Student">
     
             <!--<property name="studentId" value="1"/>-->
             <!--null值-->
             <property name="studentId">
                 <null/>
             </property>
             <!--<property name="stuName" value="vivy"/>-->
             <!--属性值包含特殊符号
                 1.把<>进行转义:&lt; &gt;
                 2.使用CDATA
             -->
             <property name="stuName">
                 <value><![CDATA[<<Vivy>>]]></value>
             </property>
             <property name="age" value="100"/>
         </bean>
     </beans>
     ```

#### 2.注入属性

##### 1.外部bean

1. 创建2个类service 和 dao

   * ```xml
     <?xml version="1.0" encoding="UTF-8"?>
     <beans xmlns="http://www.springframework.org/schema/beans"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
         <!--service和dao对象创建-->
         <bean id="userService" class="pers.service.UserService">
             <!--set方法注入属性
                 name:类中属性名
                 ref:外部bean标签id值
             -->
             <property name="userDao" ref="userDaoImpl"/>
         </bean>
         <bean id="userDaoImpl" class="pers.dao.UserDaoImpl"></bean>
     </beans>
     ```

2. 在service调用dao

   * ```java
     public class UserService {
         //创建UserDao类型属性,生成set方法
         private UserDao userDao;
     
         public void setUserDao(UserDao userDao) {
             this.userDao = userDao;
         }
     
         public void add(){
             System.out.println("service add...");
             userDao.update();
         }
     }
     ```

   * ```java
     //测试
     @Test
     public void testBean(){
         //加载spring配置文件
         ApplicationContext context = new ClassPathXmlApplicationContext("bean2.xml");
         //获取配置创建的对象
     
         UserService userService = context.getBean("userService",UserService.class);
         userService.add();
     }
     ```

##### 2.内部bean

1. 一对多关系:部门和员工

   * 部门是一;员工是多

2. 在实体类之间表示一对多关系,员工表示所属部门,使用对象类型属性进行表示

   * 部门类

     * ```java
       public class Dept {
           private String dname;
           public void setDname(String dname){
               this.dname = dname;
           }
       }
       ```

   * 员工类

     * ```java
       public class Emp {
           private String ename;
           private String gender;
           private Dept dept;
       
           public void setEname(String ename) {
               this.ename = ename;
           }
       
           public void setGender(String gender) {
               this.gender = gender;
           }
       
           public void setDept(Dept dept) {
               this.dept = dept;
           }
       }
       ```

3. 在spring配置文件中进行配置

   * ```xml
     <?xml version="1.0" encoding="UTF-8"?>
     <beans xmlns="http://www.springframework.org/schema/beans"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
     
         <!--内部bean-->
         <bean id="emp" class="pers.bean.Emp">
             <!--设置2个普通属性-->
             <property name="ename" value="Vivy"/>
             <property name="gender" value="female"/>
             <!--设置对象类型属性-->
             <property name="dept">
                 <bean id="dept" class="pers.bean.Dept">
                     <property name="dname" value="security"/>
                 </bean>
             </property>
         </bean>
     </beans>
     ```

##### 3.级联赋值

1. 使用外部bean配置赋值

   * ```xml
     <?xml version="1.0" encoding="UTF-8"?>
     <beans xmlns="http://www.springframework.org/schema/beans"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
         <!--级联赋值-->
         <bean id="emp" class="pers.bean.Emp">
             <!--设置2个普通属性-->
             <property name="ename" value="Vivy"/>
             <property name="gender" value="female"/>
             <!--级联赋值:创建外部bean对象时其属性会按配置赋值-->
             <property name="dept" ref="dept"/>
         </bean>
         <bean id="dept" class="pers.bean.Dept">
             <!--级联赋值:设置属性后,如果有引用则会自动赋值-->
             <property name="dname" value="security"/>
         </bean>
     </beans>
     ```

2. 内部指定赋值

   * ```xml
     <?xml version="1.0" encoding="UTF-8"?>
     <beans xmlns="http://www.springframework.org/schema/beans"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
         <!--级联赋值-->
         <bean id="emp" class="pers.bean.Emp">
             <!--设置2个普通属性-->
             <property name="ename" value="Vivy"/>
             <property name="gender" value="female"/>
             <!--级联赋值:创建外部bean对象时其属性默认会按配置赋值-->
             <property name="dept" ref="dept"/>
             <!--级联赋值2:给外部bean对象的指定属性赋值(需要上一行代码,需要该对象的get方法)-->
             <!--将外部bean的name的默认值engineering改为security-->
             <property name="dept.dname" value="security"/>
         </bean>
         <bean id="dept" class="pers.bean.Dept">
             <!--级联赋值1:设置属性后,如果有引用则会自动赋值-->
             <property name="dname" value="engineering"/>
         </bean>
     </beans>
     ```

#### 3.注入集合属性

##### 1.注入数组类型属性

* ```xml
  <!--数组-->
  <property name="courses">
      <array>
          <value>java</value>
          <value>database</value>
      </array>
  </property>
  ```

##### 2.注入List集合类型属性

* ```xml
  <!--List-->
  <property name="list">
      <list>
          <value>篮球</value>
          <value>足球</value>
      </list>
  </property>
  ```

##### 3.注入Map集合类型属性

* ```xml
  <!--Map-->
  <property name="map">
      <map>
          <entry key="java" value="80"/>
          <entry key="database" value="65"/>
      </map>
  </property>
  ```

##### 4.注入Set集合类型属性

* ```xml
  <!--Set-->
  <property name="sets">
      <set>
          <value>China</value>
          <value>USA</value>
      </set>
  </property>
  ```

##### 5.集合使用对象类型值

* ```xml
  <!--有集合属性的bean-->
  <!--集合使用对象类型值-->
  <property name="courseList">
      <list>
          <ref bean="course1" />
          <ref bean="course2" />
      </list>
  </property>
  ```

* ```xml
  <!--用于对象类型值的外部bean-->
  <bean id="course1" class="pers.bean.Course">
      <property name="cname" value="Spring5"></property>
  </bean>
  <bean id="course2" class="pers.bean.Course">
      <property name="cname" value="MyBatis"></property>
  </bean>
  ```

##### 6.注入Properties类型属性

* ```xml
  <bean id="shop" class="pers.spring4.n2_ioc.bean.Shop">
      <property name="prop">
          <props>
              <prop key="userName">root</prop>
              <prop key="password">root</prop>
              <prop key="url">jdbc:mysql:///test</prop>
              <prop key="driverClass">com.mysql.jdbc.Driver</prop>
          </props>
      </property>
  </bean>
  ```

##### 7.抽取注入部分

1. 在spring配置文件引入命名空间util

   * ```xml
     <?xml version="1.0" encoding="UTF-8"?>
     <beans xmlns="http://www.springframework.org/schema/beans"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xmlns:util="http://www.springframework.org/schema/util"
            xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                                http://www.springframework.org/schema/util http://www.springframework.org/schema/util/spring-util.xsd">
     ```

2. 使用util标签提取属性注入

   * ```xml
     <?xml version="1.0" encoding="UTF-8"?>
     <beans xmlns="http://www.springframework.org/schema/beans"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xmlns:util="http://www.springframework.org/schema/util"
            xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                                http://www.springframework.org/schema/util http://www.springframework.org/schema/util/spring-util.xsd">
         <!--1.提取List集合类型属性注入-->
         <util:list id="cartList">
             <value>时间简史</value>
             <value>灼眼的夏娜</value>
             <value>龙与虎</value>
         </util:list>
         <!--2.使用提取的属性注入-->
         <bean id="cart" class="pers.bean.Cart">
             <property name="list" ref="cartList"/>
         </bean>
     </beans>
     ```


### 4.通过工厂创建bean

#### 1.bean类型

* Spring有2种类型的bean,一种为普通bean,一种为工厂bean(FactoryBean)

#### 2.区别

1. 普通bean
   * 在配置文件定义bean类型就是返回类型
2. 工厂bean
   * 在配置文件定义bean类型可以和返回类型不一样

#### 3.使用步骤

##### 1.静态工厂

1. bean

   * ```java
     public class Employee {
         private String employeeName;
         private Integer employeeAge;
     
         public String getEmployeeName() {
             return employeeName;
         }
     
         public void setEmployeeName(String employeeName) {
             this.employeeName = employeeName;
         }
     
         public Integer getEmployeeAge() {
             return employeeAge;
         }
     
         public void setEmployeeAge(Integer employeeAge) {
             this.employeeAge = employeeAge;
         }
     
         @Override
         public String toString() {
             return "Employee{" +
                     "employeeName='" + employeeName + '\'' +
                     ", employeeAge=" + employeeAge +
                     '}';
         }
     }
     ```

2. factory

   * ```java
     public class EmployeeStaticFactory {
         public static Employee getEmployee(String name){
             System.out.println("creating Employee");
             Employee employee = new Employee();
             employee.setEmployeeName(name);
             employee.setEmployeeAge(24);
             return employee;
         }
     }
     ```

3. xml

   * ```xml
     <?xml version="1.0" encoding="UTF-8"?>
     <beans xmlns="http://www.springframework.org/schema/beans"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
     
         <!-- 1、静态工厂(不需要创建工厂本身)factory-method="getEmployee"：
     		指定哪个方法是工厂方法
     		class：指定静态工厂全类名
     		factory-method:指定工厂方法
     		constructor-arg：可以为方法传参
     	 -->
         <bean id="employee" class="pers.spring4.day02.n1_ioc.factory.EmployeeStaticFactory" factory-method="getEmployee">
             <constructor-arg value="vivy"></constructor-arg>
         </bean>
     </beans>
     ```

##### 2.实例工厂

1. bean

   1. 同上

2. factory

   * ```java
     public class EmployeeInstanceFactory {
         public Employee getEmployee(String name){
             System.out.println("EmployeeInstanceFactory creating Employee");
             Employee employee = new Employee();
             employee.setEmployeeName(name);
             employee.setEmployeeAge(24);
             return employee;
         }
     }
     ```

3. xml

   * ```xml
     <?xml version="1.0" encoding="UTF-8"?>
     <beans xmlns="http://www.springframework.org/schema/beans"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
         <!--2、实例工厂使用
                 1、先配置出实例工厂对象
                 2、配置我们要创建的AirPlane使用哪个工厂创建
                     1）、factory-bean：指定使用哪个工厂实例
                     2）、factory-method：使用哪个工厂方法
         -->
         <bean id="employeeInstanceFactory" class="pers.spring4.day02.n1_ioc.factory.EmployeeInstanceFactory"/>
         <bean id="employee2" class="pers.spring4.day02.n1_ioc.bean.Employee" factory-bean="employeeInstanceFactory" factory-method="getEmployee">
             <constructor-arg value="matumoto"></constructor-arg>
         </bean>
     </beans>
     ```

##### 3.FactoryBean

1. 创建类,让这个类作为工厂bean,实现接口FactoryBean

   * ```java
     public class EmployeeFactoryBeanImpl implements FactoryBean<Employee>{
         @Override
         public Employee getObject() throws Exception {
             Employee employee = new Employee();
             employee.setEmployeeName("vivy");
             employee.setEmployeeAge(100);
             return employee;
         }
     
         @Override
         public Class<?> getObjectType() {
             return Employee.class;
         }
     
         @Override
         public boolean isSingleton() {
             return false;
         }
     }
     ```
   
2. 实现接口里面的方法,在方法中定义返回类型

   * 配置文件

     * ```xml
       <?xml version="1.0" encoding="UTF-8"?>
       <beans xmlns="http://www.springframework.org/schema/beans"
              xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
              xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
           <!--FactoryBean★(是Spring规定的一个接口);
               只要是这个接口的实现类，Spring都认为是一个工厂；
               1、ioc容器启动的时候不会创建实例
               2、FactoryBean；获取的时候的才创建对象
           -->
           <bean id="employeeFactoryBeanImpl" class="pers.spring4.day02.n1_ioc.factory.EmployeeFactoryBeanImpl"/>
       </beans>
       ```
   
   * 如何使用
   
     * ```java
       @Test
       public void testMyBean(){
           //加载spring配置文件
           ApplicationContext context = new ClassPathXmlApplicationContext("springconfig_n1.xml");
           Employee employee = applicationContext.getBean("employeeFactoryBeanImpl", Employee.class);
           System.out.println(employee3);
       }
       ```

### 5.bean高级配置

#### 1.配置信息的继承

* ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
      <!-- 以empPar作为父bean，继承后可以省略公共属性值的配置 -->
      <!-- abstract="true"：(可选)这个bean的配置是一个抽象的，不能获取他的实例，只能被别人用来继承 -->
      <bean id="empPar" class="pers.spring4.day02.n1_ioc.bean.Employee" abstract="true">
          <property name="employeeAge" value="24"/>
      </bean>
      <bean id="emp1" class="pers.spring4.day02.n1_ioc.bean.Employee" parent="empPar">
          <property name="employeeName" value="v1"/>
      </bean>
      <bean id="emp2" class="pers.spring4.day02.n1_ioc.bean.Employee" parent="empPar">
          <property name="employeeName" value="v2"/>
      </bean>
  </beans>
  ```

* 子bean从父bean中继承配置，包括bean的属性配置;子bean也可以覆盖从父bean继承过来的配置

#### 2.bean之间的依赖

* ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
  
      <bean id="empPar" class="pers.spring4.day02.n1_ioc.bean.Employee">
          <property name="employeeAge" value="24"/>
      </bean>
      <bean id="emp1" class="pers.spring4.day02.n1_ioc.bean.Employee" parent="empPar">
          <property name="employeeName" value="v1"/>
      </bean>
      <bean id="emp2" class="pers.spring4.day02.n1_ioc.bean.Employee" parent="empPar">
          <property name="employeeName" value="v2"/>
      </bean>
      <!--depends-on定义依赖
          要求创建emp3之前必须先创建emp1
      -->
      <bean id="emp3" class="pers.spring4.day02.n1_ioc.bean.Employee" depends-on="emp1">
          <property name="employeeName" value="v3"/>
          <property name="employeeAge" value="14"/>
      </bean>
  </beans>
  ```

* 依赖关系不等于引用关系，Employee即使依赖Department也可以不引用它。

### 6.bean作用域

1. 在Spring里面设置创建bean实例是单实例还是多实例

2. 在Spring里面,默认情况下bean是单实例对象

3. 如何设置单实例还是多实例
   1. 使用bean标签中的属性(scope)设置
   2. scope属性值
      1. 缺省值:singleton,单实例;IOC容器对象创建时就创建bean的对象实例
      2. prototype:多实例;IOC容器在获取bean的实例时创建bean的实例对象
      3. Request:每一次请求创建一个新实例
      4. Session:每一次会话创建一个新实例
   3. singleton和prototype的其他区别
      1. singleton:加载配置文件时就会创建单实例对象(饿汉式)
      2. ptototype:直到调用getBean方法时才创建多实例对象(懒汉式)

### 7.bean生命周期

#### 1.生命周期

* 从创建到销毁的过程

#### 2.bean生命周期

1. 通过构造器创建 bean 实例（无参数构造）
2. 为 bean 的属性设置值和对其他 bean 引用（调用 set 方法）
3. 调用 bean 的初始化的方法（需要进行配置初始化的方法）
4. bean 可以使用了（对象获取到了）
5. 当容器关闭时候，调用 bean 的销毁的方法（需要进行配置销毁的方法）

#### 3.演示 bean 生命周期

1. 构造器创建bean实例(无参构造)
2. 为属性设置值(set方法)
3. 把bean实例传递给bean后置处理器的方法(postProcessBeforeInitialization)
4. 初始化方法(需要配置init-method)
5. 把bean实例传递给bean后置处理器的另一个方法(postProcessAfterInitialization)
6. bean实例化
7. 当容器关闭时,调用bean的销毁方法(需要配置destroy-method)

#### 4.代码(未添加后置处理器)

##### 类文件

* ```java
  package pers.bean;
  
  /**
   * @author dinhyu
   * @version v1.0
   * date: 2021-07-02 8:52
   */
  public class Manager {
      private String mname;
  
      public Manager() {
          System.out.println("1.无参构造执行");
      }
  
      public void setMname(String mname) {
          this.mname = mname;
          System.out.println("2.set方法执行");
      }
  
      //创建执行的初始化方法
      public void initMethod(){
          System.out.println("3.初始化方法执行");
      }
  
      //创建执行的销毁的方法
      public void destoryMethod(){
          System.out.println("5.销毁方法执行");
      }
  }
  
  ```

##### 配置文件

* ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
      <bean id="manager" class="pers.bean.Manager" init-method="initMethod" destroy-method="destoryMethod">
          <property name="mname" value="John"/>
      </bean>
  </beans>
  ```

##### 测试文件

* ```java
  @Test
  public void testManagerBean(){
      //加载spring配置文件
      ApplicationContext context = new ClassPathXmlApplicationContext("08_ManagerBean.xml");
      Manager manager = context.getBean("manager", Manager.class);
      System.out.println("4.获取bean实例对象");
      System.out.println(manager);
      //手动让bean实例销毁
      ((ClassPathXmlApplicationContext)context).close();
  }
  ```

#### 5.代码(添加后置处理器)

##### 后置处理器说明

1. bean后置处理器允许在调用**初始化方法前后**对bean进行额外的处理
2. bean后置处理器对IOC容器里的所有bean实例逐一处理，而非单一实例。其典型应用是：检查bean属性的正确性或根据特定的标准更改bean的属性。
3. bean后置处理器时需要实现接口：org.springframework.beans.factory.config.BeanPostProcessor。在初始化方法被调用前后，Spring将把每个bean实例分别传递给上述接口的以下两个方法：
   1. postProcessBeforeInitialization(Object, String)
   2. postProcessAfterInitialization(Object, String)

##### 后置处理器文件(需要在配置文件中配置)

* ```java
  package pers.bean;
  
  import org.springframework.beans.BeansException;
  import org.springframework.beans.factory.config.BeanPostProcessor;
  import org.springframework.lang.Nullable;
  
  /**
   * @author dinhyu
   * @version v1.0
   * date: 2021-07-02 9:10
   */
  public class MyBeanPost implements BeanPostProcessor {
      @Override
      public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
          System.out.println("初始化之前执行的后置处理器方法");
          return bean;
      }
  
      @Override
      public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
          System.out.println("初始化之后执行的后置处理器方法");
          return bean;
      }
  
  }
  
  ```

##### 配置文件

* ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
      <bean id="manager" class="pers.bean.Manager" init-method="initMethod" destroy-method="destoryMethod">
          <property name="mname" value="John"/>
      </bean>
      <!--配置后置处理器配置
          :会为当前配置文件中的其他所有bean添加后置处理器
      -->
      <bean id="myBeanPost" class="pers.bean.MyBeanPost"></bean>
  </beans>
  ```

### 8.xml自动装配

#### 1.自动装配

##### 1.概念

1. 手动装配：以value或ref的方式**明确指定属性值**都是手动装配
2. 自动装配：根据指定装配规则（属性名称或者属性类型），Spring 自动将匹配的属性值进行注入

##### 2.装配模式

1. 根据**类型**自动装配：将类型匹配的bean作为属性注入到另一个bean中。若IOC容器中有多个与目标bean类型一致的bean，Spring将无法判定哪个bean最合适该属性，所以不能执行自动装配
2. 根据**名称**自动装配：必须将目标bean的名称和属性名设置的完全相同
3. 通过构造器自动装配：当bean中存在多个构造器时，此种自动装配方式将会很复杂。不推荐使用。

##### 3.选用建议

* 相对于使用注解的方式实现的自动装配，在XML文档中进行的自动装配略显笨拙，在项目中更多的使用注解的方式实现。

#### 2.演示自动装配过程

1. 根据属性名称自动注入

   * 配置

     * ```xml
       <?xml version="1.0" encoding="UTF-8"?>
       <beans xmlns="http://www.springframework.org/schema/beans"
              xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
              xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
           <!--自动装配
               bean标签属性autowire,配置自动装配
               autowire属性常用的2个值
                   byName 根据属性名称注入 ，注入值 bean 的 id 值和类属性名称一样
                   byType 根据属性类型注入
           -->
           <bean id="employee" class="pers.spring4.day02.n2_ioc.bean.Employee" autowire="byName"/>
           <bean id="department" class="pers.spring4.day02.n2_ioc.bean.Department">
               <property name="depName" value="HR"/>
           </bean>
       </beans>
       ```

2. 根据属性类型自动注入

   * 配置

     * ```xml
       <?xml version="1.0" encoding="UTF-8"?>
       <beans xmlns="http://www.springframework.org/schema/beans"
              xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
              xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
           <!--自动装配
               bean标签属性autowire,配置自动装配
               autowire属性常用的2个值
                   byName 根据属性名称注入 ，注入值 bean 的 id 值和类属性名称一样
                   byType 根据属性类型注入
           -->
           <bean id="employee" class="pers.spring4.day02.n2_ioc.bean.Employee" autowire="byType"/>
           <bean id="department" class="pers.spring4.day02.n2_ioc.bean.Department">
               <property name="depName" value="HR"/>
           </bean>
           
           <!--byType:有多个同类型bean时会报错,因为不知道用哪个bean-->
           <bean id="department1" class="pers.spring4.day02.n2_ioc.bean.Department">
               <property name="depName" value="HR"/>
           </bean>
       </beans>
       ```

### 9.外部属性文件

#### 1.直接配置数据库信息

##### 1.配置德鲁伊连接池

* ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">
      <!--直接配置数据库信息-->
      <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
          <property name="username" value="root"/>
          <property name="password" value="root"/>
          <property name="url" value="jdbc:mysql:///test"/>
          <property name="driverClassName" value="com.mysql.jdbc.Driver"/>
      </bean>
  </beans>
  ```

##### 2.引入jar包

#### 2.引入外部属性文件配置数据库连接池

##### 1.创建外部属性文件

* ```properties
  prop.driverClassName=com.mysql.jdbc.Driver
  prop.url=jdbc:mysql://192.168.192.248:3306/book
  prop.username=root
  prop.password=dinhyudemysq
  ```

##### 2.把外部属性文件引入spring配置文件中

* 引入context名称空间

  * ```xml
    xmlns:context="http://www.springframework.org/schema/context"
    xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
    http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">
    ```

* 在 spring 配置文件使用标签引入外部属性文件

  * ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xmlns:context="http://www.springframework.org/schema/context"
           xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">
    
        <!--引入外部属性文件-->
        <context:property-placeholder location="classpath:jdbc.properties"/>
        <!--配置数据库信息-->
        <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
            <property name="username" value="${prop.username}"/>
            <property name="password" value="${prop.password}"/>
            <property name="url" value="${prop.url}"/>
            <property name="driverClassName" value="${prop.driverClassName}"/>
        </bean>
    </beans>
    ```

### 10.SpEL

#### 1.简介

* Spring Expression Language，Spring表达式语言，简称SpEL。支持运行时查询并可以操作对象图

#### 2.基本语法

* SpEL使用**#{…}**作为定界符，所有在大框号中的字符都将被认为是SpEL表达式

#### 3.使用字面量

1. 整数：
   1. <property name="count" value=**"#{5}"**/>
2. 小数：<property name="frequency" value=**"#{89.7}"**/>
3. 科学计数法：<property name="capacity" value=**"#{1e4}"**/>
4. String类型的字面量可以使用单引号或者双引号作为字符串的定界符号
   1. <property name=“name” value=**"#{'Chuck'}"**/>
   2. <property name='name' value=**'#{"Chuck"}'**/>
5. Boolean：<property name="enabled" value=**"#{false}"**/>

#### 4.引用其他bean

* ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xmlns:context="http://www.springframework.org/schema/context"
         xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">
      <bean id="emp1" class="pers.spring4.day02.n3_spel.bean.Employee">
  		<!--引用其他bean-->
          <property name="dept" value="#{dept}"/>
      </bean>
      <bean id="dept" class="pers.spring4.day02.n3_spel.bean.Department">
          <property name="deptName" value="HR"/>
      </bean>
  </beans>
  ```

#### 5.引用其他bean的属性值作为自己某个属性的值

* ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xmlns:context="http://www.springframework.org/schema/context"
         xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">
      <bean id="emp1" class="pers.spring4.day02.n3_spel.bean.Employee">
  		<!--引用其他bean的属性值作为自己某个属性的值-->
          <property name="deptName" value="#{dept.deptName}"/>
      </bean>
      <bean id="dept" class="pers.spring4.day02.n3_spel.bean.Department">
          <property name="deptName" value="HR"/>
      </bean>
  </beans>
  ```

#### 6.调用非静态方法

* ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xmlns:context="http://www.springframework.org/schema/context"
         xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">
      <bean id="emp2" class="pers.spring4.day02.n3_spel.bean.Employee">
          <!--调用非静态方法-->
          <property name="salaryOfYear" value="#{salaryGenerator.getSalaryOfYear(5000)}"/>
      </bean>
      <bean id="salaryGenerator" class="pers.spring4.day02.n3_spel.bean.SalaryGenerator"/>
  </beans>
  ```

#### 7.调用非静态方法

* ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xmlns:context="http://www.springframework.org/schema/context"
         xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">
      <bean id="emp3" class="pers.spring4.day02.n3_spel.bean.Employee">
          <!--调用静态方法-->
          <property name="circle" value="#{T(java.lang.Math).PI*20}"/>
      </bean>
  </beans>
  ```

#### 8.算符

1. 算术运算符：+、-、*、/、%、^
2. 字符串连接：+
3. 比较运算符：<、>、==、<=、>=、lt、gt、eq、le、ge
4. 逻辑运算符：and, or, not, |
5. 三目运算符：判断条件?判断结果为true时的取值:判断结果为false时的取值
6. 正则表达式：matches

## IOC 操作 Bean 管理(基于注解方式)

### 1.什么是注解

1. 注解是代码特殊标记，格式：@注解名称(属性名称=属性值, 属性名称=属性值..) 
2. 使用注解，注解作用在类上面，方法上面，属性上面
3. 使用注解目的：简化 xml 配置

### 2.Spring 针对 Bean 管理中创建对象提供注解

1. @Component
2. @Service
3. @Controller
4. @Repositor
5. 上面四个注解功能是一样的，都可以用来创建 bean 实例

### 3.基于注解方式实现对象创建

#### 1.引入依赖

* spring-aop-5.2.6.RELEASE.jar

#### 2.开启组件扫描

* ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xmlns:p="http://www.springframework.org/schema/p"
         xmlns:context="http://www.springframework.org/schema/context"
         xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                             http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">
      <!--开启组件扫描
          1 如果扫描多个包，多个包使用逗号隔开
          2 或者直接扫描包上层目录
      -->
      <context:component-scan base-package="pers"/>
  </beans>
  ```
  
* 详细说明

  * **base-package**属性指定一个需要扫描的基类包，Spring容器将会扫描这个基类包及其子包中的所有类。

  * 当需要扫描多个包时可以使用逗号分隔。

  * 如果仅希望扫描特定的类而非基包下的所有类，可使用resource-pattern属性过滤特定的类

    * ```xml
      <context:component-scan 
      	base-package="pers" 
      	resource-pattern="autowire/*.class"/>
      ```

  * 包含与排除

    * `<context:include-filter>`子节点表示要包含的目标类
      * 注意：通常需要与use-default-filters属性配合使用才能够达到“仅包含某些组件”这样的效果。即：通过将use-default-filters属性设置为false，禁用默认过滤器，然后扫描的就只是include-filter中的规则指定的组件了。
    * `<context:exclude-filter>`子节点表示要排除在外的目标类
    * component-scan下可以拥有若干个include-filter和exclude-filter子节点

  * 过滤表达式

    * annotation类别
      * 示例:pers.XxxAnnotation
      * 过滤所有标注了XxxAnnotation的类。这个规则根据目标组件是否标注了指定类型的注解进行过滤。
    * assignable类别
      * 示例:pers.BaseXxx
      * 过滤所有BaseXxx类的子类。这个规则根据目标组件是否是指定类型的子类的方式进行过滤
    * aspectj类别
      * 示例:pers.*Service+
      * 所有类名是以Service结束的，或这样的类的子类。这个规则根据AspectJ表达式进行过滤。
    * regex类别
      * 示例:`pers\.anno\.*`
      * 所有pers.anno包下的类。这个规则根据正则表达式匹配到的类名进行过滤。
    * custom类别
      * 示例:pers.XxxTypeFilte
      * 使用XxxTypeFilter类通过编码的方式自定义过滤规则。该类必须实现org.springframework.core.type.filter.TypeFilter接口

#### 3.创建类，在类上面添加创建对象注解

* ```java
  //在注解里面 value 属性值可以省略不写，
  //默认值是类名称，首字母小写
  //UserService --> userServic
  @Service(value = "userName") ///<bean id="userService" class=".."/>
  public class UserService {
      public void add(){
          System.out.println("pers.service add...");
      }
  }
  ```

#### 4.开启组件扫描细节配置

* ```xml
  <!--示例 1
   use-default-filters="false" 表示现在不使用默认 filter，自己配置 filter
   context:include-filter ，设置扫描哪些内容
  -->
  <context:component-scan base-package="com.atguigu" use-default-filters="false">
   <context:include-filter type="annotation" 
  expression="org.springframework.stereotype.Controller"/>
  </context:component-scan>
  <!--示例 2
   下面配置扫描包所有内容
   context:exclude-filter： 设置哪些内容不进行扫描
  -->
  <context:component-scan base-package="com.atguigu">
   <context:exclude-filter type="annotation" 
  expression="org.springframework.stereotype.Controller"/>
  </context:component-scan>
  ```

#### 5.基于注解方式实现属性注入

##### 1.@Autowired：根据属性类型进行自动装配

1. 说明

   1. 根据类型实现自动装配
   2. 构造器、普通字段(即使是非public)、一切具有参数的方法都可以应用@Autowired注解
   3. 默认情况下，所有使用@Autowired注解的属性都需要被设置。当Spring找不到匹配的bean装配属性时，会抛出异常
   4. 若某一属性允许不被设置，可以设置@Autowired注解的required属性为 false
   5. 默认情况下，当IOC容器里存在多个类型兼容的bean时，Spring会尝试匹配bean的id值是否与变量名相同，如果相同则进行装配。如果bean的id值不相同，通过类型的自动装配将无法工作。此时可以在@Qualifier注解里提供bean的名称。Spring甚至允许在方法的形参上标注@Qualifiter注解以指定注入bean的名称
   6. @Autowired注解也可以应用在数组类型的属性上，此时Spring将会把所有匹配的bean进行自动装配
   7. @Autowired注解也可以应用在集合属性上，此时Spring读取该集合的类型信息，然后自动装配所有与之兼容的bean
   8. @Autowired注解用在java.util.Map上时，若该Map的键值为String，那么 Spring将自动装配与值类型兼容的bean作为值，并以bean的id值作为键
   9. **@Inject**
      1. @Inject和@Autowired注解一样也是按类型注入匹配的bean，但没有reqired属性

2. 把 service 和 dao 对象创建，在 service 和 dao 类添加创建对象注解

3. 在 service 注入 dao 对象，在 service 类添加 dao 类型属性，在属性上面使用注解

4. dao代码

   * ```java
     @Repository
     public class UserDaoImpl implements UserDao {
     
         @Override
         public void update() {
             System.out.println("pers.dao update...");
         }
     }
     ```

5. service代码

   * ```java
     //在注解里面 value 属性值可以省略不写，
     //默认值是类名称，首字母小写
     //UserService --> userServic
     @Service(value = "userService") ///<bean id="userService" class=".."/>
     public class UserService {
         //创建UserDao类型属性,不需要生成set方法
         //添加注入属性注解
         @Autowired//根据属性类型进行自动装配
         private UserDao userDao;
     
         public void add(){
             System.out.println("pers.service add...");
             userDao.update();
         }
     }
     ```

##### 2.@Qualifier：根据名称进行注入

1. 这个@Qualifier 注解的使用，和上面@Autowired 一起使用,当有多个实现类时通过名称指定类型

2. dao代码

   * ```java
     @Repository
     public class UserDaoImpl implements UserDao {
     
         @Override
         public void update() {
             System.out.println("pers.dao.userDaoImpl update...");
         }
     }
     @Repository
     public class UserDaoImpl1 implements UserDao {
     
         @Override
         public void update() {
             System.out.println("pers.dao.userDaoImpl1 update...");
         }
     }
     
     ```

3. service代码

   * ```java
     //在注解里面 value 属性值可以省略不写，
     //默认值是类名称，首字母小写
     //UserService --> userServic
     @Service(value = "userService") ///<bean id="userService" class=".."/>
     public class UserService {
         //创建UserDao类型属性,不需要生成set方法
         //添加注入属性注解
         @Autowired//根据属性类型进行自动装配
         @Qualifier(value = "userDaoImpl1")//有多个实现类时通过名称指定类型
         private UserDao userDao;
     
         public void add(){
             System.out.println("pers.service add...");
             userDao.update();
         }
     }
     ```

##### 3.@Resource：可以根据类型注入，可以根据名称注入

* 说明
  * @Resource注解要求提供一个bean名称的属性，若该属性为空，则自动采用标注处的变量或方法名作为bean的名称

* service代码

  * ```java
    //在注解里面 value 属性值可以省略不写，
    //默认值是类名称，首字母小写
    //UserService --> userServic
    @Service(value = "userService") ///<bean id="userService" class=".."/>
    public class UserService {
        //创建UserDao类型属性,不需要生成set方法
        //添加注入属性注解
        // @Resource//根据类型注入
        @Resource(name = "userDaoImpl1")//根据名称注入
        private UserDao userDao;
    
        public void add(){
            System.out.println("pers.service add...");
            userDao.update();
        }
    }
    ```

##### 4.@Value：注入普通类型属性

* service代码

  * ```java
    //在注解里面 value 属性值可以省略不写，
    //默认值是类名称，首字母小写
    //UserService --> userServic
    @Service(value = "userService") ///<bean id="userService" class=".."/>
    public class UserService {
        //创建UserDao类型属性,不需要生成set方法
        //添加注入属性注解
        // @Resource//根据类型注入
        @Resource(name = "userDaoImpl1")//根据名称注入
        private UserDao userDao;
    
        @Value(value = "Vivy")
        private String name;
    
        public void add(){
            System.out.println("pers.service add..." + name);
            userDao.update();
        }
    }
    ```

#### 6.完全注解开发

##### 1.创建配置类，替代 xml 配置文件

* ```java
  @Configurable //作为配置类,代替xml配置文件
  @ComponentScan(basePackages = "pers")//开启扫描
  public class SpringConfig {
  
  }
  ```

##### 2.编写测试类

* ```java
  @Test
  public void testServiceWithoutXml(){
      //加载配置类
      ApplicationContext context = new AnnotationConfigApplicationContext(SpringConfig.class);
      //获取bean
      UserService userService = context.getBean("userService", UserService.class);
      System.out.println(userService);
      userService.add();
  }
  ```

#### 7.泛型依赖注入

1. 简介

   * Spring 4.x中可以为子类注入子类对应的泛型类型的成员变量的引用

2. 实现

   1. 类

      1. BaseDao

         1. ```java
            public abstract class BaseDao<T> {
            	public abstract void save();
            }
            ```

      2. BookDao

         1. ```java
            @Repository
            public class BookDao extends BaseDao<Book>{
            	@Override
            	public void save() {
            		// TODO Auto-generated method stub
            		System.out.println("BookDao....保存图书。。。");
            	}
            }
            ```

      3. UserDao

         1. ```java
            @Repository
            public class UserDao extends BaseDao<User>{
            	@Override
            	public void save() {
            		// TODO Auto-generated method stub
            		System.out.println("UserDao...保存用户....");
            	}
            }
            ```

      4. BaseService

         1. ```java
            public class BaseService<T> {
            	@Autowired
            	private BaseDao<T> baseDao;
            	
            	public void save(){
            		System.out.println("自动注入的dao："+baseDao);
            		baseDao.save();
            	}
            }
            ```

      5. BookService

         1. ```java
            @Service
            public class BookService extends BaseService<Book>{}
            ```

      6. UserService

         1. ```java
            @Service
            public class UserService extends BaseService<User>{}
            ```

   2. xml

      1. ```xml
         <?xml version="1.0" encoding="UTF-8"?>
         <beans xmlns="http://www.springframework.org/schema/beans"
                xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
                xmlns:context="http://www.springframework.org/schema/context"
                xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd">
             <context:component-scan base-package="pers.spring4.day03.n1_ioc_annotaion"/>
         </beans>
         ```

   3. test

      1. ```java
         public class IOCTest {
         	@Test
         	public void test() {
         		ApplicationContext ioc = new ClassPathXmlApplicationContext("springconfig_n1.xml");
         		BookService bookService = ioc.getBean(BookService.class);
         		UserService userService = ioc.getBean(UserService.class);
         		
         		bookService.save();
         		userService.save();
         		//父类的类型：pers.spring4.day03.n1_ioc_annotaion.service.BaseService
         		//带泛型的父类类型：pers.spring4.day03.n1_ioc_annotaion.service.BaseService<pers.spring4.day03.n1_ioc_annotaion.bean.Book>
         		//Spring中可以使用带泛型的父类类型来确定这个子类的类型
         System.out.println(bookService.getClass().getGenericSuperclass());
         		
         		//ioc是一个容器，帮我们管理所有的组件；
         		//1、依赖注入；@Autowired；自动赋值
         		//2、某个组件要使用Spring提供的更多（IOC、AOP）必须加入到容器中；
         		//体会：
         		//1、容器启动。创建所有单实例bean
         		//2、autowired自动装配的时候，是从容器中找这些符合要求的bean
         		//3、ioc.getBean("bookServlet")；也是从容器中找到这个bean；
         		//4、容器中包括了所有的bean；
         		//5、调试spring的源码，容器到底是什么？其实就是一个map；
         		//6、这个map中保存所有创建好的bean，并提供外界获取功能...
         		//7、探索，单实例的bean都保存到哪个map中了。【源码-扩展】
         		//8、源码调试的思路；
         		//	从helloworld开始的；给helloworld每一个关键步骤打上断点。进去看里面都做了什么工作？
         		//		怎么知道哪些方法都是干什么的
         		//    1、翻译这个方法是干什么？
         		//	  2、放行这个方法，看控制台,看debug的每一个变量的变化
         		//    3、看方法注释；
         		//  学到的一点：1）、规范注释，   2）、规范方法名和类名;
         	}
         }
         ```

# AOP

## 1.概念

### 1.什么是 AOP

1. 面向切面编程（方面），利用 AOP 可以对业务逻辑的各个部分进行隔离，从而使得 业务逻辑各部分之间的耦合度降低，提高程序的可重用性，同时提高了开发的效率
2. 通俗描述：不通过修改源代码方式，在主干功能里面添加新功能 
3. 例子
   1. 我们希望的是
      1. 业务逻辑：（核心功能）；日志模块；在核心功能运行期间，自己动态的加上

      2. 运行的时候，日志功能可以加上

      3. 可以使用动态代理来将日志代码动态的在目标方法执行前后先进行执行

         * 被代理类

           * ```java
             //接口
             package pers.spring4.day03.n2_aop.inter;
             
             public interface Calculator {
                 Double getSum(Double a,Double b);
             }
             ```

           * ```java
             //bean
             public class CalculatorImpl implements Calculator{
                 @Override
                 public Double getSum(Double a, Double b) {
                     System.out.println("CalculatorImpl getSum");
                     // TODO Auto-generated method stub
                     return a + b;
                 }
             }
             ```

         * 代理类

           * ```java
             public class CalculatorProxy {
                 public static<T> T getProxy(final T obj){
                     //方法执行器帮我们的目标对象执行目标方法
                     InvocationHandler ih = new InvocationHandler(){
                         /**
                          * @param proxy 代理对象
                          * @param method 当前将要执行的目标对象的方法
                          * @param args 目标方法调用时传入的参数
                          */
                         @Override
                         public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                             Object result = null;
                             try {
                                 System.out.println("前置通知");
                                 //利用反射执行目标方法
                                 //目标方法执行后的返回值
                                 result = method.invoke(obj, args);
                                 System.out.println("后置通知");
                             } catch (Exception e) {
                                 //TODO: handle exception
                                 e.printStackTrace();
                                 System.out.println("异常通知");
                             }finally{
                                 System.out.println("最终通知");
                             }
                             return result;
                         }
                     };
                     Class<?>[] interfaces = obj.getClass().getInterfaces();
                     ClassLoader classLoader = obj.getClass().getClassLoader();
             
                     //创建代理对象
                     Object proxy = Proxy.newProxyInstance(classLoader, interfaces, ih);
                     return (T)proxy;
                 }
             }
             ```

         * 测试

           * ```java
             public class AopTest {
                 @Test
                 public void testProxy(){
                     Calculator proxy = CalculatorProxy.getProxy(new CalculatorImpl());
                     Double sum = proxy.getSum(1.0, 2.0);
                     System.out.println(sum);
                 }
             }
             ```

## 2.底层原理

### 1.AOP 底层使用动态代理

#### 1.有接口情况，使用 JDK 动态代理

* 创建接口实现类代理对象，增强类的方法

#### 2.没有接口情况，使用 CGLIB 动态代理

* 创建子类的代理对象，增强类的方法

## 3. JDK 动态代理

### 1.使用 JDK 动态代理，使用 Proxy 类里面的方法创建代理对象

#### 1.newProxyInstance 方法

* static Object newProxyInstance (ClassLoader loader,类<?>[] interfaces,InvocationHandler h)
  * 有三个参数：
    * 类加载器
    * 增强方法所在的类，这个类实现的接口，支持多个接口
    * 实现这个接口 InvocationHandler，创建代理对象，写增强的部分

### 2.编写 JDK 动态代理代码

#### 1.创建接口，定义方法

* ```java
  public interface UserDao {
      public String update(String id);
      public int add(int a,int b);
  }
  ```

#### 2.创建接口实现类，实现方法

* ```java
  public class UserDaoImpl implements UserDao {
  
      @Override
      public String update(String id) {
          return id;
      }
  
      @Override
      public int add(int a, int b) {
          return a + b;
      }
  }
  ```

#### 3.使用 Proxy 类创建接口代理对象

* 代理类

  * ```java
    public class UserDaoProxy implements InvocationHandler {
        //1.把被代理的对象传递进来
        private Object obj;
        //使用有参构造传递
        public UserDaoProxy(Object obj) {
            this.obj = obj;
        }
    
        //2.增强的逻辑
        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            //方法之前
            System.out.println("方法之前执行!" + method.getName()+"传入的参数: "+ Arrays.toString(args));
            //被增强的方法执行
            Object result = method.invoke(obj, args);
            //方法之后
            System.out.println("方法之后执行!当前对象:" + obj);
            return result;
        }
    }
    
    ```

* 使用代理类创建对象

  * ```java
    @Test
    public void testProxy(){
        //创建接口实现类代理对象
        Class[] interfaces = {UserDao.class};
        //被代理类对象
        UserDao userDao = new UserDaoImpl();
        //通过newProxyInstance方法获取对象
        UserDao userDaoProxy = (UserDao) 
    Proxy.newProxyInstance(TestSpring5.class.getClassLoader(), interfaces, new UserDaoProxy(userDao));
        System.out.println(userDaoProxy.add(1, 2));
        System.out.println(userDaoProxy.update("any"));
    }
    ```

## 4.术语

### 1.横切关注点

* 从每个方法中抽取出来的同一类非核心业务

### 2.切面(Aspect)

* 封装横切关注点信息的类，每个关注点体现为一个通知方法

### 3.通知(Advice)

1. 实际增强的逻辑部分称为通知(增强)
2. 通知有多种类型
   1. @Before: 前置通知, 在方法执行之前执行
   2. @After: 后置通知, 在方法执行之后执行 。
   3. @AfterRunning: 返回通知, 在方法返回结果之后执行
   4. @AfterThrowing: 异常通知, 在方法抛出异常之后
   5. @Around: 环绕通知, 围绕着方法执行

### 4.目标(Target)

* 被通知的对象

### 5.代理(Proxy)

* 向目标对象应用通知之后创建的代理对象

### 6.连接点(Joinpoint)

* 横切关注点在程序代码中的具体体现，对应程序执行的某个特定位置。例如：类某个方法调用前、调用后、方法捕获到异常后等

## 5.准备工作

### 1.Spring 框架一般都是基于 AspectJ 实现 AOP 操作

* AspectJ 不是 Spring 组成部分，独立 AOP 框架，一般把 AspectJ 和 Spirng 框架一起使 用，进行 AOP 操作

### 2.基于 AspectJ 实现 AOP 操作

1. 基于 xml 配置文件实现
2. 基于注解方式实现（经常使用）

### 3.在项目工程里面引入 AOP 相关依赖

* 导入JAR包
  * aopalliance.jar
  * aspectj.weaver.jar
  * spring-aspects.jar
* 引入aop名称空间
* 配置
  * `<aop:aspectj-autoproxy>`
  * 当Spring IOC容器侦测到bean配置文件中的`<aop:aspectj-autoproxy>`元素时，会自动为与AspectJ切面匹配的bean创建代理

### 4.切入点表达式

#### 1.切入点表达式作用

* 知道对哪个类里面的哪个方法进行增强

#### 2.语法结构

* `execution([权限修饰符] [返回类型] [类全路径] [方法名称]([参数列表]) )`

  * 权限修饰符

    * ```java
      //1.public private等
      //2.* 表示任意修饰符
      ```

  * 类全路径

    * ```java
      // * 表示任意类
      ```

  * 方法名称

    * ```java
      // * 表示任意方法
      ```

  * 参数列表

    * ```java
      // .. 表示任意参数列表
      ```

* 例子

  1. 对 com.atguigu.dao.BookDao 类里面的 add 进行增强

     * ```java
       execution(* com.atguigu.dao.BookDao.add(..))
       ```

  2. 对 com.atguigu.dao.BookDao 类里面的所有的方法进行增强

     * ```java
       execution(* com.atguigu.dao.BookDao.* (..))
       ```

  3. 对 com.atguigu.dao 包里面所有类，类里面所有方法进行增强

     * ```java
       execution(* com.atguigu.dao.*.* (..))
       ```



## 6.AOP 操作（AspectJ 注解方式）

### 1.创建类，在类里面定义方法

* ```java
  public class CalculatorImpl implements Calculator{
      @Override
      public Double getSum(Double a, Double b) {
          System.out.println("CalculatorImpl getSum");
          // TODO Auto-generated method stub
          return a + b;
      }
  }
  ```

### 2.创建增强类（编写增强逻辑）

#### 1.在增强类里面，创建方法，让不同方法代表不同通知类型

* ```java
  public class CalculatorAopProxy {
      public void before(JoinPoint joinPoint){
          System.out.println("前置通知: " + joinPoint);
      }
  }
  ```

### 3.进行通知的配置

#### 1.在 spring 配置文件中，开启注解扫描

* ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xmlns:p="http://www.springframework.org/schema/p"
         xmlns:context="http://www.springframework.org/schema/context"
         xmlns:aop="http://www.springframework.org/schema/aop"
         xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                             http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd
                             http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd">
      <!--开启扫描-->
      <context:component-scan base-package="pers"/>
  </beans>
  ```

#### 2.使用注解创建 User 和 UserProxy 对象

* ```java
  @Component
  public class CalculatorImpl {}
  @Component
  public class CalculatorAopProxy {}
  ```

#### 3.在增强类上面添加注解 @Aspect

* ```java
  @Component
  @Aspect
  public class CalculatorAopProxy {}
  ```

#### 4.在 spring 配置文件中开启生成代理对象

* ```xml
  <?xml version="1.0" encoding="UTF-8"?>
  <beans xmlns="http://www.springframework.org/schema/beans"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xmlns:p="http://www.springframework.org/schema/p"
         xmlns:context="http://www.springframework.org/schema/context"
         xmlns:aop="http://www.springframework.org/schema/aop"
         xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                             http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd
                             http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd">
      <!--开启扫描-->
      <context:component-scan base-package="pers"/>
      <!--开启Aspect生成代理对象-->
      <aop:aspectj-autoproxy/>
  </beans>
  ```

### 4.配置不同类型的通知

* 在增强类的里面，在作为通知方法上面添加通知类型注解，使用切入点表达式配置

  * ```java
    /*
     * 切入点表达式的写法；
     * 固定格式： execution(访问权限符  返回值类型  方法全类名(参数表))
     *   
     * 通配符：
     * 		*：
     * 			1）匹配一个或者多个字符:execution(public int com.atguigu.impl.MyMath*r.*(int, int))
     * 			2）匹配任意一个参数：第一个是int类型，第二个参数任意类型；（匹配两个参数）
     * 				execution(public int com.atguigu.impl.MyMath*.*(int, *))
     * 			3）只能匹配一层路径
     * 			4）权限位置*不能；权限位置不写就行；public【可选的】
     * 		..：
     * 			1）匹配任意多个参数，任意类型参数
     * 			2）匹配任意多层路径:
     * 				execution(public int com.atguigu..MyMath*.*(..));
     * 
     * 记住两种；
     * 最精确的：execution(public int com.atguigu.impl.MyMathCalculator.add(int,int))
     * 最模糊的：execution(* *.*(..))：千万别写；
     * 
     * &&”、“||”、“!
     * 
     * &&：我们要切入的位置满足这两个表达式
     * 	MyMathCalculator.add(int,double)
     * execution(public int com.atguigu..MyMath*.*(..))&&execution(* *.*(int,int))
     * 
     * 
     * ||:满足任意一个表达式即可
     * execution(public int com.atguigu..MyMath*.*(..))&&execution(* *.*(int,int))
     * 
     * !：只要不是这个位置都切入
     * !execution(public int com.atguigu..MyMath*.*(..))
     * 
     * 告诉Spring这个result用来接收返回值：
     * 	returning="result"；
     * 
     * 我们可以在通知方法运行的时候，拿到目标方法的详细信息
     * 1）只需要为通知方法的参数列表上写一个参数：
     *      JoinPoint joinPoint：封装了当前目标方法的详细信息
     * 2）告诉Spring哪个参数是用来接收异常
     *      throwing="exception"：告诉Spring哪个参数是用来接收异常
     * 3）Exception exception:指定通知方法可以接收哪些异常
     * 
     */
    @Component
    @Aspect
    public class CalculatorAopProxy {
        @Before("execution(* pers.spring4.day03.n2_aop.bean.CalculatorImpl.*(..))")
        public void before(JoinPoint joinPoint){
            System.out.println("前置通知: " + joinPoint);
        }
    
        @AfterReturning(value = "execution(* pers.spring4.day03.n2_aop.bean.CalculatorImpl.*(..))",returning = "result")
        public void afterRunning(JoinPoint joinPoint,Object result){
            System.out.println("返回通知: " + joinPoint + ",result: " + result);
        }
    
        @After("execution(* pers.spring4.day03.n2_aop.bean.CalculatorImpl.*(..))")
        public void after(JoinPoint joinPoint){
            System.out.println("后置通知: " + joinPoint);
        }
    
        @AfterThrowing(value = "execution(* pers.spring4.day03.n2_aop.bean.CalculatorImpl.*(..))",throwing = "exception")
        public void afterThrowing(JoinPoint joinPoint,Exception exception){
            System.out.println("异常通知: " + joinPoint + ",exception: " + exception.getMessage());
        }
    
        /**
    	 * @throws Throwable 
    	 * @Around：环绕	:是Spring中强大的通知；
    	 * @Around：环绕:动态代理；
    	 * 	try{
    	 * 			//前置通知
    	 * 			method.invoke(obj,args);
    	 * 			//返回通知
    	 * 	}catch(e){
    	 * 			//异常通知
    	 *  }finally{
    	 * 			//后置通知
    	 * 	}
    	 * 		
    	 * 	四合一通知就是环绕通知；
    	 * 	环绕通知中有一个参数：	ProceedingJoinPoint pjp
    	 * 
    	 *环绕通知：是优先于普通通知执行，执行顺序；
    	 *
    	 *[普通前置]
    	 *{
    	 *	try{
    	 *		环绕前置
    	 *		环绕执行：目标方法执行
    	 *		环绕返回
    	 *	}catch(){
    	 *		环绕出现异常
    	 *	}finally{
    	 *		环绕后置
    	 *	}
    	 *}
    	 *
    	 *
    	 *[普通后置]
    	 *[普通方法返回/方法异常]
    	 *新的顺序：
    	 *		（环绕前置---普通前置）----目标方法执行----环绕正常返回/出现异常-----环绕后置----普通后置---普通返回或者异常
    	 *注意：
    	 */
        @Around("execution(* pers.spring4.day03.n2_aop.bean.CalculatorImpl.*(..))")
        public Object around(ProceedingJoinPoint pjp) throws Throwable{
            //获取调用目标方法时传入的参数
            Object[] args = pjp.getArgs();
            //获取目标方法名
            String methodName = pjp.getSignature().getName();
    
            Object proceed = null;
            try {
                System.out.println("环绕前置通知: " + methodName + "方法开始");
    
                //利用反射调用目标方法:method.invoke(obj,args)
                proceed = pjp.proceed(args);
    
                System.out.println("环绕返回通知: " + methodName + "方法返回");
            } catch (Exception e) {
                System.out.println("环绕异常通知: " + methodName + "方法异常: " + e.getMessage());
    
                //为了让外界能知道这个异常，这个异常一定抛出去
                throw new RuntimeException(e);
            }finally{
                System.out.println("环绕后置通知: " + methodName + "方法结束");
            }
    
            //调用方法后的返回值需要返回出去
            return proceed;
        }
    }
    ```

### 5.相同的切入点抽取

* ```java
  @Component
  @Aspect
  public class CalculatorAopProxy {
      //相同切入点抽取
      //通过@Pointcut注解将一个切入点声明成简单的方法
      //切入点的方法体通常是空的，因为将切入点定义与应用程序逻辑混在一起是不合理的
      @Pointcut(value = "execution(* pers.spring4.day03.n2_aop.bean.CalculatorImpl.*(..))")
      public void point(){}
  
      //其他通知可以通过方法名称引入该切入点
      @Before(value = "point()")
      public void before(JoinPoint joinPoint){
          System.out.println("前置通知: " + joinPoint);
      }
  }
  ```

### 6.有多个增强类多同一个方法进行增强，设置增强类优先级

* 在增强类上面添加注解 @Order(数字类型值)，数字类型值越小优先级越高

  * ```java
    //先执行
    @Component
    @Aspect
    @Order(1)
    public class UserProxy {}
    
    //后执行
    @Component
    @Aspect
    @Order(2)
    public class AnotherUserProxy {}
    ```

### 7.完全使用注解开发 

* 创建配置类，不需要创建 xml 配置文件

  * ```java
    //配置类
    @Configuration//标识为配置类
    @ComponentScan(basePackages = {"pers.spring4.day03.n2_aop"})//开启包扫描
    @EnableAspectJAutoProxy(proxyTargetClass = true)//开启基于注解的AOP功能,允许代理对象使用被代理接口的子类承接
    public class ConfigAop {}
    ```
    
  * ```java
    //测试使用
    @Test
    public void testAopAnnoWithoutXml(){
        //完全注解开发使用AnnotationConfigApplicationContext读取配置类的class类对象
        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(ConfigAop.class);
        Calculator calculator = applicationContext.getBean(Calculator.class);
        Double sum = calculator.getSum(1.0,2.0);
        System.out.println("testAop:" + sum);
    }
    ```

## 7.AOP 操作（AspectJ 配置文件）

### 1.创建两个类，增强类和被增强类，创建方法

* 被增强类

  * ```java
    public class Book {
        public void buy(){
            System.out.println("buy...");
        }
    }
    ```

* 增强类

  * ```java
    public class BookProxy {
        public void before(){
            System.out.println("before...");
        }
    }
    ```

### 2.在 spring 配置文件中创建两个类对象

* 配置文件

  * ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xmlns:p="http://www.springframework.org/schema/p"
           xmlns:context="http://www.springframework.org/schema/context"
           xmlns:aop="http://www.springframework.org/schema/aop"
           xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                               http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd
                               http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd">
        <!--创建Bean对象-->
        <bean id="book" class="pers.aop_xml.Book"></bean>
        <bean id="bookProxy" class="pers.proxy.BookProxy"></bean>
    </beans>
    ```

### 3.在 spring 配置文件中配置切入点

* 配置文件

  * ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xmlns:p="http://www.springframework.org/schema/p"
           xmlns:context="http://www.springframework.org/schema/context"
           xmlns:aop="http://www.springframework.org/schema/aop"
           xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                               http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd
                               http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd">
        <!--创建Bean对象-->
        <bean id="book" class="pers.aop_xml.Book"></bean>
        <bean id="bookProxy" class="pers.proxy.BookProxy"></bean>
        <!--配置AOP增强-->
        <aop:config>
            <!--配置切入点:指定被增强类的方法-->
            <aop:pointcut id="Book_buy" expression="execution(* pers.aop_xml.Book.buy(..))"/>
            <!--配置切面:指定增强类-->
            <aop:aspect ref="bookProxy">
                <!--配置增强类的指定方法作用的切入点-->
                <aop:before method="before" pointcut-ref="Book_buy"/>
            </aop:aspect>
        </aop:config>
    </beans>
    ```
    
  * 配置细节
  
    * 所有的Spring AOP配置都必须定义在`<aop:config>`元素内部
    * 对于每个切面而言，都要创建一个`<aop:aspect>`元素来为具体的切面实现引用后端bean实例
    * 切面bean必须有一个标识符，供`<aop:aspect>`元素引用
    * 声明切入点
      * 切入点使用`<aop:pointcut>`元素声明
      * 切入点必须定义在`<aop:aspect>`元素下(只对当前切面有效)，或者直接定义在`<aop:config>`元素下(对所有切面都有效)
      * 基于XML的AOP配置不允许在切入点表达式中用名称引用其他切入点
    * 声明通知
      * 在aop名称空间中，每种通知类型都对应一个特定的XML元素
      * 通知元素需要使用`<pointcut-ref>`来引用切入点，或用`<pointcut>`直接嵌入切入点表达式
      * method属性指定切面类中通知方法的名称

# JdbcTemplate

## 1.JdbcTemplate(概念和准备)

### 1.、什么是 JdbcTemplate

* Spring 框架对 JDBC 进行封装，使用 JdbcTemplate 方便实现对数据库操作

### 2.准备工作

#### 1.引入相关 jar 包

#### 2.在 spring 配置文件配置数据库连接池

#### 3.配置 JdbcTemplate 对象，注入 DataSource

* 配置文件

  * ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xmlns:p="http://www.springframework.org/schema/p"
           xmlns:context="http://www.springframework.org/schema/context"
           xmlns:aop="http://www.springframework.org/schema/aop"
           xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                               http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd
                               http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd">
        <!--引入外部属性文件-->
        <context:property-placeholder location="classpath:jdbc.properties"/>
        <!--配置连接池-->
        <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
            <property name="driverClassName" value="${prop.driverClassName}"/>
            <property name="url" value="${prop.url}"/>
            <property name="username" value="${prop.username}"/>
            <property name="password" value="${prop.password}"/>
        </bean>
        <!--JdbcTemplate对象-->
        <bean id="jdbcTemplate对象" class="org.springframework.jdbc.core.JdbcTemplate">
            <!--注入DataSource-->
            <property name="dataSource" ref="dataSource"/>
        </bean>
    </beans>
    ```

#### 4.创建 service 类，创建 dao 类，在 dao 注入 jdbcTemplate 对象

*  配置文件

  * ```xml
    Service<!-- 组件扫描 -->
    <context:component-scan base-package="pers"></context:component-scan>
    ```

* Service

  * ```java
    @Service
    public class UserService {
        //注入dao
        @Autowired
        private UserDao userDao;
    }
    
    ```

* Dao

  * ```java
    @Repository
    public class UserDaoImpl implements UserDao {
        //注入JdbcTemplate
        @Autowired
        public JdbcTemplate jdbcTemplate;
    }
    ```


## 2.JdbcTemplate 操作数据库（添加）

### 1.对应数据库创建实体类

* ```java
  public class User {
      private String userId;
      private String username;
      private String ustatus;
  
      public void setUserId(String userId) {
          this.userId = userId;
      }
  
      public void setUsername(String username) {
          this.username = username;
      }
  
      public void setUstatus(String ustatus) {
          this.ustatus = ustatus;
      }
  
      public String getUserId() {
          return userId;
      }
  
      public String getUsername() {
          return username;
      }
  
      public String getUstatus() {
          return ustatus;
      }
  }
  ```

### 2.编写 service 和 dao

1. 在 dao 进行数据库添加操作 

2. 调用 JdbcTemplate 对象里面 update 方法实现添加操作

3. sevice代码

   * ```java
     @Service
     public class UserService {
         //注入dao
         @Autowired
         private UserDao userDao;
     
         public void addUser(User user){
             userDao.add(user);
         }
     }
     ```

4. dao代码

   * ```java
     @Repository
     public class UserDaoImpl implements UserDao {
         //注入JdbcTemplate
         @Autowired
         public JdbcTemplate jdbcTemplate;
     
     
         @Override
         public void add(User user) {
             //创建sql语句
             String sql = "insert into t_user values(null,?,?)";
             //调用方法实现
             int update = jdbcTemplate.update(sql, user.getUsername(), user.getUstatus());
             System.out.println(update);
         }
     }
     ```

### 3.测试类

* ```java
  @Test
  public void testJdbcTemplateAdd(){
      ApplicationContext context = new ClassPathXmlApplicationContext("01_jdbcTemplateBean.xml");
      UserService userService = context.getBean("userService", UserService.class);
  
      userService.addUser(new User("Vivy","alive"));
  }
  ```

## 3.JdbcTemplate 操作数据库（修改和删除）

### 1.修改

* ```java
  //修改
  @Override
  public void updateUser(User user) {
      //创建sql语句
      String sql = "update t_user set username=?,ustatus=? where user_id=?";
      //调用方法实现
      int update = jdbcTemplate.update(sql, user.getUsername(), user.getUstatus(),user.getUserId());
      System.out.println(update);
  }
  ```

### 2.删除

* ```java
  //删除
  @Override
  public void deleteUser(String id) {
      //创建sql语句
      String sql = "delete from t_user where user_id=?";
      //调用方法实现
      int update = jdbcTemplate.update(sql, id);
      System.out.println(update);
  }
  ```

## 4.JdbcTemplate 操作数据库（查询返回某个值）

1. 查询表里面有多少条记录，返回是某个值

2. 使用 JdbcTemplate 实现查询返回某个值代码

   * queryForObject(String sql, Class<T> requiredType)
     * 第一个参数：sql 语句 
     * 第二个参数：返回类型 Class

3. dao代码

   * ```java
         //查询表中某个值
         public int selectCount(){
             //创建sql语句
             String sql = "select Count(*) from t_user";
             //调用方法实现
             int count = jdbcTemplate.queryForObject(sql,Integer.class);
             return count;
         }
     ```

4. 测试类

   * ```java
     @Test
     public void testJdbcTemplateQuerySingle(){
         ApplicationContext context = new ClassPathXmlApplicationContext("01_jdbcTemplateBean.xml");
         UserService userService = context.getBean("userService", UserService.class);
     
         int count = userService.findCount();
         System.out.println(count);
     }
     ```

## 5.JdbcTemplate 操作数据库（查询返回对象）

1. 场景：查询用户详情

2. JdbcTemplate 实现查询返回对象

   * queryForObject(String sql, RowMapper<T> rowMapper, @Nullable Object... args)
     * 第一个参数：sql 语句 
     *  第二个参数：RowMapper 是接口，针对返回不同类型数据，使用这个接口里面实现类完成 数据封装 
     *  第三个参数：sql 语句值

3. dao代码

   * ```java
     //查询表中某个对象
     @Override
     public User findObject(String id) {
         //创建sql语句
         String sql = "select * from t_user where user_id=?";
         //调用方法实现
         User user = jdbcTemplate.queryForObject(sql,new BeanPropertyRowMapper<User>(User.class),id);
         return user;
     }
     ```

## 6.JdbcTemplate 操作数据库（查询返回集合）

1. 场景：查询用户列表分页… 

2. 调用 JdbcTemplate 方法实现查询返回集合 

   * query(String sql, RowMapper<T> rowMapper, @Nullable Object... args)
     * 第一个参数：sql 语句 
     *  第二个参数：RowMapper 是接口，针对返回不同类型数据，使用这个接口里面实现类完成 数据封装 
     *  第三个参数：sql 语句值

3. dao代码

   * ```java
     //查询表中所有对象
     @Override
     public List<User> findAllUser() {
         //创建sql语句
         String sql = "select * from t_user";
         //调用方法实现
         List<User> list = jdbcTemplate.query(sql, new BeanPropertyRowMapper<User>(User.class));
         return list;
     }
     ```

## 7.JdbcTemplate 操作数据库（批量操作）

### 1.批量操作：操作表里面多条记录

### 2.JdbcTemplate 实现批量添加操作

* int[] batchUpdate(String sql, List<Object[]> batchArgs)

  * 第一个参数：sql 语句 
  * 第二个参数：List 集合，添加多条记录数据

* dao代码

  * ```java
    //批量添加
    @Override
    public void batchAddUser(List<Object[]> batchArgs) {
        //创建sql语句
        String sql = "insert into t_user values(null,?,?)";
        //调用方法实现
        int[] ints = jdbcTemplate.batchUpdate(sql, batchArgs);
        System.out.println(Arrays.toString(ints));
    }
    ```

* 测试类

  * ```java
    //批量添加
    @Test
    public void testJdbcTemplateBatchAdd(){
        ApplicationContext context = new ClassPathXmlApplicationContext("01_jdbcTemplateBean.xml");
        UserService userService = context.getBean("userService", UserService.class);
    
        List<Object[]> batchArgs = new ArrayList<>();
        Object[] o1 = {"Vivy","alive"};
        Object[] o2 = {"Matumoto","alive"};
        Object[] o3 = {"Alisa","dead"};
        batchArgs.add(o1);
        batchArgs.add(o2);
        batchArgs.add(o3);
    
        userService.batchAdd(batchArgs);
    }
    ```

### 3.JdbcTemplate 实现批量修改操作

* dao代码

  * ```java
    //批量修改
    @Override
    public void batchUpdateUser(List<Object[]> batchArgs) {
        //创建sql语句
        String sql = "update t_user set username=?,ustatus=? where user_id=?";
        //调用方法实现
        int[] ints = jdbcTemplate.batchUpdate(sql, batchArgs);
        System.out.println(Arrays.toString(ints));
    }
    ```

* 测试类

  * ```java
    //批量修改
    @Test
    public void testJdbcTemplateBatchUpdate(){
        ApplicationContext context = new ClassPathXmlApplicationContext("01_jdbcTemplateBean.xml");
        UserService userService = context.getBean("userService", UserService.class);
    
        List<Object[]> batchArgs = new ArrayList<>();
        Object[] o1 = {"Vivy","dead","3"};
        Object[] o2 = {"Matumoto","dead","4"};
        Object[] o3 = {"Alisa","alive","5"};
        batchArgs.add(o1);
        batchArgs.add(o2);
        batchArgs.add(o3);
    
        userService.batchUpdate(batchArgs);
    }
    ```

### 4.JdbcTemplate 实现批量删除操作

* dao代码

  * ```java
    //批量删除
    @Override
    public void batchDeleteUser(List<Object[]> batchArgs) {
        //创建sql语句
        String sql = "delete from t_user where user_id=?";
        //调用方法实现
        int[] ints = jdbcTemplate.batchUpdate(sql, batchArgs);
        System.out.println(Arrays.toString(ints));
    }
    ```

* 测试类

  * ```java
    //批量删除
    @Test
    public void testJdbcTemplateBatchDelete(){
        ApplicationContext context = new ClassPathXmlApplicationContext("01_jdbcTemplateBean.xml");
        UserService userService = context.getBean("userService", UserService.class);
    
        List<Object[]> batchArgs = new ArrayList<>();
        Object[] o1 = {"3"};
        Object[] o2 = {"4"};
        batchArgs.add(o1);
        batchArgs.add(o2);
    
        userService.batchDelete(batchArgs);
    }
    ```

# Transaction

## 1.事务操作（事务概念）

### 1.什么事务

1. 事务是数据库操作最基本单元，逻辑上一组操作，要么都成功，如果有一个失败所有操 作都失败
2. 典型场景：银行转账
   * lucy 转账 100 元 给 mary
   *  lucy 少 100，mary 多 100

###  2、事务四个特性（ACID）

1. 原子性
2. 一致性 
3. 隔离性
4. 持久性

## 2.事务操作（搭建事务操作环境）

### 1.创建数据库表，添加记录

### 2.创建 service，搭建 dao，完成对象创建和注入关系

* service 注入 dao，在 dao 注入 JdbcTemplate，在 JdbcTemplate 注入 DataSource

* service

  * ```java
    @Service
    public class UserService {
        //注入dao
        @Autowired
        private UserDao userDao;
    }
    ```

* dao

  * ```java
    @Repository
    public class UserDaoImpl implements UserDao {
        //注入JdbcTemplate
        @Autowired
        public JdbcTemplate jdbcTemplate;
    }
    ```

* jdbcTemplate

  * ```xml
    <?xml version="1.0" encoding="UTF-8"?>
    <beans xmlns="http://www.springframework.org/schema/beans"
           xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
           xmlns:p="http://www.springframework.org/schema/p"
           xmlns:context="http://www.springframework.org/schema/context"
           xmlns:aop="http://www.springframework.org/schema/aop"
           xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                               http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd
                               http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd">
        <!--引入外部属性文件-->
        <context:property-placeholder location="classpath:jdbc.properties"/>
        <!--配置连接池-->
        <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
            <property name="driverClassName" value="${prop.driverClassName}"/>
            <property name="url" value="${prop.url}"/>
            <property name="username" value="${prop.username}"/>
            <property name="password" value="${prop.password}"/>
        </bean>
        <!--JdbcTemplate对象-->
        <bean id="jdbcTemplate对象" class="org.springframework.jdbc.core.JdbcTemplate">
            <!--注入DataSource-->
            <property name="dataSource" ref="dataSource"/>
        </bean>
        <!-- 组件扫描 -->
        <context:component-scan base-package="pers"></context:component-scan>
    </beans>
    ```

### 3.在 dao 创建两个方法：多钱和少钱的方法，在 service 创建方法（转账的方法）

* dao

  * ```java
    //收钱
    @Override
    public void addMoney() {
        String sql = "update t_account set money=money+? where username=?";
        jdbcTemplate.update(sql,100,"Vivy");
    }
    //付钱
    @Override
    public void reduceMoney() {
        String sql = "update t_account set money=money-? where username=?";
        jdbcTemplate.update(sql,100,"Matumoto");
    }
    ```

* service

  * ```java
    //转账
    public void transferAccount(){
        //Matumoto-100
        userDao.reduceMoney();
        //Vivy+100
        userDao.addMoney();
    }
    ```

### 4.上面代码，如果正常执行没有问题的，但是如果代码执行过程中出现异常，有问题

#### 1.上面问题如何解决呢？

* 使用事务进行解决

#### 2.事务操作过程说明

* service

  * ```java
    public void transferAccount(){
        try {
            //1.开启事务
            //2.进行业务操作
            //Matumoto-100
            userDao.reduceMoney();
            //Vivy+100
            userDao.addMoney();
            //3.没有发生异常,提交事务
        } catch (Exception e) {
            //4.出现异常,事务回滚
            e.printStackTrace();
        }
    }
    ```

## 3.事务操作（Spring 事务管理介绍）

1. 事务添加到 JavaEE 三层结构里面 Service 层（业务逻辑层）

2. 在 Spring 进行事务管理操作 
   * 编程式事务管理
   * 声明式事务管理（常用）

3. 声明式事务管理 
   * 基于注解方式（常用）
   * 基于 xml 配置文件方式 

4. 在 Spring 进行声明式事务管理，底层使用 AOP 原理 

5. Spring 事务管理 API
   * 提供一个接口，代表事务管理器，这个接口针对不同的框架提供不同的实现类
     * PlatformTransactionManager接口
       * DataSourceTransactionManager接口

## 4.事务操作（注解声明式事务管理）

### 1.在 spring 配置文件配置事务管理器

* ```xml
  <!--创建事务管理器-->
  <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
      <!--注入数据源-->
      <property name="dataSource" ref="dataSource"/>
  </bean>
  ```

### 2.在 spring 配置文件，开启事务注解

1. 在 spring 配置文件引入名称空间 tx

   * ```xml
     <beans xmlns:tx="http://www.springframework.org/schema/tx"
     xsi:schemaLocation="http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx.xsd"></beans>
     ```

2. 开启事务注解

   * ```xml
     <!--开启事务注解-->
     <tx:annotation-driven transaction-manager="transactionManager"/>
     ```

3. xml代码

   * ```xml
     <?xml version="1.0" encoding="UTF-8"?>
     <beans xmlns="http://www.springframework.org/schema/beans"
            xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
            xmlns:p="http://www.springframework.org/schema/p"
            xmlns:context="http://www.springframework.org/schema/context"
            xmlns:aop="http://www.springframework.org/schema/aop"
            xmlns:tx="http://www.springframework.org/schema/tx"
            xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
                                http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context.xsd
                                http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop.xsd
                                http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx.xsd">
         <!--引入外部属性文件-->
         <context:property-placeholder location="classpath:jdbc.properties"/>
         <!--配置连接池-->
         <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
             <property name="driverClassName" value="${prop.driverClassName}"/>
             <property name="url" value="${prop.url}"/>
             <property name="username" value="${prop.username}"/>
             <property name="password" value="${prop.password}"/>
         </bean>
         <!--JdbcTemplate对象-->
         <bean id="jdbcTemplate对象" class="org.springframework.jdbc.core.JdbcTemplate">
             <!--注入DataSource-->
             <property name="dataSource" ref="dataSource"/>
         </bean>
         <!-- 组件扫描 -->
         <context:component-scan base-package="pers"/>
     
         <!--创建事务管理器-->
         <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
             <!--注入数据源-->
             <property name="dataSource" ref="dataSource"/>
         </bean>
         <!--开启事务注解-->
         <tx:annotation-driven transaction-manager="transactionManager"/>
     </beans>
     ```

### 3.在 service 类上面（或者 service 类里面方法上面）添加事务注解

1. @Transactional，这个注解添加到类上面，也可以添加方法上面

2. 如果把这个注解添加类上面，这个类里面所有的方法都添加事务

3. 如果把这个注解添加方法上面，为这个方法添加事务

4. service代码

   * ```java
     @Service
     @Transactional//为所有方法添加事务
     public class UserService {
         //注入dao
         @Autowired
         private UserDao userDao;
     
         public void transferAccount(){
             //Matumoto-100
             userDao.reduceMoney();
             //模拟异常
             int n = 1 / 0;
             //Vivy+100
             userDao.addMoney();
         }
     }
     ```

## 5.事务操作（声明式事务管理参数配置）

### 1.在 service 类上面添加注解@Transactional，在这个注解里面可以配置事务相关参数

* ```java
  Propagation propagation() default Propagation.REQUIRED;
  
  Isolation isolation() default Isolation.DEFAULT;
  
  int timeout() default -1;
  
  boolean readOnly() default false;
  
  Class<? extends Throwable>[] rollbackFor() default {};
  
  String[] rollbackForClassName() default {};
  
  Class<? extends Throwable>[] noRollbackFor() default {};
  
  String[] noRollbackForClassName() default {};
  ```

### 2.propagation：事务传播行为

* 多事务方法之间进行调用，这个过程中事务是如何进行管理的

  * A事务方法中调用B事务方法(有事务注解)
    1. A方法有事务注解,此时是怎样的处理
    2. A方法没有事务注解,此时是怎样的处理

* 事务方法:对数据库表数据进行变化的操作(查询操作不是事务方法)

* Spring事务传播行为

  * ```java
    public enum Propagation {
        //保证多个操作在同一个事务中
        //默认值，如果外层有事务，则当前事务加入到外层事务，一块提交，一块回滚。如果外层没有事务，新建一个事务执行
        //如果A中有事务，使用A中的事务，如果A没有，B创建一个新的事务，将A的操作包含进来
        REQUIRED(0),
    
        //保证多个操作在同一个事务中
        //如果外层有事务，则加入外层事务，如果外层没有事务，则直接使用非事务方式执行。完全依赖外层的事务
        //支持事务，如果A中有事务，使用A中的事务。如果A没有事务，不使用事务
        SUPPORTS(1),
    
        //保证多个操作在同一个事务中
        //与NEVER相反，如果外层没有事务，则抛出异常
        //如果A中有事务，使用A中的事务。如果A没有事务，抛出异常
        MANDATORY(2),
        
        //保证多个操作不在同一个事务中
        //该事务传播机制是每次都会新开启一个事务，同时把外层事务挂起，当当前事务执行完毕，恢复上层事务的执行。如果外层没有事务，执行当前新开启的事务即可
        //如果A中有事务，将A的事务挂起（暂停），B创建新事务，只包含B自身操作。如果A中没有事务，B创建一个新事务,只包含B自身操作。
        REQUIRES_NEW(3),
        
        //保证多个操作不在同一个事务中
        //如果A中有事务，将A的事务挂起。不使用事务管理。
        NOT_SUPPORTED(4),
        
        //保证多个操作不在同一个事务中
        //该传播机制不支持外层事务，即如果外层有事务就抛出异常
        //如果A中有事务，报异常
        NEVER(5),
        
        //嵌套式事务
        //该传播机制的特点是可以保存状态保存点，当前事务回滚到某一个点，从而避免所有的嵌套事务都回滚，即各自回滚各自的，如果子事务没有把异常吃掉，基本还是会引起全部回滚的。
        //如果A中有事务，按照A的事务执行，执行完成后，设置一个保存点，执行B中的操作，如果没有异常，执行通过，如果有异常，可以选择回滚到最初始位置，也可以回滚到保存点
        NESTED(6);
    }
    ```

* 为service设置传播行为

  * ```java
    @Service
    @Transactional(propagation = Propagation.REQUIRES_NEW)//为所有方法添加事务
    public class UserService {
        //...
    }
    ```

### 3.ioslation：事务隔离级别

1. 事务有特性成为隔离性，多事务操作之间不会产生影响。不考虑隔离性产生很多问题 

2. 有三个读问题：脏读、不可重复读、虚（幻）读

   1. 脏读：一个未提交事务读取到另一个未提交事务的数据
   2. 不可重复读：一个未提交事务读取到另一提交事务修改数据
   3. 虚读：一个未提交事务读取到另一提交事务添加数据

3. 解决：通过设置事务隔离级别，解决读问题

   * ```java
     public enum Isolation {
         DEFAULT(-1),
         READ_UNCOMMITTED(1),//读未提交//存在 脏读,不可重复读,虚读
         READ_COMMITTED(2),//读已提交//存在 不可重复读,虚读
         REPEATABLE_READ(4),//可重复读//存在 虚读
         SERIALIZABLE(8);//串行化
     }
     ```

4. 为service设置隔离级别

   * ```java
     @Service
     @Transactional(propagation = Propagation.REQUIRES_NEW,isolation = Isolation.REPEATABLE_READ)//为所有方法添加事务
     public class UserService {
         //...
     }
     ```

### 4.timeout：超时时间

1. 事务需要在一定时间内进行提交，如果不提交则进行回滚
2. 默认值是 -1 (不超时)，设置时间以秒单位进行计算

### 5.readOnly：是否只读

1. 读：查询操作，写：添加修改删除操作
2. readOnly 默认值 false，表示可以查询，可以添加修改删除操作
3. 设置 readOnly 值是 true，设置成 true 之后，只能查询

### 6.rollbackFor：回滚

* 设置出现哪些异常进行事务回滚

### 7.noRollbackFor：不回滚

* 设置出现哪些异常不进行事务回滚

## 6.事务操作（XML 声明式事务管理）

### 1.在 spring 配置文件中进行配置

#### 1.配置事务管理器

* ```xml
  <!--创建事务管理器-->
  <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
      <!--注入数据源-->
      <property name="dataSource" ref="dataSource"/>
  </bean>
  ```

#### 2.配置通知

* ```xml
  <!--2.配置通知-->
  <tx:advice id="txadvice">
      <!--配置事务参数-->
      <tx:attributes>
          <!--指定哪种规则的方法上面添加事务-->
          <tx:method name="transferAccount" propagation="REQUIRED"/>
          <!--<tx:method name="account*"/>也可-->
      </tx:attributes>
  </tx:advice>
  ```

#### 3.配置切入点和切面

* ```xml
  <!--配置切入点和切面-->
  <aop:config>
      <!--配置切入点-->
      <aop:pointcut id="pt" expression="execution(* pers.service.UserService.*(..))"/>
      <!--配置切面-->
      <aop:advisor advice-ref="txadvice" pointcut-ref="pt"/>
  </aop:config>
  ```

## 7.事务操作（完全注解声明式事务管理）

* 创建配置类，使用配置类替代 xml 配置文件

  * ```java
    @Configuration//配置类
    @ComponentScan(basePackages = "pers")//开启组件扫描
    @EnableTransactionManagement//开启事务注解
    public class TxConfig {
        // 配置数据库连接池
        @Bean
        public DruidDataSource getDruidDataSource(){
            Properties prop = new Properties();
            try {
                prop.load(this.getClass().getClassLoader().getResourceAsStream("jdbc.properties"));
            } catch (IOException e) {
                e.printStackTrace();
            }
            DruidDataSource dataSource = new DruidDataSource();
            dataSource.setDriverClassName(prop.getProperty("prop.driverClassName"));
            dataSource.setUrl(prop.getProperty("prop.url"));
            dataSource.setUsername(prop.getProperty("prop.username"));
            dataSource.setPassword(prop.getProperty("prop.password"));
    
            return dataSource;
        }
    
        //创建JdbcTemplate对象
        @Bean
        public JdbcTemplate getJdbcTemplate(DataSource dataSource){//到ioc容器中根据类型注入
            JdbcTemplate jdbcTemplate = new JdbcTemplate();
            jdbcTemplate.setDataSource(dataSource);
    
            return jdbcTemplate;
        }
    
        //创建事务管理器
        @Bean
        public DataSourceTransactionManager getDataSourceTransactionManager(DataSource dataSource){
            DataSourceTransactionManager dataSourceTransactionManager = new DataSourceTransactionManager();
            dataSourceTransactionManager.setDataSource(dataSource);
    
            return dataSourceTransactionManager;
        }
    }
    ```

# Spring5 框架新功能

## 1.优化及核心增强

### 1.架构优化

* 整个 Spring5 框架的代码基于 Java8，运行时兼容 JDK9，许多不建议使用的类和方 法在代码库中删除

### 2.核心特性增强

#### 1.Spring 5.0 框架自带了通用的日志封装

1. Spring5 已经移除 Log4jConfigListener，官方建议使用 Log4j2

2. Spring5 框架整合 Log4j2

3. 实现步骤

   1. 引入 jar 包

   2.  创建 log4j2.xml 配置文件

      * ```xml
        <?xml version="1.0" encoding="UTF-8"?>
        <!--日志级别以及优先级排序: OFF > FATAL > ERROR > WARN > INFO > DEBUG > TRACE > ALL -->
        <!--Configuration后面的status用于设置log4j2自身内部的信息输出，可以不设置，当设置成trace时，可以看到log4j2内部各种详细输出-->
        <configuration status="INFO">
            <!--先定义所有的appender-->
            <appenders>
                <!--输出日志信息到控制台-->
                <console name="Console" target="SYSTEM_OUT">
                    <!--控制日志输出的格式-->
                    <PatternLayout pattern="%d{yyyy-MM-dd HH:mm:ss.SSS} [%t] %-5level %logger{36} - %msg%n"/>
                </console>
            </appenders>
            <!--然后定义logger，只有定义了logger并引入的appender，appender才会生效-->
            <!--root：用于指定项目的根日志，如果没有单独指定Logger，则会使用root作为默认的日志输出-->
            <loggers>
                <root level="info">
                    <appender-ref ref="Console"/>
                </root>
            </loggers>
        </configuration>
        ```

#### 2.Spring5 框架核心容器支持@Nullable 注解

1. @Nullable 注解可以使用在方法上面，属性上面，参数上面，表示方法返回可以为空，属性值可以 为空，参数值可以为空

2. 注解用在方法上面，方法返回值可以为空

   * ```java
     @Nullable
     public String getId(){}
     ```

3. 注解使用在方法参数里面，方法参数可以为空

   * ```java
     public String setId(@Nullable String id){}
     ```

4. 注解使用在属性上面，属性值可以为空

   * ```java
     @Nullable
     private String id;
     ```

#### 3.Spring5 核心容器支持函数式风格 GenericApplicationContext

* ```java
  @Test
  public void testGenericApplicationContext(){
      //1.创建GenericApplicationContext对象
      GenericApplicationContext context = new GenericApplicationContext();
      //2.调用context的方法对象注册
      context.registerBean("user1",User.class,()->new User("1","Vivy","dead"));
      context.refresh();
      //3.获取在Spring注册的对象
      User user = (User) context.getBean("user1");//使用beanName
      //User user = (User) context.getBean("pers.entity.User");//使用类全路径
      System.out.println(user);
  }
  ```

#### 4.Spring5 支持整合 JUnit5

##### 1.整合 JUnit4

1. 引入 Spring 相关针对测试依赖

2. 创建测试类，使用注解方式完成

   * ```java
     @RunWith(SpringJUnit4ClassRunner.class)//指定测试框架版本
     //@ContextConfiguration("02_TransactionManagerBean.xml")//加载配置文件
     @ContextConfiguration(classes = TxConfig.class)//加载配置类
     public class JTest4 {
         @Autowired
         private UserService userService;
     
         @Test
         public void test1(){
             userService.transferAccount();
         }
     }
     ```

##### 2.Spring5 整合 JUnit5

1. 引入 JUnit5 的 jar 包

2. 创建测试类，使用注解完成

   * ```java
     @ExtendWith(SpringExtension.class)//注解引用
     //@ContextConfiguration("02_TransactionManagerBean.xml")//加载配置文件
     @ContextConfiguration(classes = TxConfig.class)//加载配置类
     public class JTest5  {
         @Autowired
         private UserService userService;
     
         @Test
         public void test1(){
             userService.transferAccount();
         }
     }
     ```

3. 使用复合注解

   * ```java
     // @SpringJUnitConfig(locations = "02_TransactionManagerBean.xml")//加载配置文件
     @SpringJUnitConfig(classes = TxConfig.class)//加载配置类
     public class JTest5  {
         @Autowired
         private UserService userService;
     
         @Test
         public void test1(){
             userService.transferAccount();
         }
     }
     ```

## 2.Spring5 框架新功能（Webflux）

### 1.SpringWebflux 介绍

1. 是 Spring5 添加新的模块，用于 web 开发的，功能和 SpringMVC 类似的，Webflux 使用 当前一种比较流程响应式编程出现的框架。
2. 使用传统 web 框架，比如 SpringMVC，这些基于 Servlet 容器，Webflux 是一种异步非阻 塞的框架，异步非阻塞的框架在 Servlet3.1 以后才支持，核心是基于 Reactor 的相关 API 实现 的。
3. 解释什么是异步非阻塞
   1. 异步和同步
      * 异步和同步针对调用者,调用者发送请求，如果等着对方回应之后才去做其他事情就是同 步，如果发送请求之后不等着对方回应就去做其他事情就是异步
   2. 非阻塞和阻塞
      * 阻塞和非阻塞针对被调用者，被调用者受到请求之后，做完请求任务之后才给出反馈就是阻 塞，受到请求之后马上给出反馈然后再去做事情就是非阻塞
   3. 上面都是针对对象不一样
4. Webflux 特点
   1. 非阻塞式：在有限资源下，提高系统吞吐量和伸缩性，以 Reactor 为基础实现响应式编程
   2. 函数式编程：Spring5 框架基于 java8，Webflux 使用 Java8 函数式编程方式实现路由请求
5. 比较 SpringMVC
   1. 两个框架都可以使用注解方式，都运行在 Tomet 等容器中
   2. SpringMVC 采用命令式编程，Webflux 采用异步响应式编程

### 2.响应式编程（Java 实现）

#### 1.什么是响应式编程

#### 2.Java8 及其之前版本

* 提供的观察者模式两个类 Observer 和 Observable

  * ```java
    public class ObserverDemo extends Observable {
        public static void main(String[] args) {
            ObserverDemo observer = new ObserverDemo();
            //添加观察者
            observer.addObserver((o,arg)->{
                System.out.println("发生变化");
            });
            observer.addObserver((o,arg)->{
                System.out.println("手动被观察者通知，准备改变");
            });
            observer.setChanged(); //数据变化
            observer.notifyObservers(); //通知
        }
    }
    ```

### 3.响应式编程（Reactor 实现）

#### 1.响应式编程操作中，Reactor 是满足 Reactive 规范框架

#### 2.Reactor 有两个核心类，Mono 和 Flux，这两个类实现接口 Publisher，提供丰富操作 符。Flux 对象实现发布者，返回 N 个元素；Mono 实现发布者，返回 0 或者 1 个元素

#### 3.Flux 和 Mono 都是数据流的发布者，使用 Flux 和 Mono 都可以发出三种数据信号： 元素值，错误信号，完成信号，错误信号和完成信号都代表终止信号，终止信号用于告诉 订阅者数据流结束了，错误信号终止数据流同时把错误信息传递给订阅者

#### 4.代码演示 Flux 和 Mono

1. 引入依赖

   * ```xml
     <dependency>
         <groupId>io.projectreactor</groupId>
         <artifactId>reactor-core</artifactId>
         <version>3.1.5.RELEASE</version>
     </dependency>
     ```

2. 编程代码

   * ```java
     public static void main(String[] args) {
         //just 方法直接声明
         Flux.just(1,2,3,4);
         Mono.just(1);
         //其他的方法
         Integer[] array = {1,2,3,4};
         Flux.fromArray(array);
     
         List<Integer> list = Arrays.asList(array);
         Flux.fromIterable(list);
         Stream<Integer> stream = list.stream();
         Flux.fromStream(stream);
     }
     ```

### 4.SpringWebflux 执行流程和核心 API