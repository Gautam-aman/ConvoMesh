# ConvoMesh

Distributed real-time chat system built with Spring Boot microservices, Kafka, Redis, PostgreSQL, WebSockets, Docker, and Kubernetes.

ConvoMesh is designed to model the shape of a production-style chat platform: messages enter through a gateway, flow through WebSocket and Kafka-based services, fan out through Redis-backed pub/sub, and are delivered to connected clients in real time.

## Highlights

- Real-time messaging over STOMP + SockJS
- Event-driven communication with Kafka
- Multi-instance WebSocket delivery with Redis pub/sub
- Persistent message storage with PostgreSQL
- API routing with Spring Cloud Gateway
- Containerized services with Docker
- Local orchestration with Kubernetes and Minikube

## Architecture

```text
Browser client
   |
   v
Gateway (Spring Cloud Gateway)
   |
   +--> Auth Service
   +--> Chat Service
   +--> WebSocket Service
                    |
                    v
                  Kafka
                    |
                    v
          Notification Service
                    |
                    v
                Redis Pub/Sub
                    |
                    v
         WebSocket Service replicas
                    |
                    v
               Connected clients
```

## Services

| Service | Port | Responsibility |
| --- | --- | --- |
| `gateway` | `8080` | Single entry point for external HTTP and WebSocket traffic |
| `auth-service` | `8081` | Authentication-related endpoints |
| `chat-service` | `8082` | Message persistence and chat APIs |
| `websocket-service` | `8083` | STOMP endpoint and chat message publishing |
| `notification-service` | `8084` | Kafka consumer and broadcast coordination |
| `postgres` | `5432` | Persistent storage |
| `redis` | `6379` | Cross-instance pub/sub |
| `zookeeper` | `2181` | Kafka coordination |
| `kafka` | `9092` | Event streaming backbone |

## Tech Stack

- Java 17
- Spring Boot
- Spring Cloud Gateway
- Spring WebSocket
- Apache Kafka
- Redis
- PostgreSQL
- Docker
- Kubernetes
- Minikube

## Repository Layout

```text
Auth-Service/
Chat-Service/
Gateway/
Notification-service/
websocket-service/
frontend/
k8s/
docker-compose.yml
```

## Prerequisites

Install these before running the project:

- Java 17
- Docker Desktop
- Kubernetes CLI (`kubectl`)
- Minikube
- Python 3

## Run with Minikube

This is the recommended way to run the project locally.

### 1. Start a fresh Minikube cluster

```bash
minikube start --driver=docker
kubectl config use-context minikube
kubectl cluster-info
```

If you previously had a broken local cluster, reset it first:

```bash
minikube stop
minikube delete
minikube start --driver=docker
```

### 2. Point Docker to Minikube

Build the application images inside Minikube's Docker environment so Kubernetes can use them directly.

```bash
eval $(minikube docker-env)
```

### 3. Build service images

Run these commands from the repository root:

```bash
cd Auth-Service && docker build -t auth-service:latest .
cd ../Chat-Service && docker build -t chat-service:latest .
cd ../websocket-service && docker build -t websocket-service:latest .
cd ../Notification-service && docker build -t notification-service:latest .
cd ../Gateway && docker build -t gateway:latest .
cd ..
```

### 4. Load third-party images required by the manifests

The Kubernetes manifests for PostgreSQL and Redis use `imagePullPolicy: Never`, so those images must exist inside Minikube.

```bash
docker pull postgres:latest
docker pull redis:latest
minikube image load postgres:latest
minikube image load redis:latest
```

### 5. Deploy the stack

```bash
kubectl apply -f k8s/
kubectl get pods -w
```

Wait until all workloads are in `Running` state, then stop the watch with `Ctrl + C`.

### 6. Expose the gateway

```bash
minikube service gateway --url
```

On macOS with the Docker driver, keep that terminal open. Minikube uses a local tunnel, and closing the terminal stops access to the service URL.

### 7. Start the frontend

Open a new terminal:

```bash
cd frontend
python3 -m http.server 5500
```

Then open:

```text
http://127.0.0.1:5500
```

The frontend includes a `Gateway URL` field. Use the exact URL returned by:

```bash
minikube service gateway --url
```

Example:

```text
http://127.0.0.1:52591
```

### 8. Verify the system

- Open multiple browser tabs
- Join the same room in both tabs
- Send messages from either tab
- Confirm that messages appear in real time across both sessions

## Run with Docker Compose

If you want a simpler local container setup without Kubernetes:

```bash
docker compose up --build
```

This starts the services defined in [docker-compose.yml](/Users/rigelsmacbook/Java%20Projects/ConvoMesh/docker-compose.yml).

For the frontend:

```bash
cd frontend
python3 -m http.server 5500
```

When running with Docker Compose, the gateway is available at:

```text
http://127.0.0.1:8080
```

## Useful Commands

Check running pods:

```bash
kubectl get pods
```

Inspect logs:

```bash
kubectl logs deployment/gateway --tail=100
kubectl logs deployment/websocket-service --tail=100
kubectl logs deployment/kafka --tail=100
```

Restart a deployment:

```bash
kubectl rollout restart deployment/<name>
```

Delete and redeploy the stack:

```bash
kubectl delete -f k8s/
kubectl apply -f k8s/
```

Stop Minikube:

```bash
minikube stop
```

Delete the entire local cluster:

```bash
minikube delete
```

## Troubleshooting

### `kubectl apply` fails with connection refused or OpenAPI download errors

Your cluster is usually not running yet. Restart Minikube and verify the context:

```bash
minikube start --driver=docker
kubectl config use-context minikube
kubectl cluster-info
```

### `kubectl` fails with `TLS handshake timeout`

The Kubernetes API server is unhealthy or overloaded. The cleanest fix is usually:

```bash
minikube stop
minikube start --driver=docker
```

If that is not enough:

```bash
minikube delete
minikube start --driver=docker
```

### Frontend shows `Could not connect` or `ERR_CONNECTION_REFUSED`

Most often the frontend is pointing to the wrong gateway URL.

Use the URL returned by:

```bash
minikube service gateway --url
```

Do not assume the gateway is reachable at `http://127.0.0.1:8080` when using Minikube.

### Kafka crash loops in Kubernetes

This repository already includes a fix in [k8s/kafka.yaml](/Users/rigelsmacbook/Java%20Projects/ConvoMesh/k8s/kafka.yaml): `enableServiceLinks: false`.

That prevents Kubernetes from injecting service-link environment variables like `KAFKA_PORT`, which can confuse the Confluent Kafka image and cause startup failures.

### `kubectl apply -f k8s/` says the path does not exist

Run that command from the repository root:

```text
ConvoMesh/
```

If you are inside `Gateway/` or another service directory, Kubernetes will not find the `k8s/` folder.

## Fault Tolerance Demo

Delete the WebSocket pods and watch Kubernetes recreate them:

```bash
kubectl delete pod -l app=websocket-service
kubectl get pods -w
```

This is a simple way to demonstrate self-healing behavior in the cluster.

## What This Project Demonstrates

- Microservices architecture
- Event-driven design
- Real-time bidirectional communication
- Horizontal scaling concepts
- Service discovery in Kubernetes
- Operational debugging in a production-like local environment

## Author

Aman Gautam
