/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').config(['$stateProvider', function ($stateProvider) {
    $stateProvider.state('reservation', {
        parent: 'admin',
        url: '/reservation',
        data: {
            roles: ['ROLE_ADMIN'],
            pageTitle: 'bluepassApp.reservation.home.title'
        },
        views: {
            'content@': {
                templateUrl: 'scripts/app/admin/reservation/reservations.html',
                controller: 'ReservationController'
            }
        }
    })
}]);
