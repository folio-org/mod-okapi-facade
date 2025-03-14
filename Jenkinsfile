import org.jenkinsci.plugins.workflow.libs.Library

@Library('folio_jenkins_shared_libs@EPC') _

buildMvn {
  publishModDescriptor = true
  mvnDeploy = true
  buildNode = 'jenkins-agent-java21'

  doDocker = {
    buildDocker {
      publishMaster = 'yes'
//      healthChk = 'yes'
//      healthChkCmd = 'wget --no-verbose --tries=1 --spider http://localhost:8081/admin/health || exit 1'
    }
  }
}

