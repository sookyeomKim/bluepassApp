/**
 * Created by ksk on 2015-11-20.
 */
'use strict';
angular.module('bluepassApp').controller('reservationCancelDialogController', reservationCancelDialogController);

reservationCancelDialogController.$inject = ['$mdDialog', 'ReservationCancel', 'reservationId'];

function reservationCancelDialogController($mdDialog, ReservationCancel, reservationId) {
    var vm = this;

    vm.cancel = cancel;
    vm.getReservationCancel = getReservationCancel;

    function cancel() {
        return $mdDialog.cancel();
    }

    function getReservationCancel() {
        return ReservationCancel.cancel({
            id: reservationId
        }).$promise.then(function () {
                $mdDialog.hide(true);
            }).catch(function () {
                alert("에러발생")
            })
    }


}
