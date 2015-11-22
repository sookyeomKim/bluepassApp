/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').controller('CommonCodeDetailController',
    ['$scope', '$stateParams', 'CommonCode', function ($scope, $stateParams, CommonCode) {
        $scope.commonCode = {};
        $scope.load = function (id) {
            CommonCode.get({
                id: id
            }, function (result) {
                $scope.commonCode = result;
            });
        };
        $scope.load($stateParams.id);
    }]);
