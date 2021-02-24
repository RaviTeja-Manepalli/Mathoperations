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
              withSonarQubeEnv('localpassport') {
                bat 'mvn sonar:sonar'
              }
            }
        }
        
        stage('SonarQube Quality Gate') { 
            steps{
                timeout(time: 1, unit: 'HOURS') { 
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
    
}
