# Use an official Ubuntu as a parent image
FROM ubuntu:20.04
ENV DEBIAN_FRONTEND=noninteractive

# Set environment variables
ENV LANG=C.UTF-8 \
    LC_ALL=C.UTF-8 \
    TERM=xterm-256color \
    PYTHONUNBUFFERED=1

# Update the package repository and install essential packages
RUN apt-get update && apt-get install -y \
    sudo \
    python3 \
    python3-pip \
    python3-venv \
    curl \
    vim \
    less \
    locales \
    man \
    htop \
    tmux \
    openjdk-11-jdk \
    g++ \
    && apt-get clean && rm -rf /var/lib/apt/lists/*

# Generate locales
RUN locale-gen en_US.UTF-8

# Create a non-root user with sudo privileges
RUN useradd -ms /bin/bash bluecode && \
    echo 'bluecode ALL=(ALL) NOPASSWD:ALL' >> /etc/sudoers

# Set the default working directory
WORKDIR /home/bluecode

# Set the non-root user as the default user
USER bluecode

# Set the shell to bash
SHELL ["/bin/bash", "-c"]

# Define the default command to start the shell
CMD ["/bin/bash"]
