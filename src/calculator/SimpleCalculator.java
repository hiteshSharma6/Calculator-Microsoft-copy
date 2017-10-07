/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package calculator;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;

public class SimpleCalculator extends javax.swing.JFrame implements ActionListener {
    
    double number;
    double total;
    boolean again;
    boolean change;
    boolean entered;
    String[] operation;
    
    public SimpleCalculator() {
        this.again = false;
        this.entered = false;
        this.operation = new String[]{" ", " "};
        this.change = false;
        this.number = 0;
        this.total = 0;
        initComponents();
        setAction();
    }
    
    private void setAction() {
        jButton1.addActionListener(this);
        jButton2.addActionListener(this);
        jButton3.addActionListener(this);
        jButton4.addActionListener(this);
        jButton5.addActionListener(this);
        jButton6.addActionListener(this);
        jButton7.addActionListener(this);
        jButton8.addActionListener(this);
        jButton9.addActionListener(this);
        jButton10.addActionListener(this);
        jButton11.addActionListener(this);
        jButton12.addActionListener(this);
        jButton13.addActionListener(this);
        jButton14.addActionListener(this);
        jButton15.addActionListener(this);
        jButton16.addActionListener(this);
        jButton17.addActionListener(this);
        jButton18.addActionListener(this);
        jButton19.addActionListener(this);
        jButton20.addActionListener(this);
        jButton21.addActionListener(this);
        jButton22.addActionListener(this);
        jButton23.addActionListener(this);
        jButton24.addActionListener(this);
    }
    
    @Override
    public void actionPerformed(ActionEvent evt) {
        String cmd = (String) evt.getActionCommand();
        if(!again) {
            number = Double.parseDouble(jTextField1.getText());
        }
        switch (cmd) {
            case "C":
                clearAll();
                break;
            case "CE":
                clearEntry();
                break;
            case "del":
                try {
                    delete();
                } catch (BadLocationException ex) {
                    Logger.getLogger(SimpleCalculator.class.getName()).log(Level.SEVERE, null, ex);
                }
                break;
            case "M":
                memory();
                break;
            case "+/-":
                posNeg();
                break;
            case "sqrt":
                sqrt();
                break;
            case "log":
                log();
                break;
            case "1/x":
                reciprocal();
                break;
            case "/":
                operation[1] = "/";
                break;
            case "*":
                operation[1] = "*";
                break;
            case "-":
                operation[1] = "-";
                break;
            case "+":
                operation[1] = "+";
                break;
            case "=":
                operate(operation[0]);
                result();
                break;
            default:
                if(number == 0) {
                    jTextField1.setText(cmd);
                } else if(change) {
                    if(again) {
                        clearAll();
                    }
                    checkPrevious(")");
                    jTextField1.setText(cmd);
                    change = false;
                } else {
                    jTextField1.setText(jTextField1.getText() + cmd);
                }
                break;
        }
        
        if(!" ".equals(operation[1])) {
            if(again) {
                again = false;
            } else if(!" ".equals(operation[0])) {
                checkPrevious(operation[0]);
                operate(operation[0]);
            } else {
                total = number;
            }
            afterNumber(operation[1]);
            operation[0] = operation[1];
            operation[1] = " ";
        }
        
    }

    private void clearAll() {
        jTextField1.setText("0");
        jTextField2.setText("");
        operation[0] = " ";
        operation[1] = " ";
        again = false;
        change = false;
        entered = false;
        total = 0;
    }

    private void clearEntry() {
        jTextField1.setText("0");
        if(again) {
            total = 0;
        }
        checkPrevious(")");
    }

    private void delete() throws BadLocationException {
        String text = jTextField1.getText();
        if(!change) {
            jTextField1.setText(jTextField1.getText(0, text.length()-1));
            if(jTextField1.getText().equals("")) {
                jTextField1.setText("0");
            }
        }
    }

    private void memory() {
        
    }

    private void posNeg() {
        number *= -1;
        checkNumber(number);
    }

    private void sqrt() {
        try {
            number = Math.sqrt(number);
            beforeNumber("sqrt");
        } catch(Exception e) {
            jTextField1.setText("Invalid Input");
        }
    }

    private void log() {
        try {
            number = Math.log(number);
            beforeNumber("log");
        } catch(Exception e) {
            jTextField1.setText("Invalid Input");
        }
    }

    private void reciprocal() {
        try {
            number = 1/number;
            beforeNumber("reciproc");
        } catch(Exception e) {
            jTextField1.setText("Cannot divide by zero");
        }
    }
    
    private void operate(String operand) {
        switch(operand) {
            case "/":
                try {
                    total /= number;
                } catch (Exception e) {
                    jTextField1.setText("Cannot divide by zero");
                }
                break;
            case "*":
                total *= number;
                break;
            case "-":
                total -= number;
                break;
            case "+":
                total += number;
                break;
            default:
                break;
           }
        System.out.println(total);
    }
    
    private void result() {
        checkNumber(total);
        if(total ==0 && number!=0) {
            checkNumber(number);
        }
        again = true;
        change = true;
        entered = false;
        jTextField2.setText("");
    }

