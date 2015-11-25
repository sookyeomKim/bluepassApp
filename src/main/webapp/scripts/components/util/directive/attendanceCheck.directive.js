/**
 * 만든이 : 수겨미
 */
"use strict";

angular.module("bluepassApp").directive("attendanceCheck", attendanceCheck);

attendanceCheck.$inject = ["ReservationAttend", "Alert"];

function attendanceCheck(ReservationAttend, Alert) {
    var directive = {
        restrict: "E",
        templateUrl: "scripts/components/util/directive/template/attendanceCheck.tpl.html",
        scope: {
            reservationId: "@",
            used: "="
        },
        link: link
    };
    return directive;

    function link(sco) {
        /* console.log(sco.usedCount) */
        sco.attendancePrams = {
            id: sco.reservationId,
            checkCode: null
        };
        sco.attendanceFn = function () {
            if (sco.attendancePrams.checkCode) {
                return ReservationAttend.check(sco.attendancePrams).$promise.then(function () {
                    sco.used = true;
                    return Alert.alert1('출석체크 완료');
                }).catch(function () {
                    return Alert.alert1('에러발생');
                })
            } else {
                return Alert.alert1('체크코드를 입력해주세요.');
            }
        };

        sco.$watch("used", function (newVal) {
            sco.checkButton = newVal
        })
    }
}
