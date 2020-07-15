package com.moome.leveleditor;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle;
import javax.swing.WindowConstants;

public class SendMapGUI extends JFrame {
    private JTextArea description;
    private JButton jButton1;
    private JLabel jLabel1;
    private JLabel jLabel2;
    private JLabel jLabel3;
    private JLabel jLabel4;
    private JLabel jLabel5;
    private JLabel jLabel6;
    private JLabel jLabel7;
    private JLabel jLabel8;
    private JLabel jLabel9;
    private JScrollPane jScrollPane1;
    private JScrollPane jScrollPane2;
    private JSlider jSlider1;
    private JTextField name;
    private JTextArea objective;
    private JComboBox original;
    private JTextField time;
    private JTextField title;  
    
    public String map = "err";
    
    public SendMapGUI(String map) {
        this.map = map;
        initComponents();
        name.setText(System.getProperty("user.name"));
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.title.setFocusable(true);
        this.title.requestFocus();
    }
    
    @SuppressWarnings("unchecked")
    private void initComponents() {
        jLabel1 = new JLabel();
        original = new JComboBox();
        jLabel2 = new JLabel();
        name = new JTextField();
        jLabel3 = new JLabel();
        title = new JTextField();
        jLabel4 = new JLabel();
        jScrollPane1 = new JScrollPane();
        objective = new JTextArea();
        jLabel5 = new JLabel();
        jScrollPane2 = new JScrollPane();
        description = new JTextArea();
        jLabel6 = new JLabel();
        time = new JTextField();
        jLabel7 = new JLabel();
        jLabel8 = new JLabel();
        jSlider1 = new JSlider();
        jLabel9 = new JLabel();
        jButton1 = new JButton();
        
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Moome - LevelEditor");
        
        jLabel1.setText("Did you create this map?");
        
        original.setModel(new DefaultComboBoxModel(new String[] { "Yes", "No" }));
        
        jLabel2.setText("What is your name / username?");
        
        jLabel3.setText("Title of map?");
        
        jLabel4.setText("Description of map?");
        
        objective.setColumns(20);
        objective.setLineWrap(true);
        objective.setRows(5);
        objective.setText("Collect as much ruby as you can, and as many coins as you can. Get to the portal!");
        objective.setWrapStyleWord(true);
        jScrollPane1.setViewportView(objective);
        
        jLabel5.setText("Objective of map?");
        
        description.setColumns(20);
        description.setLineWrap(true);
        description.setRows(5);
        description.setText("Perfect map for intermediate players! Find the portal! Explore worlds! Have fun!");
        description.setWrapStyleWord(true);
        jScrollPane2.setViewportView(description);
        
        jLabel6.setText("Estimated time to complete map?");
        
        time.setText("5-20 minutes");
        
        jLabel7.setText("Difficulty of map?");
        
        jLabel8.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        jLabel8.setText("Very Easy");
        
        jSlider1.setMajorTickSpacing(1);
        jSlider1.setMaximum(10);
        jSlider1.setMinorTickSpacing(1);
        jSlider1.setPaintTicks(true);
        jSlider1.setSnapToTicks(true);
        jSlider1.setValue(3);
        
        jLabel9.setFont(new java.awt.Font("Lucida Grande", 0, 10)); // NOI18N
        jLabel9.setText("Very Hard");
        
        jButton1.setText("Send Map");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        
        GroupLayout layout = new GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
                    .addComponent(original, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(name)
                    .addComponent(jLabel3, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(title)
                    .addComponent(jLabel4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addComponent(jScrollPane2, GroupLayout.DEFAULT_SIZE, 483, Short.MAX_VALUE)
                    .addComponent(time)
                    .addComponent(jSlider1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel8)
                        .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel9))
                    .addComponent(jLabel7, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel5, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jButton1, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(original, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(name, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(title, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(time, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel7)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSlider1, GroupLayout.PREFERRED_SIZE, 16, GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9))
                .addPreferredGap(LayoutStyle.ComponentPlacement.RELATED, 48, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap())
        );
        
        pack();
    }                       

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {                                         
        MapUpload m = new MapUpload(title.getText());
        m.setDescription(description.getText());
        m.setDifficulty(jSlider1.getValue());
        m.setMap(map);
        m.setObjective(objective.getText());
        String s = (String)original.getSelectedItem();
        if(s.equalsIgnoreCase("Yes")) {
            m.setOriginal(true);
        } else {
            m.setOriginal(false);
        }
        m.setTime(time.getText());
        m.setUsername(name.getText());
        
        String compiled = m.compile(MapUpload.TO_URL);
        m.launch(compiled);
        this.dispose();
    }
}
