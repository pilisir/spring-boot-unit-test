// Jenkinsfile

import hudson.model.User
import hudson.tasks.Mailer

def getUserEmail(username) {
    def user = User.get(username, false)
    def prop = user?.getProperty(Mailer.UserProperty)
    return prop?.getAddress()
}

pipeline {
    agent any
    environment {
        VAR_AUTHOR = 'Jason'
        VAR_ORIGINAL_WORKSPACE = "${env.WORKSPACE}"
        VAR_VERSION = '1.0.4'
        
        SC_PASSWORD = credentials('password-test')
    }
    parameters {
        choice(name: 'DEPLOY_ENV', choices: ['dev', 'staging', 'prod'], description: 'Deployment environment')
        // string(name: 'VERSION', defaultValue: '1.0.3', description: 'Version to deploy')
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
                        sh 'docker build -t ${VAR_DOCKER_USERNAME}/spring-boot-unit-test-myweb-jenkins:${VAR_VERSION} .'
                        // sh '''echo ${SC_DOCKER_HUB_TOKEN_PSW} | docker login -u ${VAR_DOCKER_USERNAME} --password-stdin'''
                        sh 'docker login -u ${VAR_DOCKER_USERNAME} -p ${SC_DOCKER_HUB_TOKEN_PSW}'
                        sh 'docker push ${VAR_DOCKER_USERNAME}/spring-boot-unit-test-myweb-jenkins:${VAR_VERSION}'
                        
                        sh 'docker image rm ${VAR_DOCKER_USERNAME}/spring-boot-unit-test-myweb-jenkins:${VAR_VERSION}'
                    }
                }
            }
        }
        stage('Send Email Request for Deployment to Manager') {
            steps {
                script {
                    def username = "mana";
                    def mail = getUserEmail(username)
                    
                    // echo "${username} = ${mail}"
                    
                    emailext(
                        subject: "APPROVAL RQD [JENKINS] ${currentBuild.fullDisplayName}",
                        from: 'jenkins@8087.test',
                        to: "pilisir.tw@gmail.com",
                        mimeType: "text/html",
                        body: """
                            Dear, ${username} <br>
                            請<a href="${BUILD_URL}input">批准</a>已立即部署。
                        """
                    )
                }
            }
        }
        stage('Pasue and wait the Approve') {
            input {
                message '請確認是否部署?'
                id 'envId'
                ok '開始部署'
                submitterParameter 'approverId'
                submitter 'mana' // who can approve this
                // parameters {
                //     choice choices: ['Prod', 'Pre-Prod'], name: 'envType'
                // }
            }

            steps {
                echo "Deployment approved by ${approverId}."

            }
        }
 		stage('Deploy Host Docker') {
            tools {
                dockerTool 'docker'
            }
            environment {
                VAR_DOCKER_IMAGE_TAG = '1.0'
                VAR_DOCKER_USERNAME = 'pilisir'
                SC_DOCKER_HUB_TOKEN = credentials('pilisir-dockerhub')
            }
            steps {
                echo "======= Deploy to Host Docker ======="
                script {
                    docker.withServer('tcp://docker-tcp-socat:2375', 'pilisir-dockerhub') {
                        
                        sh '''
                        	image="${VAR_DOCKER_USERNAME}/spring-boot-unit-test-myweb-jenkins:${VAR_VERSION}"
							container="sbut-myweb-jenkins-v${VAR_VERSION}"
							network="my-network"
							
							docker start oracle-xe-21c
							
							printf "\r%s" "#####                     (33%)"
							sleep 2
							printf "\r%s" "#############             (66%)"
							sleep 2
							printf "\r%s" "#######################   (100%)"
							echo "\n"
							sleep 1
							
							docker run -td --name $container -p 8080:8080 $image
							docker network connect $network $container
							docker exec -t $container bash -c "java -jar my-web-sb-v1-demo.war > /tmp/output.log 2>&1"
                        '''
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