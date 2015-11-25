/**
 * Created by ksk on 2015-11-25.
 */
'use strict';

angular.module('bluepassApp').factory('ReservationOfUser', ReservationOfUser);

ReservationOfUser.$inject = ['$resource'];

function ReservationOfUser($resource) {
    return $resource('api/reservations/myshchedules', {}, {
        'query': {
            method: 'GET',
            isArray: true
        }
    });
}
