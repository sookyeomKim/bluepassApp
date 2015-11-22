/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp')
    .controller('RequestResetController', function ($rootScope, $scope, $state, $timeout, Auth) {
        $scope.$on('$stateChangeSuccess', function () {
            jQuery("body").addClass("loginBackground").addClass("imgBackgroundOn");
        });
        $scope.$on('$stateChangeStart', function () {
            jQuery("body").removeClass("loginBackground").removeClass("imgBackgroundOn");
        });

        $scope.success = null;
        $scope.error = null;
        $scope.errorEmailNotExists = null;
        $scope.resetAccount = {};
        $timeout(function () {
            angular.element('[ng-model="resetAccount.email"]').focus();
        });

        $scope.requestReset = function () {
            $scope.error = null;
            $scope.errorEmailNotExists = null;
            Auth.resetPasswordInit($scope.resetAccount.email).then(function () {
                $scope.success = 'OK';
            }).catch(function (response) {
                $scope.success = null;
                if (response.status === 400 && response.data === 'e-mail address not registered') {
                    $scope.errorEmailNotExists = 'ERROR';
                } else {
                    $scope.error = 'ERROR';
                }
            });
        };
    });