    private void afterNumber(String s) {
        if(entered) {
            jTextField2.setText(jTextField2.getText()+ " "+ s);
             entered = false;
       } else {
            jTextField2.setText(jTextField2.getText()+ " "+ jTextField1.getText()+ " "+ s);
        }
        checkNumber(total);
        change = true;
    }

    private void beforeNumber(String s) {
        String text = jTextField2.getText();
        if(text.endsWith(")")) {
            String inside = text.substring(text.lastIndexOf(" ")+1);
            checkPrevious(")");
            jTextField2.setText(jTextField2.getText()+ " "+ s+ "("+ inside+ ")");
        } else {
            jTextField2.setText(jTextField2.getText()+ " "+ s+ "("+ jTextField1.getText()+ ")");
        }
        checkNumber(number);
        change = true;
        entered = true;
    }

    private void checkPrevious(String last) {
        if(change) {
            String text = jTextField2.getText();
            if(text.endsWith(last)) {
                jTextField2.setText(text.substring(0, text.lastIndexOf(" ")));
            entered = false;
            if(!last.equals(")")) {
                operation[0] = " ";
                entered = true;
            }}
        }
    }

    private void checkNumber(double toConvert) {
        int converted = (int) toConvert;
        if(converted == toConvert) {
            jTextField1.setText(Integer.toString(converted));
        } else {
            jTextField1.setText(Double.toString(toConvert));
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jButton12 = new javax.swing.JButton();
        jButton13 = new javax.swing.JButton();
        jButton14 = new javax.swing.JButton();
        jButton15 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        jButton18 = new javax.swing.JButton();
        jButton19 = new javax.swing.JButton();
        jButton20 = new javax.swing.JButton();
        jButton21 = new javax.swing.JButton();
        jButton22 = new javax.swing.JButton();
        jButton23 = new javax.swing.JButton();
        jButton24 = new javax.swing.JButton();
        jTextField2 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setAlwaysOnTop(true);
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setResizable(false);

        jTextField1.setEditable(false);
        jTextField1.setFont(new java.awt.Font("Consolas", 2, 24)); // NOI18N
        jTextField1.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField1.setText("0");
        jTextField1.setToolTipText("");
        jTextField1.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jTextField1.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jTextField1.setEnabled(false);

        jButton1.setText("7");

        jButton2.setText("8");

        jButton3.setText("9");

        jButton4.setText("4");

        jButton5.setText("5");

        jButton6.setText("6");

        jButton7.setText("1");

        jButton8.setText("2");

        jButton9.setText("3");

        jButton10.setText("M");

        jButton11.setText("+/-");

        jButton12.setText("sqrt");

        jButton13.setText("log");

        jButton14.setText("*");

        jButton15.setText("-");

        jButton16.setText("+");

        jButton17.setText("1/x");

        jButton18.setText("CE");

        jButton19.setText("0");

        jButton20.setText("=");

        jButton21.setText(".");

        jButton22.setText("C");

        jButton23.setText("del");

        jButton24.setText("/");

        jTextField2.setEditable(false);
        jTextField2.setFont(new java.awt.Font("Courier New", 2, 14)); // NOI18N
        jTextField2.setHorizontalAlignment(javax.swing.JTextField.RIGHT);
        jTextField2.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.LOWERED));
        jTextField2.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jTextField2.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(50, 50, 50)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 267, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jButton17, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jButton13, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton12)
                                    .addComponent(jButton11, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jButton10, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(18, 18, 18)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jButton7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jButton18, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGap(2, 2, 2)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, 47, Short.MAX_VALUE)
                                        .addComponent(jButton22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jButton5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(18, 18, 18)
                                    .addComponent(jButton19, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGap(2, 2, 2)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jButton24, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton14, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton15, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton16, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton20, javax.swing.GroupLayout.PREFERRED_SIZE, 47, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(50, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 15, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton10)
                        .addComponent(jButton18))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton23)
                        .addComponent(jButton22)
                        .addComponent(jButton24)))
                .addGap(2, 2, 2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton11)
                    .addComponent(jButton2)
                    .addComponent(jButton1)
                    .addComponent(jButton3)
                    .addComponent(jButton14))
                .addGap(2, 2, 2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton12)
                    .addComponent(jButton6)
                    .addComponent(jButton4)
                    .addComponent(jButton5)
                    .addComponent(jButton15))
                .addGap(2, 2, 2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton13)
                    .addComponent(jButton7)
                    .addComponent(jButton8)
                    .addComponent(jButton9)
                    .addComponent(jButton16))
                .addGap(2, 2, 2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton17)
                    .addComponent(jButton19)
                    .addComponent(jButton21)
                    .addComponent(jButton20))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
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
            java.util.logging.Logger.getLogger(SimpleCalculator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(SimpleCalculator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(SimpleCalculator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(SimpleCalculator.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new SimpleCalculator().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton22;
    private javax.swing.JButton jButton23;
    private javax.swing.JButton jButton24;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    // End of variables declaration//GEN-END:variables

}