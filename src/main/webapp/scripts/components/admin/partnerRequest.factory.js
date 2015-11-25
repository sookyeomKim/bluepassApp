/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').factory('PartnerRequest', PartnerRequest);

PartnerRequest.$inject = ['$resource'];

function PartnerRequest($resource) {
    return $resource('api/partnerRequests/:id', {}, {
        'query': {
            method: 'GET',
            isArray: true
        },
        'get': {
            method: 'GET',
            transformResponse: function (data) {
                data = angular.fromJson(data);
                return data;
            }
        },
        'update': {
            method: 'PUT'
        }
    });
}
