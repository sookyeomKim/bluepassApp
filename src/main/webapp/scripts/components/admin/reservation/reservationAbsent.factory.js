/**
 * Created by ksk on 2015-11-25.
 */
'use strict';

angular.module('bluepassApp').factory('ReservationAbsent', ReservationAbsent);

ReservationAbsent.$inject = ['$resource'];

function ReservationAbsent($resource) {
    return $resource('api/reservations/:id/absence', {
        id: "@id"
    }, {
        'check': {
            method: 'POST'
        }
    });
}
