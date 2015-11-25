/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').factory('Action', Action);

Action.$inject = ['$resource'];

function Action($resource) {
    return $resource('api/actions/:id', {}, {
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
}
