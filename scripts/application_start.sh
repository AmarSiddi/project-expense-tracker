#!/bin/bash
cd /home/ec2-user/spw/expense
sudo npm install
sudo npm run App
forever stopall
forever start -c "node -r esm" Server.js