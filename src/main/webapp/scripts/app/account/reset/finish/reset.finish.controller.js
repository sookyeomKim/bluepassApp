/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp')
    .controller('ResetFinishController', ['$scope', '$stateParams', '$timeout', 'Auth', function ($scope, $stateParams, $timeout, Auth) {
        $scope.$on('$stateChangeStart', function () {
            jQuery("body").removeClass("logupBackground").removeClass("imgBackgroundOn");
        });
        $scope.$on('$stateChangeSuccess', function () {
            jQuery("body").addClass("logupBackground").addClass("imgBackgroundOn");
        });
        $scope.keyMissing = $stateParams.key === undefined;
        $scope.doNotMatch = null;

        $scope.resetAccount = {};
        $timeout(function () {
            angular.element('[ng-model="resetAccount.password"]').focus();
        });

        $scope.finishReset = function () {
            if ($scope.resetAccount.password !== $scope.confirmPassword) {
                $scope.doNotMatch = 'ERROR';
            } else {
                Auth.resetPasswordFinish({
                    key: $stateParams.key,
                    newPassword: $scope.resetAccount.password
                }).then(function () {
                    $scope.success = 'OK';
                }).catch(function () {
                    $scope.success = null;
                    $scope.error = 'ERROR';

                });
            }
        };
    }]);
