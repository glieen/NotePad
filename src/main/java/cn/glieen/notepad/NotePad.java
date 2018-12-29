package cn.glieen.notepad;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;


/**
 * author: Glieen
 * date: 2018/12/29
 * time: 11:20
 * desc:
 */

public class NotePad extends JFrame implements ActionListener {

    private static final long serialVersionUID = 3161812866611339L;
    private final int WIDTH = 400;
    private final int HEIGHT = 500;
    private Image icon;
    private ImageIcon check;
    private int FONTSIZE = 20;
    private String filePath = "";
    private String text = "";
    private int wordNum = 0;

    private JMenuBar menubar = new JMenuBar();

    private JMenu file = new JMenu("File");
    private JMenuItem newFile = new JMenuItem("New File");
    private JMenuItem openFile = new JMenuItem("Open File");
    private JMenuItem saveFile = new JMenuItem("Save File");
    private JMenuItem saveAs = new JMenuItem("Save As");
    private JMenuItem close = new JMenuItem("Close");

    private JMenu edit = new JMenu("Edit");
    private JMenuItem cut = new JMenuItem("Cut");
    private JMenuItem copy = new JMenuItem("Copy");
    private JMenuItem paste = new JMenuItem("Paste");
    private JMenuItem delete = new JMenuItem("Delete");
    private JMenuItem selectAll = new JMenuItem("Select All");
    private JMenuItem find = new JMenuItem("Find");
    private JMenuItem date = new JMenuItem("Date");

    private JMenuItem mouse_cut = new JMenuItem("Cut");
    private JMenuItem mouse_copy = new JMenuItem("Copy");
    private JMenuItem mouse_paste = new JMenuItem("Paste");
    private JMenuItem mouse_delete = new JMenuItem("Delete");
    private JMenuItem mouse_selectAll = new JMenuItem("Select All");

    private JMenu format = new JMenu("Format");
    private JMenuItem wordWrap = new JMenuItem("Word Wrap");
    private JMenuItem fontSize = new JMenuItem("Font Size");

    private JMenu view = new JMenu("View");
    private JMenuItem status = new JMenuItem("Status");
    private JMenuItem showMenu = new JMenuItem("Hide Menu");

    private JMenu help = new JMenu("Help");
    private JMenuItem findHelp = new JMenuItem("Find Help");
    private JMenuItem about = new JMenuItem("About");

    private JTextArea textArea = new JTextArea();

    private JScrollPane scrollPane = new JScrollPane(textArea);

    private JPopupMenu popupMenu = new JPopupMenu();

    private JPanel panel = new JPanel(new GridLayout(0, 2));
    private JLabel wordNums = new JLabel("Word Numbers : " + 0);
    private JLabel location = new JLabel("Location As : " + 0);

    private FileDialog openFileDialog = new FileDialog(this, "Open File",
            FileDialog.LOAD);
    private FileDialog saveFileDialog = new FileDialog(this, "Save File",
            FileDialog.SAVE);
    private FileDialog saveAsDialog = new FileDialog(this, "Save As",
            FileDialog.SAVE);

    public NotePad() {
        init();
    }

