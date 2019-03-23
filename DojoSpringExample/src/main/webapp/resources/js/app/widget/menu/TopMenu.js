define([
    "dojo/ready",
    "dojo/_base/declare",
    "dojo/topic",
    "dijit/MenuBar",
    "dijit/PopupMenuBarItem",
    "dijit/Menu",
    "dijit/MenuItem",
    "dijit/DropDownMenu",
    "dijit/PopupMenuItem",
    "app/controller/TopMenuController",
    "app/factory/UsersWindowFactory"
], function(ready, declare, topic, MenuBar, PopupMenuBarItem, Menu, MenuItem, DropDownMenu, PopupMenuItem, TopMenuController,
            UsersWindowFactory) {
    var createDialogPopup = function() {
        var popup = new Menu();
        popup.addChild(new MenuItem({
            label: "Create modal dialog",
            onClick: function() {
                TopMenuController.showAlert("use controller");
            }
        }));
        popup.addChild(new MenuItem({
            label: "Create user window",
            onClick: function() {
                var window = UsersWindowFactory.createInstance();
            }
        }));
        return popup;
    };

    ready(function () {
        var menuBar = new MenuBar({}, "menu");

        var mainMenu = new DropDownMenu({});
        mainMenu.addChild(new MenuItem({
            label: "Тест",
            onClick: function() {
                alert("qqqq!!");
            }
        }));

        var helpMenu = new DropDownMenu({});

        helpMenu.addChild(new MenuItem({
            label: "Тест2",
            onClick: function() {
                alert("qqqq!!");
            }
        }));

        helpMenu.addChild(new PopupMenuItem({
            label: "popup",
            popup: createDialogPopup()
        }));

        menuBar.addChild(new PopupMenuBarItem({
            label: "Main",
            popup: mainMenu
        }));
        menuBar.addChild(new PopupMenuBarItem({
            label: "Help",
            popup: helpMenu
        }));
    });
});