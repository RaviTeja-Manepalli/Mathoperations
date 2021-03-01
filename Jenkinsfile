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
     stage('deploy to artifactory')
     {
     steps{
     
     rtUpload (
    serverId: 'ARTIFACTORY_SERVER',
    spec: '''{
          "files": [
            {
              "pattern": "target/*.jar",
              "target": "arti-maven-dev-loc"
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
        }
        failure{
            echo 'I failed!'
            mail to:'ravitejamanepalli47@gmail.com',
            subject:"Pipeline Failed: ${currentBuild.fullDisplayName}",
            body:"Built is failed with ${env.BUILD_URL}"
        }

    }
    
}
