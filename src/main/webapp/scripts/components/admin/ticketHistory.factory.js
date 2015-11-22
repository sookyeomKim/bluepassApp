/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').factory('TicketHistory', ['$resource', function ($resource) {
    return $resource('api/ticketHistory/:id', {}, {
        'query': {
            method: 'GET',
            isArray: true
        },
        'get': {
            method: 'GET',
            transformResponse: function (data) {
                data = angular.fromJson(data);
                if (data.requestDate != null)
                    data.requestDate = new Date(data.requestDate);
                if (data.activatedDate != null)
                    data.activatedDate = new Date(data.activatedDate);
                if (data.closeDate != null)
                    data.closeDate = new Date(data.closeDate);
                return data;
            }
        },
        'update': {
            method: 'PUT'
        }
    });
}]);
