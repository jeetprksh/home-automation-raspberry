package com.jeetprksh.iot.rasp.messaging;

import com.jeetprksh.iot.rasp.gpio.RaspIOController;

import org.eclipse.paho.client.mqttv3.*;

import java.util.Objects;
import java.util.logging.Logger;

/**
 * Created by Jeet Prakash on 2018-01-01.
 */
public class RaspMessageClient {

  private final Logger logger = Logger.getLogger(RaspMessageClient.class.getName());

  private MqttClient client;
  private final String brokerHost;
  private final String brokerTopic;

  public RaspMessageClient(String brokerHost, String brokerTopic) {
    this.brokerHost = brokerHost;
    this.brokerTopic = brokerTopic;
  }

  public RaspMessageClient initMessageClient() throws Exception {
    if (Objects.isNull(this.client)) {
      this.client = new MqttClient("tcp://" + this.brokerHost + ":1883", MqttClient.generateClientId());
    }
    return this;
  }

  public void publishMessage(String message, String topic) throws Exception {
    MqttMessage mqttMessage = new MqttMessage();
    mqttMessage.setPayload(message.getBytes());
    this.client.publish(topic, mqttMessage);
  }

  public RaspMessageClient stubbornConnect() {
    this.logger.severe("Attempting to connect to broker.");
    Thread connectionTask = new Thread(new ConnectionRetryTask(RaspMessageClient.this));
    connectionTask.start();
    return this;
  }

  public RaspMessageClient connect() throws MqttException {
    this.client.connect();
    return this;
  }

  public RaspMessageClient subscribe() throws MqttException {
    this.client.subscribe(this.brokerTopic);
    return this;
  }

  public RaspMessageClient reconnect() throws MqttException {
    this.client.reconnect();
    return this;
  }

  public RaspMessageClient setMessageCallback() {
    this.client.setCallback(new MessageCallback());
    return this;
  }

  public boolean isConnected() {
    return this.client.isConnected();
  }

  private class MessageCallback implements MqttCallback {

    private final Logger logger = Logger.getLogger(MessageCallback.class.getName());

    private RaspIOController raspIOController;
    private final String LIGHTS_ON_MESSAGE = "lights-on";
    private final String LIGHTS_OFF_MESSAGE = "lights-off";

    public MessageCallback() {
      this.raspIOController = new RaspIOController();
    }

    @Override
    public void connectionLost(Throwable throwable) {
      this.logger.severe("Connection to MQTT broker lost. Trying to connect again.");
      Thread reconnectTask = new Thread(new ConnectionRetryTask(RaspMessageClient.this));
      reconnectTask.start();
    }

    @Override
    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
      String message = new String(mqttMessage.getPayload());
      this.logger.info("Message received:: " + message);
      if (message.equalsIgnoreCase(LIGHTS_OFF_MESSAGE)) {
        this.logger.info("Turning lights off");
        this.raspIOController.pinOff();
      } else if (message.equalsIgnoreCase(LIGHTS_ON_MESSAGE)) {
        this.logger.info("Turning lights on");
        this.raspIOController.pinOn();
      }
    }

    @Override
    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
      this.logger.info("Delivery Complete");
    }

  }
}