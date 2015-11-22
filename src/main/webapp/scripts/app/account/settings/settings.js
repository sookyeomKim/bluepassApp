/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').config(['$stateProvider', function ($stateProvider) {
    $stateProvider.state('settings', {
        parent: 'account',
        url: '/mypage=settings',
        data: {
            roles: ['ROLE_USER']
        },
        views: {
            'content@': {
                templateUrl: 'scripts/app/account/settings/settings.html',
                controller: 'settingsController'
            },
            'profile@settings': {
                templateUrl: 'scripts/app/account/settings/settings.profile.html',
                controller: 'settingsProfileController'
            },
            'password@settings': {
                templateUrl: 'scripts/app/account/settings/settings.password.html',
                controller: 'settingsPasswordController'
            },
            'detailInfo@settings': {
                templateUrl: 'scripts/app/account/settings/settings.detailInfo.html',
                controller: 'settingsDetailInfoController'
            }
        },
        resolve: {
            $title: function () {
                return '정보수정';
            }
        }
    });
}]);
