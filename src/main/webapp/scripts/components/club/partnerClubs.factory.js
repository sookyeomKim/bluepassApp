/**
 * Created by ksk on 2015-11-25.
 */
'use strict';

angular.module('bluepassApp').factory('PartnerClubs', PartnerClubs);

PartnerClubs.$inject = ['$resource'];

function PartnerClubs($resource) {
    return $resource('api/partner/clubs', {}, {
        'query': {
            method: 'GET',
            isArray: true
        }
    });
}
