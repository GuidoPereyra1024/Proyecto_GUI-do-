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

        setSize(900, 600);
        setLayout(new BorderLayout());

        inicializarPanelMatriz();
        inicializarPanelMenu();

    
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
                matriz.cantColumnas()));
        
                
        OyenteMatriz oyenteM=new OyenteMatriz();        
        panelMatriz.setPreferredSize(new Dimension(900, 350));
        panelMatriz.setBorder(BorderFactory.createEmptyBorder(80, 15, 15, 15));

        botonMatriz = new JButton[matriz.cantFilas()][matriz.cantColumnas()];

        for (int f = 0; f < matriz.cantFilas(); f++) {
            for (int c = 0; c < matriz.cantColumnas(); c++) {
                botonMatriz[f][c] = new JButton();
                botonMatriz[f][c].setActionCommand(f+"-"+c);
                botonMatriz[f][c].setBackground(Color.WHITE);
                botonMatriz[f][c].setOpaque(true);
                botonMatriz[f][c].setBorderPainted(true);
                botonMatriz[f][c].addActionListener(oyenteM);
                botonMatriz[f][c].setFont(new Font("Arial" , Font.PLAIN, 9));

                panelMatriz.add(botonMatriz[f][c]);
            }
        }

    }

    private void inicializarPanelMenu() {
        // Panel Menu
        panelMenu = new JPanel(new BorderLayout());
        panelMenu.add(new JLabel("Panel de control"));

        etiquetaRojo = new JLabel("Rojo:");
        etiquetaVerde = new JLabel("Verde:");
        etiquetaAzul = new JLabel("Azul:");

        rojo = new JTextField("0", 10);
        azul = new JTextField("0", 10);
        verde = new JTextField("0", 10);

        panelColores = new JPanel(new GridLayout(3, 2, 5, 5));
        panelColores.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panelColores.add(etiquetaRojo);
        panelColores.add(rojo);
        panelColores.add(etiquetaVerde);
        panelColores.add(verde);
        panelColores.add(etiquetaAzul);
        panelColores.add(azul);

        // Panel Control Color
        panelControlColor = new JPanel(new FlowLayout());
        panelControlColor.setPreferredSize(new Dimension(40, 40));
        panelControlColor.setBackground(Color.BLACK);

        // Panel Container Control Color
        panelContainerControlColor = new JPanel(new FlowLayout());
        panelContainerControlColor.add(panelControlColor);

        // Panel Container Boton
        panelContainerBoton = new JPanel(new FlowLayout());
        boton = new JButton("Confirmar Color");
        consultar = new JCheckBox("Ver RGB");

        panelContainerBoton.add(boton);
        boton.addActionListener(new OyenteBotonPincel());

        panelContainerBoton.add(consultar);
        consultar.addActionListener(new OyenteBotonCheckBox());

        // Posiciones en panelMenu BorderLayout
        panelMenu.add(panelContainerControlColor, BorderLayout.CENTER);
        panelMenu.add(panelContainerBoton, BorderLayout.SOUTH);
        panelMenu.add(panelColores, BorderLayout.WEST);
    }

    private class OyenteMatriz implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Por ahora no hace nada.
            String s=e.getActionCommand();
            String[] filaCol=s.split("-");
            int fila=Integer.parseInt(filaCol[0]);
            int columna=Integer.parseInt(filaCol[1]);

            ColorRGB colorC=matriz.obtenerColor(fila, columna);
            colorC.establecerRojo(nuevoColor.obtenerRojo());
            colorC.establecerRojo(nuevoColor.obtenerVerde());
            colorC.establecerRojo(nuevoColor.obtenerAzul());

            Color colorAgregar=new Color(nuevoColor.obtenerRojo(), nuevoColor.obtenerVerde(), nuevoColor.obtenerAzul());
            botonMatriz[fila][columna].setBackground(colorAgregar);    
        }
    }

    private class OyenteBotonPincel implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Por ahora no hace nada.
            int rojoAnterior=nuevoColor.obtenerRojo();
            int verdeAnterior=nuevoColor.obtenerVerde();
            int azulAnterior=nuevoColor.obtenerAzul();

            int r = Integer.parseInt(rojo.getText());
            int v = Integer.parseInt(verde.getText());
            int a = Integer.parseInt(azul.getText());

            if (nuevoColor.establecerRojo(r)&& nuevoColor.establecerVerde(v)&& nuevoColor.establecerAzul(a)) {
                
                panelControlColor.setBackground(new Color(nuevoColor.obtenerRojo(), nuevoColor.obtenerVerde(), nuevoColor.obtenerAzul()));
            } else {
                JOptionPane.showMessageDialog(null, "Los valores de RGB deben estar entre 0 y 255", "Error",
                JOptionPane.ERROR_MESSAGE);
                nuevoColor.establecerRojo(rojoAnterior);
                nuevoColor.establecerVerde(verdeAnterior);
                nuevoColor.establecerAzul(azulAnterior);
            }
        }
    }

    private class OyenteBotonCheckBox implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            // Por ahora no hace nada.
            boolean marcado = consultar.isSelected();

            for (int i = 0; i < matriz.cantFilas(); i++) {
                for (int j = 0; j < matriz.cantColumnas(); j++) {
                    if (marcado) {
                        int r = matriz.obtenerColor(i,j).obtenerRojo();
                        int g = matriz.obtenerColor(i,j).obtenerVerde();
                        int b = matriz.obtenerColor(i,j).obtenerAzul();
                        botonMatriz[i][j].setText("(" + r + ", " + g + ", " + b + ")");
                        botonMatriz[i][j].setEnabled(false);
                    } else {
                        botonMatriz[i][j].setText("");
                         botonMatriz[i][j].setEnabled(true);
                    }
                }
            }
        }
    }
}