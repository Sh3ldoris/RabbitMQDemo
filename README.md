PostGreSQL database running on port 5572.
To initialize databaze run code:
To setup Person table crete table: 
CREATE TABLE person(
   birthnumber VARCHAR(11) NOT NULL,
   firstname VARCHAR (20) NOT NULL,
   lastname VARCHAR (30) NOT NULL,
   address CHAR (50),
   city VARCHAR(20),
   PRIMARY KEY(birthnumber)
);

And initialize it with values:



# JBOSS FUSE 6.3 instalation


- Change volume path to maven repository in `docker-compose.yml`

```yml
volumes:
  - C:/Users/$USER/.m2/repository:/root/.m2/repository
```

- Start Docker container

```bash
docker-compose up
```

- Find container id

```bash
docker container ls -f name="jboss_fuse*" -q
```

- Go into container Bash REPL

```bash
docker exec -it $(Container_ID) bash
```

## Container BASH

### Execute this script or go by step by step guide

```bash
cd /opt/jboss/jboss-fuse && \
bin/client "fabric:create --wait-for-provisioning"  && echo "fabric created..." && \
bin/client "fabric:container-create-child root dcom"  && echo "container created..." && \
bin/client "fabric:profile-create dcom" && \
sleep 5 && echo "profile created..." && \
bin/client "container-add-profile dcom dcom"  && echo "profile added..." && \
bin/client "profile-edit --repository mvn:sk.dcom.soa/sk.dcom.soa.nevgw/local-SNAPSHOT/xml/feature dcom"  && \
bin/client "profile-edit --repository mvn:sk.dcom.soa/sk.dcom.soa.security/local-SNAPSHOT/xml/feature dcom"  && \
bin/client "profile-edit --repository mvn:sk.dcom.soa/sk.dcom.soa.upvs2/local-SNAPSHOT/xml/feature dcom"  && \
bin/client "profile-edit --repository mvn:sk.dcom.soa/sk.dcom.soa.ra/local-SNAPSHOT/xml/feature dcom"  && \
bin/client "profile-edit --repository mvn:sk.dcom.soa/sk.dcom.soa.upvsext/local-SNAPSHOT/xml/feature dcom"  && \
bin/client "profile-edit --repository mvn:sk.dcom.soa/sk.dcom.soa.common/local-SNAPSHOT/xml/feature dcom"  && \
bin/client "profile-edit --repository mvn:sk.dcom.soa/sk.dcom.soa.rsdgw/local-SNAPSHOT/xml/feature dcom"  && \
bin/client "profile-edit --repository mvn:sk.dcom.soa/sk.dcom.soa.spgw/local-SNAPSHOT/xml/feature dcom"  && \
bin/client "profile-edit --repository mvn:sk.dcom.soa/sk.dcom.soa.kn/local-SNAPSHOT/xml/feature dcom"  && \
bin/client "profile-edit --repository mvn:sk.dcom.soa/sk.dcom.soa.csru/local-SNAPSHOT/xml/feature dcom"  && \
bin/client "profile-edit --repository mvn:sk.dcom.soa/sk.dcom.soa.osa/local-SNAPSHOT/xml/feature dcom"  && \
bin/client "profile-edit --repository mvn:sk.dcom.soa/sk.dcom.soa.fsgw/local-SNAPSHOT/xml/feature dcom"  &&  \
sleep 5 && echo "repositories added..." && \
bin/client 'container-edit-jvm-options dcom "-Xdebug -Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=61616"' && \
sleep 5 && echo "added debbuging capabilities..."  && \
cp /opt/jboss/jboss-fuse/adfs.wsdl /opt/jboss/jboss-fuse/instances/dcom/. && \
echo "copied key..."  &&  echo "DONE."
```

- Continue in your browser --> `http://localhost:8181/` with user: `admin` and password: `admin`

- default / io.fabric8.agent.properties Default profile
```
org.ops4j.pax.url.mvn.repositories = https://deployer:FGTDdKnAWa50@build.dcom.sk/nexus/content/groups/public@snapshots@id=build.dcom.sk
```

- connect to jboss container
docker-compose exec jboss bash

-connect to logs
tail -f instances/dcom/data/log/fuse.log

-set up database
docker run -p 5572:5432 -e POSTGRES_PASSWORD=admin -d postgres

-set up RabbitMQ image
docker run -p 5670:5672 -p 15672:15672 -e RABBITMQ_DEFAULT_USER=admin -e RABBITMQ_DEFAULT_PASS=admin rabbitmq:3-management