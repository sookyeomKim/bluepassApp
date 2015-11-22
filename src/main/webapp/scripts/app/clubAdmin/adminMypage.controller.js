/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').controller('adminMypageController',
    ['$scope', 'Principal', 'lodash', function ($scope, Principal, lodash) {
        $scope.isAuthenticated = Principal.isAuthenticated;

        /* 현재 유저 정보 */
        Principal.identity().then(function (success) {
            $scope.account = success;
            $scope.stateConfirm = lodash.findIndex($scope.account.roles, function (chr) {
                return chr === 'ROLE_ADMIN';
            });
        });
    }]);
