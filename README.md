# ☁️ Cloud-Native Spring Boot Application with MySQL on Kubernetes

A containerized **Spring Boot + MySQL** application deployed and managed using **Docker and Kubernetes**.

This project demonstrates how a backend application can be containerized, connected to a MySQL database through Kubernetes Services, and deployed using Kubernetes Deployments.

---

## 🚀 Project Overview

This project contains a **Spring Boot backend application** that communicates with a **MySQL database** running in a separate Kubernetes Pod.

Both the application and database are containerized and deployed using Kubernetes.

## 🏗️ Architecture

```text
                         ┌──────────────────────┐
                         │        Client        │
                         │   Browser / Postman  │
                         └──────────┬───────────┘
                                    │
                                  :8080
                                    │
                                    ▼
                         ┌──────────────────────┐
                         │     spring-svc       │
                         │    ClusterIP Service │
                         └──────────┬───────────┘
                                    │
                                    ▼
                         ┌──────────────────────┐
                         │     Spring Boot      │
                         │         Pod          │
                         │        :8080         │
                         └──────────┬───────────┘
                                    │
                                    │ MySQL :3306
                                    ▼
                         ┌──────────────────────┐
                         │    database-svc      │
                         │    ClusterIP Service │
                         └──────────┬───────────┘
                                    │
                                    ▼
                         ┌──────────────────────┐
                         │      MySQL Pod       │
                         │        :3306         │
                         │       sbms DB        │
                         └──────────────────────┘

```

---

## 🛠️ Technologies Used

| Technology             | Purpose                             |
| ---------------------- | ----------------------------------- |
| ☕ Java                 | Application development             |
| 🌱 Spring Boot         | Backend REST application            |
| 🗄️ MySQL              | Relational database                 |
| 🐳 Docker              | Containerization                    |
| ☸️ Kubernetes          | Container orchestration             |
| 📦 Maven               | Build and dependency management     |
| 🔗 Kubernetes Services | Application and database networking |
| 🐙 GitHub              | Source code management              |

---

## 📁 Project Structure

```text
spring-mysql-kubernetes/
│
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── ...
│   │   │
│   │   └── resources/
│   │       └── application.properties
│   │
│   └── test/
│       └── ...
│
├── k8s/
│   ├── spring.yaml
│   ├── db.yaml
│   └── configmap.yaml
│
├── Dockerfile
├── pom.xml
├── .gitignore
└── README.md

```

---

## ⚙️ Prerequisites

Before running the project, make sure you have the following installed:

- Java 17+
- Maven
- Docker
- Kubernetes
- kubectl
- Git
---

# 🏃 Getting Started

Follow these steps to run the project on a Kubernetes cluster.

## ⚙️ Prerequisites

Make sure you have:

- Kubernetes cluster
- `kubectl`
- Git

Check your Kubernetes cluster:

```bash
kubectl get nodes
```

Make sure the node status is:

```text
Ready
```

---

## 1. Clone the Repository

Clone the project:

```bash
git clone https://github.com/YOUR_USERNAME/YOUR_REPOSITORY.git
```

Navigate into the project:

```bash
cd spring-mysql-kubernetes
```

---

## 2. Deploy MySQL

Apply the MySQL Kubernetes configuration:

```bash
kubectl apply -f k8s/db.yaml
```

Check the MySQL Pod:

```bash
kubectl get pods
```

Check the MySQL Service:

```bash
kubectl get svc
```

Wait until the MySQL Pod shows:

```text
Running
```

---

## 3. Deploy Spring Boot

Apply the Spring Boot Kubernetes configuration:

```bash
kubectl apply -f k8s/spring.yaml
```

Kubernetes will automatically pull the Spring Boot Docker image from Docker Hub:

```yaml
image: aryan2311023/spring_mysql:1.0
```

Check the Pods:

```bash
kubectl get pods
```

You should see something similar to:

```text
NAME                         READY   STATUS
database-xxxxxxxxx           1/1     Running
spring-xxxxxxxxx             1/1     Running
```

---

## 4. Check Kubernetes Services

Run:

