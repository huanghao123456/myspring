package com.myspring.springdemo;

import java.io.*;

public class FileReadAndWriteTest {

    public static void main(String[] args) {
        File file = new File("E:\\download\\pdb\\completed.txt");
        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file, true))) {
            bufferedWriter.append("cewvw");
            bufferedWriter.newLine();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        try (BufferedReader reader = new BufferedReader(new FileReader("E:\\download\\pdb\\completed.txt"))) {
            String tempString;
            while ((tempString = reader.readLine()) != null) {
                System.out.println("tempString = " + tempString);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
