/**
 * 만든이 : 수겨미
 */
"use strict";

angular.module("bluepassApp").directive("attendanceCheck", ["ReservationAttend", function (ReservationAttend) {
    return {
        restrict: "E",
        templateUrl: "scripts/components/util/directive/template/attendanceCheck.tpl.html",
        scope: {
            reservationId: "@",
            used: "="
        },
        link: function (sco) {
            /* console.log(sco.usedCount) */
            sco.attendancePrams = {
                id: sco.reservationId,
                checkCode: null
            };
            sco.attendanceFn = function () {
                if (sco.attendancePrams.checkCode) {
                    var attendanceSave = ReservationAttend.check(sco.attendancePrams);
                    attendanceSave.$promise.then(function () {
                        alert("출첵완료");
                        sco.used = true;
                    }, function () {
                        alert("에러발생");
                    })
                } else {
                    alert("체크코드를 입력해주세요.");
                }
            };

            sco.$watch("used", function (newVal) {
                sco.checkButton = newVal
            })
        }
    }
}]);
