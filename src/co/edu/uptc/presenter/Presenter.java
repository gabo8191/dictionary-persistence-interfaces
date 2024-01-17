package co.edu.uptc.presenter;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import com.fasterxml.jackson.core.JsonParseException;
import co.edu.uptc.model.Dictionary;
import co.edu.uptc.persistence.IPersistence;
import co.edu.uptc.persistence.PersistenceJson;
import co.edu.uptc.persistence.PersistenceXML;
import co.edu.uptc.view.DictionaryFrame;

public class Presenter implements ActionListener {
	private DictionaryFrame view;
	private Dictionary dictionary;
	private IPersistence persistence;
	private boolean flagDocument;
	private static final String FILE_NAME = "data/dictionary.";

	public Presenter() throws JsonParseException, IOException {
		view = new DictionaryFrame(this);
		dictionary = new Dictionary();
	}

	public void selectDocument() throws JsonParseException, IOException {
		if (flagDocument) {
			persistence = new PersistenceJson(FILE_NAME + "json");
		} else {
			persistence = new PersistenceXML(FILE_NAME + "xml");
		}
		processDocument();

	}

	public void processDocument() throws JsonParseException, IOException {

		Map<String, ArrayList<String>> words = persistence.getWords();
		for (Map.Entry<String, ArrayList<String>> entry : words.entrySet()) {
			String word = entry.getKey();
			ArrayList<String> translates = entry.getValue();
			String englishWord = translates.get(0);
			String frenchWord = translates.get(1);
			dictionary.loadData(word, frenchWord, englishWord);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command = e.getActionCommand();
		switch (command) {
			case "translateToEnglish":
				handleTranslateToEnglish();
				break;

			case "TranslateToFrench":
				handleTranslateToFrench();
				break;

			case "addEnglish":
				try {
					handleAddEnglish();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				break;

			case "addFrench":
				try {
					handleAddFrench();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				break;

			case "selectDocument":
				if (view.getDocument().equals("JSON")) {
					flagDocument = true;
				} else {
					flagDocument = false;
				}
				try {
					selectDocument();
				} catch (IOException e1) {
					e1.printStackTrace();
				}

			default:
				break;
		}
	}

	public void handleTranslateToEnglish() {
		String word = view.getWordInput();
		String translate = dictionary.translateToEnglish(word);
		view.setEnglishWord(translate);
		if (translate == null || translate.equals("")) {
			view.setEnglishWord("No se encontró la palabra");
		}
		int quantityEnglish = dictionary.countEnglishWords();
		view.setQuantityEnglish(quantityEnglish);
	}

	public void handleTranslateToFrench() {
		String word = view.getWordInput();
		String translate = dictionary.translateToFrench(word);
		view.setFrenchWord(translate);
		if (translate == null || translate.equals("")) {
			view.setFrenchWord("No se encontró la palabra");
		}
		int quantity = dictionary.countFrenchWords();
		view.setQuantityFrench(quantity);
	}

	public void handleAddEnglish() throws FileNotFoundException, IOException {
		String spanishWord = view.getSpanishWordInput();
		String englishWord = view.getTranslateInput();

		if (spanishWord.equals("")) {
			view.setSpanishWordInput("No se puede agregar una palabra vacía");
			return;
		}
		if (englishWord.equals("")) {
			return;
		}

		if (persistence.validateSpanishWord(spanishWord)) {
			persistence.updateDocument(spanishWord, englishWord, null);
			dictionary.addEnglishWord(spanishWord, englishWord);
		} else {
			persistence.writeDocument(spanishWord, englishWord, true);
			dictionary.addEnglishWord(spanishWord, englishWord);
		}

		view.setSpanishWordInput("");
		view.setTranslateInput("");
	}

	public void handleAddFrench() throws FileNotFoundException, IOException {
		String spanishWord = view.getSpanishWordInput();
		String frenchWord = view.getTranslateInput();

		if (spanishWord.equals("")) {
			view.setSpanishWordInput("No se puede agregar una palabra vacía");
			return;
		}
		if (frenchWord.equals("")) {
			return;
		}

		if (persistence.validateSpanishWord(spanishWord)) {
			persistence.updateDocument(spanishWord, null, frenchWord);
			dictionary.addFrenchWord(spanishWord, frenchWord);
		} else {
			persistence.writeDocument(spanishWord, frenchWord, false);
			dictionary.addFrenchWord(spanishWord, frenchWord);
		}

		view.setSpanishWordInput("");
		view.setTranslateInput("");
	}

	public static void main(String[] args) throws JsonParseException, IOException {
		new Presenter();
	}

}
