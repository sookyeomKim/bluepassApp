/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp')
    .controller('resetFinishController', resetFinishController);

resetFinishController.$inject = ['$scope', '$stateParams', '$timeout', 'Auth'];

function resetFinishController($scope, $stateParams, $timeout, Auth) {
    $timeout(function () {
        angular.element('[ng-model="resetAccount.password"]').focus();
    });
    var vm = this;


    vm.keyMissing = $stateParams.key === undefined;
    vm.doNotMatch = null;
    vm.resetAccount = {};

    vm.finishReset = function () {
        if (vm.resetAccount.password !== vm.confirmPassword) {
            vm.doNotMatch = 'ERROR';
        } else {
            Auth.resetPasswordFinish({
                key: $stateParams.key,
                newPassword: vm.resetAccount.password
            }).then(function () {
                vm.success = 'OK';
            }).catch(function () {
                vm.success = null;
                vm.error = 'ERROR';

            });
        }
    };

    $scope.$on('$stateChangeStart', function () {
        jQuery("body").removeClass("logupBackground").removeClass("imgBackgroundOn");
    });
    $scope.$on('$stateChangeSuccess', function () {
        jQuery("body").addClass("logupBackground").addClass("imgBackgroundOn");
    });
}
