import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Player1 extends JFrame{
    
public static void main(String[] args) {
JFrame ventana = new JFrame(); // Crear ventana sin título
JPanel pantalla = new JPanel();// un panel arriba
//JPanel seccionBotones = new JPanel();//una seccion donde estan los botones para jugar
JPanel centro = new JPanel(new BorderLayout());
JPanel seccionBotones = new JPanel(new GridLayout(1, 5));
JLabel movimiento = new JLabel("",SwingConstants.CENTER);// donde aparece que movimiento hace
JButton confirmar = new JButton("Confirmar");
    confirmar.setBackground(Color.LIGHT_GRAY);
    confirmar.setOpaque(true);
    confirmar.setBorderPainted(false);
    confirmar.setFont(new Font("Arial", Font.BOLD, 14));
    confirmar.setBounds(290,600,200,25);

    ventana.setSize(800, 900);// Tamaño de la ventana
    ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Cerrar al salir
    ventana.setVisible(true); // Mostrar la ventana
    ventana.setLayout(new BorderLayout()); 
    ventana.add(pantalla, BorderLayout.CENTER);
seccionBotones.setLayout(new FlowLayout());


List<JButton> botonesOpciones = new ArrayList<>();
String[] opciones = {"PIEDRA", "PAPEL", "TIJERAS", "LAGARTO", "SPOCK"};
for (String opcion : opciones) {
JButton boton = new JButton(opcion);
 boton.setPreferredSize(new Dimension(140,200));
 boton.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent e) {
 movimiento.setText("Seleccionaste: " + opcion);
 }
 });
seccionBotones.add(boton);
botonesOpciones.add(boton);
}   
final boolean[] confirmado = {false};

confirmar.addActionListener(x -> {
    if (!confirmado[0]) {
confirmar.setBackground(Color.GREEN);
confirmar.setText("¡Confirmado!");
confirmado[0] = true;
for (JButton boton : botonesOpciones) {
 boton.setEnabled(false);
 }
 } else {
 confirmar.setBackground(Color.LIGHT_GRAY);
 confirmar.setText("Confirmar");
 confirmado[0] = false;
for (JButton boton : botonesOpciones) {
 boton.setEnabled(true);
 }
    }
});

    centro.add(confirmar);
    centro.add(movimiento, BorderLayout.CENTER);
    ventana.add(centro, BorderLayout.CENTER);
    ventana.add(seccionBotones, BorderLayout.SOUTH);
    ventana.setVisible(true);
}
}