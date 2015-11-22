/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').factory('Club', ['$resource', function ($resource) {
    return $resource('api/clubs/:id', {}, {
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
        },
        '_delete': {
            method: 'DELETE'
        }
    });
}]).factory('partnerClubs', ['$resource', function ($resource) {
    return $resource('api/partner/clubs', {}, {
        'query': {
            method: 'GET',
            isArray: true
        }
    });
}]).factory('customerByClub', ['$resource', function ($resource) {
    return $resource('api/clubs/:id/customers', {
        id: "@id"
    }, {
        'query': {
            method: 'GET',
            isArray: true
        }
    });
}]);
