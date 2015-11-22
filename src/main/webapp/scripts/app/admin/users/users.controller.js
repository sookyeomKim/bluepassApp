/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').controller(
    'usersController',
    [
        '$scope',
        '$timeout',
        '$log',
        'Users',
        'ParseLinks',
        'DTOptionsBuilder',
        function ($scope, $timeout, $log, Users, ParseLinks, DTOptionsBuilder) {

            /* 유저들call */
            $scope.loadAll = function () {
                Users.query({
                    page: -1
                }).$promise.then(function (success) {
                        $scope.userList = success;
                    })
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
                        type: "select",
                        bRegex: false,
                        values: ['남자', '여자']
                    }, {
                        type: 'text',
                        bRegex: true,
                        bSmart: true
                    }, {
                        type: "select",
                        bRegex: false,
                        values: ['등록', '미등록', '등록요청']
                    }]
                });
        }]);
