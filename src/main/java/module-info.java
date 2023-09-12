module kbur.c482 {
    requires javafx.controls;
    requires javafx.fxml;
            
                            
    opens kbur.c482 to javafx.fxml;
    opens kbur.c482.model to javafx.fxml;
    opens kbur.c482.controller to javafx.fxml;

    exports kbur.c482;
    exports kbur.c482.controller;
    exports kbur.c482.model;
}



