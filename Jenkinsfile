node {

   def mvn = tool (name: 'maven3', type: 'maven') + '/bin/mvn'
    
   stage('SCM Checkout'){
    // Clone repo
	git branch: 'master', 
	credentialsId: 'github', 
	url: 'https://github.com/ambuj750/trade'
   
   }
    
    stage('Mvn Package'){
	   // Build using maven
	   sh "${mvn} clean package deploy"
   }
}
