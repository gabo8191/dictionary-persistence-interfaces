package co.edu.uptc.view;

import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class DictionaryFrame extends JFrame {
	private WordPanel wordPanel;
	private QuantityPanel quantityPanel;
	private TranslatePanel translatePanel;

	public DictionaryFrame(ActionListener listener) {
		super("Mi traductor");
		this.setSize(600, 250);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		initComponents(listener);
		this.setVisible(true);
	}

	public void initComponents(ActionListener listener) {
		JPanel container = new JPanel();
		container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));

		wordPanel = new WordPanel(listener);
		container.add(wordPanel);

		quantityPanel = new QuantityPanel(listener);
		container.add(quantityPanel);

		translatePanel = new TranslatePanel(listener);
		container.add(translatePanel);

		this.setContentPane(container);
	}

	public String getWordInput() {
		return wordPanel.getWordInput().getText().toLowerCase();
	}

	public String getDocument() {
		return wordPanel.getComboDocument().getSelectedItem().toString();
	}

	public void setEnglishWord(String word) {
		wordPanel.getEnglishWord().setText(word);
	}

	public void setFrenchWord(String word) {
		wordPanel.getFrenchWord().setText(word);
	}

	public void setQuantityEnglish(int quantity) {
		quantityPanel.getQuantityEnglish().setText(String.valueOf(quantity));
	}

	public void setQuantityFrench(int quantity) {
		quantityPanel.getQuantityFrench().setText(String.valueOf(quantity));
	}

	public String getSpanishWordInput() {
		return translatePanel.getSpanishWordInput().getText().toLowerCase();
	}

	public String getTranslateInput() {
		return translatePanel.getTranslateOutput().getText().toLowerCase();
	}

	public void setSpanishWordInput(String word) {
		translatePanel.getSpanishWordInput().setText(word);
	}

	public void setTranslateInput(String word) {
		translatePanel.getTranslateOutput().setText(word);
	}

}
