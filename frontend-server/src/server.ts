import express from "express";
import { logger } from "./logUtil";
import dotenv from "dotenv";
import axios from 'axios';
import * as path from 'path';
import Helmet from "helmet";

// initialize environment configuration
dotenv.config();

// port is now available to the Node.js runtime
// as if it were an environment variable
// const port = process.env.SERVER_PORT? process.env.SERVER_PORT : 8080;
const port = 8080;
const baseBackendUrl = process.env.NODE_ENV === 'dev' ? 'http://localhost:9010' : process.env.BACKEND;

const app = express();

//Security settings
app.use(Helmet({
    contentSecurityPolicy: false,
}));
app.disable('x-powered-by');

// to parse incoming request body
app.use(express.json());

// Route request to backend with headers, body etc
app.use('/medley/*', (req, res) => {
    try {
        logger.debug(`Headers: ${JSON.stringify(req.headers)}`);
        const reqObj = {
            url: `${baseBackendUrl}${req.originalUrl}`,
            method: req.method,
            headers: req.headers,
            data: {}
        }

        if ((req.method === 'POST' || req.method === 'PUT') && req.body) {
            reqObj.data = JSON.stringify(req.body)
        }

        logger.info(`Forwarding response to ${reqObj.url} as a ${reqObj.method} with body ${JSON.stringify(reqObj.data)} and header ${JSON.stringify(reqObj.headers)}`);
        axios(reqObj).then((response) => {
            res.status(response.status).send(response.data);
        })
            .catch((error) => {
                if (error.response) {
                    logger.info(`Error: response received: ${error.response.status} ${error.response.data}`)
                    res.status(error.response.status).send(error.response.data);
                } else if (error.request) {
                    logger.info(`Error: no response received`);
                    res.status(502).json({ message: 'no response received' });
                } else {
                    res.status(500).json('Internal server error in backend');
                }
            });;

    } catch (error) {
        logger.error(`Error ocurred ${JSON.stringify(error)}`);
    }

})

// basic healthcheck endpoint
app.get("/healthCheck", (req, res) => {
    res.send("AOK!");
});



// custom error handler
app.use((err: any, req: any, res: any, next: any) => {
    logger.error(err.stack);
    res.status(500).send('Something broke!')
})

// Host static angular files
app.use(express.static('dist/reiphy-pharma/'));

// Send all not found routes to UI 
app.get('*', (req, res) => {
    res.sendFile(path.resolve('dist/reiphy-pharma/index.html'));
});

// start the Express server
app.listen(port, () => {
    logger.info(`Server started at http://localhost:${port}`);
});