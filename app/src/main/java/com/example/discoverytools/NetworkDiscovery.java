package com.example.discoverytools;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

public class NetworkDiscovery {

    // Função para verificar se um host está ativo através do Ping
    public static boolean isHostAlive(String ipAddress) {
        try {
            InetAddress inet = InetAddress.getByName(ipAddress);
            return inet.isReachable(1000);  // Timeout de 1 segundo
        } catch (IOException e) {
            return false;
        }
    }

    // Função para realizar um Ping Sweep em uma sub-rede
    public static void pingSweep(String subnet) {
        String baseIP = subnet.substring(0, subnet.lastIndexOf(".") + 1);
        for (int i = 1; i < 255; i++) {
            String ip = baseIP + i;
            if (isHostAlive(ip)) {
                System.out.println("Host ativo: " + ip);
            }
        }
    }

    // Função para verificar se uma porta está aberta
    public static boolean isPortOpen(String ipAddress, int port) {
        try (Socket socket = new Socket(ipAddress, port)) {
            return true;
        } catch (IOException e) {
            return false;
        }
    }

    // Função para escanear portas abertas em um host
    public static void scanPorts(String ipAddress) {
        for (int port = 1; port <= 1024; port++) {
            if (isPortOpen(ipAddress, port)) {
                System.out.println("Porta aberta: " + port);
            }
        }
    }
}
