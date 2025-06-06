### 项目相关

##### 代码地址

[huanghao123456/myspring: pdb](https://github.com/huanghao123456/myspring)

##### 在服务器的地址

后端相关以及用户上传文件地址：/home/b827/pdb

前端页面相关地址：/home/b827/pdb_web

后端日志：/home/b827/pdb/log.out

##### 已完成接口

```
/user/login
POST, 登录示例：
{
    "email":"cheng.lu@stu.jiangnan.edu.cn"
}

```

```、
/user/upload
POST, 上传示例：
Content-Type: application/json;charset=UTF-8
pdb: (手动上传)
task_name: abc123
full_sequence: aaa111
email: 824874832@qq.com
```

```
/user/calcDistMatrix
POST, 计算示例：
pdbId: 2025-06-05T14-32-13-396_huanghaobbb
```

```
/manager/gpu
GET, 查看GPU利用率：
(无需参数)
```

```
/manager/msgCount
GET, 查看消息队列排队状况：
(无需参数)
```



### 后端服务

后端为SpringBoot，服务挂后，在确认RabbitMQ服务正常后，直接起jar包，命令为：

~~~bash
nohup java -jar -Dspring.profiles.active=prod /home/b827/pdb/springdemo-0.0.1-SNAPSHOT.jar >/dev/null 2>&1 &
~~~



### RabbitMQ

##### 控制台登录

IP与端口为172.19.238.240:15672

账号：root

密码：root

##### 起服务

~~~bash
docker start rabbitmq
~~~

##### 若是第一次起服务

~~~bash
docker run -d \
  --name rabbitmq \
  -p 15672:15672 \
  -p 5672:5672 \
  -e RABBITMQ_DEFAULT_USER=root \
  -e RABBITMQ_DEFAULT_PASS=root \
  rabbitmq:3-management
~~~



### 其它

+ 未搭建MySQL，无鉴权操作，仅靠邮箱和时间戳作为唯一识别
+ RabbitMQ是为了排队调用显卡而引入，环境及外层代码框架已搭建好，目前生工无明确执行脚本，待完善。
+ 矩阵计算流程为：Java发送Python指令-->Python执行结果打印-->Java截取输出流-->Java返回结果。

