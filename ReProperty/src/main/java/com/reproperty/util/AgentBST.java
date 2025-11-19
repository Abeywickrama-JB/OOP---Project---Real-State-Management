package com.reproperty.util;

import com.reproperty.model.Agent;

public class AgentBST {
    private class Node {
        Agent agent;
        Node left, right;

        Node(Agent agent) {
            this.agent = agent;
        }
    }

    private Node root;

    public void insert(Agent agent) {
        root = insertRec(root, agent);
    }

    private Node insertRec(Node root, Agent agent) {
        if (root == null) return new Node(agent);

        if (agent.getName().compareToIgnoreCase(root.agent.getName()) < 0)
            root.left = insertRec(root.left, agent);
        else
            root.right = insertRec(root.right, agent);

        return root;
    }

    public Agent search(String name) {
        return searchRec(root, name);
    }

    private Agent searchRec(Node root, String name) {
        if (root == null) return null;
        int cmp = name.compareToIgnoreCase(root.agent.getName());
        if (cmp == 0) return root.agent;
        if (cmp < 0) return searchRec(root.left, name);
        else return searchRec(root.right, name);
    }
}

