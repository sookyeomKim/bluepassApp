/**
 * Created by ksk on 2015-11-25.
 */
'use strict';

angular.module('bluepassApp').factory('ReservationStatus', ReservationStatus);

ReservationStatus.$inject = ['$resource'];

function ReservationStatus($resource) {
    return $resource('api/reservations/status', {}, {
        'call': {
            method: 'POST'
        }
    });
}
