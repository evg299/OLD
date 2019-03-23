<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>${message}</title>
    <script type="text/javascript">
        dojoConfig= {
            async:true,
            parseOnLoad:true,
            locale:'en-us',
            extraLocale: ['ru-ru'],
            packages:[{
                name:'app',
                location:'<%=request.getContextPath()%>/resources/js/app'
            }]
        };
    </script>
    <script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/dojo/1.10.2/dojo/dojo.js"></script>

    <link rel="stylesheet" type="text/css" href="//ajax.googleapis.com/ajax/libs/dojo/1.10.2/dojo/resources/dojo.css"/>
    <link rel="stylesheet" type="text/css" href="//ajax.googleapis.com/ajax/libs/dojo/1.10.2/dijit/themes/claro/claro.css"/>

    <style type="text/css">
        @import "//ajax.googleapis.com/ajax/libs/dojo/1.10.2/dojox/layout/resources/FloatingPane.css";
        @import "//ajax.googleapis.com/ajax/libs/dojo/1.10.2/dojox/layout/resources/ResizeHandle.css";

        @import "//ajax.googleapis.com/ajax/libs/dojo/1.10.2/dijit/themes/claro/document.css";
        @import "//ajax.googleapis.com/ajax/libs/dojo/1.10.2/dijit/themes/claro/claro.css";
        @import "//ajax.googleapis.com/ajax/libs/dojo/1.10.2/dojox/grid/resources/Grid.css";
        @import "//ajax.googleapis.com/ajax/libs/dojo/1.10.2/dojox/grid/resources/claroGrid.css";
        @import "//ajax.googleapis.com/ajax/libs/dojo/1.10.2/dojox/grid/enhanced/resources/claro/EnhancedGrid.css";
        @import "//ajax.googleapis.com/ajax/libs/dojo/1.10.2/dojox/grid/enhanced/resources/EnhancedGrid_rtl.css";
        @import "//ajax.googleapis.com/ajax/libs/dojo/1.10.2/dojox/layout/resources/ScrollPane.css";
    </style>

    <link el="stylesheet" type="text/css" href="<%=request.getContextPath()%>/resources/css/style.css"/>

    <script type="text/javascript">
        require(["dojo/parser"]);
    </script>
</head>
<body class="claro">
<!-- app runner -->
<div data-dojo-type="app/ApplicationRun"></div>

<!-- containers -->
<div id="menu"></div>
<div id="info"></div>
<div id="layout"></div>

</body>
</html>