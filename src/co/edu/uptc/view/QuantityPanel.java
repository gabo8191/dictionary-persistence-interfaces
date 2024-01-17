package co.edu.uptc.view;

import java.awt.GridLayout;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import java.awt.Color;

public class QuantityPanel extends JPanel {
	private JLabel totalEnglish;
	private JLabel totalFrench;
	private JTextArea quantityEnglish;
	private JTextArea quantityFrench;

	public QuantityPanel(ActionListener listener) {
		this.setBackground(Color.LIGHT_GRAY);
		initComponents(listener);
	}

	public void initComponents(ActionListener listener) {
		this.setLayout(new GridLayout(1, 4, 6, 6));

		totalEnglish = new JLabel("Total Ingles: ");
		totalEnglish.setHorizontalAlignment(JLabel.CENTER);
		add(totalEnglish);

		quantityEnglish = new JTextArea();

		quantityEnglish.setBackground(Color.LIGHT_GRAY);
		add(quantityEnglish);

		totalFrench = new JLabel("Total Frances: ");
		totalFrench.setHorizontalAlignment(JLabel.CENTER);
		add(totalFrench);

		quantityFrench = new JTextArea();
		quantityFrench.setBackground(Color.LIGHT_GRAY);
		add(quantityFrench);
	}

	public JLabel getTotalEnglish() {
		return totalEnglish;
	}

	public void setTotalEnglish(JLabel totalEnglish) {
		this.totalEnglish = totalEnglish;
	}

	public JLabel getTotalFrench() {
		return totalFrench;
	}

	public void setTotalFrench(JLabel totalFrench) {
		this.totalFrench = totalFrench;
	}

	public JTextArea getQuantityEnglish() {
		return quantityEnglish;
	}

	public void setQuantityEnglish(JTextArea quantityEnglish) {
		this.quantityEnglish = quantityEnglish;
	}

	public JTextArea getQuantityFrench() {
		return quantityFrench;
	}

	public void setQuantityFrench(JTextArea quantityFrench) {
		this.quantityFrench = quantityFrench;
	}

}
