module ca.benbingham.javachessmodule {
    requires javafx.controls;

    exports ca.benbingham.javachess.gamelogic to javafx.graphics;
    exports ca.benbingham.javachess.popupwindow to javafx.graphics;
    exports ca.benbingham.javachess;
}