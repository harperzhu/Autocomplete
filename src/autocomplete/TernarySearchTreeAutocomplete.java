package autocomplete;

import java.util.ArrayList;
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
        for(CharSequence key: terms) {
            overallRoot = get(overallRoot, key, 0);
    	  }
    }
    
    private Node get(Node root, CharSequence key, int index) {
    	  if (root == null) {
    		  root = new Node(key.charAt(index), false);
     	  }
    	
    	  if (key.charAt(index) < root.data) {
            root.left = get(root.left, key, index);
        } else if (key.charAt(index) > root.data) {
            root.right = get(root.right, key, index);
        } else {
            if (index + 1 < key.length()) {
                root.mid = get(root.mid, key, index + 1);
            } else {
                root.isTerm = true;
            }
        }
        return root;	
    }

    @Override
    public List<CharSequence> allMatches(CharSequence prefix) {
        
        List<CharSequence> list = new ArrayList<>();
    	  Node target = put(overallRoot, (String) prefix, 0);
    	
    	  if (target != null && target.isTerm) {
    	      list.add(prefix);
    	  }
        
        if (target != null) {
    	      subMatch(target.mid, list, (String) prefix);
        }
    	
    	  return list;
    }
    
    private void subMatch(Node root, List<CharSequence> list, String words) {
    	  if (root == null) {
    		  return;
    	  }
    	
    	  if (root.isTerm) {
    		  list.add (words + root.data);
    	  }
        
    	  if (root.mid != null) {
    		  subMatch (root.mid, list, words + root.data);
    	  }
        
    	  if (root.left != null) {
    		  subMatch (root.left, list, words);
    	  }
        
    	  if (root.right != null) {
    		  subMatch (root.right, list, words);
    	  }
    }

    private Node put(Node root, String word, int index) {
    	  if(root == null) {
    	      return root;
    	  }
        
    	  char c = word.charAt(index);
    	  if (c == root.data) {                       
            if (word.length() == index + 1) {                          
                return root;
            } else {
                if (root.mid == null) {                                          
                    return root.mid;
                } else {                                   
                    return put(root.mid, word, index + 1);  
                } 
            }
        } else if (c < root.data) {
            if (root.left == null) {
                return root.left;
            } else {        
                return put(root.left, word, index);
            }
        } else { 
            if (root.right == null) {
                return root.right;
            } else {                                            
                return put(root.right, word, index);
            }
        }
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
