/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').controller(
    'ReservationController',
    [
        '$scope',
        'Reservation',
        'ParseLinks',
        'DTOptionsBuilder',
        function ($scope, Reservation, ParseLinks, DTOptionsBuilder) {
            $scope.reservations = [];

            $scope.page = 1;
            $scope.loadAll = function () {
                Reservation.query({
                    page: -1
                }, function (result, headers) {
                    $scope.links = ParseLinks.parse(headers('link'));
                    $scope.reservations = result;
                });
            };
            $scope.loadPage = function (page) {
                $scope.page = page;
                $scope.loadAll();
            };
            $scope.loadAll();

            /* 데이터테이블 */
            $scope.dtOptions = DTOptionsBuilder.newOptions().withPaginationType('full_numbers')
                .withColumnFilter({
                    aoColumns: [{
                        type: "null"
                    }, {
                        type: 'text',
                        bRegex: true,
                        bSmart: true
                    }, {
                        type: 'text',
                        bRegex: true,
                        bSmart: true
                    }, {
                        type: 'text',
                        bRegex: true,
                        bSmart: true
                    }, {
                        type: 'text',
                        bRegex: true,
                        bSmart: true
                    }, {
                        type: 'text',
                        bRegex: true,
                        bSmart: true
                    }, {
                        type: 'text',
                        bRegex: true,
                        bSmart: true
                    }, {
                        type: "select",
                        bRegex: false,
                        values: ['취소안함', '취소함']
                    }, {
                        type: "select",
                        bRegex: false,
                        values: ['체크안함', '체크함']
                    }, {
                        type: "null"
                    }]
                });
        }]);
