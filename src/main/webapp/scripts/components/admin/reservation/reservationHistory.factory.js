/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').factory('ReservationHistory', ReservationHistory);

ReservationHistory.$inject = ['$resource'];

function ReservationHistory($resource) {
    return $resource('api/reservationHistorys/:id', {}, {
        'query': {
            method: 'GET',
            isArray: true
        },
        'get': {
            method: 'GET',
            transformResponse: function (data) {
                data = angular.fromJson(data);
                return data;
            }
        },
        'update': {
            method: 'PUT'
        }
    });
}
