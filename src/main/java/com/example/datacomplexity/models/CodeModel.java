package com.example.datacomplexity.models;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class CodeModel {
    private final HashMap<String, Integer> spenTable;
    private final VariableList variableList;
    private String code;


    public CodeModel(String code) {
        this.variableList = new VariableList();
        this.spenTable = new HashMap<>();
        this.code = code;
    }

    public CodeModel() {
        this.variableList = new VariableList();
        this.spenTable = new HashMap<>();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    //Художественный фильм "Позаимствовали"
    public HashMap<String, Integer> codeSpenAnalyzing() {
        String codeTemp = code.replaceAll("(=begin\\s(.*\\r?\\n)*?=end\\s)|(#.*)", "");
        codeTemp = codeTemp.replaceAll("(\".*?[^\\\\](\\\\\\\\)*\")|('.*?[^\\\\](\\\\\\\\)*')", "");

        Pattern pattern = Pattern.compile("\\b[_a-zA-Z]\\w*\\b(?=\\s*=)");
        Matcher matcher = pattern.matcher(codeTemp);

        while (matcher.find()) {
            spenTable.put(matcher.group(), 0);
        }

        for (String key : spenTable.keySet()) {
            if (!key.contains("\"") && !key.contains("'")) {
                int count = 0;
                pattern = Pattern.compile("\\b" + key + "\\b");
                matcher = pattern.matcher(codeTemp);
                while (matcher.find()) {
                    count++;
                }
                spenTable.put(key, count - 1);
            }
        }
        return spenTable;
    }

    public int getSumSpen() {
        int sumSpen = 0;
        for (int spen : spenTable.values()) {
            sumSpen += spen;
        }
        return sumSpen;
    }

    public void codeChepinsAnalyzing() {
        if (!this.spenTable.isEmpty()) {
            for (String varName : this.spenTable.keySet()) {
                this.variableList.addVariable(varName);
            }
        } //Создаём массив переменных из таблицы со спенами

        String codeTemp = code.replaceAll("(=begin\\s(.*\\r?\\n)*?=end\\s)|(#.*)", "");
        codeTemp = codeTemp.replaceAll("(\".*?[^\\\\](\\\\\\\\)*\")|('.*?[^\\\\](\\\\\\\\)*')", "");
        String[] codeLines = codeTemp.split("\n");//Делим код по линиям, в которых будем искать признаки для Чепина
        Pattern pattern;
        Matcher matcher;
        for (String line : codeLines) { //Прокручиваем код построчно
            for (Variable variable : variableList.getVariables()) {
                pattern = Pattern.compile("\\b" + variable.getName() + "\\b");
                matcher = pattern.matcher(line);
                if (matcher.find()) { //Смотрим, есть ли в строке какая-либо из наших переменных
                    pattern = Pattern.compile("gets\\.chomp");//gets.chomp.to_smth оператор ввода
                    matcher = pattern.matcher(line);
                    if (matcher.find() &&
                            (variable.getVarType() != VarType.C ||
                                    variable.getVarType() != VarType.M)) {
                        variable.setVarType(VarType.P); //Переменная становится вводимой
                        variable.setIO(true); //Состояние IO необходимо как флажок метрик ввода/вывода
                    } else if (variable.getVarType() != VarType.C) {  //Иначе проверка на её модифицируемость
                        pattern = Pattern.compile(variable.getName() + "(\\s[%+*\\/-]*=(?!=)|\\+\\+|--)");
                        matcher = pattern.matcher(line);
                        if (matcher.find()) {
                            if (spenTable.get(variable.getName()) != 0) {
                                variable.setVarType(VarType.M);
                            } //Иначе сохранится Т
                        }
                    }
                    pattern = Pattern.compile("\\bwhile\\b|\\buntil\\b|\\bif\\b|\\bfor\\b|\\bcase\\b");
                    matcher = pattern.matcher(line); //Если в строке с проверяемой переменной есть оператор ветвления,
                    if (matcher.find()) { //то скорее-всего переменная является управляющей
                        variable.setVarType(VarType.C);
                    }
                    pattern = Pattern.compile("puts");//Оператор вывода
                    matcher = pattern.matcher(line);
                    if (matcher.find()) {
                        variable.setIO(true); //Состояние IO необходимо для метрик ввода/вывода
                    }
                }
            }
        }
    } //Ну и дерьмовый метод, если честно, пиздец. Но вроде рабочий(тест пока только через дебаг в контроллере)
}
