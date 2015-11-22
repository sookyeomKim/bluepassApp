/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').factory('Account', ['$resource', function ($resource) {
    return $resource('api/account', {}, {
        'get': {
            method: 'GET',
            params: {},
            isArray: false,
            interceptor: {
                response: function (response) {
                    return response;
                }
            }
        }
    });
}]).factory('AccountChangeType', ['$resource', function ($resource) {
    return $resource('/api/account/change_type', {}, {
        'change': {
            method: 'POST'
        }
    });
}]);
