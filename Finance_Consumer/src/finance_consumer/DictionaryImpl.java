package finance_consumer;

import java.util.Set;

public class DictionaryImpl implements Dictionary {

	private static final Set<String> WORDS = Set.of("osgi", "eclipse", "equinox");
	private static final String LANGUAGE = "en_US";

	public String getLanguage() {
		return LANGUAGE;
	}

	public boolean check(String word) {
		return WORDS.contains(word);
	}

	public String toString() {
		return LANGUAGE;
	}

}
