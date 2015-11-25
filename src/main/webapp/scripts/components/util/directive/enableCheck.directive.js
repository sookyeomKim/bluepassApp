/**
 * 만든이 : 수겨미
 */
"use strict";

angular.module("bluepassApp").directive("enableCheck", enableCheck);

enableCheck.$inject = [];

function enableCheck() {
    var directive = {
        restrict: "A",
        scope: {
            enable: "=",
            check: "="
        },
        link:link
    };
    return directive;

    function link(sco) {
        sco.$watch("enable", function (newVal) {
            sco.check = newVal;
        })
    }
}
