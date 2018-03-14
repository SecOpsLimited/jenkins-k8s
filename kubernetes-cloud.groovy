#!groovy
import org.csanchez.jenkins.plugins.kubernetes.*
import org.csanchez.jenkins.plugins.kubernetes.volumes.*
import jenkins.model.*

def j = Jenkins.getInstance()

def k = new KubernetesCloud(
  'k8s',
  null,
  '',
  'jenkins',
  'https://jenkins.example.me',
  '10', 0, 0, 5
)
k.setJenkinsTunnel('jenkins:50000')

def c1 = new ContainerTemplate('jnlp', '<redacted>.dkr.ecr.eu-west-1.amazonaws.com/platform/jenkins-jnlp-slave', '', '')
c1.setPrivileged(true)
c1.setAlwaysPullImage(true)
c1.setResourceLimitMemory('4G')
c1.setResourceRequestMemory('2G')

def ips = new PodImagePullSecret('awsecr-cred')

def hpdocksock = new HostPathVolume('/var/run/docker.sock', '/var/run/docker.sock:z')
def secretawsjenkins = new SecretVolume('/home/jenkins/.aws', 'aws-jenkins-creds')

def p1 = new PodTemplate()
p1.setName('dood')
p1.setLabel('dood')
p1.setNamespace('jenkins')
p1.setContainers(Arrays.asList(c1))
p1.setVolumes(Arrays.asList(hpdocksock,secretawsjenkins))
p1.setImagePullSecrets(Arrays.asList(ips))

k.addTemplate(p1)

def c2 = new ContainerTemplate('jnlp', '<redacted>.dkr.ecr.eu-west-1.amazonaws.com/platform/alt-jslave', '', '')
c2.setPrivileged(true)
c2.setAlwaysPullImage(true)
c2.setResourceLimitMemory('4G')
c2.setResourceRequestMemory('2G')

def p2 = new PodTemplate()
p2.setName('dood-alt')
p2.setLabel('dood-alt')
p2.setNamespace('jenkins')
p2.setContainers(Arrays.asList(c2))
p2.setVolumes(Arrays.asList(hpdocksock,secretawsjenkins))
p2.setImagePullSecrets(Arrays.asList(ips))

k.addTemplate(p2)

j.clouds.replace(k)
j.save()
