// Jenkinsfile

pipeline {
    agent any
    environment {
        VAR_AUTHOR = 'Jason'
        SC_PASSWORD = credentials('password-test')
        VAR_ORIGINAL_WORKSPACE = "${env.WORKSPACE}"
    }
    parameters {
        choice(name: 'DEPLOY_ENV', choices: ['dev', 'staging', 'prod'], description: 'Deployment environment')
        string(name: 'VERSION', defaultValue: '1.0.0', description: 'Version to deploy')
    }
    stages {
        stage('Flow Startup') {
            environment {
                VAR_LOCAL_TEST = 'Start var'
            }
            steps {
                echo "======= Start Jenkinsfile with Evn: ${MYVAR} ======="
                echo "Author: ${VAR_AUTHOR}"

                sh 'echo Test \'sh\' command: OK'

                // echo "JEkins URL: ${YOUR_JENKINS_URL}"
                echo "Test local stage var: ${VAR_LOCAL_TEST}"
                echo "Test secret text: ${SC_PASSWORD}"
                echo "Test jobs evn.JOB_NAME: ${env.JOB_NAME} "
                echo "Test jobs params.DEPLOY_ENV: ${params.DEPLOY_ENV} "
                echo "Test jobs params.VERSION: ${params.VERSION} "
            }
        }
        stage('Git Checkout') {
            steps {
                script {
                    git branch: 'master',
                        credentialsId: 'github-token',
                        url: 'https://github.com/pilisir/spring-boot-unit-test.git'
                }
            }
        }
        stage('Test project') {
            tools { 
                maven 'maven' 
                jdk 'zulu_openjdk21' 
            }
            steps {
                echo "======= Maven Test ======="
                sh 'mvn clean'
                sh 'mvn test'
                //junit '**/target/*.xml'
                junit allowEmptyResults: true, testResults: '**/target/*.xml'
            }
        }
        stage('Build project') {
            tools { 
                maven 'maven' 
                jdk 'zulu_openjdk21' 
            }
            steps {
                echo "======= Maven Package ======="
                sh 'mvn clean'
                sh 'mvn package'
            }
        }
        stage('Push Docker') {
            // agent any // Can not remove with "script bracket", or causes "Cannot connect to the Docker daemon"
            tools {
                dockerTool 'docker'
            }
            environment {
                VAR_DOCKER_IMAGE_TAG = '1.0'
                VAR_DOCKER_USERNAME = 'pilisir'
                SC_DOCKER_HUB_TOKEN = credentials('pilisir-dockerhub')
            }
            steps {
                echo "======= Push to Docker Hub ======="
                script {
                    // def dockerImage
                    docker.withServer('tcp://docker-tcp-socat:2375', 'pilisir-dockerhub') {
                        // dockerImage = docker.build("${VAR_DOCKER_USERNAME}/spring-boot-unit-test-myweb-jenkins:${VAR_DOCKER_IMAGE_TAG}", "${VAR_ORIGINAL_WORKSPACE}")
                        // dockerImage.push()
                        
                        // sh("docker version")
                        sh 'docker build -t ${VAR_DOCKER_USERNAME}/spring-boot-unit-test-myweb-jenkins:${VERSION} .'
                        // sh '''echo ${SC_DOCKER_HUB_TOKEN_PSW} | docker login -u ${VAR_DOCKER_USERNAME} --password-stdin'''
                        sh 'docker login -u ${VAR_DOCKER_USERNAME} -p ${SC_DOCKER_HUB_TOKEN_PSW}'
                        sh 'docker push ${VAR_DOCKER_USERNAME}/spring-boot-unit-test-myweb-jenkins:${VERSION}'
                        //sh 'docker image rm ${VAR_DOCKER_USERNAME}/spring-boot-unit-test-myweb-jenkins:${VERSION}'

                    }
                }
            }
        }
    }
    post {
        always {
            dir("${env.WORKSPACE}@tmp") {
                deleteDir()
            }
            dir("${env.WORKSPACE}@2") {
                deleteDir()
            }
            dir("${env.WORKSPACE}@2@tmp") {
                deleteDir()
            }
        }
    }
}