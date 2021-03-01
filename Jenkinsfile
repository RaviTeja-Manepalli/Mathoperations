pipeline {
    agent any
    tools{
        maven 'Maven'
        jdk 'JDK'
    }
    
    stages {
        
        stage('Clean')
        {
             steps {
                    bat 'mvn  clean'
                
            }
        }
      /*  stage('Build')
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
    */
   
       
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
 
  
    //buildName: 'Math Operation',
   // buildNumber: '42'
)
     }}


    }
    post{
        success{
            echo 'I succeeded!'
            mail to:'ravitejamanepalli47@gmail.com',
            subject:"Pipeline Succeeded: ${currentBuild.fullDisplayName}",
            body:"Built is success with ${env.BUILD_URL}"
            
             sshagent(['018f2730-23a7-4486-aeaf-3da5e807c0fa']){
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
