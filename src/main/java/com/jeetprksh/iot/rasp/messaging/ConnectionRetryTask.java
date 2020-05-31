package com.jeetprksh.iot.rasp.messaging;

import java.util.logging.Logger;

public class ConnectionRetryTask implements Runnable {

    private static final Logger logger = Logger.getLogger(ConnectionRetryTask.class.getName());

    RaspMessageClient messageClient;

    ConnectionRetryTask(RaspMessageClient messageClient) {
        this.messageClient = messageClient;
    }

    public void run() {
        for(int i = 0;  ; i++) {
            if (!messageClient.isConnected()) {
                this.logger.info("Try connection attempt " + i);
                try {
                    Thread.sleep(7000);
                    messageClient.connect();
                    messageClient.setMessageCallback();
                    messageClient.subscribe();
                } catch (Exception e) {
                    this.logger.severe(e.toString());
                }
            } else {
                this.logger.info("Connected on retry " + i);
                break;
            }
        }
    }

}
