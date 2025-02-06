import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import py4j.GatewayServer;

public class AIController {
    @FXML
    private TextField inputField;
    @FXML
    private TextArea outputArea;
    @FXML
    private Button sendButton;
    @FXML
    private Button clearButton;
    @FXML
    private Button exitButton;

    private GatewayServer gatewayServer;
    private AIOperator aiOperator;

    @FXML
    public void initialize() {
        // Iniciar el servidor Py4J
        gatewayServer = new GatewayServer();
        gatewayServer.start();

        // Conectar con el operador de IA en Python
        try {
            aiOperator = (AIOperator) gatewayServer.getGateway().get("aiOperator");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Configurar los eventos de los botones
        sendButton.setOnAction(e -> sendButtonClicked());
        clearButton.setOnAction(e -> clearButtonClicked());
        exitButton.setOnAction(e -> exitButtonClicked());
    }

    private void sendButtonClicked() {
        String input = inputField.getText().trim();
        if (!input.isEmpty()) {
            try {
                String response = aiOperator.generateResponse(input);
                outputArea.appendText("Tú: " + input + "\n");
                outputArea.appendText("IA: " + response + "\n");
                inputField.clear();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void clearButtonClicked() {
        outputArea.clear();
        inputField.clear();
    }

    private void exitButtonClicked() {
        System.exit(0);
    }
}