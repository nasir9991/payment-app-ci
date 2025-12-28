pipeline {
    agent any
    environment {
        DOCKER_HUB = "nasir1999"
        APP_NAME = "payment-app"
    }
    stages {
        stage('Build Artifact') {

            steps {
                sh "chmod +x mvnw"
                sh "./mvnw clean package -DskipTests"
            }
        }
    stage('Docker Build & Push') {
            steps {
                script {
                    docker.withRegistry('', 'docker-creds') {
                        def img = docker.build("${DOCKER_HUB}/${APP_NAME}")
                        img.push('latest')
                    }
                }
            }
        }
    stage('Deploy') {
            steps {
                sh "docker stop ${APP_NAME} || true && docker rm ${APP_NAME} || true"
                sh "docker run -d --name ${APP_NAME} -p 8081:8081 ${DOCKER_HUB}/${APP_NAME}:latest"
            }
        }
    }
