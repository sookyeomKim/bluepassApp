/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').config(['$stateProvider', function ($stateProvider) {
    $stateProvider.state('clause1', {
        parent: 'site',
        url: '/privacy',
        data: {
            roles: []
        },
        views: {
            'content@': {
                templateUrl: 'scripts/app/clause/privacy.html'
            }
        },
        resolve: {
            $title: function () {
                return '개인정보수집안내';
            }
        }
    }).state('clause2', {
        parent: 'site',
        url: '/terms',
        data: {
            roles: []
        },
        views: {
            'content@': {
                templateUrl: 'scripts/app/clause/terms.html'
            }
        },
        resolve: {
            $title: function () {
                return '서비스약관';
            }
        }
    }).state('clause3', {
        parent: 'site',
        url: '/qna',
        data: {
            roles: []
        },
        views: {
            'content@': {
                templateUrl: 'scripts/app/clause/qna.html'
            }
        },
        resolve: {
            $title: function () {
                return 'QNA';
            }
        }
    });
}]);
