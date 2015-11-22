/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').controller('settingsPasswordController', ['$scope', 'Auth', function ($scope, Auth) {
    $scope.success = null;
    $scope.error = null;
    $scope.doNotMatch = null;

    $scope.changePassword = function () {
        if ($scope.password !== $scope.confirmPassword) {
            $scope.doNotMatch = 'ERROR';
        } else {
            $scope.doNotMatch = null;
            Auth.changePassword($scope.password).then(function () {
                Auth.login({
                    username: $scope.settingsAccount.email,
                    password: $scope.password
                });
                $scope.password = null;
                $scope.confirmPassword = null;
                $scope.error = null;
                $scope.success = 'OK';
            }).catch(function () {
                $scope.success = null;
                $scope.error = 'ERROR';
            });
        }
    };
}]);
