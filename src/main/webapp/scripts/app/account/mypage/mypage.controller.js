/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').controller('mypageController', mypageController);

mypageController.$inject = ['$scope', 'Principal', 'lodash'];

function mypageController($scope, Principal, lodash) {
    var vm = this;

    vm.stateConfirmVendor = null;

    getAccount();
    function getAccount() {
        return Principal.identity(true).then(function (response) {
            vm.stateConfirmVendor = lodash.findIndex(response.roles, function (chr) {
                return chr === 'ROLE_VENDOR'
            });
            return $scope.$broadcast('Account', response);
        }, function () {
            alert("유저정보를 불러오지 못 하였습니다.")
        })
    }
}
