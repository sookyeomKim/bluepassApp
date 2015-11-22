/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').controller('AddressIndexDialogController',
    ['$scope', '$stateParams', '$modalInstance', 'entity', 'AddressIndex',
        function ($scope, $stateParams, $modalInstance, entity, AddressIndex) {

            $scope.addressIndex = entity;
            $scope.load = function (id) {
                AddressIndex.get({id: id}, function (result) {
                    $scope.addressIndex = result;
                });
            };

            var onSaveFinished = function (result) {
                $scope.$emit('bluepassApp:addressIndexUpdate', result);
                $modalInstance.close(result);
            };

            $scope.save = function () {
                if ($scope.addressIndex.id != null) {
                    AddressIndex.update($scope.addressIndex, onSaveFinished);
                } else {
                    AddressIndex.save($scope.addressIndex, onSaveFinished);
                }
            };

            $scope.clear = function () {
                $modalInstance.dismiss('cancel');
            };
        }]);
