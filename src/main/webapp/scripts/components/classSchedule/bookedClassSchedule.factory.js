/**
 * Created by ksk on 2015-11-25.
 */
'use strict';

angular.module('bluepassApp').factory('BookedClassSchedule',BookedClassSchedule);

BookedClassSchedule.$inject=['$resource'];

function BookedClassSchedule($resource) {
    return $resource('api/classSchedules/:id/reservations', {
        id: "@id"
    }, {
        'query': {
            method: 'GET',
            isArray: true
        }
    });
}
