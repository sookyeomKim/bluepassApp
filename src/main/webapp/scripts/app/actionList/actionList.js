/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').config(actionListConfig);

actionListConfig.$inject = ['$stateProvider'];

function actionListConfig($stateProvider) {
    $stateProvider.state('actionListImgtype', {
        parent: 'site',
        url: '/findClass',
        data: {
            roles: []
        },
        views: {
            'content@': {
                templateUrl: 'scripts/app/actionList/actionList.html',
                controller: 'actionListController',
                controllerAs: 'vm'
            },
            'imgtype@actionListImgtype': {
                templateUrl: 'scripts/app/actionList/actionList.imgType.html',
                controller: 'actionListImgTypeController',
                controllerAs: 'vm'
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
                controller: 'actionListController',
                controllerAs: 'vm'
            },
            'tabletype@actionListTabletype': {
                templateUrl: 'scripts/app/actionList/actionList.tableType.html',
                controller: 'actionListTableTypeController',
                controllerAs: 'vm'
            }
        },
        resolve: {
            $title: function () {
                return '모든 스케줄';
            }
        }
    })
}
