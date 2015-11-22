/**
 * 만든이 : 수겨미
 */
"use strict";

angular.module("bluepassApp").controller(
    "allPartnersController",
    [
        '$scope',
        '$log',
        '$timeout',
        'Club',
        'User',
        'ParseLinks',
        'DTOptionsBuilder',
        function ($scope, $log, $timeout, Club, User, ParseLinks, DTOptionsBuilder) {
            $scope.mpc = true;

            /* 업체/그룹 CRUD */
            $scope.clubList = [];
            $scope.loadAll = function () {
                Club.query({
                    page: -1
                }).$promise.then(function (success) {
                        $scope.clubList = success;
                        $scope.mpc = false;
                        /*console.log($scope.clubList)*/
                    })
            };

            $scope.loadAll();

            $scope.reset = function () {
                $scope.clubList = [];
                $scope.mpc = true;
                $scope.page = 1;
                $scope.loadAll();
            };

            /* 클럽삭제 */
            $scope.clubDelete = function (id) {
                Club.get({
                    id: id
                }, function (result) {
                    $scope.club = result;
                    $('#deleteClubConfirmation').modal('show');
                });
            };

            $scope.confirmDelete = function (id) {
                Club._delete({
                    id: id
                }, function () {
                    $scope.reset();
                    $('#deleteClubConfirmation').modal('hide');
                });
            };

            /* 데이터테이블 */
            $scope.dtOptions = DTOptionsBuilder.newOptions().withPaginationType('full_numbers')
                .withColumnFilter({
                    aoColumns: [{
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
                        values: ['여성전용', '남여공용']
                    }, {
                        type: 'text',
                        bRegex: true,
                        bSmart: true
                    }, {
                        type: "null"
                    }]
                });

            /* 클럽디테일 */
            $scope.detailModal = function (id) {
                $scope.clubDetail = null;
                Club.get({
                    id: id
                }).$promise.then(function (success) {
                        $scope.clubDetail = success;
                        /*console.log($scope.clubDetail)*/
                    })
            }
        }]);
