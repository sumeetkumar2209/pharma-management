### 1. Before starting server:
Run the following command in frontend folder
`npm run build-production`

### 2. Starting UI server
In frontend-server folder use command `npm start` to start the server
Server starts on http://localhost:8080/

### 3. Running prod server
Change server and backend link as desired
`NODE_ENV=prod BACKEND=http://localhost:9010  SERVER_PORT=9000 npm start`

logs can be found in frontend-server/logs/server.log
### P.S For deployment only the frontend-server folder with compiled UI in dist folder is required