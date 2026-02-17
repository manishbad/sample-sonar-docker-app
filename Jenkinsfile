pipeline {
    agent any

    tools {
        maven 'Maven3.12'
    }

    environment {
        SONAR_SCANNER = tool 'SonarQubeScanner'
        DOCKER_IMAGE = "manishbadgujar/sample-sonar-app:latest"
        CONTAINER_NAME = "sample-sonar-container"
    }

    stages {

        stage('Checkout') {
            steps {
                git branch: 'main',
                    url: 'https://github.com/manishbad/sample-sonar-docker-app.git'
            }
        }

        stage('Build & Test') {
            steps {
                sh 'mvn clean verify'
            }
        }

        stage('SonarCloud Analysis') {
            steps {
                withSonarQubeEnv('SonarCloud') {
                    sh '''
                    mvn sonar:sonar \
                    -Dsonar.projectKey=sample-sonar-docker-app \
                    -Dsonar.organization=Manish_Organizarion \
                    -Dsonar.host.url=https://sonarcloud.io \
                    -Dsonar.login=$SONAR_AUTH_TOKEN
                    '''
                }
            }
        }

        stage('Quality Gate') {
            steps {
                timeout(time: 5, unit: 'MINUTES') {
                    waitForQualityGate abortPipeline: true
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t $DOCKER_IMAGE .'
            }
        }

        stage('Remove Old Container') {
            steps {
                sh '''
                docker rm -f $CONTAINER_NAME || true
                '''
            }
        }

        stage('Run Container') {
            steps {
                sh '''
                docker run -d -p 9090:9090 --name $CONTAINER_NAME $DOCKER_IMAGE
                '''
            }
        }
    }
}

