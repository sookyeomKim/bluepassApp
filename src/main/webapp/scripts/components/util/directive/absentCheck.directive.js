/**
 * 만든이 : 수겨미
 */
"use strict";

angular.module("bluepassApp").directive("absentCheck", absentCheck);

absentCheck.$inject = ["ReservationAbsent", "Alert"];

function absentCheck(ReservationAbsent, Alert) {
    var directive = {
        restrict: "E",
        templateUrl: "scripts/components/util/directive/template/absentCheck.tpl.html",
        scope: {
            reservationId: "@",
            used: "="
        },
        link: link
    };
    return directive;

    function link(sco) {
        sco.absentPrams = {
            id: sco.reservationId
        };

        sco.absentFn = function () {
            if (confirm("미출석처리 하시겠습니까? \n *미출석처리시 해당 유저는 3일의 기간삭감 패널티를 받습니다.") == true) { // 확인
                return ReservationAbsent.check(sco.absentPrams).$promise.then(function () {
                    sco.used = false;
                    return Alert.alert1('미출석체크 완료');
                }).catch(function () {
                    return Alert.alert1('에러발생');
                })
            } else { // 취소
                return false;
            }
        };

        sco.$watch("used", function (newVal) {
            sco.checkButton = newVal
        })
    }
}
