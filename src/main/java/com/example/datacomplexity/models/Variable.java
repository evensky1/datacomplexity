package com.example.datacomplexity.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Variable {
    private String name;
    private boolean isIO;
    private VarType varType;
}
