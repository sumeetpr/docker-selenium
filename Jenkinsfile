pipeline{

    agent any

    stages{
        stage('Build jar'){

            steps{
               bat "mvn clean package -DskipTests"
            }
        }
        stage('build image'){
             steps{
                 bat "docker build -t=sumeeetpr/selenium-new ."
            }
        }
        stage('Push Image'){
            environment{
                DOCKER_HUB = credentials('dockerhub_creds')
            }
             steps{
                 bat 'docker login -u %DOCKER_HUB_USR% -p %DOCKER_HUB_PSW%'
                 bat 'docker push sumeeetpr/selenium-new:latest'
                 bat "docker tag sumeeetpr/selenium-new:latest sumeeetpr/selenium:${env.BUILD_NUMBER}"
                 bat "docker push sumeeetpr/selenium-new:${env.BUILD_NUMBER}"
            }
        }
    }
    post{
        always{
            bat "docker logout"
        }
    }
}