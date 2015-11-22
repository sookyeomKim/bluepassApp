/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').config(['$stateProvider', function ($stateProvider) {
    $stateProvider.state('users', {
        parent: 'admin',
        url: '/users',
        data: {
            roles: ['ROLE_ADMIN']
        },
        views: {
            'content@': {
                templateUrl: 'scripts/app/admin/users/users.html',
                controller: 'usersController'
            }
        },
        resolve: {
            $title: function () {
                return '관리자페이지';
            }
        }
    })
}]);
