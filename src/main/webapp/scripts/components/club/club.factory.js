/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').factory('Club', Club);

Club.$inject = ['$resource'];

function Club($resource) {
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
}
