/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').config(['$stateProvider', function ($stateProvider) {
    $stateProvider.state('actionListDetail', {
        parent: 'site',
        url: '/actionList/:id',
        data: {
            roles: []
        },
        views: {
            'content@': {
                templateUrl: 'scripts/app/actionList/detail/actionList.detail.html',
                controller: 'actionListDetailController'
            },
            'schedule@actionListDetail': {
                templateUrl: 'scripts/app/actionList/detail/actionList.detail.schedule.html',
                controller: 'actionListDetailScheduleController'
            },
            'instructor@actionListDetail': {
                templateUrl: 'scripts/app/actionList/detail/actionList.detail.instructor.html',
                controller: 'actionListDetailInstructorController'
            },
            'otherClass@actionListDetail': {
                templateUrl: 'scripts/app/actionList/detail/actionList.detail.otherClass.html',
                controller: 'actionListDetailOtherClassController'
            }
        },
        resolve: {
            $title: function () {
                return '상세보기';
            },
            pixel: ["$window", function ($window) {
                /* 페이스북 맞춤타겟 코드 */
                $window._fbq.push(['track', 'actionListDetail', {}]);
            }]
        }
    });
}]);
