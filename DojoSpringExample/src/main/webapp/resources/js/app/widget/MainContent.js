define([
    "dojo",
    "dojo/ready",
    "dojo/_base/lang",
    "dojo/_base/declare",
    "dojo/topic",
    "dijit/layout/BorderContainer",
    "dijit/TitlePane",
    "dijit/layout/ContentPane",
    "dijit/layout/AccordionContainer",
    "dijit/layout/TabContainer",
    "app/factory/TreeGridFactory"
], function(dojo, ready, lang, declare,
            topic,
            BorderContainer, TitlePane, ContentPane, AccordionContainer, TabContainer, TreeGridFactory) {
    var createAccordion = function() {
        var accordion = new AccordionContainer({style:"width: 300px"});
        accordion.addChild(new ContentPane({
            title: "This is a content pane",
            content: "Hi!"
        }));
        accordion.addChild(new ContentPane({
            title:"This is as well",
            content:"Hi how are you?"
        }));
        accordion.addChild(new ContentPane({
            title:"This too",
            content:"Hello im fine.. thnx"
        }));
        return accordion;
    };

    var createTabs = function() {
        var tc = new TabContainer({});
        var tmpChild;

        var treeGrid = TreeGridFactory.createInstance();

        tc.addChild(tmpChild = new ContentPane({
            title: "Food",
            content: "We offer amazing food"
        }));

        tmpChild.addChild(treeGrid);

        tc.addChild(tmpChild = new ContentPane({
            title: "Drinks",
            content: "We are known for our drinks."
        }));
        tmpChild.addChild(createNativeList());

        return tc;
    };

    var createNativeList = function() {
        var ul = dojo.create("ul");
        var items = ["one", "two", "three", "four"];
        dojo.forEach(items, function(data){
            dojo.create("li", { innerHTML: data }, ul);
        });

        var handle = dojo.connect(ul, "onclick", function(event){
            console.log(arguments);
            alert("ul onclick");
        });

        return new ContentPane({
            content: ul
        })
    }

    ready(function() {
        var container = new BorderContainer({style: "height: 777px; width: 100%;"}, "layout");
        container.startup();

        var accordion = createAccordion();
        lang.mixin(accordion, { region: "left" });

        var tabs = createTabs();
        lang.mixin(tabs, { region: "center" });

        var tabPanel3 = new ContentPane({
            style: "width: 150px",
            content: "Подсказки и всякая хрень",
            region: "right"
        });

        container.addChild(accordion);
        container.addChild(tabs);
        container.addChild(tabPanel3);

        container.addChild(new ContentPane({
            region: "bottom",
            style: "height: 100px",
            content: "общие ссылки"
        }));
    });
});