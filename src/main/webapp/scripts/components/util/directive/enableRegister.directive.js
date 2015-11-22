/**
 * 만든이 : 수겨미
 */
"use strict";

angular.module("bluepassApp").directive(
    "enableRegister",
    [
        'ActionScheduleEnable',
        function (ActionScheduleEnable) {
            return {
                restrict: "E",
                templateUrl: "scripts/components/util/directive/template/enableRegister.tpl.html",
                scope: {
                    scheduleId: "@",
                    enable: "="
                },
                link: function (sco, el) {
                    /* 활성화버튼의 상태를 불러온다 */
                    var registerPrams = {};
                    sco.registerFn = function () {
                        if (sco.enable === false) {
                            registerPrams = {
                                id: sco.scheduleId,
                                enable: true
                            };
                            var scheduleEnable = ActionScheduleEnable.enable(registerPrams);
                            scheduleEnable.$promise.then(function () {
                                sco.enable = true;
                            }, function () {
                                alert("에러발생");
                            });
                        } else {
                            registerPrams = {
                                id: sco.scheduleId,
                                enable: false
                            };
                            var scheduleNotEnable = ActionScheduleEnable.enable(registerPrams);
                            scheduleNotEnable.$promise.then(function () {
                                sco.enable = false;
                            }, function (error) {
                                if (error.data.message) {
                                    alert(error.data.message);
                                } else {
                                    alert("에러발생");
                                }
                            });
                        }
                    };

                    sco.$watch("enable", function (newVal) {
                        sco.buttonText = newVal ? "종료" : "시작";
                        newVal ? el.find(".enableButton").addClass('btn-material-blue-A200') : el.find(
                            ".enableButton").removeClass('btn-material-blue-A200')
                    });
                }
            }
        }]);
