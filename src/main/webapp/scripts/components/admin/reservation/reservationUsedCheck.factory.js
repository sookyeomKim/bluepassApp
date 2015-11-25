/**
 * Created by ksk on 2015-11-25.
 */
'use strict';

angular.module('bluepassApp').factory('ReservationUsedCheck', ReservationUsedCheck);

ReservationUsedCheck.$inject = ['$resource'];

function ReservationUsedCheck($resource) {
    return $resource('api/reservations/:id/used', {
        id: "@id"
    }, {
        'status': {
            method: 'GET'
        }
    });
}
