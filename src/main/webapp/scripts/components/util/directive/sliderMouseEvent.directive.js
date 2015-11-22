/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module("bluepassApp").directive("timeEvent", function () {
    return {
        restrict: "A",
        link: function (scope, element) {
            element.find(".ngrs-handle").on("mouseup", function () {
                scope.$broadcast('timeEvent', {
                    exerciseType: scope.exerciseType,
                    siteType: scope.siteType,
                    timeType: scope.timeType
                });
            })
        }
    }
});
