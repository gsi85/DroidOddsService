package com.droidodds.engine;

import com.droidodds.domain.card.Card;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Stack;
import org.springframework.stereotype.Component;

/**
 * @author Laszlo_Sisa
 */
@Component
public class UnOrderedPermutationFactory<T> {

    public List<Set<T>> getUnOrderedPermutationWithoutRepetition(final List<T> availableElements, final int requiredLength) {
        List<Set<T>> permutations = new ArrayList<>();
        fillUpPermutations(permutations, availableElements, new Stack<>(), requiredLength, 0);
        return permutations;
    }

    private void fillUpPermutations(final List<Set<T>> permutations, final List<T> availableElements, final Stack<T> currentSubset, final int requiredLength, final int currentIndex) {
        for (int i = currentIndex; i < availableElements.size(); i++) {
            currentSubset.push(availableElements.get(i));
            if (currentSubset.size() == requiredLength) {
                permutations.add(new HashSet<>(currentSubset));
            } else {
                fillUpPermutations(permutations, availableElements, currentSubset, requiredLength, i + 1);
            }
            currentSubset.pop();
        }
    }

}
