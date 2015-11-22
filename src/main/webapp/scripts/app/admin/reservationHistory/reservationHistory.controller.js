/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp')
    .controller('ReservationHistoryController', ['$scope', 'ReservationHistory', 'ParseLinks', function ($scope, ReservationHistory, ParseLinks) {
        $scope.reservationHistorys = [];
        $scope.page = 1;
        $scope.loadAll = function () {
            ReservationHistory.query({page: $scope.page, per_page: 20}, function (result, headers) {
                $scope.links = ParseLinks.parse(headers('link'));
                $scope.reservationHistorys = result;
            });
        };
        $scope.loadPage = function (page) {
            $scope.page = page;
            $scope.loadAll();
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            ReservationHistory.get({id: id}, function (result) {
                $scope.reservationHistory = result;
                jQuery('#deleteReservationHistoryConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            ReservationHistory.delete({id: id},
                function () {
                    $scope.loadAll();
                    jQuery('#deleteReservationHistoryConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.reservationHistory = {
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
        };
    }]);
