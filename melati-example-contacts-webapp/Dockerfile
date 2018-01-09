FROM ubuntu
MAINTAINER timp@melati.org

RUN \
    apt-get update && \
    apt-get install -y cowsay && \
    apt-get install -y fortunes && \
    apt-get install -y openjdk-8-jre

# Define working directory.
WORKDIR /data

# Define commonly used JAVA_HOME variable
#ENV JAVA_HOME /usr/lib/jvm/java-6-oracle

CMD java -version
CMD /usr/games/fortune -a 

