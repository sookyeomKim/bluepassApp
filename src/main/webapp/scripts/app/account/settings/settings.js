/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').config(settingsConfig);

settingsConfig.$inejct = ['$stateProvider'];

function settingsConfig($stateProvider) {
    $stateProvider.state('settings', {
        parent: 'account',
        url: '/mypage=settings',
        data: {
            roles: ['ROLE_USER']
        },
        views: {
            'content@': {
                templateUrl: 'scripts/app/account/settings/settings.html',
                controller: 'settingsController',
                controllerAs: 'vm'
            },/*
            'profile@settings': {
                templateUrl: 'scripts/app/account/settings/settings.profile.html',
                controller: 'settingsProfileController',
                controllerAs: 'vm'
            },*/
            'password@settings': {
                templateUrl: 'scripts/app/account/settings/settings.password.html',
                controller: 'settingsPasswordController',
                controllerAs: 'vm'
            },
            'detailInfo@settings': {
                templateUrl: 'scripts/app/account/settings/settings.detailInfo.html',
                controller: 'settingsDetailInfoController',
                controllerAs: 'vm'
            }
        },
        resolve: {
            $title: function () {
                return '정보수정';
            }
        }
    });
}
