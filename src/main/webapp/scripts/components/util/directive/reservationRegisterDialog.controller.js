/**
 * Created by ksk on 2015-11-20.
 */
'use strict';

angular.module('bluepassApp').controller('reservationRegisterDialogController', reservationRegisterDialogController);

reservationRegisterDialogController.$inject = ['reservatable', 'messages', 'alreadyReservated', 'reservationParams', '$mdDialog', '$scope', 'Reservation'];

function reservationRegisterDialogController(reservatable, messages, alreadyReservated, reservationParams, $mdDialog, $scope, Reservation) {
    var vm = this;

    vm.reservatable = reservatable;
    vm.messages = messages;
    vm.alreadyReservated = alreadyReservated;
    vm.cancel = cancel;
    vm.getReservation = getReservation;

    function cancel() {
        return $mdDialog.cancel();
    }

    function getReservation() {
        return Reservation.save(reservationParams).$promise.then(function () {
            $mdDialog.hide(true);
        }).catch(function () {
            alert("에러발생");
        })
    }

    $scope.$watch("reservatable", function () {
        vm.reservatable ? vm.isDisabled = false : vm.isDisabled = true;
    });
}
