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

        stage('Test') {
            steps {
                bat 'mvn test'
            }
            post {
        always {
            junit 'build/reports/**/*.xml'
        }
        }
    
   
       
        stage('Package') {
            steps {
                    bat 'mvn  package'
                
            }
        }
         
    }
       
        stage( 'SonarQube analysis'){
            steps {
              withSonarQubeEnv('localpassport') {
                bat 'mvn sonar:sonar'
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
