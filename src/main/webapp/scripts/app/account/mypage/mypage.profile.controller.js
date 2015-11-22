/**
 * 만든이 : 수겨미
 */
'use strict';
angular.module('bluepassApp').controller('mypageProfileController', mypageProfileController);

mypageProfileController.$inject = ['$scope', 'lodash'];

function mypageProfileController($scope, lodash) {
    var vm = this;

    vm.account = {};
    vm.stateConfirmRegister = null;
    vm.stateConfirmVendor = null;
    vm.stateConfirmAdmin = null;

    getAccount();
    function getAccount() {
        return $scope.$on('Account', function (event, response) {
            vm.stateConfirmRegister = lodash.findIndex(response.roles, function (chr) {
                return chr === 'ROLE_REGISTER'
            });
            vm.stateConfirmVendor = lodash.findIndex(response.roles, function (chr) {
                return chr === 'ROLE_VENDOR'
            });
            vm.stateConfirmAdmin = lodash.findIndex(response.roles, function (chr) {
                return chr === 'ROLE_ADMIN'
            });
            return vm.account = response;
        })
    }
}
