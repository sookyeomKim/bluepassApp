/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').factory('Feature', Feature);

Feature.$inject = ['$resource'];

function Feature($resource) {
    return $resource('api/features/:id', {}, {
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
