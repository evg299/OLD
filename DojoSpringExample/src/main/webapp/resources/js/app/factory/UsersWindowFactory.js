define([
    'dojo/ready',
    "dojo/topic",
    "dojox/layout/FloatingPane",
    "dijit/layout/BorderContainer",
    "dijit/layout/ContentPane",
    "dojox/layout/TableContainer",
    'dojox/grid/EnhancedGrid',
    'dojox/grid/enhanced/plugins/Pagination',
    'dojox/grid/enhanced/plugins/Filter',
    'dojox/grid/enhanced/plugins/Search',
    "dojo/data/ObjectStore",
    "dojo/store/JsonRest",
    'dojo/date/stamp',
    'dojo/date/locale',
    "dijit/form/Form",
    "dijit/form/TextBox",
    "dijit/form/DateTextBox",
    "dijit/form/Button"
], function (ready, topic,
             FloatingPane, BorderContainer, ContentPane, TableContainer, EnhancedGrid,
             Pagination, Filter, Search,
             ObjectStore, JsonRest,
             stamp, locale,
             Form, TextBox, DateTextBox, Button) {
    // private part
    var createFilterForm = function () {
        var form = new Form();

        var fieldsContainer = new TableContainer({
            region: "left",
            style: "width: 269px;",
            showLabels: true,
            cols: 1
        }).placeAt(form.containerNode);

        fieldsContainer.addChild(new TextBox({
            label: "First name",
            name: "fname",
            style: "width: 185px;"
        }));
        fieldsContainer.addChild(new TextBox({
            label: "Email",
            name: "email",
            style: "width: 185px;"
        }));
        fieldsContainer.addChild(new DateTextBox({
            label: "Birth date",
            name: "birthDate",
            style: "width: 185px;"
        }));

        return form;
    };

    var createGrid = function () {
        var usersRest = new JsonRest({
            target: "/rest/users/"
        });
        var usersStore = new ObjectStore({
            objectStore: usersRest,
            idAttribute: "id"
        });

        var grid = new EnhancedGrid({
            style: "height: 351px;",
            store: usersStore,
            structure: [[
                {'name': 'ID', 'field': 'id', 'width': '50px', datatype: "number"},
                {'name': 'First name', 'field': 'fname', 'width': '100px', editable: false, datatype: "string"},
                {'name': 'Email', 'field': 'email', 'width': '150px', datatype: "string"},
                {
                    'name': 'Birth Date', 'field': 'birthDate', 'width': '130px', editable: false, datatype: "date",
                    formatter: function (datum) {
                        var d = new Date(datum);
                        return locale.format(d, {selector: 'date', formatLength: 'long'});
                    }
                }
            ]],
            rowSelector: '20px',
            plugins: {
                pagination: {
                    pageSizes: ["10", "25", "50", "100"],
                    description: true,
                    sizeSwitch: true,
                    pageStepper: true,
                    gotoButton: true,
                    maxPageStep: 4,
                    position: "bottom"
                },
                filter: {
                    closeFilterbarButton: false,
                    ruleCount: 0,
                    itemsName: "users"
                },
                search: {}
            }
        });

        topic.subscribe("userList/filter", function () {
            usersRest.query(arguments[0], arguments[1])
                .then(function (results) {
                    console.log(results);
                    // grid.render();

                    grid.searchRow({
                        "fname":    /^[Qq]qqq/,
                        "email":    "qaz*"
                    }, function(rowIndex, item){
                        console.log(rowIndex, item);
                    });
                });
        });

        return grid;
    };

    var createFilterPane = function() {
        var result = new TableContainer({
            region: "left",
            style: "width: 271px; height: 385px;",
            showLabels: false,
            cols: 1
        });

        result.addChild(new ContentPane({
            content: "<b>Server filter:</b>"
        }));
        result.addChild(filterForm = createFilterForm());
        result.addChild(new Button({
            label: "Show",
            onClick: function () {
                // filterForm.validate();
                console.log(filterForm.getValues());
                topic.publish("userList/filter",
                    {
                        fname: "Ivan"
                    },
                    {
                        sort: [
                            { attribute: "email" },
                            { attribute: "birthDate", descending: true }
                        ]
                    }
                );
            }
        }));
        return result;
    };

    return {
        createInstance: function () {
            var fp, bc, gridPane, filterPane, filterForm;

            fp = new FloatingPane({
                resizable: false,
                dockable: true,
                maxable: false,
                closable: true,
                style: {
                    left: '50px',
                    top: '50px',
                    width: '800px',
                    height: '400px',
                    border: '1px solid black',
                    position: 'absolute',
                    backgroundColor: '#eee'
                },
                title: "Select users"
            });


            //---------

            fp.addChild(bc = new BorderContainer({
                //gutters: false,
                //style: {padding: '0'}
            }));
            bc.addChild(filterPane = createFilterPane());


            bc.addChild(gridPane = new TableContainer({
                region: "center",
                style: "width: 529px;",
                showLabels: false,
                cols: 1
            }));

            gridPane.addChild(new ContentPane({
                content: "<b>DATA:</b>"
            }));

            var grid = createGrid();
            gridPane.addChild(grid);

            fp.placeAt(document.body);

            fp.startup();
            fp.bringToTop();

            return fp;
        }
    };
});