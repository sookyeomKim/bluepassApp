/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp')
    .controller('CommonCodeController', ['$scope', 'CommonCode', function ($scope, CommonCode) {
        /* 공통코드CRUD */
        $scope.commonCodes = [];
        $scope.loadAll = function () {
            CommonCode.query({page: -1}, function (result) {
                $scope.commonCodes = result;
            });
        };
        $scope.loadAll();

        $scope.showUpdate = function (id) {
            CommonCode.get({id: id}, function (result) {
                $scope.commonCode = result;
                jQuery('#saveCommonCodeModal').modal('show');
            });
        };

        $scope.save = function () {
            if ($scope.commonCode.id != null) {
                CommonCode.update($scope.commonCode,
                    function () {
                        $scope.refresh();
                    });
            } else {
                CommonCode.save($scope.commonCode,
                    function () {
                        $scope.refresh();
                    });
            }
        };

        $scope.delete = function (id) {
            CommonCode.get({id: id}, function (result) {
                $scope.commonCode = result;
                jQuery('#deleteCommonCodeConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            CommonCode.delete({id: id},
                function () {
                    $scope.loadAll();
                    jQuery('#deleteCommonCodeConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            jQuery('#saveCommonCodeModal').modal('hide');
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.commonCode = {
                name: null,
                value: null,
                description: null,
                option1: null,
                option2: null,
                option3: null,
                id: null
            };
            $scope.editForm.$setPristine();
            $scope.editForm.$setUntouched();
        };

        /* 종류별로보기 */
        $scope.queryCommonCode = "";
    }]);
