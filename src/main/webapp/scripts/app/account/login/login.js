/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').config(loginConfig);

loginConfig.$inject = ['$stateProvider'];

function loginConfig($stateProvider) {
    $stateProvider.state('login', {
        parent: 'account',
        url: '/login',
        data: {
            roles: []
        },
        views: {
            'content@': {
                templateUrl: 'scripts/app/account/login/login.html',
                controller: 'loginController',
                controllerAs: 'vm'
            }
        },
        resolve: {
            $title: function () {
                return '로그인';
            }
        }
    });
}
