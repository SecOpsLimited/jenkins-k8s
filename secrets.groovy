#!groovy
import jenkins.model.*
import com.cloudbees.plugins.credentials.*
import com.cloudbees.plugins.credentials.common.*
import com.cloudbees.plugins.credentials.domains.*
import com.cloudbees.plugins.credentials.impl.*
import org.jenkinsci.plugins.plaincredentials.*
import org.jenkinsci.plugins.plaincredentials.impl.*
import hudson.util.Secret

domain = Domain.global()
store = Jenkins.instance.getExtensionList('com.cloudbees.plugins.credentials.SystemCredentialsProvider')[0].getStore()

def env = System.getenv()

npmAuth = new StringCredentialsImpl(
  CredentialsScope.GLOBAL,
  'NPM_AUTH_TOKEN',
  'NPM auth token',
  Secret.fromString(env.NPM_AUTH_TOKEN)
)

mvnDeploy = new StringCredentialsImpl(
  CredentialsScope.GLOBAL,
  'MVN_DEPLOY_PW',
  'Maven deploy pw',
  Secret.fromString(env.MVN_DEPLOY_PW)
)

mvnRo = new StringCredentialsImpl(
  CredentialsScope.GLOBAL,
  'MVN_RO_PW',
  'Maven ro pw',
  Secret.fromString(env.MVN_RO_PW)
)

store.addCredentials(domain, npmAuth)
store.addCredentials(domain, mvnDeploy)
store.addCredentials(domain, mvnRo)
