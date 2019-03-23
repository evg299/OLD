if (typeof console === "undefined" || typeof console.log === "undefined") {
    console = {};
    console.log = function() {};
    console.debug = function() {};
}
if (typeof console.debug === "undefined") {
    console.debug = console.log;
}