pipeline {
    agent any
    tools{
        maven 'Maven'
        jdk 'JDK'
    }
     environment{
         AWS_REGION='ap-south-1	
         AWS_DEFAULT_REGION='ap-south-1'
     }
    
    stages {
        
        stage('Clean')
        {
             steps {
                    bat 'mvn  clean'
                
            }
        }
       stage('Build')
        {
             steps {
                    bat 'mvn  compile'
                
            }
        }

        stage('Test') {
            steps {
                bat 'mvn test'
            }
        }
    
   
       
        stage('Package') {
            steps {
                    bat 'mvn  package'
                
            }
        }
     

         
    
       
        stage( 'SonarQube analysis'){
            steps {
              withSonarQubeEnv('sonar') {
                bat 'mvn sonar:sonar'
              }
            }
        }
        
        stage('SonarQube Quality Gate') { 
            steps{
                timeout(time: 1, unit: 'MINUTES') { 
                    script{
                        def qg = waitForQualityGate() 
                        if (qg.status != 'OK') {
                            error "Pipeline aborted due to quality gate failure: ${qg.status}"
                         }
                    }
                    
                }
            }
        }

        stage('collect artifact'){
     steps{
     archiveArtifacts artifacts: 'target/*.jar', followSymlinks: false
     }
     }
      stage('Deploy to S3 Bucket'){
           steps{
                  withCredentials([[$class: 'AmazonWebServicesCredentialsBinding', accessKeyVariable: 'AWS_ACCESS_KEY_ID', credentialsId: 'a5a4b59e-2298-426c-8065-45eb47c1eef2', secretKeyVariable: 'AWS_SECRET_ACCESS_KEY']]) {
                  s3Upload(file:'C:/Users/roshe/.jenkins/workspace/AwsChallenge/CodingChallenge-2/target/sportApplication-0.0.1-SNAPSHOT.jar', bucket:'1-challenge-s3', path:'sampleFile/sportApplication-0.0.1-SNAPSHOT.jar')
   
 }
         }
            }      
  
     stage('deploy to artifactory')
     {
     steps{
     
     rtUpload (
             serverId: 'ARTIFACTORY_SERVER',
      spec: '''{
          "files": [
            {
               "pattern": "target/*.jar",
              "target": "arti-maven-dev-loc/"
            }
         ]
    }''',
 
  
    buildName: 'Math Operation',
    buildNumber: '42'
)
     }}


    }
    post{
        success{
            echo 'I succeeded!'
            mail to:'ravitejamanepalli47@gmail.com',
            subject:"Pipeline Succeeded: ${currentBuild.fullDisplayName}",
            body:"Built is success with ${env.BUILD_URL}"
            
             sshagent(['351279ad-c9c0-4745-a8dc-344733d7b8f2']){
                    bat 'scp -r C:/Windows/System32/config/systemprofile/AppData/Local/Jenkins/.jenkins/workspace/Math Operations/target/*.jar ubuntu@13.126.108.30:/home/ubuntu/artifacts'
        }
        
        }
        failure{
            echo 'I failed!'
            mail to:'ravitejamanepalli47@gmail.com',
            subject:"Pipeline Failed: ${currentBuild.fullDisplayName}",
            body:"Built is failed with ${env.BUILD_URL}"
        }

    }
    
}
