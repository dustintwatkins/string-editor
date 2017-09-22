package spell;
import java.util.TreeSet;
import java.util.SortedSet;

public class Trie implements ITrie {
    private Node rootNode;
    private  int nodeCount;
    private int wordCount;
    int maxFreq;
    SortedSet<String> words;

    public Trie()
    {
        rootNode = new Node();
        nodeCount = 1;
        wordCount = 0;
//        words = new TreeSet<String>();
    }
    public void add(String word)
    {
//        words.add(word);
        rootNode.add(word);
    }

    public INode find(String word)
    {
        System.out.println("entered find method");
        Node node = rootNode;
//        System.out.println("word.length = " + word.length());
        for(int i = 0; i < word.length(); i++)
        {
            if(node.children[word.charAt(i) - 'a'] == null)
            {
                System.out.println("returned null");
                return null;
            }
            else
            {
                if(i == word.length() - 1) // last char in word
                {

                    if(node.children[word.charAt(i) -'a'].freq > 0)
                    {
                        System.out.println("last char in word, i = " + i);
                        return node.children[word.charAt(i)-'a'];
                    }
                    else
                    {
                        System.out.println("2nd return null");
                        return null;
                    }
                }
                node = node.children[word.charAt(i) - 'a'];
            }
        }
        return null;
    }

    public int getWordCount()
    {
        return wordCount;
    }

    public int getNodeCount()
    {
        return nodeCount;
    }

    @Override
    public String toString()
    {
        StringBuilder sb = new StringBuilder();
        StringBuilder sb2 = new StringBuilder();
        stringBuilderHelper(rootNode, sb2, sb);
//        System.out.println("exited recursion");
        return sb.toString();
    }

    public void stringBuilderHelper(Node node, StringBuilder sb2, StringBuilder sb)
    {
       if(node.freq != 0)
       {
           sb.append(sb2.toString() + "\n");
       }
       char c = 'a';
        //iterate through children array;
        for(int i = 0; i < 26; i++)
        {
            if (node.children[i] == null) {
                //do nothing
            } else {
//                System.out.println("before");
                sb2.append((char)(c + i));
//                System.out.println("middle");
                stringBuilderHelper(node.children[i], sb2, sb);
                sb2.deleteCharAt(sb2.length() - 1);
//                System.out.println("after");
//                System.out.println("end");
            }
        }
    }//end stringBuilderHelper()

    @Override
    public int hashCode()
    {
        return getWordCount() * getNodeCount() * 31;
    }

    @Override
    public boolean equals(Object o)
    {
        if(o == null)
        {
//            System.out.println("o == null");
            return false;
        }
        if(o == this)
        {
//            System.out.println("o == this");
            return true;
        }
        if(!(o instanceof Trie))
        {
//            System.out.println("0 instance of");
            return false;
        }
        Trie that = (Trie)o;
        if(this == that)//compares memory location
        {
//            System.out.println("this == that");
            return true;
        }
        //compare wordCount and nodeCount
//        System.out.println("this word = " +this.getWordCount() + " this node = " + this.getNodeCount());
        /*System.out.println("that word = " + that.getWordCount() + " that node = " + that.getNodeCount());*/
        if(this.getWordCount() == that.getWordCount() && this.getNodeCount() == that.getNodeCount())
        {
//            System.out.println("wordCount and nodecount are equal...");
            return this.rootNode.equals(that.rootNode);
        }
//        System.out.println("last statement");
        return false;
    }

    public class Node implements ITrie.INode
    {
        int freq;
//        String s;
        Node[] children = new Node[26];

        Node()
        {
            children = new Node[26];
            nodeCount++;
        }

        public int getValue(){return freq;}

        public void add(String word)
        {
            if(word.equals(""))
            {
                if(freq == 0)
                {
                    wordCount++;
                }
                freq++;
                return;
            }

            char c = word.charAt(0);

            if(children[c - 'a'] == null)
            {
                children[c-'a'] = new Node();
            }
            children[c-'a'].add(word.substring(1));
        }//end add()

        public boolean equals(Node n)
        {
            if(freq != n.freq)
            {
//                System.out.println("freq != n.freq");
                return false;
            }
            for(int i = 0; i < 26; i++)
            {
                if(children[i] == null && n.children[i] != null)
                {
//                    System.out.println("== null && != null");
                    return false;
                }
                if(children[i] != null && n.children[i] == null)
                {
//                    System.out.println("!= null && == null");
                    return false;
                }
                if(children[i] !=  null && n.children[i] != null)
                {
                    if(!(children[i].equals(n.children[i])))
                    {
//                        System.out.println("!= null && != null");
                        return false;
                    }
                }
            }
//            System.out.println("finally return true");
            return true;
        }//end equals()
     }
}
