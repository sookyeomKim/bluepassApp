/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp')
    .controller('registerController', registerController);

registerController.$inject = ['$scope', '$timeout', 'Auth', '$stateParams', 'Alert'];

function registerController($scope, $timeout,  Auth, $stateParams, Alert) {
    var vm = this;

    vm.getRegister = getRegister;
    vm.clauseCheck = false;
    vm.success = null;
    vm.registerAccount = {};

    if ($stateParams.email) {
        vm.registerAccount.email = $stateParams.email;
        $timeout(function () {
            angular.element('[ng-model="vm.registerAccount.password"]').focus();
        });
    } else {
        $timeout(function () {
            angular.element('[ng-model="vm.registerAccount.email"]').focus();
        });
    }

    function getRegister() {
        if (vm.registerAccount.password !== vm.confirmPassword) {
            Alert.alert1("비밀번호가 일치하지 않습니다.");
        } else {
            vm.registerAccount.langKey = "kr";

            Auth.createAccount(vm.registerAccount).then(function () {
                vm.success = 'OK';
            }).catch(function (response) {
                vm.success = null;
                if (response.status === 400 && response.data === '이미 존재하는 이메일입니다.') {
                    Alert.alert1("이미 사용 중인 이메일입니다.")
                } else {
                    Alert.alert1("회원가입에 실패하였습니다. 다시 시도해주세요.")
                }
            });
        }
    }

    $scope.$on('$stateChangeStart', function () {
        jQuery("body").removeClass("logupBackground").removeClass("imgBackgroundOn");
    });
    $scope.$on('$stateChangeSuccess', function () {
        jQuery("body").addClass("logupBackground").addClass("imgBackgroundOn");
    });
}
