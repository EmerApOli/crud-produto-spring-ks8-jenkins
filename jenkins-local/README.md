# Jenkins local com Docker, Maven e kubectl

Esta pasta sobe um Jenkins local com:

- Maven
- Docker CLI
- Docker Compose plugin
- kubectl
- acesso ao Docker da máquina pelo `/var/run/docker.sock`
- acesso ao Kubernetes local pelo kubeconfig montado em `/root/.kube`

## Subir Jenkins

No Linux, execute dentro desta pasta:

```bash
test -f "$HOME/.kube/config"
kubectl config current-context
docker compose down
docker compose up -d --build
```

Acesse:

```text
http://localhost:8085
```

Senha inicial:

```powershell
docker exec -it jenkins-local cat /var/jenkins_home/secrets/initialAdminPassword
```

## Testar ferramentas dentro do Jenkins

```powershell
docker exec -it jenkins-local mvn -version
docker exec -it jenkins-local docker version
docker exec -it jenkins-local kubectl version --client
docker exec -it jenkins-local kubectl get nodes
```

Se `kubectl get nodes` falhar, confira se o Kubernetes está habilitado no Docker Desktop e se o arquivo abaixo existe:

```text
C:\Users\Dell\.kube\config
```

No Docker Desktop, habilite Kubernetes em:

```text
Settings > Kubernetes > Enable Kubernetes
```
