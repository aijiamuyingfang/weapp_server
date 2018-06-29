pipeline {
    agent any
    options {
        buildDiscarder(logRotator(numToKeepStr: '25'))
        disableConcurrentBuilds()
        timestamps()
        skipDefaultCheckout()
    }

    stages {
        
        stage('get code') {
            steps {
                git 'https://github.com/fuyoushengwu/weapp_server.git'
            }
        }

        stage('code analysis') {
            steps {
                dir("sources") {
                    sh 'mvn clean install sonar:sonar -Dsonar.organization=fuyoushengwu-github -Dsonar.host.url=https://sonarcloud.io -Dsonar.login=1a1a48e2f6cd3453ae98de53fc46ef27b53732f7'
                }
            }
        }
    }

    post {

        success {
            dir("sources/distribute") {
                archive 'weapp/**'
            }
        }
    }

}