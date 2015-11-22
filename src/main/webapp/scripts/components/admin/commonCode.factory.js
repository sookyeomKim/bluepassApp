/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').factory('CommonCode', ['$resource', function ($resource) {
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
}]).factory('codeNameCommonCode', ['$resource', function ($resource) {
    return $resource('api/commonCodes/:codeName/children', {
        codeName: '@codeName'
    }, {
        'query': {
            method: 'GET',
            isArray: true
        }
    });
}]);
