package co.edu.uptc.persistence;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.core.JsonParseException;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PersistenceJson implements IPersistence {

    private String fileName;
    private JsonParser jsonParser;
    private Map<String, ArrayList<String>> words;

    public PersistenceJson(String fileName) {
        this.fileName = fileName;
        this.words = new HashMap<>();
        readDocument();
    }

    public void readDocument() {
        try {
            this.jsonParser = new JsonFactory().createParser(new File(fileName));
            parseJSON(jsonParser);
            jsonParser.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void parseJSON(JsonParser jsonParser) throws JsonParseException, IOException {
        while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
            if (jsonParser.getCurrentName() != null) {
                if (jsonParser.getCurrentName().equals("words")) {
                    jsonParser.nextToken();
                    while (jsonParser.nextToken() != JsonToken.END_ARRAY) {
                        if (jsonParser.getCurrentToken() == JsonToken.START_OBJECT) {
                            String word = null;
                            String englishTranslation = null;
                            String frenchTranslation = null;

                            while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
                                if (jsonParser.getCurrentName() != null) {
                                    switch (jsonParser.getCurrentName()) {
                                        case "word":
                                            jsonParser.nextToken();
                                            word = jsonParser.getText();
                                            break;
                                        case "translates":
                                            jsonParser.nextToken();
                                            while (jsonParser.nextToken() != JsonToken.END_OBJECT) {
                                                String language = jsonParser.getCurrentName();
                                                jsonParser.nextToken();
                                                String translation = jsonParser.getText();
                                                if (language.equals("english")) {
                                                    englishTranslation = translation;
                                                } else if (language.equals("french")) {
                                                    frenchTranslation = translation;
                                                }
                                            }
                                            break;
                                    }
                                }
                            }
                            setWords(word, frenchTranslation, englishTranslation);
                        }
                    }
                }
            }
        }
    }

    public void setWords(String word, String frenchWord, String englishWord) {
        if (words.containsKey(word)) {
            words.get(word).add(englishWord);
            words.get(word).add(frenchWord);
        } else {
            ArrayList<String> translations = new ArrayList<>();
            translations.add(englishWord);
            translations.add(frenchWord);
            words.put(word, translations);
        }
    }

    public boolean validateSpanishWord(String word) {
        return words.containsKey(word);
    }

    public Map<String, ArrayList<String>> getWords() {
        return words;
    }

    @Override
    public void writeDocument(String word, String translate, boolean flagLanguage) {

        if (words.containsKey(word)) {
            ArrayList<String> translations = words.get(word);
            if (flagLanguage) {
                translations.set(0, translate);
            } else {
                translations.set(1, translate);
            }
        } else {
            ArrayList<String> translations = new ArrayList<>();
            if (flagLanguage) {
                translations.add(translate);
                translations.add("");
            } else {
                translations.add("");
                translations.add(translate);
            }

            words.put(word, translations);
        }
        try {
            JsonGenerator jsonGenerator = new JsonFactory().createGenerator(new FileOutputStream(fileName),
                    JsonEncoding.UTF8);
            jsonGenerator.setPrettyPrinter(new DefaultPrettyPrinter());
            jsonGenerator.writeStartObject();
            jsonGenerator.writeFieldName("words");
            jsonGenerator.writeStartArray();

            for (Map.Entry<String, ArrayList<String>> entry : words.entrySet()) {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeStringField("word", entry.getKey());
                jsonGenerator.writeFieldName("translates");
                jsonGenerator.writeStartObject();
                jsonGenerator.writeStringField("english", entry.getValue().get(0));
                jsonGenerator.writeStringField("french", entry.getValue().get(1));
                jsonGenerator.writeEndObject();
                jsonGenerator.writeEndObject();
            }

            jsonGenerator.writeEndArray();
            jsonGenerator.writeEndObject();
            jsonGenerator.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateDocument(String word, String englishTranslation, String frenchTranslation) {
        if (words.containsKey(word)) {
            if (englishTranslation != null) {
                words.get(word).set(0, englishTranslation);
            }
            if (frenchTranslation != null) {
                words.get(word).set(1, frenchTranslation);
            }
        } else {
            ArrayList<String> translations = new ArrayList<>();
            translations.add(englishTranslation);
            translations.add(frenchTranslation);
            words.put(word, translations);
        }

        JsonGenerator jsonGenerator;
        try {
            jsonGenerator = new JsonFactory().createGenerator(new FileOutputStream(fileName),
                    JsonEncoding.UTF8);
            jsonGenerator.setPrettyPrinter(new DefaultPrettyPrinter());
            jsonGenerator.writeStartObject();
            jsonGenerator.writeFieldName("words");
            jsonGenerator.writeStartArray();

            for (Map.Entry<String, ArrayList<String>> entry : words.entrySet()) {
                jsonGenerator.writeStartObject();
                jsonGenerator.writeStringField("word", entry.getKey());
                jsonGenerator.writeFieldName("translates");
                jsonGenerator.writeStartObject();
                jsonGenerator.writeStringField("english", entry.getValue().get(0));
                jsonGenerator.writeStringField("french", entry.getValue().get(1));
                jsonGenerator.writeEndObject();
                jsonGenerator.writeEndObject();
            }

            jsonGenerator.writeEndArray();
            jsonGenerator.writeEndObject();
            jsonGenerator.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
