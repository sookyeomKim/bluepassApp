/**
 * 만든이 : 수겨미
 */
"use strict";

angular.module("bluepassApp").directive("remainTicket", ['UserTicketInfo', function (UserTicketInfo) {
    return {
        restrict: "A",
        scope: {
            userId: "@",
            remain: "=",
            closed: "="
        },
        link: function (sco) {
            if (sco.userId) {
                UserTicketInfo.get({
                    id: sco.userId
                }).$promise.then(function (success) {
                        /*console.log(success)*/
                        sco.remain = success.remainDays;
                        sco.closed = success.closed;
                    })
            }
        }
    }
}]);
