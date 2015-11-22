/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').factory('ActionSchedule', ['$resource', function ($resource) {
    return $resource('api/actionSchedules/:id', {}, {
        'query': {
            method: 'GET',
            isArray: true
        },
        'get': {
            method: 'GET',
            transformResponse: function (data) {
                data = angular.fromJson(data);
                /* data.startTime 1970-01-01-01T14:00:00Z */
                /*
                 * if (data.startTime != null) data.startTime = new
                 * Date(data.startTime); data.startTime Thu Jan 01 1970 23:00:00
                 * GMT+0900 if (data.endTime != null) data.endTime = new
                 * Date(data.endTime); if (data.startDate != null)
                 * data.startDate = new Date(data.startDate); if (data.endDate !=
                 * null) data.endDate = new Date(data.endDate);
                 */
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
}]).factory('ActionScheduleByaAction', ['$resource', function ($resource) {
    return $resource('/api/actions/:id/actionSchedules', {
        id: '@id'
    }, {
        'query': {
            method: 'GET',
            isArray: true
        }
    });
}]).factory('ActionScheduleEnable', ['$resource', function ($resource) {
    return $resource('/api/actionSchedules/:id/active', {
        id: '@id',
        enable: '@enable'
    }, {
        'enable': {
            method: 'POST'
        }
    });
}]);
