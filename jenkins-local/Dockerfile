FROM jenkins/jenkins:lts

ARG KUBECTL_VERSION=stable

ENV KUBECONFIG=/var/jenkins_home/.kube/config

USER root

SHELL ["/bin/bash", "-o", "pipefail", "-c"]

RUN set -eux; \
    apt-get update; \
    apt-get install --no-install-recommends -y \
        ca-certificates \
        curl \
        gnupg \
        maven; \
    install -m 0755 -d /etc/apt/keyrings && \
    . /etc/os-release; \
    distribution="${ID}"; \
    case "${distribution}" in debian|ubuntu) ;; *) echo "Distribuição não suportada: ${distribution}" >&2; exit 1 ;; esac; \
    curl -fsSL "https://download.docker.com/linux/${distribution}/gpg" \
        -o /etc/apt/keyrings/docker.asc; \
    chmod a+r /etc/apt/keyrings/docker.asc; \
    echo "deb [arch=$(dpkg --print-architecture) signed-by=/etc/apt/keyrings/docker.asc] https://download.docker.com/linux/${distribution} ${VERSION_CODENAME} stable" \
        > /etc/apt/sources.list.d/docker.list; \
    apt-get update; \
    apt-get install --no-install-recommends -y docker-ce-cli docker-compose-plugin; \
    rm -rf /var/lib/apt/lists/*

RUN set -eux; \
    architecture="$(dpkg --print-architecture)"; \
    case "${architecture}" in amd64|arm64) ;; *) echo "Arquitetura não suportada: ${architecture}" >&2; exit 1 ;; esac; \
    if [[ "${KUBECTL_VERSION}" == "stable" ]]; then \
        resolved_version="$(curl -fsSL https://dl.k8s.io/release/stable.txt)"; \
    else \
        resolved_version="${KUBECTL_VERSION}"; \
    fi; \
    curl -fsSL "https://dl.k8s.io/release/${resolved_version}/bin/linux/${architecture}/kubectl" \
        -o /usr/local/bin/kubectl; \
    chmod 0755 /usr/local/bin/kubectl; \
    kubectl version --client

RUN install -d -o jenkins -g jenkins /var/jenkins_home/.kube

USER jenkins
