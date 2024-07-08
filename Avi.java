import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import package1.package2.Message; // Ensure this package exists and Message is imported correctly

public class Avi extends Frame implements Runnable, ActionListener {
    TextField textField;
    TextArea textArea;
    Button send;

    Socket socket;
    DataInputStream dataInputStream;
    DataOutputStream dataOutputStream;
    Thread chat;

    Avi() {
        textField = new TextField();
        textArea = new TextArea();
        send = new Button("Send");

        send.addActionListener(this);

        try {
            socket = new Socket("localhost", 12000);
            dataInputStream = new DataInputStream(socket.getInputStream());
            dataOutputStream = new DataOutputStream(socket.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }

        add(textField);
        add(textArea);
        add(send);

        setSize(500, 500);
        setTitle("Avi");
        setLayout(new FlowLayout());
        setVisible(true);

        chat = new Thread(this);
        chat.setDaemon(true);
        chat.start();
    }

    public void actionPerformed(ActionEvent e) {
        String msg = textField.getText();
        textArea.append("Avi: " + msg + "\n");
        textField.setText("");
        try {
            dataOutputStream.writeUTF(msg);
            dataOutputStream.flush();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Avi();
    }

    public void run() {
        while (true) {
            try {
                String msg = dataInputStream.readUTF();
                textArea.append("Ajay: " + msg + "\n");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
