pipeline {
    agent any

    environment {
        DOCKER_HUB = "nasir1999"
        APP_NAME   = "payment-app"
    }

    stages {

        stage('Build Artifact') {
            steps {
                sh '''
                chmod +x mvnw
                ./mvnw clean package -DskipTests
                '''
            }
        }

        stage('Docker Build & Push') {
            environment {
                DOCKER_USER = credentials('DOCKER_USER')
                DOCKER_PASS = credentials('DOCKER_PASS')
            }
            steps {
                sh '''
                echo "Building Docker image..."
                docker build -t ${DOCKER_HUB}/${APP_NAME}:latest .

                echo "Logging in to Docker Hub..."
                echo "$DOCKER_PASS" | docker login -u "$DOCKER_USER" --password-stdin

                echo "Pushing Docker image..."
                docker push ${DOCKER_HUB}/${APP_NAME}:latest
                '''
            }
        }

        stage('Deploy') {
            steps {
                sh '''
                echo "Stopping old container (if exists)..."
                docker stop ${APP_NAME} || true
                docker rm ${APP_NAME} || true

                echo "Starting payment backend..."
                docker run -d \
                  --name ${APP_NAME} \
                  -p 8081:8080 \
                  ${DOCKER_HUB}/${APP_NAME}:latest
                '''
            }
        }
    }
}
