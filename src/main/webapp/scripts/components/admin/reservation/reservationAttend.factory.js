/**
 * Created by ksk on 2015-11-25.
 */
'use strict';

angular.module('bluepassApp').factory('ReservationAttend', ReservationAttend);

ReservationAttend.$inject = ['$resource'];

function ReservationAttend($resource) {
    return $resource('api/reservations/:id/attend', {
        id: "@id",
        checkCode: "@checkCode"
    }, {
        'check': {
            method: 'POST'
        }
    });
}
