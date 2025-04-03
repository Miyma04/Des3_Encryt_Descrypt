package com.encryptiontool;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;

public class Controller {
    @FXML private Label fileLabel;
    @FXML private ToggleGroup modeToggle;
    @FXML private Label statusLabel;
    @FXML private RadioButton encryptMode;

    private File selectedFile;

    @FXML
    private void chooseFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Выберите файл для обработки");
        selectedFile = fileChooser.showOpenDialog(new Stage());

        if (selectedFile != null) {
            fileLabel.setText("Выбран файл: " + selectedFile.getName());
        } else {
            fileLabel.setText("Файл не выбран");
        }
    }

    @FXML
    private void processFile() {
        if (selectedFile == null) {
            statusLabel.setText("Ошибка: выберите файл!");
            statusLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        boolean isEncrypt = encryptMode.isSelected();
        String mode = isEncrypt ? "Шифрование" : "Дешифрование";

        statusLabel.setText(mode + " файла...");
        statusLabel.setStyle("-fx-text-fill: blue;");

        new FileProcessor().process(selectedFile, isEncrypt);

        statusLabel.setText("Готово!");
        statusLabel.setStyle("-fx-text-fill: green;");
    }
}
