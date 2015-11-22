/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module("bluepassApp").directive("autoWidth", [function () {
    function calculateWidth() {
        var currentWidth = window.innerWidth;
        if (currentWidth < 600) {
            currentWidth = currentWidth * 0.55;
        } else if (currentWidth < 1200) {
            currentWidth = currentWidth * 0.25;
        } else {
            currentWidth = currentWidth * 0.2;
        }
        return currentWidth;
    }

    return {
        restrict: "A",
        scope: true,
        link: function (scope, element) {
            element.css("width", calculateWidth() + "px");
            $(window).resize(function () {
                element.css("width", calculateWidth() + "px");
            });
        }
    }
}]);
