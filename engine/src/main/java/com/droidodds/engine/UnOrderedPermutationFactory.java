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
class UnOrderedPermutationFactory {

    List<Set<Card>> getUnOrderedPermutationWithoutRepetition(final List<Card> availableCards, final int requiredLength) {
        List<Set<Card>> permutations = new ArrayList<>();
        fillUpPermutations(permutations, availableCards, new Stack<>(), requiredLength, 0);
        return permutations;
    }

    private void fillUpPermutations(final List<Set<Card>> permutations, final List<Card> availableCards, final Stack<Card> currentSubset, final int requiredLength, final int currentIndex) {
        for (int i = currentIndex; i < availableCards.size(); i++) {
            currentSubset.push(availableCards.get(currentIndex));
            if (currentSubset.size() == requiredLength) {
                permutations.add(new HashSet<>(currentSubset));
            } else {
                fillUpPermutations(permutations, availableCards, currentSubset, requiredLength, i + 1);
            }
            currentSubset.pop();
        }
    }

}
