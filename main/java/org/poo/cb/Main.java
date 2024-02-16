package org.poo.cb;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.text.DecimalFormat;

public class Main {
    public static void main(String[] args) {
        CodeRunner codeRunner = CodeRunner.getInstance();
        codeRunner.runCode(args);
    }
}