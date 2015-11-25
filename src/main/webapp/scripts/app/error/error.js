/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').config(errorConfig);

errorConfig.$inject = ['$stateProvider'];

function errorConfig($stateProvider) {
    $stateProvider.state('error', {
        parent: 'site',
        url: '/error',
        data: {
            roles: []
        },
        views: {
            'content@': {
                templateUrl: 'scripts/app/error/error.html'
            }
        },
        resolve: {
            $title: function () {
                return '에러';
            }
        }
    }).state('accessdenied', {
        parent: 'site',
        url: '/accessdenied',
        data: {
            roles: []
        },
        views: {
            'content@': {
                templateUrl: 'scripts/app/error/accessdenied.html',
                controller: ["$timeout", "$state", "$rootScope", function ($timeout, $state, $rootScope) {
                    $timeout(function () {
                        if ($rootScope.previousStateName) {
                            if ($rootScope.previousStateName === "login") {
                                $state.go("actionListImgtype");
                            } else {
                                $state.go($rootScope.previousStateName, $rootScope.previousStateParams);
                            }
                        } else {
                            $state.go("actionListImgtype");
                        }
                    }, 1500)
                }]
            }
        },
        resolve: {
            $title: function () {
                return '접근불가';
            }
        }
    });
}
