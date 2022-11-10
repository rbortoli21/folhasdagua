//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.folhasdagua.folhasdagua.service;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import jssc.SerialPortEventListener;
import jssc.SerialPortException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.util.Arrays;

import static com.fazecast.jSerialComm.SerialPort.TIMEOUT_READ_SEMI_BLOCKING;


@Service
public class SensorConnectService {

    String value;
    SerialPort serialPort;

    public SensorConnectService() {
        serialPort = SerialPort.getCommPorts()[0];

    }

    public ResponseEntity<Boolean> connect() {
        if (!serialPort.isOpen()) {
            serialPort.openPort();
            serialPort.setComPortTimeouts(TIMEOUT_READ_SEMI_BLOCKING, 0, 0);
        }
        return ResponseEntity.ok(serialPort.isOpen());
    }

    public ResponseEntity<Boolean> disconnect() {
        if (serialPort.isOpen()) {
            serialPort.closePort();
        }
        return ResponseEntity.ok(serialPort.isOpen());
    }

    public String read() {
        serialPort.openPort();
        serialPort.addDataListener(new SerialPortDataListener() {
            @Override
            public int getListeningEvents() {
                return SerialPort.LISTENING_EVENT_DATA_RECEIVED;
            }
            @Override
            public void serialEvent(SerialPortEvent event) {
                byte[] newData = event.getReceivedData();
                setValueOut("");
                for (int i = 0; i < newData.length; ++i){
                    setValueOut(value += (char)newData[i]);
                }
            }
        });
        System.out.println("Final Value: " + getValueOut());
        return getValueOut();
    }

    public void setValueOut(String value){
        this.value = value;
    }

    public String getValueOut(){
        return this.value;
    }
}