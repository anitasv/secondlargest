package com.amazon.interview.common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Dag<T> {

    private final Map<T, List<T>> partialOrder =
            new HashMap<>();

    public void addPartialOrder(T a, T b) {
        partialOrder.computeIfAbsent(a, (ignore) -> new ArrayList<T>()).add(b);
    }

    private boolean findPathInternal(T a, T b, Set<T> visited) {
        if (visited.contains(a)) {
            return false;
        }
        visited.add(a);
        List<T> next = partialOrder.get(a);
        if (next == null) {
            return false;
        }
        for (T succ : next) {
            if (succ == b) {
                return true;
            }
        }

        for (T succ : next) {
            if (findPathInternal(succ, b, visited)) {
                return true;
            }
        }
        return false;
    }

    public boolean findPath(T a, T b) {
        return findPathInternal(a, b, new HashSet<>());
    }

    private void dominatingCountInternal(T a, Set<T> visited) {
        if (visited.contains(a)) {
            return;
        }
        visited.add(a);
        List<T> next = partialOrder.get(a);
        if (next == null) {
            return;
        }
        for (T succ : next) {
            dominatingCountInternal(succ, visited);
        }
    }

    public int dominatingCount(T a) {
        Set<T> dominatingSet = new HashSet<>();
        dominatingCountInternal(a, dominatingSet);
        return dominatingSet.size() - 1;
    }

    @Override
    public String toString() {
        StringBuilder graph = new StringBuilder();
        graph.append("digraph G {");
        for (Map.Entry<T, List<T>> entry : partialOrder.entrySet()) {
            for (T b : entry.getValue()) {
                graph.append(entry.getKey()).append(" -> ").append(b).append("\n");
            }
        }
        graph.append("}");
        return graph.toString();
    }
}
