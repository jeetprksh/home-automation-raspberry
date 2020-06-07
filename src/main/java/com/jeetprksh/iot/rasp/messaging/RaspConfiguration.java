package com.jeetprksh.iot.rasp.messaging;

/**
 * Created by Jeet Prakash on 2018-01-04.
 */
public class RaspConfiguration {

  private String serverAddress;
  private String serverPort;
  private String brokerAddress;
  private String brokerPort;
  private String brokerProtocol;
  private String brokerTopic;

  public String getServerAddress() {
    return serverAddress;
  }

  public RaspConfiguration setServerAddress(String serverAddress) {
    this.serverAddress = serverAddress;
    return this;
  }

  public String getServerPort() {
    return serverPort;
  }

  public RaspConfiguration setServerPort(String serverPort) {
    this.serverPort = serverPort;
    return this;
  }

  public String getBrokerAddress() {
    return brokerAddress;
  }

  public RaspConfiguration setBrokerAddress(String brokerAddress) {
    this.brokerAddress = brokerAddress;
    return this;
  }

  public String getBrokerPort() {
    return brokerPort;
  }

  public RaspConfiguration setBrokerPort(String brokerPort) {
    this.brokerPort = brokerPort;
    return this;
  }

  public String getBrokerProtocol() {
    return brokerProtocol;
  }

  public RaspConfiguration setBrokerProtocol(String brokerProtocol) {
    this.brokerProtocol = brokerProtocol;
    return this;
  }

  public String getBrokerTopic() {
    return brokerTopic;
  }

  public RaspConfiguration setBrokerTopic(String brokerTopic) {
    this.brokerTopic = brokerTopic;
    return this;
  }
}
