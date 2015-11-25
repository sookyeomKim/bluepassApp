/**
 * Created by ksk on 2015-11-25.
 */
'use strict';

angular.module('bluepassApp').factory('ClassScheduleCount', ClassScheduleCount);

ClassScheduleCount.$inject = ['$resource'];

function ClassScheduleCount($resource) {
    return $resource('api/classSchedules/count', {}, {
        'query': {
            method: 'GET',
            isArray: false
        }
    });
}
