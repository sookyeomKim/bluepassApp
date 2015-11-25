/**
 * Created by ksk on 2015-11-25.
 */
'use strict';

angular.module('bluepassApp').factory('ActionScheduleEnable', ActionScheduleEnable);

ActionScheduleEnable.$inject = ['$resource'];

function ActionScheduleEnable($resource) {
    return $resource('/api/actionSchedules/:id/active', {
        id: '@id',
        enable: '@enable'
    }, {
        'enable': {
            method: 'POST'
        }
    });
}
