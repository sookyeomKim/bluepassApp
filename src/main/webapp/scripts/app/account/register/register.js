/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').config(['$stateProvider', function ($stateProvider) {
    $stateProvider.state('register', {
        parent: 'account',
        url: '/register',
        data: {
            roles: []
        },
        params: {
            email: null
        },
        views: {
            'content@': {
                templateUrl: 'scripts/app/account/register/register.html',
                controller: 'registerController',
                controllerAs: 'vm'
            }
        },
        resolve: {
            $title: function () {
                return '회원가입';
            }
        }
    });
}]);
