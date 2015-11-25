/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').controller('adminMypageUserManageController', adminMypageUserManageController);

adminMypageUserManageController.$inject = ['PartnerClubs'];

function adminMypageUserManageController(PartnerClubs) {
    var vm = this;

    /*초기화*/
    vm.mpc = true;
    vm.clubList = [];

    /*클럽목록불러오기*/
    getpartnerClubsQuery();

    function getpartnerClubsQuery() {
        return PartnerClubs.query({page: -1}).$promise.then(function (success) {
            vm.clubList = success;
            vm.mpc = false;
        })
    }
}
