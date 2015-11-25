/**
 * 만든이 : 수겨미
 */
"use strict";

angular.module("bluepassApp").directive("remainTicket", remainTicket);

remainTicket.$inject = ['UserTicketInfo'];

function remainTicket(UserTicketInfo) {
    var directive = {
        restrict: "A",
        scope: {
            userId: "@",
            remain: "=",
            closed: "="
        },
        link: link
    };
    return directive;

    function link(sco) {
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
