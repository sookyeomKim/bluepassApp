/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').config(['$stateProvider', function ($stateProvider) {
    $stateProvider.state('finishReset', {
        parent: 'account',
        url: '/reset/finish?key',
        data: {
            roles: []
        },
        views: {
            'content@': {
                templateUrl: 'scripts/app/account/reset/finish/reset.finish.html',
                controller: 'ResetFinishController'
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
}]);
