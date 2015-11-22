/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').factory('PartnerRequest', ['$resource', function ($resource) {
    return $resource('api/partnerRequests/:id', {}, {
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
}]);
