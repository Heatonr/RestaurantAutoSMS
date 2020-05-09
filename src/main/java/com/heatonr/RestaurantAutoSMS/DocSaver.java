package com.heatonr.RestaurantAutoSMS;
import javafx.application.Platform;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.sax.BodyContentHandler;
import org.xml.sax.ContentHandler;

import javax.sound.sampled.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.Time;
import java.sql.Timestamp;

public class DocSaver extends Thread {
    private Robot robot;
    private SMSSender sender;
    private SMSGUI gui;
    private boolean stopWork;

    public DocSaver(SMSSender pSender, SMSGUI pGUI) {
        sender = pSender;
        gui = pGUI;
    }

    @Override
    public void run() {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            sendMessage(e.getMessage());
            e.printStackTrace();
        }

        while(!stopWork) {

            Color pixelColor = robot.getPixelColor(1620, 970);
            if(pixelColor.equals(new Color(225,225,225))){
                try {
                    saveDocument();
                } catch (Exception e) {
                    sendMessage(e.getMessage());
                    e.printStackTrace();
                }
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                sendMessage(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private void saveDocument() throws Exception {
        robot.keyPress(KeyEvent.VK_O);
        Thread.sleep(50);
        robot.keyPress(KeyEvent.VK_R);
        Thread.sleep(50);
        robot.keyPress(KeyEvent.VK_D);
        Thread.sleep(50);
        robot.keyPress(KeyEvent.VK_E);
        Thread.sleep(50);
        robot.keyPress(KeyEvent.VK_R);
        Thread.sleep(50);
        robot.keyPress(KeyEvent.VK_TAB);
        Thread.sleep(50);
        robot.keyPress(KeyEvent.VK_DOWN);
        Thread.sleep(50);
        robot.keyPress(KeyEvent.VK_DOWN);
        Thread.sleep(50);
        robot.keyPress(KeyEvent.VK_TAB);
        Thread.sleep(50);
        robot.keyPress(KeyEvent.VK_ENTER);
        Thread.sleep(50);
        robot.keyPress(KeyEvent.VK_TAB);
        Thread.sleep(50);
        robot.keyPress(KeyEvent.VK_ENTER);

        Thread.sleep(2000);

        File xpsFile = new File("C:/Users/Heato/Desktop/order.xps");
        InputStream inputStream = new FileInputStream(xpsFile);

        Metadata metadata = new Metadata();
        ContentHandler handler = new BodyContentHandler();

        new XPSParser().parse(inputStream, handler, metadata, new ParseContext());
        String docContents = handler.toString();
        System.out.println(docContents);
        inputStream.close();

        scanOrder(docContents);
        String recipientNum = SMSGUI.customerObservableList.get(SMSGUI.customerObservableList.size() - 1).getPhoneNumber();
        if(sender.sendMessage(recipientNum, "Your order has been submitted.\nThe current pickup wait time is "
                + (int) gui.getWaitTime() + " minutes, as of " + new Timestamp(System.currentTimeMillis()).toString().substring(11,16) +". You will receive a text when the order is ready.")) {

            File file = new File("Beeping-sound.wav");
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(file);

            AudioFormat format = audioStream.getFormat();
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            Clip audioClip = (Clip) AudioSystem.getLine(info);

            audioClip.open(audioStream);
            audioClip.start();
        }
        else{
            SMSGUI.customerObservableList.remove(SMSGUI.customerObservableList.size() - 1);
        }
    }

    public void scanOrder(String input){
        StringBuilder name = new StringBuilder();
        StringBuilder phoneNumber = new StringBuilder();
        while(input.length() > 6){
            if(input.substring(0,6).equals("Phone:")){
                input = input.substring(6);
                while(Character.isWhitespace(input.charAt(0)) || Character.isDigit(input.charAt(0))
                        || (input.charAt(0) == '-')){
                    if(Character.isDigit(input.charAt(0))){
                        phoneNumber.append(Integer.parseInt(input.substring(0, 1)));
                    }
                    input = input.substring(1);
                }
            }
            else if(input.substring(0,5).equals("Name:")){
                input = input.substring(6);
                while(input.length() > 0 && input.charAt(0) != '\n'){
                    name.append(input.charAt(0));
                    input = input.substring(1);
                }
            }
            else {
                input = input.substring(1);
            }
        }
        SMSGUI.customerObservableList.add(new Customer(phoneNumber.toString(), name.toString(), 0));
    }

    public void stopWork(){
        stopWork = true;
    }
    public void sendMessage(String message){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                gui.printError(message);
            }
        });
    }
}
