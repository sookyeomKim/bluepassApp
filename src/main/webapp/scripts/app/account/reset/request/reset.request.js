/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').config(function ($stateProvider) {
    $stateProvider.state('requestReset', {
        parent: 'account',
        url: '/reset/request',
        data: {
            roles: []
        },
        views: {
            'content@': {
                templateUrl: 'scripts/app/account/reset/request/reset.request.html',
                controller: 'RequestResetController'
            }
        }
    });
});
