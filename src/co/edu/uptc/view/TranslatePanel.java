package co.edu.uptc.view;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;

public class TranslatePanel extends JPanel {
	private JLabel spanishWord;
	private JLabel translate;
	private JTextArea spanishWordInput;
	private JTextArea translateOutput;
	private JButton addEnglish;
	private JButton addFrench;

	public TranslatePanel(ActionListener listener) {
		initComponents(listener);
	}

	public void initComponents(ActionListener listener) {
		this.setLayout(new GridLayout(2, 4, 5, 5));
		spanishWord = new JLabel("Palabra: ");
		spanishWord.setHorizontalAlignment(JLabel.CENTER);
		add(spanishWord);

		spanishWordInput = new JTextArea();
		add(spanishWordInput);

		translate = new JLabel("Traducción:");
		translate.setHorizontalAlignment(JLabel.CENTER);
		add(translate);

		translateOutput = new JTextArea();
		add(translateOutput);

		add(new JLabel(""));

		addEnglish = new JButton("Agregar al inglés");
		addEnglish.addActionListener(listener);
		addEnglish.setActionCommand("addEnglish");
		add(addEnglish);

		addFrench = new JButton("Agregar al frances");
		addFrench.addActionListener(listener);
		addFrench.setActionCommand("addFrench");
		add(addFrench);

		add(new JLabel(""));

	}

	public JLabel getSpanishWord() {
		return spanishWord;
	}

	public void setSpanishWord(JLabel spanishWord) {
		this.spanishWord = spanishWord;
	}

	public JLabel getTranslate() {
		return translate;
	}

	public void setTranslate(JLabel translate) {
		this.translate = translate;
	}

	public JTextArea getSpanishWordInput() {
		return spanishWordInput;
	}

	public void setSpanishWordInput(JTextArea spanishWordInput) {
		this.spanishWordInput = spanishWordInput;
	}

	public JTextArea getTranslateOutput() {
		return translateOutput;
	}

	public void setTranslateOutput(JTextArea translateOutput) {
		this.translateOutput = translateOutput;
	}

	public JButton getAddEnglish() {
		return addEnglish;
	}

	public void setAddEnglish(JButton addEnglish) {
		this.addEnglish = addEnglish;
	}

	public JButton getAddFrench() {
		return addFrench;
	}

	public void setAddFrench(JButton addFrench) {
		this.addFrench = addFrench;
	}

}
