package co.edu.uptc.view;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class WordPanel extends JPanel {
	private JLabel wordLabel, documentLabel;
	private JTextArea wordInput;
	private JButton toEnglish;
	private JTextArea englishWord;
	private JButton toFrench;
	private JTextArea frenchWord;
	// private JRadioButton radioJson;
	// private JRadioButton radioXml;
	private JComboBox<String> comboDocument;
	private JButton selectDocument;

	public WordPanel(ActionListener listener) {
		initComponents(listener);
	}

	public void initComponents(ActionListener listener) {
		this.setLayout(new GridLayout(4, 2, 5, 5));

		documentLabel = new JLabel("Tipo de documento: ");
		add(documentLabel);

		comboDocument = new JComboBox<String>();
		comboDocument.addItem("JSON");
		comboDocument.addItem("XML");
		comboDocument.addActionListener(listener);
		comboDocument.setActionCommand("selectDocument");
		add(comboDocument);

		selectDocument = new JButton("Seleccionar documento");
		selectDocument.addActionListener(listener);
		selectDocument.setActionCommand("selectDocument");
		add(selectDocument);

		add(new JLabel());
		wordLabel = new JLabel("Palabra: ");
		add(wordLabel);

		wordInput = new JTextArea();
		add(wordInput);

		add(new JLabel());
		add(new JLabel());

		toEnglish = new JButton("Traducir al inglés");
		toEnglish.addActionListener(listener);
		toEnglish.setActionCommand("translateToEnglish");
		add(toEnglish);

		englishWord = new JTextArea();
		add(englishWord);
		add(new JLabel());
		add(new JLabel());

		toFrench = new JButton("Traducir al francés");
		toFrench.addActionListener(listener);
		toFrench.setActionCommand("TranslateToFrench");
		add(toFrench);

		frenchWord = new JTextArea();
		add(frenchWord);
	}

	public JComboBox<String> getComboDocument() {
		return comboDocument;
	}

	public void setComboDocument(JComboBox<String> comboDocument) {
		this.comboDocument = comboDocument;
	}

	public JLabel getWordLabel() {
		return wordLabel;
	}

	public void setWordLabel(JLabel wordLabel) {
		this.wordLabel = wordLabel;
	}

	public JTextArea getWordInput() {
		return wordInput;
	}

	public void setWordInput(JTextArea wordInput) {
		this.wordInput = wordInput;
	}

	public JButton getToEnglish() {
		return toEnglish;
	}

	public void setToEnglish(JButton toEnglish) {
		this.toEnglish = toEnglish;
	}

	public JTextArea getEnglishWord() {
		return englishWord;
	}

	public void setEnglishWord(JTextArea englishWord) {
		this.englishWord = englishWord;
	}

	public JButton getToFrench() {
		return toFrench;
	}

	public void setToFrench(JButton toFrench) {
		this.toFrench = toFrench;
	}

	public JTextArea getFrenchWord() {
		return frenchWord;
	}

	public void setFrenchWord(JTextArea frenchWord) {
		this.frenchWord = frenchWord;
	}

}
