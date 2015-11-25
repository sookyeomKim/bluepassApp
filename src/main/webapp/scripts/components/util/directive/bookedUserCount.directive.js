/**
 * 만든이 : 수겨미
 */
"use strict";

angular.module("bluepassApp").directive("bookedUserCount", ['BookedClassSchedule', function (BookedClassSchedule) {
    return {
        restrict: "A",
        scope: {
            scheduleId: "@",
            count: "="
        },
        link: function (sco) {
            if (sco.scheduleId) {
                var bookedUserCall = BookedClassSchedule.query({
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
