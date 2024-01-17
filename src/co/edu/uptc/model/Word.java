package co.edu.uptc.model;

import java.util.ArrayList;
import java.util.List;

public class Word {
    private String word;
    private List<String> translate;

    public Word(String word) {
        this.word = word.toLowerCase();
        translate = new ArrayList<>();
    }

    public void addTranslate(String translate) {
        this.translate.add(translate);
    }

    public List<String> getTranslate() {
        return translate;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public void setTranslate(List<String> translate) {
        this.translate = translate;
    }
}
