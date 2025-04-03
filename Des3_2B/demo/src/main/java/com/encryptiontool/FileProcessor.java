package com.encryptiontool;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class FileProcessor {
    public void process(File file, boolean isEncrypt) {
        try {
            String key = ConfigLoader.getKey();
            String fieldsConfig = ConfigLoader.getEncryptFields();
            TripleDESEncryption des3 = new TripleDESEncryption(key);

            List<Integer> encryptFields = parseFieldConfig(fieldsConfig);
            File outputFile = new File(file.getParent(), generateOutputFileName(file, isEncrypt));

            try (BufferedReader reader = new BufferedReader(new FileReader(file));
                 BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile))) {

                String line;
                while ((line = reader.readLine()) != null) {
                    String[] fields = line.split(";");
                    for (int fieldIndex : encryptFields) {
                        if (fieldIndex - 1 < fields.length) {
                            fields[fieldIndex - 1] = isEncrypt ? des3.encrypt(fields[fieldIndex - 1])
                                    : des3.decrypt(fields[fieldIndex - 1]);
                        }
                    }
                    writer.write(String.join(";", fields));
                    writer.newLine();
                }
            }

            System.out.println("Файл успешно обработан: " + outputFile.getAbsolutePath());

        } catch (Exception e) {
            System.err.println("Ошибка обработки файла: " + e.getMessage());
        }
    }

    private List<Integer> parseFieldConfig(String config) {
        return Arrays.stream(config.split(","))
                .flatMap(range -> {
                    if (range.contains("-")) {
                        String[] parts = range.split("-");
                        int start = Integer.parseInt(parts[0]);
                        int end = Integer.parseInt(parts[1]);
                        return java.util.stream.IntStream.rangeClosed(start, end).boxed();
                    } else {
                        return java.util.stream.Stream.of(Integer.parseInt(range));
                    }
                })
                .collect(Collectors.toList());
    }

    private String generateOutputFileName(File file, boolean isEncrypt) {
        String name = file.getName();
        String suffix = isEncrypt ? "_enc" : "_dec";
        return name.contains(".") ? name.replaceFirst("(\\.\\w+)$", suffix + "$1") : name + suffix;
    }
}
