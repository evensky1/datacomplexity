package com.example.datacomplexity.models;

import java.util.ArrayList;
import java.util.LinkedList;

public class VariableList {
    private LinkedList<Variable> variables;

    VariableList() {
        variables = new LinkedList<>();
    }

    public void addVariable(String name) {
        Variable variable = new Variable(name, false, VarType.T);
        this.variables.add(variable);
    }

    public LinkedList<Variable> getVariables() {
        return variables;
    }

    public Variable findVariableByName(String name) throws NullPointerException {
        for (Variable variable : this.variables) {
            if (variable.getName().equals(name)) {
                return variable;
            }
        }
        return null;
    }

    public boolean contains(String name) {
        for (Variable variable : this.variables) {
            if (variable.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<String> nameSet() {
        ArrayList<String> nameSet = new ArrayList<>();

        for (Variable variable : this.variables) {
            nameSet.add(variable.getName());
        }

        return nameSet;
    }
}
