/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module("bluepassApp").directive("reservationCancel", reservationCancel);

reservationCancel.$inject = [];

function reservationCancel() {
    var directive = {
        restrict: "E",
        templateUrl: "scripts/components/util/directive/template/reservationCancel.tpl.html",
        scope: {
            year: "@",
            month: "@",
            date: "@",
            hour: "@",
            minute: "@",
            reservationId: "@",
            reservationClose: "@",
            canceled: "=",
            used: "="
        },
        link: reservationCancelLink,
        controller: reservationCancelController,
        controllerAs: "vm",
        bindToController: true
    };
    return directive;

    function reservationCancelLink(sco, el) {
        sco.vm.buttonShow = false;
        sco.vm.scheduleAlert = false;

        sco.vm.getReservationStatus().then(function (success) {
            sco.vm.used = success.used;
            sco.vm.canceled = success.canceled;
            sco.vm.buttonShow = true;
        });

        el.find("timer").css({
            "cursor": "default"
        });

        el.find("timer").hover(function () {
            jQuery(this).css("background-color", "inherit");
        });

        if (sco.vm.timeRemaining <= 0) {
            sco.vm.buttonLock = true;
            sco.vm.timeOver = true;
        } else {
            sco.vm.buttonLock = false;
            sco.vm.timeOver = false;
        }

        sco.vm.callback = function () {
            sco.vm.buttonLock = true;
            sco.vm.timeOver = true;
        };

        sco.$watchGroup(["vm.canceled", "vm.used"], function () {
            if (sco.vm.canceled === true) {
                sco.vm.cancelText = "취소완료"
            }
            if (sco.vm.used === true) {
                sco.vm.cancelText = "출석완료"
            }

            if (sco.vm.used === false) {
                sco.vm.cancelText = "결석처리"
            }

            if (sco.vm.used === null) {
                sco.vm.cancelText = "취소하기"
            }

            if (sco.vm.timeOver === true) {
                sco.vm.cancelText = "수업시작"
            }
            if (sco.vm.canceled || (sco.vm.used === true) || (sco.vm.used === false)) {
                sco.vm.buttonLock = true;
            }
        });
    }
}

reservationCancelController.$inject = [
    '$scope',
    '$timeout',
    'Reservation',
    '$mdDialog'
];

function reservationCancelController($scope, $timeout, Reservation, $mdDialog) {
    var vm = this;

    vm.getReservationStatus = getReservationStatus;
    vm.reservationCancleDialog = reservationCancleDialog;

    getTimeRemaining();
    function getTimeRemaining() {
        var date1 = new Date(vm.year, vm.month - 1, vm.date, vm.hour,
            vm.minute, 0).getTime();

        var date2 = new Date().getTime();

        vm.timeRemaining = (date1 - date2) / 1000;

        var toMidnight = (vm.hour * 60 * 60) + (vm.minute * 60);

        switch (vm.reservationClose) {
            case '0':
                if (vm.timeRemaining < 60 * 60) {
                    vm.scheduleAlert = true;
                }
                break;
            case '1':
                if (vm.timeRemaining < 60 * 60 * 3) {
                    vm.scheduleAlert = true;
                }
                break;
            case '2':
                if (vm.timeRemaining < toMidnight) {
                    vm.scheduleAlert = true;
                }
                break;
            default:
                if (vm.timeRemaining < 60 * 60) {
                    vm.scheduleAlert = true;
                }
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
        return Reservation.get({
            id: vm.reservationId
        }).$promise
    }

    function reservationCancleDialog($event) {
        var parentEl = angular.element(document.body);
        return $mdDialog.show({
            parent: parentEl,
            targetEvent: $event,
            templateUrl: 'scripts/components/util/directive/template/reservationCancelDialog.tpl.html',
            locals: {
                reservationId: vm.reservationId
            },
            clickOutsideToClose: true,
            controller: reservationCancelDialogController,
            controllerAs: "vm"
        }).then(function (boolean) {
            vm.canceled = boolean;
        })
    }
}
