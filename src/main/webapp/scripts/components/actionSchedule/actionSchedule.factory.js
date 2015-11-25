/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').factory('ActionSchedule', ActionSchedule);

ActionSchedule.$inject = ['$resource'];

function ActionSchedule($resource) {
    return $resource('api/actionSchedules/:id', {}, {
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
}
