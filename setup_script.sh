#!/bin/bash
#setup.sh - Setup for Evalebike on AlmaLinux9 Azure VM
#Target: AlmaLinux 9, West Europe Azure, Gen2
#Author: abir.belhadj@student.kdg.be
#Description: Install Docker, Nginx, Certbot, sets firewall

set -euo pipefai

log() {
  echo -e "\n ->  \033[1;32m$1\033[0m\n"
}l

log “Updating sustem… “
sudo dnf -y update

log "Installing EPEL..."
sudo dnf install -y epel-release

log "Installing Docker, NGINX, Certbot, and utilities..."
sudo dnf install -y \
  nginx \
  docker \
  python3-certbot-nginx \
  firewalld \
  curl \
  git \
  unzip

log "Enabling Docker and NGINX..."
sudo systemctl enable --now docker
sudo systemctl enable --now nginx

log "Enabling firewalld..."
sudo systemctl enable --now firewalld

log "Configuring firewall: allow SSH (22), HTTP (80), HTTPS (443)..."
sudo firewall-cmd --permanent --add-service=ssh
sudo firewall-cmd --permanent --add-service=http
sudo firewall-cmd --permanent --add-service=https
sudo firewall-cmd --reload

log "Setup complete. Ready for Dockerized app deployment."
