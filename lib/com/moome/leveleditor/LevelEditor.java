package com.moome.leveleditor;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JMenuBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.UIManager;
import javax.swing.KeyStroke;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.awt.event.WindowEvent;
import java.awt.event.ActionEvent;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import javax.swing.BorderFactory;
import javax.swing.border.EmptyBorder;
import javax.swing.border.EtchedBorder;
import com.moome.Tileset;
import com.moome.TileSettings;
import com.moome.Util;
import java.io.File;
import javax.swing.text.AbstractDocument;
import javax.swing.text.DocumentFilter;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.ArrayList;
import java.util.List;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

public class LevelEditor extends JPanel implements MouseMotionListener, MouseListener {
    public static int w = 0;
    public static int h = 0;
    public BufferedImage image = new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB);
    public int mouseX = -1;
    public int mouseY = -1;
    Tileset tileset;
    public int i = 0;
    char tile = ' ';
    public int dx = 0;
    public SMap map;
    public int tiles;
    JFileChooser fc = new JFileChooser();
    final JFrame dframe = new JFrame("New Map");
    final JFrame frame;
    public List<String[]> history = new ArrayList<>();
    JScrollPane scrollPane;
    public Selector selector;
    public static final boolean repeat = false;
    
    public LevelEditor() {
        try {
            /*
            for(javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                System.out.println(info.getName());
                if("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }*/
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
        } catch(Exception e) {
            try {
                UIManager.setLookAndFeel("com.sun.java.swing.plaf.motif.MotifLookAndFeel");
            } catch(Exception e1) {}
        }
        init_dframe();
        fc.setCurrentDirectory(new File("../data/maps/"));
        tileset = new Tileset("../data/images/tilesetLE.txt");
        tiles = 0;
        for(int i = 0; i < tileset.ids.size(); i++) {
            for(; TileSettings.isGenerated(tileset.ids.get(i)); i++) {}
            tiles++;
        }
        selector = new Selector(this);
        this.addMouseMotionListener(this);
        this.addMouseListener(this);
        this.setBackground(new Color(0, 205, 240));
        frame = new JFrame("Moome - Level Editor");
        frame.setExtendedState(frame.getExtendedState()|JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(new BorderLayout());
        frame.setLocationRelativeTo(null);
        final JFrame j = frame;
        frame.addWindowListener(new WindowListener() {
            public void windowClosing(WindowEvent e) {
                j.dispose();
            }
            
            public void windowDeactivated(WindowEvent e) {}
            public void windowActivated(WindowEvent e) {}
            public void windowDeiconified(WindowEvent e) {}
            public void windowOpened(WindowEvent e) {}
            public void windowClosed(WindowEvent e) {}
            public void windowIconified(WindowEvent e) {}
        });
        
        /* Setup MenuBar */
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        menuBar.add(file);
        JMenu edit = new JMenu("Edit");
        menuBar.add(edit);
        
        
        /* MenuBar > Edit */
        JMenuItem undo = new JMenuItem("Undo");
        undo.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {
            replaceHistory();
        }});
        
        JMenuItem test = new JMenuItem("test");
        test.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {
            for(String[] s : history) {
                System.out.println("> START");
                for(String ss : s) {
                    System.out.println("    " + ss);
                }
            }
            /*
            boolean c = false;
            String[] l = new String[2];
            for(String[] s : history) {
                if(c) {
                    c = false;
                    String first = "";
                    for(String ss : s) {
                        first += ss;
                    }
                    String second = "";
                    for(String ss : l) {
                        second += ss;
                    }
                    
                    System.out.println("Test val=" + (first == second));
                } else {
                    c = true;
                    l = s;
                }
            }*/
        }});
        edit.add(test);
        
        JMenuItem redo = new JMenuItem("Redo");
        redo.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {}});
        
        edit.add(undo);
        
        /* MenuBar > File */
        JMenuItem new_level = new JMenuItem("New");
        new_level.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {
            LevelEditor.this.saveRequest();
            dframe.setVisible(true);
        }});
        JMenuItem open = new JMenuItem("Import...");
        open.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {
            open();
        }});
        JMenuItem save = new JMenuItem("Export...");
        save.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {
            save();
        }});
        JMenuItem upload = new JMenuItem("Upload Map");
        upload.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {
            upload();
        }});
        JMenuItem print = new JMenuItem("Print");
        print.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {
            new Thread(new PrintActionListener(image)).start();
        }});
        JMenuItem quit = new JMenuItem("Exit");
        quit.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {
            LevelEditor.this.saveRequest();
            System.exit(0);
        }});
        
        new_level.setAccelerator(KeyStroke.getKeyStroke('N', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        open.setAccelerator(KeyStroke.getKeyStroke('O', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        save.setAccelerator(KeyStroke.getKeyStroke('S', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        print.setAccelerator(KeyStroke.getKeyStroke('P', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        quit.setAccelerator(KeyStroke.getKeyStroke('Q', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        undo.setAccelerator(KeyStroke.getKeyStroke('Z', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
        
        file.add(new_level);
        file.addSeparator();
        file.add(open);
        file.add(save);
        file.addSeparator();
        file.add(upload);
        file.addSeparator();
        file.add(print);
        file.addSeparator();
        file.add(quit);
        
        scrollPane = new JScrollPane(this);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        
        frame.getContentPane().add("North", menuBar);
        frame.getContentPane().add("Center", scrollPane);
        frame.getContentPane().add("South", new JScrollPane(selector));
        
        frame.pack();
        frame.setVisible(true);
        Thread t = new Thread(new Runnable() {
            public void run() {
                for(;;) {
                    LevelEditor.this.i++;
                    repaint();
                    selector.repaint();
                    try {
                        Thread.sleep(50);
                    } catch(Exception e) {}
                }
            }
        });
        t.start();
        dframe.setVisible(true);
    }
    
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(image, 0, 0, w * 40, h * 40, null);
        int a = 128 + (int)Math.abs(Math.round(Math.sin(Math.toRadians(i * 5)) * 40));
        if(tile == ' ') {
            g.setColor(Color.BLACK);
            g.drawRect(mouseX * 40, mouseY * 40, 40, 40);
            g.setColor(new Color(a, a, a, 128));
            g.fillRect(mouseX * 40, mouseY * 40, 40, 40);
        } else {
            g.drawImage(tileset.image(tile), mouseX * 40, mouseY * 40, 40, 40, null);
        }
        g.setColor(Color.red);
        g.drawRect(0, 0, w * 40 - 1, h * 40 - 1);
    }
    
    public void mousePressed(MouseEvent e) {
        addHistory();
        if(e.getX() < w * 40 && e.getX() > 0 && e.getY() < h * 40 && e.getY() > 0) {
            Graphics2D g = (Graphics2D)image.createGraphics();
            g.setBackground(new Color(0, 0, 0, 0));
            g.clearRect(mouseX * 20, mouseY * 20, 20, 20);
            g.drawImage(tileset.image(tile), mouseX * 20, mouseY * 20, null);
            map.setBlock(mouseX, mouseY, tile);
            g.dispose();
        }
    }
    
    public void mouseMoved(MouseEvent e) {
        mouseX = Math.round(e.getX() / 40);
        mouseY = Math.round(e.getY() / 40);
    }
    
    public void mouseDragged(MouseEvent e) {
        mouseX = Math.round(e.getX() / 40);
        mouseY = Math.round(e.getY() / 40);
        if(e.getX() < w * 40 && e.getX() > 0 && e.getY() < h * 40 && e.getY() > 0) {
            Graphics2D g = (Graphics2D)image.createGraphics();
            g.setBackground(new Color(0, 0, 0, 0));
            g.clearRect(mouseX * 20, mouseY * 20, 20, 20);
            g.drawImage(tileset.image(tile), mouseX * 20, mouseY * 20, null);
            g.dispose();
            map.setBlock(mouseX, mouseY, tile);
        }
    }
    
    public void mouseClicked(MouseEvent e) {}
    public void mouseReleased(MouseEvent e) {}
    public void mouseEntered(MouseEvent e) {}
    public void mouseExited(MouseEvent e) {}
    
    public void init_dframe() {
        dframe.setLocationRelativeTo(null);
        dframe.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        dframe.getContentPane().setLayout(new GridLayout(1, 0));
        JPanel panel = new JPanel(new BorderLayout());
        JPanel panel1 = new JPanel(new GridLayout(0, 2));
        JPanel panel2 = new JPanel(new GridLayout(1, 0));
        JPanel panel3 = new JPanel(new BorderLayout());
        panel.add(panel1);
        
        panel1.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(EtchedBorder.LOWERED), "New Map"));
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));
        
        panel1.add(new JLabel("Width:"));
        
        final JTextField xField = new JTextField(10);
        setNumbersOnly(xField, 100);
        panel1.add(xField);
        
        panel1.add(new JLabel("Height:"));
        
        final JTextField yField = new JTextField(10);
        setNumbersOnly(yField, 100);
        panel1.add(yField);
        
        JButton doneButton = new JButton("Done");
        doneButton.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {
            try {
                w = Integer.parseInt(xField.getText());
                h = Integer.parseInt(yField.getText());
                map = new SMap(w, h);
                rerender();
                resize();
            } catch(Exception e1) {}
            dframe.setVisible(false);
        }});
        
        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(new ActionListener() {public void actionPerformed(ActionEvent e) {
            dframe.setVisible(false);
        }});
        
        panel2.add(cancelButton);
        panel2.add(doneButton);
        panel3.add("East", panel2);
        panel.add("South", panel3);
        
        dframe.getContentPane().add(panel);
        dframe.pack();
        dframe.setLocationRelativeTo(null);
        dframe.setResizable(false);
    }
    
    public void save() {
        if(fc.showSaveDialog(this) == JFileChooser.APPROVE_OPTION) {
            map.writeFile(fc.getSelectedFile());
        }
    }
    
    public void saveRequest() {
        int dialogResult = JOptionPane.showConfirmDialog(null, "Save map?", "Are you sure..?", JOptionPane.YES_NO_OPTION);
        if(dialogResult == JOptionPane.YES_OPTION){
            save();
        }
    }
    
    public void open() {
        if(fc.showOpenDialog(this) == JFileChooser.APPROVE_OPTION) {
            map = new SMap(fc.getSelectedFile());
            w = map.width;
            h = map.height;
            rerender();
            resize();
            frame.pack();
        }
    }
    
    public void setNumbersOnly(final JTextField textField, final int max) {
        ((AbstractDocument)textField.getDocument()).setDocumentFilter(new DocumentFilter() {
            boolean a = true;
            public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
                if(a) {
                    Matcher matcher = Pattern.compile("\\d+").matcher(text);
                    if (!matcher.matches()) {
                        return;
                    }
                    if(textField.getText().length() > Integer.toString(max).length() || Integer.parseInt(textField.getText() + text) > max) {
                        a = false;
                        textField.setText(Integer.toString(max));
                        a = true;
                        return;
                    }
                }
                super.replace(fb, offset, length, text, attrs);
            }
        });
    }
    
    public void addHistory() {
        String[] map1 = new String[map.map.length];
        System.arraycopy(map.map, 0, map1, 0, map.map.length);
        history.add(map1);
    }
    
    public void replaceHistory() {
        String[] map1 = new String[map.map.length];
        System.arraycopy(map.map, 0, map1, 0, map.map.length);
        map.map = history.get(i);
        history.remove(history.size() - 1);
        rerender();
    }
    
    public void rerender() {
        image = new BufferedImage(w * 20, h * 20, BufferedImage.TYPE_INT_ARGB);
        Graphics g = image.createGraphics();
        for(int x = 0; x < w; x++) {
            for(int y = 0; y < h; y++) {
                g.drawImage(tileset.image(map.getTile(x, y)), x * 20, y * 20, null);
            }
        }
        g.dispose();
        repaint();
    }
    
    public void resize() {
        this.setPreferredSize(new Dimension(w * 40, h * 40));
    }
    
    public void upload() {
        if(map != null) {
            new SendMapGUI(map.toString());
        }
    }
}

// PRINTING CLASSES: STILL BETA

class PrintActionListener implements Runnable {
    private BufferedImage image;
    
    public PrintActionListener(BufferedImage image) {
        this.image = image;
    }
    
    public void run() {
        PrinterJob printJob = PrinterJob.getPrinterJob();
        printJob.setPrintable(new ImagePrintable(printJob, image));
        
        if (printJob.printDialog()) {
            try {
                printJob.print();
            } catch (PrinterException prt) {
                prt.printStackTrace();
            }
        }
    }
}

class ImagePrintable implements Printable {
    private double x, y, width;
    private int orientation;
    private BufferedImage image;
    
    public ImagePrintable(PrinterJob printJob, BufferedImage image) {
        PageFormat pageFormat = printJob.defaultPage();
        this.x = pageFormat.getImageableX();
        this.y = pageFormat.getImageableY();
        this.width = pageFormat.getImageableWidth();
        this.orientation = pageFormat.getOrientation();
        this.image = image;
    }
    
    public int print(Graphics g, PageFormat pageFormat, int pageIndex) throws PrinterException {
        if (pageIndex == 0) {
            int pWidth = 0;
            int pHeight = 0;
            if (orientation == PageFormat.PORTRAIT) {
                pWidth = (int) Math.min(width, (double) image.getWidth());
                pHeight = pWidth * image.getHeight() / image.getWidth();
            } else {
                pHeight = (int) Math.min(width, (double) image.getHeight());
                pWidth = pHeight * image.getWidth() / image.getHeight();
            }
            g.drawImage(image, (int) x, (int) y, pWidth, pHeight, null);
            return PAGE_EXISTS;
        } else {
            return NO_SUCH_PAGE;
        }
    }
}
