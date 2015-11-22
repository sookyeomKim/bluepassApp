/**
 * 만든이 : 수겨미
 */

'use strict';

angular.module('bluepassApp').config(activateConfig);

activateConfig.$inject = ['$stateProvider'];

function activateConfig($stateProvider) {
    $stateProvider.state('activate', {
        parent: 'account',
        url: '/activate?key',
        data: {
            roles: []
        },
        views: {
            'content@': {
                templateUrl: 'scripts/app/account/activate/activate.html',
                controller: 'activationController',
                controllerAs: 'vm'
            }
        },
        resolve: {
            $title: function () {
                return '인증하기';
            }
        }
    });
}
