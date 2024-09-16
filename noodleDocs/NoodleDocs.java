package noodleDocs;
//Ibrahim Faruquee
import javax.print.*;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.event.PrintJobAdapter;
import javax.print.event.PrintJobEvent;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.UndoableEditEvent;
import javax.swing.event.UndoableEditListener;
import java.awt.event.*;
import java.io.PrintWriter;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.undo.CannotRedoException;
import javax.swing.undo.CannotUndoException;
import javax.swing.undo.UndoManager;

class MainFrame extends JFrame implements ActionListener, DocumentListener, KeyListener, ItemListener {
	private JFrame frame = new JFrame();
	private ImageIcon frameIcon = new ImageIcon("icons8-google-docs-48.png");

	private JTextArea textArea = new JTextArea();
	private JScrollPane scrollPane = new JScrollPane(textArea);
	private JFileChooser fileChooser;
	private String currentFile = "Untitled";
	private boolean isSaved = true;
	private Color currentColor = Color.BLACK;
	private UndoManager undoManager = new UndoManager();

	private JToolBar toolBar = new JToolBar();
	private ImageIcon undoIcon = new ImageIcon("undo_FILL0_wght400_GRAD0_opsz48 (1).png");
	private ImageIcon redoIcon = new ImageIcon("redo_FILL0_wght400_GRAD0_opsz48 (1).png");
	private ImageIcon printIcon = new ImageIcon("print_FILL0_wght400_GRAD0_opsz48 (1).png");
	private ImageIcon decreaseIcon = new ImageIcon("text_decrease_FILL0_wght400_GRAD.png");
	private ImageIcon increaseIcon = new ImageIcon("text_increase_FILL0_wght400_GRAD.png");
	private ImageIcon bulletIcon = new ImageIcon("format_list_bulleted_FILL0_wght4.png");
	private ImageIcon boldIcon = new ImageIcon("format_bold_FILL0_wght400_GRAD0_.png");
	private ImageIcon italicIcon = new ImageIcon("format_italic_FILL0_wght400_GRAD.png");
	private ImageIcon colorIcon = new ImageIcon("format_color_text_FILL0_wght400_.png");
	private JButton undoButton = new JButton(undoIcon);
	private JButton redoButton = new JButton(redoIcon);
	private JButton printButton = new JButton(printIcon);
	private JComboBox fontOptions = new JComboBox();
	private JButton decreaseButton = new JButton(decreaseIcon);
	private JTextField sizeField = new JTextField(textArea.getFont().getSize() + "");
	private JButton increaseButton = new JButton(increaseIcon);
	private JCheckBox boldButton = new JCheckBox(boldIcon);
	private JCheckBox italicButton = new JCheckBox(italicIcon);
	private JButton colorButton = new JButton(colorIcon);
	private JButton bulletButton = new JButton(bulletIcon);

	private JMenuBar menuBar = new JMenuBar();

	private JMenu fileMenu = new JMenu("File");
	private JMenuItem newFileItem = new JMenuItem("New");
	private JMenuItem openFileItem = new JMenuItem("Open");
	private JMenuItem saveFileItem = new JMenuItem("Save");
	private JMenuItem saveAsFileItem = new JMenuItem("Save As...");
	private JMenuItem printItem = new JMenuItem("Print");

	private JMenu editMenu = new JMenu("Edit");
	private JMenuItem undoItem = new JMenuItem("Undo");
	private JMenuItem redoItem = new JMenuItem("Redo");
	private JMenuItem cutItem = new JMenuItem("Cut");
	private JMenuItem copyItem = new JMenuItem("Copy");
	private JMenuItem pasteItem = new JMenuItem("Paste");
	private JMenuItem selectAllItem = new JMenuItem("Select All");

	private JMenu formatMenu = new JMenu("Format");
	private JMenu textMenu = new JMenu("Text");
	private JCheckBoxMenuItem boldItem = new JCheckBoxMenuItem("Bold");
	private JCheckBoxMenuItem italicItem = new JCheckBoxMenuItem("Italic");
	private JMenuItem colorItem = new JMenuItem("Color");
	private JMenu sizeMenu = new JMenu("Size");
	private JMenuItem increaseItem = new JMenuItem("Increase font size");
	private JMenuItem decreaseItem = new JMenuItem("Decrease font size");
	private JMenuItem bulletItem = new JMenuItem("Bullets");

	private JMenu help = new JMenu("Help");
	private JMenu nohelp = new JMenu("There is no help :)");
	private JMenuItem enjoy = new JMenuItem("Enjoy!");

	private JPopupMenu popupMenu = new JPopupMenu();
	private JMenuItem cutPopup = new JMenuItem("Cut");
	private JMenuItem copyPopup = new JMenuItem("Copy");
	private JMenuItem pastePopup = new JMenuItem("Paste");

