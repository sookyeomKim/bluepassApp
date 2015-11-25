/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module("bluepassApp").directive("vendorRegister", vendorRegister);

vendorRegister.$inject = ['Alert'];

function vendorRegister(Alert) {
    var directive = {
        restrict: "E",
        scope: {
            model: "@"
        },
        replace: false,
        templateUrl: "scripts/components/util/directive/template/vendorRegister.html",
        link: link,
        controller: vendorRegisterController,
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

        sco.vm.vendorRegisterFn = function () {
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

vendorRegisterController.$inject = ['AccountChangeType', 'lodash'];

function vendorRegisterController(AccountChangeType, lodash) {
    var vm = this;
    var params = {};
    var parseModel;
    var userId;
    var authoritiesArry;

    parseModel = JSON.parse(vm.model);
    userId = parseModel.user.id;
    authoritiesArry = parseModel.user.authorities;

    vm.initialValue = {
        beforeChangeBtText: "제휴사회원전환",
        afterChangeBtText: "제휴사회원취소",
        type: "ROLE_VENDOR",
        clazz: "btn-material-blue-A200"
    };
    vm.stateConfirm = stateConfirm();
    vm.getAccountChangeType = getAccountChangeType;

    function stateConfirm() {
        return lodash.findIndex(authoritiesArry, function (chr) {
            return chr.name === 'ROLE_VENDOR';
        });
    }

    function getAccountChangeType() {
        if (vm.stateConfirm === -1) {
            params = {
                userId: userId,
                requestType: vm.initialValue.type
            };
        } else {
            params = {
                userId: userId,
                requestType: vm.initialValue.type,
                remove: true
            };
        }
        return AccountChangeType.change(params).$promise
    }
}
