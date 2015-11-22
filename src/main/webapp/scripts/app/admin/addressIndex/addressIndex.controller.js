/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp')
    .controller('AddressIndexController', ['$scope', 'AddressIndex', function ($scope, AddressIndex) {
        $scope.addressIndexs = [];
        $scope.loadAll = function () {
            AddressIndex.query(function (result) {
                $scope.addressIndexs = result;
            });
        };
        $scope.loadAll();

        $scope.delete = function (id) {
            AddressIndex.get({id: id}, function (result) {
                $scope.addressIndex = result;
                jQuery('#deleteAddressIndexConfirmation').modal('show');
            });
        };

        $scope.confirmDelete = function (id) {
            AddressIndex.delete({id: id},
                function () {
                    $scope.loadAll();
                    jQuery('#deleteAddressIndexConfirmation').modal('hide');
                    $scope.clear();
                });
        };

        $scope.refresh = function () {
            $scope.loadAll();
            $scope.clear();
        };

        $scope.clear = function () {
            $scope.addressIndex = {oldAddress: null, id: null};
        };
    }]);
