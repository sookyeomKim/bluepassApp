/**
 * 만든이 : 수겨미
 */
"use strict";

angular.module("bluepassApp").directive("enableRegister", enableRegister);

enableRegister.$inject = ['Alert'];

function enableRegister(Alert) {
    var directive = {
        restrict: "E",
        templateUrl: "scripts/components/util/directive/template/enableRegister.tpl.html",
        scope: {
            scheduleId: "@",
            enable: "="
        },
        link: link,
        controller: enableRegisterController,
        controllerAs: 'vm',
        bindToController: true
    };
    return directive;

    function link(sco, el) {
        sco.vm.registerFn = function () {
            if (sco.vm.enable === false) {
                sco.vm.getActionScheduleEnable().$promise.then(function () {
                    sco.vm.enable = true;
                }).catch(function () {
                    return Alert.alert1('에러발생')
                })
            } else {
                sco.vm.getActionScheduleEnable().$promise.then(function () {
                    sco.vm.enable = false;
                }).catch(function () {
                    return Alert.alert1('에러발생')
                })
            }
        };

        sco.$watch("vm.enable", function (newVal) {
            sco.vm.buttonText = newVal ? "종료" : "시작";
            newVal ? el.find(".enableButton").addClass('btn-material-blue-A200') : el.find(
                ".enableButton").removeClass('btn-material-blue-A200')
        });
    }
}

enableRegisterController.$inject = ['ActionScheduleEnable'];

function enableRegisterController() {
    var vm = this;
    var registerPrams = {};

    vm.getActionScheduleEnable = getActionScheduleEnable;

    function getActionScheduleEnable() {
        registerPrams.id = vm.scheduleId;
        registerPrams.enable = vm.enable === false;

        return ActionScheduleEnable.enable(registerPrams).$promise
    }
}
