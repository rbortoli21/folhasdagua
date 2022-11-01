//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.folhasdagua.folhasdagua.service;

import jssc.SerialPort;
import jssc.SerialPortException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class SensorConnectService {

    SerialPort serialPort;
    String port;

    public SensorConnectService(String port) {
        this.port = port;
        this.serialPort = new SerialPort(port);
    }

    public SensorConnectService() {
    }

    public ResponseEntity<SerialPort> setPort(String port) throws SerialPortException {
        return this.serialPort.isOpened() ? ResponseEntity.ok(this.serialPort = new SerialPort(port)) : ResponseEntity.notFound().build();
    }

    public ResponseEntity<Boolean> connect() throws SerialPortException {
        if(serialPort.isOpened()){
            disconnect();
        }
        this.serialPort.openPort();
        this.serialPort.setParams(9600, 8, 1, 0);

        return ResponseEntity.ok(this.serialPort.isOpened());
    }

    public ResponseEntity<Boolean> disconnect() throws SerialPortException {
        if (this.serialPort.isOpened()) {
            this.serialPort.closePort();
        }
        return ResponseEntity.ok(!this.serialPort.isOpened());
    }

    public String read(Integer byteCount) throws SerialPortException {
        return serialPort.readString(byteCount);
    }
}
