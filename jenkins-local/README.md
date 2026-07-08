# Jenkins local com Docker, Maven e kubectl

Esta pasta sobe um Jenkins local com:

- Maven
- Docker CLI
- Docker Compose plugin
- kubectl
- acesso ao Docker da máquina pelo `/var/run/docker.sock`
- acesso ao Kubernetes local pelo kubeconfig montado em `/var/jenkins_home/.kube/config`

O host pode ser Ubuntu. As instalações do `Dockerfile`, porém, são executadas
dentro da imagem do Jenkins, que atualmente é baseada em Debian. O arquivo detecta
Debian ou Ubuntu automaticamente ao configurar o repositório do Docker.

## Pré-requisitos no Ubuntu

```bash
sudo apt-get update
sudo apt-get install -y docker.io docker-compose-v2
sudo usermod -aG docker "$USER"
```

Após adicionar o usuário ao grupo `docker`, encerre e abra novamente a sessão.

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

```bash
docker exec -it jenkins-local cat /var/jenkins_home/secrets/initialAdminPassword
```

## Testar ferramentas dentro do Jenkins

```bash
docker exec -it jenkins-local mvn -version
docker exec -it jenkins-local docker version
docker exec -it jenkins-local docker compose version
docker exec -it jenkins-local kubectl version --client
docker exec -it jenkins-local kubectl get nodes
```

Se `kubectl get nodes` falhar, confirme o contexto e o arquivo usados no host:

```bash
test -f "$HOME/.kube/config"
kubectl config current-context
```

Para usar outro kubeconfig:

```bash
KUBE_CONFIG_PATH=/caminho/para/config docker compose up -d --build
```
