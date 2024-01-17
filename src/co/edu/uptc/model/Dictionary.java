package co.edu.uptc.model;

import java.util.ArrayList;
import java.util.List;

public class Dictionary {
    private List<Word> wordList;

    public Dictionary() {
        wordList = new ArrayList<>();

    }

    public void loadData(String word, String frenchWord, String englishWord) {
        Word wordAux = searchWordIgnoreCase(word);
        if (wordAux == null) {
            wordAux = new Word(word.toLowerCase());
            wordAux.addTranslate(englishWord.toLowerCase());
            if (frenchWord != null) {
                wordAux.addTranslate(frenchWord.toLowerCase());
            } else {
                wordAux.addTranslate(null);
            }
            wordList.add(wordAux);
        } else {
            if (wordAux.getTranslate().size() > 0) {
                wordAux.getTranslate().set(0, englishWord.toLowerCase());
            } else {
                wordAux.addTranslate(englishWord.toLowerCase());
            }
            if (wordAux.getTranslate().size() > 1) {
                wordAux.getTranslate().set(1, frenchWord.toLowerCase());
            } else {
                wordAux.addTranslate(frenchWord.toLowerCase());
            }
        }
    }

    public Word searchWordIgnoreCase(String word) {
        for (Word wordAux1 : wordList) {
            if (wordAux1.getWord().equalsIgnoreCase(word)) {
                return wordAux1;
            }
        }
        return null;
    }

    public Word searchWord(String word) {
        Word wordAux = null;
        for (Word wordAux1 : wordList) {
            if (wordAux1.getWord().equalsIgnoreCase(word)) {
                wordAux = wordAux1;
            }
        }
        return wordAux;
    }

    public void addEnglishWord(String spanishWord, String englishWord) {
        Word wordAux = searchWord(spanishWord.toLowerCase());
        if (wordAux == null) {
            wordAux = new Word(spanishWord.toLowerCase());
            wordAux.addTranslate(englishWord.toLowerCase());
            wordAux.addTranslate(null);
            wordList.add(wordAux);
        } else {
            if (wordAux.getTranslate().get(0) == null || wordAux.getTranslate().get(0) == "") {
                wordAux.getTranslate().set(0, englishWord.toLowerCase());
            } else {
                wordAux.addTranslate(englishWord.toLowerCase());
            }
        }
    }

    public void addFrenchWord(String spanishWord, String frenchWord) {
        Word wordAux = searchWord(spanishWord.toLowerCase());
        if (wordAux == null) {
            wordAux = new Word(spanishWord.toLowerCase());
            if (wordAux.getTranslate().get(0) == null || wordAux.getTranslate().get(0) == "") {
                wordAux.addTranslate(null);
            }
            wordAux.addTranslate(frenchWord.toLowerCase());
            wordList.add(wordAux);
        } else {
            if (wordAux.getTranslate().get(1) == null || wordAux.getTranslate().get(1) == "") {
                wordAux.getTranslate().set(1, frenchWord.toLowerCase());
            } else {
                wordAux.addTranslate(frenchWord.toLowerCase());
            }
        }

    }

    public String translateToEnglish(String word) {
        Word wordAux = searchWord(word.toLowerCase());
        if (wordAux != null) {
            return wordAux.getTranslate().get(0);
        }
        return null;
    }

    public String translateToFrench(String word) {
        Word wordAux = searchWord(word.toLowerCase());
        if (wordAux != null) {
            return wordAux.getTranslate().get(1);
        }
        return null;
    }

    public int countEnglishWords() {
        int count = 0;
        for (Word word : wordList) {
            if (word.getTranslate().size() > 0) {
                if (word.getTranslate().get(0) != null && word.getTranslate().get(0) != "") {
                    count++;
                }
            }
        }
        return count;
    }

    public int countFrenchWords() {
        int count = 0;
        for (Word word : wordList) {
            if (word.getTranslate().size() > 1) {
                if (word.getTranslate().get(1) != null && word.getTranslate().get(1) != "") {
                    count++;
                }
            }
        }
        return count;
    }

}
