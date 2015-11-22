/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').factory('LogsService', ['$resource', function ($resource) {
    return $resource('api/logs', {}, {
        'findAll': {
            method: 'GET',
            isArray: true
        },
        'changeLevel': {
            method: 'PUT'
        }
    });
}]);
