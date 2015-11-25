/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').factory('Zip', Zip);

Zip.$inject = ['$resource'];

function Zip($resource) {
    return $resource('api/zips/:id', {}, {
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
        }
    });
}
