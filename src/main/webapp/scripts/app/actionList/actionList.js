/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').config(['$stateProvider', function ($stateProvider) {
    $stateProvider.state('actionListImgtype', {
        parent: 'site',
        url: '/findClass',
        data: {
            roles: []
        },
        views: {
            'content@': {
                templateUrl: 'scripts/app/actionList/actionList.html',
                controller: 'actionListController'
            },
            'imgtype@actionListImgtype': {
                templateUrl: 'scripts/app/actionList/actionList.imgType.html',
                controller: 'actionListImgTypeController'
            }
        },
        params: {
            exerciseType: null
        },
        resolve: {
            $title: function () {
                return '모든 클래스';
            }
        }
    }).state('actionListTabletype', {
        parent: 'site',
        url: '/takeReservations',
        data: {
            roles: []
        },
        views: {
            'content@': {
                templateUrl: 'scripts/app/actionList/actionList.html',
                controller: 'actionListController'
            },
            'tabletype@actionListTabletype': {
                templateUrl: 'scripts/app/actionList/actionList.tableType.html',
                controller: 'actionListTableTypeController'
            }
        },
        resolve: {
            $title: function () {
                return '모든 스케줄';
            }
        }
    })
}]);
