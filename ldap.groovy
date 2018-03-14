#!groovy
import jenkins.model.*
import hudson.security.*
import jenkins.security.plugins.ldap.*
import hudson.util.Secret
import jenkins.model.IdStrategy

def j = Jenkins.getInstance()

def env = System.getenv()

String server = 'ldaps://ldap.example.com:636'
String rootDN = 'DC=bob,DC=com'
String userSearchBase = 'ou=london'
String userSearch = 'saMAccountName={0}'
String groupSearchBase = 'ou=london'
String groupSearchFilter = '(& (cn={0}) (objectclass=group) )'
String managerDN = 'CN=jenkins-ldap,OU=System Accounts,OU=london,DC=bob,DC=com'
//String managerPasswordSecret = 'blah'
String managerPasswordSecret = env['LDAP_BIND_PW']
boolean inhibitInferRootDN = true

LDAPConfiguration ldapConf = new LDAPConfiguration(server, rootDN, inhibitInferRootDN, managerDN, Secret.fromString(managerPasswordSecret))
ldapConf.userSearchBase = userSearchBase
ldapConf.userSearch = userSearch
ldapConf.groupSearchBase = groupSearchBase
ldapConf.groupSearchFilter = groupSearchFilter

List<LDAPConfiguration> configurations = [ldapConf]

j.securityRealm = new LDAPSecurityRealm(
  configurations,
  false,
  null,
  IdStrategy.CASE_INSENSITIVE,
  IdStrategy.CASE_INSENSITIVE)
//note more security customisation to do yet
j.save()
