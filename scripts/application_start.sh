#!/bin/bash
sudo rm -rf /home/ec2-user/spw/build
sudo npm run build
forever stopall
forever start -c "node -r esm" Server.js