/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp')
    .controller('requestResetController', requestResetController);

requestResetController.$inject = ['$scope', '$timeout', 'Auth'];

function requestResetController($scope, $timeout, Auth) {
    $timeout(function () {
        angular.element('[ng-model="resetAccount.email"]').focus();
    });
    var vm = this;

    vm.success = null;
    vm.error = null;
    vm.errorEmailNotExists = null;
    vm.resetAccount = {};


    vm.requestReset = function () {
        vm.error = null;
        vm.errorEmailNotExists = null;
        Auth.resetPasswordInit(vm.resetAccount.email).then(function () {
            vm.success = 'OK';
        }).catch(function (response) {
            vm.success = null;
            if (response.status === 400 && response.data === 'e-mail address not registered') {
                vm.errorEmailNotExists = 'ERROR';
            } else {
                vm.error = 'ERROR';
            }
        });
    };

    $scope.$on('$stateChangeSuccess', function () {
        jQuery("body").addClass("loginBackground").addClass("imgBackgroundOn");
    });
    $scope.$on('$stateChangeStart', function () {
        jQuery("body").removeClass("loginBackground").removeClass("imgBackgroundOn");
    });
}
