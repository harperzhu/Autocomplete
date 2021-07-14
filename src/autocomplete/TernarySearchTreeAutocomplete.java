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
            overallRoot = put(overallRoot, key, 0);
        }
    }
    
    private Node get(Node x, CharSequence key, int d) {
        if (x == null) return null;
        char data = key.charAt(d);
        if      (data < x.data)        return get(x.left,  key, d);
        else if (data > x.data)        return get(x.right, key, d);
        else if (d < key.length() - 1) return get(x.mid,   key, d + 1);
        else                           return x;
    }
    
    private Node put(Node x, CharSequence key, int d) {
        char data = key.charAt(d);       
        if (x == null) {
            x = new Node(key.charAt(d), true);
            x.data = data;
        }

        data = key.charAt(d);
        if      (data < x.data)        x.left  = put(x.left,  key, d);
        else if (data > x.data)        x.right = put(x.right, key, d);
        else                           x.mid   = put(x.mid,   key, d + 1);
        return x;
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
        Node x = get(overallRoot, prefix, 0);
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
