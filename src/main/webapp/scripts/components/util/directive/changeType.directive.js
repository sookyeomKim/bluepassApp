/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module("bluepassApp").directive("changeType", changeType);

changeType.$inject = ['Alert'];

function changeType(Alert) {
    var directive = {
        restrict: "E",
        scope: {
            changeType: "@",
            model: "@"
        },
        replace: false,
        templateUrl: "scripts/components/util/directive/template/changeType.html",
        link: link,
        controller: changeTypeController,
        controllerAs: 'vm',
        bindToController: true
    };
    return directive;

    function link(sco, el) {
        if (sco.vm.stateConfirm === -1) {
            el.find("button").removeClass(sco.vm.initialValue.clazz);
            sco.vm.buttonText = sco.vm.initialValue.beforeChangeBtText;
        } else {
            el.find("button").addClass(sco.vm.initialValue.clazz);
            sco.vm.buttonText = sco.vm.initialValue.afterChangeBtText;
        }

        sco.vm.changeTypeFn = function () {
            if (sco.vm.stateConfirm === -1) {
                sco.vm.getAccountChangeType().then(function () {
                    el.find("button").addClass(sco.vm.initialValue.clazz);
                    sco.vm.buttonText = sco.vm.initialValue.afterChangeBtText;
                    sco.vm.stateConfirm = 1;
                }).catch(function () {
                    return Alert('에러발생')
                })
            } else {
                sco.vm.getAccountChangeType().then(function () {
                    el.find("button").removeClass(sco.vm.initialValue.clazz);
                    sco.vm.buttonText = sco.vm.initialValue.beforeChangeBtText;
                    sco.vm.stateConfirm = -1;
                }).catch(function () {
                    return Alert('에러발생')
                })
            }
        }
    }
}

changeTypeController.$inject = ['AccountChangeType', 'lodash'];

function changeTypeController(AccountChangeType, lodash) {
    var vm = this;
    var changeTypeParams = {};
    var parseModel;
    var userId;
    var authoritiesArry;

    parseModel = JSON.parse(vm.model);
    userId = parseModel.user.id;
    authoritiesArry = parseModel.user.authorities;

    vm.initialValue = {};
    vm.stateConfirm = stateConfirm();
    vm.getAccountChangeType = getAccountChangeType;

    if (vm.changeType === "VENDOR") {
        vm.initialValue = {
            beforeChangeBtText: "제휴사회원전환",
            afterChangeBtText: "제휴사회원취소",
            type: "ROLE_VENDOR",
            clazz: "btn-material-blue-A200"
        }
    } else if (vm.changeType === "REGISTER") {
        vm.initialValue = {
            beforeChangeBtText: "회원전환",
            afterChangeBtText: "회원취소",
            type: "ROLE_REGISTER",
            clazz: "btn-material-blue-A200"
        }
    }

    function stateConfirm() {
        return lodash.findIndex(authoritiesArry, function (chr) {
            return chr.name === 'ROLE_VENDOR';
        });
    }

    function getAccountChangeType() {
        if (vm.stateConfirm === -1) {
            changeTypeParams = {
                userId: userId,
                requestType: vm.initialValue.type
            };
        } else {
            changeTypeParams = {
                userId: userId,
                requestType: vm.initialValue.type,
                remove: true
            };
        }
        return AccountChangeType.change(changeTypeParams).$promise
    }
}
