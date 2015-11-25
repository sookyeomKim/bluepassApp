/**
 * Created by ksk on 2015-11-25.
 */
'use strict';

angular.module('bluepassApp').factory('CustomerByClub', CustomerByClub);

CustomerByClub.$inject = ['$resource'];

function CustomerByClub($resource) {
    return $resource('api/clubs/:id/customers', {id: "@id"}, {
        'query': {
            method: 'GET',
            isArray: true
        }
    });
}
