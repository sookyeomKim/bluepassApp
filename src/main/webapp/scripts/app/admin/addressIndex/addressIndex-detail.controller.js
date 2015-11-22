/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp')
    .controller('AddressIndexDetailController', function ($scope, $rootScope, $stateParams, entity, AddressIndex) {
        $scope.addressIndex = entity;
        $scope.load = function (id) {
            AddressIndex.get({id: id}, function (result) {
                $scope.addressIndex = result;
            });
        };
        $rootScope.$on('bluepassApp:addressIndexUpdate', function (event, result) {
            $scope.addressIndex = result;
        });
    });
