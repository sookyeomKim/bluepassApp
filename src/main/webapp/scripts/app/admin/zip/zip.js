/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').config(['$stateProvider', function ($stateProvider) {
    $stateProvider.state('zip', {
        parent: 'admin',
        url: '/zip',
        data: {
            roles: ['ROLE_USER'],
            pageTitle: 'bluepassApp.zip.home.title'
        },
        views: {
            'content@': {
                templateUrl: 'scripts/app/admin/zip/zips.html',
                controller: 'ZipController'
            }
        }
    })
}]);
