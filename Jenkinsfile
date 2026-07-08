pipeline {
    agent any

    options {
        skipDefaultCheckout(true)
        timestamps()
    }

    environment {
        APP_IMAGE = 'crud-produto:latest'
        K8S_CONTEXT = 'docker-desktop'
        K8S_NAMESPACE = 'estudo-jenkins'
        APP_DEPLOYMENT = 'crud-produto-app'
        POSTGRES_DEPLOYMENT = 'postgres'
        HEALTH_URL = 'http://host.docker.internal:30080/actuator/health'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
                sh '''
                  echo "Workspace atual:"
                  pwd
                  echo "Arquivos na raiz:"
                  ls -la
                '''
            }
        }

        stage('Build Maven') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }

        stage('Tests') {
            steps {
                sh 'mvn test'
            }
        }

        stage('Build Docker Image') {
            steps {
                sh 'docker build -t ${APP_IMAGE} .'
            }
        }

        stage('Verify Kubernetes Access') {
            steps {
                sh '''
                  kubectl version --client
                  kubectl --context ${K8S_CONTEXT} get nodes
                '''
            }
        }

        stage('Deploy Kubernetes') {
            steps {
                sh '''
                  kubectl --context ${K8S_CONTEXT} apply -f k8s/
                '''
            }
        }

        stage('Rollout Status') {
            steps {
                sh '''
                  kubectl --context ${K8S_CONTEXT} rollout status deployment/${POSTGRES_DEPLOYMENT} -n ${K8S_NAMESPACE} --timeout=180s
                  kubectl --context ${K8S_CONTEXT} rollout status deployment/${APP_DEPLOYMENT} -n ${K8S_NAMESPACE} --timeout=180s
                '''
            }
        }

        stage('Health Check') {
            steps {
                sh '''
                  echo "Pods:"
                  kubectl --context ${K8S_CONTEXT} get pods -n ${K8S_NAMESPACE} -o wide

                  echo "Services:"
                  kubectl --context ${K8S_CONTEXT} get svc -n ${K8S_NAMESPACE}

                  echo "Testando aplicação em ${HEALTH_URL}"
                  for i in $(seq 1 30); do
                    if curl -fsS ${HEALTH_URL}; then
                      echo "Aplicação respondeu com sucesso."
                      exit 0
                    fi
                    echo "Aguardando aplicação responder... tentativa $i"
                    sleep 3
                  done

                  echo "Aplicação não respondeu no health check. Últimos logs:"
                  kubectl --context ${K8S_CONTEXT} logs deployment/${APP_DEPLOYMENT} -n ${K8S_NAMESPACE} --tail=120 || true
                  exit 1
                '''
            }
        }
    }

    post {
        success {
            echo 'Deploy no Kubernetes finalizado com sucesso.'
        }
        failure {
            echo 'Pipeline falhou. Verifique os logs do Jenkins.'
        }
    }
}
