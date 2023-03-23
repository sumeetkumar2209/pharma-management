import express from "express";
import { logger } from "./logUtil";
import dotenv from "dotenv";
import axios from 'axios';
import * as path from 'path';

// initialize configuration
dotenv.config();

// port is now available to the Node.js runtime
// as if it were an environment variable
const port = process.env.SERVER_PORT;

const app = express();
const baseBackendUrl = 'http://localhost:9010'

app.use('/medley/*', async (req, res) => {
    try {
        logger.debug(`Headers: ${JSON.stringify(req.headers)}`);
        const reqObj = {
            url: `${baseBackendUrl}${req.originalUrl}`,
            method: req.method,
            headers: req.headers,
            body: {}
        }
        if ((req.method === 'POST' || req.method === 'PUT') && req.body) {
            reqObj.body = req.body
        }
        logger.info(`Forwarding response to ${reqObj.url} as a ${reqObj.method} with body ${JSON.stringify(reqObj.body)}`);
        const response = await axios(reqObj);
        res.status(response.status).send(response.data);
    } catch (error) {
        if (error.response) {
            logger.info(`Error: response received: ${error.response.status} ${error.response.data}`)
            res.status(error.response.status).send(error.response.data);
        }else if (error.request){
            logger.info(`Error: no response received`);
            res.status(502).json({ message: 'no response received' });
        }else{
            res.status(500).json('Internal server error in backend');
        }
    }

})

app.get("/healthCheck", (req, res) => {
    res.send("AOK!");
});



app.use(express.static('dist/reiphy-pharma/'));

app.get('*', (req, res) => {
    res.sendFile(path.resolve('dist/reiphy-pharma/index.html'));
});

// start the Express server
app.listen(port, () => {
    logger.info(`Server started at http://localhost:${port}`);
});