package spell;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Set;
import java.util.Scanner;
import java.util.TreeSet;

/*
 * Created by DustinWatkins on 9/17/17.
 */

public class spellCorrector implements ISpellCorrector {
    Trie dictionary = new Trie();
//    Trie test = new Trie();

    public void useDictionary(String dictionaryFileName) throws IOException
    {
        String word;
        File fr = new File(dictionaryFileName);
        Scanner scan = new Scanner(fr);
        while(scan.hasNext())
        {
            String s = scan.next().toLowerCase();
            dictionary.add(s);
//            test.add(s);
        }
        scan.close();
//            dictionary.add("yea");
//            dictionary.add("cat");
//            dictionary.add("dog");
//            dictionary.add("easy");
//        test.add("asdf");
//        System.out.println("dictionary word count = " + dictionary.getWordCount() + " node count = " + dictionary.getNodeCount());
//        System.out.println("test word count = " + test.getWordCount() + " node count = " + test.getNodeCount());
//        dictionary.equals(test);
//        dictionary.toString();
    }//end useDictionary()

    public String suggestSimilarWord(String inputWord)
    {
//        Set<String> wordstoCheck = new TreeSet<String>();
        System.out.println("entered suggest word");
        Set<String> wordstoCheckD2 = new TreeSet<String>();
        //make sure not empty string
        if(inputWord.length() == 0)
        {
//            System.out.println("input word length is 0");
            return null;
        }
        //input word to lowercase cast to lowercase
        inputWord = inputWord.toLowerCase();

        //check if the word is in the dicitionary
        if(dictionary.find(inputWord) != null)
        {
//            System.out.println("word was found!");
            return inputWord;
        }
        else
        {
//            System.out.println("first check");
            wordstoCheck = runEdits(inputWord, wordstoCheck);
        }
        String similar = null;
        int similarFreq = 0;
        for(String s: wordstoCheck)
        {
            if(dictionary.find(s) != null)
            {
                if (dictionary.find(s).getValue() > similarFreq)
                {
//                    System.out.println("found word 1 edit distance");
                    similar = s;
                    similarFreq = dictionary.find(s).getValue();
                }
            }//else do nothing, go to next string in treeset
        }
        if(similar != null)
        {
//            System.out.println("returning word 1 edit distance");
            return similar;
        }
        else //round 2, edits of distance 2
        {
//            System.out.println("checking edit 2 distance");
            for(String s: wordstoCheck) {
                wordstoCheckD2 = runEdits(s, wordstoCheckD2);
            }
//            System.out.println(wordstoCheckD2.size());
            for(String s: wordstoCheckD2)
            {
                if(dictionary.find(s) != null)
                {
//                    System.out.println("found word in d2!");
                    if(dictionary.find(s).getValue() > similarFreq)
                    {
                        similar = s;
                        similarFreq = dictionary.find(s).getValue();
                    }
                }
            }
            if(similar != null)
            {
                return similar;
            }
            else
            {
                //return null here?
                return null;
            }
        }
    }//end suggestSimilar()

    public void deletion(String inputWord, Set<String> wordstoCheck)
    {
        //deletion cat = at ct ca
//        System.out.println("entered delete function");
        for(int i = 0; i < inputWord.length(); i++)
        {
            StringBuilder sb = new StringBuilder(inputWord);
            String s;
            sb.deleteCharAt(i);
            s = sb.toString();
            wordstoCheck.add(s);
        }
    }//end delete()

    public void insertion(String inputWord, Set<String> wordstoCheck)
    {
//        System.out.println("entered insert function");
        //insertion cat = acat bcat caat cbat caat cabt..
        for(int i = 0; i <= inputWord.length(); i++)
        {
            for(char c = 'a'; c <= 'z'; c++)
            {
                StringBuilder sb = new StringBuilder(inputWord);
                String s;
                sb.insert(i,c);
                s = sb.toString();
                wordstoCheck.add(s);
            }//end inner for loop
        }//end out for loop
    }//end insertion()

    public void alteration(String inputWord, Set<String> wordstoCheck)
    {
//        System.out.println("entered alteration function");
        //alteration cat = aat bat cat dat... cat cbt cct... caa cab cac...
        for(int i = 0; i < inputWord.length(); i++)
        {
            for(char c = 'a'; c <= 'z'; c++)
            {
                wordstoCheck.add(inputWord.substring(0,i) + c + inputWord.substring(i+1));
            }//end inner for loop

        }//end outer for loop
    }

    public void transposition(String inputWord, Set<String> wordstoCheck)
    {
//        System.out.println("entered transpose function");
        //transpose cat = act cta
        char[] wordArray = inputWord.toCharArray();
        for(int i = 0; i < inputWord.length() - 1; i++)
        {
//            System.out.println("transpose i = " + i);
                char first = wordArray[i];
                char second = wordArray[i+1];
                wordArray[i] = second;
                wordArray[i+1] = first;
                String newWord = new String(wordArray);
//                System.out.println(newWord);
                wordstoCheck.add(newWord);
                //reset inputWord to original
                wordArray = inputWord.toCharArray();
        }//end for loop
//        System.out.println("exit transpose for loop");
    }//end transposition()

    public Set<String> runEdits(String s, Set<String> words)
    {
//        System.out.println("entered run edits function");
        deletion(s, words);
        insertion(s, words);
        alteration(s, words);
        transposition(s, words);
        return words;
    }

//    public Set<String> distance2Edit(Set<String> words)
//    {
//        Set<String> newSet = new TreeSet<String>();
//        for(String s: words)
//        {
//            runEdits(s,newSet);
//        }
//        return newSet;
//    }

}//end class
