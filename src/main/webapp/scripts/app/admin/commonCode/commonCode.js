/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').config(['$stateProvider', function ($stateProvider) {
    $stateProvider.state('commonCode', {
        parent: 'admin',
        url: '/commonCode',
        data: {
            roles: ['ROLE_ADMIN']
        },
        views: {
            'content@': {
                templateUrl: 'scripts/app/admin/commonCode/commonCodes.html',
                controller: 'CommonCodeController'
            }
        },
        resolve: {
            $title: function () {
                return '관리자페이지';
            }
        }
    }).state('commonCodeDetail', {
        parent: 'admin',
        url: '/commonCode/:id',
        data: {
            roles: ['ROLE_ADMIN']
        },
        views: {
            'content@': {
                templateUrl: 'scripts/app/admin/commonCode/commonCodeDetail.html',
                controller: 'CommonCodeDetailController'
            }
        },
        resolve: {
            $title: function () {
                return '관리자페이지';
            }
        }
    });
}]);
