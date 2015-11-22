/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').config(partnersHomeConfig);

partnersHomeConfig.$inject = ['$stateProvider'];

function partnersHomeConfig($stateProvider) {
    $stateProvider.state('partnersHome', {
        parent: 'site',
        url: '/withPartners',
        data: {
            roles: []
        },
        views: {
            'content@': {
                templateUrl: 'scripts/app/partnersMain/partnersMain.html',
                controller: 'partnersMainController',
                controllerAs: 'vm'
            }
        },
        resolve: {
            $title: function () {
                return '함께 만들어가요';
            }
        }
    });
}
