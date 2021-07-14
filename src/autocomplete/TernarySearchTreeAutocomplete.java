package autocomplete;

import java.util.Collection;
import java.util.List;

/**
 * Ternary search tree (TST) implementation of the {@link Autocomplete} interface.
 *
 * @see Autocomplete
 */
public class TernarySearchTreeAutocomplete implements Autocomplete {
    /**
     * The overall root of the tree: the first character of the first autocompletion term added to this tree.
     */
    private Node overallRoot;

    /**
     * Constructs an empty instance.
     */
    public TernarySearchTreeAutocomplete() {
        overallRoot = null;
    }

    @Override
    public void addAll(Collection<? extends CharSequence> terms) {
        // TODO: Replace with your code
        throw new UnsupportedOperationException("Not implemented yet");
        for (CharSequence key : terms) {
            overallRoot = put(overallRoot, key);    
        }
        
    }
    
    private Node get(Node x, CharSequence key) {
        if (x == null) return null;
        for (int i = 0; i < key.length(); i++) {
            char data = key.charAt(i);
            if      (data < x.data) return get(x.left,  key);
            else if (data > x.data) return get(x.right, key);
            else                    return get(x.mid,   key);
        }
    }
    
    private Node put(Node x, CharSequence key) {
        char data = key.charAt(0);       
        if (x == null) {
            x = new Node(key.charAt(0), true);
            x.data = data;
        }
        
        for (int i = 1; i < key.length(); i++) {
            data = key.charAt(i);
            if      (data < x.data)  x.left  = put(x.left,  key);
            else if (data > x.data)  x.right = put(x.right, key);
            else                     x.mid   = put(x.mid,   key);
            return x;
        }
    }
    
    private void collect(Node x, StringBuilder prefix, List<CharSequence> list) {
        if (x == null) return;
        collect(x.left, prefix, list);
        prefix.append(x.data);
        collect(x.mid, prefix, list);
        prefix.deleteCharAt(prefix.length() - 1);
        collect(x.right, prefix, list);
    }
    
    @Override
    public List<CharSequence> allMatches(CharSequence prefix) {
        // TODO: Replace with your code
        throw new UnsupportedOperationException("Not implemented yet");
        if (prefix == null) {
            throw new NullPointerException("calls keysWithPrefix() with null argument");
        } else if (prefix.length() == 0) {
            throw new IllegalArgumentException("prefix must have length >= 1");
        }
        
        // This line of code has an error
        List<CharSequence> newList = new ArrayList<>();
        Node x = get(overallRoot, prefix);
        if (x == null) return newList;
        collect(x.mid, new StringBuilder(prefix), newList);
        return newList;
    }

    /**
     * A search tree node representing a single character in an autocompletion term.
     */
    private static class Node {
        private char data;
        private boolean isTerm;
        private Node left;
        private Node mid;
        private Node right;

        public Node(char data, boolean isTerm) {
            this.data = data;
            this.isTerm = isTerm;
            this.left = null;
            this.mid = null;
            this.right = null;
        }
    }
}
