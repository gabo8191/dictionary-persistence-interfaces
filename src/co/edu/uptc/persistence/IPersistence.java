package co.edu.uptc.persistence;

import java.util.ArrayList;
import java.util.Map;

public interface IPersistence {

    public void readDocument();

    public void writeDocument(String word, String translate, boolean flagLanguage);

    public void updateDocument(String word, String englishTranslation, String frenchTranslation);

    public Map<String, ArrayList<String>> getWords();

    public boolean validateSpanishWord(String word);

}
