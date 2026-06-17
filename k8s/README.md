# Kubernetes local

Esta pasta contém os manifests para rodar o CRUD no Kubernetes local.

Ordem dos arquivos:

```text
00-namespace.yaml
01-postgres-secret.yaml
02-postgres.yaml
03-app-configmap.yaml
04-app.yaml
```

Para aplicar manualmente:

```bash
kubectl apply -f k8s/
kubectl get pods -n estudo-jenkins
kubectl get svc -n estudo-jenkins
```

A aplicação fica exposta por NodePort em:

```text
http://localhost:30080
```

Health check:

```bash
curl http://localhost:30080/actuator/health
```

Para remover:

```bash
kubectl delete namespace estudo-jenkins
```
