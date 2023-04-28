pipeline {
    agent any

    stages {
        stage('pull code') {
            steps {
                checkout([$class: 'GitSCM',
                branches: [[name: '*/master']],
                extensions: [],
                userRemoteConfigs: [[credentialsId: '7137f7ee-6712-4902-b47d-b90314f17521',
                url: 'ssh://git@124.222.155.96:9922/zhiyin/spring.git']]])
            }
        }

        stage('build project') {
            steps {
                sh 'mvn clean package dockerfile:build'
                // 停止旧容器
                sh 'docker stop registry.cn-hangzhou.aliyuncs.com/zhiyin_spring/springdemo:latest'
                // 删除旧容器
                sh 'docker rm registry.cn-hangzhou.aliyuncs.com/zhiyin_spring/springdemo:latest'
                // 删除旧镜像
                sh 'docker rmi registry.cn-hangzhou.aliyuncs.com/zhiyin_spring/springdemo:latest'
                // 删除旧标签
                sh 'docker rmi springdemo:latest'
                // 对新镜像打上标签
                sh "docker tag springdemo:latest registry.cn-hangzhou.aliyuncs.com/zhiyin_spring/springdemo:latest"
                // 新镜像推送至阿里云私有仓库
                sh "docker push registry.cn-hangzhou.aliyuncs.com/zhiyin_spring/springdemo:latest"
            }
        }

        stage('publish project') {
            steps {
                // 镜像仓库拉取镜像
                sh "docker pull registry.cn-hangzhou.aliyuncs.com/zhiyin_spring/springdemo:latest"
                // 镜像推送至阿里云私有仓库
                sh "docker run -di -p 10086:10086 registry.cn-hangzhou.aliyuncs.com/zhiyin_spring/springdemo:latest"
            }
        }
    }
}
