pipeline{

    agent any

    stages {
        stage('Build jar') {
        agent {
            docker {
                image 'maven:eclipse-temurin:17-jdk-focal'
            }
        }
            steps {
               bat "mvn clean package -DskipTests"
            }
        }
        stage('build image') {
             steps{
                script {
                    app = docker.build('sumeeetpr/selenium')
                }
//                  bat "docker build -t=sumeeetpr/selenium ."
            }
        }
        stage('Push Image') {
//             environment{
//                 DOCKER_HUB = credentials('dockerhub_creds')
//             }
             steps {
             script {
                docker.withRegistry('', 'dockerhub_creds') {
                    app.push('latest')
                }
              }
//                  bat 'docker login -u %DOCKER_HUB_USR% -p %DOCKER_HUB_PSW%'
//                  bat "docker push sumeeetpr/selenium"
            }
        }
    }
    post{
        always{
            bat "docker logout"
        }
    }
}