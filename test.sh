# Create a user
curl -H 'Content-Type: application/json' \
     -d'{"id":1,"name":"Bob"}' \
     -X POST http://localhost:8081/users/

# Send a tweet
curl -H "Content-Type: application/json" \
     -d '{"id":124, "content":"this is a tweet", "sender":1, "timeline":12323234234}' \
     -X POST http://localhost:8080/tweets/

# Get total number of counts
curl -X GET http://localhost:8082/tweets/1/counts