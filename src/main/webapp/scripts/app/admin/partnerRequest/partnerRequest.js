'use strict';

angular.module('bluepassApp').config(partnerRequestConfig);

partnerRequestConfig.$inject = ['$stateProvider'];

function partnerRequestConfig($stateProvider) {
    $stateProvider.state('partnerRequest', {
        parent: 'admin',
        url: '/partnerRequest',
        data: {
            roles: ['ROLE_ADMIN']
        },
        views: {
            'content@': {
                templateUrl: 'scripts/app/admin/partnerRequest/partnerRequests.html',
                controller: 'partnerRequestController',
                controllerAs: 'vm'
            }
        },
        resolve: {
            $title: function () {
                return '관리자페이지';
            }
        }
    })
}
