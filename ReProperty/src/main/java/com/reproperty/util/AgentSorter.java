package com.reproperty.util;

import com.reproperty.model.Agent;

public class AgentSorter {

    // Selection sort descending by rating
    public static void selectionSortByRating(Agent[] agents) {
        int n = agents.length;

        for (int i = 0; i < n-1; i++) {
            int maxIdx = i;
            for (int j = i+1; j < n; j++) {
                if (agents[j].getRating() > agents[maxIdx].getRating()) {
                    maxIdx = j;
                }
            }
            // Swap
            Agent temp = agents[maxIdx];
            agents[maxIdx] = agents[i];
            agents[i] = temp;
        }
    }
}
