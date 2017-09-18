package spell;

import java.io.IOException;

public interface ISpellCorrector {

	/**
	 * Tells this <code>SpellCorrector</code> to use the given file as its dictionary
	 * for generating suggestions.
	 * @param dictionaryFileName File containing the words to be used
	 * @throws IOException If the file cannot be read
	 */
	public void useDictionary(String dictionaryFileName) throws IOException;

	/**
	 * Suggest a word from the dictionary that most closely matches
	 * <code>inputWord</code>
	 * @param inputWord
	 * @return The suggestion or null if there is no similar word in the dictionary
	 */
	public String suggestSimilarWord(String inputWord);

}
/*
look for given word in dictionary
if it exists return it
else
	insert()
	delete()
	alteration()
	transpose()
	
	inputWord
	find(inputWord)
	if exists 
		return input word
	else
		//google treeset
		treeset editOne
		delete(inputword, editOne)
		insert(inputword, editOne)
		
		
		delete(inputword,editOne)
		{
			//ex if word is "bad"
			 return ad, bd, ba
			 so delete 1 char
			 add newWord to editOne set
			 
		}
		
		for string s : editOne
		{
			Node n = myTrie.find(s)
			if(n != null)
				maxfrequency = n.frequency;
				suggestedWord = s;
			if(n.freq > max.freq)
		}
		

*/