    public void init() {

        try {
            icon = ImageIO.read(NotePad.class.getResource("/icon/icon.png"));
            check = new ImageIcon(ImageIO.read(NotePad.class
                    .getResource("/icon/check.png")));
        } catch (IOException e1) {
            e1.printStackTrace();
        }

        panel.add(wordNums);
        panel.add(location);

        file.add(newFile);
        newFile.addActionListener(this);
        file.add(openFile);
        openFile.addActionListener(this);
        file.add(saveFile);
        saveFile.addActionListener(this);
        file.add(saveAs);
        saveAs.addActionListener(this);
        file.add(close);
        close.addActionListener(this);

        edit.add(cut);
        cut.addActionListener(this);
        edit.add(copy);
        copy.addActionListener(this);
        edit.add(paste);
        paste.addActionListener(this);
        edit.add(delete);
        delete.addActionListener(this);
        edit.add(selectAll);
        selectAll.addActionListener(this);
        edit.add(find);
        find.addActionListener(this);
        edit.add(date);
        date.addActionListener(this);

        format.add(wordWrap);
        wordWrap.addActionListener(this);
        format.add(fontSize);
        fontSize.addActionListener(this);

        view.add(status);
        status.addActionListener(this);
        status.setIcon((Icon) check);
        view.add(showMenu);
        showMenu.addActionListener(this);

        help.add(findHelp);
        findHelp.addActionListener(this);
        help.add(about);
        about.addActionListener(this);

        menubar.add(file);
        menubar.add(edit);
        menubar.add(format);
        menubar.add(view);
        menubar.add(help);

        popupMenu.add(mouse_cut);
        mouse_cut.addActionListener(this);
        popupMenu.add(mouse_copy);
        mouse_copy.addActionListener(this);
        popupMenu.add(mouse_paste);
        mouse_paste.addActionListener(this);
        popupMenu.add(mouse_delete);
        mouse_delete.addActionListener(this);
        popupMenu.add(mouse_selectAll);
        mouse_selectAll.addActionListener(this);

        textArea.setFont(new Font("Microsoft YaHei UI", Font.BOLD, FONTSIZE));
        textArea.addMouseListener(new MouseAdapter() {
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    popupMenu.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });
        textArea.getDocument().addDocumentListener(new DocumentListener() {
            public void removeUpdate(DocumentEvent e) {
                text = textArea.getText();
                wordNum = text.length();
                wordNums.setText("Word Numbers : " + String.valueOf(wordNum));
                location.setText("Location As : "
                        + String.valueOf(textArea.getSelectionEnd()));
            }

            public void insertUpdate(DocumentEvent e) {
                text = textArea.getText();
                wordNum = text.length();
                wordNums.setText("Word Numbers : " + String.valueOf(wordNum));
                location.setText("Location As : "
                        + String.valueOf(textArea.getSelectionEnd()));
            }

            public void changedUpdate(DocumentEvent e) {
                text = textArea.getText();
                wordNum = text.length();
                wordNums.setText("Word Numbers : " + String.valueOf(wordNum));
                location.setText("Location As : "
                        + String.valueOf(textArea.getSelectionEnd()));
            }
        });
        textArea.addMouseListener(new MouseListener() {

            public void mouseReleased(MouseEvent e) {
                int start = textArea.getSelectionStart();
                int end = textArea.getSelectionEnd();
                if (start == end) {
                    location.setText("Location As : " + start);
                } else {
                    location.setText("Selected : " + (start + 1) + " - "
                            + (end + 1));
                }
            }

            public void mousePressed(MouseEvent e) {

            }

            public void mouseExited(MouseEvent e) {

            }

            public void mouseEntered(MouseEvent e) {

            }

            public void mouseClicked(MouseEvent e) {

            }
        });

        panel.setVisible(true);

        this.add("Center", scrollPane);
        this.add("South", panel);
        this.setJMenuBar(menubar);
        this.setTitle("NotePad");
        this.setSize(WIDTH, HEIGHT);
        this.setIconImage(icon);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        this.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                int result = JOptionPane.showConfirmDialog(null,
                        "Do you save this document?", "Warning",
                        JOptionPane.YES_NO_CANCEL_OPTION);
                if (result == 0) {
                    saveFile();
                    System.exit(0);
                } else if (result == 1) {
                    System.exit(0);
                }
            }
        });
        this.setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        Object object = e.getSource();
        if (object == newFile) {
            int result = JOptionPane.showConfirmDialog(this,
                    "Do you save this document?", "Warning",
                    JOptionPane.YES_NO_CANCEL_OPTION);
            if (result == 0) {
                if (saveFile()) {
                    textArea.setText("");
                    filePath = "";
                }
            } else if (result == 1) {
                textArea.setText("");
                filePath = "";
            }
        } else if (object == openFile) {
            int result = JOptionPane.showConfirmDialog(this,
                    "Do you save this document?", "Warning",
                    JOptionPane.YES_NO_CANCEL_OPTION);
            if (result == 0) {
                if (saveFile()) {
                    textArea.setText("");
                    filePath = "";
                }
                openFile();
            } else if (result == 1) {
                textArea.setText("");
                filePath = "";
                openFile();
            }
        } else if (object == saveFile) {
            saveFile();
        } else if (object == saveAs) {
            saveAs();
        } else if (object == close) {
            int result = JOptionPane.showConfirmDialog(this,
                    "Do you save this document?", "Warning",
                    JOptionPane.YES_NO_CANCEL_OPTION);
            if (result == 0) {
                saveFile();
                this.dispose();
            } else if (result == 1) {
                this.dispose();
            }
        } else if (object == cut || object == mouse_cut) {
            textArea.cut();
        } else if (object == copy || object == mouse_copy) {
            textArea.copy();
        } else if (object == paste || object == mouse_paste) {
            textArea.paste();
        } else if (object == delete || object == mouse_delete) {
            String text = textArea.getText();
            int start = textArea.getSelectionStart();
            int end = textArea.getSelectionEnd();
            textArea.setText(text.substring(0, start) + text.substring(end));
        } else if (object == selectAll || object == mouse_selectAll) {
            textArea.selectAll();
        } else if (object == find) {
            text = textArea.getText();
            new FindString();
        } else if (object == date) {
            String text = textArea.getText();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:MM:ss");
            int start = textArea.getSelectionStart();
            int end = textArea.getSelectionEnd();
            textArea.setText(text.substring(0, start) + sdf.format(new Date())
                    + text.substring(end));
        } else if (object == wordWrap) {
            if (textArea.getLineWrap()) {
                textArea.setLineWrap(false);
                wordWrap.setIcon(null);
            } else {
                textArea.setLineWrap(true);
                wordWrap.setIcon(check);
            }
        } else if (object == fontSize) {
            Object[] fontSize = {10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30,
                    32, 34, 36, 38, 40};
            Integer FONTSIZE = (Integer) JOptionPane.showInputDialog(null,
                    "plese select the font size:\n", "Font Size",
                    JOptionPane.PLAIN_MESSAGE, null, fontSize, textArea
                            .getFont().getSize());
            if (FONTSIZE != null)
                textArea.setFont(new Font("Microsoft YaHei UI", Font.BOLD,
                        FONTSIZE));
        } else if (object == status) {
            if (panel.isVisible()) {
                panel.setVisible(false);
                status.setIcon(null);
                this.repaint();
            } else {
                panel.setVisible(true);
                status.setIcon(check);
                this.repaint();
            }
        } else if (object == showMenu) {
            if (menubar.isVisible()) {
                menubar.setVisible(false);
                showMenu.setText("Show Menu");
                view.remove(showMenu);
                popupMenu.add(showMenu);
            } else {
                menubar.setVisible(true);
                showMenu.setText("Hide Menu");
                popupMenu.remove(showMenu);
                view.add(showMenu);
            }
        } else if (object == findHelp) {
            try {
                URI uri = new URI("www.baidu.com");
                Desktop.getDesktop().browse(uri);
            } catch (URISyntaxException e1) {
                e1.printStackTrace();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        } else if (object == about) {
            JOptionPane
                    .showOptionDialog(
                            null,
                            "NotePad\n\nVersion 1.0\n\nLanguage : Java\n\nAuthor : Glieen\n",
                            "About", JOptionPane.PLAIN_MESSAGE, 0,
                            new ImageIcon(""), null, null);
        }
    }

    public void openFile() {
        String buffer = "";
        String line = null;
        BufferedReader br;
        openFileDialog.setVisible(true);
        if (openFileDialog.getFile() != null) {
            filePath = openFileDialog.getDirectory() + openFileDialog.getFile();
            File file = new File(filePath);
            openFileDialog.dispose();
            try {
                br = new BufferedReader(new InputStreamReader(
                        new FileInputStream(file), "UTF-8"));
                while ((line = br.readLine()) != null) {
                    buffer += (line + "\n");
                }
                textArea.setText(buffer);
                br.close();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    public boolean saveFile() {
        BufferedWriter bw;
        File file;
        String fileName = "";
        String str = textArea.getText();
        if (filePath == "") {
            saveFileDialog.setVisible(true);
            fileName = saveFileDialog.getFile();
        } else {
            fileName = new File(filePath).getName();
        }
        if (fileName == null) {
            return false;
        } else {
            if (filePath == "") {
                file = new File(saveFileDialog.getDirectory()
                        + saveFileDialog.getFile());
            } else {
                file = new File(filePath);
            }
            saveFileDialog.dispose();
            try {
                bw = new BufferedWriter(new OutputStreamWriter(
                        new FileOutputStream(file), "UTF-8"));
                String[] strs = str.split("\n");
                for (String string : strs) {
                    bw.write(string);
                    bw.newLine();
                }
                bw.flush();
                bw.close();
                return true;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public void saveAs() {
        BufferedWriter bw;
        File file;
        String str = textArea.getText();
        saveAsDialog.setVisible(true);
        filePath = saveAsDialog.getDirectory() + saveAsDialog.getFile();
        file = new File(filePath);
        saveAsDialog.dispose();
        try {
            bw = new BufferedWriter(new OutputStreamWriter(
                    new FileOutputStream(file), "UTF-8"));
            bw.write(str.toCharArray(), 0, str.length());
            bw.flush();
            bw.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    class FindString extends JFrame implements ActionListener {

        private static final long serialVersionUID = -8053513872265374947L;
        private final int WIDTH = 270;
        private final int HEIGTH = 110;
        private int start = 0;
        private int end = 0;

        JLabel find = new JLabel("FInd");
        JLabel replace = new JLabel("Replace");

        JTextField findText = new JTextField("");
        JTextField replacetext = new JTextField();

        JButton bfind = new JButton("Find");
        JButton breplace = new JButton("Replace");

        public FindString() {

            this.add(find);
            this.add(replace);
            this.add(findText);
            findText.getDocument().addDocumentListener(new DocumentListener() {

                public void removeUpdate(DocumentEvent e) {
                    start = 0;
                    end = 0;
                    bfind.setText("Find");
                }

                public void insertUpdate(DocumentEvent e) {
                    start = 0;
                    end = 0;
                    bfind.setText("Find");
                }

                public void changedUpdate(DocumentEvent e) {
                    start = 0;
                    end = 0;
                    bfind.setText("Find");
                }
            });
            this.add(replacetext);
            this.add(bfind);
            bfind.addActionListener(this);
            this.add(breplace);
            breplace.addActionListener(this);

            find.setBounds(10, 10, 30, 20);
            findText.setBounds(45, 10, 60, 20);
            replace.setBounds(130, 10, 60, 20);
            replacetext.setBounds(190, 10, 60, 20);
            bfind.setBounds(40, 50, 80, 20);
            breplace.setBounds(145, 50, 80, 20);

            this.setTitle("Find");
            this.setLayout(null);
            this.setSize(WIDTH, HEIGTH);
            this.setResizable(false);
            this.setVisible(true);
            this.setAlwaysOnTop(true);
            this.setLocationRelativeTo(null);
            this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        }

        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == bfind) {
                if (findText.getText().trim() != "") {
                    if (text.indexOf(findText.getText(), end) != -1) {
                        start = text.indexOf(findText.getText(), end);
                        end = start + findText.getText().length();
                        textArea.select(start, end);
                        bfind.setText("Next");
                    } else {
                        start = end;
                        textArea.select(start, end);
                        JOptionPane.showMessageDialog(this, "There isn't  \""
                                + findText.getText() + "\" in textarea!");
                    }
                } else {
                    JOptionPane.showMessageDialog(this, "Findtext is empty!");
                }
            } else if (e.getSource() == breplace) {
                if (start != end) {
                    textArea.replaceSelection(replacetext.getText());
                    start = end;
                }
            }
        }

    }
}
