package com.serial;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;
import com.fazecast.jSerialComm.SerialPortPacketListener;

public class Main {
    static String deviceName = "/dev/ttyACM0";

    public static void main(String[] args) {
        final SerialPort scanner = SerialPort.getCommPort(deviceName);
        scanner.setBaudRate(268435456);
        scanner.setParity(0);
        scanner.setNumStopBits(1);
        scanner.setNumDataBits(8);
        scanner.setRTS();
        scanner.openPort();
        PacketListener listener = new PacketListener();
        scanner.addDataListener(listener);
        try {
            Thread.sleep(50000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        scanner.removeDataListener();
        scanner.closePort();
    }

    private static final class PacketListener implements SerialPortPacketListener
    {
        @Override
        public int getListeningEvents() { return SerialPort.LISTENING_EVENT_DATA_RECEIVED; }

        @Override
        public int getPacketSize() { return 0; }

        @Override
        public void serialEvent(SerialPortEvent event)
        {
            byte[] newData = event.getReceivedData();
            System.out.println("Received data of size: " + newData.length);
            for (int i = 0; i < newData.length; ++i)
                System.out.print((char)newData[i]);
            System.out.println("\n");
        }
    }
}
