#!/bin/bash

echo "🔧 Installing Java 21, Maven, and Docker..."

# Update and install OpenJDK 21
sudo apt update
sudo apt install -y openjdk-21-jdk

# Verify Java
java -version

# Install Maven
sudo apt install -y maven
mvn -v

# Install Docker
sudo apt install -y docker.io

# Add current user to docker group (you may need to reboot after this)
sudo usermod -aG docker $USER

# Enable Docker service
sudo systemctl enable docker
sudo systemctl start docker

echo "Setup complete. Please reboot to apply docker permissions."
