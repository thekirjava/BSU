package bsu.fpmi.artsiushkevich.window;

import bsu.fpmi.artsiushkevich.parsers.SAXParse;
import bsu.fpmi.artsiushkevich.parsers.XMLCreater;
import bsu.fpmi.artsiushkevich.utility.LibraryCard;
import bsu.fpmi.artsiushkevich.utility.Pair;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeModel;
import javax.swing.tree.TreeModel;
import javax.swing.tree.TreePath;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.URL;

import static bsu.fpmi.artsiushkevich.parsers.DOMParser.parseDOM;
import static bsu.fpmi.artsiushkevich.parsers.HTMLCreater.createHTML;

public class MainWindow extends JFrame {
    public MainWindow() {
        this.setTitle("Lab12");
        this.setSize(1000, 600);
        //this.setResizable(false);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container container = this.getContentPane();
        JMenuBar menuBar = new JMenuBar();
        JMenu file = new JMenu("File");
        JMenuItem openDOM = new JMenuItem("Open with DOM");
        JMenuItem openSAX = new JMenuItem("Open with SAX");
        JMenuItem checkXSD = new JMenuItem("Check with XSD");
        JMenuItem createHTML = new JMenuItem("Create HTML");
        JMenuItem saveXML = new JMenuItem("Save to xml");
        JMenuItem openBinary = new JMenuItem("Open binary");
        JMenuItem saveBinary = new JMenuItem("Save to binary");
        JMenuItem addItem = new JMenuItem("Add item");
        JMenuItem deleteItem = new JMenuItem("Delete item");
        file.add(openDOM);
        file.add(openSAX);
        file.add(checkXSD);
        file.add(createHTML);
        file.add(saveXML);
        file.add(openBinary);
        file.add(saveBinary);
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
        openSAX.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("XML files", "xml"));
            fileChooser.setCurrentDirectory(new File("."));
            if (fileChooser.showDialog(MainWindow.this, "Open") == JFileChooser.APPROVE_OPTION) {
                try {
                    SAXParse parse = new SAXParse();
                    Pair<LibraryCard, LibraryCard> ans = parse.parseSax(fileChooser.getSelectedFile());
                    JPanel ansPane = new JPanel();
                    JLabel taker = new JLabel(ans.first.toString());
                    JLabel returner = new JLabel(ans.second.toString());
                    ansPane.setLayout(new GridLayout(2, 1));
                    ansPane.add(taker);
                    ansPane.add(returner);
                    JOptionPane.showMessageDialog(MainWindow.this, ansPane);
                } catch (ParserConfigurationException | IOException | SAXException parserConfigurationException) {
                    parserConfigurationException.printStackTrace();
                }
            }
        });
        checkXSD.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    JFileChooser fileChooser = new JFileChooser();
                    fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("XML files", "xml"));
                    fileChooser.setCurrentDirectory(new File("."));
                    if (fileChooser.showDialog(MainWindow.this, "Open") == JFileChooser.APPROVE_OPTION) {
                        SchemaFactory factory = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
                        //Schema schema = factory.newSchema(new File("D:\\Coding\\BSU\\Programming\\Java\\sem4\\Lab12\\src\\resources\\schema.xsd"));
                        Schema schema = factory.newSchema(Thread.currentThread().getContextClassLoader().getResource("schema.xsd"));
                        Validator validator = schema.newValidator();
                        validator.validate(new StreamSource(fileChooser.getSelectedFile()));
                        JOptionPane.showMessageDialog(MainWindow.this, "Document is valid");
                    }
                } catch (SAXParseException exception) {
                    JOptionPane.showMessageDialog(MainWindow.this, "Document isn't valid");
                } catch (SAXException | IOException exception) {
                    exception.printStackTrace();
                }
            }
        });
        createHTML.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.addChoosableFileFilter(new FileNameExtensionFilter("XML files", "xml"));
                fileChooser.setCurrentDirectory(new File("."));
                if (fileChooser.showDialog(MainWindow.this, "Open") == JFileChooser.APPROVE_OPTION) {
                    try {
                        createHTML(fileChooser.getSelectedFile(), new File(Thread.currentThread().getContextClassLoader().getResource("style.xsl").getPath()));
                    } catch (IOException | TransformerException ioException) {
                        ioException.printStackTrace();
                    }
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
                if ((path.getLastPathComponent().toString().equals("takenBooks")) || (path.getLastPathComponent().toString().equals("returnedBooks"))) {
                    JPanel panel = new JPanel();
                    panel.setLayout(new GridLayout(2, 1));
                    JLabel label = new JLabel("Input book title");
                    JTextField field = new JTextField();
                    panel.add(label);
                    panel.add(field);
                    if (JOptionPane.showConfirmDialog(MainWindow.this, panel, "Input", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                        String s = field.getText();
                        DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) path.getLastPathComponent();
                        treeNode.add(new DefaultMutableTreeNode(s));
                        tree.updateUI();
                    }
                }
                if (path.getPathComponent(0).equals(path.getLastPathComponent())) {
                    JPanel panel = new JPanel();
                    panel.setLayout(new GridLayout(2, 3));
                    JLabel nameLabel = new JLabel("Input name");
                    JLabel surnameLabel = new JLabel("Input surname");
                    JLabel idLabel = new JLabel("Input id");
                    JTextField nameField = new JTextField("John");
                    JTextField surnameField = new JTextField("Dow");
                    JTextField idField = new JTextField("123456");
                    panel.add(nameLabel);
                    panel.add(surnameLabel);
                    panel.add(idLabel);
                    panel.add(nameField);
                    panel.add(surnameField);
                    panel.add(idField);
                    if (JOptionPane.showConfirmDialog(MainWindow.this, panel, "Input", JOptionPane.OK_CANCEL_OPTION) == JOptionPane.OK_OPTION) {
                        DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) path.getLastPathComponent();
                        DefaultMutableTreeNode card = new DefaultMutableTreeNode("libraryCard");
                        card.add(new DefaultMutableTreeNode("takenBooks"));
                        card.add(new DefaultMutableTreeNode("returnedBooks"));
                        card.add(new DefaultMutableTreeNode("id=" + idField.getText()));
                        card.add(new DefaultMutableTreeNode("name=" + nameField.getText()));
                        card.add(new DefaultMutableTreeNode("surname=" + surnameField.getText()));
                        treeNode.add(card);
                        tree.updateUI();
                    }
                }
            }
        });
        deleteItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                TreePath path = tree.getSelectionPath();
                if ((path.getLastPathComponent().toString().equals("libraryCard")) || (path.getParentPath().getLastPathComponent().toString().equals("takenBooks")) ||
                        (path.getParentPath().getLastPathComponent().toString().equals("returnedBooks"))) {
                    DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) path.getLastPathComponent();
                    DefaultMutableTreeNode parentNode = (DefaultMutableTreeNode) path.getParentPath().getLastPathComponent();
                    parentNode.remove(treeNode);
                    tree.updateUI();
                }
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
        openBinary.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new FileNameExtensionFilter("Binary files", "bin"));
                fileChooser.setCurrentDirectory(new File("."));
                if (fileChooser.showDialog(MainWindow.this, "Open") == JFileChooser.APPROVE_OPTION) {
                    try {
                        ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileChooser.getSelectedFile()));
                        tree.setModel((TreeModel) inputStream.readObject());
                    } catch (IOException | ClassNotFoundException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        });
        saveBinary.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setFileFilter(new FileNameExtensionFilter("Binary files", "bin"));
                fileChooser.setCurrentDirectory(new File("."));
                if (fileChooser.showDialog(MainWindow.this, "Save") == JFileChooser.APPROVE_OPTION) {
                    try {
                        ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileChooser.getSelectedFile()));
                        DefaultTreeModel model = (DefaultTreeModel) tree.getModel();
                        outputStream.writeObject(model);
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        });
        container.add(tree);
        this.setJMenuBar(menuBar);
    }

    DefaultTreeModel treeModel;
}
