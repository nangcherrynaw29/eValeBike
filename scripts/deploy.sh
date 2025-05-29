#!/bin/bash
#
# Author: Your Name
# Description: Deploys the team-4 application to the Azure VM using SSH and Docker Compose.
# Created: 2025-05-06
# Usage: bash deploy.sh

AZURE_USER="team4"
AZURE_HOST="4.180.13.98"
AZURE_APP_PATH="/home/team4/team-4"

echo "starting deployment on azure vm ($AZURE_USER@$AZURE_HOST)..."
PROJECT_ROOT=$(dirname "$(dirname "$(realpath "$0")")")
cd "$PROJECT_ROOT" || {
  echo "❌ Failed to change to project root directory."
  exit 1
}

# --- DEPLOY REMOTELY OVER SSH ---
export SSH_CONFIG_PATH=/home/team4/.ssh/config  # Update this line to use the correct config path

ssh -F $SSH_CONFIG_PATH "$AZURE_USER@$AZURE_HOST" << EOF
  set -e

  echo "Pulling latest code..."
  cd "$AZURE_APP_PATH"
  git pull origin main

  echo "Stopping existing containers..."
  docker-compose down || true

  echo "Building and starting containers..."
  docker-compose up --build -d

  echo "Deployment successful."
  docker ps
EOF

