package autocomplete;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Sequential search implementation of the {@link Autocomplete} interface.
 *
 * @see Autocomplete
 */
public class SequentialSearchAutocomplete implements Autocomplete {
    /**
     * {@link List} of added autocompletion terms.
     */
    private final List<CharSequence> terms;

    /**
     * Constructs an empty instance.
     */
    public SequentialSearchAutocomplete() {
        this.terms = new ArrayList<>();
    }

    @Override
    public void addAll(Collection<? extends CharSequence> terms) {
        for (CharSequence element : terms){
            this.terms.add(element);
        }
    }

    @Override
    public List<CharSequence> allMatches(CharSequence prefix) {
        List<CharSequence> result = new ArrayList<>();
        for (CharSequence item : this.terms) {
            if (prefix.length() <= item.length()) {
                CharSequence part = item.subSequence(0, prefix.length());
                if (CharSequence.compare(prefix, part) == 0) {
                   result.add(item);
                }
            }
        }
        return result;
        }
}
