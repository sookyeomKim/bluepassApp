/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module("bluepassApp").directive("autoWidth", autoWidth);

autoWidth.$inject = [];

function autoWidth() {
    var directiva = {
        restrict: "A",
        scope: true,
        link: link
    };
    return directiva;

    function link(scope, element) {
        element.css("width", calculateWidth() + "px");
        $(window).resize(function () {
            element.css("width", calculateWidth() + "px");
        });
    }

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
}
