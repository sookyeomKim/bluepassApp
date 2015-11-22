/**
 * 만든이 : 수겨미
 */
"use strict";

angular.module("bluepassApp").directive("absentCheck", ["ReservationAbsent", function (ReservationAbsent) {
    return {
        restrict: "E",
        templateUrl: "scripts/components/util/directive/template/absentCheck.tpl.html",
        scope: {
            reservationId: "@",
            used: "="
        },
        link: function (sco) {
            /* console.log(sco.usedCount) */
            sco.absentPrams = {
                id: sco.reservationId
            };

            sco.absentFn = function () {
                if (confirm("미출석처리 하시겠습니까? \n *미출석처리시 해당 유저는 3일의 기간삭감 패널티를 받습니다.") == true) { // 확인
                    var absentSave = ReservationAbsent.check(sco.absentPrams);
                    absentSave.$promise.then(function () {
                        sco.used = false;
                    }, function () {
                        alert("에러발생");
                    });
                } else { // 취소
                    return false;
                }
            };

            sco.$watch("used", function (newVal) {
                sco.checkButton = newVal
            })
        }
    }
}]);
