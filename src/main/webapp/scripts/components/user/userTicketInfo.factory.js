/**
 * Created by ksk on 2015-11-25.
 */
'use strict';

angular.module('bluepassApp').factory('UserTicketInfo', UserTicketInfo);

UserTicketInfo.$inject = ['$resource'];

function UserTicketInfo($resource) {
    return $resource('api/users/:id/ticket', {
        id: "@id"
    }, {

        'get': {
            method: 'GET',
            transformResponse: function (data) {
                data = angular.fromJson(data);
                return data;
            }
        }
    });
}
