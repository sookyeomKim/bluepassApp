/**
 * 만든이 : 수겨미
 */
"use strict";

angular.module('bluepassApp').config(['$stateProvider', function ($stateProvider) {
    $stateProvider.state('allPartners', {
        parent: 'admin',
        url: '/allPartners',
        data: {
            roles: ['ROLE_ADMIN']
        },
        views: {
            'content@': {
                templateUrl: 'scripts/app/admin/allPartners/allPartners.html',
                controller: 'allPartnersController'
            }
        },
        resolve: {
            $title: function () {
                return '관리자페이지';
            }
        }
    })
}]);
