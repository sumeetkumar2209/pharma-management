#!/usr/bin/env bash
echo "Workspace: $1"
cd frontend
echo "Installing angular dependencies"
npm ci
echo "Building angular project, with output path $1/frontend-server/dist/reiphy-pharma"
npm run build-production -- --output-path $1/frontend-server/dist/reiphy-pharma
cd ../frontend-server
echo "Installing frontend server dependencies"
npm ci
echo "Building frontend server"
npm run build --if-present