/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').config(['$stateProvider', function ($stateProvider) {
    $stateProvider.state('reservationHistory', {
        parent: 'admin',
        url: '/reservationHistorys',
        data: {
            roles: ['ROLE_USER'],
            pageTitle: 'ReservationHistorys'
        },
        views: {
            'content@': {
                templateUrl: 'scripts/app/admin/reservationHistory/reservationHistorys.html',
                controller: 'ReservationHistoryController'
            }
        },
        resolve: {}
    }).state('reservationHistory.detail', {
        parent: 'admin',
        url: '/reservationHistory/{id}',
        data: {
            roles: ['ROLE_USER'],
            pageTitle: 'ReservationHistory'
        },
        views: {
            'content@': {
                templateUrl: 'scripts/app/admin/reservationHistory/reservationHistory-detail.html',
                controller: 'ReservationHistoryDetailController'
            }
        },
        resolve: {
            entity: ['$stateParams', 'ReservationHistory', function ($stateParams, ReservationHistory) {
                return ReservationHistory.get({
                    id: $stateParams.id
                });
            }]
        }
    }).state('reservationHistory.new', {
        parent: 'reservationHistory',
        url: '/new',
        data: {
            roles: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$modal', function ($stateParams, $state, $modal) {
            $modal.open({
                templateUrl: 'scripts/app/admin/reservationHistory/reservationHistory-dialog.html',
                controller: 'ReservationHistoryDialogController',
                size: 'lg',
                resolve: {
                    entity: function () {
                        return {
                            reservationId: null,
                            reservationMethod: null,
                            reservationTime: null,
                            startTime: null,
                            endTime: null,
                            used: null,
                            userId: null,
                            userEmail: null,
                            userName: null,
                            userPhoneNumber: null,
                            userGender: null,
                            registerStatus: null,
                            actionScheduleId: null,
                            actionScheduleName: null,
                            day: null,
                            scheduleType: null,
                            attendeeLimit: null,
                            classScheduleId: null,
                            classScheduleEtc: null,
                            clubId: null,
                            clubName: null,
                            clubZipcode: null,
                            clubAddress1: null,
                            clubAddress2: null,
                            clubPhoneNumber: null,
                            onlyFemail: null,
                            clubManagerMobile: null,
                            notificationType: null,
                            reservationClose: null,
                            actionId: null,
                            actionTitle: null,
                            actionDescription: null,
                            actionUseLimitType: null,
                            actionUseLimitValue: null,
                            instructorId: null,
                            instructorName: null,
                            categoryId: null,
                            categoryName: null,
                            ticketId: null,
                            ticketName: null,
                            id: null
                        };
                    }
                }
            }).result.then(function () {
                    $state.go('reservationHistory', null, {
                        reload: true
                    });
                }, function () {
                    $state.go('reservationHistory');
                })
        }]
    }).state('reservationHistory.edit', {
        parent: 'reservationHistory',
        url: '/{id}/edit',
        data: {
            roles: ['ROLE_USER']
        },
        onEnter: ['$stateParams', '$state', '$modal', function ($stateParams, $state, $modal) {
            $modal.open({
                templateUrl: 'scripts/app/admin/reservationHistory/reservationHistory-dialog.html',
                controller: 'ReservationHistoryDialogController',
                size: 'lg',
                resolve: {
                    entity: ['ReservationHistory', function (ReservationHistory) {
                        return ReservationHistory.get({
                            id: $stateParams.id
                        });
                    }]
                }
            }).result.then(function () {
                    $state.go('reservationHistory', null, {
                        reload: true
                    });
                }, function () {
                    $state.go('^');
                })
        }]
    });
}]);
