/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').factory('Register', ['$resource', function ($resource) {
    return $resource('api/register', {}, {});
}]).factory('RegisterAdd', ['$resource', function ($resource) {
    return $resource('api/register/add', {}, {
        add: {
            method: 'POST',
            responseType: 'json'
        }
    });
}]);
