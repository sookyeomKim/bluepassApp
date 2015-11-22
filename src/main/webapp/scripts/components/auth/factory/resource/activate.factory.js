/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').factory('Activate', ['$resource', function ($resource) {
    return $resource('api/activate', {}, {
        'get': {
            method: 'GET',
            params: {},
            isArray: false
        }
    });
}]);
