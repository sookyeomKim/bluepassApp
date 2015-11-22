/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').controller(
    'TicketHistoryController',
    [
        '$scope',
        'TicketHistory',
        'User',
        'CommonCode',
        'ParseLinks',
        'DTOptionsBuilder',
        function ($scope, TicketHistory, User, CommonCode, ParseLinks, DTOptionsBuilder) {
            $scope.loadAll = function () {
                TicketHistory.query({
                    page: -1
                }).$promise.then(function (success) {
                        $scope.ticketHistorys = success;
                    });
            };
            $scope.loadAll();

            /* 데이터테이블 */
            $scope.dtOptions = DTOptionsBuilder.newOptions().withPaginationType('full_numbers')
                .withColumnFilter({
                    aoColumns: [{
                        type: 'text',
                        bRegex: true,
                        bSmart: true
                    }, {
                        type: "select",
                        bRegex: false,
                        values: ['한달']
                    }, {
                        type: 'null'
                    }, {
                        type: "select",
                        bRegex: false,
                        values: ['true', 'falseFS']
                    }, {
                        type: 'null'
                    }, {
                        type: "null"
                    }, {
                        type: "null"
                    }]
                });
        }]);
