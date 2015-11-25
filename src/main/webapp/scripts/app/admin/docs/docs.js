/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').config(docsConfig);

docsConfig.$inject = ['$stateProvider'];

function docsConfig($stateProvider) {
    $stateProvider.state('docs', {
        parent: 'admin',
        url: '/docs',
        data: {
            roles: ['ROLE_ADMIN']
        },
        views: {
            'content@': {
                templateUrl: 'scripts/app/admin/docs/docs.html'
            }
        },
        resolve: {
            $title: function () {
                return '관리자페이지';
            }
        }
    });
}
