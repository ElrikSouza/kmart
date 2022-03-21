package com.elrik.tap;

import java.util.ArrayList;

import com.elrik.tap.authorization.LoginScene;
import com.elrik.tap.db.DbConn;
import com.elrik.tap.home.HomeScene;
import com.elrik.tap.inventory.AddItemToInventoryScene;
import com.elrik.tap.inventory.InventoryMainScene;
import com.elrik.tap.inventory.PaymentScene;
import com.elrik.tap.inventory.PurchaseEntry;
import com.elrik.tap.inventory.SellProductScene;
import com.elrik.tap.stats.KMartStatsScene;
import com.elrik.tap.ui.AppWindow;

public class App {
    public static void main(String[] args) {
        var appWindow = AppWindow.getInstance();

        appWindow.showAppWindow();
        appWindow.setCurrentPanel(new KMartStatsScene());
    }
}
