**Debug:** 
  export DEBUG_ARGS="-Xdebug -agentlib:jdwp=transport=dt_socket,address=127.0.0.1:8888,server=y,suspend=n"

**Producer:**
  ./bin/artemis producer --url tcp://localhost:61616 --destination <the queue> --user admin --password admin --message-count 1 --protocol amqp
  
**Consumer:**
  ./bin/artemis consumer  --url tcp://localhost:61616 --destination <the queue> --user admin --password admin --protocol amqp
  