	private boolean bulletBool = false;

	public MainFrame() {
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension d = tk.getScreenSize();
		int screenHeight = d.height;
		int screenWidth = d.width;
		setSize(screenWidth, screenHeight);
		setLocation(0, 0);
		setTitle("Noodle Docs");
		setIconImage(frameIcon.getImage());
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		initializeFontOptions();

		textArea.getDocument().addDocumentListener(this);
		textArea.setFont(new Font("SansSerif", Font.PLAIN, 14));
		textArea.setLineWrap(true);
		textArea.addKeyListener(this);
		textArea.getDocument().addUndoableEditListener(new UndoableEditListener() {
			public void undoableEditHappened(UndoableEditEvent e) {
				undoManager.addEdit(e.getEdit());
			}
		});
		add(scrollPane, "Center");

		add(toolBar, "North");
		toolBar.add(undoButton);
		toolBar.add(redoButton);
		toolBar.add(printButton);
		toolBar.addSeparator();
		fontOptions.setPreferredSize(new Dimension(150, 32));
		fontOptions.setMinimumSize(new Dimension(150, 32));
		fontOptions.setMaximumSize(new Dimension(175, 32));
		toolBar.add(fontOptions);
		toolBar.addSeparator();
		toolBar.add(decreaseButton);
		sizeField.setEditable(false);
		sizeField.setPreferredSize(new Dimension(45, 32));
		sizeField.setMinimumSize(new Dimension(45, 32));
		sizeField.setMaximumSize(new Dimension(45, 32));
		sizeField.setHorizontalAlignment(SwingConstants.CENTER);
		toolBar.add(sizeField);
		toolBar.add(increaseButton);
		toolBar.addSeparator();
		toolBar.add(boldButton);
		toolBar.add(italicButton);
		toolBar.add(colorButton);
		toolBar.addSeparator();
		toolBar.add(bulletButton);
		undoButton.setFocusPainted(false);
		redoButton.setFocusPainted(false);
		printButton.setFocusPainted(false);
		decreaseButton.setFocusPainted(false);
		increaseButton.setFocusPainted(false);
		colorButton.setFocusPainted(false);
		bulletButton.setFocusPainted(false);
		undoButton.addActionListener(this);
		redoButton.addActionListener(this);
		printButton.addActionListener(this);
		fontOptions.addActionListener(this);
		decreaseButton.addActionListener(this);
		increaseButton.addActionListener(this);
		boldButton.addActionListener(this);
		boldButton.addItemListener(this);
		italicButton.addItemListener(this);
		italicButton.addActionListener(this);
		colorButton.addActionListener(this);
		bulletButton.addActionListener(this);

		setJMenuBar(menuBar);
		menuBar.add(fileMenu);
		menuBar.add(editMenu);
		menuBar.add(formatMenu);
		menuBar.add(help);
		fileMenu.addActionListener(this);
		editMenu.addActionListener(this);
		help.addActionListener(this);

		fileMenu.add(newFileItem);
		fileMenu.add(openFileItem);
		fileMenu.addSeparator();
		fileMenu.add(saveFileItem);
		fileMenu.add(saveAsFileItem);
		fileMenu.addSeparator();
		fileMenu.add(printItem);
		newFileItem.addActionListener(this);
		openFileItem.addActionListener(this);
		saveFileItem.addActionListener(this);
		saveAsFileItem.addActionListener(this);
		printItem.addActionListener(this);
		newFileItem.setAccelerator(KeyStroke.getKeyStroke('N', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		openFileItem.setAccelerator(KeyStroke.getKeyStroke('O', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		saveFileItem.setAccelerator(KeyStroke.getKeyStroke('S', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		printItem.setAccelerator(KeyStroke.getKeyStroke('P', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

		editMenu.add(undoItem);
		editMenu.add(redoItem);
		editMenu.addSeparator();
		editMenu.add(cutItem);
		editMenu.add(copyItem);
		editMenu.add(pasteItem);
		editMenu.addSeparator();
		editMenu.add(selectAllItem);
		undoItem.addActionListener(this);
		redoItem.addActionListener(this);
		cutItem.addActionListener(this);
		copyItem.addActionListener(this);
		pasteItem.addActionListener(this);
		selectAllItem.addActionListener(this);
		undoItem.setAccelerator(KeyStroke.getKeyStroke('Z', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		redoItem.setAccelerator(KeyStroke.getKeyStroke('Y', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		cutItem.setAccelerator(KeyStroke.getKeyStroke('X', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		copyItem.setAccelerator(KeyStroke.getKeyStroke('C', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		pasteItem.setAccelerator(KeyStroke.getKeyStroke('V', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));
		selectAllItem.setAccelerator(KeyStroke.getKeyStroke('A', Toolkit.getDefaultToolkit().getMenuShortcutKeyMask()));

		formatMenu.add(textMenu);
		textMenu.add(boldItem);
		textMenu.add(italicItem);
		textMenu.add(sizeMenu);
		formatMenu.add(colorItem);
		sizeMenu.add(increaseItem);
		sizeMenu.add(decreaseItem);
		formatMenu.add(bulletItem);
		boldItem.addActionListener(this);
		italicItem.addActionListener(this);
		colorItem.addActionListener(this);
		increaseItem.addActionListener(this);
		decreaseItem.addActionListener(this);
		bulletItem.addActionListener(this);

		help.add(nohelp);
		nohelp.add(enjoy);

		popupMenu.add(cutPopup);
		popupMenu.add(copyPopup);
		popupMenu.add(pastePopup);
		cutPopup.addActionListener(this);
		copyPopup.addActionListener(this);
		pastePopup.addActionListener(this);

		textArea.addMouseListener(new MouseAdapter() {
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					popupMenu.show(e.getComponent(), e.getX(), e.getY());
				}
			}
		});

		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				exit();
			}
		});

	}

	private void initializeFontOptions() {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		String[] fonts = ge.getAvailableFontFamilyNames();

		int fontL = fonts.length;
		for (int i = 0; i < fontL; i++) {
			fontOptions.addItem(fonts[i]);
		}
	}

	public void exitAction(Object exitSource) {
		System.exit(0);
	}

	private void newFile() {
		if (!isSaved) {
			int option = JOptionPane.showConfirmDialog(frame, "Do you want to save changes to " + currentFile + "?",
					"Save Changes", JOptionPane.YES_NO_CANCEL_OPTION);
			if (option == JOptionPane.YES_OPTION) {
				saveFile();
			} else if (option == JOptionPane.CANCEL_OPTION) {
				return;
			}
		}
		currentFile = "Untitled";
		isSaved = true;
		textArea.setText("");
		frame.setTitle(currentFile);
	}

	private void openFile() {
		if (!isSaved) {
			int option = JOptionPane.showConfirmDialog(frame, "Do you want to save changes to " + currentFile + "?",
					"Save Changes", JOptionPane.YES_NO_CANCEL_OPTION);
			if (option == JOptionPane.YES_OPTION) {
				saveFile();
			} else if (option == JOptionPane.CANCEL_OPTION) {
				return;
			}
		}
		fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Open File");
		int result = fileChooser.showOpenDialog(frame);
		if (result == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			try {
				BufferedReader reader = new BufferedReader(new FileReader(file));
				String line = reader.readLine();
				StringBuilder sb = new StringBuilder();
				while (line != null) {
					sb.append(line);
					sb.append(System.lineSeparator());
					line = reader.readLine();
				}
				reader.close();
				textArea.setText(sb.toString());
				currentFile = file.getName();
				isSaved = true;
				frame.setTitle(currentFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void saveFile() {
		if (isSaved) {
			save();
		} else {
			saveFileAs();
		}
	}

	private void saveFileAs() {
		fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Save As");
		int result = fileChooser.showSaveDialog(frame);
		if (result == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			currentFile = file.getName();
			isSaved = true;
			frame.setTitle(currentFile);
			save();
		}
	}

	private void save() {
		try {
			PrintWriter writer = new PrintWriter(new FileWriter(currentFile));
			writer.print(textArea.getText());
			writer.close();
			isSaved = true;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void print() {
		try {
			PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
			PrintService defaultPrintService = PrintServiceLookup.lookupDefaultPrintService();
			PrintRequestAttributeSet attrib = new HashPrintRequestAttributeSet();
			PrintService selectedPrintService = ServiceUI.printDialog(null, 150, 150, printServices,
					defaultPrintService, null, attrib);
			if (selectedPrintService != null) {
				DocPrintJob job = selectedPrintService.createPrintJob();
				job.addPrintJobListener(new PrintJobAdapter() {

					public void printDataTransferCompleted(PrintJobEvent pje) {
						super.printDataTransferCompleted(pje);
					}
				});
				Doc doc = new SimpleDoc(textArea.getText(), DocFlavor.STRING.TEXT_PLAIN, null);
				PrintRequestAttributeSet printSet = new HashPrintRequestAttributeSet();
				printSet.add(new Copies(1));
				job.print(doc, printSet);
			} else {
				JOptionPane.showMessageDialog(null, "Print job cancelled.");
			}
		} catch (Exception ex) {
			ex.printStackTrace();
			JOptionPane.showMessageDialog(null, "Could not print at this time.");
		}
	}

	private void undo() {
		try {
			undoManager.undo();
		} catch (CannotUndoException ex) {
		}
	}

	private void redo() {
		try {
			undoManager.redo();
		} catch (CannotRedoException ex) {
		}
	}

	private void exit() {
		if (!isSaved) {
			int option = JOptionPane.showConfirmDialog(frame, "Do you want to save changes to " + currentFile + "?",
					"Save Changes", JOptionPane.YES_NO_CANCEL_OPTION);
			if (option == JOptionPane.YES_OPTION) {
				saveFile();
			} else if (option == JOptionPane.CANCEL_OPTION) {
				return;
			}
		}
		System.exit(0);
	}

	private void cut() {
		textArea.cut();
	}

	private void copy() {
		textArea.copy();
	}

	private void paste() {
		textArea.paste();
	}

	private void selectAll() {
		textArea.selectAll();
	}

	private void changeFont() {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		String[] fonts = ge.getAvailableFontFamilyNames();
		String font = (String) JOptionPane.showInputDialog(frame, "Select Font", "Font", JOptionPane.PLAIN_MESSAGE,
				null, fonts, textArea.getFont().getFamily());
		if (font != null) {
			int size = textArea.getFont().getSize();
			int style = textArea.getFont().getStyle();
			textArea.setFont(new Font(font, style, size));
		}
	}

	private void changeColor() {
		currentColor = JColorChooser.showDialog(frame, "Select Color", currentColor);
		textArea.setForeground(currentColor);
	}

	private void fontIncrease() {
		String font = textArea.getFont().getFontName();
		int style = textArea.getFont().getStyle();
		int size = textArea.getFont().getSize();
		size += 2;
		textArea.setFont(new Font(font, style, size));
		sizeField.setText(size + "");
	}

	private void fontDecrease() {
		String font = textArea.getFont().getFontName();
		int style = textArea.getFont().getStyle();
		int size = textArea.getFont().getSize();
		size -= 2;
		textArea.setFont(new Font(font, style, size));
		sizeField.setText(size + "");
	}

	public void actionPerformed(ActionEvent evt) {
		String command = evt.getActionCommand();
		Object source = evt.getSource();

		switch (command) {
		case "New":
			newFile();
			break;
		case "Open":
			openFile();
			break;
		case "Save":
			saveFile();
			break;
		case "Save As":
			saveFileAs();
			break;
		case "Print":
			print();
			break;
		case "Cut":
			cut();
			break;
		case "Copy":
			copy();
			break;
		case "Paste":
			paste();
			break;
		case "Select All":
			selectAll();
			break;
		case "Font":
			changeFont();
			break;
		case "Color":
			changeColor();
			break;
		case "Increase font size":
			fontIncrease();
			break;
		case "Decrease font size":
			fontDecrease();
			break;
		}

		int f = ((italicItem.isSelected() && italicButton.isSelected()) ? Font.ITALIC : 0)
				+ ((boldItem.isSelected() && boldButton.isSelected()) ? Font.BOLD : 0);
		textArea.setFont(textArea.getFont().deriveFont(f));

		if (source == fontOptions) {
			String font = String.valueOf(fontOptions.getSelectedItem());

			if (font != null) {
				int size = textArea.getFont().getSize();
				int style = textArea.getFont().getStyle();
				textArea.setFont(new Font(font, style, size));
			}

		}

		if (source == undoItem) {
			undo();
		}

		if (source == redoItem) {
			redo();
		}

		if (source == bulletItem) {
			bulletBool = !bulletBool;
		}

		if (source == undoButton) {
			undo();
		}

		if (source == redoButton) {
			redo();
		}

		if (source == printButton) {
			print();
		}

		if (source == decreaseButton) {
			fontDecrease();
		}

		if (source == increaseButton) {
			fontIncrease();
		}

		if (source == bulletButton) {
			bulletBool = !bulletBool;
		}

		if (source == colorButton) {
			changeColor();
		}

	}

	public void itemStateChanged(ItemEvent e) {
		Object source = e.getSource();

		if (source == boldButton) {
			boldItem.setSelected(boldButton.isSelected());
		} else if (source == boldItem) {
			boldButton.setSelected(boldItem.isSelected());
		}

		if (source == italicButton) {
			italicItem.setSelected(italicButton.isSelected());
		} else if (source == italicItem) {
			italicButton.setSelected(italicItem.isSelected());
		}
	}

	public void insertUpdate(DocumentEvent e) {
		isSaved = false;
	}

	public void removeUpdate(DocumentEvent e) {
		isSaved = false;
	}

	public void changedUpdate(DocumentEvent e) {
		isSaved = false;
	}

	public void keyTyped(KeyEvent e) {

	}

	public void keyPressed(KeyEvent e) {

	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ENTER && bulletBool == true) {
			textArea.setText(textArea.getText() + "  •  ");
		}
	}
}

public class NoodleDocs {

	public static void main(String[] args) {
		JFrame frame = new MainFrame();
		frame.setVisible(true);
	}

}