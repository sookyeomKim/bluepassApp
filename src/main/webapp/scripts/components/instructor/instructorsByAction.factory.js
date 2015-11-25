/**
 * Created by ksk on 2015-11-25.
 */
'use strict';

angular.module('bluepassApp').factory('InstructorsByAction', InstructorsByAction);

InstructorsByAction.$inject = ['$resource'];

function InstructorsByAction($resource) {
    return $resource('/api/actions/:id/instructors', {
        id: '@id'
    }, {
        'query': {
            method: 'GET',
            isArray: true
        }
    });
}
