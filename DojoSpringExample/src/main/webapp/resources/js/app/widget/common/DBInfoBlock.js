define([
    "dojo/ready",
    "dojo/_base/declare",
    "dijit/layout/ContentPane",
    "dojox/layout/TableContainer",
    "dijit/TitlePane",
    "dojo/store/JsonRest"
], function(ready, declare, ContentPane, TableContainer, TitlePane, JsonRest){
    ready(function () {
        var tabPanel = new TitlePane({
            style: "width: 100%;  height: 95%;",
            title: "Информация по странице",
            content: "qweqwe"
        }, "info");
    });
});