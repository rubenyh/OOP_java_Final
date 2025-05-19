import javax.swing.ImageIcon;
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
import java.awt.Image;
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

//botones con acciones 
List<JButton> botonesOpciones = new ArrayList<>();//lista que guarda las opciones de los botones
String[] opciones = {"PIEDRA", "PAPEL", "TIJERAS", "LAGARTO", "SPOCK"};
String[] rutasimg = {"img/rock.png","img/paper.png","img/scissors.png","img/lizzard.png","img/spock.png"};

for (int i = 0; i < opciones.length; i++){
String opcion = opciones[i];
ImageIcon iconoOriginal = new ImageIcon(rutasimg[i]);
Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(121, 190, Image.SCALE_SMOOTH);
Image iconoMostrar = iconoOriginal.getImage().getScaledInstance(230, 400, Image.SCALE_SMOOTH);
ImageIcon iconoEscalado = new ImageIcon(imagenEscalada);
ImageIcon iconoxMostrar = new ImageIcon(iconoMostrar);

JButton boton = new JButton(opcion,iconoEscalado);
 boton.setPreferredSize(new Dimension(121,193));
 boton.addActionListener(new ActionListener() {
public void actionPerformed(ActionEvent e) {
 movimiento.setIcon(iconoxMostrar);
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
for (JButton boton : botonesOpciones)//desactiva las botones
{
 boton.setEnabled(false);
 }
 } else {
 confirmar.setBackground(Color.LIGHT_GRAY);
 confirmar.setText("Confirmar");
 confirmado[0] = false;
for (JButton boton : botonesOpciones)//vuelve a activar lo botones 
{
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