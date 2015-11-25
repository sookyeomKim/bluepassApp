/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').controller('adminMypageRegManageDetailController', adminMypageRegManageDetailController);

adminMypageRegManageDetailController.$inject = ['$stateParams', 'Club'];

function adminMypageRegManageDetailController($stateParams, Club) {
    var vm = this;

    /* 탭 */
    vm.tabSelected = 1;

    /* 클럽 정보 불러오기 */
    getClubGet($stateParams.id);

    function getClubGet(id) {
        return Club.get({id: id}).$promise.then(function (response) {
            return vm.club = response;
        })
    }
}
