/**
 * 만든이 : 수겨미
 */
'use strict';

angular.module('bluepassApp').controller('adminMypageRegManageController', adminMypageRegManageController);

adminMypageRegManageController.$inject = [
    'PartnerClubs', 'Club', 'Alert', 'lodash'
];

function adminMypageRegManageController(PartnerClubs, Club, Alert, lodash) {
    var vm = this;

    vm.mpc = true;
    vm.clubList = [];
    vm.deleteConfirm = deleteConfirm;

    /* 업체/그룹 불러오기 */
    getPartnerClubs();

    function getPartnerClubs() {
        return PartnerClubs.query({
            page: -1
        }).$promise.then(function (success) {
                vm.clubList = success;
                vm.mpc = false;
            })
    }

    function deleteConfirm($event, id) {
        return Alert.alert2($event, '삭제', '정말 삭제하시겠습니까?', '삭제하기').then(function () {
            return Club.delete({id: id}).$promise.then(function () {
                var delIndex = lodash.findIndex(vm.clubList, {id: id});
                vm.clubList.splice(delIndex, 1);
                return Alert.alert1('삭제되었습니다.')
            }).catch(function () {
                return Alert.alert1('에러가 발생하여 삭제되지 않았습니다.')
            })
        });
    }
}
