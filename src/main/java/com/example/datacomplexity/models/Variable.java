package com.example.datacomplexity.models;

enum VarType {
    P(1), M(2), C(3), T((float)0.5);
    private float numVal;

    VarType(float numVal) {
        this.numVal = numVal;
    }

    public float getNumVal() {
        return numVal;
    }
}

public class Variable {
    private String name;
    private boolean isIO;
    private VarType varType;

    public Variable(String name, boolean isIO, VarType varType) {
        this.name = name;
        this.isIO = isIO;
        this.varType = varType;
    }

    public Variable() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isIO() {
        return isIO;
    }

    public void setIO(boolean IO) {
        isIO = IO;
    }

    public VarType getVarType() {
        return varType;
    }

    public void setVarType(VarType varType) {
        this.varType = varType;
    }
}
