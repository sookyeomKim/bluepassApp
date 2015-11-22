/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').config(mypageConfig);

mypageConfig.$inject = ['$stateProvider'];

function mypageConfig($stateProvider) {
    $stateProvider.state('mypage', {
        parent: 'account',
        url: '/mypage',
        data: {
            roles: ['ROLE_USER', 'ROLE_REGISTER', 'ROLE_ADMIN']
        },
        views: {
            'content@': {
                templateUrl: 'scripts/app/account/mypage/mypage.html',
                controller: 'mypageController',
                controllerAs:'vm'
            },
            'profile@mypage': {
                templateUrl: 'scripts/app/account/mypage/mypage.profile.html',
                controller: 'mypageProfileController',
                controllerAs:'vm'
            },
            'schedule@mypage': {
                templateUrl: 'scripts/app/account/mypage/mypage.schedule.html',
                controller: 'mypageScheduleController',
                controllerAs:'vm'
            }
        },
        resolve: {
            $title: function () {
                return '마이페이지';
            }
        }
    }).state('bpReg', {
        parent: 'account',
        url: '/bpReg',
        data: {
            roles: ['ROLE_USER']
        },
        views: {
            'content@': {
                templateUrl: 'scripts/app/account/mypage/mypage.bpReg.html',
                controller: 'mypageBpRegController',
                controllerAs: 'vm'
            }
        },
        resolve: {
            $title: function () {
                return '회원등록';
            },
            validation: ['$state', 'Principal', function ($state, Principal) {
                if (Principal.isInRole("ROLE_REGISTER")) {
                    $state.go($rootScope.previousStateName, $rootScope.previousStateParams);
                }
            }]
        }
    }).state('bpStandby', {
        parent: 'account',
        url: '/bpStandBy?paymentWay',
        data: {
            roles: ['ROLE_USER']
        },
        views: {
            'content@': {
                templateUrl: 'scripts/app/account/mypage/mypage.standBy.html',
                controller: 'bpStandByController',
                controllerAs: 'vm'
            }
        },
        resolve: {
            $title: function () {
                return '감사합니다.';
            },
            validation: ['$stateParams', function ($stateParams) {
                if ($stateParams.paymentWay === undefined || !$stateParams.paymentWay) {
                    $state.go($rootScope.previousStateName, $rootScope.previousStateParams);
                }
            }]
        }
    });
}
