package com.example.datacomplexity.models;

enum VarType {
    P, //Вводимые, но не модифицируемые и не управляющие
    M, //Модифицируемые, но не управляющие
    C, //Модифицируемые и управляющие
    T  //Мусор
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
