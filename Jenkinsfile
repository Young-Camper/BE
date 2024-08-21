pipeline {
    agent any

    stages {
        stage('Prepare Config') {
            steps {
                configFileProvider([
                configFile(fileId: 'application.properties', targetLocation: 'application.properties'),
                configFile(fileId: 'test.properties', targetLocation: '/src/test/java/resources/application.properties')
                ]) {
                }
            }
        }

        stage('Build') {
            steps {
                sh 'chmod +x gradlew'
                sh './gradlew clean build'
            }
        }
         stage('Test') {
                    steps {
                        sh './gradlew test'
                    }
                }


        stage('Run') {
            steps {
                sh 'java -jar build/libs/server-0.0.1-SNAPSHOT.jar'
            }
        }
    }
}
