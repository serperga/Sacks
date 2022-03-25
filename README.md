
# Sacks Store Code Exercise

Code exercise for Sacks implementing  a store and creating backend code to cover some scernarios describe in the exercise.




## Installation

Prerquisites:

- maven installed
- git installed
- Java installed
- IDE installed such as IntelliJ IDEA


Install docker desktop. You can get it here https://docs.docker.com/get-docker/

Install minikube following the instructions following the installation guide in its official page https://minikube.sigs.k8s.io/docs/start/





## Deployment

To deploy this project run

```bash
  npm run deploy
```

Download the project from github using git. The instruction to download the repo is

```bash
  git clone https://github.com/serperga/Sacks.git
```

Once you downladed the repo go to the root folder of the project (the one with the pom.xml file) and compile the project with maven.

```bash
  mvn clean Install
```

Once we compiled the project we need to create the docker image and upload it to the local repository

```bash
  docker build --file=Dockerfile \
  --tag=sacks-store:1.0.0 --rm=true .
```
If there is no errors, you have created and uplaoded the image to docker local registry.

Start minikube cluster

```bash
  minikube start
```

Create the deployment and expose it on port 8080

```bash
  kubectl create deployment sacks-store --image=sacks-store:1.0.0
  kubectl expose deployment sacks-store --type=LoadBalancer --port=8080
```

It may take a moment, but your deployment will soon show up when you run:

```bash
  kubectl get services sacks-store
```

In another window, start the tunnel to create a routable IP for the ‘balanced’ deployment:

```bash
  minikube tunnel
```

To find the routable IP, run this command and examine the EXTERNAL-IP column:

```bash
  kubectl get services sacks-store
```

Your application is running

## Swagger Documentation

Once you have the deployment running you can access Swagger in the next URL:

http://<EXTERNAL IP>:8080/swagger-ui.html

Here you can found all the API Documentation

To start System you can hit the endpoint 

http://<EXTERNAL IP>:8080/simulate