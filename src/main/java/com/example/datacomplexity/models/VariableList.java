package com.example.datacomplexity.models;

import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


public class VariableList {
    private LinkedList<Variable> variables;

    VariableList() {
        variables = new LinkedList<>();
    }

    public void addVariable(String name) {
        this.variables.add(new Variable(name, false, VarType.T));
    }

    public List<Variable> getVariables() {
        return variables.stream()
                .sorted(Comparator.comparing(Variable::getVarType))
                .collect(Collectors.toList());
    }

    public List<Variable> getIOVariables() {
        return variables.stream()
                .filter(Variable::isIO)
                .sorted(Comparator.comparing(Variable::getVarType))
                .collect(Collectors.toList());
    }

    public Optional<Variable> findVariableByName(String name) throws NullPointerException {
        return variables.stream().filter(n -> n.getName().equals(name)).findFirst();
    }

    public boolean contains(String name) {
        return variables.stream().anyMatch(n -> n.getName().equals(name));
    }
}
