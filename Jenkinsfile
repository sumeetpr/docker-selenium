pipeline{

    agent {label "NODE1"}

    stages{
        stage('Build jar'){
            steps{
               sh "mvn clean package -DskipTests"
            }
        }
        stage('build image'){
             steps{
                 sh "docker build -t=sumeeetpr/selenium ."
            }
        }
        stage('Push Image'){
            environment{
                DOCKER_HUB = credentials('dockerhub_creds')
            }
             steps{
                 sh 'docker login -u ${DOCKER_HUB_USR} -p ${DOCKER_HUB_PSW}'
                 sh "docker push sumeeetpr/selenium"
            }
        }
    }
    post{
        always{
            sh "docker logout"
        }
    }
}