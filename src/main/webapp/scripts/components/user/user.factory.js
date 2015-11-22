/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').factory('User', ['$resource', function ($resource) {
    return $resource('api/users/:login', {}, {
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
        }
    });
}]).factory('UserTicketInfo', ['$resource', function ($resource) {
    return $resource('api/users/:id/ticket', {
        id: "@id"
    }, {

        'get': {
            method: 'GET',
            transformResponse: function (data) {
                data = angular.fromJson(data);
                return data;
            }
        }
    });
}]);
