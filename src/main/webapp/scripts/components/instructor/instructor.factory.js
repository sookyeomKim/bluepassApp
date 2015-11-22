/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').factory('Instructor', ['$resource', function ($resource) {
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
}]).factory('InstructorByClub', ['$resource', function ($resource) {
    return $resource('/api/clubs/:id/instructors', {
        id: '@id'
    }, {
        'query': {
            method: 'GET',
            isArray: true
        }
    });
}]).factory('InstructorsByAction', ['$resource', function ($resource) {
    return $resource('/api/actions/:id/instructors', {
        id: '@id'
    }, {
        'query': {
            method: 'GET',
            isArray: true
        }
    });
}]);
