# build local docker image for app
* `build_docker_image.sh`

# start app, kafka, zookeeper
`docker-compose up -d`

# Check current stats of the page views
`curl -i localhost:8080/counts`

# Check current stats of the page views by looping call evey 2 seconds
```
while sleep 2; do curl localhost:8080/counts; echo ""; done
```
