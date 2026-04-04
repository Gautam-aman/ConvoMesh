# 🚀 ConvoMesh – Distributed Real-Time Chat System

A production-grade **distributed real-time chat system** built using **Spring Boot microservices**, deployed on **Kubernetes**, and powered by **Kafka, Redis, and WebSockets**.

---

## 🧠 Overview

ConvoMesh is designed to simulate how real-world messaging platforms (like WhatsApp/Slack) work at scale.

It follows a **microservices + event-driven architecture** to ensure:

* Scalability
* Fault tolerance
* Real-time communication
* Loose coupling between services

---

## 🏗️ Architecture

```
Client (HTML/JS)
        ↓
API Gateway (Spring Cloud Gateway)
        ↓
WebSocket Service (STOMP + SockJS)
        ↓
Kafka (Event Streaming)
        ↓
Notification Service
        ↓
Redis (Pub/Sub)
        ↓
WebSocket (All Instances)
        ↓
Client (Real-time updates)
```

---

## ⚙️ Tech Stack

### 🔹 Backend

* Java + Spring Boot
* Spring Cloud Gateway
* Spring WebSocket (STOMP + SockJS)

### 🔹 Messaging & Caching

* Apache Kafka (Event-driven architecture)
* Redis (Pub/Sub for real-time sync)

### 🔹 Database

* PostgreSQL

### 🔹 DevOps & Deployment

* Docker (Containerization)
* Kubernetes (Orchestration & Scaling)
* Minikube (Local K8s cluster)

---

## 🔥 Features

* ✅ Real-time chat using WebSockets
* ✅ Event-driven messaging with Kafka
* ✅ Distributed pub/sub with Redis
* ✅ API Gateway routing
* ✅ Multi-instance WebSocket scaling
* ✅ Fault-tolerant system (auto-recovery via Kubernetes)
* ✅ Persistent message storage (PostgreSQL)
* ✅ Microservices architecture

---

## 📦 Microservices

| Service              | Description                          |
| -------------------- | ------------------------------------ |
| auth-service         | Handles authentication               |
| chat-service         | Stores and retrieves messages        |
| websocket-service    | Manages real-time connections        |
| notification-service | Consumes Kafka & broadcasts messages |
| gateway              | Routes all external requests         |

---

## 🚀 How to Run Locally

### 1️⃣ Start Minikube

```bash
minikube start
```

---

### 2️⃣ Build Docker Images (inside Minikube)

```bash
eval $(minikube docker-env)

cd Auth-Service && docker build -t auth-service:latest .
cd ../Chat-Service && docker build -t chat-service:latest .
cd ../websocket-service && docker build -t websocket-service:latest .
cd ../Notification-service && docker build -t notification-service:latest .
cd ../Gateway && docker build -t gateway:latest .
```

---

### 3️⃣ Deploy to Kubernetes

```bash
kubectl apply -f k8s/
```

---

### 4️⃣ Verify Services

```bash
kubectl get pods
```

All services should be in **Running** state.

---

### 5️⃣ Access Application

```bash
minikube service gateway
```

---

### 6️⃣ Run Frontend

```bash
cd frontend
python3 -m http.server 5500
```

Open:

```
http://127.0.0.1:5500
```

---

## 🧪 Testing the System

* Open multiple browser tabs
* Join same room
* Send messages
* Observe real-time sync across tabs

---

## 💥 Fault Tolerance Demo

```bash
kubectl delete pod -l app=websocket-service
```

👉 Pods restart automatically and chat continues working.

---

## 🧠 Key Design Decisions

### Why Kafka?

* Decouples services
* Enables asynchronous communication
* Scales horizontally

### Why Redis?

* Enables real-time pub/sub across WebSocket instances

### Why Kubernetes?

* Self-healing (auto restart pods)
* Horizontal scaling
* Service discovery

---

## 📈 Scalability

* WebSocket service runs multiple replicas
* Redis ensures synchronization across instances
* Kafka handles high-throughput messaging

---

## 🏆 What This Project Demonstrates

* Distributed systems design
* Microservices architecture
* Event-driven systems
* Real-time communication
* Kubernetes deployment & scaling
* Debugging production-like issues

---


## 👨‍💻 Author

**Aman Gautam**

---

## ⭐ If you like this project

Give it a star ⭐ and feel free to fork!

---
