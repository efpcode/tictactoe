module org.example.tictactoe {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.tictactoe to javafx.fxml;
    exports org.example.tictactoe;
    exports org.example.tictactoe.view.controllers;
    opens org.example.tictactoe.view.controllers to javafx.fxml;
}
