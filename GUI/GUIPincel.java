import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class GUIPincel extends JFrame {

    private MatrizColor matriz;
    private ColorRGB nuevoColor;

    private JButton[][] botonMatriz;
    private JButton boton;
    private JCheckBox consultar;

    private JPanel panelMenu;
    private JPanel panelMatriz;
    private JPanel panelColores;
    private JPanel panelContainerControlColor;
    private JPanel panelContainerBoton;
    private JPanel panelControlColor;
    
    private JLabel etiquetaRojo;
    private JLabel etiquetaAzul;
    private JLabel etiquetaVerde;

    private JTextField rojo;
    private JTextField azul;
    private JTextField verde;

    public GUIPincel(MatrizColor c) {
        super("Pincel de IPOO");

        matriz = c;
        nuevoColor = new ColorRGB(0, 0, 0);

        setSize(700, 600);
        setLayout(new BorderLayout());

        inicializarPanelMatriz();
        inicializarPanelMenu();

        /*add(panelMatriz, BorderLayout.CENTER);*/ //Consultar si se puede borrar, en la consigna esta North pero en el proyecto base no
        add(panelMatriz, BorderLayout.NORTH);
        add(panelMenu, BorderLayout.SOUTH);

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void inicializarPanelMatriz() {
        panelMatriz = new JPanel();

        panelMatriz.setLayout(new GridLayout(
            matriz.cantFilas(),
            matriz.cantColumnas()
        ));

        panelMatriz.setPreferredSize(new Dimension(700, 350));
        panelMatriz.setBorder(BorderFactory.createEmptyBorder(80, 15, 15, 15));

        botonMatriz = new JButton[
            matriz.cantFilas()
        ][
            matriz.cantColumnas()
        ];

        for (int f = 0; f < matriz.cantFilas(); f++) {
            for (int c = 0; c < matriz.cantColumnas(); c++) {
                JButton boton = new JButton();

                boton.setBackground(Color.WHITE);
                boton.setOpaque(true);
                boton.setBorderPainted(true);
                boton.addActionListener(new OyenteMatriz());

                botonMatriz[f][c] = boton;
                panelMatriz.add(boton);
            }
        }
    }

    private void inicializarPanelMenu() {
        //Panel Menu
        panelMenu = new JPanel(new BorderLayout());
        panelMenu.add(new JLabel("Panel de control"));
        
        etiquetaRojo=new JLabel("Rojo:");
        etiquetaVerde=new JLabel("Verde:");
        etiquetaAzul=new JLabel("Azul:");

        rojo = new JTextField("0", 3); 
        azul = new JTextField("0", 3);                                            
        verde = new JTextField("0", 3);

        panelColores = new JPanel(new GridLayout(3, 2, 5, 5));
        panelColores.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelColores.add(etiquetaRojo);
        panelColores.add(rojo);
        panelColores.add(etiquetaVerde);
        panelColores.add(verde);
        panelColores.add(etiquetaAzul);
        panelColores.add(azul);

        
        //Panel Control Color
        panelControlColor = new JPanel(new FlowLayout());
        panelControlColor.setPreferredSize(new Dimension(40,40));
        panelControlColor.setBackground(Color.BLACK);
        
        //Panel Container Control Color
        panelContainerControlColor = new JPanel(new FlowLayout());
        panelContainerControlColor.add(panelControlColor);

        //Panel Container Boton
        panelContainerBoton = new JPanel(new FlowLayout());
        boton = new JButton("Confirmar Color");
        consultar = new JCheckBox("Ver RGB");
        panelContainerBoton.add(boton);
        panelContainerBoton.add(consultar);
        consultar.addActionListener(new OyenteBotonCheckBox());


        //Posiciones en panelMenu BorderLayout
        panelMenu.add(panelContainerControlColor, BorderLayout.CENTER);
        panelMenu.add(panelContainerBoton, BorderLayout.SOUTH);   
        panelMenu.add(panelColores, BorderLayout.WEST);     
    }

    private class OyenteMatriz implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Por ahora no hace nada.
        }
    }

    private class OyenteBotonPincel implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            // Por ahora no hace nada.
            int r = Integer.parseInt(rojo.getText());
            int v = Integer.parseInt(verde.getText());
            int a = Integer.parseInt(azul.getText());
            
            if(r>=0 && r<=255 && v>=0 && v<=255 && a>=0 && a<=255) {
                panelControlColor.setBackground(new Color(r, v, a));
            } else {
                JOptionPane.showMessageDialog(null, "Los valores de RGB deben estar entre 0 y 255", "Error", JOptionPane.ERROR_MESSAGE);
            } 
        }
    }

    private class OyenteBotonCheckBox implements ActionListener{
        public void actionPerformed(ActionEvent e) {
            // Por ahora no hace nada.
            boolean marcado = consultar.isSelected();

            for(int i = 0; i < matriz.cantFilas(); i++) {
                for(int j = 0; j < matriz.cantColumnas(); j++) {
                    if(marcado) {
                        Color color = botonMatriz[i][j].getBackground();
                        int r = color.getRed();
                        int g = color.getGreen();
                        int b = color.getBlue();
                        botonMatriz[i][j].setText( "(" +r +", " + g + ", " + b +")" );
                    } else {
                        botonMatriz[i][j].setText("");
                    }
                }
            }
        }
    }
}