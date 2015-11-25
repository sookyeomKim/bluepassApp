/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module("bluepassApp").directive("finishedRegister", finishedRegister);

finishedRegister.$inject = ['Alert'];

function finishedRegister(Alert) {
    var directive = {
        restrict: "E",
        scope: {
            finished: "=",
            scheduleId: "@"
        },
        templateUrl: "scripts/components/util/directive/template/finishedRegister.tpl.html",
        link: link,
        controller: finishedRegisterController,
        controllerAs: 'vm',
        bindToController: true
    };
    return directive;

    function link(sco, el) {
        sco.vm.registerFn = function () {
            if (sco.vm.finished === false) {
                sco.vm.getClassSchedule().then(function () {
                    return sco.vm.finished = true;
                }).catch(function () {
                    return Alert.alert1('에러발생')
                })
            } else {
                sco.vm.getClassSchedule().then(function () {
                    return sco.vm.finished = false;
                }).catch(function () {
                    return Alert.alert1('에러발생')
                })
            }
        };

        sco.$watch("vm.finished", function (newVal) {
            sco.vm.buttonText = newVal ? "제개하기" : "예약마감";
            newVal ? el.find("button").addClass("btn-material-blue-A200") : el.find("button")
                .removeClass("btn-material-blue-A200");
        });
    }
}

finishedRegisterController.$inject = [ClassSchedule];

function finishedRegisterController(ClassSchedule) {
    var vm = this;
    var registerPrams = {};

    vm.getClassSchedule = getClassSchedule;

    function getClassSchedule() {
        registerPrams.id = vm.scheduleId;
        registerPrams.registerPrams = sco.finished === false;

        return ClassSchedule.update(registerPrams).$promise
    }
}
