/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').config(['$stateProvider', function ($stateProvider) {
    $stateProvider.state('main', {
        parent: 'site',
        url: '/',
        data: {
            roles: []
        },
        views: {
            'content@': {
                templateUrl: 'scripts/app/main/main.html',
                controller: 'mainController',
                controllerAs: 'vm'
            }
        },
        resolve: {
            $title: function () {
                return '어서와';
            }
        }
    });
}]);
