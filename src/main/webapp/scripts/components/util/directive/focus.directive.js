/**
 * 만든이 : 수겨미
 */
"use strict";

angular.module("bluepassApp").directive('focusMe', focusMe);

focusMe.$inject = ['$timeout'];

function focusMe($timeout) {
    var directive = {
        scope: {
            trigger: '=focusMe'
        },
        link: link
    };
    return directive;

    function link(scope, element) {
        scope.$watch('trigger', function (value) {
            if (value === true) {
                // console.log('trigger',value);
                $timeout(function () {
                    element[0].focus();
                    scope.trigger = false;
                });
            }
        });
    }
}
