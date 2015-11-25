/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').factory('User', User);

User.$inject = ['$resource'];

function User($resource) {
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
}
