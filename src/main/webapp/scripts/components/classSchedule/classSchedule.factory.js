/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').factory('ClassSchedule', ClassSchedule);

ClassSchedule.$inject = ['$resource'];

function ClassSchedule($resource) {
    return $resource('api/classSchedules/:id', {}, {
        'query': {
            method: 'GET',
            isArray: true
        },
        'get': {
            method: 'GET',
            transformResponse: function (data) {
                data = angular.fromJson(data);
                if (data.startTime != null)
                    data.startTime = new Date(data.startTime);
                if (data.endTime != null)
                    data.endTime = new Date(data.endTime);
                return data;
            }
        },
        'update': {
            method: 'PUT'
        }
    });
}
