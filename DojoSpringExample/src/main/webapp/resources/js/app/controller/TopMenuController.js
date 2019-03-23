define([
    'dojo/ready',
    'dojo/_base/declare'
], function(ready, declare) {
    var controller = {
        showAlert: function(txt) {
            alert(txt);
        }
    };
    return controller;
});