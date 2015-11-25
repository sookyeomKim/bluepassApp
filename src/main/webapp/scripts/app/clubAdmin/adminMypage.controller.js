/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').controller('adminMypageController', adminMypageController);

adminMypageController.$inject = ['lodash', 'authorize'];

function adminMypageController(lodash, authorize) {
    var vm = this;

    vm.account = authorize;
    vm.stateConfirmAdmin = lodash.findIndex(vm.account.roles, function (chr) {
        return chr === 'ROLE_ADMIN';
    });
}
