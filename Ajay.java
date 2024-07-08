import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class Ajay extends Frame implements Runnable, ActionListener {
    TextField textField;
    TextArea textArea;
    Button send;

    ServerSocket serverSocket;
    Socket socket;

    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;

    Thread chat;

    Ajay() {
        textField = new TextField();
        textArea = new TextArea();
        send = new Button("Send");

        send.addActionListener(this);

        try {
            serverSocket = new ServerSocket(12000);
            socket = serverSocket.accept();
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
        } catch (Exception e) {
        }

        add(textField);
        add(textArea);
        add(send);

        setSize(500, 500);
        setTitle("Ajay");
        setLayout(new FlowLayout());
        setVisible(true);

        chat = new Thread(this);
        chat.setDaemon(true);
        chat.start();
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        String msg = textField.getText();
        textArea.append("Ajay: " + msg + "\n");
        textField.setText("");
        try {
            dataOutputStream.writeUTF(msg);
            dataOutputStream.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Ajay();
    }
    @Override
    public void run() {
        while (true) {
            try {
                String msg = dataInputStream.readUTF();
                textArea.append("Avi: " + msg + "\n");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
