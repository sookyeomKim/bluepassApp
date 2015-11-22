/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module("bluepassApp").directive(
    "finishedRegister",
    [
        'ClassSchedule',
        function (ClassSchedule) {
            return {
                restrict: "E",
                scope: {
                    finished: "=",
                    scheduleId: "@"
                },
                templateUrl: "scripts/components/util/directive/template/finishedRegister.tpl.html",
                link: function (sco, el) {
                    var registerPrams = {};

                    sco.registerFn = function () {
                        if (sco.finished === false) {
                            registerPrams = {
                                finished: true,
                                id: sco.scheduleId
                            };
                            var scheduleFinished = ClassSchedule.update(registerPrams);
                            scheduleFinished.$promise.then(function () {
                                sco.finished = true;
                            }, function () {
                                alert("에러발생");
                            });
                        } else {
                            registerPrams = {
                                finished: false,
                                id: sco.scheduleId
                            };
                            var scheduleNotFinished = ClassSchedule.update(registerPrams);
                            scheduleNotFinished.$promise.then(function () {
                                sco.finished = false;
                            }, function () {
                                alert("에러발생");
                            });
                        }
                    };

                    sco.$watch("finished", function (newVal) {
                        sco.buttonText = newVal ? "제개하기" : "예약마감";
                        newVal ? el.find("button").addClass("btn-material-blue-A200") : el.find("button")
                            .removeClass("btn-material-blue-A200");
                    });
                }
            }
        }]);
