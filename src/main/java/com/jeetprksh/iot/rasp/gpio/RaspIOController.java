package com.jeetprksh.iot.rasp.gpio;

import com.pi4j.io.gpio.*;

import java.util.logging.Logger;

/**
 * Created by Jeet Prakash on 2018-01-01.
 */
public class RaspIOController {

  private final Logger logger = Logger.getLogger(RaspIOController.class.getName());

  private final GpioController gpio;
  private final GpioPinDigitalOutput pin;

  public RaspIOController() {
    this.gpio = GpioFactory.getInstance();
    this.pin = gpio.provisionDigitalOutputPin(RaspiPin.GPIO_01, "OfficeLights", PinState.HIGH);
    this.pin.setShutdownOptions(true, PinState.LOW);
  }

  public void pinOn() {
    this.pin.high();
  }

  public void pinOff() {
    this.pin.low();
  }

  public void pinShutdown() {
    this.gpio.shutdown();
  }
}