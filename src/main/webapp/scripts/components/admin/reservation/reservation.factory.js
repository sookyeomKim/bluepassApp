/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').factory('Reservation', Reservation);

Reservation.$inject = ['$resource'];

function Reservation($resource) {
    return $resource('api/reservations/:id', {}, {
        'query': {
            method: 'GET',
            isArray: true
        },
        'get': {
            method: 'GET',
            transformResponse: function (data) {
                data = angular.fromJson(data);
                if (data.reservationTime != null)
                    data.reservationTime = new Date(data.reservationTime);
                if (data.startTime != null)
                    data.startTime = new Date(data.startTime);
                if (data.endTime != null)
                    data.endTime = new Date(data.endTime);
                return data;
            }
        },
        'update': {
            method: 'PUT'
        }
    });
}
