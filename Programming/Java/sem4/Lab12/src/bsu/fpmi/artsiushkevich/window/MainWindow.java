package bsu.fpmi.artsiushkevich.window;

import bsu.fpmi.artsiushkevich.parsers.XMLCreater;
import org.xml.sax.SAXException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreePath;
import javax.xml.parsers.ParserConfigurationException;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import static bsu.fpmi.artsiushkevich.parsers.DOMParser.parseDOM;

public class MainWindow extends JFrame {
    public MainWindow() {
        this.setTitle("Lab12");
        this.setSize(1000, 600);
        this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container container = this.getContentPane();
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenuItem openDOM = new JMenuItem("Open with DOM");
        JMenuItem saveXML = new JMenuItem("Save to xml");
        JMenuItem addItem = new JMenuItem("Add item");
        JMenuItem deleteItem = new JMenuItem("Delete item");
        file.add(openDOM);
        file.add(saveXML);
        menuBar.add(file);
        container.setLayout(new FlowLayout());
        treeModel = new DefaultTreeModel(new DefaultMutableTreeNode(""));
        JPopupMenu popupMenu = new JPopupMenu();
        popupMenu.add(addItem);
        popupMenu.add(deleteItem);
        openDOM.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("XML files", "xml"));
            fileChooser.setCurrentDirectory(new File("."));
            if (fileChooser.showDialog(MainWindow.this, "Open") == JFileChooser.APPROVE_OPTION) {
                try {
                    treeModel.setRoot(parseDOM(fileChooser.getSelectedFile()));
                } catch (ParserConfigurationException | IOException | SAXException parserConfigurationException) {
                    parserConfigurationException.printStackTrace();
                }
            }
        });
        JTree tree = new JTree(treeModel);
        tree.setComponentPopupMenu(popupMenu);
        tree.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    int selRow = tree.getRowForLocation(e.getX(), e.getY());
                    TreePath selPath = tree.getPathForLocation(e.getX(), e.getY());
                    tree.setSelectionPath(selPath);
                    if (selRow > -1) {
                        tree.setSelectionRow(selRow);
                    }
                }
            }
        });
        addItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TreePath path = tree.getSelectionPath();
                String s = JOptionPane.showInputDialog(MainWindow.this, "Input value or attribute to be added");
                DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) path.getLastPathComponent();
                treeNode.add(new DefaultMutableTreeNode(s));
                tree.updateUI();
            }
        });
        deleteItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TreePath path = tree.getSelectionPath();
                DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) path.getLastPathComponent();
                DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) path.getParentPath().getLastPathComponent();
                parentNode.remove(treeNode);
                tree.updateUI();
            }
        });
        saveXML.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new FileNameExtensionFilter("XML files", "xml"));
                fileChooser.setCurrentDirectory(new File("."));
                if (fileChooser.showDialog(MainWindow.this, "Save") == JFileChooser.APPROVE_OPTION) {
                    try {
                        XMLCreater.createXML(fileChooser.getSelectedFile(), (DefaultTreeModel) tree.getModel());
                    } catch (FileNotFoundException fileNotFoundException) {
                        fileNotFoundException.printStackTrace();
                    }
                }
            }
        });
        container.add(tree);
        this.setJMenuBar(menuBar);
    }

    DefaultTreeModel treeModel;
}
