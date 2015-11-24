/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').config(resetConfig);

resetConfig.$inject = ['$stateProvider'];

function resetConfig($stateProvider) {
    $stateProvider.state('finishReset', {
        parent: 'account',
        url: '/reset/finish?key',
        data: {
            roles: []
        },
        views: {
            'content@': {
                templateUrl: 'scripts/app/account/reset/finish/reset.finish.html',
                controller: 'resetFinishController',
                controllerAs: 'vm'
            }
        },
        resolve: {
            validation: ['$stateParams', function ($stateParams) {
                if ($stateParams.key === undefined) {
                    $state.go($rootScope.previousStateName, $rootScope.previousStateParams);
                }
            }]
        }
    });
}
