/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').config(['$stateProvider', function ($stateProvider) {
    $stateProvider.state('ticketHistory', {
        parent: 'admin',
        url: '/ticketHistory',
        data: {
            roles: ['ROLE_ADMIN'],
            pageTitle: 'bluepassApp.ticketHistory.home.title'
        },
        views: {
            'content@': {
                templateUrl: 'scripts/app/admin/ticketHistory/ticketHistorys.html',
                controller: 'TicketHistoryController'
            }
        }
    });
}]);
