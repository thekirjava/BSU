package com.company;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.util.HashMap;

public class Window extends JFrame {
    class Country{
        public Country(String name, ImageIcon flag) {
            this.name = name;
            this.flag = flag;
        }

        @Override
        public String toString() {
            return name;
        }

        public ImageIcon getFlag() {
            return flag;
        }

        public String name;
        public ImageIcon flag;
    }
    class ListCountryRenderer extends JLabel implements ListCellRenderer
    {
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            Country entry = (Country) value;
            setText(value.toString());
            setIcon(entry.getFlag());
            if (isSelected) {
                setBackground(list.getSelectionBackground());
                setForeground(list.getSelectionForeground());
            }
            else {
                setBackground(list.getBackground());
                setForeground(list.getForeground());
            }
            setEnabled(list.isEnabled());
            setFont(list.getFont());
            setOpaque(true);
            return this;
        }
    }
    Window() {
        this.setBounds(300, 100, 900, 600);
        this.setResizable(false);
        this.setTitle("Lab3");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JTabbedPane tabbedPane = new JTabbedPane();

        JPanel panel1= new JPanel();
        panel1.setLayout(new BorderLayout());
        DefaultListModel<Country> listModel = new DefaultListModel<>();
        listModel.add(0, new Country("Bhutan", new ImageIcon(Toolkit.getDefaultToolkit().getImage("flag_bhutan.png"))));
        listModel.add(1, new Country("Canada", new ImageIcon(Toolkit.getDefaultToolkit().getImage("flag_canada.png"))));
        listModel.add(2, new Country("Iceland", new ImageIcon(Toolkit.getDefaultToolkit().getImage("flag_iceland.png"))));
        listModel.add(3, new Country("Monaco", new ImageIcon(Toolkit.getDefaultToolkit().getImage("flag_monaco.png"))));
        listModel.add(4, new Country("Switzerland", new ImageIcon(Toolkit.getDefaultToolkit().getImage("flag_switzerland.png"))));
        listModel.add(5, new Country("Tuvalu", new ImageIcon(Toolkit.getDefaultToolkit().getImage("flag_tuvalu.png"))));
        JList<Country> jList = new JList<>(listModel);
        jList.setCellRenderer(new ListCountryRenderer());
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("Bhutan", "Thimphu");
        hashMap.put("Canada", "Ottawa");
        hashMap.put("Iceland", "Reykjavik");
        hashMap.put("Monaco", "Monaco-Ville");
        hashMap.put("Switzerland", "Bern");
        hashMap.put("Tuvalu", "Funafuti");
        JLabel chosen = new JLabel("");
        jList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                chosen.setIcon(listModel.get(e.getFirstIndex()).getFlag());
                chosen.setText(listModel.get(e.getFirstIndex()).toString() +" "+ hashMap.get(listModel.get(e.getFirstIndex()).toString()));
            }
        });
        panel1.add(chosen, BorderLayout.EAST);
        panel1.add(jList, BorderLayout.CENTER);

        JPanel panel2 = new JPanel();
        
        Container container = this.getContentPane();
        tabbedPane.add(panel1, "Task 1");
        tabbedPane.add(panel2, "Task 2");
        container.add(tabbedPane);
    }
}
