/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').factory('Action', ['$resource', function ($resource) {
    return $resource('api/actions/:id', {}, {
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
        },
        '_delete': {
            method: 'DELETE'
        }
    });
}]).factory('ActionByClub', ['$resource', function ($resource) {
    return $resource('/api/clubs/:id/actions', {
        id: '@id'
    }, {
        'query': {
            method: 'GET',
            isArray: true
        }
    });
}]).factory('DeleteImgByAction', ['$resource', function ($resource) {
    return $resource('/api/actions/:id/image/:imageId', {
        id: '@id'
    }, {
        'imgDelete': {
            method: 'delete',
            params: {
                imageId: '@imageId'
            }
        }
    });
}]).factory('CustomersByAction', function ($resource) {
    return $resource('/api/actions/:id/customers', {
        id: "@id"
    }, {
        'query': {
            method: 'GET',
            isArray: true
        }
    });
});
