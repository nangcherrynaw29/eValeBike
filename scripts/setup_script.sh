#!/bin/bash
# setup.sh - Setup for Evalebike on AlmaLinux9 Azure VM
# Target: AlmaLinux 9, West Europe Azure, Gen2
# Author: abir.belhadj@student.kdg.be
# Description: Install Docker (from Docker repo), NGINX, Certbot, sets firewall

set -euo pipefail

log() {
  echo -e "\n ->  \033[1;32m$1\033[0m\n"
}

if [[ "$EUID" -ne 0 ]]; then
  echo "Please run as root or with sudo."
  exit 1
fi

log "Updating system..."
dnf -y update

log "Installing EPEL..."
dnf install -y epel-release

log "Setting up Docker repository..."
dnf install -y dnf-plugins-core
dnf config-manager --add-repo https://download.docker.com/linux/centos/docker-ce.repo

log "Removing podman-docker if present to avoid conflict..."
dnf remove -y podman-docker || true

log "Installing Docker from Docker's official repo..."
dnf install -y docker-ce docker-ce-cli containerd.io docker-buildx-plugin docker-compose-plugin

log "Installing NGINX, Certbot, and utilities..."
dnf install -y \
  nginx \
  python3-certbot-nginx \
  firewalld \
  curl \
  git \
  unzip

log "Enabling and starting Docker and NGINX..."
systemctl enable --now docker
systemctl enable --now nginx

log "Enabling firewalld..."
systemctl enable --now firewalld

log "Configuring firewall: allowing SSH (22), HTTP (80), HTTPS (443)..."
firewall-cmd --permanent --add-service=ssh
firewall-cmd --permanent --add-service=http
firewall-cmd --permanent --add-service=https
firewall-cmd --reload

log "Verifying Docker installation..."
docker version
docker compose version

log "Setup complete. Ready for Dockerized app deployment."
