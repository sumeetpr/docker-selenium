pipeline{

    agent any

    stages{
        stage('Build jar'){
        agent {
            docker{
                image 'maven:eclipse-temurin:17-jdk-focal'
            }
        }
            steps{
               sh "mvn clean package -DskipTests"
            }
        }
        stage('build image'){
             steps{
                 bat "docker build -t=sumeeetpr/selenium ."
            }
        }
        stage('Push Image'){
            environment{
                DOCKER_HUB = credentials('dockerhub_creds')
            }
             steps{
                 bat 'docker login -u %DOCKER_HUB_USR% -p %DOCKER_HUB_PSW%'
                 bat "docker push sumeeetpr/selenium"
            }
        }
    }
    post{
        always{
            bat "docker logout"
        }
    }
}