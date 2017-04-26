/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaproject;

import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javaproject.SocketClient;
import javaproject.SocketClient.chatClient;

/**
 *
 * @author Thomas
 */
public class ChatWindow extends javax.swing.JFrame {
    //Decleration of chat client and array
    chatClient cc;
    String details[];
    String user;
    String friend;
    /**
     * Creates new form ChatWindow
     */
    
    public ChatWindow(String data) throws InterruptedException {
        initComponents();
        details = data.split("~");
        user = details[0];
        friend = details[1];
        nameLabel.setText(friend);
        connectToServer();
        this.setTitle("NTU Music Network - "+ user);
        Thread.sleep(1000);
        addToList();
        listen();
        
    }

    private ChatWindow() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        chat = new javax.swing.JPanel();
        sendMessageButton = new javax.swing.JButton();
        chatjLabel = new javax.swing.JLabel();
        nameLabel = new javax.swing.JLabel();
        jScrollPane8 = new javax.swing.JScrollPane();
        messagesTextArea = new javax.swing.JTextArea();
        messageTextField = new javax.swing.JTextField();
        messagejLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        sendMessageButton.setText("Send");
        sendMessageButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendMessageButtonActionPerformed(evt);
            }
        });

        chatjLabel.setText("Chatting to: ");

        nameLabel.setText("Name");

        messagesTextArea.setEditable(false);
        messagesTextArea.setColumns(20);
        messagesTextArea.setRows(5);
        jScrollPane8.setViewportView(messagesTextArea);

        messageTextField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                messageTextFieldActionPerformed(evt);
            }
        });

        messagejLabel.setText("Message:");

        javax.swing.GroupLayout chatLayout = new javax.swing.GroupLayout(chat);
        chat.setLayout(chatLayout);
        chatLayout.setHorizontalGroup(
            chatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(chatLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(chatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(chatLayout.createSequentialGroup()
                        .addComponent(chatjLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(nameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(561, 561, 561))
                    .addGroup(chatLayout.createSequentialGroup()
                        .addGroup(chatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 453, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, chatLayout.createSequentialGroup()
                                .addComponent(messagejLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(messageTextField)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(sendMessageButton)))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
        chatLayout.setVerticalGroup(
            chatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(chatLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(chatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chatjLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(nameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(chatLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sendMessageButton)
                    .addComponent(messageTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(messagejLabel))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(chat, javax.swing.GroupLayout.PREFERRED_SIZE, 476, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 30, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(chat, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void sendMessageButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendMessageButtonActionPerformed
            //Get the name of sender and reciever and send a message to server
            String receiver = nameLabel.getText();
            String sendingMessage = messageTextField.getText();
            messagesTextArea.append(details[0]+": "+sendingMessage);
            messagesTextArea.append("\n");
            messageTextField.setText("");
            cc.sendMessage(user+"~"+friend+"~"+sendingMessage);
    }//GEN-LAST:event_sendMessageButtonActionPerformed

    private void messageTextFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_messageTextFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_messageTextFieldActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ChatWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ChatWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ChatWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ChatWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ChatWindow().setVisible(true);
            }
        });
    }
    
    public void connectToServer(){
        //Create new chat client by reqeusting
        SocketClient socketClient = new SocketClient();
        socketClient.request("createchat", " ");
        cc = socketClient.returnClient();
    }
    
    public void addToList(){
        //add current user to server list of conneected users
        cc.sendMessage("setup"+"~"+user);
    }
    
    public void listen(){
        //Listen for a connection every 2 seconds
        java.util.Timer t = new java.util.Timer();
        t.schedule(new TimerTask()
        {
        @Override
             public void run() 
             {
                //Get the replies from the client listener
                String reply = cc.listener.message;
                if (!reply.equals("")){
                    //if the reply is connected (appending user to array list)
                    if (cc.listener.message.equals("connected"))
                    {
                        messagesTextArea.append("You are now connected to the chat server");
                        messagesTextArea.append("\n");
                    }
                    else
                    {
                    //split inbound message
                    String[] messageDetails = reply.split("~");
                        if (messageDetails[0].equals(friend))
                            {
                            //if message is from right friend append it to text
                            messagesTextArea.append(friend+": "+messageDetails[1]);
                            messagesTextArea.append("\n");
                            }
                    }
                    
                    cc.listener.message = "";
                }
             }
          },0, 2000);
       }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel chat;
    private javax.swing.JLabel chatjLabel;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTextField messageTextField;
    private javax.swing.JLabel messagejLabel;
    private javax.swing.JTextArea messagesTextArea;
    private javax.swing.JLabel nameLabel;
    private javax.swing.JButton sendMessageButton;
    // End of variables declaration//GEN-END:variables
}
