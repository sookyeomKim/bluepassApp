/**
 * Created by ksk on 2015-11-25.
 */
'use strict';

angular.module('bluepassApp').factory('ActionScheduleByAction', ActionScheduleByAction);

ActionScheduleByAction.$inject = ['$resource'];

function ActionScheduleByAction($resource) {
    return $resource('/api/actions/:id/actionSchedules', {
        id: '@id'
    }, {
        'query': {
            method: 'GET',
            isArray: true
        }
    });
}
