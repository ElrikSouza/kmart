package com.elrik.tap;

import com.elrik.tap.authorization.LoginScene;
import com.elrik.tap.ui.AppWindow;

public class App {
    public static void main(String[] args) {
        var appWindow = AppWindow.getInstance();

        appWindow.showAppWindow();
        appWindow.setCurrentPanel(new LoginScene());
    }
}
