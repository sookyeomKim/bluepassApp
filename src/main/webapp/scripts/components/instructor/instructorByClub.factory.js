/**
 * Created by ksk on 2015-11-25.
 */
'use strict';

angular.module('bluepassApp').factory('InstructorByClub', InstructorByClub);

InstructorByClub.$inject = ['$resource'];

function InstructorByClub($resource) {
    return $resource('/api/clubs/:id/instructors', {
        id: '@id'
    }, {
        'query': {
            method: 'GET',
            isArray: true
        }
    });
}
