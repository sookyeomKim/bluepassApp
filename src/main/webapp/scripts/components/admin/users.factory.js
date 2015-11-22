/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').factory('Users', ['$resource', function ($resource) {
    return $resource('api/users/', {}, {
        'query': {
            method: 'GET',
            isArray: true
        }
    });
}]);
