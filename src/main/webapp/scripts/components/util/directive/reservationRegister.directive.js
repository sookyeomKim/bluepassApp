/**
 * 만든이 : 수겨미
 */
"use strict";

angular.module("bluepassApp").directive("reservationRegister", reservationRegister);

reservationRegister.$inject = [
    'lodash'
];

function reservationRegister(lodash) {
    var directive = {
        restrict: "E",
        templateUrl: "scripts/components/util/directive/template/reservationRegister.tpl.html",
        scope: {
            userId: "@",
            userStatus: "@",
            userRoles: "@",
            scheduleId: "@",
            year: "@",
            month: "@",
            date: "@",
            hour: "@",
            minute: "@",
            reservationClose: "@"
        },
        link: link,
        controller: reservationRegisterController,
        controllerAs: "vm",
        bindToController: true
    };

    return directive;

    function link(sco, el) {
        sco.vm.messages = [];
        sco.vm.buttonShow = false;
        sco.vm.registerStatus = false;

        if (sco.vm.userId) {
            var userRolesArry = JSON.parse(sco.vm.userRoles);
            var stateConfirm;

            stateConfirm = lodash.findIndex(userRolesArry, function (chr) {
                return chr === 'ROLE_REGISTER' || chr === 'ROLE_ADMIN';
            });

            if (stateConfirm === -1) {
                if (sco.vm.userStatus === "등록요청") {
                    sco.vm.registerStatus = true
                }
                sco.vm.reservatable = false;
                sco.vm.buttonShow = true;
            } else {
                sco.vm.getReservationStatus().then(function (success) {
                    sco.vm.reservatable = success.reservatable;
                    sco.vm.messages = success.messages;
                    sco.vm.alreadyReservated = success.alreadyReservated;
                    sco.vm.buttonShow = true;
                })
            }
        } else {
            sco.vm.buttonShow = true;
        }

        el.find("timer").css({
            "cursor": "default"
        });

        el.find("timer").hover(function () {
            jQuery(this).css("background-color", "inherit");
        });

        sco.vm.buttonLock = sco.vm.timeRemaining <= 0;
        sco.vm.buttonText = sco.vm.timeRemaining <= 0;

        sco.vm.callback = function () {
            sco.vm.buttonLock = true;
            sco.vm.buttonText = true;
        };

        sco.$watchGroup(["vm.buttonLock", "vm.alreadyReservated"], function () {
            sco.vm.reservationStateText = sco.vm.buttonText ? "예약마감" : (sco.vm.alreadyReservated ? '예약완료'
                : '예약');
            if (sco.vm.alreadyReservated) {
                sco.vm.buttonLock = true;
            }
        });
    }
}

reservationRegisterController.$inject = ['$scope',
    'ReservationStatus',
    '$timeout',
    "$state",
    '$mdDialog'
];

function reservationRegisterController($scope, ReservationStatus, $timeout, $state, $mdDialog) {
    var vm = this;

    vm.getReservationStatus = getReservationStatus;
    vm.reservationRegisterDialog = reservationRegisterDialog;

    /* 남은 시간 계산 */
    getTimeRemaining();

    function getTimeRemaining() {
        var date1 = new Date(vm.year, vm.month - 1, vm.date, vm.hour,
            vm.minute, 0).getTime();
        var date2 = new Date().getTime();
        var toMidnight = (vm.hour * 60 * 60) + (vm.minute * 60);

        switch (vm.reservationClose) {
            case '0':
                vm.timeRemaining = ((date1 - date2) / 1000) - (60 * 60);
                break;
            case '1':
                vm.timeRemaining = ((date1 - date2) / 1000) - (60 * 60 * 3);
                break;
            case '2':
                vm.timeRemaining = ((date1 - date2) / 1000) - toMidnight;
                break;
            default:
                vm.timeRemaining = ((date1 - date2) / 1000) - toMidnight;
                break;
        }
        if (vm.timeRemaining <= 0) {
            vm.timeRemaining = 0;
            $timeout(function () {
                $scope.$broadcast('timer-stop');
            }, 0)
        }
    }

    function getReservationStatus() {
        return ReservationStatus.call({
            userId: vm.userId,
            scheduleId: vm.scheduleId
        }).$promise
    }

    function reservationRegisterDialog($event) {
        var parentEl = angular.element(document.body);
        if (!vm.userId) {
            return $state.go("login");
        } else {
            return $mdDialog.show({
                parent: parentEl,
                targetEvent: $event,
                templateUrl: 'scripts/components/util/directive/template/reservationRegisterDialog.tpl.html',
                locals: {
                    reservationParams: {
                        userId: vm.userId,
                        scheduleId: vm.scheduleId,
                        cancel: false
                    },
                    reservatable: vm.reservatable,
                    messages: vm.messages,
                    alreadyReservated: vm.alreadyReservated
                },
                clickOutsideToClose: true,
                controller: reservationRegisterDialogController,
                controllerAs: "vm"
            }).then(function (boolean) {
                vm.alreadyReservated = boolean;
            })
        }
    }
}
