FROM websphere-liberty:microProfile
COPY server.xml /config/
ADD target/springboot-mongodb-crud-0.0.1-SNAPSHOT.jar /opt/ibm/wlp/usr/servers/defaultServer/dropins/
ENV LICENSE accept
EXPOSE 9080

## Running the container locally
# mvn clean install
# docker build -t eLocalShoppieSvc:latest .
# docker run -d --name myjavacontainer eLocalShoppieSvc
# docker run -p 9080:9080 --name myjavacontainer eLocalShoppieSvc
# Visit http://localhost:9080/eLocalShoppieSvc/

## Push container to IBM Cloud
# docker tag getstartedjava:latest registry.ng.bluemix.net/<my_namespace>/eLocalShoppieSvc:latest
# docker push registry.ng.bluemix.net/<my_namespace>/eLocalShoppieSvc:latest
# ibmcloud ic images # Verify new image
