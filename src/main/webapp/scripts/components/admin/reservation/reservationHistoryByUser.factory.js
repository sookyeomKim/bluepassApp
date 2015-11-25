/**
 * Created by ksk on 2015-11-25.
 */
'use strict';

angular.module('bluepassApp').factory('ReservationHistoryByUser', ReservationHistoryByUser);

ReservationHistoryByUser.$inject = ['$resource'];

function ReservationHistoryByUser($resource) {
    return $resource('api/ReservationHistoryByUsers', {}, {
        'query': {
            method: 'GET',
            isArray: true
        }
    });
}
