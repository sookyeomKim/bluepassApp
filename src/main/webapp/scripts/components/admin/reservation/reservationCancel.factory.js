/**
 * Created by ksk on 2015-11-25.
 */
'use strict';

angular.module('bluepassApp').factory('ReservationCancel', ReservationCancel);

ReservationCancel.$inject = ['$resource'];

function ReservationCancel($resource) {
    return $resource('api/reservations/:id/cancel', {
        id: "@id"
    }, {
        'cancel': {
            method: 'POST'
        }
    });
}
