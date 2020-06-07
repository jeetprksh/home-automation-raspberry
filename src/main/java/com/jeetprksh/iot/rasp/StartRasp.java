package com.jeetprksh.iot.rasp;

import com.jeetprksh.iot.rasp.messaging.RaspConfiguration;
import com.jeetprksh.iot.rasp.messaging.RaspMessageClient;

import java.util.logging.Logger;

/**
 * Created by Jeet Prakash on 2018-01-01.
 */
public class StartRasp {

  private static final Logger logger = Logger.getLogger(StartRasp.class.getName());

  private static final String DEFAULT_BROKER_ADDRESS = "localhost";
  private static final String DEFAULT_BROKER_TOPIC = "iot_data";
  private static final String DEFAULT_BROKER_PORT = "1883";
  private static final String DEFAULT_BROKER_PROTOCOL = "tcp";
  private static final String DEFAULT_SERVER_ADDRESS = DEFAULT_BROKER_ADDRESS;
  private static final String DEFAULT_SERVER_PORT = "8080";

  public static void main(String args[]) {
    RaspConfiguration config = getRaspConfiguration();
    try {
      new RaspMessageClient(config.getBrokerAddress(), config.getBrokerTopic())
              .initMessageClient()
              .stubbornConnect();
                                /*.setMessageCallback()
                                .subscribe();*/
    } catch (Exception e) {
      logger.severe(e.toString());
      e.printStackTrace();
    }
  }

  private static RaspConfiguration getRaspConfiguration() {
    return new RaspConfiguration()
            .setBrokerAddress(System.getProperty("broker.address", DEFAULT_BROKER_ADDRESS))
            .setBrokerPort(System.getProperty("broker.port", DEFAULT_BROKER_PORT))
            .setBrokerProtocol(System.getProperty("broker.protocol", DEFAULT_BROKER_PROTOCOL))
            .setBrokerTopic(System.getProperty("broker.topic", DEFAULT_BROKER_TOPIC))
            .setServerAddress(System.getProperty("server.address", DEFAULT_SERVER_ADDRESS))
            .setServerPort(System.getProperty("server.port", DEFAULT_SERVER_PORT));
  }
}
