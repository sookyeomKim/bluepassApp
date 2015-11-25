/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').factory('CommonCode', CommonCode);

CommonCode.$inject = ['$resource'];

function CommonCode($resource) {
    return $resource('api/commonCodes/:id', {}, {
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
