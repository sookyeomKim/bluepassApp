/**
 * Created by ksk on 2015-11-25.
 */
'use strict';

angular.module('bluepassApp').factory('CustomersByAction', CustomersByAction);

CustomersByAction.$inject = ['$resource'];

function CustomersByAction($resource) {
    return $resource('/api/actions/:id/customers', {
        id: "@id"
    }, {
        'query': {
            method: 'GET',
            isArray: true
        }
    });
}
