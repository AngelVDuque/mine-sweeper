/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package com.mycompany.mine.sweeper;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.Queue;
import javax.swing.*;
import java.util.Random;

/**
 *
 * @author Famas & Alimcho
 */
public class MineSweeper extends javax.swing.JFrame {
    @SuppressWarnings("FieldMayBeFinal")
    private JButton reset_button;
    
    private JButton change_mode;
    private int number_mode;
    private String[] modes = {"Facil", "Medio", "Dificil" };
    
    //Tomando por pares
    // (0, 1) (2, 3) (4, 5)
    private int[] map_size = { 520, 500, 620, 600, 720, 700}; 
    
    private JButton[][] button_matrix;
    private String[][] current_map;
    
    // Alcor task
    // Follow up: Me gustaria una funcion para crear botones 
    // Para no repetir el mismo codigo una y otra vez.
    
    // Alcor task
    // Follow up: 
    // Cambiar el arreglo de sizes de local a global para ahorrar memoria y tiempo
    
    private Color[] colors = { Color.black, 
        Color.blue, Color.green, Color.red, 
        new java.awt.Color(0,68,129), new java.awt.Color(139,0,0), new java.awt.Color(64,224,208),
        Color.black, Color.black};
    
    
    /**
     * Creates new form MineSweeperView
     */
    public MineSweeper() {
        number_mode = 0;
        initComponents();
        setSize(520, 500);
        
        change_mode = new JButton("Facil");
        change_mode.setBounds(75, 0, 75, 50);
        change_mode.setFocusPainted(false);
        change_mode.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                number_mode = (number_mode + 1) % 3;
                setSize(map_size[number_mode * 2], map_size[number_mode * 2 + 1]);
                change_mode.setText(modes[number_mode]);
                ResetButtonMatrix();
                EnableMap(number_mode);
            }
        });
        
        reset_button = new JButton("Reset");
        reset_button.setBounds(0, 0, 75, 50);
        reset_button.setFocusPainted(false);
        
        reset_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ResetButtonMatrix();
            }
        });
                
        getContentPane().add(reset_button);
        getContentPane().add(change_mode);
        
        button_matrix = new JButton[18][32];
        for(int i = 0; i < 14; ++i){
            for(int j = 0; j < 12; ++j){
                button_matrix[i][j] = new JButton("");
                button_matrix[i][j].setFont(new Font("Arial", Font.PLAIN, 20));
            }
        }
        
        current_map = new String[18][32]; 
        
        // Estas funciones deben ser modificadas 
        ResetMapMatrix();
        RandomizerTest();
        // Hasta aqui
        
        initMatrix();
        EnableMap(number_mode);
    }
    
    // Alichos task
    // Si el string tiene el formato <numero><-><numero>
    // Ejemplo: 18-34
    // Devuelve en la posicion 0 : 18
    // Devuelve en la posicion 1 : 34
    // Separa por coordenada (x, y)
    private int[] getCoord(String coord){
        coord += "-";
        
        int k = 0;
        int[] coords = new int[2];
        String current_number = "";
        for(int i = 0; i < coord.length(); ++i){
            if(coord.charAt(i) == '-'){
                coords[k++] = Integer.parseInt(current_number);
                current_number = "";
            } else {
                current_number += coord.charAt(i);
            }
        }
        return coords;
    }
    
    // Alichos task
    private void ResetButtonMatrix(){
        for(int i = 0; i < 14; ++i){
            for(int j = 0; j < 12; ++j){
                button_matrix[i][j].setText("");
                button_matrix[i][j].setBackground(new java.awt.Color(255,255,255));
                current_map[i][j] = "";
            }
        }
        RandomizerTest();
    }
    
    private void ResetMapMatrix(){  
        for(int i = 0; i < 14; ++i){
            for(int j = 0; j < 12; ++j){
                current_map[i][j] = "";
            }
        }
    }
    
    private void EnableMap(int mode){
        int [] sizes = { 10, 8, 12, 10, 14, 12};
        
        for(int i = 0; i < sizes[mode * 2]; ++i){
            for(int j = 0; j < sizes[mode * 2 + 1]; ++j){
                button_matrix[i][j].setVisible(true);
            }
        }
        
        for(int i = sizes[mode * 2]; i < 14; ++i){
            for(int j = 0; j < 12; ++j){
                button_matrix[i][j].setVisible(false);
            }
        }
        
        for(int i = 0; i < 14; ++i){
            for(int j = sizes[mode * 2 + 1]; j < 12; ++j){
                button_matrix[i][j].setVisible(false);
            }
        }
    }
    
    // Alcor task
    // Esta funcion deberia considerar el primer click del usuario
    // Tambien debe considerar la dificultad
    // Tanto para minas, como para tamano del mapa
    // Considerar cuando una mina de sobre lapa con otra
    private void RandomizerTest(){        
        // Completar esta funcion :) 
        current_map[0][0] = "M"; 
    }
    
    // Alichos Task
    private void FloodField(int[] coord){
        Queue<int[]> queue = new LinkedList<>();
        queue.add(coord);
        
        int[][] dir = { {0, 1}, {0, -1}, {1, 0}, {-1, 0} };
        
        while(!queue.isEmpty()){
            int x = queue.peek()[0];
            int y = queue.peek()[1];
            queue.remove();
            
            for(int k = 0; k < 4; ++k){
                int sx = x + dir[k][0];
                int sy = y + dir[k][1];
               
                if(sx < 0 || sy < 0 || sx == 14 || sy == 12 || current_map[sx][sy].equals("M")) continue;
                        
                if(current_map[sx][sy].equals("")){
                    // Empty position
                    int[] new_coord = {sx, sy};
                    if(button_matrix[sx][sy].getBackground() != Color.gray)
                        queue.add(new_coord);
                    
                    button_matrix[sx][sy].setBackground(Color.gray);
                } else {
                    // Proximity position
                    int[] num_coord = getCoord(button_matrix[sx][sy].getName());
                    
                    int num = 0;
                    if(!current_map[num_coord[0]][num_coord[1]].equals("M") && !current_map[num_coord[0]][num_coord[1]].equals("")){
                        num = Integer.parseInt(current_map[num_coord[0]][num_coord[1]]);
                    }
                    
                    button_matrix[sx][sy].setText(current_map[num_coord[0]][num_coord[1]]);
                    button_matrix[sx][sy].setForeground(colors[num]);
                    button_matrix[sx][sy].setBackground(Color.gray);
                }
            }
        }
    }
    
    // Alichos task
    private void initMatrix(){
        int x = 0;
        int y = 0;
        for(int i = 0; i < 14; ++i){
            y = 55;
            for(int j = 0; j < 12; ++j){
                button_matrix[i][j].setBounds(x, y, 50, 50);
                button_matrix[i][j].setFocusPainted(false);
                button_matrix[i][j].setName(i + "-" + j);
                button_matrix[i][j].setBackground(new java.awt.Color(255,255,255));
                
                    
                button_matrix[i][j].addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        JButton jb = (JButton)e.getSource();
                        int[] coord = getCoord(jb.getName());
                        
                        int num = 0;
                        if(!current_map[coord[0]][coord[1]].equals("M") && !current_map[coord[0]][coord[1]].equals("")){
                            num = Integer.parseInt(current_map[coord[0]][coord[1]]);
                        }

                        jb.setText(current_map[coord[0]][coord[1]]);
                        jb.setBackground(Color.gray);
                        jb.setForeground(colors[num]);
                        
                        if(jb.getText().equals("")){
                            FloodField(coord);
                        }
                    }
                });
                
                getContentPane().add(button_matrix[i][j]);
                
                y += 50;
            }
            x += 50;
        }
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setSize(new java.awt.Dimension(0, 0));
        getContentPane().setLayout(null);

        pack();
    }// </editor-fold>//GEN-END:initComponents

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
            java.util.logging.Logger.getLogger(MineSweeper.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MineSweeper.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MineSweeper.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MineSweeper.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MineSweeper().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
}
