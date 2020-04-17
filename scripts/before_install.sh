#!/bin/bash
sudo yum update
sudo yum install -y gcc-c++ make
curl -sL https://rpm.nodesource.com/setup_13.x | sudo -E bash -
sudo yum install -y nodejs
sudo npm install forever -g