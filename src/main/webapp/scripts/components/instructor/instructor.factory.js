/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').factory('Instructor',Instructor);

Instructor.$inject=['$resource'];

function Instructor($resource) {
    return $resource('api/instructors/:id', {}, {
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
