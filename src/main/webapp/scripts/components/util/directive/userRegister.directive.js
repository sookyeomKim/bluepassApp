/**
 * 만든이 : 수겨미
 */
"use strict";

angular.module("bluepassApp").directive("userRegister", userRegister);

userRegister.$inject = [];

function userRegister() {
    var directive = {
        restrict: "E",
        template: "<md-button class='md-raised userRegisterButton' ng-click='vm.activateConfirm($event)'>{{vm.userStateText}}</md-button>",
        scope: {
            pId: "@",
            userId: "@",
            userName: "@",
            ticketId: "@",
            activated: "="
        },
        link: userRegisterLink,
        controller: userRegisterController,
        controllerAs: 'vm',
        bindToController: true
    };
    return directive;

    function userRegisterLink(sco) {
        sco.$watch('vm.activated', function (newVal) {
            sco.vm.userStateText = newVal ? '회원취소' : '회원등록';
        });
    }
}

userRegisterController.$inject = [
    'TicketHistory',
    'Alert'
];

function userRegisterController(TicketHistory,Alert) {
    var vm = this;

    vm.activatePrams = {
        id: vm.pId,
        userId: vm.userId,
        ticketId: vm.ticketId
    };
    vm.getActivate = getActivate;
    vm.activateConfirm = activateConfirm;

    function getActivate() {
        vm.activated ? vm.activatePrams.activated = false : vm.activatePrams.activated = true;
        return TicketHistory.update(vm.activatePrams).$promise
    }

    function activateConfirm($event) {
        var message = vm.activated ? vm.userName + "님의 회원권한을 취소시키겠습니까?" :
        vm.userName + "님을 회원으로 전환하시겠습니까?";
        return Alert.alert2($event, '전환', message, '전환하기').then(function () {
            return vm.getActivate().then(function () {
                vm.activated ? vm.activated = false : vm.activated = true
            }).catch(function () {
                return Alert.alert1('에러가 발생하여 전환되지 않았습니다.')
            })
        })
    }
}
