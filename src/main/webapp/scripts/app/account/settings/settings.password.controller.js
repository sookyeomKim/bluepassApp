/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').controller('settingsPasswordController', settingsPasswordController);

settingsPasswordController.$inject = ['$scope', 'Auth', 'Alert'];

function settingsPasswordController($scope, Auth, Alert) {
    var vm = this;

    vm.doNotMatch = null;
    vm.settingsAccount = {};

    getAccount();
    function getAccount() {
        return $scope.$on('Account', function (event, response) {
            return vm.settingsAccount = response;
        })
    }

    vm.getChangePassword = function () {
        if (vm.password !== vm.confirmPassword) {
            return Alert.alert1('비밀번호가 일치하지 않습니다.')
        } else {
            vm.doNotMatch = null;
            return Auth.changePassword(vm.password).then(function () {
                return Auth.login({
                    username: vm.settingsAccount.email,
                    password: vm.password
                }).then(function () {
                    vm.password = null;
                    vm.confirmPassword = null;
                    return Alert.alert1('변경완료')
                })
            }).catch(function () {
                return Alert.alert1('변경실패')
            });
        }
    };
}