```bash
kubectl get svc
```

You should see:

```text
NAME            TYPE        PORT
database-svc    ClusterIP   3306
spring-svc      ClusterIP   8080
```

The Spring Boot application connects to MySQL using:

```text
jdbc:mysql://database-svc:3306/sbms
```

Kubernetes automatically resolves `database-svc` to the MySQL Service.

---

## 5. Verify the Application

Check all resources:

```bash
kubectl get all
```

Check Spring Boot logs:

```bash
kubectl logs deployment/spring
```

Check MySQL logs:

```bash
kubectl logs deployment/database
```

---

## 6. Access the Spring Boot Application

Because `spring-svc` is a `ClusterIP` Service, use port forwarding for local testing:

```bash
kubectl port-forward svc/spring-svc 8080:8080
```

Then open:

```text
http://localhost:8080
```

You can also test the application using Postman or:

```bash
curl http://localhost:8080
```

---


# 🛑 Remove the Application

Remove the Spring Boot application:

```bash
kubectl delete -f k8s/spring.yaml
```

Remove MySQL:

```bash
kubectl delete -f k8s/db.yaml
```


# 🔗 Kubernetes Networking

Spring Boot communicates with MySQL through the Kubernetes Service:

```text
┌─────────────────────┐
│   Spring Boot Pod   │
└──────────┬──────────┘
           │
           │ jdbc:mysql://
           │ database-svc:3306/sbms
           ▼
┌─────────────────────┐
│    database-svc     │
│       :3306         │
└──────────┬──────────┘
           │
           ▼
┌─────────────────────┐
│      MySQL Pod      │
│       :3306         │
└─────────────────────┘

```

Kubernetes DNS provides service discovery.

Instead of connecting to a Pod IP address, Spring Boot connects to:

```text
database-svc

```

This is useful because Pod IP addresses can change when Pods are recreated.

---

# 🔐 Environment Variables

The Spring Boot application receives database configuration through environment variables:

```yaml
env:
  - name: MYSQL_URL
    value: From ConfigMap

  - name: MYSQL_USERNAME
    value: From Secrets

  - name: MYSQL_PASSWORD
    value: From Secrets

```

Spring Boot reads these values from `application.properties`:

```properties
spring.datasource.url=${MYSQL_URL}
spring.datasource.username=${MYSQL_USERNAME}
spring.datasource.password=${MYSQL_PASSWORD}

```



---


# 📌 Current Kubernetes Components

This project currently demonstrates:

- ☸️ Kubernetes Deployments
- 🔗 Kubernetes Services
- 🌐 ClusterIP networking
- 🔍 Kubernetes DNS / service discovery
- 🐳 Containerized Spring Boot application
- 🗄️ Containerized MySQL database
- ⚙️ Environment-based configuration
- 📦 Docker image deployment
- 🔄 Spring Boot → MySQL communication
- 🔐 Kubernetes Secrets
---

# 🔮 Future Improvements

The project can be extended with additional cloud-native and DevOps capabilities:


- 💾 PersistentVolume and PersistentVolumeClaim for MySQL
- 🌐 Kubernetes Ingress
- ❤️ Liveness and Readiness Probes
- 📈 Horizontal Pod Autoscaler
- 🔄 GitHub Actions CI/CD
- 📊 Prometheus and Grafana monitoring
- 📝 Centralized logging
- 🔑 JWT authentication
- 🧪 Automated unit and integration testing
- 🚀 Helm charts
- ☁️ Deployment to AWS / GCP / Azure

---

# 🎯 Learning Objectives

This project provides hands-on experience with:

```text
Spring Boot
     ↓
REST API
     ↓
Docker
     ↓
Kubernetes
     ↓
Deployments
     ↓
Services
     ↓
Service Discovery
     ↓
MySQL

```

The main objective is to understand how a backend application can be **containerized, deployed, networked, and managed using Kubernetes**.

---

# 👨‍💻 Author

**Aryan**

Built with:

☕ **Spring Boot** · 🐳 **Docker** · ☸️ **Kubernetes** · 🗄️ **MySQL**

---
