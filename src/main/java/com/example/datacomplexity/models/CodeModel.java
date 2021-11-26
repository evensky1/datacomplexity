package com.example.datacomplexity.models;

import org.springframework.stereotype.Component;

@Component
public class CodeModel {
    private String code;

    public CodeModel(String code) {
        this.code = code;
    }

    public CodeModel() {
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
