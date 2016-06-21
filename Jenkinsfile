node {

 stage 'checkout'
 checkout scm

 stage 'test+coverage'
 sh "./gradlew clean test jacoco"
 publishHTML(target:[allowMissing: true, alwaysLinkToLastBuild: true, keepAll: true, reportDir: "build/reports/tests", reportFiles: 'index.html', reportName: 'Test report'])
  publishHTML(target:[allowMissing: true, alwaysLinkToLastBuild: true, keepAll: true, reportDir: "build/reports/jacoco/test/html", reportFiles: 'index.html', reportName: 'Jacoco report'])
 
 stage 'build'
 sh "./gradlew build"
}