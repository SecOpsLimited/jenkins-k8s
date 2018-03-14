FROM jenkins/jenkins:2.110
#COPY ldap_ca.cer $JAVA_HOME/jre/lib/security
#USER root
#RUN cd $JAVA_HOME/jre/lib/security && keytool -keystore cacerts -storepass changeit -noprompt -trustcacerts -importcert -alias ldapcert -file ldap_ca.cer
USER jenkins
COPY ldap.groovy proxy.groovy kubernetes-cloud.groovy git-ssh.groovy secrets.groovy /usr/share/jenkins/ref/init.groovy.d/
COPY plugins.txt /usr/share/jenkins/ref/
RUN /usr/local/bin/install-plugins.sh < /usr/share/jenkins/ref/plugins.txt
