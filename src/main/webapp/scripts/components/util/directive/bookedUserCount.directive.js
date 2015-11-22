/**
 * 만든이 : 수겨미
 */
"use strict";

angular.module("bluepassApp").directive("bookedUserCount", ['bookedClassSchedule', function (bookedClassSchedule) {
    return {
        restrict: "A",
        scope: {
            scheduleId: "@",
            count: "="
        },
        link: function (sco) {
            if (sco.scheduleId) {
                var bookedUserCall = bookedClassSchedule.query({
                    id: sco.scheduleId
                });
                bookedUserCall.$promise.then(function (success) {
                    sco.count = success.length;
                    /*console.log(sco.count)*/
                })
            } else {
                sco.count = 0;
            }
        }
    }
}]);
