#!groovy
import hudson.security.csrf.DefaultCrumbIssuer
import jenkins.model.Jenkins

def j = Jenkins.instance
j.setCrumbIssuer(new DefaultCrumbIssuer(true))
j.save()
