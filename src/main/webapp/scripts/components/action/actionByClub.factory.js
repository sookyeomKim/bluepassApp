/**
 * Created by ksk on 2015-11-25.
 */
'use strict';

angular.module('bluepassApp').factory('ActionByClub', ActionByClub);

ActionByClub.$inject = ['$resource'];

function ActionByClub($resource) {
    return $resource('/api/clubs/:id/actions', {
        id: '@id'
    }, {
        'query': {
            method: 'GET',
            isArray: true
        }
    });
}
