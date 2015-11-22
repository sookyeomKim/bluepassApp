/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').factory('Reservation', ['$resource', function ($resource) {
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
}]).factory('ReservationStatus', ['$resource', function ($resource) {
    return $resource('api/reservations/status', {}, {
        'call': {
            method: 'POST'
        }
    });
}]).factory('ReservationAttend', ['$resource', function ($resource) {
    return $resource('api/reservations/:id/attend', {
        id: "@id",
        checkCode: "@checkCode"
    }, {
        'check': {
            method: 'POST'
        }
    });
}]).factory('ReservationAbsent', ['$resource', function ($resource) {
    return $resource('api/reservations/:id/absence', {
        id: "@id"
    }, {
        'check': {
            method: 'POST'
        }
    });
}]).factory('ReservationOfUser', ['$resource', function ($resource) {
    return $resource('api/reservations/myshchedules', {}, {
        'query': {
            method: 'GET',
            isArray: true
        }
    });
}])

    .factory('ReservationCancel', ['$resource', function ($resource) {
        return $resource('api/reservations/:id/cancel', {
            id: "@id"
        }, {
            'cancel': {
                method: 'POST'
            }
        });
    }]).factory('ReservationUsedCheck', ['$resource', function ($resource) {
        return $resource('api/reservations/:id/used', {
            id: "@id"
        }, {
            'status': {
                method: 'GET'
            }
        });
    }]).factory('ReservationCancelCheck', ['$resource', function ($resource) {
        return $resource('api/reservations/{id}/checkcancel', {
            id: "@id"
        }, {
            'status': {
                method: 'PUT'
            }
        });
    }]);
