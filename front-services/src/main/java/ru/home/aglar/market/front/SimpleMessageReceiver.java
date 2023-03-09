package ru.home.aglar.market.front;

import org.springframework.stereotype.Component;

@Component
public class SimpleMessageReceiver {
    public void receiveMessage(byte[] message) {
        System.out.println("Received from topic <" + new String(message) + ">");
    }
}
