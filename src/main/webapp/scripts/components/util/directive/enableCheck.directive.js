/**
 * 만든이 : 수겨미
 */
"use strict";

angular.module("bluepassApp").directive("enableCheck", function () {
    return {
        restrict: "A",
        scope: {
            enable: "=",
            check: "="
        },
        link: function (sco) {
            sco.$watch("enable", function (newVal) {
                sco.check = newVal;
            })
        }
    }
});
