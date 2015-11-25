/**
 * Created by ksk on 2015-11-25.
 */
'use strict';

angular.module('bluepassApp').factory('ReservationCancelCheck', ReservationCancelCheck);

ReservationCancelCheck.$inject = ['$resource'];

function ReservationCancelCheck($resource) {
    return $resource('api/reservations/{id}/checkcancel', {
        id: "@id"
    }, {
        'status': {
            method: 'PUT'
        }
    });
}
