/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').config(adminMypageConfig);

adminMypageConfig.$inject = ['$stateProvider'];

function adminMypageConfig($stateProvider) {
    $stateProvider.state('adminRegManage', {
        parent: 'site',
        url: '/partnerManage',
        data: {
            roles: ['ROLE_VENDOR', 'ROLE_ADMIN']
        },
        views: {
            'content@': {
                templateUrl: 'scripts/app/clubAdmin/adminMypage.html',
                controller: 'adminMypageController',
                controllerAs: 'vm'
            },
            'regManage@adminRegManage': {
                templateUrl: 'scripts/app/clubAdmin/regManage/club/club.html',
                controller: 'adminMypageRegManageController',
                controllerAs: 'vm'
            }
        },
        resolve: {
            $title: function () {
                return '파트너페이지';
            }
        }
    }).state('adminRegManage.edit', {
        parent: 'site',
        url: '/partnerManage/:id',
        data: {
            roles: ['ROLE_VENDOR', 'ROLE_ADMIN']
        },
        views: {
            'content@': {
                templateUrl: 'scripts/app/clubAdmin/adminMypage.html',
                controller: 'adminMypageController',
                controllerAs: 'vm'
            },
            'regManage@adminRegManage.edit': {
                templateUrl: 'scripts/app/clubAdmin/regManage/club/club.edit.html',
                controller: 'adminMypageRegManageEditController',
                controllerAs: 'vm'
            }
        },
        resolve: {
            $title: function () {
                return '파트너페이지';
            }
        }
    }).state('adminRegManage.Instructor', {
        parent: 'site',
        url: '/partnerManage/:id/insManage',
        data: {
            roles: ['ROLE_VENDOR', 'ROLE_ADMIN']
        },
        views: {
            'content@': {
                templateUrl: 'scripts/app/clubAdmin/adminMypage.html',
                controller: 'adminMypageController',
                controllerAs: 'vm'
            },
            'regManage@adminRegManage.Instructor': {
                templateUrl: 'scripts/app/clubAdmin/regManage/club/clubDetail.html',
                controller: 'adminMypageRegManageDetailController',
                controllerAs: 'vm'
            },
            'instructor@adminRegManage.Instructor': {
                templateUrl: 'scripts/app/clubAdmin/regManage/instructor/instructor.html',
                controller: 'adminMypageRegManageInstructorController',
                controllerAs: 'vm'
            }
        },
        resolve: {
            $title: function () {
                return '강사관리';
            }
        }
    }).state('adminRegManage.Instructor.edit', {
        parent: 'site',
        url: '/partnerManage/:id/insManage/:insId',
        data: {
            roles: ['ROLE_VENDOR', 'ROLE_ADMIN']
        },
        views: {
            'content@': {
                templateUrl: 'scripts/app/clubAdmin/adminMypage.html',
                controller: 'adminMypageController',
                controllerAs: 'vm'
            },
            'regManage@adminRegManage.Instructor.edit': {
                templateUrl: 'scripts/app/clubAdmin/regManage/club/clubDetail.html',
                controller: 'adminMypageRegManageDetailController',
                controllerAs: 'vm'
            },
            'instructor@adminRegManage.Instructor.edit': {
                templateUrl: 'scripts/app/clubAdmin/regManage/instructor/instructor.edit.html',
                controller: 'adminMypageRegManageInstructorEditController',
                controllerAs: 'vm'
            }
        },
        resolve: {
            $title: function () {
                return '강사관리';
            }
        }
    }).state('adminRegManage.Action', {
        parent: 'site',
        url: '/partnerManage/:id/actManage',
        data: {
            roles: ['ROLE_VENDOR', 'ROLE_ADMIN']
        },
        views: {
            'content@': {
                templateUrl: 'scripts/app/clubAdmin/adminMypage.html',
                controller: 'adminMypageController',
                controllerAs: 'vm'
            },
            'regManage@adminRegManage.Action': {
                templateUrl: 'scripts/app/clubAdmin/regManage/club/clubDetail.html',
                controller: 'adminMypageRegManageDetailController',
                controllerAs: 'vm'
            },
            'action@adminRegManage.Action': {
                templateUrl: 'scripts/app/clubAdmin/regManage/action/action.html',
                controller: 'adminMypageRegManageActionController',
                controllerAs: 'vm'
            }
        },
        resolve: {
            $title: function () {
                return '클래스관리';
            }
        }
    }).state('adminRegManage.Action.edit', {
        parent: 'site',
        url: '/partnerManage/:id/actManage/:actionId',
        data: {
            roles: ['ROLE_VENDOR', 'ROLE_ADMIN']
        },
        views: {
            'content@': {
                templateUrl: 'scripts/app/clubAdmin/adminMypage.html',
                controller: 'adminMypageController',
                controllerAs: 'vm'
            },
            'regManage@adminRegManage.Action.edit': {
                templateUrl: 'scripts/app/clubAdmin/regManage/club/clubDetail.html',
                controller: 'adminMypageRegManageDetailController',
                controllerAs: 'vm'
            },
            'action@adminRegManage.Action.edit': {
                templateUrl: 'scripts/app/clubAdmin/regManage/action/action.edit.html',
                controller: 'adminMypageRegManageActionEditController',
                controllerAs: 'vm'
            }
        },
        resolve: {
            $title: function () {
                return '클래스관리';
            }
        }
    }).state('adminRegManage.Action.Schedule', {
        parent: 'site',
        url: '/partnerManage/:id/actManage/:actionId/cheduleManage',
        data: {
            roles: ['ROLE_VENDOR', 'ROLE_ADMIN']
        },
        views: {
            'content@': {
                templateUrl: 'scripts/app/clubAdmin/adminMypage.html',
                controller: 'adminMypageController',
                controllerAs: 'vm'
            },
            'regManage@adminRegManage.Action.Schedule': {
                templateUrl: 'scripts/app/clubAdmin/regManage/club/clubDetail.html',
                controller: 'adminMypageRegManageDetailController',
                controllerAs: 'vm'
            },
            'action@adminRegManage.Action.Schedule': {
                templateUrl: 'scripts/app/clubAdmin/regManage/schedule/schedule.html',
                controller: 'adminMypageRegManageScheduleController',
                controllerAs: 'vm'
            }
        },
        resolve: {
            $title: function () {
                return '스케줄관리';
            }
        }
    }).state('adminUserManage', {
        parent: 'site',
        url: '/partnersUser',
        data: {
            roles: ['ROLE_VENDOR', 'ROLE_ADMIN']
        },
        views: {
            'content@': {
                templateUrl: 'scripts/app/clubAdmin/adminMypage.html',
                controller: 'adminMypageController',
                controllerAs: 'vm'
            },
            'userManage@adminUserManage': {
                templateUrl: 'scripts/app/clubAdmin/userManage/adminMypage.userManage.html',
                controller: 'adminMypageUserManageController',
                controllerAs: 'vm'
            }
        },
        resolve: {
            $title: function () {
                return '예약관리';
            }
        }
    }).state('adminUserManage.detail', {
        parent: 'site',
        url: '/partnersUser/:id',
        data: {
            roles: ['ROLE_VENDOR', 'ROLE_ADMIN']
        },
        views: {
            'content@': {
                templateUrl: 'scripts/app/clubAdmin/adminMypage.html',
                controller: 'adminMypageController',
                controllerAs: 'vm'
            },
            'userManage@adminUserManage.detail': {
                templateUrl: 'scripts/app/clubAdmin/userManage/adminMypage.userManage.detail.html',
                controller: 'adminMypageUserManageDetailController',
                controllerAs: 'vm'
            }
        },
        resolve: {
            $title: function () {
                return '예약관리';
            }
        }
    });
}
