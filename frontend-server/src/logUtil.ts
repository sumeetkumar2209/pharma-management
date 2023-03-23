import { createLogger, transports, format } from "winston";
import { createWriteStream } from "fs";
export const logger = createLogger({
    transports: [  new transports.File({
        dirname: "logs",
        filename: "server.log",
      }),],
    format: format.combine(

      format.timestamp(),
      format.printf(({ timestamp, level, message,service }) => {
        return `[${timestamp}] ${service} ${level}: ${message}`;
      })
    ),
    defaultMeta: {
        service: "MedleyUIBackend",
      },
  });